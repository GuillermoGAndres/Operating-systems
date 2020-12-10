//Hilo Listar que recibe una ruta y el patron de busqueda (Hijo Main)
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class Listar extends Thread{
	// Atributos
	String ruta;
	String patron;
    Date date;
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
    
	
	//Constructor
	Listar(String ruta, String patron){
		this.ruta = ruta;
		this.patron = patron;
	}

    Listar(String ruta, String patron, String name) {
        this(ruta, patron);
        setName(name);
    }
    
	//Metodo
    @Override
	public void run(){
		//Corrida del hilo listar
		System.out.println(ANSI_RED+"Listado Completo"+ANSI_RESET);
		File carpeta = new File(this.ruta); // archivo donde se guarda la ruta
			if(carpeta.exists()){ // comprueba si existe
				if(carpeta.isDirectory()) {
					File[] archivos;
					archivos = carpeta.listFiles(); // guarda la lista de archivos de la ruta  
					for(int i=0; i<archivos.length; i++) {
                        // Se puede hacer aun mas recursivo listando  de carpetas adentro de carpetas.
                        System.out.println(ANSI_RED+getName()+" (Listado) ----> " + carpeta.getPath() + carpeta.separator  +archivos[i].getName()+ANSI_RESET); //imprime lista de archivos 
					}
				}else{
					System.out.println(ANSI_RED+getName()+" No se encontraron archivos"+ANSI_RESET);
				}
			} else {
                System.out.println(ANSI_RED+getName()+" No se encontro ruta"+ANSI_RESET);
			}
            //Patron hiloNieto = new Patron(this.ruta, this.patron); // crea un nuevo hilo nieto que ejecuta el patron
			Patron hiloNieto = new Patron(this.ruta, this.patron, "Nieto"); 
			hiloNieto.start(); // corre el hilo
            date = new Date();
            DateFormat hourDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
			System.out.println(ANSI_RED + getName() +" TERMINO ( " + hourDateFormat.format(date) + ")" + ANSI_RESET);
            
	}
    
}
