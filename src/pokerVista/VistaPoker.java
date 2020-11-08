package pokerVista;

import java.util.List;

import javax.swing.JFrame;

import pokerModelo.Carta;

public class VistaPoker extends JFrame {
	
	private MesaJuego mesaJuego;
	public VistaPoker(List<Boolean> isHuman,List<String> nombre, List<List<Carta>> manoJugador, List<Integer> dineroInicial) {
		initGUI(isHuman, nombre, manoJugador, dineroInicial);
		setVisible(true);
		setResizable(false);
		this.pack();
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	private void initGUI(List<Boolean> isHuman,List<String> nombre, List<List<Carta>> manoJugador, List<Integer> dineroInicial) {
		mesaJuego = new MesaJuego(isHuman, nombre, manoJugador, dineroInicial);
		add(mesaJuego);
	}
	
	public void actualizarVistaApuesta(int apuestaJugador, String nombre) {
		mesaJuego.actualizarMesaApuesta(apuestaJugador,nombre);
	}
	
}
