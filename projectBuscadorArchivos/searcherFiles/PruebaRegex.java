import java.util.regex.Pattern;
import java.util.regex.Matcher;

class PruebaRegex {
    public static void main(String[] args) {
        String cadena = "Hola.java";
        String regex = "\\bjava\\b";
        //boolean b = Pattern.matches(regex, cadena);
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(cadena);
        boolean b = m.find();
        // boolean b = m.matches();
        System.out.println("Cadena: " + cadena + " Result: " + b);
    }
}
