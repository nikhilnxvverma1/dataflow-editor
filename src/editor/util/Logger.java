package editor.util;

/**
 * In house logging utility class. This is a facade we will change the implementation later.
 * TODO Handle initialization and maintenance of this utility. Currently it delegates to SOP statements
 */
public class Logger {

    public static void debug(String string){
        System.out.println(string);
    }
    public static void info(String string){
        System.out.println(string);
    }
}
