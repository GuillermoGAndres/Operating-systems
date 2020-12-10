package productorconsumidormsj;
class ProductorConsumidorMsg {

   public static void main (String[] ars) {
	//creaci�n de los hilos
	CMensaje mensaje = new CMensaje();
	CProductor  productor1 = new CProductor(mensaje);
	CConsumidor consumidor1 = new CConsumidor(mensaje);

	
	//ejecuci�n de los hilos
	productor1.start();
	consumidor1.start();
    }
}
