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
	private boolean interrumpido;
	private int paso = 1;
	private int contador;
	private boolean corriendo=false;
	
	public JugadorCPU( int dineroInicial, String nombreJugador,int cantidadADescartar, ControlPoker control) {
		control.naceHilo();
		this.dineroInicial = dineroInicial;
		this.nombreJugador = nombreJugador;
		this.cantidadADescartar = cantidadADescartar;
		random = new Random();
		this.control = control;
		interrumpido=false;
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
	
	public boolean apostar(int cantidad) {
		if(dineroInicial>= cantidad) {
			vecesApuesta++;
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
		System.out.println(nombreJugador+ " he sido interrumpido");
		interrumpido = true;
		System.out.println("el interrumpido de "+nombreJugador+" quedó en "+interrumpido);
		if(!corriendo) {
			System.out.println(nombreJugador+" está disponible");
		}
		else {
			System.out.println(nombreJugador+" aún no está disponible");
		}
		if(control.getControlador() <= 2 && !this.interrumpido)
		{
			System.out.println(nombreJugador+ " sí debería continuar");
		}
		else {
			System.out.println(nombreJugador+ " no debería continuar");
		}
	}
	
	public boolean getInterrumpido() {
		return interrumpido;
	}
	public void iniciarRondaDescarte() {
		System.out.println("He sido despertado para ronda de descartes - "+nombreJugador);
		paso = 2;
	}
	
	public void reiniciarApuesta() {
		apuestaActual=100;
	}
	
	public void iniciarRondaApuesta() {
		paso = 1;
		apuestaActual = 100;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(control.getControlador() <= 2 && !interrumpido) {
			//System.out.println(nombreJugador+" sigue vivo");
			switch(paso) {
				case 1:
					System.out.println(nombreJugador+" se ocupa");
					//Apuesta
					
				control.turnos(turno, nombreJugador, apuestaActual,getDineroInicial());	
				paso = 0;
				if(turno == 5 ) {
					
					System.out.println("Activo ronda de descarte \n");
					control.activarRondaDescarte();
				}
				System.out.println(nombreJugador+" se desocupa");
				break;
				case 2:
					System.out.println(nombreJugador+" se ocupa");
					corriendo=true;
					//Descarte	
					control.turnos(turno, nombreJugador, cantidadADescartar, getDineroInicial());	
					paso = 0;
					//System.out.println(nombreJugador+" TURNO: "+turno);
					if(turno == 5) {
						System.out.println(nombreJugador+" activo ronda de apuesta again");
						control.activarRondaApuestas();
					}
					corriendo=false;
					System.out.println(nombreJugador+" se desocupa");
					break;
		}
			if(interrumpido) {
				break;
			}
	}
		System.out.print(nombreJugador+" murio \n");
		control.muereHilo();

 }
}
