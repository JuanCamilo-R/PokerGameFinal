package pokerVista;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import pokerModelo.Carta;



public class PanelJugador extends JPanel {
	
	private List<Carta> mano = new ArrayList<Carta>();
	private JLabel nombre, mensaje, dineroInicial, dineroApostado;
	private JPanel panelMano;
	private boolean isHuman;
	private Escuchas escucha;
	private JButton confirmarApuesta, cederTurno, fichaDiez, fichaCinco, fichaCien;
	private ImageIcon imagen;
	public PanelJugador(boolean isHuman, String nombreJugador, List<Carta> cartas, int dineroInicial) {
		
		this.isHuman = isHuman;
		nombre.setText(nombreJugador);
		this.dineroInicial.setText(String.valueOf(dineroInicial));
		this.dineroApostado.setText("_____");
		mano = cartas;
		
		//Se agrega cada carta a la mano del jugador.
		for(Carta carta: cartas) {
			mano.add(carta);
		}
		
		panelMano = new JPanel();
		
		
		
		if(isHuman) {
			confirmarApuesta = new JButton();
			cederTurno = new JButton();
			fichaDiez = new JButton();
			fichaCinco = new JButton();
			fichaCien = new JButton();
			/*
			imagen = new ImageIcon(getClass().getResource("/recursos/"+indexImagen+".png"));
			//le doy la imagen al Label
			
			//configuraciones del label
			Border raised = BorderFactory.createRaisedBevelBorder();// crea el borde con esas caracteristicas
			Border lowered = BorderFactory.createRaisedSoftBevelBorder();
			//le defino a la clase este tipo de borde
			confirmar.setBorder(BorderFactory.createCompoundBorder(raised,lowered));
			*/
		}
		
	}
	
	private void refrescarMano () {
		panelMano.removeAll();
		if(mano!=null) {
			for(Carta carta : mano) {
				panelMano.add(carta);
			}
		}
	}
	
	
	private class Escuchas extends MouseAdapter {
		
	}
	
}
