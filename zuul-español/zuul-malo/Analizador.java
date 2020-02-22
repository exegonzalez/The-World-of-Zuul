import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Esta clase es parte de la apliciación "World of Zuul". 
 * "World of Zuul" es un juego de aventuras sencillo basado en texto.  
 * 
 * Este analizador lee las entradas ingresadas por el usuario e intenta 
 * interpretarlas como un cmando. Cada vez que es llamado lee una linea
 * del termina e intenta interpretar esta linea como dos palabras comando,
 * devilviendo el comando como un objeto de la clase comando.
 *
 * El analizador contiene un set de palabras comando conocidas. Verifica si 
 * las entradas del usuario son comandos conocidos, y si no lo son, devuelve 
 * un objeto comando que marcado como comando desconocido.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Analizador 
{
    private PalabrasComando comandos;  // contiene todas las palabras comando
    private Scanner lector;         // entrada de comandos

    /**
     * Crea un analizador para leer desde una terminal.
     */
    public Analizador() 
    {
        comandos = new PalabrasComando();
        lector = new Scanner(System.in);
    }

    /**
     * @return el siguiente comando del usuario
     */
    public Comando getComando() 
    {
        String lineaIngresada;   // va a contenter toda una linea
        String palabra1 = null;
        String palabra2 = null;

        System.out.print("> ");     // imprime prompt

        lineaIngresada = lector.nextLine();

        // Encuantra hasta dos palabras de la linea.
        Scanner separador = new Scanner(lineaIngresada);
        if(separador.hasNext()) {
            palabra1 = separador.next();      // obtiene la primer palabra
            if(separador.hasNext()) {
                palabra2 = separador.next();      // obtiene la segunda palabra
                // se ignora el resto de la línea
            }
        }

        // Ahora se verifica si la palabra es conocida. Si es así, crea un
        // comando con ella. si no, crea un comando "null" (para comandos 
        // desconocidos).
        if(comandos.esComando(palabra1)) {
            return new Comando(palabra1, palabra2);
        }
        else {
            return new Comando(null, palabra2); 
        }
    }
}
