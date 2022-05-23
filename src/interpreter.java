import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;

public class interpreter {
    String[] types = {
        "function",
        "point",
        "variable",
        "eqution"
    };

    public static String getType(String input) {
        if (input.equals("clear")) {
            return "clear";
        }
        if (input.contains(",")) {
            return "point";
        }
        return "fun";
    }
    
    public static punktermedbernard createPoint(String input) {
        Color color = new Color((int)(Math.random() * 0x1000000));
        String[] vals = input.split(",");
        vals[0] = vals[0].substring(1);
        vals[1] = vals[1].substring(0,vals[1].length()-1);
        return new punktermedbernard(vals[0], vals[1], color);
    }
    
    // public static String[] getVariabals(String input) {
    //     return
    // }
    public static <T> boolean contains(final T[] array, final T v) {
        if (array instanceof graph[]) {
            
        }
        for (final T e : array){
            if (e == v || v != null && v.equals(e)){
                return true;
            }
        }
        return false;
    }
}
