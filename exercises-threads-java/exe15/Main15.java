//package exe15;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.LinkedList;

public class Main15 {

    public static void main(String[] args) {       
	
		//Thread myThread1 = new MyThread(Thread.NORM_PRIORITY,"1", 2);
		LinkedList<Thread> lista = new LinkedList<>();

		// Defino mis hilos para mis tablas
		for(int i = 1; i <= 10;  i++) {
			lista.add(new MyThread15(Thread.NORM_PRIORITY,String.valueOf(i), i));
		}

		// Ejecuto mis hilos para las tablas
		for(Thread hilo : lista) {
			hilo.start();
		}
		//--------------------------------
	
		Date date = new Date();
		DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
		System.out.println("----Hilo principal terminado----" + hourFormat.format(date));
    }
}
