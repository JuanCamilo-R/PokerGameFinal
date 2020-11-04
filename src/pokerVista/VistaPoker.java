package pokerVista;

import java.util.List;

import javax.swing.JFrame;

import pokerModelo.Carta;

public class VistaPoker extends JFrame {
	
	private PanelJugador panelJugador;
	public VistaPoker(boolean isHuman, String nombre, List<Carta> manoJugador, int dineroInicial) {
		initGUI(isHuman, nombre, manoJugador, dineroInicial);
		setVisible(true);
		setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		
	}
	
	public void initGUI(boolean isHuman, String nombre, List<Carta> manoJugador, int dineroInicial) {
		panelJugador = new PanelJugador(isHuman, nombre, manoJugador, dineroInicial);
		add(panelJugador);
	}
	
}
