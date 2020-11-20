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

// TODO: Auto-generated Javadoc
/**
 * The Class ControlPoker.
 */
public class ControlPoker {
	
	/** The random. */
	private Random random;
	
	
	/** The turno actual. */
	private int turnoActual = 1;
	
	/** The apuesta usuario. */
	private int apuestaUsuario;
	
	/** The baraja. */
	private Baraja baraja;
	
	/** The mano jugadores. */
	private List<List<Carta>> manoJugadores;
	
	/** The nombres. */
	private List<String> nombres;
	
	/** The vista. */
	private VistaPoker vista;
	
	/** The mesa juego. */
	private MesaJuego mesaJuego;
	
	/** The panel usuario. */
	private PanelJugador panelUsuario;
	
	/** The jugador 5. */
	private JugadorCPU jugador1,jugador2,jugador4,jugador5;
	
	/** The tipo jugador. */
	private List<Boolean> tipoJugador;
	
	/** The dinero. */
	private List<Integer> dinero;
	
	/** The descarte. */
	private int[] descarte;
	
	/** The ronda. */
	private int ronda = 0;
	
	/** The dormir. */
	private boolean dormir = false;
	
	/** The bloqueo. */
	private Lock bloqueo = new ReentrantLock(); //manejo de sincronizacion
	
	/** The esperar turno apuesta. */
	private Condition esperarTurnoApuesta = bloqueo.newCondition(); //manejo de sincronizacion
	
	/** The esperar turno descarte. */
	private Condition esperarTurnoDescarte = bloqueo.newCondition();
	
	/** The tipo ronda. */
	private boolean tipoRonda = true; //True si es el momento de apostar y false si es el momento de descartar
	
	/** The jugadores CPU. */
	private List<JugadorCPU> jugadoresCPU;
	
	/** The manos iguales. */
	List<List<Carta>> manosIguales;
	
	/** The posicion descarte. */
	private int posicionDescarte;
	
	/** The controlador. */
	private int controlador = 0;
	
	/** The posicion empate. */
	private int posicionEmpate;
	
	/** The asteriscos abiertos. */
	private boolean asteriscosAbiertos=false;
	
	/** The hilos corriendo. */
	private int hilosCorriendo=0;;
	
	/** The interrumpiendo. */
	private boolean interrumpiendo=false;
	
