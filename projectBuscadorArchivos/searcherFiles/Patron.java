//Hilo Patron que recibe una ruta y el patron de busqueda (Nieto Main)
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class Patron extends Thread{
	// Atributos
	String ruta;
	String patron;
	File carpeta;
    Date date;
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RESET = "\u001B[0m";
	
	//Constructor
	Patron(String ruta, String patron){
		this.ruta = ruta;
		this.patron = patron;
	}

    Patron(String ruta, String patron, String name) {
        this(ruta, patron);
        setName(name);
    }
    
	//Metodo
	public void run(){
		File carpeta = new File(this.ruta); //obtenemos la ruta
		// sobreescribimos el metodo Filefilter con la condicion de matches
        // Se crea una clase anonima o puede crearse una lamda por una fuctional interfaces.
		FileFilter filtro = new FileFilter(){ 
			public boolean accept(File file) { 
                // Verificacion de  los nombres.
                // Debe cumplir ciertas expresiones regulares para ser verdadero y sea
                // admitido como elemento del arreglo de FIle.   
                return file.getName().matches(patron);
			}
		};
        
		if(carpeta.isDirectory()) {
			File[] archivos;
			archivos = carpeta.listFiles(filtro); //hacemos un filtro de los archivos
			if(archivos.length == 0){
				System.out.println(ANSI_GREEN+getName()+" (Patron) ----> No se encontraron archivos o directorios con el patron: "+this.patron+ANSI_RESET);
			}else{
                for(int i=0; i<archivos.length; i++) {
                    //imprime lista de archivos y directorios que cumple el patron
                    System.out.println(ANSI_GREEN+getName()+" (Patron) ----> "+ archivos[i].getName()+" | [tama\u00f1o: "+Math.round(Math.ceil(archivos[i].length()/1024.0))+" KB ]"+ANSI_RESET);
                }
                // crea dos bisnietos
                Archivo hiloBisnieto1 = new Archivo(archivos, "Bisnieto1");
                hiloBisnieto1.start();
                Directorio hiloBisnieto2 = new Directorio(archivos, "Bisnieto2");
                hiloBisnieto2.start();
			}
		}
        
        date = new Date();
        DateFormat hourDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		System.out.println(ANSI_GREEN+getName()+" (Patron) TERMINO (" + hourDateFormat.format(date) + ")" + ANSI_RESET);
	}
	
}
