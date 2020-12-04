package exe11;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class Main {

    public static void main(String[] args) {       
	
	Thread myThread1 = new MyThread(Thread.NORM_PRIORITY,"1", 1000, 2,  10);
	myThread1.start();
	Thread myThread2 = new MyThread(Thread.MAX_PRIORITY, "2", 1000, 5, 20);
	myThread2.start();
	Thread myThread3 = new MyThread(Thread.MIN_PRIORITY,"3", 1000, 10, 50);
	myThread3.start();
	//myThread1.join();
	Date date = new Date();
	DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
	System.out.println("----Hilo principal terminado----" + hourFormat.format(date));
    }
}
