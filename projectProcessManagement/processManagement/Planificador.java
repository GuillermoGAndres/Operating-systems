//package processsManagement;
/**
 * Representa nuestro planificador principal de todo el sistema.
 *
 */
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Arrays;

public class Planificador {
	private Queue<Proceso> colaProcesosListos = new LinkedQueue<>();
    private MemoriaRam memoriaRam = new MemoriaRam();
    private Procesador cpu = new Procesador();
    private boolean bajoProcesoCPU = false;
    private boolean entroCPU = false; // Esta variable nos ayuda a saber si entro en el procesador.
    private static Reloj reloj = new Reloj();

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
    
	public void iniciarSimulacion(Proceso[] tablaProcesos){
        AtomicInteger i= new AtomicInteger(0);        
        Proceso proceso = null;
        // Una vez terminados la ejecucion guardaremos los procesos en el vector para hacer los calculos de tiempo promedio.
        Proceso[] procesosFinalizados = new Proceso[tablaProcesos.length];
        int totalProcesosFinalizados = tablaProcesos.length;
        AtomicInteger indiceProcesosFinalizados = new AtomicInteger(0);        

        // Necesistaremos romper ese ciclo while para pueda calcular los tiempos.
        // int tiempoTotalEjecucion = 0;
        // for(int k=0; k < tablaProcesos.length; k++) {
        //     tiempoTotalEjecucion += tablaProcesos[k].getTiempoRequeridoEjecucion();
        // }
        // System.out.println("Tiempo que tardara la simulacion: " + tiempoTotalEjecucion);
        // reloj.getTiempo() <= tiempoTotalEjecucion

        while(indiceProcesosFinalizados.get() < totalProcesosFinalizados) {
            entroCPU = false; // Inicializamos nuestros variables a cero.
            System.out.printf("Tiempo: %d\n", reloj.getTiempo());
            
            // Como se que la tabla de procesos ya esta ordena, los posteriores elementos
            // tendran un numero mayor de tiempo de llegada
            // Error -> Existe la posibilidad de que todos lleguen al mismo tiempo y por lo tanto si estara ordenedad, pero
            // en el instance de tiempo tienen que entrar todos a la cola de procesos listo y no solamente el que sigue
            // de la tabla de procesos listos.

            // if(i.get() < tablaProcesos.length && reloj.getTiempo() == tablaProcesos[i.get()].getTiempoLLegadaProceso()) {
            //     agregarColaProcesosListo(tablaProcesos[i.get()]);
            //     int tmp = i.get() + 1;
            //     i.set(tmp);
            // }
            verificarTiempoLLegadaProcesos(tablaProcesos, reloj, i);
            //System.out.println("(Atomic integer)Index: " + i);
            
            // Siguiendo la politica primero se forman y luego vaja el proceso a la cola, se pone aqui el if.
            if(bajoProcesoCPU) {
                agregarColaProcesosListo(proceso); // proceso el el ultimo valor que guardo.
                bajoProcesoCPU = false; // Regresamos a su valor por defecto.
            }
            
            // Verificamos que exista tamaño en la memoria, que existe tambien proceoso en cola de espera
            // y que tambien exista espacio adecuado para el proceoso.
            // Planificador de mediano plazo
            while ( memoriaRam.estaDispoble() && !colaProcesosListos.isEmpty() && (memoriaRam.getTamanio() - colaProcesosListos.first().getTamanio()) >= 0 ) {
                proceso = null;
                try{
                    proceso = colaProcesosListos.dequeue();
                } catch (EmptyCollectionException e) {}
                System.out.printf("***Subio el proceso %s a la memoria***\n", proceso.getNombre());
                System.out.println("***Insertando en la memoria RAM***");
                dormir();
                System.out.println(ANSI_RED + "\tMemoria Ram" + ANSI_RESET);
                //System.out.println("Ram tamanio " + memoriaRam.getTamanio());
                //System.out.println("Proceso tamanio " + proceso.getTamanio());
                int tamanioRestante = memoriaRam.getTamanio() - proceso.getTamanio();                
                memoriaRam.insertarProceso(proceso);
                System.out.println(memoriaRam);
                memoriaRam.setTamanio(tamanioRestante);
                System.out.printf("Espacio restante RAM: %dKB\n", memoriaRam.getTamanio());
                proceso = null;
            }
            
            // Planificador de corto plazo
            if (cpu.estaDispoble() && memoriaRam.tieneProcesosCargados()) {
                proceso = null;
                proceso = memoriaRam.sacarProceso();
                System.out.printf("Subio el proceso %s a la CPU\n", proceso.getNombre());
                // Si se ejecuta un proceso significa que libero espacio en la memoria ram, y por la tanto recupero el espacio ocupado por el proceso.
                memoriaRam.setTamanio(memoriaRam.getTamanio() + proceso.getTamanio());
                System.out.printf("Actualizando espacio disponible en la memoria RAM: %dKB\n", memoriaRam.getTamanio());
                System.out.println("---Insertando a la CPU---");
                cpu.ejecutar(proceso, reloj, tablaProcesos, i, colaProcesosListos, memoriaRam, procesosFinalizados, indiceProcesosFinalizados);
                // Baja de la CPU
                System.out.printf("Bajando de la CPU el proceso %s...\n", proceso.getNombre());
                if(proceso.getTiempoRequeridoEjecucion() > 0) {
                    bajoProcesoCPU = true;
                } else {
                    proceso = null;
                }
                dormir();
                entroCPU = true;
            }
            // Esto nos ayudara a no contar doble, ya que el procesador tambien esta llevando la cuenta.
            //System.out.println("Entro cpu: " + entroCPU);
            if (!entroCPU){
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e){}
                int t = reloj.getTiempo() + 1;
                reloj.setTiempo(t);
                // Como sabemos que existiran procesos la cpu siempre esta trabando,
                // en el momento que ya no esta trabanjo significa que fue el ultimo proceoso ejecutar.
                // Error existen la posibilidad que en los tiempos intermedios no se ejecute el CPU porque no ha llegado un proceso.
                //break;
            }
            
        }

