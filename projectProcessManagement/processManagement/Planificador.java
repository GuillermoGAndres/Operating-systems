//package processsManagement;
/**
 * Representa nuestro planificador principal de todo el sistema.
 *
 */

public class Planificador {
	Queue<Proceso> colaProcesosListos = new LinkedQueue<>();
	Queue<Proceso> colaProcesosEjecucion =  new LinkedQueue<>();
	

    
	public void iniciarSimulacion(Proceso[] tablaProcesos){
        //Incresamos los datos en la cola
        for(Proceso process : tablaProcesos) {
            System.out.println("Insertando a la cola procesos listos ...");
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e){}
            colaProcesosListos.enqueue(process);
            System.out.println(colaProcesosListos);
        }
        
        
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
