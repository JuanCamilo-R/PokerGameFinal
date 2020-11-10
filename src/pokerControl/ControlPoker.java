package pokerControl;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;

import javafx.util.Pair;
import pokerModelo.Baraja;
import pokerModelo.Carta;
import pokerModelo.JugadorCPU;
import pokerVista.MesaJuego;
import pokerVista.PanelJugador;
import pokerVista.VistaPoker;

public class ControlPoker {
	private Random random;
	private ArrayList<Integer> turnos;
	private int turnoActual = 1;
	private int apuestaUsuario;
	private Baraja baraja;
	private List<List<Carta>> manoJugadores;
	private ArrayList<String> nombres;
	VistaPoker vista;
	private MesaJuego mesaJuego;
	private PanelJugador panelUsuario;
	private JugadorCPU jugador1,jugador2,jugador4,jugador5;
	private List<Boolean> tipoJugador;
	private List<Integer> dinero;
	private int[] descarte;
	private Lock bloqueo = new ReentrantLock(); //manejo de sincronizacion
	private Condition esperarTurno = bloqueo.newCondition(); //manejo de sincronizacion
	private boolean tipoRonda = true; //True si es el momento de apostar y false si es el momento de descartar
	private ArrayList<JugadorCPU> jugadoresCPU;
	public ControlPoker() {
		turnos = new ArrayList<Integer>();
		nombres = new ArrayList<String>();
		random = new Random();
		jugadoresCPU = new ArrayList<JugadorCPU>();
		tipoJugador = new ArrayList<Boolean>();
		dinero = new ArrayList<Integer>();
		baraja = new Baraja();
		descarte = new int[5];
		manoJugadores = new ArrayList<List<Carta>>();
		iniciarJuego();
		vista = new VistaPoker(tipoJugador,nombres, manoJugadores,dinero);
		this.mesaJuego = vista.getMesaJuego();
		panelUsuario = (PanelJugador) mesaJuego.getPanelUsuario();

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
			nombres.add(jugador4.getNombre());
			nombres.add(jugador5.getNombre());
	
		
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
			dinero.add(jugador5.getDineroInicial());
			dinero.add(jugador4.getDineroInicial());
	
		
	}
	
	
	
	private ArrayList<Carta> seleccionarCartas() {
		// TODO Auto-generated method stub
		ArrayList<Carta> manoJugador = new ArrayList<Carta>();
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
		
		int aux = random.nextInt(5)+1;
		/*JugadorCPU */jugador1 = new JugadorCPU(500, "Samuel", 1, this);
		/*JugadorCPU */jugador2 = new JugadorCPU(600, "David", 2, this);
		/*JugadorCPU */jugador4 = new JugadorCPU(400, "Valentina", 3, this);
		/*JugadorCPU */jugador5 = new JugadorCPU(400, "Santiago", 4, this);
		switch(aux) {
			case 1:
				jugador1.setTurno(1);
				jugador2.setTurno(2);
				jugador4.setTurno(4);
				jugador5.setTurno(5);
				break;
			case 2:
				jugador2.setTurno(1);
				jugador4.setTurno(3);
				jugador5.setTurno(4);
				jugador1.setTurno(5);
				break;
			case 3:
				System.out.print("Inicias");
				jugador4.setTurno(2);
				jugador5.setTurno(3);
				jugador1.setTurno(4);
				jugador2.setTurno(5);
				break;
			case 4:
				jugador4.setTurno(1);
				jugador5.setTurno(2);
				jugador1.setTurno(3);
				jugador2.setTurno(4);
				break;
			case 5:
				jugador5.setTurno(1);
				jugador1.setTurno(2);
				jugador2.setTurno(3);
				jugador4.setTurno(5);
		}
			jugadoresCPU.add(jugador1);
			jugadoresCPU.add(jugador2);
			jugadoresCPU.add(jugador4);
			jugadoresCPU.add(jugador5);
			
	      ExecutorService ejecutorSubprocesos = Executors.newCachedThreadPool();
		  ejecutorSubprocesos.execute(jugador1); 
		  ejecutorSubprocesos.execute(jugador2);
		  ejecutorSubprocesos.execute(jugador4);
		  ejecutorSubprocesos.execute(jugador5);
		  
		  ejecutorSubprocesos.shutdown();
		
	}
	
