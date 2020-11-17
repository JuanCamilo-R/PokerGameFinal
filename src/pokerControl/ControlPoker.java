package pokerControl;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;

import javafx.util.Pair;
import pokerModelo.Baraja;
import pokerModelo.Carta;
import pokerModelo.JugadorCPU;
import pokerVista.MesaJuego;
import pokerVista.PanelJugador;
import pokerVista.VistaPoker;

public class ControlPoker {
	private Random random;
	private ArrayList<Integer> turnos;
	private int turnoActual = 1;
	private int apuestaUsuario;
	private Baraja baraja;
	private List<List<Carta>> manoJugadores;
	private List<String> nombres;
	private VistaPoker vista;
	private MesaJuego mesaJuego;
	private PanelJugador panelUsuario;
	private JugadorCPU jugador1,jugador2,jugador4,jugador5;
	private List<Boolean> tipoJugador;
	private List<Integer> dinero;
	private int[] descarte;
	private int ronda = 0;
	private boolean dormir = false;
	private Lock bloqueo = new ReentrantLock(); //manejo de sincronizacion
	private Condition esperarTurnoApuesta = bloqueo.newCondition(); //manejo de sincronizacion
	private Condition esperarTurnoDescarte = bloqueo.newCondition();
	private boolean tipoRonda = true; //True si es el momento de apostar y false si es el momento de descartar
	private List<JugadorCPU> jugadoresCPU;
	List<List<Carta>> manosIguales;
	private int posicionDescarte;
	private int controlador = 0;
	private int posicionEmpate;
	public ControlPoker() {
		turnos = new ArrayList<Integer>();
		nombres = new ArrayList<String>();
		random = new Random();
		jugadoresCPU = new ArrayList<JugadorCPU>();
		tipoJugador = new ArrayList<Boolean>();
		manosIguales = new ArrayList<List<Carta>>();
		dinero = new ArrayList<Integer>();
		baraja = new Baraja();
		descarte = new int[5];
		manoJugadores = new ArrayList<List<Carta>>();
		
		iniciarJuego();
		vista = new VistaPoker(tipoJugador,nombres, manoJugadores,dinero, this);
		this.mesaJuego = vista.getMesaJuego();
		panelUsuario = (PanelJugador) mesaJuego.getPanelUsuario();
		iniciarJugadoresCPU();		

	}
	
	private void iniciarJuego() {
		baraja = new Baraja();
		for(int i = 0; i < 5 ;i++) {
			manoJugadores.add(seleccionarCartas());
		//	jugadoresCPU.get(i).recibirCartasIniciales(manoJugadores.get(i));
		}
		
		
		definirJugadoresCPU();
		darTipo();
		agregarNombres();
		darTipo();
		darDinero();
		
		
	}
	
	private void agregarNombres() {
			
			
			nombres.add(jugador1.getNombre());
			nombres.add(jugador2.getNombre());
			nombres.add("ElBicho");
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
			dinero.add(15000);
			dinero.add(jugador5.getDineroInicial());
			dinero.add(jugador4.getDineroInicial());
	
		
	}
	
	
	
	private ArrayList<Carta> seleccionarCartas() {
		// TODO Auto-generated method stub
		ArrayList<Carta> manoJugador = new ArrayList<Carta>();
		manoJugador.add(baraja.getCarta());
		manoJugador.add(baraja.getCarta());
		manoJugador.add(baraja.getCarta());
		manoJugador.add(baraja.getCarta());
		manoJugador.add(baraja.getCarta());
		
		return manoJugador;
	}
	
	public void reiniciarJuego() {
		PanelJugador.apuestaMinima=100;
		System.out.print("\033[H\033[2J");  
	    System.out.flush();  
		tipoRonda = true;
		controlador = 0;
		turnoActual = 1;
		for(int i = 0; i < 5; i++) {
			manoJugadores.get(i).clear();
		}
		manoJugadores.clear();
		jugadoresCPU.clear();
		manosIguales.clear();
		dinero.clear();
		nombres.clear();
		tipoJugador.clear();
		iniciarJuego();
		mesaJuego.reiniciarJuego(manoJugadores,dinero);
		//activarRondaApuestas();
		iniciarJugadoresCPU();
		reiniciarApuesta();
		
	}
	
	
	public void definirJugadoresCPU() {
		/*JugadorCPU */jugador1 = new JugadorCPU(1500, "Samuel", 1, this);
		/*JugadorCPU */jugador2 = new JugadorCPU(1500, "David", 2, this);
		/*JugadorCPU */jugador4 = new JugadorCPU(1500, "Valentina", 3, this);
		/*JugadorCPU */jugador5 = new JugadorCPU(1500, "Santiago", 4, this);
	}
	
