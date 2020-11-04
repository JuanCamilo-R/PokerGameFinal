package pokerModelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JugadorCPU implements Runnable {
	private String nombreJugador;
	private int turno, dineroApuesta, dineroInicial;
	private Random random;
	private boolean ronda = false; //True = Ronda de descarte // False = Ronda de apuesta
	private int cantidadADescartar;
	private List<Carta> cartas = new ArrayList<Carta>();
	
	
	public JugadorCPU(int turno, int dineroInicial, String nombreJugador) {
		this.turno = turno;
		this.dineroInicial = dineroInicial;
		this.nombreJugador = nombreJugador;
		random = new Random();
	}
	
	public void recibirCartasIniciales(List<Carta> cartasRecibidas) {
		cartas = cartasRecibidas;
	}
	
	public void descartarCartas() {
		for(int i = 0; i < cantidadADescartar; i++) {
			cartas.remove(i);
		}	
	}
	
	public void recibirCartas(Carta cartaRecibida) {
		cartas.add(cartaRecibida);
	}
	
	public int getCantidadADescartar() {
		return cantidadADescartar;
	}
	
	public String getNombre() {
		return nombreJugador;
	}
	public int getDineroInicial() {
		return dineroInicial;
	}
	
	public List<Carta>  getCartas (){
		return cartas;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(ronda) {
			cantidadADescartar = random.nextInt(6);
		}
		
		
	}

}
