
class Productor extends Thread {

    private Mensaje mensaje;   //�ltimo mensaje generado

    public Productor(Mensaje c, String name) {
        mensaje = c;
        setName(name);
    }

    public void run() {
        int newMessage;	//numero de mensaje

        while (true) {
            newMessage = (int) (Math.random() * 100); //genera num. de  msj
            mensaje.almacenar(newMessage, getName()); //almaena el mensaje
            //System.out.println("Productor " + getName() + ":   almacena el mensaje #" + newMessage);
        }
    }
}
