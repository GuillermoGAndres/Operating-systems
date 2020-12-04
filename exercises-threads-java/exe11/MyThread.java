package exe11;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class MyThread extends Thread {

    private Date date;
    private int tiempo = 1000; // Default time.

	private int incremento;
    private int valorFinal;
    
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

    public MyThread(int priority, String name, int tiempo) {
	this(name, tiempo);
	setPriority(priority);
    }

    public MyThread(int priority, String name, int tiempo, int incremento, int valorFinal) {
	this(priority, name, tiempo);
	this.incremento = incremento;
	this.valorFinal = valorFinal;
    }

    @Override
    public void run() {
	date = new Date();	
	System.out.println("\tSoy el Thread " + getName());
	for (int i = 0; i <= this.valorFinal; i += this.incremento) {
	    System.out.println("Soy Thread " + getName() + " y digo " + i);
	}
	System.out.println("----Termine soy el Thread " + getName());
    }
	    
}

//SimpleDateFormat tiempoLocal = new SimpleDateFormat("hh:mm:ss");
// SimpleDateFormat.format() sola es para darle un formato y convertilo en un String.
//System.out.println( getName() + " trabajando  (" + tiempoLocal.format(date.getTime()) + ")");

// System.out.println("Me voy dormir Soy el Thread " + getName());
// try {
// 	sleep(this.tiempo);
// } catch (Exception e) {
// 	e.getMessage();
// }
// System.out.printf("Hola soy Thread %s despues de haber dormido: %d (Tiempo: %tT)\n", getName(), this.tiempo,
// 		      date);
