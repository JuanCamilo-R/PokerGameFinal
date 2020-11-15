package pokerVista;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.prism.paint.Color;


public class Instrucciones extends JFrame {
	private Escucha escucha;
	private JButton aceptar;
	private JPanel informacion;
	private JLabel textoQuinto,descarte,apuesta,textoInicial,textoSegundo ,textoTercero,textoCuarta,textoSexto,textoSeptimo;
	private ImageIcon imagen;
	public Instrucciones() {
		this.setVisible(true);
		this.setTitle("Instrucciones");
		this.setResizable(false);
		this.setVisible(false);
		this.setBackground(java.awt.Color.green);
		initGUI();
		pack();
		this.setLocationRelativeTo(null);
		//this.getBackground();
	
	}
	
	public void initGUI() {
		this.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints contraints = new GridBagConstraints();
		getContentPane().setBackground(java.awt.Color.green);
		setBackground(java.awt.Color.green);
		//Texto de significado imagen de agua
		textoInicial = new JLabel("1.Apostamos antes de entrar al juego (100)");
		textoInicial.setFont(new Font("Times New Roman", Font.BOLD, 20));
		contraints.gridx=0;
		contraints.gridy=0;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.WEST;
		add(textoInicial,contraints);
		
		//Texto de significado imagen de tocado
		textoSegundo = new JLabel("2.Repartimos a todos las cartas(5 cartas)");
		textoSegundo.setFont(new Font("Times New Roman", Font.BOLD, 20));
		contraints.gridx=0;
		contraints.gridy=1;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.WEST;
		add(textoSegundo,contraints);
		

		//Texto de significado imagen de tocado
		textoTercero = new JLabel("3.Empieza ronda de apuesta, podemos apostar con estos botones y finalizamos dando en confirmar");
		textoTercero.setFont(new Font("Times New Roman", Font.BOLD, 20));
		contraints.gridx=0;
		contraints.gridy=3;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.WEST;
		add(textoTercero,contraints);
		
		//Ponemos la imagen de hundido
		imagen = new ImageIcon("src/resources/imagenApuesta.png");//Darle la direccion de la imagen
		apuesta = new JLabel(imagen);//Puede recibir un objeto de tipo ImageIcon
		apuesta.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(200, 100,0)));
		contraints.gridx=0;
		contraints.gridy=4;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.CENTER;
		add(apuesta,contraints);
		
		
		textoCuarta = new JLabel("4.Empieza ronda de descarte, podemos descartar dando click sobre las cartas y finalizamos dando en confirmar");
		textoCuarta.setFont(new Font("Times New Roman", Font.BOLD, 20));
		contraints.gridx=0;
		contraints.gridy=5;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.WEST;
		add(textoCuarta,contraints);
		
		imagen = new ImageIcon("src/resources/imagnesDescarte.png");//Darle la direccion de la imagen
		descarte = new JLabel(imagen);//Puede recibir un objeto de tipo ImageIcon
		descarte.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(200, 100,0)));
		contraints.gridx=0;
		contraints.gridy=6;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.CENTER;
		add(descarte,contraints);
		
		textoQuinto = new JLabel("5. Empieza la segunda ronda de apuesta y se finaliza escogiendo la mejor jugada");
		textoQuinto.setFont(new Font("Times New Roman", Font.BOLD, 20));
		contraints.gridx=0;
		contraints.gridy=7;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.WEST;
		add(textoQuinto,contraints);
		
		textoSexto = new JLabel("Notas:");
		textoSexto.setFont(new Font("Times New Roman", Font.BOLD, 20));
		contraints.gridx=0;
		contraints.gridy=8;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.WEST;
		add(textoSexto,contraints);
		
		textoSeptimo = new JLabel("Siempre se deben igualar las apuestas, si hay empate se escoge la carta mas alta");
		textoSeptimo.setFont(new Font("Times New Roman", Font.BOLD, 20));
		contraints.gridx=0;
		contraints.gridy=9;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.WEST;
		add(textoSeptimo,contraints);


		escucha = new Escucha();
		aceptar = new JButton("Aceptar");
		aceptar.addMouseListener(escucha);
		contraints.gridx=0;
		contraints.gridy=10;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.fill= GridBagConstraints.CENTER;
		contraints.anchor = GridBagConstraints.CENTER;
		add(aceptar,contraints);
	}
	
	public class Escucha extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			if(event.getSource() == aceptar) {
				setVisible(false);
			}
		}
	}
}
