//package processsManagement;
/**
 * Representa nuestro planificador principal de todo el sistema.
 *
 */

public class Planificador {
	private Queue<Proceso> colaProcesosListos = new LinkedQueue<>();
    private MemoriaRam memoriaRam = new MemoriaRam();
    
	private static int tiempo = 0;
    
	public void iniciarSimulacion(Proceso[] tablaProcesos){
        int i=0;
        while(true) {
            System.out.printf("Tiempo: %d\n", this.tiempo);
            // Como se que la tabla de procesos ya esta ordena, los posteriores elementos
            // tendran un numero mayor de tiempo de llegada
            if(i < tablaProcesos.length && this.tiempo == tablaProcesos[i].getTiempoLLegadaProceso()) {
                System.out.println("Insertando a la cola procesos listos ...");
                colaProcesosListos.enqueue(tablaProcesos[i]);
                System.out.println(colaProcesosListos);
                i++;
            }
            // Verificamos que exista tamaño en la memoria, que existe tambien proceoso en cola de espera
            // y que tambien exista espacio adecuado para el proceoso.
            if ( memoriaRam.estaDispoble() && !colaProcesosListos.isEmpty() && (memoriaRam.getTamanio() - colaProcesosListos.first().getTamanio()) >= 0 ) {
                Proceso proceso = null;
                try{
                    proceso = colaProcesosListos.dequeue();
                } catch (EmptyCollectionException e) {}
                System.out.println("***Insertando en la memoria RAM***");
                //System.out.println("Ram tamanio " + memoriaRam.getTamanio());
                //System.out.println("Proceso tamanio " + proceso.getTamanio());
                int tamanioRestante = memoriaRam.getTamanio() - proceso.getTamanio();                
                memoriaRam.insertarProceso(proceso);
                System.out.println(memoriaRam);
                memoriaRam.setTamanio(tamanioRestante);
                System.out.printf("Espacio restante RAM: %dKB\n", memoriaRam.getTamanio());
                
            }
            
            
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e){}
            tiempo++;
        }
        
        //Incresamos los datos en la cola
        // for(Proceso process : tablaProcesos) {
        //     System.out.println("Insertando a la cola procesos listos ...");
        //     try{
        //         Thread.sleep(2000);
        //     } catch (InterruptedException e){}
        //     colaProcesosListos.enqueue(process);
        //     System.out.println(colaProcesosListos);
        // }
        
        
	}


    public void planificadorMedianoPlazo() {
        
    }

	/**
	 * Este es el agregar a la cola de procesos que listos para despues ingresar a la memoria.
	 * Primero ingresara los procesos que tienen un tiempo de llegada menor, ya que simulando 
	 * el suceso esos procesos son los que llegan primero.
	 */
	public void agregarColaProcesosListo() {
		
	}
}
