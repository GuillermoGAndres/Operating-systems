package exe5;

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

    public MyThread(int priority) {
	this();
	this.setPriority(priority);
    }
    public MyThread(int priority, String name) {
	this(priority);
	this.setName(name);	
    }
    
    @Override
    public void run() {
	date =  new Date();
	//SimpleDateFormat tiempo = new SimpleDateFormat("hh:mm:ss");
	System.out.println("\tSoy el hijo " + getName());
	System.out.println(getName() + "-" + "prioridad: " + getPriority());
	System.out.println(getName() + "-" + "getID:  " + getId());
    }
}
