import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class Main {
c
    public static void main(String[] args) {
	
		Thread myThread = new MyThread();
		Thread myThread2 = new MyThread();
		Thread myThread3 = new MyThread();
       	myThread.start();
       	myThread2.start();
		myThread3.start();
	
		Date date = new Date();
		DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
		System.out.println("Hilo principal " + hourFormat.format(date));
    }
}
