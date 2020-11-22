/*
 * Jennyfer Belalcazar 		- 1925639-3743
 * Samuel Riascos Prieto 	- 1922540-3743
 * Juan Camilo Randazzo		- 1923948-3743
 */
package pokerVista;

import java.util.List;

import javax.swing.JFrame;

import pokerControl.ControlPoker;
import pokerModelo.Carta;

// TODO: Auto-generated Javadoc
/**
 * The Class VistaPoker.
 * Lo que ve en pantalla el usuario
 */
public class VistaPoker extends JFrame {
	
	/** The mesa juego. */
	private MesaJuego mesaJuego;
	
	/** The control. */
	private ControlPoker control;
	
	/**
	 * Instantiates a new vista poker.
	 *
	 * @param isHuman the is human
	 * @param nombre the nombre
	 * @param manoJugador the mano jugador
	 * @param dineroInicial the dinero inicial
	 * @param control the control
	 */
	public VistaPoker(List<Boolean> isHuman,List<String> nombre, List<List<Carta>> manoJugador, List<Integer> dineroInicial, ControlPoker control) {
		this.control = control;
		mesaJuego = new MesaJuego(isHuman, nombre, manoJugador, dineroInicial, this.control);
		initGUI(isHuman, nombre, manoJugador, dineroInicial);
		setVisible(true);
		setResizable(false);
		this.pack();	
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Inits the GUI.
	 *
	 * @param isHuman the is human
	 * @param nombre the nombre
	 * @param manoJugador the mano jugador
	 * @param dineroInicial the dinero inicial
	 */
	private void initGUI(List<Boolean> isHuman,List<String> nombre, List<List<Carta>> manoJugador, List<Integer> dineroInicial) {
		add(mesaJuego);
	}
	
	/**
	 * Actualizar vista apuesta.
	 * ACtualiza los labels despues de apostar
	 * @param apuestaJugador the apuesta jugador
	 * @param nombre the nombre
	 * @param dineroInicial the dinero inicial
	 */
	public void actualizarVistaApuesta(int apuestaJugador, String nombre, String dineroInicial) {	
		mesaJuego.actualizarMesaApuesta(apuestaJugador,nombre, dineroInicial);
	}
	
	/**
	 * Actualizar area estado.
	 * Actualia el JTextArea que muestra el historial del juego
	 *
	 * @param apuestaJugador the apuesta jugador
	 * @param nombreJugador the nombre jugador
	 * @param mensaje the mensaje
	 */
	public void actualizarAreaEstado(int apuestaJugador,String nombreJugador, String mensaje) {
		mesaJuego.actualizarAreaEstado(apuestaJugador, nombreJugador, mensaje);
	}
	
	/**
	 * Actualizar vista carta despues de descartar
	 *
	 * @param nombre the nombre
	 * @param cartasNuevas the cartas nuevas
	 */
	public void actualizarVistaCartas(String nombre,List<Carta> cartasNuevas) {
		mesaJuego.actualizarMesaCartas(nombre, cartasNuevas);
	}
	
	/**
	 * Gets the mesa juego.
	 *
	 * @return the mesa juego
	 */
	public MesaJuego getMesaJuego() {
		return mesaJuego;
	}
	
	/**
	 * Dar apuesta usuario.
	 * Conocer la apuesta del usuario
	 * @return the int
	 */
	public int darApuestaUsuario() {
		return mesaJuego.getApuestaUsuario();
	}
}
