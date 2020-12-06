
import java.util.concurrent.atomic.AtomicInteger;

public class Procesador {

    private boolean disponible;
    private int quantum; 
    public Procesador() {
        disponible = true;
        quantum = 4; 
    }

    public boolean estaDispoble() {
        return disponible == true;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Ejecura el proceso y ademas tambien llevara el tiempo que va trancurriendo en la cuenta
     * y lo actuaclizara.
     * Tambien en ese mismo momento en el cpu tiene que tambien estar atento cuando llegue 
     * un proceso en la cola de espera.
     * 
     */   
    public void ejecutar(Proceso proceso, Reloj reloj, Proceso[] tablaProcesos, AtomicInteger indice, Queue<Proceso> colaProcesosListos) {        
        for(int i = 0; i < this.quantum; i++) {
            if(proceso.getTiempoRequeridoEjecucion() == 0)
                return;
            //Formar en la lista de procesos
            // System.out.println("Indice: " + indice);
            // System.out.println(tablaProcesos[indice.get()]);
            // System.out.println("getTiempo de llagada el siguiente procesador: " + tablaProcesos[indice.get()].getTiempoLLegadaProceso());
            // System.out.println("timepo: " + reloj.getTiempo());
            // System.out.println(indice.get() < tablaProcesos.length && reloj.getTiempo() == tablaProcesos[indice.get()].getTiempoLLegadaProceso());
            if(indice.get() < tablaProcesos.length && reloj.getTiempo() == tablaProcesos[indice.get()].getTiempoLLegadaProceso()) {
                System.out.println("Insertando a la cola procesos listos ...");
                System.out.println("Entro********");
                System.out.println("Valor de indice: " + indice);
                dormirProcesador(2000);
                colaProcesosListos.enqueue(tablaProcesos[indice.get()]);
                System.out.println(colaProcesosListos);
                int tmp = indice.get() + 1;
                indice.set(tmp);
            }
            System.out.printf("%s en ejecucion %d msg\n", proceso.getNombre(), proceso.getTiempoRequeridoEjecucion());
            int t = reloj.getTiempo() + 1;
            reloj.setTiempo(t);
            System.out.printf("Tiempo: %d\n", reloj.getTiempo());
            dormirProcesador(1000);
            int aux = proceso.getTiempoRequeridoEjecucion();
            proceso.setTiempoRequeridoEjecucion(--aux);            
        }
    }

    private void dormirProcesador(int tempo) {
        try{
            Thread.sleep(tempo);
        } catch (InterruptedException e){}
    }
   
}
