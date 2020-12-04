import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class Main {

    public static void main(String[] args) {
	
	Thread myThread = new MyThread();
       	myThread.start();
	
	Date date = new Date();
	DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
	System.out.println("Hilo principal " + hourFormat.format(date));
    }
}
