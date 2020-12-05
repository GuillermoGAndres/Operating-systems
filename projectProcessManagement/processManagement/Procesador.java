

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
    
    public void ejecutar(Proceso proceso) {        
        for(int i = 0; i < this.quantum; i++) {
            if(proceso.getTiempoRequeridoEjecucion() == 0)
                return;
            System.out.printf("%s en ejecucion %d msg\n", proceso.getNombre(), proceso.getTiempoRequeridoEjecucion());
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
