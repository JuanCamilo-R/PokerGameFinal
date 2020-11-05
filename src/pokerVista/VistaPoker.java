package pokerVista;

import java.util.List;

import javax.swing.JFrame;

import pokerModelo.Carta;

public class VistaPoker extends JFrame {
	
	private MesaJuego mesaJuego;
	public VistaPoker(boolean isHuman, String nombre, List<List<Carta>> manoJugador, int dineroInicial) {
		initGUI(isHuman, nombre, manoJugador, dineroInicial);
		setVisible(true);
		setResizable(false);
		this.pack();
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void initGUI(boolean isHuman, String nombre, List<List<Carta>> manoJugador, int dineroInicial) {
		mesaJuego = new MesaJuego(isHuman, nombre, manoJugador, dineroInicial);
		add(mesaJuego);
	}
	
}
