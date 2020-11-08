package pokerControl;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import pokerModelo.Baraja;
import pokerModelo.Carta;
import pokerModelo.JugadorCPU;
import pokerVista.VistaPoker;

public class ControlPoker {
	private Random random;
	private ArrayList<Integer> turnos;
	private int turnoActual = 1;
	private Baraja baraja;
	private List<List<Carta>> manoJugadores;
	private ArrayList<String> nombres;
	private JugadorCPU jugador1,jugador2,jugador4,jugador5;
	private List<Boolean> tipoJugador;
	private List<Integer> dinero;
	private Lock bloqueo = new ReentrantLock(); //manejo de sincronizacion
	private Condition esperarTurno = bloqueo.newCondition();
	private boolean tipoRonda = true;//true apostar,descartar
	private ArrayList<JugadorCPU> jugadoresCPU;
	private VistaPoker vista;
	private int[] descarte = new int[5];
	public ControlPoker() {
		random = new Random();
		turnos = new ArrayList<Integer>();
		nombres = new ArrayList<String>();
		tipoJugador = new ArrayList<Boolean>();
		dinero = new ArrayList<Integer>();
		baraja = new Baraja();
		manoJugadores = new ArrayList<List<Carta>>();
		jugadoresCPU = new ArrayList<JugadorCPU>();
		descarte = new int[5];
		iniciarJuego();
		
		
		vista = new VistaPoker(tipoJugador,nombres, manoJugadores,dinero);
	}
	
	private void iniciarJuego() {
		baraja = new Baraja();
		for(int i = 0; i < 5 ;i++) {
			manoJugadores.add(seleccionarCartas());
		}
		iniciarJugadoresCPU();
		darTipo();
		agregarNombres();
		darTipo();
		darDinero();
	}
	
	private void agregarNombres() {
			

			nombres.add(jugador1.getNombre());
			nombres.add(jugador2.getNombre());
			nombres.add("Santiago");
			nombres.add(jugador4.getNombre());
			nombres.add(jugador5.getNombre());
	
		
	}
	
	private void darTipo() {
		
			tipoJugador.add(false);
			tipoJugador.add(false);
			tipoJugador.add(true);
			tipoJugador.add(false);
			tipoJugador.add(false);
		
		
	}
	
	private void darDinero() {
		


		
			dinero.add(jugador1.getDineroInicial());
			dinero.add(jugador2.getDineroInicial());
			dinero.add(500);
			dinero.add(jugador4.getDineroInicial());
			dinero.add(jugador5.getDineroInicial());
	
		
	}
	
	
	
	private ArrayList<Carta> seleccionarCartas() {
		// TODO Auto-generated method stub
		ArrayList<Carta> manoJugador = new ArrayList<Carta>();
		//se dan 2 cartas al jugador
		manoJugador.add(baraja.getCarta());
		manoJugador.add(baraja.getCarta());
		manoJugador.add(baraja.getCarta());
		manoJugador.add(baraja.getCarta());
		manoJugador.add(baraja.getCarta());
		return manoJugador;
	}
	
	
	public void asignarCartas() {
		
	}
	