	public void iniciarJugadoresCPU() {
		
		int aux = random.nextInt(5)+1;
		mesaJuego.espaciar();
		mesaJuego.mensaje("Orden de turnos:");
		switch(2) {
			case 1:
				jugador1.setTurno(1);
				mesaJuego.mensaje(jugador1.getNombre());
				jugador2.setTurno(2);
				mesaJuego.mensaje(jugador2.getNombre());
				panelUsuario.setTurno(3);
				mesaJuego.mensaje(nombres.get(2));
				jugador4.setTurno(4);
				mesaJuego.mensaje(jugador4.getNombre());
				jugador5.setTurno(5);
				mesaJuego.mensaje(jugador5.getNombre());
				break;
			case 2:
				jugador2.setTurno(1);
				mesaJuego.mensaje(jugador2.getNombre());
				panelUsuario.setTurno(2);
				mesaJuego.mensaje(nombres.get(2));
				jugador4.setTurno(3);
				mesaJuego.mensaje(jugador4.getNombre());
				jugador5.setTurno(4);
				mesaJuego.mensaje(jugador5.getNombre());
				jugador1.setTurno(5);
				mesaJuego.mensaje(jugador1.getNombre());
				break;
			case 3:
				System.out.print("Inicias");
				panelUsuario.setTurno(1);
				mesaJuego.mensaje(nombres.get(2));
				jugador4.setTurno(2);
				mesaJuego.mensaje(jugador4.getNombre());
				jugador5.setTurno(3);
				mesaJuego.mensaje(jugador5.getNombre());
				jugador1.setTurno(4);
				mesaJuego.mensaje(jugador1.getNombre());
				jugador2.setTurno(5);
				mesaJuego.mensaje(jugador2.getNombre());
				break;
			case 4:
				jugador4.setTurno(1);
				mesaJuego.mensaje(jugador4.getNombre());
				jugador5.setTurno(2);
				mesaJuego.mensaje(jugador5.getNombre());
				jugador1.setTurno(3);
				mesaJuego.mensaje(jugador1.getNombre());
				jugador2.setTurno(4);
				mesaJuego.mensaje(jugador2.getNombre());
				panelUsuario.setTurno(5);
				mesaJuego.mensaje(nombres.get(2));
				break;
			case 5:
				jugador5.setTurno(1);
				mesaJuego.mensaje(jugador5.getNombre());
				jugador1.setTurno(2);
				mesaJuego.mensaje(jugador1.getNombre());
				jugador2.setTurno(3);
				mesaJuego.mensaje(jugador2.getNombre());
				panelUsuario.setTurno(4);
				mesaJuego.mensaje(nombres.get(2));
				jugador4.setTurno(5);
				mesaJuego.mensaje(jugador4.getNombre());
		}

		
		  jugadoresCPU.add(jugador1);
		  jugadoresCPU.add(jugador2);
		  jugadoresCPU.add(jugador4);
		  jugadoresCPU.add(jugador5);
		
		for(int i = 0; i < 4 ;i++) {
			jugadoresCPU.get(i).recibirCartasIniciales(manoJugadores.get(i));
			if(i>=2) {
				jugadoresCPU.get(i).recibirCartasIniciales(manoJugadores.get(i+1));
			}
		}
		mesaJuego.espaciar();
	      ExecutorService ejecutorSubprocesos = Executors.newCachedThreadPool();
		  ejecutorSubprocesos.execute(jugador1); 
		  ejecutorSubprocesos.execute(jugador2);
		  ejecutorSubprocesos.execute(jugador4);
		  ejecutorSubprocesos.execute(jugador5);
		  
		  ejecutorSubprocesos.shutdown();
		  
		  
	}
	public int getTurno() {
		return turnoActual;
	}
	public void activarRondaDescarte() {
		turnoActual = 1;
		if(controlador <= 3) {
			setControlador();	
		}
		
		//System.out.println("CONTROLADOR EN DESCARTE: "+controlador);
		if(controlador == 3) {
			System.out.println("Entro a controlador = 3");
			System.out.println("El ganador es : "+determinarGanador(determinarParejas()));
			/*
			List<Carta> manoJugadorSamuel;
			System.out.println("");
			manoJugadorSamuel = ordenarPorNumero(manoJugadores.get(0));
			for(int i = 0; i < manoJugadorSamuel.size(); i++) {
				System.out.print(manoJugadorSamuel.get(i).getValorNumerico()+ "  ");
			}
			*/
		}
		tipoRonda = false;
		for(int i=0;i<jugadoresCPU.size();i++) {
			jugadoresCPU.get(i).iniciarRondaDescarte();
		}
	}
	public void setControlador() {
		controlador++;
	}
	public int getControlador() {
		return controlador;
	}
	public void activarRondaApuestas() {
		turnoActual = 1;
		tipoRonda = true;
		if(controlador <= 3) {
			setControlador();
		}
		
		//System.out.println("CONTROLADOR EN APUESTAS: "+controlador);
		for(int i = 0; i < jugadoresCPU.size(); i++) {
			jugadoresCPU.get(i).iniciarRondaApuesta();
		}
	}
	public void turnos(int turnoJugador, String nombreJugador, int dato, int dineroInicial) {
		bloqueo.lock();


		//System.out.println(nombreJugador+ "entro a turnos");
		
		try
		{
			
			
			if(tipoRonda) { //Ronda de apuesta
				while(turnoJugador != turnoActual && !panelUsuario.getSiguienteTurno()) {
					System.out.println(nombreJugador+" intento a ronda apuesta entrar pero se duerme");
					esperarTurnoApuesta.await();
				}
				System.out.println(nombreJugador+" va a apostar en turnos: "+dato);
				if(turnoJugador <= 5 && setApuestaJugador(nombreJugador,dato)) {
					for(int i=0;i<jugadoresCPU.size();i++) {
						if(jugadoresCPU.get(i).getNombre()==nombreJugador) {
							vista.actualizarVistaApuesta(dato, nombreJugador, String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
						}
					}
					verificarApuesta(nombreJugador);
					
					
				}

				//vista.funcionPrueba();
				if(turnoJugador <= 5) {
					//System.out.println("TURNO ACTUAL EN APUESTA: "+turnoActual);
					turnoActual++;
					//System.out.println(nombreJugador+" TURNO DEL HILO QUE ENTRA: "+turnoJugador);
				}
				
				esperarTurnoApuesta.signalAll();
			}else {
				while(turnoJugador != turnoActual && !panelUsuario.getSiguienteTurno()) {
					System.out.println(nombreJugador+" intento a ronda descarte entrar pero se duerme");
					esperarTurnoDescarte.await();
				}
				if(turnoJugador <= 5) {
					 posicionDescarte = turnoActual - 1;
					 descartar(dato, nombreJugador,turnoJugador);
					 //System.out.println("TURNO ACTUAL EN DESCARTE: "+turnoActual);
					 turnoActual++;
					 //System.out.println(nombreJugador+" TURNO DEL HILO QUE ENTRA: "+turnoJugador);
				}
				//System.out.println(nombreJugador+ "es el encargado de despertar a todos");
				esperarTurnoDescarte.signalAll();
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally {
			//panelUsuario.setSiguienteTurno(false);
			if(turnoActual == 6 && tipoRonda) {
				verificarApuestasFinal(nombreJugador);
			}
			bloqueo.unlock();
			panelUsuario.setSiguienteTurno(false);
			/*
			if(turnoActual == 6) {
				if(tipoRonda == false) { //Ronda de descarte
					System.out.print("Empiezo ronda apuesta");
					tipoRonda = true;
					turnoActual = 1;
					panelUsuario.setSiguienteTurno(false);
					
				}
				if(tipoRonda) { //Ronda de apuesta
					tipoRonda = false;
					controlar++;
					verificarApuestasFinal(nombreJugador);
					System.out.print("Empiezo ronda descarte");
					//panelUsuario.setSiguienteTurno(false);
					System.out.print("Tipo ronda"+tipoRonda);
					turnoActual = 1;
					System.out.print("Tipo turno"+turnoActual);
				}
			*/
			}
		}
	
	public void descartar(int dato, String nombreJugador, int turnoJugador) {
		for(int i = 0; i < jugadoresCPU.size(); i++) {
			if(jugadoresCPU.get(i).getTurno() == turnoActual)
			{
				descarte[posicionDescarte] = dato;
				//System.out.println("Entro hilo: "+jugadoresCPU.get(i).getNombre()+"222");
				jugadoresCPU.get(i).descartarCartas();				
				darCartas(jugadoresCPU.get(i).getCantidadADescartar(),i);
				vista.actualizarVistaCartas( jugadoresCPU.get(i).getNombre(),jugadoresCPU.get(i).getCartas());
				//vista.actualizarVistaApuesta(0, jugadoresCPU.get(i).getNombre(), String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
				//System.out.println("de"+panelUsuario.getSiguienteTurno()+"descarte \n");
				
				
				
			}
		}
	}
	
	public void interrumpirHilos() {
		jugador1.interrumpir();
		jugador2.interrumpir();
		jugador4.interrumpir();
		jugador5.interrumpir();
	}
	public void darCartas(int cantidad,int i) {	
        //cartas para jugadores simulados
		//System.out.print("Dar cartasaaaa \n");
		List<Carta> cartas2 = new ArrayList<Carta>();
		
		for(int j=0;j<cantidad;j++) {
			cartas2.add(baraja.getCarta());
		}
		asignarCartas(cartas2,i,cantidad);
				
	}
	public boolean getTipoRonda() {
		return tipoRonda;
	}
	
	
	public void reiniciarApuesta() {
		
		for(int i = 0; i < jugadoresCPU.size(); i++) {
			jugadoresCPU.get(i).reiniciarApuesta();
			if(jugadoresCPU.get(i).getTurno() < panelUsuario.getTurno()) {
				vista.actualizarVistaApuesta(jugadoresCPU.get(i).getApuestaActual(), jugadoresCPU.get(i).getNombre(),
						String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
			}else {
				System.out.println("ENTRO AQUIIIIIIIII8"+jugadoresCPU.get(i).getNombre());
				vista.actualizarVistaApuesta(0, jugadoresCPU.get(i).getNombre(),
						String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
			}
			
		}
	}
	public void asignarCartas(List<Carta> cartas,int i,int cantidad) {
		for(int j=0;j<cantidad;j++) {
			jugadoresCPU.get(i).recibirCartas(cartas.get(j));
			//System.out.print("Cantidad "+jugadoresCPU.get(i).getCantidadADescartar()+"   ");
			//System.out.print("Valor "+cartas.get(j).getValor()+"   \n");
		}

	}
	
	public synchronized void verificarApuesta(String nombreJugador) {
		for(int i = 0; i < jugadoresCPU.size(); i++) {
			if(jugadoresCPU.get(i).getNombre() == nombreJugador) {
				//System.out.println(jugadoresCPU.get(i).getNombre()+ " veces apostado: "+jugadoresCPU.get(i).getVecesApostado());
				if(jugadoresCPU.get(i).getApuestaActual() != panelUsuario.getApuestaUsuario()) {
					//System.out.println(jugadoresCPU.get(i).getNombre()+" verifica apuestas en la mitad");
					int apuestaCPU = jugadoresCPU.get(i).getApuestaActual();
					int apuestaUsuario = PanelJugador.apuestaMinima;
					//System.out.println("Dinero actual de "+jugadoresCPU.get(i).getNombre()+ "antes de recibir su apuesta: "+jugadoresCPU.get(i).getDineroInicial());
					jugadoresCPU.get(i).devolverApuesta(jugadoresCPU.get(i).getApuestaActual());
					if(jugadoresCPU.get(i).apostar(apuestaUsuario)) {
						if(jugadoresCPU.get(i).getVecesApostado() != 3) {
							System.out.println(nombreJugador+ "apuesta: "+apuestaUsuario);
							//System.out.println("Llego aqui");
							vista.actualizarVistaApuesta(apuestaUsuario, nombreJugador, String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
							vista.actualizarAreaEstado(apuestaUsuario, nombreJugador, " apuesta para igualar ");
						}else {
							vista.actualizarAreaEstado(apuestaUsuario, nombreJugador, " apuesta ");
							//System.out.println("Dinero actual de "+jugadoresCPU.get(i).getNombre()+ "despues de recibir su apuesta: "+jugadoresCPU.get(i).getDineroInicial());
							System.out.println("Apuesta de "+nombreJugador+ " ha apostado: "+jugadoresCPU.get(i).getApuestaActual());
							vista.actualizarVistaApuesta(apuestaUsuario, nombreJugador, String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
						}
						
			
					}else {
						vista.actualizarVistaApuesta(0, jugadoresCPU.get(i).getNombre(), String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
						//System.out.println(jugadoresCPU.get(i).getNombre()+ "ha recibido por no poder apostar en la mitad: "+jugadoresCPU.get(i).getApuestaActual());
						//System.out.println("Dinero actual de "+jugadoresCPU.get(i).getNombre()+ "despues de no apostar otra vez en la mitad: "+jugadoresCPU.get(i).getDineroInicial());
						
					}
				}
			}
			
		}
	}
	public  synchronized void verificarApuestasFinal(String nombre) {
		/*
		for(int i = 0; i < jugadoresCPU.size(); i++) {
			jugadoresCPU.get(i).run();
		}
		*/
		dormir = false;
		for(int i = 0; i < jugadoresCPU.size(); i++) {
			if(jugadoresCPU.get(i).getApuestaActual() != panelUsuario.getApuestaUsuario()) {
				System.out.println(jugadoresCPU.get(i).getNombre()+" verifica apuestas al final");
				int apuestaUsuario = PanelJugador.apuestaMinima;
				int apuestaCPU = jugadoresCPU.get(i).getApuestaActual();
				System.out.println("Apuesta de "+jugadoresCPU.get(i).getNombre()+ " antes de apostar otra vez al final "+jugadoresCPU.get(i).getApuestaActual());
				jugadoresCPU.get(i).devolverApuesta(jugadoresCPU.get(i).getApuestaActual());
				//System.out.println("Dinero actual de "+jugadoresCPU.get(i).getNombre()+ "antes de apostar otra vez al final: "+jugadoresCPU.get(i).getDineroInicial());
				
				if(jugadoresCPU.get(i).apostar(apuestaUsuario)){
					vista.actualizarAreaEstado(apuestaUsuario, nombre, " apuesta al final para igualar: ");
					//System.out.println("Dinero actual de "+jugadoresCPU.get(i).getNombre()+ "despues de apostar su apuesta: "+jugadoresCPU.get(i).getDineroInicial());
					System.out.println("Apuesta de "+nombre+ " ha apostado al final: "+jugadoresCPU.get(i).getApuestaActual());
					vista.actualizarVistaApuesta(apuestaUsuario, jugadoresCPU.get(i).getNombre(), String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
			
				}else {
					vista.actualizarVistaApuesta(0, jugadoresCPU.get(i).getNombre(), String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
					//System.out.println("Dinero actual de "+jugadoresCPU.get(i).getNombre()+ "despues de apostar otra vez al final: "+jugadoresCPU.get(i).getDineroInicial());
				
				}
			}
		}
	}
	public synchronized boolean setApuestaJugador(String nombreJugador, int cantidadApuesta) {
		for(int i = 0; i < jugadoresCPU.size(); i++) {
			if(jugadoresCPU.get(i).getNombre() == nombreJugador) {
				if(jugadoresCPU.get(i).apostar(cantidadApuesta)) {
					//System.out.println(jugadoresCPU.get(i).getNombre()+" aposto: "+cantidadApuesta);
					if(cantidadApuesta>PanelJugador.apuestaMinima) {
						PanelJugador.apuestaMinima=cantidadApuesta;
						//System.out.println(nombreJugador+" aumentó la apuesta mínima a "+cantidadApuesta);
					}
					return true;
				}
			}
		}
		return false;
	}
	public void setTurnoActual() {
		turnoActual++;
	}
	/*
	public void darCartas(JugadorCPU jugador, int cartasADar) {
		for(int i = 0; i < cartasADar; i++ ) {
			jugador.recibirCartas(baraja.getCarta());
		}
	}*/
	
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
		//jugador.descartarCartas();
		//Damos la cantidad de cartas que el jugador descarto
		for(int i = 0; i < jugador.getCantidadADescartar(); i++) {
			jugador.recibirCartas(baraja.getCarta());
		}
	}
	
	public void descarteHumano(Carta cartaEliminada) {
		
		for(int i=0;i<manoJugadores.get(2).size();i++) {
			if(cartaEliminada == manoJugadores.get(2).get(i)) {
				//System.out.println("Elimino carta "+cartaEliminada.getValorNumerico());
				//System.out.print("Entreeeeeee a descarte humano");
				manoJugadores.get(2).remove(i);
			}
		}
		//System.out.println("MANO EN DESCARTE HUMANO CONTROL");
		//for(int i = 0; i < manoJugadores.get(2).size(); i++) {
			//System.out.println(manoJugadores.get(2));
		//}
	}
	
	public void agregarCarta(List<Carta> nuevasCartas) {
		
		//System.out.println("MANO JUGADOR HUMANO ANTES DE AÑADIR NUEVAS CARTAS");
		//System.out.println(manoJugadores.get(2));
		for(int i=0;i<nuevasCartas.size();i++) {
			manoJugadores.get(2).add(nuevasCartas.get(i));
		}
		//System.out.println("MANO JUGADOR HUMANO EN CONTROL SIZE: "+manoJugadores.get(2));
		panelUsuario.recibirCartasHumano(manoJugadores.get(2));
	}
	
	

	
	public void darCartasHumanos(int contadorCartas) {
		List<Carta> cartasNuevasHumano = new ArrayList<Carta>();
		for(int i=0;i<contadorCartas;i++) {
			cartasNuevasHumano.add(baraja.getCarta());
		}
		System.out.println("CARTAS NUEVAS HUMANO");
		System.out.println(cartasNuevasHumano);
		agregarCarta(cartasNuevasHumano);
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
	//Verificar que todas las cartas tengan el mismo color
	public boolean verificarColor(List<Carta> manoJugador) { //True si todas tienen el mismo color, false si no
		for(int i = 0; i < manoJugador.size()-1; i++) {
			if(manoJugador.get(i).getPalo() != manoJugador.get(i+1).getPalo()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean escaleraRealColor(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenado;
		int aux = 10;
		if(verificarColor(manoJugador)) {
			manoJugadorOrdenado = ordenarPorNumero(manoJugador);
			for(int i = 0; i < manoJugadorOrdenado.size(); i++) {
				if(manoJugadorOrdenado.get(i).getValorNumerico() != aux) {
					return false;
				}else {
					aux++;
				}
			}
			return true;
		}else {
			return false;
		}
	}
	
	public boolean escaleraColor(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenado;
		if(verificarColor(manoJugador)) {
			manoJugadorOrdenado = ordenarPorNumero(manoJugador);
			int aux = manoJugadorOrdenado.get(0).getValorNumerico();
			for(int i = 0; i < manoJugadorOrdenado.size(); i++) {
				if(aux != manoJugadorOrdenado.get(i).getValorNumerico()) {
					return false;
				}else {
					aux++;
				}
			}
			return true;
		}
		
		return false;
	}
	
	public boolean poker(List<Carta> manoJugador) {
		int contador = 0;
		List<Carta> manoJugadorOrdenado;
		manoJugadorOrdenado = ordenarPorNumero(manoJugador);
		for(int i = 0; i < manoJugadorOrdenado.size(); i++) {
			if(manoJugadorOrdenado.get(i).getValorNumerico() == manoJugadorOrdenado.get(i+1).getValorNumerico()) {
				if(i < 3) {
					contador++;
				}
			}else {
				for(int j = 1; j < manoJugadorOrdenado.size()-1;j++) {
					if(manoJugadorOrdenado.get(j).getValorNumerico() == manoJugadorOrdenado.get(j+1).getValorNumerico()) {
						contador++;
					}
				}
				break;
			}
		}
		if(contador == 3) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	public boolean full(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenado;
		manoJugadorOrdenado = ordenarPorNumero(manoJugador);
		if(manoJugadorOrdenado.get(0).getValorNumerico() == manoJugadorOrdenado.get(1).getValorNumerico()) {
			if(manoJugadorOrdenado.get(2).getValorNumerico() == manoJugadorOrdenado.get(3).getValorNumerico() && manoJugadorOrdenado.get(3).getValorNumerico() == manoJugadorOrdenado.get(4).getValorNumerico()) {
				return true;
			}
		}else if (manoJugadorOrdenado.get(0).getValorNumerico() == manoJugadorOrdenado.get(1).getValorNumerico() && manoJugadorOrdenado.get(1).getValorNumerico() == manoJugadorOrdenado.get(2).getValorNumerico()) {
			if(manoJugadorOrdenado.get(3).getValorNumerico() == manoJugadorOrdenado.get(4).getValorNumerico()) {
				return true;
			}
		}
		return false;
	}
	
	
	
	public List<Carta> ordenarPorNumero(List<Carta> manoJugador) {
	        Carta temp;
	        for(int i=1;i < manoJugador.size();i++){
	            for (int j=0 ; j < manoJugador.size() - 1; j++){
	                if (manoJugador.get(j).getValorNumerico() > manoJugador.get(j+1).getValorNumerico()){
	                    temp = manoJugador.get(j);
	                    manoJugador.set(j, manoJugador.get(j+1));
	                    manoJugador.set(j+1,temp);
	                }
	            }
	        }
	        return manoJugador;
	}
	
	public boolean escalera(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenada;
		manoJugadorOrdenada = ordenarPorNumero(manoJugador);
		if(manoJugadorOrdenada.get(0).getValorNumerico() + 1 == manoJugadorOrdenada.get(1).getValorNumerico() ) {
			if(manoJugadorOrdenada.get(1).getValorNumerico() + 1 == manoJugadorOrdenada.get(2).getValorNumerico()) {
				if(manoJugadorOrdenada.get(2).getValorNumerico() + 1 == manoJugadorOrdenada.get(3).getValorNumerico()) {
					if(manoJugadorOrdenada.get(3).getValorNumerico() + 1 == manoJugadorOrdenada.get(4).getValorNumerico()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean trio(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenada;
		manoJugadorOrdenada = ordenarPorNumero(manoJugador);
		for(int i=0;i<3;i++) {
			if(manoJugadorOrdenada.get(i).getValorNumerico()==manoJugadorOrdenada.get(i+1).getValorNumerico() &&
					manoJugadorOrdenada.get(i+1).getValorNumerico() ==manoJugadorOrdenada.get(i+2).getValorNumerico()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean doblePareja(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenada;
		manoJugadorOrdenada = ordenarPorNumero(manoJugador);
		if(manoJugadorOrdenada.get(0).getValorNumerico() == manoJugadorOrdenada.get(1).getValorNumerico()
		   && manoJugadorOrdenada.get(3).getValorNumerico() == manoJugadorOrdenada.get(4).getValorNumerico() ) {
			return true;
		}else if(manoJugadorOrdenada.get(1).getValorNumerico() == manoJugadorOrdenada.get(2).getValorNumerico()
				&& manoJugadorOrdenada.get(3).getValorNumerico() == manoJugadorOrdenada.get(4).getValorNumerico()) {
			return true;
		}else if(manoJugadorOrdenada.get(0).getValorNumerico() == manoJugadorOrdenada.get(1).getValorNumerico() 
				&& manoJugadorOrdenada.get(2).getValorNumerico() == manoJugadorOrdenada.get(3).getValorNumerico()) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean pareja(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenada;
		manoJugadorOrdenada = ordenarPorNumero(manoJugador);
		for(int i = 0; i < 4 ; i++) {
			if(manoJugadorOrdenada.get(i).getValorNumerico() == manoJugadorOrdenada.get(i+1).getValorNumerico()) {
				return true;
			}
		}
		return false;
	}
	//Obtengo la carta de mayor numero
	public Carta cartaAlta(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenada;
		manoJugadorOrdenada = ordenarPorNumero(manoJugador);
		return manoJugadorOrdenada.get(4);
	}
	/*
	 * Atributo jugada de jugadorCPU
	 * 10 = Escalera real de color.
	 * 9 = Escalera de color
	 * 8 = Poker
	 * 7 = Full
	 * 6 = Color
	 * 5 = Escalera
	 * 4 = Trio
	 * 3 = Doble pareja
	 * 2 = Pareja
	 * 1 = Carta mas alta
	 */
	
	//Recibe una mano de jugador
	public int determinarJugada(List<Carta> manoJugador) {
		if(escaleraRealColor(manoJugador)) {
			return 10;
		} else if(escaleraColor(manoJugador)) {
			return 9;
		} else if(poker(manoJugador)) {
			return 8;
		}else if(full(manoJugador)) {
			return 7;
		} else if(verificarColor(manoJugador)) {
			return 6;
		}else if(escalera(manoJugador)) {
			return 5;
		} else if(trio(manoJugador)) {
			return 4;
		} else if(doblePareja(manoJugador)) {
			return 3;
		} else if(pareja(manoJugador)) {
			return 2;
		}else {
			return 1;
		}
	}
	/*
	public void verCartas() {
		System.out.println("Mano jugadores: ");
		for(int i = 0; i < manoJugadores.size(); i++) {
			System.out.println(manoJugadores.get(i));
		}
	}
	*/
	
	public String determinarGanador(List<Pair<Integer, List<Carta>>> parejaJugadasManos) {
		int contador = 1;
		int valor = 0;
		//No hay parejas
		if(!checkRepeatPlay(parejaJugadasManos)) {
			List<Carta> manoGanadora = parejaJugadasManos.get(4).getValue();
			System.out.println("NO HAY EMPATE");
			System.out.println("Mano Ganadora: ");
			
			for(int i = 0; i < manoGanadora.size(); i++) {
				System.out.println(manoGanadora.get(i));
			}
			
			for(int i = 0; i < manoJugadores.size(); i++) {
				for(int j = 0; j < manoJugadores.get(i).size(); j++) {
					if(manoJugadores.get(i).get(j).getValor() == manoGanadora.get(j).getValor() &&
					   manoJugadores.get(i).get(j).getValorNumerico() == manoGanadora.get(j).getValorNumerico()) 
					{
						contador++;
						if(contador == 5) {
							valor = i;
							break;
						}
					}
				}
			}
			System.out.println("Contador en comparar en no empate: "+contador);
			System.out.println("Valor en no empate: "+valor);
			return getNombreGanador(valor);
			
		}else {
			int aux;
			int comparacion;
			aux = cartaAlta(manosIguales.get(0)).getValorNumerico();
			for(int i = 1; i < manosIguales.size(); i++) {
				comparacion = cartaAlta(manosIguales.get(i)).getValorNumerico();
				if(aux <= comparacion) {
					aux = comparacion;
				}
			}
			return getNombreGanador(buscarCarta(aux));
		}
	}
	
	public int buscarCarta(int numero) {
		int valor = 0;
		int valorFinal = 0;
		for(int i = 0; i < manosIguales.size(); i++) {
			if(numero == cartaAlta(manosIguales.get(i)).getValorNumerico()) {
				valor = i;
			}
		}
		
		for(int i = 0; i < manoJugadores.size(); i++) {
			if(manosIguales.get(valor) == manoJugadores.get(i)) {
				valorFinal = i;
			}
		}
		
		return valorFinal;
	}
	public String getNombreGanador(int valor) {
		switch(valor) {
			case 0:
				return "Samuel";
			case 1:
				return "David";
			case 2:
				return "ElBicho";
			case 3:
				return "Valentina";
			case 4:
				return "Santiago";
			default:
				return "";
		
		}
	}
	
	public List<Pair<Integer, List<Carta>>> determinarParejas() {
		//La mano de cada jugador se empareja con una jugada respectiva
		List<Pair<Integer, List<Carta>>> parejaJugadasManos = new ArrayList<Pair<Integer, List<Carta>>>();
		List<Carta> manoJugadaOrdenada = new ArrayList<Carta>();
		
		for(int i = 0; i < 5; i++) {
			manoJugadaOrdenada = ordenarPorNumero(manoJugadores.get(i));
			parejaJugadasManos.add(new Pair<Integer, List<Carta>>(determinarJugada(manoJugadores.get(i)), manoJugadaOrdenada));
			
		}
		
		parejaJugadasManos = ordenarParejas(parejaJugadasManos);
		for(int i = 0; i < parejaJugadasManos.size(); i++) {
			System.out.println("Key: "+parejaJugadasManos.get(i).getKey());
			System.out.println("Value: "+parejaJugadasManos.get(i).getValue());
		}
		return parejaJugadasManos;
		
		
	}
	//Recibimos una parejaJugadasManos ordenados de menor a mayor
	public boolean checkRepeatPlay(List<Pair<Integer, List<Carta>>> parejaJugadasManos) {
		List<Integer> jugadas = new ArrayList<Integer>();
		for(int i = 0; i < 5; i++) {
			jugadas.add(parejaJugadasManos.get(i).getKey());
		}
		
		for(int i = 0; i < jugadas.size() - 1; i++) {
			if(jugadas.get(i) == jugadas.get(i+1) && Collections.max(jugadas) == jugadas.get(i+1)) {
				
				manosIguales.add(parejaJugadasManos.get(i).getValue());
				manosIguales.add(parejaJugadasManos.get(i+1).getValue());
			}
		}
		
		for(int i = 0; i < manosIguales.size(); i++) {
			System.out.println("MANOS IGUALES");
			System.out.println(manosIguales.get(i));
		}
		
		if(jugadas.get(4) == jugadas.get(3)) {
			return true;
		}
		
		return false;
	}
	//Ordenamos parejas de menor a mayor dependiendo de su jugada final
	public List<Pair<Integer, List<Carta>>> ordenarParejas(List<Pair<Integer, List<Carta>>> parejaJugadasManos){
	     	Pair<Integer, List<Carta>> temp;
	        for(int i=1;i < parejaJugadasManos.size();i++){
	            for (int j=0 ; j < parejaJugadasManos.size() - 1; j++){
	                if (parejaJugadasManos.get(j).getKey() > parejaJugadasManos.get(j+1).getKey()){
	                    temp = parejaJugadasManos.get(j);
	                    parejaJugadasManos.set(j, parejaJugadasManos.get(j+1));
	                    parejaJugadasManos.set(j+1,temp);
	                }
	            }
	        }
	        return parejaJugadasManos;
	}
}