	public void turnos(int turnoJugador, int cartasPedidas) {
		
		bloqueo.lock();
		
		try
		{
			
			while(turnoJugador != turnoActual) {
				System.out.println("Intento entrar pero se duerme");
				esperarTurno.await();
			}
			
			if(tipoRonda) { //Ronda de apuesta
				for(int i = 0; i < 5; i++) {
					if(jugadoresCPU.get(i).getTurno() == turnoActual) {
						if(jugadoresCPU.get(i).apostar(100)) {
							System.out.println("Entro hilo: "+jugadoresCPU.get(i).getNombre());
							vista.actualizarVistaApuesta(100, jugadoresCPU.get(i).getNombre());
							vista.funcionPrueba();
							turnoActual++;
							esperarTurno.signalAll();
						}
					}
				}
			} else { //Ronda de descarte
				int posicionDescarte = turnoActual - 1;
				if(posicionDescarte != 2) {
					for(int i = 0; i < 5; i++) {
						if(jugadoresCPU.get(i).getTurno() == turnoActual)
						{
							jugadoresCPU.get(i).descartarCartas();
							turnoActual++;
							esperarTurno.signalAll();
						}
					}
					descarte[posicionDescarte] = cartasPedidas;
				}
			}
		}catch(Exception e) {
			
		}finally {
			bloqueo.unlock();
			if(turnoActual == 6) {
				if(tipoRonda == false) { //Ronda de descarte
					tipoRonda = true;
					turnoActual = 1;
				}
				if(tipoRonda) { //Ronda de apuesta
					tipoRonda = false;
				}
			}
		}
		
	}
	
	public void darCartas(JugadorCPU jugador, int cartasADar) {
		for(int i = 0; i < cartasADar; i++ ) {
			jugador.recibirCartas(baraja.getCarta());
		}
	}
	