	/**
	 * Instantiates a new control poker.
	 */
	public ControlPoker() {
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
	
	/**
	 * Iniciar juego.
	 */
	private void iniciarJuego() {
		baraja = new Baraja();
		for(int i = 0; i < 5 ;i++) {
			manoJugadores.add(seleccionarCartas());
		}
		
		
		definirJugadoresCPU();
		darTipo();
		agregarNombres();
		darDinero();
		
		
	}
	
	/**
	 * Agregar nombres.
	 */
	private void agregarNombres() {
			
			
			nombres.add(jugador1.getNombre());
			nombres.add(jugador2.getNombre());
			nombres.add("ElBicho");
			nombres.add(jugador4.getNombre());
			nombres.add(jugador5.getNombre());
	
		
	}
	
	/**
	 * Dar tipo.
	 */
	private void darTipo() {
		
			tipoJugador.add(false);
			tipoJugador.add(false);
			tipoJugador.add(true);
			tipoJugador.add(false);
			tipoJugador.add(false);
		
		
	}
	
	/**
	 * Dar dinero.
	 */
	private void darDinero() {
		


		
			dinero.add(jugador1.getDineroInicial());
			dinero.add(jugador2.getDineroInicial());
			dinero.add(15000);
			dinero.add(jugador5.getDineroInicial());
			dinero.add(jugador4.getDineroInicial());
	
		
	}
	
	
	
	/**
	 * Seleccionar cartas.
	 *
	 * @return the array list
	 */
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
	
	/**
	 * Reiniciar juego.
	 */
	public void reiniciarJuego() {
		PanelJugador.apuestaMinima=100;
		tipoRonda = true;
		controlador = 0;
		turnoActual = 1;
		for(int i = 0; i < 5; i++) {
			manoJugadores.get(i).clear();
		}
		jugador1 = null;
		jugador2 = null;
		jugador4 = null;
		jugador5 = null;
		manoJugadores.clear();
		jugadoresCPU.clear();
		manosIguales.clear();
		dinero.clear();
		nombres.clear();
		tipoJugador.clear();
		iniciarJuego();
		mesaJuego.reiniciarJuego(manoJugadores,dinero);
		iniciarJugadoresCPU();
		reiniciarApuesta();
		
	}
	
	
	/**
	 * Definir jugadores CPU.
	 */
	public void definirJugadoresCPU() {
		/*JugadorCPU */jugador1 = new JugadorCPU(1500, "Samuel", 1, this);
		/*JugadorCPU */jugador2 = new JugadorCPU(1500, "David", 2, this);
		/*JugadorCPU */jugador4 = new JugadorCPU(1500, "Valentina", 3, this);
		/*JugadorCPU */jugador5 = new JugadorCPU(1500, "Santiago", 4, this);
	}
	
	/**
	 * Iniciar jugadores CPU.
	 */
	public void iniciarJugadoresCPU() {
		
		int aux = random.nextInt(5)+1;
		asteriscosAbiertos=true;
		mesaJuego.espaciar();
		mesaJuego.mensaje("Orden de turnos:");

		switch(5) {
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
		asteriscosAbiertos=false;
	      ExecutorService ejecutorSubprocesos = Executors.newCachedThreadPool();
		  ejecutorSubprocesos.execute(jugador1); 
		  ejecutorSubprocesos.execute(jugador2);
		  ejecutorSubprocesos.execute(jugador4);
		  ejecutorSubprocesos.execute(jugador5);
		  
		  ejecutorSubprocesos.shutdown();
		  
		  
	}
	
	/**
	 * Gets the turno.
	 *
	 * @return the turno
	 */
	public int getTurno() {
		return turnoActual;
	}
	
	/**
	 * Activar ronda descarte.
	 */
	/*
	 * Activa la ronda de descartes, llamada desde el jugador con turno 5
	 */
	public void activarRondaDescarte() {
		//Se verifica si no ha sido interrumpido el juego.
		if(!interrumpiendo) {
			turnoActual = 1;
			if(controlador <= 3) {
				setControlador();	
			}

			
			if(controlador == 3) {
				System.out.println("Entro a controlador = 3");
				System.out.println("El ganador es : "+determinarGanador(determinarParejas()));
			}
			
			tipoRonda = false; //Tipo ronda descarte.
			//Iniciamos a los hilos.
			for(int i=0;i<jugadoresCPU.size();i++) {
				jugadoresCPU.get(i).iniciarRondaDescarte();
			}
		}
	}
	
	/**
	 * Sets the controlador.
	 */
	public void setControlador() {
		controlador++;
	}
	
	/**
	 * Gets the controlador.
	 *
	 * @return the controlador
	 */
	public int getControlador() {
		return controlador;
	}
	
	
	/**
	 * Activar ronda apuestas.
	 */
	public void activarRondaApuestas() {
		turnoActual = 1;
		if(!interrumpiendo) {
			tipoRonda = true;
			if(controlador <= 3) {
				setControlador();
			}

			
			for(int i = 0; i < jugadoresCPU.size(); i++) {
				jugadoresCPU.get(i).iniciarRondaApuesta();
			}
		}
	}
	
	/**
	 * Turnos.
	 *
	 * @param turnoJugador the turno jugador
	 * @param nombreJugador the nombre jugador
	 * @param dato the dato
	 * @param dineroInicial the dinero inicial
	 */
	/*
	 * Funcion sincronizada accedida por los hilos y el jugador humano. "ElBicho" 
	 * 
	 */
	public void turnos(int turnoJugador, String nombreJugador, int dato, int dineroInicial) {
		bloqueo.lock();

		
		
		
		try
		{
			if(!asteriscosAbiertos) {
				mesaJuego.espaciar();
				asteriscosAbiertos=true;
			}
			
			if(tipoRonda) { //Ronda de apuesta
				/*
				 * getSiguienteTurno: boolean que decide si ya puede tirar el siguiente jugador despues
				 * del bicho (jugador humano)
				 */
				
				while(turnoJugador != turnoActual && !panelUsuario.getSiguienteTurno()) {
					if(interrumpiendo) {
						break;
					}
					esperarTurnoApuesta.await();
				}
				
				
				if(turnoJugador <= 5 && setApuestaJugador(nombreJugador,dato)) {
					for(int i=0;i<jugadoresCPU.size();i++) {
						if(jugadoresCPU.get(i).getNombre()==nombreJugador) {
							//Actualiza las apuestas
							vista.actualizarVistaApuesta(dato, nombreJugador, String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
						}
					}
					if(!interrumpiendo)
					verificarApuesta(nombreJugador);
					
					
				}
				else if(nombreJugador.equals("ElBicho") && !interrumpiendo) {
					//Actualiza la apuesta del Bicho
					vista.actualizarAreaEstado(panelUsuario.getApuestaUsuario(), nombreJugador, " apuesta: ");
				}

				
				if(turnoJugador <= 5) {
					
					turnoActual++;
					
				}
				
				esperarTurnoApuesta.signalAll();
			}else {
				while(turnoJugador != turnoActual && !panelUsuario.getSiguienteTurno()) {
					if(interrumpiendo) {
						break;
					}
					
					esperarTurnoDescarte.await();
				}
				
				if(turnoJugador <= 5) {
					
					 posicionDescarte = turnoActual - 1;
					 //Si el juego no ha sido interrumpido se le permite al jugador descartar.
					 if(!interrumpiendo) {
					 descartar(dato, nombreJugador,turnoJugador);
					 }
					 
					 turnoActual++;
				}
				
				if(nombreJugador.equals("ElBicho") && !interrumpiendo) {
					if(panelUsuario.getContadorCartasPedidas()!=1) {
						mesaJuego.mensaje(nombreJugador+" descarto "+panelUsuario.getContadorCartasPedidas()+" cartas");
					}
					else {
						mesaJuego.mensaje(nombreJugador+" descarto "+panelUsuario.getContadorCartasPedidas()+" carta");
					}
				}
				
				esperarTurnoDescarte.signalAll();
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally {
			if(interrumpiendo) {
				//Reinicia turnos al final.
				if(turnoActual==6) {
					turnoActual=1;
				}
			}
			
			if(turnoActual == 6 && !interrumpiendo) {
				if(tipoRonda) {
					//Verifica las apuestas de los jugadores antes del Bicho.
					verificarApuestasFinal(nombreJugador);
				}
				mesaJuego.espaciar();
				asteriscosAbiertos=false;
			}
			bloqueo.unlock();
			panelUsuario.setSiguienteTurno(false);
			}
		}
	
	/**
	 * Descartar.
	 *
	 * @param dato the dato
	 * @param nombreJugador the nombre jugador
	 * @param turnoJugador the turno jugador
	 */
	public void descartar(int dato, String nombreJugador, int turnoJugador) {
		for(int i = 0; i < jugadoresCPU.size(); i++) {
			if(jugadoresCPU.get(i).getTurno() == turnoActual)
			{
				descarte[posicionDescarte] = dato;
				
				jugadoresCPU.get(i).descartarCartas();				
				darCartas(jugadoresCPU.get(i).getCantidadADescartar(),i);
				vista.actualizarVistaCartas( jugadoresCPU.get(i).getNombre(),jugadoresCPU.get(i).getCartas());
				//If para el plural del sustantivo "cartas"
				if(jugadoresCPU.get(i).getCantidadADescartar()!=1) {
					mesaJuego.mensaje(nombreJugador+" descartó "+jugadoresCPU.get(i).getCantidadADescartar()+" cartas");
				}
				else {
					mesaJuego.mensaje(nombreJugador+" descartó "+jugadoresCPU.get(i).getCantidadADescartar()+" carta");
				}
				
				
				
			}
		}
	}
	
	/**
	 * Interrumpir hilos.
	 */
	public void interrumpirHilos() {
		interrumpiendo=true;
		controlador=3;
		System.out.println("HE INTERRUMPIDO A TODOS LOS HILOS");
		
		jugador1.interrumpir();
		jugador2.interrumpir();
		jugador4.interrumpir();
		jugador5.interrumpir();
		//Si hay hilos corriendo se despiertan para detener su ejecucion.
		while(hilosCorriendo>0) {
			if(turnoActual==panelUsuario.getTurno()) {
				//Despierto a los hilos.
				panelUsuario.setSiguienteTurno(true);
				turnoActual++;
				turnos(100, "ElBicho", 0, 100);
			}
			if(hilosCorriendo==0) {
				break;
			}
		}
		interrumpiendo=false;
	}
	
	/**
	 * Dar cartas.
	 *
	 * @param cantidad the cantidad
	 * @param i the i
	 */
	public void darCartas(int cantidad,int i) {	
        
		List<Carta> cartas2 = new ArrayList<Carta>();
		
		for(int j=0;j<cantidad;j++) {
			cartas2.add(baraja.getCarta());
		}
		asignarCartas(cartas2,i,cantidad);
				
	}
	
	/**
	 * Gets the tipo ronda.
	 *
	 * @return the tipo ronda
	 */
	public boolean getTipoRonda() {
		return tipoRonda;
	}
	
	
	/**
	 * Reiniciar apuesta.
	 * Reinicia las apuestas de los jugadores al empezar ronda de Descartes
	 */
	public void reiniciarApuesta() {
		
		for(int i = 0; i < jugadoresCPU.size(); i++) {
			jugadoresCPU.get(i).reiniciarApuesta();
			if(jugadoresCPU.get(i).getTurno() < panelUsuario.getTurno()) {
				vista.actualizarVistaApuesta(jugadoresCPU.get(i).getApuestaActual(), jugadoresCPU.get(i).getNombre(),
						String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
			}else {
				
				vista.actualizarVistaApuesta(0, jugadoresCPU.get(i).getNombre(),
						String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
			}
			
		}
	}
	
	/**
	 * Asignar cartas.
	 *
	 * @param cartas the cartas
	 * @param i the i
	 * @param cantidad the cantidad
	 */
	public void asignarCartas(List<Carta> cartas,int i,int cantidad) {
		for(int j=0;j<cantidad;j++) {
			jugadoresCPU.get(i).recibirCartas(cartas.get(j));
		}

	}
	
	/**
	 * Verificar apuesta.
	 *
	 * @param nombreJugador the nombre jugador
	 */
	public synchronized void verificarApuesta(String nombreJugador) {
		for(int i = 0; i < jugadoresCPU.size(); i++) {
			if(jugadoresCPU.get(i).getNombre() == nombreJugador) {
				
				if(jugadoresCPU.get(i).getApuestaActual() != panelUsuario.getApuestaUsuario()) {
					
					int apuestaCPU = jugadoresCPU.get(i).getApuestaActual();
					int apuestaUsuario = PanelJugador.apuestaMinima; 
					/*
					 * Dado que no fue suficiente la apuesta del jugador, se le devuelve la apuesta
					 * Y asi, que de el monto completo de una vez.
					 */
					
					jugadoresCPU.get(i).devolverApuesta(jugadoresCPU.get(i).getApuestaActual());
					
					//Si puede apostar, se actualiza el JTextArea y los labels.
					if(jugadoresCPU.get(i).apostar(apuestaUsuario)) {
						if(jugadoresCPU.get(i).getTurno()<panelUsuario.getTurno() && jugadoresCPU.get(i).getApuestaActual()!=PanelJugador.apuestaMinima) {
							vista.actualizarVistaApuesta(apuestaUsuario, nombreJugador, String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
							vista.actualizarAreaEstado(apuestaUsuario, nombreJugador, " apuesta para igualar, total apostado en la ronda: ");
						}else {
							vista.actualizarAreaEstado(apuestaUsuario, nombreJugador, " apuesta: ");
							vista.actualizarVistaApuesta(apuestaUsuario, nombreJugador, String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
						}
						
			
					}else {
						vista.actualizarVistaApuesta(0, jugadoresCPU.get(i).getNombre(), String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
					}
				}
				else {
					vista.actualizarAreaEstado(jugadoresCPU.get(i).getApuestaActual(), nombreJugador, " apuesta: ");
				}
			}
			
		}
	}
	
	/**
	 * Verificar apuestas final.
	 * Verifica las apuestas de los jugadores antes del Bicho.
	 *
	 * @param nombre the nombre
	 */
	public  synchronized void verificarApuestasFinal(String nombre) {
		dormir = false;
		for(int i = 0; i < jugadoresCPU.size(); i++) {
			if(jugadoresCPU.get(i).getApuestaActual() != panelUsuario.getApuestaUsuario()) {
				int apuestaUsuario = PanelJugador.apuestaMinima;
				int apuestaCPU = jugadoresCPU.get(i).getApuestaActual();
				//Se le devuelve su apuesta inicial al jugador
				jugadoresCPU.get(i).devolverApuesta(jugadoresCPU.get(i).getApuestaActual());
				
				//Y apuesta el monto del Bicho, si lo hace se actualiza la vista.
				if(jugadoresCPU.get(i).apostar(apuestaUsuario)){
					vista.actualizarAreaEstado(apuestaUsuario, jugadoresCPU.get(i).getNombre(), " apuesta al final para igualar, total apostado en la ronda: ");
					vista.actualizarVistaApuesta(apuestaUsuario, jugadoresCPU.get(i).getNombre(), String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
			
				}else {
					vista.actualizarVistaApuesta(0, jugadoresCPU.get(i).getNombre(), String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
					
				
				}
			}
		}
	}
	
	/**
	 * Sets the apuesta jugador.
	 * Apuesta para el jugador CPU.
	 * @param nombreJugador the nombre jugador
	 * @param cantidadApuesta the cantidad apuesta
	 * @return true, if successful
	 */
	public synchronized boolean setApuestaJugador(String nombreJugador, int cantidadApuesta) {
		for(int i = 0; i < jugadoresCPU.size(); i++) {
			if(jugadoresCPU.get(i).getNombre() == nombreJugador) {
				if(jugadoresCPU.get(i).apostar(cantidadApuesta)) {
					//Se verifica si la cantidad que apuesta la CPU es mayor que la apuesta minima
					if(cantidadApuesta>PanelJugador.apuestaMinima) {
						PanelJugador.apuestaMinima=cantidadApuesta;
						
					}
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Sets the turno actual.
	 */
	public void setTurnoActual() {
		turnoActual++;
	}
	
	/**
	 * Dar cartas iniciales.
	 *
	 * @param jugador the jugador
	 */
	//Dar cartas iniciales (Se llama en la ronda 1) No se usa para descartar cartas
	public void darCartasIniciales(JugadorCPU jugador) {
		ArrayList<Carta> cartasADar = new ArrayList<Carta>();
		for(int i = 0; i < 5; i++) {
			cartasADar.add(baraja.getCarta());
		}
		jugador.recibirCartasIniciales(cartasADar);
	}
	
	/**
	 * Descartar cartas.
	 *
	 * @param jugador the jugador
	 */
	//Descarta y se le da cartas al instante
	public void descartarCartas(JugadorCPU jugador) {
		//Damos la cantidad de cartas que el jugador descarto
		for(int i = 0; i < jugador.getCantidadADescartar(); i++) {
			jugador.recibirCartas(baraja.getCarta());
		}
	}
	
	/**
	 * Descarte humano.
	 * 
	 * @param cartaEliminada the carta eliminada
	 */
	public void descarteHumano(Carta cartaEliminada) {
		
		for(int i = 0; i < manoJugadores.get(2).size(); i++) {
			if(cartaEliminada == manoJugadores.get(2).get(i)) {
				manoJugadores.get(2).remove(i);
			}
		}
	}
	
	/**
	 * Agregar carta para el humano.
	 *
	 * @param nuevasCartas the nuevas cartas
	 */
	public void agregarCarta(List<Carta> nuevasCartas) {
		
		
		for(int i = 0; i < nuevasCartas.size(); i++) {
			manoJugadores.get(2).add(nuevasCartas.get(i));
		}
		
		panelUsuario.recibirCartasHumano(manoJugadores.get(2));
	}
	
	

	
	/**
	 * Dar cartas humanos.
	 *
	 * @param contadorCartas the contador cartas
	 */
	public void darCartasHumanos(int contadorCartas) {
		List<Carta> cartasNuevasHumano = new ArrayList<Carta>();
		for(int i=0;i<contadorCartas;i++) {
			cartasNuevasHumano.add(baraja.getCarta());
		}
		
		agregarCarta(cartasNuevasHumano);
	}
	
	/**
	 * Obtener apuesta usuario.
	 *
	 * @param jugadorUsuario the jugador usuario
	 */
	public void obtenerApuestaUsuario(PanelJugador jugadorUsuario) {
		apuestaUsuario = vista.darApuestaUsuario();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 EventQueue.invokeLater(new Runnable() {
	        	public void run() {
	        		new ControlPoker();
	        		
	        	}
	        });
	}
	
	/**
	 * Verificar color.
	 *
	 * @param manoJugador the mano jugador
	 * @return true, if successful
	 */
	//Verificar que todas las cartas tengan el mismo color
	public boolean verificarColor(List<Carta> manoJugador) { //True si todas tienen el mismo color, false si no
		for(int i = 0; i < manoJugador.size()-1; i++) {
			if(manoJugador.get(i).getPalo() != manoJugador.get(i+1).getPalo()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Escalera real color.
	 * Cinco cartas consecutivas del mismo palo empezando por la carta #10
	 * @param manoJugador the mano jugador
	 * @return true, if successful
	 */
	
	public boolean escaleraRealColor(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenado;
		int aux = 10;
		//Verifica que todas las cartas tenga el mismo color (palo)
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
	
	/**
	 * Escalera color.
	 * Cinco cartas consecutivas del mismo palo (no importa desde que numero empiece)
	 * @param manoJugador the mano jugador
	 * @return true, if successful
	 */
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
	
	/**
	 * Poker.
	 *	Cuatro cartas iguales (numero) y una diferente.
	 * @param manoJugador the mano jugador
	 * @return true, if successful
	 */
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
	
	
	/**
	 * Full, tres cartas del mismo valor y dos cartas de un valor distinto.
	 *
	 * @param manoJugador the mano jugador
	 * @return true, if successful
	 */
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
	
	
	
	/**
	 * Ordenar por numero, metodo burbuja.
	 *
	 * @param manoJugador the mano jugador
	 * @return the list
	 */
	public List<Carta> ordenarPorNumero(List<Carta> manoJugador) {
	        //Variable auxiliar.
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
	
	/**
	 * Escalera, cinco cartas con valor numerico consecutivas.
	 *
	 * @param manoJugador the mano jugador
	 * @return true, if successful
	 */
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
	
	/**
	 * Trio, tres cartas del mismo valor y dos de un valor diferente.
	 *
	 * @param manoJugador the mano jugador
	 * @return true, if successful
	 */
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
	
	/**
	 * Doble pareja.
	 *
	 * @param manoJugador the mano jugador
	 * @return true, if successful
	 */
	public boolean doblePareja(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenada;
		manoJugadorOrdenada = ordenarPorNumero(manoJugador);
		//Se evaluan los casos donde podrian estar en la baraja
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
	
	/**
	 * Pareja.
	 *
	 * @param manoJugador the mano jugador
	 * @return true, if successful
	 */
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
	
	/**
	 * Carta alta.
	 *
	 * @param manoJugador the mano jugador
	 * @return the carta
	 */
	//Obtengo la carta de mayor numero
	public Carta cartaAlta(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenada;
		manoJugadorOrdenada = ordenarPorNumero(manoJugador);
		return manoJugadorOrdenada.get(4);
	}
	/**
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
	
	/**
	 * Determinar jugada.
	 *
	 * @param manoJugador the mano jugador
	 * @return the int
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
	
	/**
	 * Determinar ganador.
	 *
	 * @param parejaJugadasManos the pareja jugadas manos
	 * @return the string
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
						//Obtiene el indice del i, para saber quien gano.
						if(contador == 5) {
							valor = i;
							break;
						}
					}
				}
			}
			return getNombreGanador(valor);
			
		}else {
			int aux;
			int comparacion;
			//Obtengo la carta alta de la primera mano de manosIguales.
			aux = cartaAlta(manosIguales.get(0)).getValorNumerico();
			for(int i = 1; i < manosIguales.size(); i++) {
				comparacion = cartaAlta(manosIguales.get(i)).getValorNumerico();
				//Condicion para ir cambiando la comparacion.
				if(aux <= comparacion) {
					aux = comparacion;
				}
			}
			return getNombreGanador(buscarCarta(aux));
		}
	}
	
	/**
	 * Buscar carta.
	 *
	 * @param numero the numero
	 * @return the int
	 */
	public int buscarCarta(int numero) {
		int valor = 0;
		int valorFinal = 0;
		for(int i = 0; i < manosIguales.size(); i++) {
			//Busca en que baraja esta la carta mas alta.
			if(numero == cartaAlta(manosIguales.get(i)).getValorNumerico()) {
				valor = i;
			}
		}
		
		for(int i = 0; i < manoJugadores.size(); i++) {
			//Busca en que baraja de manosJugadores esta la baraja ganadora de manosIguales
			if(manosIguales.get(valor) == manoJugadores.get(i)) {
				valorFinal = i;
			}
		}
		
		return valorFinal;
	}
	
	/**
	 * Gets the nombre ganador.
	 *
	 * @param valor the valor
	 * @return the nombre ganador
	 */
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
	
	/**
	 * Determinar parejas.
	 *  
	 * @return the list
	 */
	public List<Pair<Integer, List<Carta>>> determinarParejas() {
		//La mano de cada jugador se empareja con una jugada respectiva
		List<Pair<Integer, List<Carta>>> parejaJugadasManos = new ArrayList<Pair<Integer, List<Carta>>>();
		List<Carta> manoJugadaOrdenada = new ArrayList<Carta>();
		
		for(int i = 0; i < 5; i++) {
			manoJugadaOrdenada = ordenarPorNumero(manoJugadores.get(i));
			parejaJugadasManos.add(new Pair<Integer, List<Carta>>(determinarJugada(manoJugadores.get(i)), manoJugadaOrdenada));
			
		}
		//Se ordena las parejas por key. (menor a mayor)
		parejaJugadasManos = ordenarParejas(parejaJugadasManos);
		for(int i = 0; i < parejaJugadasManos.size(); i++) {
			System.out.println("Key: "+parejaJugadasManos.get(i).getKey());
			System.out.println("Value: "+parejaJugadasManos.get(i).getValue());
		}
		return parejaJugadasManos;
		
		
	}
	
	/**
	 * Check repeat play.
	 *
	 * @param parejaJugadasManos the pareja jugadas manos
	 * @return true, if successful
	 */
	//Recibimos una parejaJugadasManos ordenados de menor a mayor
	public boolean checkRepeatPlay(List<Pair<Integer, List<Carta>>> parejaJugadasManos) {
		List<Integer> jugadas = new ArrayList<Integer>();
		//Agregamos el key de las parejas a jugadas
		for(int i = 0; i < 5; i++) {
			jugadas.add(parejaJugadasManos.get(i).getKey());
		}
			//Anado las barajas iguales a manosIguales
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
		//Si hay jugadas iguales (las mas altas) retorna verdadero.
		if(jugadas.get(4) == jugadas.get(3)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Ordenar parejas.
	 * Ordenamos parejas de menor a mayor dependiendo de su jugada final. (Key)
	 * Metodo burbuja
	 * @param parejaJugadasManos the pareja jugadas manos
	 * @return the list
	 */
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
	
	/**
	 * Nace hilo.
	 */
	public synchronized void naceHilo() {
		hilosCorriendo++;
	}
	
	/**
	 * Muere hilo.
	 */
	public synchronized void muereHilo() {
		hilosCorriendo--;
		System.out.println("Hilos actuales: "+hilosCorriendo);
	}
}
