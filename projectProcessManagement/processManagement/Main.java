//package processManagement;
import java.util.Scanner;
import java.util.Arrays;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
	private static Scanner parse = new Scanner(System.in);
	
	public static void main(String[] args) {
		Queue<Proceso> colaProcesosListos = new LinkedQueue<>();
		Planificador planificador = new Planificador();

        String pathFile = "inputTest.txt";       
 		Proceso[] tablaProcesos = leerEntradasPorFichero(pathFile,4);        
        System.out.println(Arrays.toString(tablaProcesos));         
        System.out.println("-----ordenado");        
        ordenarTabla(tablaProcesos);
        System.out.println(Arrays.toString(tablaProcesos));
        
        // Una vez obtenidos los datos iniciaresmos con la simulacion
        planificador.iniciarSimulacion(tablaProcesos);
        
        
	}

    

    /**
     * Metodo auxiliar para testear los datos de entrada de muchos proceso en un archivo
     * llamado inputTest.txt
     */
    public static Proceso[] leerEntradasPorFichero(String path, int numProcesos) {
        String idProceso, nombreProceso;
		int tamanio;
		int prioridad;
		int tiempoRequeridoEjecucion;	
		int tiempoLLegadaProceso;        
		Proceso[] tablaProcesos = new Proceso[numProcesos];
        int i = 0;
        try {
            BufferedReader br;
            FileReader fr = new FileReader(path);
            br = new BufferedReader(fr);
            // Se que la primera linea solo es el encabezado
            String linea = br.readLine();
            linea = br.readLine();
            while( linea != null) {
                System.out.println(linea);
                StringTokenizer token = new StringTokenizer(linea, ",");
                // Como yo se cuantos datos hay en el cvs, puedo hacer esto sin provar una exepcion
                // https://es.stackoverflow.com/questions/38085/leer-fichero-formato-csv-en-java
                idProceso = token.nextToken().trim();
                nombreProceso = token.nextToken().trim();
                tamanio = Integer.parseInt(token.nextToken().trim());
                prioridad = Integer.parseInt(token.nextToken().trim());                
                tiempoRequeridoEjecucion =Integer.parseInt(token.nextToken().trim());    
                tiempoLLegadaProceso = Integer.parseInt(token.nextToken().trim());
                tablaProcesos[i] = new Proceso(idProceso, nombreProceso, prioridad, tamanio, tiempoRequeridoEjecucion, tiempoLLegadaProceso);
                linea = br.readLine();
                i++;
            }
            br.close();
        } catch (IOException ioe) {
            System.out.println("Salio algo en el archivo");
            ioe.printStackTrace();
        }
        return tablaProcesos;
        
    }


    
    /**
     * Metodo que captura los datos del usuario
     *
     */
    public static Proceso[] pedirDatosUsuario() {
        String idProceso, nombreProceso;
		int tamanio;
		int prioridad;
		int tiempoRequeridoEjecucion;	
		int tiempoLLegadaProceso;
		int numProcesos;
		System.out.print("Numero de procesos a crear: ");
		numProcesos = parse.nextInt();
        Proceso[] tablaProcesos = new Proceso[numProcesos];
        
        for(int i = 0; i < numProcesos; i++) {
			System.out.printf("\n\t Datos del proceso %d\n", i+1);
			System.out.printf("Id del proceso (Alfanumerico o numerico): ");
			idProceso = parse.next();
			System.out.printf("Nombre del proceso(Alfanumerico): ");
			nombreProceso = parse.next();
			System.out.printf("Tamanio del proceso(KB): ");
			tamanio = parse.nextInt();
			System.out.printf("Tiempo requerido para su ejecucion(s): ");
			tiempoRequeridoEjecucion = parse.nextInt();
			do {
				System.out.printf("Prioridad del proceso(1-10): ");
				prioridad = parse.nextInt();
			} while(!(prioridad >= 1 && prioridad <= 10));			
			System.out.printf("Tiempo de llegada del proceso(s): ");		
			tiempoLLegadaProceso = parse.nextInt();
			tablaProcesos[i] = new Proceso(idProceso, nombreProceso, prioridad, tamanio, tiempoRequeridoEjecucion, tiempoLLegadaProceso);		
		}
        return tablaProcesos;            
    }
    

    /**
     * Ordena tabla de proceso segun el tiempo de llegada que capturo en los datos
     *
     */
    public static void ordenarTabla(Proceso[] tablaProcesos) {

        int length = tablaProcesos.length;
        for(int i = 0; i < length -1; i++) {
            for (int j = 0; j < length - i -1; j++) {
                if(tablaProcesos[j].getTiempoLLegadaProceso() > tablaProcesos[j+1].getTiempoLLegadaProceso()) {
                    Proceso aux = tablaProcesos[j+1];
                    tablaProcesos[j+1] = tablaProcesos[j];
                    tablaProcesos[j] = aux;
                }                
            }

        }
        
    }



    
	
}
