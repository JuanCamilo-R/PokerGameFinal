package pokerVista;

import java.util.List;

import javax.swing.JFrame;

import pokerControl.ControlPoker;
import pokerModelo.Carta;

public class VistaPoker extends JFrame {
	
	private MesaJuego mesaJuego;
	private ControlPoker control;
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
	
	private void initGUI(List<Boolean> isHuman,List<String> nombre, List<List<Carta>> manoJugador, List<Integer> dineroInicial) {
		add(mesaJuego);
	}
	
	public void actualizarVistaApuesta(int apuestaJugador, String nombre) {
		mesaJuego.actualizarMesaApuesta(apuestaJugador,nombre);
	}
	
	public void funcionPrueba() {
		System.out.println("Entro a funcion prueba");
	}
	public MesaJuego getMesaJuego() {
		return mesaJuego;
	}
	
	public int darApuestaUsuario() {
		return mesaJuego.getApuestaUsuario();
	}
}
