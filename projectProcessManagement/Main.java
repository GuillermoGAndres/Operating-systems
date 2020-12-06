

public class Main {

    public static void main(String[] args) {
        Integer num = new Integer(4);
        System.out.println("Antes de la funcion:");
        System.out.println(num);
        foo(num);
        System.out.println("Saliendo de la funcion: ");
        System.out.println(num);

    }

    public static void foo(Integer num)  {
        System.out.println("Entrando en la funcion");
        num = 66;
        System.out.println(num);
    }
}
