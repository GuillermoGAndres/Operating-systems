package productorconsumidormsj;

class CProductor extends Thread {

    private CMensaje mensaje;   //�ltimo mensaje generado

    public CProductor(CMensaje c) {
        mensaje = c;
    }

    public void run() {
        int nMsj;	//numero de mensaje

        while (true) {
            nMsj = (int) (Math.random() * 100); //genera num. de  msj
            mensaje.Almacenar(nMsj); //almaena el mensaje
            System.out.println("Productor " + getName() + ":   almacena el mensaje #" + nMsj);
        }

    }
}
