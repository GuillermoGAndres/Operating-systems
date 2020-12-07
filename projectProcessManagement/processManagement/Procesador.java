
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Arrays;
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
     * Otro caso importante a considerar es que al estar en cpu, tambien tiene que esta atendo a la memoria Ram
     * Ya que en el momento en el que llegue un proceoso a la cola de listo, tambien la memoria ram tiene que estar al
     * pendiente para guardarlo en la memoria ram y se cargado despues.
     * Haste momento momento(commit) la memoeria Ram solamente ha guardado solo un proceoso a la vez, no esta utilizando
     * el maximo de su capacidad.
     * Si se ejecuta un proceso significa que libero espacio en la memoria ram, y por la tanto recupero el espacio ocupado
     * por el proceso.
     */   
    public void ejecutar(Proceso proceso, Reloj reloj, Proceso[] tablaProcesos, AtomicInteger indice, Queue<Proceso> colaProcesosListos, MemoriaRam memoriaRam, Proceso[] procesosFinalizados, AtomicInteger indiceProcesosFinalizados) {        

        
        if (proceso.isSubioPorPrimeraVez() == false){
            proceso.setSubioPorPrimeraVez(true);
            proceso.setTiempoQueSubioPorPrimeraVez(reloj.getTiempo());            
        }

        proceso.setTiempoQueSubioAntesTerminar(reloj.getTiempo());
        for(int i = 0; i < this.quantum; i++) {

            // El procesador acaba antes de que termine su quantum.
            if(proceso.getTiempoRequeridoEjecucion() == 0){
                proceso.setTiempoQueTermino(reloj.getTiempo());
                proceso.setTiempoQueSeEstuvoEjecutando(proceso.getTiempoQueSeEstuvoEjecutando() - (i));
                procesosFinalizados[indiceProcesosFinalizados.get()] = proceso;
                //System.out.println(Arrays.toString(procesosFinalizados));
                //System.out.println("indice finalizados :" + indiceProcesosFinalizados);
                indiceProcesosFinalizados.set(indiceProcesosFinalizados.get() + 1);
                //System.out.println("indice finalizados :" + indiceProcesosFinalizados);
                return;
            }
            
            //Formar en la lista de procesos
            if(indice.get() < tablaProcesos.length && reloj.getTiempo() == tablaProcesos[indice.get()].getTiempoLLegadaProceso()) {
                System.out.println("Insertando a la cola procesos listos ...");
                //System.out.println("Entro********");
                //System.out.println("Valor de indice: " + indice);
                dormirProcesador(2000);
                colaProcesosListos.enqueue(tablaProcesos[indice.get()]);
                System.out.println(colaProcesosListos);
                int tmp = indice.get() + 1;
                indice.set(tmp);
            }

            // Verificamos que exista tamaño en la memoria, que existe tambien proceoso en cola de espera
            // y que tambien exista espacio adecuado para el proceoso.
            // Planificador de mediano plazo
            if ( memoriaRam.estaDispoble() && !colaProcesosListos.isEmpty() && (memoriaRam.getTamanio() - colaProcesosListos.first().getTamanio()) >= 0 ) {
                Proceso procesoListo = null;
                try{
                    procesoListo = colaProcesosListos.dequeue();
                } catch (EmptyCollectionException e) {}
                System.out.printf("Subio el proceso %s a la memoria\n", procesoListo.getNombre());
                System.out.println("***Insertando en la memoria RAM***");
                dormirProcesador(2000);
                //System.out.println("Ram tamanio " + memoriaRam.getTamanio());
                //System.out.println("Proceso tamanio " + proceso.getTamanio());
                int tamanioRestante = memoriaRam.getTamanio() - procesoListo.getTamanio();                
                memoriaRam.insertarProceso(procesoListo);
                System.out.println(memoriaRam);
                memoriaRam.setTamanio(tamanioRestante);
                System.out.printf("Espacio restante RAM: %dKB\n", memoriaRam.getTamanio());
                procesoListo = null;
            }
            
            System.out.printf("%s en ejecucion %d msg\n", proceso.getNombre(), proceso.getTiempoRequeridoEjecucion());
            int t = reloj.getTiempo() + 1;
            reloj.setTiempo(t);
            System.out.printf("Tiempo: %d\n", reloj.getTiempo());
            proceso.setTiempoQueSeEstuvoEjecutando(proceso.getTiempoQueSeEstuvoEjecutando() + 1);
            dormirProcesador(1000);
            int aux = proceso.getTiempoRequeridoEjecucion();
            proceso.setTiempoRequeridoEjecucion(--aux);
            //System.out.println("Tiempo requerido proceso actualizado: " +  proceso.getTiempoRequeridoEjecucion());
            //System.out.println("Tiempo que se estuvo ejecutando: " + proceso.getTiempoQueSeEstuvoEjecutando());
            // Verifico otra vez, debe ser antes y despues, porque existe la posibilidad que se el ultimo numero actualizado
            // sea de 0, por lo tanto, por tanto que guardar sus valores porque ya no entrara nuevamente a la cpu
            if(proceso.getTiempoRequeridoEjecucion() == 0){
                proceso.setTiempoQueTermino(reloj.getTiempo());
                proceso.setTiempoQueSeEstuvoEjecutando(proceso.getTiempoQueSeEstuvoEjecutando() - (i+1));
                procesosFinalizados[indiceProcesosFinalizados.get()] = proceso;
                //System.out.println(Arrays.toString(procesosFinalizados));
                //System.out.println("indice finalizados :" + indiceProcesosFinalizados);
                indiceProcesosFinalizados.set(indiceProcesosFinalizados.get() + 1);
                // System.out.println("indice finalizados :" + indiceProcesosFinalizados);
                return;
            }
        }
    }

    private void dormirProcesador(int tempo) {
        try{
            Thread.sleep(tempo);
        } catch (InterruptedException e){}
    }
   
}
