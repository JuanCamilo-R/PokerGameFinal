package pokerVista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import pokerModelo.Carta;



public class PanelJugador extends JPanel {
	
	private List<Carta> mano = new ArrayList<Carta>();
	private JLabel nombre, mensaje, dineroInicial, dineroApostado,apuesta;
	private JPanel panelMano,panelFichas,panelBotones, panelTexto,panelTexto2;
	private boolean isHuman;
	private Escuchas escucha;
	private JButton confirmarApuesta, cederTurno, fichaDiez, fichaCinco, fichaCien;
	private ImageIcon imagen;
	private GridBagConstraints constraints;
	private Border loweredbevel;
	public PanelJugador(boolean isHuman, String nombreJugador, List<Carta> cartas, int dineroInicial) {
		//nombre = new JLabel(nombreJugador);
		nombre = new JLabel();
		nombre.setFont(new Font("Comic Sans  MS",Font.BOLD,15));
		nombre.setText( nombreJugador);
		
		this.isHuman = isHuman;
		
		this.dineroInicial = new JLabel("Dinero inicial: "+String.valueOf(dineroInicial));
		dineroApostado = new JLabel("Dinero apostado");
		
		//Se agrega cada carta a la mano del jugador.
		
		for(Carta carta: cartas) {
			mano.add(carta);
		}
		
		panelMano = new JPanel();
		panelMano.setBackground(Color.GREEN);
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
		
		loweredbevel = BorderFactory.createBevelBorder(EtchedBorder.LOWERED);
		this.setBorder(loweredbevel);
		
		
		if(isHuman) {
			
			panelFichas= new JPanel();
			panelFichas.setLayout(new FlowLayout());
			panelFichas.setBackground(Color.green);
			
			panelBotones = new JPanel();
			panelBotones.setLayout(new GridLayout(2,1));
			panelBotones.setBackground(Color.GREEN);
			
			panelTexto = new JPanel();
			panelTexto.setLayout(new GridLayout(2,1));
			panelTexto.setBackground(Color.GREEN);
			
			confirmarApuesta = new JButton("Confirmar apuesta");
			confirmarApuesta.setFont(new Font("Times New Roman", Font.BOLD, 15));
			confirmarApuesta.setPreferredSize(new Dimension(190,30));
			cederTurno = new JButton("Retirarse");
			cederTurno.setPreferredSize(new Dimension(190,30));
			cederTurno.setFont(new Font("Times New Roman", Font.BOLD, 15));
			fichaDiez = new JButton();
			fichaCien = new JButton();
			imagen = new ImageIcon(getClass().getResource("/resources/ficha1.png"));
			fichaCinco = new JButton();
			fichaCinco.setBorder(null);
			fichaCinco.setContentAreaFilled(false);
			fichaCinco.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT)));
			

			imagen = new ImageIcon(getClass().getResource("/resources/ficha3.png"));
			fichaDiez.setBorder(null);
			fichaDiez.setContentAreaFilled(false);
			fichaDiez.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT)));
			
	
			imagen = new ImageIcon(getClass().getResource("/resources/ficha2.png"));
			fichaCien.setBorder(null);
			fichaCien.setContentAreaFilled(false);
			
			fichaCien.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT)));
			
			apuesta = new JLabel("Apuesta");
			
			panelFichas.add(fichaCinco);
			panelFichas.add(fichaDiez);
			panelFichas.add(fichaCien);
			
			constraints.gridx = 1;
			constraints.gridy = 2;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.anchor = GridBagConstraints.CENTER;
			add(panelFichas, constraints);
			
			panelBotones.add(confirmarApuesta);
			panelBotones.add(cederTurno);
			
			constraints.gridx = 2;
			constraints.gridy = 1;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.anchor = GridBagConstraints.NORTH;
			add(panelBotones, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 3;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.anchor = GridBagConstraints.CENTER;
			add(apuesta, constraints);
			
			
			this.dineroInicial.setFont(new Font("Times New Roman", Font.BOLD, 15));
			dineroApostado.setFont(new Font("Times New Roman", Font.BOLD, 15));
			panelTexto.add(this.dineroInicial);
			panelTexto.add(dineroApostado);
			
			constraints.gridx = 2;
			constraints.gridy = 2;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.anchor = GridBagConstraints.CENTER;
			add(panelTexto, constraints);
			
			
		}
		if(!isHuman) {
			panelTexto2 = new JPanel();
			panelTexto2.setBackground(Color.GREEN);
			this.dineroInicial = new JLabel("Dinero inicial: "+String.valueOf(dineroInicial));
			dineroApostado = new JLabel("|   Dinero apostado");
			
			this.dineroInicial.setFont(new Font("Times New Roman", Font.BOLD, 15));
			dineroApostado.setFont(new Font("Times New Roman", Font.BOLD, 15));
			
			
			constraints.gridx = 1;
			constraints.gridy = 2;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.anchor = GridBagConstraints.CENTER;
			panelTexto2.add(this.dineroInicial);
			
			
			constraints.gridx = 1;
			constraints.gridy = 2;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.anchor = GridBagConstraints.CENTER;
			panelTexto2.add(dineroApostado);
			add(panelTexto2, constraints);
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