	//Dar cartas iniciales (Se llama en la ronda 1) No se usa para descartar cartas
	public void darCartasIniciales(JugadorCPU jugador) {
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
	
	public void obtenerApuestaUsuario(PanelJugador jugadorUsuario) {
		apuestaUsuario = vista.darApuestaUsuario();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 EventQueue.invokeLater(new Runnable() {
	        	public void run() {
	        		new ControlPoker();
	        		
	        	}
	        });
	}
	//Verificar que todas las cartas tengan el mismo color
	public boolean verificarColor(List<Carta> manoJugador) { //True si todas tienen el mismo color, false si no
		for(int i = 0; i < manoJugador.size()-1; i++) {
			if(manoJugador.get(i).getPalo() != manoJugador.get(i+1).getPalo()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean escaleraRealColor(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenado;
		int aux = 10;
		if(verificarColor(manoJugador)) {
			manoJugadorOrdenado = ordenarPorNumero(manoJugador);
			for(int i = 0; i < manoJugadorOrdenado.size(); i++) {
				if(manoJugadorOrdenado.get(i).getValorNumerico() != aux) {
					return false;
				}else {
					aux++;
				}
			}
			return true;
		}else {
			return false;
		}
	}
	
	public boolean escaleraColor(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenado;
		if(verificarColor(manoJugador)) {
			manoJugadorOrdenado = ordenarPorNumero(manoJugador);
			int aux = manoJugadorOrdenado.get(0).getValorNumerico();
			for(int i = 0; i < manoJugadorOrdenado.size(); i++) {
				if(aux != manoJugadorOrdenado.get(i).getValorNumerico()) {
					return false;
				}else {
					aux++;
				}
			}
			return true;
		}
		
		return false;
	}
	
	public boolean poker(List<Carta> manoJugador) {
		int contador = 0;
		List<Carta> manoJugadorOrdenado;
		manoJugadorOrdenado = ordenarPorNumero(manoJugador);
		for(int i = 0; i < manoJugadorOrdenado.size(); i++) {
			if(manoJugadorOrdenado.get(i).getValorNumerico() == manoJugadorOrdenado.get(i+1).getValorNumerico()) {
				if(i < 3) {
					contador++;
				}
			}else {
				for(int j = 1; j < manoJugadorOrdenado.size()-1;j++) {
					if(manoJugadorOrdenado.get(j).getValorNumerico() == manoJugadorOrdenado.get(j+1).getValorNumerico()) {
						contador++;
					}
				}
				break;
			}
		}
		if(contador == 3) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	public boolean full(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenado;
		manoJugadorOrdenado = ordenarPorNumero(manoJugador);
		if(manoJugadorOrdenado.get(0).getValorNumerico() == manoJugadorOrdenado.get(1).getValorNumerico()) {
			if(manoJugadorOrdenado.get(2).getValorNumerico() == manoJugadorOrdenado.get(3).getValorNumerico() && manoJugadorOrdenado.get(3).getValorNumerico() == manoJugadorOrdenado.get(4).getValorNumerico()) {
				return true;
			}
		}else if (manoJugadorOrdenado.get(0).getValorNumerico() == manoJugadorOrdenado.get(1).getValorNumerico() && manoJugadorOrdenado.get(1).getValorNumerico() == manoJugadorOrdenado.get(2).getValorNumerico()) {
			if(manoJugadorOrdenado.get(3).getValorNumerico() == manoJugadorOrdenado.get(4).getValorNumerico()) {
				return true;
			}
		}
		return false;
	}
	
	
	
	public List<Carta> ordenarPorNumero(List<Carta> manoJugador) {
	        Carta temp;
	        for(int i=1;i < manoJugador.size();i++){
	            for (int j=0 ; j < manoJugador.size() - 1; j++){
	                if (manoJugador.get(j).getValorNumerico() > manoJugador.get(j+1).getValorNumerico()){
	                    temp = manoJugador.get(j);
	                    manoJugador.set(j, manoJugador.get(j+1));
	                    manoJugador.set(j+1,temp);
	                }
	            }
	        }
	        return manoJugador;
	}
	
	public boolean escalera(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenada;
		manoJugadorOrdenada = ordenarPorNumero(manoJugador);
		if(manoJugadorOrdenada.get(0).getValorNumerico() + 1 == manoJugadorOrdenada.get(1).getValorNumerico() ) {
			if(manoJugadorOrdenada.get(1).getValorNumerico() + 1 == manoJugadorOrdenada.get(2).getValorNumerico()) {
				if(manoJugadorOrdenada.get(2).getValorNumerico() + 1 == manoJugadorOrdenada.get(3).getValorNumerico()) {
					if(manoJugadorOrdenada.get(3).getValorNumerico() + 1 == manoJugadorOrdenada.get(4).getValorNumerico()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean trio(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenada;
		manoJugadorOrdenada = ordenarPorNumero(manoJugador);
		for(int i=0;i<3;i++) {
			if(manoJugadorOrdenada.get(i).getValorNumerico()==manoJugadorOrdenada.get(i+1).getValorNumerico() &&
					manoJugadorOrdenada.get(i+1).getValorNumerico() ==manoJugadorOrdenada.get(i+2).getValorNumerico()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean doblePareja(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenada;
		manoJugadorOrdenada = ordenarPorNumero(manoJugador);
		if(manoJugadorOrdenada.get(0).getValorNumerico() == manoJugadorOrdenada.get(1).getValorNumerico()
		   && manoJugadorOrdenada.get(3).getValorNumerico() == manoJugadorOrdenada.get(4).getValorNumerico() ) {
			return true;
		}else if(manoJugadorOrdenada.get(1).getValorNumerico() == manoJugadorOrdenada.get(2).getValorNumerico()
				&& manoJugadorOrdenada.get(3).getValorNumerico() == manoJugadorOrdenada.get(4).getValorNumerico()) {
			return true;
		}else if(manoJugadorOrdenada.get(0).getValorNumerico() == manoJugadorOrdenada.get(1).getValorNumerico() 
				&& manoJugadorOrdenada.get(2).getValorNumerico() == manoJugadorOrdenada.get(3).getValorNumerico()) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean pareja(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenada;
		manoJugadorOrdenada = ordenarPorNumero(manoJugador);
		for(int i = 0; i < 4 ; i++) {
			if(manoJugadorOrdenada.get(i).getValorNumerico() == manoJugadorOrdenada.get(i+1).getValorNumerico()) {
				return true;
			}
		}
		return false;
	}
	
	public Carta cartaAlta(List<Carta> manoJugador) {
		List<Carta> manoJugadorOrdenada;
		manoJugadorOrdenada = ordenarPorNumero(manoJugador);
		return manoJugadorOrdenada.get(4);
	}
	/*
	 * Atributo jugada de jugadorCPU
	 * 10 = Escalera real de color.
	 * 9 = Escalera de color
	 * 8 = Poker
	 * 7 = Full
	 * 6 = Color
	 * 5 = Escalera
	 * 4 = Trio
	 * 3 = Doble pareja
	 * 2 = Pareja
	 * 1 = Carta mas alta
	 */
	
	//Recibe una mano de jugador
	public int determinarJugada(List<Carta> manoJugador) {
		if(escaleraRealColor(manoJugador)) {
			return 10;
		} else if(escaleraColor(manoJugador)) {
			return 9;
		} else if(poker(manoJugador)) {
			return 8;
		}else if(full(manoJugador)) {
			return 7;
		} else if(verificarColor(manoJugador)) {
			return 6;
		}else if(escalera(manoJugador)) {
			return 5;
		} else if(trio(manoJugador)) {
			return 4;
		} else if(doblePareja(manoJugador)) {
			return 3;
		} else if(pareja(manoJugador)) {
			return 2;
		}else {
			return 1;
		}
	}
	
	public void determinarGanador() {
		//La mano de cada jugador se empareja con una jugada respectiva
		List<Pair<Integer, List<Carta>>> parejaJugadasManos = new ArrayList<Pair<Integer, List<Carta>>>();
		List<Carta> manoJugadaOrdenada = new ArrayList<Carta>();
		
		for(int i = 0; i < 5; i++) {
			manoJugadaOrdenada.clear();
			manoJugadaOrdenada = ordenarPorNumero(manoJugadores.get(i));
			parejaJugadasManos.add(new Pair<Integer, List<Carta>>(determinarJugada(manoJugadores.get(i)), manoJugadaOrdenada));
		}
		
		parejaJugadasManos = ordenarParejas(parejaJugadasManos);
		
		
	}
	//Recibimos una parejaJugadasManos ordenados de menor a mayor
	public boolean checkRepeatPlay(List<Pair<Integer, List<Carta>>> parejaJugadasManos) {
		List<Integer> jugadas = new ArrayList<Integer>();
		for(int i = 0; i < 5; i++) {
			jugadas.add(parejaJugadasManos.get(i).getKey());
		}
		if(jugadas.get(4) == jugadas.get(3)) {
			return true;
		}
		return false;
	}
	//Ordenamos parejas de menor a mayor dependiendo de su jugada final
	public List<Pair<Integer, List<Carta>>> ordenarParejas(List<Pair<Integer, List<Carta>>> parejaJugadasManos){
	     	Pair<Integer, List<Carta>> temp;
	        for(int i=1;i < parejaJugadasManos.size();i++){
	            for (int j=0 ; j < parejaJugadasManos.size() - 1; j++){
	                if (parejaJugadasManos.get(j).getKey() > parejaJugadasManos.get(j+1).getKey()){
	                    temp = parejaJugadasManos.get(j);
	                    parejaJugadasManos.set(j, parejaJugadasManos.get(j+1));
	                    parejaJugadasManos.set(j+1,temp);
	                }
	            }
	        }
	        return parejaJugadasManos;
	}
}
