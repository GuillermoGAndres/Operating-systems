
public class MemoriaRam {
    
	private Queue<Proceso> colaProcesosEjecucion;
    private int tamanio;

    public MemoriaRam() {
        colaProcesosEjecucion = new LinkedQueue<>();
        tamanio = 500;  // Representa 500KB
    }
    
    public void insertarProceso(Proceso proceso) {
        colaProcesosEjecucion.enqueue(proceso);
    }

    
    public Proceso borrarProceso() {
        Proceso elem = null;
        try {
            elem = colaProcesosEjecucion.dequeue();
        } catch (EmptyCollectionException e) { }
        return elem;
    }

    public boolean estaDispoble() {
        return getTamanio() >= 0;
    }
    
    public int getTamanio() {
        return tamanio;
    }
    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

    public String toString() {
        return colaProcesosEjecucion.toString();
    }
        
}
