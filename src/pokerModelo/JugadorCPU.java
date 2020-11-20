package pokerModelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pokerControl.ControlPoker;
import pokerVista.PanelJugador;

// TODO: Auto-generated Javadoc
/**
 * The Class JugadorCPU.
 */
public class JugadorCPU implements Runnable {
	
	/** The nombre jugador. */
	private String nombreJugador;
	
	/** The apuesta actual. */
	private int turno, dineroApuesta, dineroInicial, apuestaActual;
	
	/** The random. */
	private Random random;
	
	/** The control. */
	private ControlPoker control;
	
	/** The ronda. */
	private boolean ronda = false; //True = Ronda de descarte // False = Ronda de apuesta
	
	/** The cantidad A descartar. */
	private int cantidadADescartar;
	
	/** The cartas. */
	private List<Carta> cartas = new ArrayList<Carta>();
	
	/** The jugada. */
	private int jugada;
	
	/** The veces apuesta. */
	private int vecesApuesta = 0;
	
	/** The interrumpido. */
	private boolean interrumpido;
	
	/** The paso. */
	private int paso = 1;
	
	/** The contador. */
	private int contador;

	/**
	 * Instantiates a new jugador CPU.
	 *
	 * @param dineroInicial the dinero inicial
	 * @param nombreJugador the nombre jugador
	 * @param cantidadADescartar the cantidad A descartar
	 * @param control the control
	 */
	public JugadorCPU( int dineroInicial, String nombreJugador,int cantidadADescartar, ControlPoker control) {
		control.naceHilo();
		this.dineroInicial = dineroInicial;
		this.nombreJugador = nombreJugador;
		this.cantidadADescartar = cantidadADescartar;
		random = new Random();
		this.control = control;
		interrumpido=false;
		apostar(100);
	}

	/**
	 * Gets the apuesta actual.
	 *
	 * @return the apuesta actual
	 */
	public int getApuestaActual() {
		return apuestaActual;
	}
	
	/**
	 * Recibir cartas iniciales.
	 *
	 * @param cartasRecibidas the cartas recibidas
	 */
	public void recibirCartasIniciales(List<Carta> cartasRecibidas) {
		cartas = cartasRecibidas;
	}

	/**
	 * Descartar cartas.
	 */
	public void descartarCartas() {
		for(int i = 0; i < cantidadADescartar; i++) {
			cartas.remove(0);
		}	
	}

	/**
	 * Recibir cartas.
	 *
	 * @param cartaRecibida the carta recibida
	 */
	public void recibirCartas(Carta cartaRecibida) {
		cartas.add(cartaRecibida);
	}

	/**
	 * Gets the cantidad A descartar.
	 *
	 * @return the cantidad A descartar
	 */
	public int getCantidadADescartar() {
		return cantidadADescartar;
	}

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombreJugador;
	}
	
	/**
	 * Gets the dinero inicial.
	 *
	 * @return the dinero inicial
	 */
	public int getDineroInicial() {
		return dineroInicial;
	}

	/**
	 * Gets the cartas.
	 *
	 * @return the cartas
	 */
	public List<Carta>  getCartas (){
		return cartas;
	}

	/**
	 * Sets the turno.
	 *
	 * @param turno the new turno
	 */
	public void setTurno(int turno) {
		this.turno = turno;
	}

	/**
	 * Gets the turno.
	 *
	 * @return the turno
	 */
	public int getTurno() {
		return turno;
	}

	/**
	 * Gets the veces apostado.
	 *
	 * @return the veces apostado
	 */
	public int getVecesApostado() {
		return vecesApuesta;
	}

	/**
	 * Apostar.
	 *
	 * @param cantidad the cantidad
	 * @return true, if successful
	 */
	public boolean apostar(int cantidad) {
		if(dineroInicial>= cantidad) {
			vecesApuesta++;
			apuestaActual = cantidad;
			dineroApuesta=cantidad;
			dineroInicial -=cantidad;
			return true;
		}
		return false;
	}

	/**
	 * Devolver apuesta.
	 *
	 * @param cantidadDevuelta the cantidad devuelta
	 */
	public void devolverApuesta(int cantidadDevuelta) {
		dineroInicial += cantidadDevuelta;
	}

	/**
	 * Interrumpir.
	 */
	public void interrumpir() {
		interrumpido = true;
	}

	/**
	 * Gets the interrumpido.
	 *
	 * @return the interrumpido
	 */
	public boolean getInterrumpido() {
		return interrumpido;
	}
	
	/**
	 * Iniciar ronda descarte.
	 */
	public void iniciarRondaDescarte() {
		//Reinicio el paso para que el hilo pueda entrar al switch con el caso respectivo.
		paso = 2;
	}

	/**
	 * Reiniciar apuesta.
	 */
	public void reiniciarApuesta() {
		apuestaActual=100;
	}

	/**
	 * Iniciar ronda apuesta.
	 */
	public void iniciarRondaApuesta() {
		//Reinicio el paso para que el hilo pueda entrar al switch con el caso respectivo.
		paso = 1;
		apuestaActual = 100;
	}
	
	/**
	 * Run.
	 * Funcion que se ejecuta en el ExecutorService.
	 * Cuesta de un while que se detiene cuando el hilo ha sido interrumpido.
	 * Posee un switch de dos casos, apuesta y descarte respectivamente.
	 * 
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(control.getControlador() <= 2 && !interrumpido) {
			switch(paso) {
			//Apuesta
			case 1:
				control.turnos(turno, nombreJugador, apuestaActual,getDineroInicial());	
				/*
				 * se cambia el paso para que no vuelva a entrar al switch y solo
				 * quede en el while hasta que sea su turno en la proxima ronda.
				 */
				paso = 0;
				//Si el hilo tiene el turno 5, es el encargado de activar la ronda siguiente.
				if(turno == 5 ) {
					control.activarRondaDescarte();
				}
				break;
				//Descarte
			case 2:				
				control.turnos(turno, nombreJugador, cantidadADescartar, getDineroInicial());	
				paso = 0;
				if(turno == 5) {
					control.activarRondaApuestas();
				}
				break;
			}
			if(interrumpido) {
				break;
			}
		}
		control.muereHilo();

	}
}
