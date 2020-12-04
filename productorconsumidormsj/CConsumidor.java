package productorconsumidormsj;
class CConsumidor extends Thread {
	private CMensaje mensaje;   //�ltimo mensaje generado
	
	public CConsumidor (CMensaje c) {
		mensaje = c;
	}
	
	public void run() {
	  String Msj;	//mensaje a mostrar
		
	  while (true) {
	     Msj = mensaje.Obtener(); //obtener el �ltimo mensaje
	     System.out.println("Consumidor "+ getName() + ":  obtuvo " + Msj + "\n");
	  }
	}
}
