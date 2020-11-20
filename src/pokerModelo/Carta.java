package pokerModelo;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

// TODO: Auto-generated Javadoc
/**
 * The Class Carta.
 */
public class Carta extends JLabel {
	 
 	/** The valor. */
 	private String valor;
	 
 	/** The palo. */
 	private String palo;
	 
 	/** The imagen. */
 	private BufferedImage imagen;
	 
 	/** The valor numerico. */
 	private int valorNumerico;

	/**
	 * Instantiates a new carta.
	 *
	 * @param valor the valor
	 * @param palo the palo
	 */
	public Carta(String valor, String palo) {
		 this.valor = valor;
		 this.palo = palo;

		 switch(valor) {
		   case "J": valorNumerico=11;break;
		   case "Q": valorNumerico=12;break;
		   case "K": valorNumerico=13;break;
		   case "As": valorNumerico=14;break;
		   default: valorNumerico = Integer.parseInt(valor);break;
		   } 
	 }
	
	 /**
 	 * Gets the valor numerico.
 	 *
 	 * @return the valor numerico
 	 */
 	public int getValorNumerico() {
		 return valorNumerico;
	 }

	 /**
 	 * Gets the valor.
 	 *
 	 * @return the valor
 	 */
 	public String getValor() {
		 return valor;
	 }

	/**
	 * Sets the valor.
	 *
	 * @param valor the new valor
	 */
	public void setValor(String valor) {
		 this.valor = valor;
	 }

	 /**
 	 * Gets the palo.
 	 *
 	 * @return the palo
 	 */
 	public String getPalo() {
		 return palo;
	 }

	 /**
 	 * Sets the palo.
 	 *
 	 * @param palo the new palo
 	 */
 	public void setPalo(String palo) {
		 this.palo = palo;
	 }

	 /**
 	 * To string.
 	 *
 	 * @return the string
 	 */
 	public String toString() {
		 return valor+palo;
	 }

	 /**
 	 * Scaled image.
 	 *	Funcion que escala las imagenes de las cartas
 	 * @param img the img
 	 * @param w the w
 	 * @param h the h
 	 * @return the image
 	 */
 	private Image scaledImage(Image img, int w, int h) {
		 BufferedImage resizedImage = new BufferedImage( w , h , BufferedImage.TYPE_INT_RGB);
		 //Se crea grafica 2d para renderizar y escalar la imagen.
		 Graphics2D g2 = resizedImage.createGraphics();
		 //Se renderiza y se dibuja.
		 g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		 g2.drawImage(img, 0, 0, w, h, null);
		 //Libero los recursos nativos a la grafica
		 g2.dispose();
		 return resizedImage;
	 }
	 
 	/**
 	 * Sets the imagen.
 	 *
 	 * @param imagen the new imagen
 	 */
 	public void setImagen(BufferedImage imagen) {
		 this.imagen= (BufferedImage) scaledImage(imagen,100,50);
		 setIcon(new ImageIcon(scaledImage(imagen,80,110)));
	 }
	 
	 /**
 	 * Gets the imagen.
 	 *
 	 * @return the imagen
 	 */
 	public BufferedImage getImagen() {
		return imagen;
	}
}
