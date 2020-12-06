//package processsManagement;
/**
 * Representa nuestro planificador principal de todo el sistema.
 *
 */
import java.util.concurrent.atomic.AtomicInteger;

public class Planificador {
	private Queue<Proceso> colaProcesosListos = new LinkedQueue<>();
    private MemoriaRam memoriaRam = new MemoriaRam();
    private Procesador cpu = new Procesador();
    private boolean bajoProcesoCPU = false;
    private boolean entroCPU = false; // Esta variable nos ayuda a saber si entro en el procesador.
    private static Reloj reloj = new Reloj();    
    
	public void iniciarSimulacion(Proceso[] tablaProcesos){
        AtomicInteger i= new AtomicInteger(0);        
        Proceso proceso = null;
        while(true) {
            entroCPU = false; // Inicializamos nuestros variables a cero.
            System.out.printf("Tiempo: %d\n", reloj.getTiempo());            
            // Como se que la tabla de procesos ya esta ordena, los posteriores elementos
            // tendran un numero mayor de tiempo de llegada
            if(i.get() < tablaProcesos.length && reloj.getTiempo() == tablaProcesos[i.get()].getTiempoLLegadaProceso()) {
                agregarColaProcesosListo(tablaProcesos[i.get()]);
                int tmp = i.get() + 1;
                i.set(tmp);
            }
            
            // Siguiendo la politica primero se forman y luego vaja el proceso a la cola, se pone aqui el if.
            if(bajoProcesoCPU) {
                agregarColaProcesosListo(proceso); // proceso el el ultimo valor que guardo.
                bajoProcesoCPU = false; // Regresamos a su valor por defecto.
            }
            
            // Verificamos que exista tamaño en la memoria, que existe tambien proceoso en cola de espera
            // y que tambien exista espacio adecuado para el proceoso.
            if ( memoriaRam.estaDispoble() && !colaProcesosListos.isEmpty() && (memoriaRam.getTamanio() - colaProcesosListos.first().getTamanio()) >= 0 ) {
                proceso = null;
                try{
                    proceso = colaProcesosListos.dequeue();
                } catch (EmptyCollectionException e) {}
                System.out.printf("Subio el proceso %s a la memoria\n", proceso.getNombre());
                System.out.println("***Insertando en la memoria RAM***");
                dormir();
                //System.out.println("Ram tamanio " + memoriaRam.getTamanio());
                //System.out.println("Proceso tamanio " + proceso.getTamanio());
                int tamanioRestante = memoriaRam.getTamanio() - proceso.getTamanio();                
                memoriaRam.insertarProceso(proceso);
                System.out.println(memoriaRam);
                memoriaRam.setTamanio(tamanioRestante);
                System.out.printf("Espacio restante RAM: %dKB\n", memoriaRam.getTamanio());
                proceso = null;
            }

            if (cpu.estaDispoble() && memoriaRam.tieneProcesosCargados()) {
                proceso = null;
                proceso = memoriaRam.sacarProceso();
                System.out.printf("Subio el proceso %s a la CPU\n", proceso.getNombre());
                System.out.println("---Insertando a la CPU---");
                cpu.ejecutar(proceso, reloj, tablaProcesos, i, colaProcesosListos);
                if(proceso.getTiempoRequeridoEjecucion() > 0) {
                    bajoProcesoCPU = true;
                } else {
                    proceso = null;
                }
                dormir();
                entroCPU = true;
            }
            // Esto nos ayudara a no contar doble, ya que el procesador tambien esta llevando la cuenta.
            System.out.println("Entro cpu: " + entroCPU);
            if (!entroCPU){
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e){}
                int t = reloj.getTiempo() + 1;
                reloj.setTiempo(t);
            }
            
        }
        
	}


	/**
	 * Agregar a la cola de procesos listos para despues ingresar a la memoria.
     * 
	 */
	public void agregarColaProcesosListo(Proceso proceso) {
        System.out.println("Insertando a la cola procesos listos ...");
        dormir();
        colaProcesosListos.enqueue(proceso);
        System.out.println(colaProcesosListos);

	}

    private void dormir() {
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e){}
    }

    
}
