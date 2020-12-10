
class Consumidor extends Thread {

	private Mensaje mensaje;   //�ltimo mensaje generado
	
	public Consumidor (Mensaje c, String name) {
		mensaje = c;
        setName(name);
	}
	
	public void run() {
	  int msj;	//mensaje a mostrar
		
	  while (true) {
          msj = mensaje.obtener(getName()); //obtener el �ltimo mensaje
          //System.out.println("Consumidor "+ getName() + ":  obtuvo " + Msj + "\n");
	  }
	}
}
