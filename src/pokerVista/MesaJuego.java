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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import pokerControl.ControlPoker;
import pokerModelo.Carta;



public class MesaJuego extends JPanel {
	private PanelJugador panelJugador1,panelJugador2,panelJugador3,panelJugador4,panelJugador5;
	private ImageIcon imagen;
	private JLabel logo, tipoRonda,espacio;
	private JButton salir,estadoJuego,instrucciones;
	private JTextArea areaEstado;
	private GridBagConstraints constraints;
	private ControlPoker control;
	private JPanel panelBotones2;
	private Escuchas2 escuchas;
	private Instrucciones instrucciones2;
		public MesaJuego(List<Boolean> isHuman,List<String> nombre, List<List<Carta>> manoJugador, List<Integer> dineroInicial, ControlPoker control) {
			this.control = control;
			this.setBackground(Color.GREEN);
			escuchas = new Escuchas2();
			instrucciones2 = new  Instrucciones();
			initGUI(isHuman,nombre,  manoJugador, dineroInicial);
		}
		
		public void initGUI(List<Boolean> isHuman,List<String> nombre, List<List<Carta>> manoJugador, List<Integer> dineroInicial) {
			this.setLayout(new GridBagLayout());
			constraints = new GridBagConstraints();
			panelJugador1 = new PanelJugador(isHuman.get(0),nombre.get(0),manoJugador.get(0),dineroInicial.get(0), this.control);
			panelJugador2 = new PanelJugador(isHuman.get(1),nombre.get(1),manoJugador.get(1),dineroInicial.get(1), this.control);
			panelJugador3 = new PanelJugador(isHuman.get(2),nombre.get(2),manoJugador.get(2),dineroInicial.get(2), this.control); //Nosotros
			panelJugador4 = new PanelJugador(isHuman.get(3),nombre.get(3),manoJugador.get(3),dineroInicial.get(3), this.control);
			panelJugador5 = new PanelJugador(isHuman.get(4),nombre.get(4),manoJugador.get(4),dineroInicial.get(4), this.control);
			 
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
			constraints.gridx =1;
			constraints.gridy =0;
			constraints.gridwidth =1;
			constraints.gridheight =1;
			constraints.anchor = constraints.CENTER;
			add(logo, constraints);
			
			tipoRonda = new JLabel("Inicia");
			tipoRonda.setFont(new Font("Times New Roman", Font.BOLD, 20));
			constraints.gridx =1;
			constraints.gridy =1;
			constraints.gridwidth =1;
			constraints.gridheight =1;
			constraints.anchor = constraints.CENTER;
			constraints.fill = constraints.CENTER;
			add(tipoRonda, constraints);
			
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
			
			
	
			
			estadoJuego = new JButton("Ver estado el juego");
			estadoJuego.setPreferredSize(new Dimension(150,20));
			constraints.gridx = 4;
			constraints.gridy = 3;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.anchor = constraints.NORTHEAST;
			add(estadoJuego,constraints);

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
			/*
			areaGanadores = new JTextArea(15,40);
			areaGanadores.setEditable(false);
			//JScrollPane scroll2 = new JScrollPane(areaGanadores);
			constraints.gridx = 3;
			constraints.gridy = 4;
			constraints.gridheight = 3;
			constraints.gridwidth = 3;
			constraints.anchor = constraints.CENTER;
			//add(scroll2,constraints);
			*/
			
		}
		
		public void actualizarAreaEstado(int apuestaJugador,String nombreJugador, String mensaje) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					areaEstado.append(nombreJugador+mensaje+apuestaJugador+" \n");
				}
			});
			
			
		}
		public void actualizarMesaApuesta(int apuestaJugador,String nombreJugador, String dineroInicial) {
			
			
			
			
			
			panelJugador1.refrescarLabels(apuestaJugador, nombreJugador, dineroInicial);
			panelJugador2.refrescarLabels(apuestaJugador, nombreJugador, dineroInicial);
			//panelJugador3.refrescarLabels(apuestaJugador, nombreJugador);
			panelJugador4.refrescarLabels(apuestaJugador, nombreJugador, dineroInicial);
			panelJugador5.refrescarLabels(apuestaJugador, nombreJugador, dineroInicial);
		}
		
		public void actualizarMesaCartas(String nombreJugador,List<Carta> cartasNuevas) {
			System.out.print("Entre a mesa");
			panelJugador1.refrescarCartas( nombreJugador, cartasNuevas);
			panelJugador2.refrescarCartas( nombreJugador, cartasNuevas);
			//panelJugador3.refrescarLabels(apuestaJugador, nombreJugador);
			panelJugador4.refrescarCartas( nombreJugador, cartasNuevas);
			panelJugador5.refrescarCartas( nombreJugador, cartasNuevas);
		}
		
		public int getApuestaUsuario() {
			return panelJugador3.getApuestaUsuario();
		}
		
		public JPanel getPanelUsuario() {
			return panelJugador3;
		}
		
		public void getControl(ControlPoker control) {
			this.control = control;
		}

		public void mensaje(String mensaje) {
			areaEstado.append(mensaje+"\n");
		}
		
		public void espaciar() {
			mensaje("****************************************************************************************");
		}
		
		private class Escuchas2 extends MouseAdapter {
			public void mouseClicked(MouseEvent event) {
				
				if(event.getSource() == salir) {
					System.exit(0);
				}
				
				if (event.getSource() == instrucciones) {
					instrucciones2.setVisible(true);

				}
			}
			
			
		}
}
