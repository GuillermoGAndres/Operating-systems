
public class Mensaje {
    private int[] muestras;
    private int tope;

    public Mensaje(int n) {
        muestras = new int[n];
        tope = -1;
    }

    public void imprimirDatos() {
        if (tope == -1) {
            System.out.println("Pila vacia");
            return;
        }
        System.out.println("Pila");
        for (int i = 0; i <= tope; i++) {
            System.out.print(" | " + muestras[i] + " | ");
        }
        System.out.println();
    }

    public synchronized void almacenar(int msj, String hiloName) {
        // Es while porque cuando cumpla cierta condicion, tendra
        // esperarte infinitamente
        
		while (tope == muestras.length) {
		    //el �ltimo mensaje no ha sido mostrado
		    try {
                System.out.println("\n" + hiloName +"sperando pila llena...\n");
                wait(); //se pone a dormir y cede el monitor
            } catch (InterruptedException e) {} 
		}
        // Si no entra al while significa que pila no esta llena        
		//numMsj = nmsj;
        
		//colocar el c�digo correpondiente para buscar el mensaje en una tabla de mensajes
        //impresiones son las que se tardan mucho.
        // La palabra monitor el controlador del cerrojo.
        //tope++;
		muestras[++tope] = msj;
        System.out.println(hiloName + " " + msj);
        imprimirDatos(); // Esto nos permite ver todo sin que corte la impresion
        // porque se encuentra en una estructura atomica por sincronized
		notifyAll();
	}

    
	public synchronized int obtener (String hiloName) {
		int mensaje;
		while (tope == -1) {
		    //no hay mensaje
		    try {
                wait(); //se pone a dormir y cede el monitor
                System.out.println("\n" + hiloName +"sperando pila llena...\n");
		    } catch (InterruptedException e) {} 
		}
        
        mensaje = muestras[tope];
        tope--;
        System.out.println(hiloName + "toma la muestra" + mensaje);        
        imprimirDatos();
		notifyAll();
		return (mensaje);
	}
    
}
