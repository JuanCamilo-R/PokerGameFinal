package pokerModelo;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import pokerModelo.Carta;
import pokerModelo.FileIO;

// TODO: Auto-generated Javadoc
/**
 * The Class Baraja.
 */
public class Baraja {
	   
   	/** The Constant CARTAS_FILE. */
   	public static final String CARTAS_FILE="/resources/cards.png";
	   
   	/** The Constant CARTA_WIDTH. */
   	public static final int CARTA_WIDTH=45;
	   
   	/** The Constant CARTA_HEIGHT. */
   	public static final int CARTA_HEIGHT=60;
	   
   	/** The Constant PALOS. */
   	private static final int PALOS=4;
	   
   	/** The Constant VALORES. */
   	private static final int VALORES=13;
	   
   	/** The Constant CARTA_BACK_INDEX. */
   	private static final int CARTA_BACK_INDEX=PALOS*VALORES;
	   
   	/** The Constant TOTAL_IMAGES. */
   	private static final int TOTAL_IMAGES=PALOS*VALORES+1;
	  
	   /** The mazo. */
   	private ArrayList<Carta> mazo;
	   
   	/** The aleatorio. */
   	private Random aleatorio;
	   
	   /**
   	 * Instantiates a new baraja.
   	 */
   	public Baraja() {
		   aleatorio = new Random();
		   mazo = new ArrayList<Carta>();
		   String valor;
		   for(int i=1;i<=4;i++) {
			   for(int j=2;j<=14;j++) {
				   //Se encarga del valor.
				   switch(j) {
				   case 11: valor="J";break;
				   case 12: valor="Q";break;
				   case 13: valor="K";break;
				   case 14: valor="As";break;
				   default: valor= String.valueOf(j);break;
				   } 
				   //Se encarga de agregar el mazo con el valor y el palo
				   switch(i) {
				   case 1: mazo.add(new Carta(valor,"C"));break;
				   case 2: mazo.add(new Carta(valor,"D"));break;
				   case 3: mazo.add(new Carta(valor,"P"));break;
				   case 4: mazo.add(new Carta(valor,"T"));break;
				   }
			   }
		   }
		   asignarImagen();
	   }
	   
	   /**
   	 * Asignar imagen.
   	 */
   	private void asignarImagen() {   	   
		   BufferedImage cardsImage = FileIO.readImageFile(this, CARTAS_FILE);
			int index = 0;
		    for (int palo = 0; palo < PALOS; palo++) {
		      for (int valor = 0; valor < VALORES; valor++) {
		          int x = valor * CARTA_WIDTH;
		          int y = palo * CARTA_HEIGHT;
		          mazo.get(index).setImagen(cardsImage.getSubimage(x, y, CARTA_WIDTH, CARTA_HEIGHT));
		          index++;
		      } 
		     }		    
	  }
	   
	 /**
   	 * Gets the carta.
   	 *
   	 * @return the carta
   	 */
   	public Carta getCarta() {
		   int index = aleatorio.nextInt(mazoSize());
		   Carta carta = mazo.get(index);
		   mazo.remove(index); //elimina del mazo la carta usada
		   return carta;
	   }
	   
	 /**
   	 * Mazo size.
   	 *
   	 * @return the int
   	 */
   	public int mazoSize() {
		   return mazo.size();
	   }
}
