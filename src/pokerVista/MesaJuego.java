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
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import pokerControl.ControlPoker;
import pokerModelo.Carta;



// TODO: Auto-generated Javadoc
/**
 * The Class MesaJuego.
 * Simula la mesa donde juegan los jugadores
 */
public class MesaJuego extends JPanel {
	
	/** The panel
	 * Cada jugador tiene su propio panel 
	 * . */
	private PanelJugador panelJugador1,panelJugador2,panelJugador3,panelJugador4,panelJugador5;
	
	/** The imagen. 
	 * Para darle la imagen al logo
	 * */
	private ImageIcon imagen;
	
	/** The JLabel
	 *  Label que se usa para poner el logo
	 * */
	private JLabel logo,tipoRonda,espacio, totalApostado;
	
	/** The JButton
	 *  cederTurno es para volver a iniciar el juego
	 * . */
	private JButton salir,instrucciones, cederTurno;
	
	/** The area estado.
	 * Historial del juego
	 *  */

	private JTextArea areaEstado;
	
	/** The constraints. */
	private GridBagConstraints constraints;
	
	/** The control. */
	private ControlPoker control;
	
	/** The panel botones 2. 
	 * Se ponen los botones de salir,instrucciones y cederTurno
	 * */
	private JPanel panelBotones2;
	
	/** The escuchas.
	 * Poner escuchas
	 *  */
	private Escuchas2 escuchas;
	
	/** The instrucciones 2. */
	private Instrucciones instrucciones2;
	
	/** The vista. */
	private JFrame vista;
		
		/**
		 * Instantiates a new mesa juego.
		 *
		 * @param isHuman the is human
		 * @param nombre the nombre
		 * @param manoJugador the mano jugador
		 * @param dineroInicial the dinero inicial
		 * @param control the control
		 */
		public MesaJuego(List<Boolean> isHuman,List<String> nombre, List<List<Carta>> manoJugador, List<Integer> dineroInicial, ControlPoker control) {
			this.control = control;
			this.setBackground(Color.GREEN);
			escuchas = new Escuchas2();
			instrucciones2 = new  Instrucciones();
			//Creo los paneles de cada jugador
			panelJugador1 = new PanelJugador(isHuman.get(0),nombre.get(0),manoJugador.get(0),dineroInicial.get(0), this.control);
			panelJugador2 = new PanelJugador(isHuman.get(1),nombre.get(1),manoJugador.get(1),dineroInicial.get(1), this.control);
			panelJugador3 = new PanelJugador(isHuman.get(2),nombre.get(2),manoJugador.get(2),dineroInicial.get(2), this.control); //Nosotros
			panelJugador4 = new PanelJugador(isHuman.get(3),nombre.get(3),manoJugador.get(3),dineroInicial.get(3), this.control);
			panelJugador5 = new PanelJugador(isHuman.get(4),nombre.get(4),manoJugador.get(4),dineroInicial.get(4), this.control);
			initGUI();
		}
		
