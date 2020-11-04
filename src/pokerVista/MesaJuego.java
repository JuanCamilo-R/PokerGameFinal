package pokerVista;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JPanel;

import pokerModelo.Carta;

public class MesaJuego extends JPanel {
	private PanelJugador panelJugador1,panelJugador2,panelJugador3,panelJugador4,panelJugador5;
	
	private GridBagConstraints constraints;
	
		public MesaJuego(boolean isHuman, String nombre, List<List<Carta>> manoJugador, int dineroInicial) {
			
			initGUI(isHuman,nombre,  manoJugador, dineroInicial);
		}
		
		public void initGUI(boolean isHuman, String nombre, List<List<Carta>> manoJugador, int dineroInicial) {
			this.setLayout(new GridBagLayout());
			constraints = new GridBagConstraints();
			panelJugador1 = new PanelJugador(true,nombre,manoJugador.get(0),dineroInicial);
			panelJugador2 = new PanelJugador(false,"J",manoJugador.get(1),dineroInicial);
			/*panelJugador3 = new PanelJugador(isHuman,nombre,manoJugador,dineroInicial);
			panelJugador4 = new PanelJugador(isHuman,nombre,manoJugador,dineroInicial);
			panelJugador5 = new PanelJugador(isHuman,nombre,manoJugador,dineroInicial);
			*/
			
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
			/*
			constraints.gridx =0;
			constraints.gridy =5;
			constraints.gridwidth =2;
			constraints.gridheight =1;
			constraints.fill = constraints.HORIZONTAL;
			add(panelJugador3);*/
			
		}
}
