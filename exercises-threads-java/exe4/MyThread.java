import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class MyThread extends Thread {

    public Date date;

    public MyThread() {
	date = new Date();
	DateFormat hourDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
	System.out.print("\nSe creado un nuevo hilo " + getName());
	System.out.println("(" + hourDateFormat.format(date) + ")");
    }
    
    @Override
    public void run() {
	date =  new Date();
	SimpleDateFormat tiempo = new SimpleDateFormat("hh:mm:ss");
	System.out.println("\tSoy el hijo " + getName());
	//	System.out.println("Prioridad" + getPriori());
	System.out.println("GetID:  " + getId());
	System.out.println("getThreadGroup: " + getThreadGroup());
	System.out.println("getState: " + getState());
	System.out.println("Thread: " + Thread.activeCount());       
	//	System.out.println("Prioridad" + getPriori());
	while(true) {
	    date = new Date();
	    System.out.println( getName() + " trabajando  (" + tiempo.format(date.getTime()) + ")");

	    try {
		sleep(1000); 
	    } catch (Exception e) {
		e.getMessage();
	    }
	}

    }
}
