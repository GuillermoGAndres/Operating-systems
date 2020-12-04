/**
 * Clase que simula un proceso
 */
public class Proceso{
	private String nombre;
	private String idProceso;	
	private int tamanio;
    private int prioridad;
	private int tiempoRequeridoEjecucion;	
	private int tiempoLLegadaProceso;

	public Proceso() {}
    
	public Proceso(String idProceso, String nombre, int prioridad, int tamanio, int tiempoRequeridoEjecucion, int tiempoLLegadaProceso) {
		this.idProceso = idProceso;
        this.nombre = nombre;        
		this.tamanio = tamanio;
        this.prioridad = prioridad;
		this.tiempoRequeridoEjecucion = tiempoRequeridoEjecucion;
		this.tiempoLLegadaProceso = tiempoLLegadaProceso;
	}   


    @Override
    public String toString() {
        return getNombre();
    }
    
	// @Override
	// public String toString() {
	// 	String aux = "";
	// 	aux += "\tProceso" + "\n";
	// 	aux += "Id del proceso: " + getIdProceso() + "\n";
	// 	aux += "Nombre del proceso: " + getNombre() + "\n";
	// 	aux += "Tamanio del proceso: " + getTamanio() +  "\n";
	// 	aux += "Tiempo requerido para su ejecucion: " + getTiempoRequeridoEjecucion() + "\n";
	// 	aux += "Prioridad del proceso: " + getPrioridad() + "\n";
	// 	aux += "Tiempo de llegada del proceso: " + getTiempoLLegadaProceso() + "\n";
	// 	return aux;

	// }

    public String getNombre() {
        return nombre;
    }
    
    public int getPrioridad() {
        return prioridad;
    }

	public int getTiempoLLegadaProceso() {
		return tiempoLLegadaProceso;
	}

	public void setTiempoLLegadaProceso(int tiempoLLegadaProceso) {
		this.tiempoLLegadaProceso = tiempoLLegadaProceso;
	}

	public String getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}

	public int getTamanio() {
		return tamanio;
	}

	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}

	public int getTiempoRequeridoEjecucion() {
		return tiempoRequeridoEjecucion;
	}

	public void setTiempoRequeridoEjecucion(int tiempoRequeridoEjecucion) {
		this.tiempoRequeridoEjecucion = tiempoRequeridoEjecucion;
	}
	
	
	
}
