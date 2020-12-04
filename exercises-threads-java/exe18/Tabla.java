package exe18;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.LinkedList;

public class Tabla extends Thread {

    private Date date;
	private int table;
	private LinkedList<Thread> listaRenglones = new LinkedList<>();
		
    public Tabla() {
		date = new Date();
		DateFormat hourDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		System.out.print("\nSe creado un nuevo hilo " + getName());
		System.out.println("(" + hourDateFormat.format(date) + ")");
    }

	public Tabla(String name) {
		setName(name);
		date = new Date();
		DateFormat hourDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		System.out.print("\nSe creado un nueva Tabla con el nombre: " + getName());
		System.out.println("\t(" + hourDateFormat.format(date) + ")");		
	}
	

	public Tabla(String name, int table) {
		this(name);		
		this.table = table;
		
	}

    public Tabla(int priority, String name, int table) {
		this(name, table);
		setPriority(priority);
    }

    @Override
    public void run() {
		date = new Date();	
		System.out.println("\tSoy la Tabla " + getName() + " estoy creando a mis 10 hilos renglonTabla");
		// Creamos nuestros renglones que haran nuestras multiplicaciones.
		for(int i = 1; i <= 10; i++) {
			listaRenglones.add(new RenglonTabla(this.table, i));
		}

		// Ejecutamos nuestros hilos
		for(Thread renglon : listaRenglones) {
			renglon.start();
		}
		
		//System.out.printf("----- (%tT) Termine mi tabla, soy la Tabla %s\n", date, getName());
    }

	public void makeTable(int table) {
		for(int i=0; i <= 10; i++) {
			System.out.printf("%d x %d = %d\n", table, i, table * i);
		}
	}
	    
}