        System.out.println("Termino la simulacion");
        //System.out.println(Arrays.toString(procesosFinalizados));
        System.out.println("Calculando tiempos");
        float tiempoPromedioEspera = 0.0f;
        float tiempoPromedioRespuesta = 0.0f;
        float tiempoPromedioEjecucion = 0.0f;
        System.out.println("--------------------------------------");
        for(Proceso process : procesosFinalizados) {
            // System.out.println(process.getNombre());
            // System.out.println("-------Proceso " + process.getNombre() + "-----------");
            // System.out.println("Ejecucion: " + process.getTiempoQueSeEstuvoEjecutando());
            // System.out.println("Termino: " + process.getTiempoQueTermino());
            // System.out.println("Subio : " + process.getTiempoQueSubioAntesTerminar());
            // System.out.println("Tiempo de llegada: " + process.getTiempoLLegadaProceso());
            // System.out.println("Tiempo espera");
            // System.out.printf("%d - %d - %d\n", process.getTiempoQueSubioAntesTerminar(),  process.getTiempoLLegadaProceso(), process.getTiempoQueSeEstuvoEjecutando() );
            // System.out.println("Tiempo ejecucion");
            // System.out.printf("%d - %d\n", process.getTiempoQueTermino(), process.getTiempoLLegadaProceso());
            // System.out.println("Tiempo respuesta");
            // System.out.printf("%d - %d\n", process.getTiempoQueSubioPorPrimeraVez(), process.getTiempoLLegadaProceso());

            tiempoPromedioEspera += (process.getTiempoQueSubioAntesTerminar() - process.getTiempoLLegadaProceso() - process.getTiempoQueSeEstuvoEjecutando());
            tiempoPromedioEjecucion += (process.getTiempoQueTermino() - process.getTiempoLLegadaProceso());
            tiempoPromedioRespuesta += (process.getTiempoQueSubioPorPrimeraVez() - process.getTiempoLLegadaProceso());

            
        }

        System.out.println("Tiempo Promedio Espera: " + tiempoPromedioEspera/ procesosFinalizados.length + " ms");
        System.out.println("Tiempo Promedio Ejecucion: " + tiempoPromedioEjecucion/ procesosFinalizados.length + " ms");
        System.out.println("Tiempo Promedio Respuesta: " + tiempoPromedioRespuesta/ procesosFinalizados.length + " ms");        
                
	}


	/**
	 * Agregar a la cola de procesos listos para despues ingresar a la memoria.
     * 
	 */
	public void agregarColaProcesosListo(Proceso proceso) {
        System.out.println("Insertando a la cola procesos listos ...");
        dormir();
        System.out.println(ANSI_GREEN+"\tCola de Procesos"+ANSI_RESET);
        colaProcesosListos.enqueue(proceso);
        System.out.println(colaProcesosListos);

	}

    private void dormir() {
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e){}
    }

    /**
     * Veficica en la tabla de procesos los tiempos de llegada y los coloca en la table de procesos.
     */
    private void verificarTiempoLLegadaProcesos(Proceso[] tablaProcesos, Reloj tiempo, AtomicInteger index) {

        for(int i = index.get(); i < tablaProcesos.length; i++) {
            if (tablaProcesos[i].getTiempoLLegadaProceso() == tiempo.getTiempo()) {
                agregarColaProcesosListo(tablaProcesos[i]);
                index.set(index.get() + 1);                    
            }
        }
    }

    
}
