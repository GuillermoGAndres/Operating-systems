

public class MyThread extends Thread {

    @Override
    public void run() {
	System.out.println("\tSoy el hijo " + getName());
	System.out.println("Prioridad" + getPriori());

    }
}
