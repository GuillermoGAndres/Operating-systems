//package exe15;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class MyThread15 extends Thread {

    private Date date;
    private int tiempo = 1000; // Default time.

	private int incremento;
	private int valorFinal;
	private int table;
	
		
    public MyThread15() {
		date = new Date();
		DateFormat hourDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		System.out.print("\nSe creado un nuevo hilo " + getName());
		System.out.println("(" + hourDateFormat.format(date) + ")");
    }

	public MyThread15(String name) {
		setName(name);
		date = new Date();
		DateFormat hourDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		System.out.print("\nSe creado un nuevo Thread con el nombre: " + getName());
		System.out.println("\t(" + hourDateFormat.format(date) + ")");		
	}
	

    public MyThread15(int tiempo) {
		this();
		this.tiempo = tiempo;
    }

    // public MyThread15(String name, int tiempo) {
	// 	this(tiempo);
	// 	setName(name);
    // }

	public MyThread15(String name, int table) {
		this(name);		
		this.table = table;
		
	}

    public MyThread15(int priority, String name, int table) {
		this(name, table);
		setPriority(priority);
    }

    public MyThread15(int priority, String name, int tiempo, int incremento, int valorFinal) {
		this(priority, name, tiempo);
		this.incremento = incremento;
		this.valorFinal = valorFinal;
    }

    @Override
    public void run() {
		date = new Date();	
		System.out.println("\tSoy el Thread " + getName());

		makeTable(this.table);
	
		System.out.println("----Termine soy el Thread " + getName());
    }

	public void makeTable(int table) {
		for(int i=0; i <= 10; i++) {
			System.out.printf("%d x %d = %d\n", table, i, table * i);
		}
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
