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
	private ArrayList<String> nombres;
	private JugadorCPU jugador1,jugador2,jugador3,jugador4;
	private List<Boolean> tipoJugador;
	private List<Integer> dinero;
	
	public ControlPoker() {
		turnos = new ArrayList<Integer>();
		nombres = new ArrayList<String>();
		tipoJugador = new ArrayList<Boolean>();
		dinero = new ArrayList<Integer>();
		baraja = new Baraja();
		manoJugadores = new ArrayList<List<Carta>>();
		iniciarJuego();
		
		VistaPoker vista = new VistaPoker(tipoJugador,nombres, manoJugadores,dinero);
	}
	
	private void iniciarJuego() {
		baraja = new Baraja();
		for(int i = 0; i < 5 ;i++) {
			manoJugadores.add(seleccionarCartas());
		}
		iniciarJugadoresCPU();
		darTipo();
		agregarNombres();
		darTipo();
		darDinero();
	}
	
	private void agregarNombres() {
			

			nombres.add(jugador1.getNombre());
			nombres.add(jugador2.getNombre());
			nombres.add("Santiago");
			nombres.add(jugador3.getNombre());
			nombres.add(jugador4.getNombre());
	
		
	}
	
	private void darTipo() {
		
			tipoJugador.add(false);
			tipoJugador.add(false);
			tipoJugador.add(true);
			tipoJugador.add(false);
			tipoJugador.add(false);
		
		
	}
	
	private void darDinero() {
		


		
			dinero.add(jugador1.getDineroInicial());
			dinero.add(jugador2.getDineroInicial());
			dinero.add(500);
			dinero.add(jugador3.getDineroInicial());
			dinero.add(jugador4.getDineroInicial());
	
		
	}
	
	
	
	private ArrayList<Carta> seleccionarCartas() {
		// TODO Auto-generated method stub
		ArrayList<Carta> manoJugador = new ArrayList<Carta>();
		//se dan 2 cartas al jugador
		manoJugador.add(baraja.getCarta());
		manoJugador.add(baraja.getCarta());
		manoJugador.add(baraja.getCarta());
		manoJugador.add(baraja.getCarta());
		manoJugador.add(baraja.getCarta());
		return manoJugador;
	}
	
	
	public void asignarCartas() {
		
	}
	
	public void iniciarJugadoresCPU() {
		/*for(int i = 0; i < 5; i++) {
			turnos.set(i, random.nextInt(4)+1);
		}*/
		
		/*JugadorCPU */jugador1 = new JugadorCPU(/*turnos.get(0)*/1, 500, "Samuel");
		//turnos.remove(0);
		/*JugadorCPU */jugador2 = new JugadorCPU(/*turnos.get(1)*/2, 600, "David");
		//turnos.remove(1);
		/*JugadorCPU */jugador3 = new JugadorCPU(/*turnos.get(2)*/3, 400, "Valentina");
		//turnos.remove(2);
		/*JugadorCPU */jugador4 = new JugadorCPU(/*turnos.get(3)*/4, 400, "Santiago");
		//turnos.remove(3);
		
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
