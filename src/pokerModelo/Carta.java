package pokerModelo;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Carta extends JLabel {
	 private String valor;
	 private String palo;
	 private BufferedImage imagen;
	 private int valorNumerico;

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
	
	 public int getValorNumerico() {
		 return valorNumerico;
	 }

	 public String getValor() {
		 return valor;
	 }

	public void setValor(String valor) {
		 this.valor = valor;
	 }

	 public String getPalo() {
		 return palo;
	 }

	 public void setPalo(String palo) {
		 this.palo = palo;
	 }

	 public String toString() {
		 return valor+palo;
	 }

	 private Image scaledImage(Image img, int w, int h) {
		 BufferedImage resizedImage = new BufferedImage( w , h , BufferedImage.TYPE_INT_RGB);
		 Graphics2D g2 = resizedImage.createGraphics();
		 g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		 g2.drawImage(img, 0, 0, w, h, null);
		 g2.dispose();
		 return resizedImage;
	 }
	 public void setImagen(BufferedImage imagen) {
		 this.imagen= (BufferedImage) scaledImage(imagen,100,50);
		 setIcon(new ImageIcon(scaledImage(imagen,80,110)));
	 }
	 
	 public BufferedImage getImagen() {
		return imagen;
	}
}
