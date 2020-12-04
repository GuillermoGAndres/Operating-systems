package exe17;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class MyThread extends Thread {

    private Date date;
	private int valorFinal;
	private int table;
	
		
    public MyThread() {
		date = new Date();
		DateFormat hourDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		System.out.print("\nSe creado un nuevo hilo " + getName());
		System.out.println("(" + hourDateFormat.format(date) + ")");
    }

	public MyThread(String name) {
		setName(name);
		date = new Date();
		DateFormat hourDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		System.out.print("\nSe creado un nuevo Thread con el nombre: " + getName());
		System.out.println("\t(" + hourDateFormat.format(date) + ")");		
	}
	

	public MyThread(String name, int table) {
		this(name);		
		this.table = table;
		
	}

    public MyThread(int priority, String name, int table) {
		this(name, table);
		setPriority(priority);
    }

    @Override
    public void run() {
		date = new Date();	
		System.out.println("\tSoy el Thread " + getName());

		makeTable(this.table);
	
		System.out.println("----Termine mi tarea soy el Thread " + getName());
    }

	public void makeTable(int table) {
		for(int i=0; i <= 10; i++) {
			System.out.printf("%d x %d = %d\n", table, i, table * i);
		}
	}
	    
}

