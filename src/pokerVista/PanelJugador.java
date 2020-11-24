/*
 * Jennyfer Belalcazar 		- 1925639-3743
 * Samuel Riascos Prieto 	- 1922540-3743
 * Juan Camilo Randazzo		- 1923948-3743
 */
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



// TODO: Auto-generated Javadoc
/**
 * The Class PanelJugador.
 * 
 * Clase en la cual se guarda informacion de cada jugador como:
 * -Dinero actual apostado
 * -Dinero actual
 * -nombre
 * -cartas
 * 
 */
public class PanelJugador extends JPanel {
	
	/** The mano. 
	 * Lista donde se guardan las cartas de cada jugador
	 * */
	private List<Carta> mano = new ArrayList<Carta>();
	
	/** JLabels
	 *  Para mostrar en pantalla informacion de cada jugador
	 *
	 * */
	private JLabel nombre, dineroInicial, dineroApostado,apuesta,numeroDineroInicial,numeroDineroApostado;
	
	/** Jpanels
	 * Para agrupar informacion
	 * panelMano: se guardan las cartas 
	 * panelFichas: son las fichas sobre las cuales el usuario representa su apuesta
	 * panelBotones: agrupamos los botones que sean necesarios
	 * panelTexto y panelTexto2 : agrupamos los labels que sean necesarios
	 *  */
	private JPanel panelMano,panelFichas,panelBotones, panelTexto,panelTexto2;
	
	/** The is human.
	 *  Para identificar si es el panel del usuario
	 * */
	private boolean isHuman;
	
	/** The escucha.
	 * Para darle las escuchas a los botones
	 *  */
	private Escuchas escucha;
	
	/** JButtons.
	 * confirmarApuesta: Boton que usa el usuario para confirmar su apuesta
	 * fichaCinco, fichaDiez, fichaCien : Botones por los cuales el usuario representa su apuesta
	 * confirmarDescarte: Boton que usa el usuario para confirmar su descarte
	 *  */
	private JButton confirmarApuesta, fichaDiez, fichaCinco, fichaCien, confirmarDescarte;
	
	/** The imagen. 
	 * Para poner las imagenes necesarias a botones y labels
	 * */
	private ImageIcon imagen;
	
	/** The turno.
	 * Saber en que turno estamos
	 *  */
	private int turno;
	
	/** The apuesta minima.
	 * La apuesta minima al iniciar( cambia en la segunda ronda)
	 *  */
	public  static int apuestaMinima=100;
	
	/** The siguiente turno. 
	 * se pone true cuando he apostado o descartado
	 * */
	private boolean siguienteTurno;
	
	/** The constraints. */
	private GridBagConstraints constraints;
	
	/** The loweredbevel. */
	private Border loweredbevel;
	
	/** The nosotros. */
	private JPanel nosotros;
	
	/** The control. */
	private ControlPoker control;
	
	/** The saber si aposto. */
	private boolean saberSiAposto;
	
	/** The contador cartas pedidas. */
	private int contadorCartasPedidas = 0; 
	
	/**
	 * Retorna cuantas cartas pidio el usuario
	 * Gets the contador cartas pedidas.
	 *
	 * @return the contador cartas pedidas
	 */
	public int getContadorCartasPedidas() {
		return contadorCartasPedidas;
	}

	/**
	 * Instantiates a new panel jugador.
	 *
	 * @param isHuman (para identificar si es el usuario o un hilo)
	 * @param nombreJugador (de cada jugador)
	 * @param cartas (de cada jugador)
	 * @param dineroInicial (de cada jugador)
	 * @param control the control
	 */
	public PanelJugador(boolean isHuman, String nombreJugador, List<Carta> cartas, int dineroInicial, ControlPoker control) {
		//Constructor
		
		this.control = control;
		
		//Creo el label de nombre y se lo asigno
		nombre = new JLabel();
		nombre.setFont(new Font("Comic Sans  MS",Font.BOLD,15));
		nombre.setText( nombreJugador);
		
		escucha = new Escuchas();
		
		this.isHuman = isHuman;
		siguienteTurno = false;
		
		//referencia a nosotros mismos
		nosotros = this;
		saberSiAposto =false;
		
		//Se agrega cada carta a la mano del jugador y el dinero inicial
		initGUI(cartas, dineroInicial);	
	}
	
