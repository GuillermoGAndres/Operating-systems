
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.Scanner;


public class ExamenHilos1{
    
	public static void main (String[] args){
		//intruduccion de datos
		Scanner sc = new Scanner(System.in);
		// System.out.println("Escribe la ruta: ");
		// String ruta = sc.nextLine();
        String ruta = "/home/guillermo/Documents/prueba";
		// System.out.println("Escribe el patron: ");
		// String patron = sc.nextLine();
        String patron = ".*java.*";
		
		Listar hilo = new Listar(ruta, patron, "Hijo");
        
		hilo.start();
		System.out.println("Hilo principal esperando "+Thread.activeCount()+" hilos");
		System.out.println("1) "+ Thread.currentThread());
		System.out.println("2) "+ hilo.currentThread());
		try{
			hilo.join();
			}catch(InterruptedException ex){}
		
		
	}
}
