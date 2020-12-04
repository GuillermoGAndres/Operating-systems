package exe18;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class RenglonTabla extends Thread {
	private int operando1;
	private int operando2;

	
	public RenglonTabla(int operando1, int operando2) {
		this.operando1 = operando1;
		this.operando2 = operando2;
		// String nombreCompleto = String.valueOf(operando1) + "x" + String.valueOf(operando2);
		// setName(nombreCompleto);
		// Date date;
		// System.out.printf("\n\t---*He sido creado soy el renglonTabla %s---\n", getName());

	}
   	
	
	public void run() {
		//System.out.printf("\t--- Haciendo multiplicacion rengloTable %d ---\n",getName() );
		System.out.printf("%s %d x %d = %d\n", getName(), operando1, operando2, operando1 * operando2);
		System.out.println(getName() + " Termino renglon");
	}

   
}
