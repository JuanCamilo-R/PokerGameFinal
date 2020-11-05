package pokerVista;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pokerModelo.Carta;

public class MesaJuego extends JPanel {
	private PanelJugador panelJugador1,panelJugador2,panelJugador3,panelJugador4,panelJugador5;
	private ImageIcon imagen;
	private JLabel logo;
	
	private GridBagConstraints constraints;
	
		public MesaJuego(boolean isHuman, String nombre, List<List<Carta>> manoJugador, int dineroInicial) {
			
			initGUI(isHuman,nombre,  manoJugador, dineroInicial);
		}
		
		public void initGUI(boolean isHuman, String nombre, List<List<Carta>> manoJugador, int dineroInicial) {
			this.setLayout(new GridBagLayout());
			constraints = new GridBagConstraints();
			panelJugador1 = new PanelJugador(false,nombre,manoJugador.get(0),dineroInicial);
			panelJugador2 = new PanelJugador(false,"J",manoJugador.get(1),dineroInicial);
			panelJugador3 = new PanelJugador(true,"L",manoJugador.get(2),dineroInicial); //Nosotros
			panelJugador4 = new PanelJugador(false,"M",manoJugador.get(3),dineroInicial);
			panelJugador5 = new PanelJugador(false,"O",manoJugador.get(4),dineroInicial);
			
			
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
			imagen = new ImageIcon(getClass().getResource("/resources/logo.png"));
			logo.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)));
			constraints.gridx =2;
			constraints.gridy =0;
			constraints.gridwidth =1;
			constraints.gridheight =1;
			constraints.fill = constraints.WEST;
			add(logo, constraints);
			
		}
}
