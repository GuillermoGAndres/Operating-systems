package exe13;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class Main {

    public static void main(String[] args) {       
	
	Thread myThread1 = new MyThread(Thread.NORM_PRIORITY,"1", 1000, 2,  10);
	Thread myThread2 = new MyThread(Thread.MAX_PRIORITY, "2", 1000, 5, 20);
	Thread myThread3 = new MyThread(Thread.MIN_PRIORITY,"3", 1000, 10, 50);
	myThread1.start();
	myThread2.start();
	System.out.println("Esperando a mi hijo A:");
	try{
		myThread1.join();
	} catch(InterruptedException ex) {
		System.out.println("Salio algo mal al esperar el hijo1");
	}
	
	System.out.println("Esperando a mi hijo 2");
	try{
		myThread2.join();
	} catch (InterruptedException ex) {
		System.out.println("Salio algo mal al esperar al hijo2");
	}

	System.out.println("MAIN: Ejecutando al hilo 3");
	myThread3.start();
	try{
		myThread3.join();
	} catch (InterruptedException ex) {
		System.out.println("Algo salio mal al esperar al hijo3");
	}
	
	
	Date date = new Date();
	DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
	System.out.println("----Hilo principal terminado----" + hourFormat.format(date));
    }
}
