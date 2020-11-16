package pokerModelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pokerControl.ControlPoker;
import pokerVista.PanelJugador;

public class JugadorCPU implements Runnable {
	private String nombreJugador;
	private int turno, dineroApuesta, dineroInicial, apuestaActual;
	private Random random;
	private ControlPoker control;
	private boolean ronda = false; //True = Ronda de descarte // False = Ronda de apuesta
	private int cantidadADescartar;
	private List<Carta> cartas = new ArrayList<Carta>();
	private int jugada;
	private int vecesApuesta = 0;
	private boolean interrumpido = false;
	private int paso = 1;
	private int contador;
	
	public JugadorCPU( int dineroInicial, String nombreJugador,int cantidadADescartar, ControlPoker control) {
		
		this.dineroInicial = dineroInicial;
		this.nombreJugador = nombreJugador;
		this.cantidadADescartar = cantidadADescartar;
		random = new Random();
		this.control = control;
		apostar(100);
	}
	
	public int getApuestaActual() {
		return apuestaActual;
	}
	public void recibirCartasIniciales(List<Carta> cartasRecibidas) {
		cartas = cartasRecibidas;
	}
	
	public void descartarCartas() {
		for(int i = 0; i < cantidadADescartar; i++) {
			cartas.remove(0);
			//System.out.print("Cantidad"+cantidadADescartar+"   ");
			//System.out.print("Valor"+cartas.get(0).getValor()+"   ");
			
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
	
	public void setTurno(int turno) {
		this.turno = turno;
	}
	
	public int getTurno() {
		return turno;
	}
	
	public int getVecesApostado() {
		return vecesApuesta;
	}
	public void reiniciarApuesta() {
		apuestaActual = 0;
	}
	public boolean apostar(int cantidad) {
		if(dineroInicial>= cantidad) {
			vecesApuesta++;
			System.out.println(nombreJugador+ " apuesta "+cantidad+" en su objeto");
			//System.out.println(nombreJugador+" apuesta veces: "+vecesApuesta);
			apuestaActual = cantidad;
			dineroApuesta=cantidad;
			dineroInicial -=cantidad;
			return true;
		}
		return false;
	}
	
	public void devolverApuesta(int cantidadDevuelta) {
		dineroInicial += cantidadDevuelta;
	}
	
	public void interrumpir() {
		interrumpido = true;
	}
	
	public boolean getInterrumpido() {
		return interrumpido;
	}
	public void iniciarRondaDescarte() {
		paso = 2;
	}
	
	public void iniciarRondaApuesta() {
		paso = 1;
		apuestaActual = 100;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(control.getControlador() <= 2 && !this.interrumpido) {
			switch(paso) {
				case 1:
					//Apuesta
					
				control.turnos(turno, nombreJugador, apuestaActual,getDineroInicial());
					
					
					
				paso = 0;
				if(turno == 5 ) {
					
					System.out.println(nombreJugador+" activo ronda de descarte \n");
					control.activarRondaDescarte();
				}
				break;
				case 2:
					//Descarte
					
					control.turnos(turno, nombreJugador, cantidadADescartar, getDineroInicial());
					
					paso = 0;
					//System.out.println(nombreJugador+" TURNO: "+turno);
					if(turno == 5) {
						System.out.println(nombreJugador+" activo ronda de apuesta again");
						control.activarRondaApuestas();
					}
					break;
		}
	}
		System.out.print(nombreJugador+" murio \n");

 }
}
