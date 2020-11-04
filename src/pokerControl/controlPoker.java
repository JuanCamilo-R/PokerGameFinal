package pokerControl;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pokerModelo.Baraja;
import pokerModelo.Carta;
import pokerModelo.JugadorCPU;
import pokerVista.VistaPoker;

public class ControlPoker {
	private Random random;
	private ArrayList<Integer> turnos;
	private int turnoActual = 1;
	private Baraja baraja;
	private List<List<Carta>> manoJugadores;
	
	public ControlPoker() {
		turnos = new ArrayList<Integer>();
		baraja = new Baraja();
		List<Carta> manoPrueba = new ArrayList<Carta>();
		for(int i = 0; i < 4; i++) {
			manoPrueba.add(baraja.getCarta());
		}
		VistaPoker vista = new VistaPoker(true, "Santiago", manoPrueba,5000);
	}
	
	public void asignarCartas() {
		
	}
	
	public void iniciarJugadoresCPU() {
		for(int i = 0; i < 5; i++) {
			turnos.set(i, random.nextInt(4)+1);
		}
		
		JugadorCPU jugador1 = new JugadorCPU(turnos.get(0), 500, "Samuel");
		turnos.remove(0);
		JugadorCPU jugador2 = new JugadorCPU(turnos.get(1), 600, "David");
		turnos.remove(1);
		JugadorCPU jugador3 = new JugadorCPU(turnos.get(2), 400, "Valentina");
		turnos.remove(2);
		JugadorCPU jugador4 = new JugadorCPU(turnos.get(3), 400, "Santiago");
		turnos.remove(3);
		
	}
	
	//Dar cartas iniciales (Se llama en la ronda 1) No se usa para descartar cartas
	public void darCartas(JugadorCPU jugador) {
		ArrayList<Carta> cartasADar = new ArrayList<Carta>();
		for(int i = 0; i < 5; i++) {
			cartasADar.add(baraja.getCarta());
		}
		jugador.recibirCartasIniciales(cartasADar);
	}
	//Descarta y se le da cartas al instante
	public void descartarCartas(JugadorCPU jugador) {
		jugador.descartarCartas();
		//Damos la cantidad de cartas que el jugador descarto
		for(int i = 0; i < jugador.getCantidadADescartar(); i++) {
			jugador.recibirCartas(baraja.getCarta());
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 EventQueue.invokeLater(new Runnable() {
	        	public void run() {
	        		new ControlPoker();
	        	}
	        });
	}
}
