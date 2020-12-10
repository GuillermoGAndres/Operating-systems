

public class Main {

    public static void main(String[] args) {
        Mensaje mensaje = new Mensaje(5);
        Productor  productor1 = new Productor(mensaje, "Prodcutro1");
        Productor  productor2 = new Productor(mensaje, "Prodcutro2");
        
        Consumidor consumidor1 = new Consumidor(mensaje, "Consumidor 1");
        Consumidor consumidor2 = new Consumidor(mensaje, "Consumidor 2");
	
        //ejecuci�n de los hilos
        productor1.start();
        productor1.start();
        consumidor1.start();
        consumidor2.start();
        
        
    
    }
}
