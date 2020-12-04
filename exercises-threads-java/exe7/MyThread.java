package exe7;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class MyThread extends Thread {

    private Date date;
    private int tiempo = 1000; // Default time.    
   
    public MyThread() {
	date = new Date();
	DateFormat hourDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
	System.out.print("\nSe creado un nuevo hilo " + getName());
	System.out.println("(" + hourDateFormat.format(date) + ")");
    }
        
    public MyThread(int tiempo) {
	this();
	this.tiempo = tiempo;
    }

    public MyThread(String name, int tiempo) {
	this(tiempo);
        setName(name);
    }
    
    @Override
    public void run() {
	date = new Date();
	System.out.println("\tSoy el Thread " + getName());
	//SimpleDateFormat tiempoLocal = new SimpleDateFormat("hh:mm:ss");
	// SimpleDateFormat.format() sola es para darle un formato y convertilo en un String.
	//System.out.println( getName() + " trabajando  (" + tiempoLocal.format(date.getTime()) + ")");
	try {
	    sleep(this.tiempo); 
	} catch (Exception e) {
	    e.getMessage();
	}
	System.out.printf("Hola soy Thread %s despues de haber dormido: %d (Tiempo: %tT)\n", getName(), this.tiempo, date);

    }
}
