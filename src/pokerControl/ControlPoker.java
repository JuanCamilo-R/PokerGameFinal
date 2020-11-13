package pokerControl;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
	private List<String> nombres;
	private VistaPoker vista;
	private MesaJuego mesaJuego;
	private PanelJugador panelUsuario;
	private JugadorCPU jugador1,jugador2,jugador4,jugador5;
	private List<Boolean> tipoJugador;
	private List<Integer> dinero;
	private int[] descarte;
	private int ronda = 0;
	private boolean dormir = false;
	private Lock bloqueo = new ReentrantLock(); //manejo de sincronizacion
	private Condition esperarTurno = bloqueo.newCondition(); //manejo de sincronizacion
	private boolean tipoRonda = true; //True si es el momento de apostar y false si es el momento de descartar
	private List<JugadorCPU> jugadoresCPU;
	private int controlar=0;
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
		iniciarJugadoresCPU();
		

	}
	
	private void iniciarJuego() {
		baraja = new Baraja();
		for(int i = 0; i < 5 ;i++) {
			manoJugadores.add(seleccionarCartas());
		//	jugadoresCPU.get(i).recibirCartasIniciales(manoJugadores.get(i));
		}
		
		definirJugadoresCPU();
		darTipo();
		agregarNombres();
		darTipo();
		darDinero();
		vista = new VistaPoker(tipoJugador,nombres, manoJugadores,dinero, this);
		this.mesaJuego = vista.getMesaJuego();
		panelUsuario = (PanelJugador) mesaJuego.getPanelUsuario();
		
	}
	
	private void agregarNombres() {
			
			
			nombres.add(jugador1.getNombre());
			nombres.add(jugador2.getNombre());
			nombres.add("ElBicho");
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
	
	public void definirJugadoresCPU() {
		/*JugadorCPU */jugador1 = new JugadorCPU(500, "Samuel", 1, this);
		/*JugadorCPU */jugador2 = new JugadorCPU(600, "David", 2, this);
		/*JugadorCPU */jugador4 = new JugadorCPU(400, "Valentina", 3, this);
		/*JugadorCPU */jugador5 = new JugadorCPU(400, "Santiago", 4, this);
	}
	
	public void iniciarJugadoresCPU() {
		
		int aux = random.nextInt(5)+1;
		mesaJuego.espaciar();
		mesaJuego.mensaje("Orden de turnos:");
		switch(aux) {
			case 1:
				jugador1.setTurno(1);
				mesaJuego.mensaje(jugador1.getNombre());
				jugador2.setTurno(2);
				mesaJuego.mensaje(jugador2.getNombre());
				panelUsuario.setTurno(3);
				mesaJuego.mensaje(nombres.get(2));
				jugador4.setTurno(4);
				mesaJuego.mensaje(jugador4.getNombre());
				jugador5.setTurno(5);
				mesaJuego.mensaje(jugador5.getNombre());
				break;
			case 2:
				jugador2.setTurno(1);
				mesaJuego.mensaje(jugador2.getNombre());
				panelUsuario.setTurno(2);
				mesaJuego.mensaje(nombres.get(2));
				jugador4.setTurno(3);
				mesaJuego.mensaje(jugador4.getNombre());
				jugador5.setTurno(4);
				mesaJuego.mensaje(jugador5.getNombre());
				jugador1.setTurno(5);
				mesaJuego.mensaje(jugador1.getNombre());
				break;
			case 3:
				System.out.print("Inicias");
				panelUsuario.setTurno(1);
				mesaJuego.mensaje(nombres.get(2));
				jugador4.setTurno(2);
				mesaJuego.mensaje(jugador4.getNombre());
				jugador5.setTurno(3);
				mesaJuego.mensaje(jugador5.getNombre());
				jugador1.setTurno(4);
				mesaJuego.mensaje(jugador1.getNombre());
				jugador2.setTurno(5);
				mesaJuego.mensaje(jugador2.getNombre());
				break;
			case 4:
				jugador4.setTurno(1);
				mesaJuego.mensaje(jugador4.getNombre());
				jugador5.setTurno(2);
				mesaJuego.mensaje(jugador5.getNombre());
				jugador1.setTurno(3);
				mesaJuego.mensaje(jugador1.getNombre());
				jugador2.setTurno(4);
				mesaJuego.mensaje(jugador2.getNombre());
				panelUsuario.setTurno(5);
				mesaJuego.mensaje(nombres.get(2));
				break;
			case 5:
				jugador5.setTurno(1);
				mesaJuego.mensaje(jugador5.getNombre());
				jugador1.setTurno(2);
				mesaJuego.mensaje(jugador1.getNombre());
				jugador2.setTurno(3);
				mesaJuego.mensaje(jugador2.getNombre());
				panelUsuario.setTurno(4);
				mesaJuego.mensaje(nombres.get(2));
				jugador4.setTurno(5);
				mesaJuego.mensaje(jugador4.getNombre());
		}

		
		  jugadoresCPU.add(jugador1);
		  jugadoresCPU.add(jugador2);
		  jugadoresCPU.add(jugador4);
		  jugadoresCPU.add(jugador5);
		
		for(int i = 0; i < 4 ;i++) {
			jugadoresCPU.get(i).recibirCartasIniciales(manoJugadores.get(i));
				if(i>=2) {
					jugadoresCPU.get(i).recibirCartasIniciales(manoJugadores.get(i+1));
				}
		}

		mesaJuego.espaciar();

			
	      ExecutorService ejecutorSubprocesos = Executors.newCachedThreadPool();
		  ejecutorSubprocesos.execute(jugador1); 
		  ejecutorSubprocesos.execute(jugador2);
		  ejecutorSubprocesos.execute(jugador4);
		  ejecutorSubprocesos.execute(jugador5);
		  
		  ejecutorSubprocesos.shutdown();
		  
		  
	}
	public int getTurno() {
		return turnoActual;
	}
	public void activarRondaDescarte() {
		turnoActual=1;
		tipoRonda = false;
		for(int i=0;i<jugadoresCPU.size();i++) {
			jugadoresCPU.get(i).iniciarRondaDescarte();
		}
	}
	public void turnos(int turnoJugador, String nombreJugador, int dato, int dineroInicial) {
		bloqueo.lock();


		System.out.println(nombreJugador+ "entro a turnos");

		try
		{
			
			while(turnoJugador != turnoActual && !panelUsuario.getSiguienteTurno()) {
				System.out.println(nombreJugador+" intento entrar pero se duerme");
				esperarTurno.await();
			}
			if(tipoRonda) { //Ronda de apuesta
				//System.out.println("Size: "+jugadoresCPU.size());
				//System.out.println("Turno actual: "+turnoActual);
				System.out.println("Entro hilo: "+nombreJugador);
				System.out.println("Turno del jugador: "+nombreJugador+" = " + turnoJugador);
				System.out.println("Turno en general: "+turnoActual);
				if(turnoJugador <= 5 && setApuestaJugador(nombreJugador,dato)) {
					vista.actualizarVistaApuesta(dato, nombreJugador, String.valueOf(dineroInicial));
					verificarApuesta(nombreJugador);
					controlar++;
					
				}

				//vista.funcionPrueba();
				if(turnoJugador <= 5) {
					turnoActual++;
				}
				
				esperarTurno.signalAll();
			}else {
				int posicionDescarte = turnoActual - 1;
				for(int i = 0; i < jugadoresCPU.size(); i++) {
					if(jugadoresCPU.get(i).getTurno() == turnoActual)
					{
						descarte[posicionDescarte] = dato;
						System.out.println("Entro hilo: "+jugadoresCPU.get(i).getNombre()+"222");
						jugadoresCPU.get(i).descartarCartas();				
						darCartas(jugadoresCPU.get(i).getCantidadADescartar(),i);
						vista.actualizarVistaCartas( jugadoresCPU.get(i).getNombre(),jugadoresCPU.get(i).getCartas());
						turnoActual++;
						System.out.println("de"+panelUsuario.getSiguienteTurno()+"descarte \n");
						controlar++;
						
						
					}
				}
				esperarTurno.signalAll();
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally {
			/*panelUsuario.setSiguienteTurno(false);
			if(turnoActual == 6) {
				verificarApuestasFinal(nombreJugador);
				System.out.println("Entro al final: "+nombreJugador);
			}*/
			bloqueo.unlock();
		//	panelUsuario.setSiguienteTurno(false);
			if(turnoActual == 6) {
				if(tipoRonda == false) { //Ronda de descarte
					System.out.print("Empiezo ronda apuesta");
					tipoRonda = true;
					verificarApuestasFinal(nombreJugador);
					turnoActual = 1;
					panelUsuario.setSiguienteTurno(false);
				}
				if(tipoRonda && panelUsuario.getSiguienteTurno()) { //Ronda de apuesta
					tipoRonda = false;
					System.out.print("Empiezo ronda descarte");
					panelUsuario.setSiguienteTurno(false);
					System.out.print("Tipo ronda"+tipoRonda);
					turnoActual = 1;
					System.out.print("Tipo turno"+turnoActual);
				}
			}
		}
	}
	private void darCartas(int cantidad,int i) {	
        //cartas para jugadores simulados
		System.out.print("Dar cartasaaaa \n");
		List<Carta> cartas2 = new ArrayList<Carta>();
		
		for(int j=0;j<cantidad;j++) {
			cartas2.add(baraja.getCarta());
		}
		asignarCartas(cartas2,i,cantidad);
				
	}
	public boolean getTipoRonda() {
		return tipoRonda;
	}
	
	public void asignarCartas(List<Carta> cartas,int i,int cantidad) {
		for(int j=0;j<cantidad;j++) {
			jugadoresCPU.get(i).recibirCartas(cartas.get(j));
			System.out.print("Cantidad "+jugadoresCPU.get(i).getCantidadADescartar()+"   ");
			System.out.print("Valor "+cartas.get(j).getValor()+"   \n");
		}

	}
	public void sumarControl() {
		controlar++;
	}
	public int getControlador() {
		return controlar;
	}
	public synchronized void verificarApuesta(String nombreJugador) {
		for(int i = 0; i < jugadoresCPU.size(); i++) {
			if(jugadoresCPU.get(i).getNombre() == nombreJugador) {
				if(jugadoresCPU.get(i).getApuestaActual() != panelUsuario.getApuestaUsuario()) {
					System.out.println(jugadoresCPU.get(i).getNombre()+" verifica apuestas en la mitad");
					int apuesta = panelUsuario.getApuestaUsuario();
					System.out.println("Dinero actual de "+jugadoresCPU.get(i).getNombre()+ "antes de recibir su apuesta: "+jugadoresCPU.get(i).getDineroInicial());
					jugadoresCPU.get(i).devolverApuesta(jugadoresCPU.get(i).getApuestaActual());
					if(jugadoresCPU.get(i).apostar(apuesta)) {
						System.out.println("Dinero actual de "+jugadoresCPU.get(i).getNombre()+ "despues de recibir su apuesta: "+jugadoresCPU.get(i).getDineroInicial());
						System.out.println("Apuesta de "+nombreJugador+ " ha apostado: "+jugadoresCPU.get(i).getApuestaActual());
						vista.actualizarVistaApuesta(apuesta, nombreJugador, String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
						jugadoresCPU.get(i).interrumpir();
					}else {
						vista.actualizarVistaApuesta(0, jugadoresCPU.get(i).getNombre(), String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
						System.out.println(jugadoresCPU.get(i).getNombre()+ "ha recibido por no poder apostar en la mitad: "+jugadoresCPU.get(i).getApuestaActual());
						System.out.println("Dinero actual de "+jugadoresCPU.get(i).getNombre()+ "despues de no apostar otra vez en la mitad: "+jugadoresCPU.get(i).getDineroInicial());
						jugadoresCPU.get(i).interrumpir();
					}
				}
			}
			
		}
	}
	public  synchronized void verificarApuestasFinal(String nombre) {
		/*
		for(int i = 0; i < jugadoresCPU.size(); i++) {
			jugadoresCPU.get(i).run();
		}
		*/
		dormir = false;
		for(int i = 0; i < jugadoresCPU.size(); i++) {
			if(jugadoresCPU.get(i).getApuestaActual() != panelUsuario.getApuestaUsuario()) {
				System.out.println(jugadoresCPU.get(i).getNombre()+" verifica apuestas al final");
				int apuesta = panelUsuario.getApuestaUsuario();
				//System.out.println("Apuesta de "+jugadoresCPU.get(i).getNombre()+ " antes de apostar otra vez al final "+jugadoresCPU.get(i).getApuestaActual());
				jugadoresCPU.get(i).devolverApuesta(jugadoresCPU.get(i).getApuestaActual());
				//System.out.println("Dinero actual de "+jugadoresCPU.get(i).getNombre()+ "antes de apostar otra vez al final: "+jugadoresCPU.get(i).getDineroInicial());
				
				if(jugadoresCPU.get(i).apostar(apuesta)){
					System.out.println("Dinero actual de "+jugadoresCPU.get(i).getNombre()+ "despues de apostar su apuesta: "+jugadoresCPU.get(i).getDineroInicial());
					System.out.println("Apuesta de "+nombre+ " ha apostado al final: "+jugadoresCPU.get(i).getApuestaActual());
					vista.actualizarVistaApuesta(apuesta, jugadoresCPU.get(i).getNombre(), String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
					jugadoresCPU.get(i).interrumpir();
				}else {
					vista.actualizarVistaApuesta(0, jugadoresCPU.get(i).getNombre(), String.valueOf(jugadoresCPU.get(i).getDineroInicial()));
					System.out.println("Dinero actual de "+jugadoresCPU.get(i).getNombre()+ "despues de apostar otra vez al final: "+jugadoresCPU.get(i).getDineroInicial());
					jugadoresCPU.get(i).interrumpir();
				}
			}
		}
	}
	public synchronized boolean setApuestaJugador(String nombreJugador, int cantidadApuesta) {
		for(int i = 0; i < jugadoresCPU.size(); i++) {
			if(jugadoresCPU.get(i).getNombre() == nombreJugador) {
				if(jugadoresCPU.get(i).apostar(cantidadApuesta)) {
					System.out.println(jugadoresCPU.get(i).getNombre()+" aposto: "+cantidadApuesta);
					return true;
				}
			}
		}
		return false;
	}
	public void setTurnoActual() {
		turnoActual++;
	}
	public synchronized void despertarHilos() {
		esperarTurno.signalAll();
	}
	/*
	public void darCartas(JugadorCPU jugador, int cartasADar) {
		for(int i = 0; i < cartasADar; i++ ) {
			jugador.recibirCartas(baraja.getCarta());
		}
	}*/
	
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