	public void iniciarJugadoresCPU() {
		int aux = random.nextInt(5)+1;
		jugador1 = new JugadorCPU( 500, "Samuel",1);
		jugador2 = new JugadorCPU( 600, "David",2);
		jugador4 = new JugadorCPU(400, "Valentina",3);
		jugador5 = new JugadorCPU(400, "Luisa",4);
		
		switch(aux) {
		case 1:
			jugador1.setTurno(1);
			jugador2.setTurno(2);
			jugador4.setTurno(4);
			jugador5.setTurno(5);
			
		break;
		case 2:
			jugador1.setTurno(5);
			jugador2.setTurno(1);
			jugador4.setTurno(3);
			jugador5.setTurno(4);
			break;
		case 3:
			jugador1.setTurno(4);
			jugador2.setTurno(5);
			jugador4.setTurno(2);
			jugador5.setTurno(3);
			break;
		case 4:
			jugador1.setTurno(3);
			jugador2.setTurno(4);
			jugador4.setTurno(1);
			jugador5.setTurno(2);
		 break;
		case 5:
			jugador1.setTurno(2);
			jugador2.setTurno(3);
			jugador4.setTurno(5);
			jugador5.setTurno(1);
			break;
		}

		 jugadoresCPU.add(jugador1);
		 jugadoresCPU.add(jugador2);
		 jugadoresCPU.add(jugador4);
		 jugadoresCPU.add(jugador5);
		 
		  ExecutorService ejecutorSubprocesos = Executors.newCachedThreadPool();
		  ejecutorSubprocesos.execute(jugador1); 
		  ejecutorSubprocesos.execute(jugador2);
		  
		  ejecutorSubprocesos.shutdown();
		  
	
		 
	}

	public void turnos(int turnoJugador, int cartasPedidas) {
		
		bloqueo.lock();
		
		try
		{
			while(turnoJugador!= turnoActual) {
				esperarTurno.await();
			}
			
			if(tipoRonda) {//roda para apostar
				for(int i=0;i<5;i++) {
					if(jugadoresCPU.get(i).getTurno() ==turnoActual) {
						if(jugadoresCPU.get(i).apostar(100)) {
							vista.actualizarVistaApuesta(100, jugadoresCPU.get(i).getNombre());
						}
					}
				}
			}else { //descarte
				int posicionDescarte = turnoActual-1;
				
				if(posicionDescarte !=2) {
					for(int i=0;i<5;i++) {
						if(jugadoresCPU.get(i).getTurno() ==turnoActual) {
							jugadoresCPU.get(i).descartarCartas();
							darCartas(jugadoresCPU.get(i),jugadoresCPU.get(i).getCantidadADescartar());
						}	
					descarte[turnoActual-1] = cartasPedidas;
				}
				
				}
			}
			turnoActual++;
			esperarTurno.signalAll();
		}catch(Exception e) {
			
		}finally {
			bloqueo.unlock();
			if(turnoActual==6) {
				if(tipoRonda==false) 
				{
				//	while()
					tipoRonda=true;
					
				}
				
				if(tipoRonda ) {
					tipoRonda=false;
				}
				
			}
		}
		
	}

	
	//Dar cartas iniciales (Se llama en la ronda 1) No se usa para descartar cartas
	public void darCartasIniciales(JugadorCPU jugador) {
		ArrayList<Carta> cartasADar = new ArrayList<Carta>();
		for(int i = 0; i < 5; i++) {
			cartasADar.add(baraja.getCarta());
		}
		jugador.recibirCartasIniciales(cartasADar);
	}
	
	private void darCartas(JugadorCPU jugador,int cartasADescartar) {	
       
		for(int i=0;i<cartasADescartar;i++) { 
			jugador.recibirCartas(baraja.getCarta());
		}
		
	}
	
	//calcula cuantas cartas dar a cada jugador, luego del descarte
	private void asignarCartas(List<Carta> manoJugador) {
		if(manoJugador.size()==0) {//asignar 2 cartas
			manoJugador.add(baraja.getCarta());
			manoJugador.add(baraja.getCarta());
		}else {
			if(manoJugador.size()==1) {//dar 1 carta
				manoJugador.add(baraja.getCarta());
			}
		}			
	}
	
	//Descarta y se le da cartas al instante
	public void descartarCartas(JugadorCPU jugador) {
		jugador.descartarCartas();
		//Damos la cantidad de cartas que el jugador descarto
		for(int i = 0; i < jugador.getCantidadADescartar(); i++) {
			jugador.recibirCartas(baraja.getCarta());
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 EventQueue.invokeLater(new Runnable() {
	        	public void run() {
	        		new ControlPoker();
	        		
	        	}
	        });
	}
}