		/**
		 * Inits the GUI.
		 */
		public void initGUI() {
			//Agrego lo necesario al JPanel
			this.setLayout(new GridBagLayout());
			constraints = new GridBagConstraints();
			//Pongo fondo verde
			panelJugador1.setBackground(Color.GREEN);
			panelJugador2.setBackground(Color.GREEN);
			panelJugador3.setBackground(Color.GREEN);
			panelJugador4.setBackground(Color.GREEN);
			panelJugador5.setBackground(Color.GREEN);
			
			constraints.gridx =0;
			constraints.gridy =0;
			constraints.gridwidth =1;
			constraints.gridheight =1;
			add(panelJugador1,constraints);
			
			constraints.gridx =0;
			constraints.gridy =2;
			constraints.gridwidth =1;
			constraints.gridheight =1;
			add(panelJugador2,constraints);
			
			constraints.gridx =0;
			constraints.gridy =4;
			constraints.gridwidth =2;
			constraints.gridheight =1;
			constraints.fill = constraints.HORIZONTAL;
			add(panelJugador3, constraints);
			
			logo = new JLabel();
			imagen = new ImageIcon(getClass().getResource("/resources/logo1.png"));
			logo.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(180,180, Image.SCALE_DEFAULT)));
			logo.setText("Total Apostado: "+control.getTotalApostado());
			logo.setVerticalTextPosition(SwingConstants.BOTTOM);
			logo.setHorizontalTextPosition(SwingConstants.CENTER);
			constraints.gridx =1;
			constraints.gridy =0;
			constraints.gridwidth =1;
			constraints.gridheight =1;
			constraints.anchor = constraints.CENTER;
			add(logo, constraints);
			
			panelBotones2 = new JPanel();
			panelBotones2.setLayout(new GridLayout(3,1));
			panelBotones2.setBackground(Color.GREEN);
			
			salir = new JButton("Salirse");
			salir.setFont(new Font("Times New Roman", Font.BOLD, 20));
			salir.addMouseListener(escuchas);
			panelBotones2.add(salir);
			
			instrucciones = new JButton("Instrucciones");
			instrucciones.setFont(new Font("Times New Roman", Font.BOLD, 20));
			instrucciones.addMouseListener(escuchas);
			panelBotones2.add(instrucciones);
			
			cederTurno = new JButton("Iniciar de nuevo");
			cederTurno.setPreferredSize(new Dimension(190,30));
			cederTurno.setFont(new Font("Times New Roman", Font.BOLD, 20));
			cederTurno.addMouseListener(escuchas);
			panelBotones2.add(cederTurno);

			constraints.gridx =1;
			constraints.gridy =2;
			constraints.gridwidth =1;
			constraints.gridheight =1;
			constraints.anchor = constraints.CENTER;
			constraints.fill = constraints.CENTER;
			add(panelBotones2,constraints);
			
			constraints.gridx =4;
			constraints.gridy =0;
			constraints.gridwidth =1;
			constraints.gridheight =1;
			add(panelJugador5,constraints);
			
			constraints.gridx =4;
			constraints.gridy =2;
			constraints.gridwidth =1;
			constraints.gridheight =1;
			add(panelJugador4,constraints);

			areaEstado = new JTextArea(15,40);
			areaEstado.setBackground(Color.white);
			areaEstado.setEditable(false);
			JScrollPane scroll = new JScrollPane(areaEstado);
			scroll.setPreferredSize(new Dimension(430,300)); 
			constraints.gridx = 4;
			constraints.gridy = 4;
			constraints.gridheight = 3;
			constraints.gridwidth = 3;
			constraints.anchor = constraints.CENTER;
			add(scroll,constraints);	
		}
		
		/**
		 * Actualizar area estado.
		 * JTextArea del historial del juego
		 * @param apuestaJugador the apuesta jugador
		 * @param nombreJugador the nombre jugador
		 * @param mensaje the mensaje
		 */
		public void actualizarAreaEstado(int apuestaJugador,String nombreJugador, String mensaje) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					areaEstado.append(nombreJugador+mensaje+apuestaJugador+" \n");
				}
			});		
		}
		
		/**
		 * Actualizar mesa apuesta.
		 * Refresca los labels de los hilos despues de apostar
		 * @param apuestaJugador the apuesta jugador
		 * @param nombreJugador the nombre jugador
		 * @param dineroInicial the dinero inicial
		 */
		public void actualizarMesaApuesta(int apuestaJugador,String nombreJugador, String dineroInicial) {
			panelJugador1.refrescarLabels(apuestaJugador, nombreJugador, dineroInicial);
			panelJugador2.refrescarLabels(apuestaJugador, nombreJugador, dineroInicial);
			panelJugador4.refrescarLabels(apuestaJugador, nombreJugador, dineroInicial);
			panelJugador5.refrescarLabels(apuestaJugador, nombreJugador, dineroInicial);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					logo.setText("Total Apostado: "+control.getTotalApostado());
				}
			});
		}
		
		/**
		 * Actualizar mesa cartas.
		 * Refresca las cartas de los hilos despues de descartar
		 * @param nombreJugador the nombre jugador
		 * @param cartasNuevas the cartas nuevas
		 */
		public void actualizarMesaCartas(String nombreJugador,List<Carta> cartasNuevas) {
			panelJugador1.refrescarCartas( nombreJugador, cartasNuevas);
			panelJugador2.refrescarCartas( nombreJugador, cartasNuevas);
			panelJugador4.refrescarCartas( nombreJugador, cartasNuevas);
			panelJugador5.refrescarCartas( nombreJugador, cartasNuevas);
		}
		
		/**
		 * Gets the apuesta usuario.
		 *
		 * @return the apuesta usuario
		 */
		public int getApuestaUsuario() {
			return panelJugador3.getApuestaUsuario();
		}
		
		/**
		 * Gets the panel usuario.
		 *
		 * @return the panel usuario
		 */
		public JPanel getPanelUsuario() {
			return panelJugador3;
		}
		
		/**
		 * Gets the control.
		 * 
		 * @param control the control
		 * @return the control
		 */
		public void getControl(ControlPoker control) {
			this.control = control;
		}

		/**
		 * Mensaje.
		 * Para el JTextArea del historial
		 * @param mensaje the mensaje
		 */
		public void mensaje(String mensaje) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					areaEstado.append(mensaje+"\n");
				}
			});
		}
		
		
		/**
		 * Espaciar.
		 * Para el JTextArea del historial
		 */
		public void espaciar() {
			mensaje("****************************************************************************************");
		}
		
		/**
		 * Reiniciar juego.
		 *  Borro el historial del JTextArea y reinicio los paneles
		 * @param cartas the cartas
		 * @param dineroInicial the dinero inicial
		 */
		public void reiniciarJuego(List<List<Carta>> cartas, List<Integer> dineroInicial) {
			areaEstado.selectAll();
			areaEstado.replaceSelection("");
			panelJugador1.reiniciarJuego(cartas.get(0), dineroInicial.get(0));
			panelJugador2.reiniciarJuego(cartas.get(1), dineroInicial.get(1));
			panelJugador3.reiniciarJuego(cartas.get(2), dineroInicial.get(2));
			panelJugador4.reiniciarJuego(cartas.get(3), dineroInicial.get(3));
			panelJugador5.reiniciarJuego(cartas.get(4), dineroInicial.get(4));
			this.revalidate();
			this.repaint();
		}
		
		/**
		 * The Class Escuchas2.
		 */
		private class Escuchas2 extends MouseAdapter {
			
			/**
			 * Mouse clicked.
			 *
			 * @param event the event
			 */
			public void mouseClicked(MouseEvent event) {
				
				//Salirse del juego
				if(event.getSource() == salir) {
					System.exit(0);
				}
				
				//Volver a iniciar el Juego
				if(event.getSource() == cederTurno) {
					
					control.interrumpirHilos();
					control.reiniciarJuego();		
				}
				
				//Boton para ver las instrucciones del juego
				if (event.getSource() == instrucciones) {
					instrucciones2.setVisible(true);
				}
			}
		}	
}
