public class interpreter {
    String[] types = {
        "function",
        "point",
        "variable",
        "eqution"
    };

    public static String getType(String input) {
        char[] inputs = input.toCharArray();
        
        return "w";
    }
    public static <T> boolean contains(final T[] array, final T v) {
        for (final T e : array)
            if (e == v || v != null && v.equals(e))
                return true;
    
        return false;
    }
}
