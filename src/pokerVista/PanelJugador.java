package pokerVista;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
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
	private GridBagConstraints constraints;
	public PanelJugador(boolean isHuman, String nombreJugador, List<Carta> cartas, int dineroInicial) {
		nombre = new JLabel(nombreJugador);
		this.isHuman = isHuman;
		this.dineroInicial = new JLabel(String.valueOf(dineroInicial));
		dineroApostado = new JLabel("_____");
		
		//Se agrega cada carta a la mano del jugador.
		
		for(Carta carta: cartas) {
			mano.add(carta);
		}
		
		panelMano = new JPanel();
		this.setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 3;
		constraints.anchor = GridBagConstraints.CENTER;
		add(nombre, constraints);
		
		refrescarMano();
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		add(panelMano, constraints);
		
		if(isHuman) {
			confirmarApuesta = new JButton();
			cederTurno = new JButton();
			imagen = new ImageIcon(getClass().getResource("/resources/ficha1.png"));
			fichaCinco = new JButton();
			fichaCinco.setBorder(null);
			fichaCinco.setContentAreaFilled(false);
			fichaCinco.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
			//imagenPrincipal.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
			fichaDiez = new JButton();
			fichaCien = new JButton();
			
			constraints.gridx = 2;
			constraints.gridy = 1;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.anchor = GridBagConstraints.CENTER;
			add(fichaCinco, constraints);
			
			
			//le doy la imagen al Label
			//configuraciones del label
			Border raised = BorderFactory.createRaisedBevelBorder();// crea el borde con esas caracteristicas
			Border lowered = BorderFactory.createRaisedSoftBevelBorder();
			//le defino a la clase este tipo de borde
			//confirmar.setBorder(BorderFactory.createCompoundBorder(raised,lowered));
			
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
