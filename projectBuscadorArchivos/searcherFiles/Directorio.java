//Hilo Directorio que recibe una lista de archivos que ya tienen un patron
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class Directorio extends Thread{
	// Atributos
	File[] file;
    Date date;
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_RESET = "\u001B[0m";
	//Constructor
	Directorio(File [] directorios){
		file = new File[directorios.length];
		for(int i=0; i<directorios.length;i++){
			file[i]=directorios[i];
		}
	}
    Directorio(File[] directorios, String name) {
        this(directorios);
        setName(name);
    }
    
	//Metodo
	public void run(){
		//imprime solo la lista de directorios que cumple el patron
		for(int i=0; i<file.length; i++) {
				if(file[i].isDirectory()) {
                    System.out.println(ANSI_PURPLE+getName()+" (Directorio) ----> "+file[i].getName()+" | [tama\u00f1o: "+Math.round(Math.ceil(file[i].length()/1024.0))+" KB ]"+ANSI_PURPLE);
				}
		}
        date = new Date();
        DateFormat hourDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		System.out.println(ANSI_PURPLE+getName()+" (Directorio) TERMINO (" + hourDateFormat.format(date) + ")" + ANSI_RESET);
	}
}
