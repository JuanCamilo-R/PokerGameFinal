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

import javax.swing.JPanel;

import pokerModelo.Baraja;
import pokerModelo.Carta;
import pokerModelo.JugadorCPU;
import pokerVista.PanelJugador;
import pokerVista.VistaPoker;

public class ControlPoker {
	private Random random;
	private ArrayList<Integer> turnos;
	private int turnoActual = 1;
	private int apuestaUsuario;
	private Baraja baraja;
	private List<List<Carta>> manoJugadores;
	private ArrayList<String> nombres;
	VistaPoker vista;
	private JugadorCPU jugador1,jugador2,jugador4,jugador5;
	private List<Boolean> tipoJugador;
	private List<Integer> dinero;
	private int[] descarte;
	private Lock bloqueo = new ReentrantLock(); //manejo de sincronizacion
	private Condition esperarTurno = bloqueo.newCondition(); //manejo de sincronizacion
	private boolean tipoRonda = true; //True si es el momento de apostar y false si es el momento de descartar
	private ArrayList<JugadorCPU> jugadoresCPU;
	public ControlPoker() {
		turnos = new ArrayList<Integer>();
		nombres = new ArrayList<String>();
		random = new Random();
		jugadoresCPU = new ArrayList<JugadorCPU>();
		tipoJugador = new ArrayList<Boolean>();
		dinero = new ArrayList<Integer>();
		baraja = new Baraja();
		descarte = new int[5];
		manoJugadores = new ArrayList<List<Carta>>();
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
			dinero.add(jugador5.getDineroInicial());
			dinero.add(jugador4.getDineroInicial());
	
		
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
		/*JugadorCPU */jugador1 = new JugadorCPU(500, "Samuel", 1);
		/*JugadorCPU */jugador2 = new JugadorCPU(600, "David", 2);
		/*JugadorCPU */jugador4 = new JugadorCPU(400, "Valentina", 3);
		/*JugadorCPU */jugador5 = new JugadorCPU(400, "Santiago", 4);
		switch(aux) {
			case 1:
				jugador1.setTurno(1);
				jugador2.setTurno(2);
				jugador4.setTurno(4);
				jugador5.setTurno(5);
				break;
			case 2:
				jugador2.setTurno(1);
				jugador4.setTurno(3);
				jugador5.setTurno(4);
				jugador1.setTurno(5);
				break;
			case 3:
				jugador4.setTurno(2);
				jugador5.setTurno(3);
				jugador1.setTurno(4);
				jugador2.setTurno(5);
				break;
			case 4:
				jugador4.setTurno(1);
				jugador5.setTurno(2);
				jugador1.setTurno(3);
				jugador2.setTurno(4);
				break;
			case 5:
				jugador5.setTurno(1);
				jugador1.setTurno(2);
				jugador2.setTurno(3);
				jugador4.setTurno(5);
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
			while(turnoJugador != turnoActual) {
				esperarTurno.await();
			}
			
			if(tipoRonda) { //Ronda de apuesta
				for(int i = 0; i < 5; i++) {
					if(jugadoresCPU.get(i).getTurno() == turnoActual) {
						if(jugadoresCPU.get(i).apostar(100)) {
							vista.actualizarVistaApuesta(100, jugadoresCPU.get(i).getNombre());
						}
					}
				}
			} else { //Ronda de descarte
				int posicionDescarte = turnoActual - 1;
				if(posicionDescarte != 2) {
					for(int i = 0; i < 5; i++) {
						if(jugadoresCPU.get(i).getTurno() == turnoActual)
						{
							jugadoresCPU.get(i).descartarCartas();
						}
					}
					descarte[posicionDescarte] = cartasPedidas;
				}
			}
			turnoActual++;
			esperarTurno.signalAll();
		}catch(Exception e) {
			
		}finally {
			bloqueo.unlock();
			if(turnoActual == 6) {
				if(tipoRonda == false) { //Ronda de descarte
					tipoRonda = true;
				}
				if(tipoRonda) { //Ronda de apuesta
					tipoRonda = false;
				}
			}
		}
		
	}
	
	public void darCartas(JugadorCPU jugador, int cartasADar) {
		for(int i = 0; i < cartasADar; i++ ) {
			jugador.recibirCartas(baraja.getCarta());
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
	//Descarta y se le da cartas al instante
	public void descartarCartas(JugadorCPU jugador) {
		jugador.descartarCartas();
		//Damos la cantidad de cartas que el jugador descarto
		for(int i = 0; i < jugador.getCantidadADescartar(); i++) {
			jugador.recibirCartas(baraja.getCarta());
		}
	}
	
	public void obtenerApuestaUsuario(PanelJugador jugadorUsuario) {
		apuestaUsuario = vista.darApuestaUsuario();
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
