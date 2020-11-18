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
import java.util.Collections;
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
	public  static int apuestaMinima=100;
	private boolean siguienteTurno;
	private GridBagConstraints constraints;
	private Border loweredbevel;
	private JPanel nosotros;
	private ControlPoker control;
	private boolean saberSiAposto;
	private int contadorCartasPedidas = 0; 
	public int getContadorCartasPedidas() {
		return contadorCartasPedidas;
	}

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
		saberSiAposto =false;
		//Se agrega cada carta a la mano del jugador.
		initGUI(cartas, dineroInicial);
		
	}
	
	public void initGUI(List<Carta> cartas, int dineroInicial) {
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
			this.dineroInicial = new JLabel("Dinero actual: ");
			dineroApostado = new JLabel("Apuesta actual: ");
			
			numeroDineroInicial  = new JLabel(String.valueOf(dineroInicial-100));
			numeroDineroApostado = new JLabel("0");
			
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
			this.dineroInicial = new JLabel("Dinero actual: ");
			dineroApostado = new JLabel("|   Apuesta actual: ");
			
			numeroDineroInicial  = new JLabel(String.valueOf(dineroInicial));
			numeroDineroApostado = new JLabel("0");
			
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
	public void reiniciarJuego(List<Carta> cartas, int dineroInicial) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(isHuman) {
					siguienteTurno = false;
					apuesta.setText("Apuesta");
					apuesta.setVisible(true);
					mano.clear();
					for(Carta carta: cartas) {
						mano.add(carta);
					}
					refrescarMano();
					contadorCartasPedidas = 0;
					numeroDineroApostado.setText("0");
					numeroDineroInicial.setText(String.valueOf(dineroInicial-100));
					fichaCinco.removeMouseListener(escucha);
					fichaDiez.removeMouseListener(escucha);
					fichaCien.removeMouseListener(escucha);
					for(int i = 0; i < mano.size(); i++) {
						mano.get(i).removeMouseListener(escucha);
					}
					confirmarDescarte.removeMouseListener(escucha);
					confirmarDescarte.setEnabled(false);
					confirmarApuesta.setEnabled(false);
					confirmarApuesta.removeMouseListener(escucha);
					
					confirmarApuesta.setEnabled(true);
					confirmarApuesta.addMouseListener(escucha);
					fichaCinco.addMouseListener(escucha);
					fichaDiez.addMouseListener(escucha);
					fichaCien.addMouseListener(escucha);
					nosotros.revalidate();
					nosotros.repaint();
				}else {
					mano.clear();
					for(Carta carta: cartas) {
						mano.add(carta);
					}
					refrescarMano();
					numeroDineroInicial.setText("0");
					numeroDineroInicial.setText(String.valueOf(dineroInicial));
				}
			}
			
		});
		
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
	
	public String getDineroInicial() {
		return this.numeroDineroInicial.getText();
	}
	
	public void refrescarLabels(int apuesta, String nombreJugador, String dineroInicial) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if( nombre.getText() == nombreJugador) {
					if(nombreJugador == "ElBicho") {
					//	System.out.println(nombreJugador+" entra a refrescar labels");
					}
					//System.out.println("Entro a refrescarLabels");
					numeroDineroInicial.setText(dineroInicial);
					numeroDineroApostado.setText(String.valueOf(apuesta));
					//System.out.println("El dinero apostado es "+apuesta);
					nosotros.revalidate();	
				    nosotros.repaint();
				    
				}
			}
			
		});
		
	}
	
	public void refrescarCartas(String nombreJugador, List<Carta> cartasNuevas) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if( nombre.getText() == nombreJugador) {
					if(nombreJugador == "ElBicho") {
					}
					//System.out.print("Entre a panel"+nombreJugador+"  ");

					panelMano.removeAll();
					if(cartasNuevas!=null) {
						for(Carta carta : cartasNuevas) {
							panelMano.add(carta);
						}
					}
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

	private void eliminarCarta(Carta cartaEliminar) { 
		   mano = Collections.synchronizedList(mano);
		   synchronized (mano){
			   for(int i = 0;i < mano.size(); i++) {
				 if(cartaEliminar.getValor() == mano.get(i).getValor() && cartaEliminar.getPalo()==mano.get(i).getPalo() ) {
					 mano.remove(i);
				 }   
			   }
			   //System.out.println("MANO EN PANEL JUGADOR");
			   //System.out.println(mano);
		   }
	   }
	
	public void recibirCartasHumano(List<Carta> nuevasCartas) {
		mano.clear();
		mano = nuevasCartas;
	}
	
	private class Escuchas extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			int dineroInicial = Integer.parseInt(numeroDineroInicial.getText());
			int dineroApostado = Integer.parseInt(numeroDineroApostado.getText());
			if(event.getSource() == fichaCinco ) {
				numeroDineroApostado.setText(String.valueOf(Integer.parseInt(numeroDineroApostado.getText())+5));
				saberSiAposto =true;
			}
			if(event.getSource() == fichaDiez ) {
				numeroDineroApostado.setText(String.valueOf(Integer.parseInt(numeroDineroApostado.getText())+10));
				saberSiAposto =true;
			}
			if(event.getSource() == fichaCien ) {
				//System.out.println("Apuesto cien");
				numeroDineroApostado.setText(String.valueOf(Integer.parseInt(numeroDineroApostado.getText())+100));
				saberSiAposto =true;
			}
			if(event.getSource() == confirmarApuesta) {
				dineroApostado = Integer.parseInt(numeroDineroApostado.getText());
				if(dineroInicial <= dineroApostado || Integer.parseInt(numeroDineroApostado.getText())<apuestaMinima) {
					JOptionPane.showMessageDialog(null, "No puedes apostar todo esto o no has apostado");
					numeroDineroApostado.setText("0");
				}
				//Si puedo apostar
				else {
					if(control.getTipoRonda()) { //Ronda apuesta
						//System.out.println("Dinero apuesta usuario: "+getApuestaUsuario());
						if(getApuestaUsuario()>apuestaMinima) {
							apuestaMinima=getApuestaUsuario();
						}
						refrescarLabels(getApuestaUsuario(), "ElBicho", String.valueOf(Integer.parseInt(getDineroInicial())-getApuestaUsuario()));
						siguienteTurno = true;
						//System.out.println("Turno usuario: "+turno);
						//System.out.println("TURNO ACTUAL EN APUESTA ESCUCHAS: "+control.getTurno());
						control.setTurnoActual();
						
						//System.out.println(nombre+" TURNO DEL HILO QUE ENTRA: "+getTurno());
						control.turnos(100, "ElBicho", 0, Integer.parseInt(getDineroInicial()));
						saberSiAposto =false;
						//System.out.println(siguienteTurno);
						confirmarApuesta.setEnabled(false);
						confirmarApuesta.removeMouseListener(escucha);
					if(control.getControlador()<2) {
						confirmarDescarte.setEnabled(true);
						confirmarDescarte.addMouseListener(escucha);
						apuesta.setText("Descarta...");
						//System.out.println("Mano size: "+mano.size());
						//System.out.println("IMPRIMIR MANO:.....");
						for(int i = 0; i < mano.size(); i++) {
							System.out.println(mano.get(i));
							mano.get(i).addMouseListener(escucha);
						}
						fichaCinco.removeMouseListener(escucha);
						fichaDiez.removeMouseListener(escucha);
						fichaCien.removeMouseListener(escucha);
					}else {
						apuesta.setText("");
						confirmarDescarte.setEnabled(false);
						confirmarDescarte.removeMouseListener(escucha);
						fichaCinco.removeMouseListener(escucha);
						fichaDiez.removeMouseListener(escucha);
						fichaCien.removeMouseListener(escucha);
						for(int i = 0; i < mano.size(); i++) {
							mano.get(i).removeMouseListener(escucha);
						}
					}
						if(control.getControlador()>=3) {
							fichaCinco.removeMouseListener(escucha);
							fichaDiez.removeMouseListener(escucha);
							fichaCien.removeMouseListener(escucha);
							for(int i = 0; i < mano.size(); i++) {
								mano.get(i).removeMouseListener(escucha);
							}
						}
				
						if(getTurno()==5) {
							System.out.println("El bicho depierta a los hilos en ronda de descarte");
							control.activarRondaDescarte();
						}
					}
					
				}
				
				
			}
			
			for(int i = 0; i < mano.size(); i++) {
				if(event.getSource() == mano.get(i)) {
					contadorCartasPedidas++;
					control.descarteHumano(mano.get(i));
					eliminarCarta(mano.get(i));
					//System.out.println("Entro a mano descartada");
					refrescarMano();
					panelMano.revalidate();
					panelMano.repaint();
				}
			}
			
			if(event.getSource() == confirmarDescarte) {
				control.reiniciarApuesta();
				numeroDineroApostado.setText("0");
				PanelJugador.apuestaMinima=100;
				control.darCartasHumanos(contadorCartasPedidas);
				refrescarMano();
				//System.out.println("CARTAS PEDIDAS: "+ contadorCartasPedidas);
				//System.out.println("Mano size: "+mano.size());
				setSiguienteTurno(true);
				//System.out.println("TURNO ACTUAL EN DESCARTE ESCUCHAS: "+control.getTurno());
				control.setTurnoActual();
				//System.out.println(nombre.getText()+" TURNO DEL HILO QUE ENTRA: "+getTurno());
				control.turnos(100,"ElBicho", 0, Integer.parseInt(getDineroInicial())); //Descarten después de mí
				
				confirmarDescarte.setEnabled(false);
				confirmarDescarte.removeMouseListener(escucha);
				for(int i = 0; i < mano.size(); i++) {
					mano.get(i).removeMouseListener(escucha);
				}
				if(control.getControlador()<=2) {
					System.out.println(" controlador en <= 2: "+control.getControlador());
					//System.out.println("ENTRO A CONDICION DE CONTROLADOR");
					apuesta.setText("Apuesta...");
					apuesta.setVisible(true);
					fichaCinco.addMouseListener(escucha);
					fichaDiez.addMouseListener(escucha);
					fichaCien.addMouseListener(escucha);
					confirmarApuesta.setEnabled(true);
					confirmarApuesta.addMouseListener(escucha);
				}else {
					apuesta.setVisible(false);
					fichaCinco.removeMouseListener(escucha);
					fichaDiez.removeMouseListener(escucha);
					fichaCien.removeMouseListener(escucha);
				}
				/*
				confirmarApuesta.setEnabled(true);
				confirmarApuesta.addMouseListener(escucha);*/
				if(control.getControlador()>=3) {
					fichaCinco.removeMouseListener(escucha);
					fichaDiez.removeMouseListener(escucha);
					fichaCien.removeMouseListener(escucha);
					for(int i = 0; i < mano.size(); i++) {
						mano.get(i).removeMouseListener(escucha);
					}
				}
				
				
				if(getTurno() == 5) {
					
					control.activarRondaApuestas();
				}
				
			}
			
			
		}
	}

	
 
	
}
