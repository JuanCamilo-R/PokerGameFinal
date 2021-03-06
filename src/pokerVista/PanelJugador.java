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
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import pokerControl.ControlPoker;
import pokerModelo.Carta;



public class PanelJugador extends JPanel {
	
	private List<Carta> mano = new ArrayList<Carta>();
	private JLabel nombre, mensaje, dineroInicial, dineroApostado,apuesta,numeroDineroInicial,numeroDineroApostado;
	private JPanel panelMano,panelFichas,panelBotones, panelTexto,panelTexto2;
	private boolean isHuman;
	private Escuchas escucha;
	private JButton confirmarApuesta, cederTurno, fichaDiez, fichaCinco, fichaCien, confirmarDescarte;
	private ImageIcon imagen;
	private int turno;
	private boolean siguienteTurno;
	private GridBagConstraints constraints;
	private Border loweredbevel;
	private JPanel nosotros;
	private ControlPoker control;
	public PanelJugador(boolean isHuman, String nombreJugador, List<Carta> cartas, int dineroInicial, ControlPoker control) {
		//nombre = new JLabel(nombreJugador);
		this.control = control;
		nombre = new JLabel();
		nombre.setFont(new Font("Comic Sans  MS",Font.BOLD,15));
		nombre.setText( nombreJugador);
		escucha = new Escuchas();
		this.isHuman = isHuman;
		siguienteTurno = false;
		nosotros = this;
		//Se agrega cada carta a la mano del jugador.
		
		for(Carta carta: cartas) {
			mano.add(carta);
		}
		

		for(int i = 0; i < mano.size(); i++) {
			mano.get(i).addMouseListener(escucha);
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
			this.dineroInicial = new JLabel("Dinero inicial: ");
			dineroApostado = new JLabel("Dinero apostado: ");
			
			numeroDineroInicial  = new JLabel(String.valueOf(dineroInicial));
			numeroDineroApostado = new JLabel("100");
			
			panelFichas= new JPanel();
			panelFichas.setLayout(new FlowLayout());
			panelFichas.setBackground(Color.green);
			
			panelBotones = new JPanel();
			panelBotones.setLayout(new GridLayout(3,1));
			panelBotones.setBackground(Color.GREEN);
			
			panelTexto = new JPanel();
			panelTexto.setLayout(new GridLayout(2,1));
			panelTexto.setBackground(Color.GREEN);
			
			confirmarApuesta = new JButton("Confirmar apuesta");
			confirmarApuesta.setFont(new Font("Times New Roman", Font.BOLD, 15));
			confirmarApuesta.setPreferredSize(new Dimension(190,30));
			confirmarApuesta.addMouseListener(escucha);
			
			confirmarDescarte = new JButton("Confirmar descarte");
			confirmarDescarte.setFont(new Font("Times New Roman", Font.BOLD, 15));
			confirmarDescarte.setPreferredSize(new Dimension(190,30));
			confirmarDescarte.setEnabled(false);
			confirmarDescarte.addMouseListener(escucha);
			
			cederTurno = new JButton("Retirarse");
			cederTurno.setPreferredSize(new Dimension(190,30));
			cederTurno.setFont(new Font("Times New Roman", Font.BOLD, 15));
			cederTurno.addMouseListener(escucha);
			
			
			
			imagen = new ImageIcon(getClass().getResource("/resources/ficha1.png"));
			fichaCinco = new JButton();
			fichaCinco.setBorder(null);
			fichaCinco.setContentAreaFilled(false);
			fichaCinco.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT)));
			fichaCinco.addMouseListener(escucha);
			
			fichaDiez = new JButton();
			imagen = new ImageIcon(getClass().getResource("/resources/ficha3.png"));
			fichaDiez.setBorder(null);
			fichaDiez.setContentAreaFilled(false);
			fichaDiez.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT)));
			fichaDiez.addMouseListener(escucha);
			
			
			fichaCien = new JButton();
			imagen = new ImageIcon(getClass().getResource("/resources/ficha2.png"));
			fichaCien.setBorder(null);
			fichaCien.setContentAreaFilled(false);
			fichaCien.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT)));
			fichaCien.addMouseListener(escucha);
			
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
			panelBotones.add(confirmarDescarte);
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
			numeroDineroInicial.setFont(new Font("Times New Roman", Font.BOLD, 15));
			numeroDineroApostado.setFont(new Font("Times New Roman", Font.BOLD, 15));
			panelTexto.add(this.dineroInicial);
			panelTexto.add(numeroDineroInicial);
			panelTexto.add(dineroApostado);
			panelTexto.add(numeroDineroApostado);
			
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
			this.dineroInicial = new JLabel("Dinero inicial: ");
			dineroApostado = new JLabel("|   Dinero apostado: ");
			
			numeroDineroInicial  = new JLabel(String.valueOf(dineroInicial));
			numeroDineroApostado = new JLabel();
			
			this.dineroInicial.setFont(new Font("Times New Roman", Font.BOLD, 15));
			dineroApostado.setFont(new Font("Times New Roman", Font.BOLD, 15));
			numeroDineroInicial.setFont(new Font("Times New Roman", Font.BOLD, 15));
			numeroDineroApostado.setFont(new Font("Times New Roman", Font.BOLD, 15));
			
			
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
			panelTexto2.add(numeroDineroInicial);
			
			constraints.gridx = 1;
			constraints.gridy = 3;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.anchor = GridBagConstraints.CENTER;
			panelTexto2.add(dineroApostado);
			
			constraints.gridx = 1;
			constraints.gridy = 4;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.anchor = GridBagConstraints.CENTER;
			panelTexto2.add(numeroDineroApostado);
			
			
			add(panelTexto2, constraints);
		}
		
		
	}
	
	public void getControl(ControlPoker control) {
		this.control = control;
	}
	private void refrescarMano () {
		panelMano.removeAll();
		if(mano!=null) {
			for(Carta carta : mano) {
				panelMano.add(carta);
			}
		}
	}
	
	public void setTurno(int turno) {
		if(isHuman) {
			this.turno = turno;
		}
	}
	
	public void refrescarLabels(int apuesta, String nombreJugador) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if( nombre.getText() == nombreJugador) {
					//System.out.println("Entro a refrescarLabels");
					numeroDineroApostado.setText(String.valueOf(apuesta));
					numeroDineroInicial.setText(String.valueOf(Integer.parseInt(numeroDineroInicial.getText())-apuesta));
					nosotros.revalidate();	
				    nosotros.repaint();
				    
				}
			}
			
		});
		
	}
	
	public boolean getSiguienteTurno() {
		return siguienteTurno;
	}
	
	public void setSiguienteTurno(boolean turno) {
		this.siguienteTurno = turno;
	}
	
	public int getTurno() {
		return turno;
	}
	public int getApuestaUsuario() {
		if(isHuman) {
			int aux = Integer.parseInt(numeroDineroApostado.getText());
			return aux;
		}
		return 0;
	}
	
	private class Escuchas extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			int dineroInicial = Integer.parseInt(numeroDineroInicial.getText());
			int dineroApostado = Integer.parseInt(numeroDineroApostado.getText());
			if(event.getSource() == fichaCinco ) {
				System.out.print("Turno usuario: "+turno);
				numeroDineroApostado.setText(String.valueOf(Integer.parseInt(numeroDineroApostado.getText())+5));
			}
			if(event.getSource() == fichaDiez ) {
				numeroDineroApostado.setText(String.valueOf(Integer.parseInt(numeroDineroApostado.getText())+10));
			}
			if(event.getSource() == fichaCien ) {
				numeroDineroApostado.setText(String.valueOf(Integer.parseInt(numeroDineroApostado.getText())+100));
			}
			if(event.getSource() == confirmarApuesta) {
				dineroApostado = Integer.parseInt(numeroDineroApostado.getText());
				if(dineroInicial <= dineroApostado ) {
					JOptionPane.showMessageDialog(null, "No puedes apostar todo esto!");
				}
				siguienteTurno = true;
				control.setTurnoActual();
				control.turnos(7,4);
				System.out.println(siguienteTurno);
			}
		}
	}
	
}
