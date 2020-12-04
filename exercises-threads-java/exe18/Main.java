package exe18;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {       
	
		LinkedList<Thread> lista = new LinkedList<>();

		// Crea a mis hijos y le se asigno a cada uno una tabla.
		for(int i = 1; i <= 10;  i++) {
			lista.add(new Tabla(Thread.NORM_PRIORITY,String.valueOf(i), i));
		}

		// Empiezo a correr mis hilos para las tablas de multiplicar.
		for(Thread hilo : lista) {			
			hilo.start();			
		}

		//Barreras para esperar a cada hilo.
		for(Thread hilo : lista) {
			Date date = new Date();
			System.out.printf("(%tT) Hilo activos: %d\n",date, Thread.activeCount());
			try{
				hilo.join();				
			}catch (Exception e) {
				System.out.println("Salio algo mal");
			}
			System.out.printf("(%tT) La tabla %s termino su ejecucion\n", date, hilo.getName() );

		}
		
		// Informacion extra del hilo principal
		Date date = new Date();
		DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
		System.out.println("----Hilo principal terminado----" + hourFormat.format(date));
    }
}
