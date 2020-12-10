//Hilo Archivo que recibe una lista de archivos que ya tienen un patron
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class Archivo extends Thread{
		// Atributos
	File[] file;
    Date date;
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_RESET = "\u001B[0m";
	//Constructor
	Archivo(File [] archivos){
		file = new File[archivos.length];
		for(int i=0; i<archivos.length;i++){
			file[i]=archivos[i];
		}
	}
    
    Archivo(File[] archivos, String name) {
        this(archivos);
        setName(name);
    }
    
	//Metodo
	public void run(){
        
		//imprime solo la lista de archivos que cumple el patron
		for(int i=0; i<file.length; i++) {
			if(file[i].isDirectory()) {
			}else{
                System.out.println(ANSI_BLUE+getName()+" (Archivo) ----> "+file[i].getName()+" |[tama\u00f1o: "+Math.round(Math.ceil(file[i].length()/1024.0))+" KB ]"+ANSI_RESET);
			}
		}
        
        date = new Date();
        DateFormat hourDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		System.out.println(ANSI_BLUE+getName()+" (Archivo) TERMINO ("+ hourDateFormat.format(date) + ")" + ANSI_RESET);
	}
}