	/**
	 * Inits the GUI.
	 *
	 * @param cartas the cartas
	 * @param dineroInicial the dinero inicial
	 */
	public void initGUI(List<Carta> cartas, int dineroInicial) {
		//Agregamos cartas a la mano de ada jugador
		for(Carta carta: cartas) {
			mano.add(carta);
		}
		
		panelMano = new JPanel();
		panelMano.setBackground(Color.GREEN);
		this.setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		
		//Agregamos el nombre
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 3;
		constraints.anchor = GridBagConstraints.CENTER;
		add(nombre, constraints);
		
		//Actualizar la GUI
		refrescarMano();
		
		//Agregamos el panel mano( las cartas de cada jugador)
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		add(panelMano, constraints);
		
		//Le ponemos bordes a cada panel
		loweredbevel = BorderFactory.createBevelBorder(EtchedBorder.LOWERED);
		this.setBorder(loweredbevel);
		
		//Le agregamos mas cosas al panel si es el usuario
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
		//Le agreamos cosas al panel en caso que sea un hilo
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
	
	/**
	 * Reiniciar juego.
	 * Recibe nuevas cartas y el dinero inicial
	 * @param cartas the cartas
	 * @param dineroInicial the dinero inicial
	 */
	public void reiniciarJuego(List<Carta> cartas, int dineroInicial) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(isHuman) {
					//Ponemos las variables en su estado inicial, removemos todas las escuchas y ponemos las necesarias
					siguienteTurno = false;
					saberSiAposto = false;
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
	
	/**
	 * Gets the control.
	 *	Darle el control a panel
	 * @param control the control
	 * @return the control
	 */
	public void getControl(ControlPoker control) {
		this.control = control;
	}
	
	/**
	 * Agrega las nuevas cartas
	 * Refrescar mano.
	 */
	private void refrescarMano () {
		panelMano.removeAll();
		if(mano!=null) {
			for(Carta carta : mano) {
				panelMano.add(carta);
			}
		}
	}
	
	/**
	 * Sets the turno.
	 * Da el turno al usuario
	 * @param turno the new turno
	 */
	public void setTurno(int turno) {
		if(isHuman) {
			this.turno = turno;
		}
	}
	
	/**
	 * Gets the dinero inicial.
	 * Retorna el dinero inicial
	 * @return the dinero inicial
	 */
	public String getDineroInicial() {
		//Retorna el dinero inicial
		return this.numeroDineroInicial.getText();
	}
	
	/**
	 * Refrescar labels.
	 * Despues de apostar actualizamos los labels del dinero
	 * @param apuesta the apuesta
	 * @param nombreJugador the nombre jugador
	 * @param dineroInicial the dinero inicial
	 */
	public void refrescarLabels(int apuesta, String nombreJugador, String dineroInicial) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				if( nombre.getText() == nombreJugador) {		

					numeroDineroInicial.setText(dineroInicial);
					numeroDineroApostado.setText(String.valueOf(apuesta));
					nosotros.revalidate();	
				    nosotros.repaint();	    
				}
			}
			
		});
		
	}
	
	/**
	 * Refrescar cartas.
	 *Despues de descartar actualizamos los labels de las cartas
	 * @param nombreJugador the nombre jugador
	 * @param cartasNuevas the cartas nuevas
	 */
	public void refrescarCartas(String nombreJugador, List<Carta> cartasNuevas) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub

				if( nombre.getText() == nombreJugador) {		

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
	
	/**
	 * Gets the siguiente turno.
	 * Retorna true o false dependiendo si ya aposte o desarte
	 * @return the siguiente turno
	 */
	public boolean getSiguienteTurno() {
		return siguienteTurno;
	}
	
	/**
	 * Sets the siguiente turno.
	 * Modificar el atributo de siguienteTurno al ya apostar o descartar
	 * @param turno the new siguiente turno
	 */
	public void setSiguienteTurno(boolean turno) {
		this.siguienteTurno = turno;
	}
	
	/**
	 * Gets the turno.
	 * Retorna el turno
	 * @return the turno
	 */
	public int getTurno() {
		return turno;
	}
	
	/**
	 * Gets the apuesta usuario.
	 * Retorna la apuesta que esta en el label del usuario
	 * @return the apuesta usuario
	 */
	public int getApuestaUsuario() {
		if(isHuman) {
			int aux = Integer.parseInt(numeroDineroApostado.getText());
			return aux;
		}
		return 0;
	}

	/**
	 * Eliminar carta.
	 * Recibe como parametro una carta la cual sera eliminada
	 * @param cartaEliminar the carta eliminar
	 */
	private void eliminarCarta(Carta cartaEliminar) { 
		   mano = Collections.synchronizedList(mano);
		   synchronized (mano){
			   for(int i = 0;i < mano.size(); i++) {
				 if(cartaEliminar.getValor() == mano.get(i).getValor() && cartaEliminar.getPalo()==mano.get(i).getPalo() ) {
					 mano.remove(i);
				 }   
			   }
		   }
	   }
	
	/**
	 * Recibir cartas humano.
	 * Descapuesta de descartar le damos a la lista mano la nueva lista de cartas
	 * @param nuevasCartas the nuevas cartas
	 */
	public void recibirCartasHumano(List<Carta> nuevasCartas) {
		mano.clear();
		mano = nuevasCartas;
	}
	
	/**
	 * The Class Escuchas.
	 */
	private class Escuchas extends MouseAdapter {
		
		/**
		 * Mouse clicked.
		 *
		 * @param event the event
		 */
		public void mouseClicked(MouseEvent event) {
			//Guardamenos el string que esta en los labels de dinero
			int dineroInicial = Integer.parseInt(numeroDineroInicial.getText());
			int dineroApostado = Integer.parseInt(numeroDineroApostado.getText());
			
			if(event.getSource() == fichaCinco ) {
				//Aumentamos la apuesta en 5
				numeroDineroApostado.setText(String.valueOf(Integer.parseInt(numeroDineroApostado.getText())+5));
				saberSiAposto =true;
			}
			if(event.getSource() == fichaDiez ) {
				//Aumentamos la apuesta en 10
				numeroDineroApostado.setText(String.valueOf(Integer.parseInt(numeroDineroApostado.getText())+10));
				saberSiAposto =true;
			}
			if(event.getSource() == fichaCien ) {
				//Aumentamos la apuesta en 100
				numeroDineroApostado.setText(String.valueOf(Integer.parseInt(numeroDineroApostado.getText())+100));
				saberSiAposto =true;
			}
			
			if(event.getSource() == confirmarApuesta) {
				dineroApostado = Integer.parseInt(numeroDineroApostado.getText());
				
				//Verificamos que el usuario haya apostado el dinero minimo requerido, sino debe aumentar o igualar la apuesta
				if((dineroInicial <= dineroApostado || Integer.parseInt(numeroDineroApostado.getText())<apuestaMinima )&& control.getControlador()<2) {
					JOptionPane.showMessageDialog(null, "No puedes apostar todo esto o no has apostado. La apuesta minima es de "+apuestaMinima);
					numeroDineroApostado.setText("0");
				}
				//Si puedo apostar
				else {
					if(control.getTipoRonda()) { //Ronda apuesta

						control.añadirAlTotal(getApuestaUsuario());
						if(getApuestaUsuario()>apuestaMinima) {//Actualizamos la apuesta minima
							apuestaMinima=getApuestaUsuario();
						}
						refrescarLabels(getApuestaUsuario(), nombre.getText(), String.valueOf(Integer.parseInt(getDineroInicial())-getApuestaUsuario()));
						//Le doy paso el siguiente jugador

						siguienteTurno = true;
						//Aumentamos el turno
						control.setTurnoActual();

						
						//Despertamos a los hilos llamando a turnos que es la funcion que sincroniza los hilos
						control.turnos(100, nombre.getText(), 0, Integer.parseInt(getDineroInicial()), false);

						saberSiAposto =false;
						//Descativamos la escucha si la apuesta es valida
						confirmarApuesta.setEnabled(false);
						confirmarApuesta.removeMouseListener(escucha);
						
					//El controlador me revisa en que ronda estoy, cuando llegue a 3 es porque ya se acabo el juego
					if(control.getControlador()<2) {
						//Quitamos las escuchas y agregamos las de descarte
						confirmarDescarte.setEnabled(true);
						confirmarDescarte.addMouseListener(escucha);
						apuesta.setText("Descarta...");
						for(int i = 0; i < mano.size(); i++) {
							mano.get(i).addMouseListener(escucha);
						}
						fichaCinco.removeMouseListener(escucha);
						fichaDiez.removeMouseListener(escucha);
						fichaCien.removeMouseListener(escucha);
					}else {
						//Quitamos las escuchas
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
					//Al llegar a 3  debo quitar las escuchas porque estoy en la ultima ronda de apuestas
						if(control.getControlador()>=3) {
							fichaCinco.removeMouseListener(escucha);
							fichaDiez.removeMouseListener(escucha);
							fichaCien.removeMouseListener(escucha);
							for(int i = 0; i < mano.size(); i++) {
								mano.get(i).removeMouseListener(escucha);
							}
						}
				
						//Si mi turno era el 5 debo activar la ronda de descarte
						if(getTurno()==5) {
							control.activarRondaDescarte();
						}
					}
					
				}
				
				
			}
			
			//Para saber cual carta toco el usuario y así borrarla de su mano
			for(int i = 0; i < mano.size(); i++) {
				if(event.getSource() == mano.get(i)) {
					contadorCartasPedidas++;
					control.descarteHumano(mano.get(i));
					eliminarCarta(mano.get(i));
					refrescarMano();
					panelMano.revalidate();
					panelMano.repaint();
				}
			}
			
			if(event.getSource() == confirmarDescarte) {
				//Despues de descartar es una nueva ronda de apuesta por lo tanto los label de apuesta se incializan
				control.reiniciarApuesta();
				numeroDineroApostado.setText("0");
				PanelJugador.apuestaMinima=100;
				
				//Le doy las cartas al usuario
				control.darCartasHumanos(contadorCartasPedidas);
				refrescarMano();
				setSiguienteTurno(true);
				//aumento el turno
				control.setTurnoActual();

				//Despertar los hilos despues de mi para que apuesten
				control.turnos(100,nombre.getText(), 0, Integer.parseInt(getDineroInicial()), false); 

				
				//Quito escucha e inavilito el boton despues de confirmar el descarte
				confirmarDescarte.setEnabled(false);
				confirmarDescarte.removeMouseListener(escucha);
				
				//Quito las escuchas de las cartas
				for(int i = 0; i < mano.size(); i++) {
					mano.get(i).removeMouseListener(escucha);
				}
				
				//El controlador me revisa en que ronda estoy, cuando llegue a 3 es porque ya se acabo el juego
				if(control.getControlador()<=2) {
					//Quitamos y agreamos las escuchas necesarias
					apuesta.setText("Apuesta...");
					apuesta.setVisible(true);
					fichaCinco.addMouseListener(escucha);
					fichaDiez.addMouseListener(escucha);
					fichaCien.addMouseListener(escucha);
					confirmarApuesta.setEnabled(true);
					confirmarApuesta.addMouseListener(escucha);
				}else {
					//Quitamos las escuchas
					apuesta.setVisible(false);
					fichaCinco.removeMouseListener(escucha);
					fichaDiez.removeMouseListener(escucha);
					fichaCien.removeMouseListener(escucha);
				}
				//Al llegar a 3  debo quitar las escuchas porque estoy en la ultima ronda de apuestas
				if(control.getControlador()>=3) {
					fichaCinco.removeMouseListener(escucha);
					fichaDiez.removeMouseListener(escucha);
					fichaCien.removeMouseListener(escucha);
					for(int i = 0; i < mano.size(); i++) {
						mano.get(i).removeMouseListener(escucha);
					}
				}
				//Si mi turno era el 5 debo activar la ronda de descarte
				if(getTurno() == 5) {
					control.activarRondaApuestas();
				}
				
			}		
		}
	}

}
