package exe8;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class Main {

    public static void main(String[] args) {
	
	Thread myThread1 = new MyThread(Thread.NORM_PRIORITY,"1", 4000);
	Thread myThread2 = new MyThread(Thread.MAX_PRIORITY,"2", 2000);
	Thread myThread3 = new MyThread(Thread.MIN_PRIORITY,"3", 100);
       	myThread1.start();
       	myThread2.start();
	myThread3.start();
	
	Date date = new Date();
	DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
	System.out.println("----Hilo principal terminado----" + hourFormat.format(date));
    }
}
