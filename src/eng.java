// import java.util.HashMap;
// import java.util.ArrayList;
// import java.util.Arrays;
import java.awt.*;
import java.util.ArrayList;
// import java.util.Arrays;

public class eng {
    String[] types = {
        "function",
        "point",
        "variable",
        "eqution"
    };
    private static ArrayList<String> variabels = new ArrayList<String>();
    private static ArrayList<String> points = new ArrayList<String>();
    private static ArrayList<String> functions = new ArrayList<String>();

    public static element getType(String input) {
        String name = "nulll";
        if (input.contains("=")) {
            String[] elements = input.split("=");
            name = elements[0];
            input = elements[1];
        } else {
            for (char i = 'a'; i < 'z'; i++) {
                String letter = new String(new char[] {i});
                if (!variabels.contains(letter)) {
                    name = letter;
                    break;
                }
            }
        }
        if (input.contains(",")) {
            return createPoint(name,input);
        }
        return createfunction(name,input);
    }
    
    public final static punktermedbernard createPoint(String name, String input) {
        Color color = new Color((int)(Math.random() * 0x1000000));
        String[] vals = input.split(",");
        vals[0] = vals[0].substring(1);
        vals[1] = vals[1].substring(0,vals[1].length()-1);
        points.add(name);
        return new punktermedbernard(name, vals[0], vals[1], color);
    }

    public final static graph createfunction(String name, String input) {
        Color color = new Color((int)(Math.random() * 0x1000000));
        functions.add(name);
        return new graph(name, input, color);
    }

    public final static variable createvariable(String name, String input) {
        variabels.add(name);
        return new variable(name,input);
    }

    public static double eval(final String str) { // original code by boann
        return new Object() {
            int pos = -1, ch;
            
            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }
            
            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }
            
            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }
            
            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)` | number
            //        | functionName `(` expression `)` | functionName factor
            //        | factor `^` factor
            
            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }
            
            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else if (eat('%')) x %= parseFactor(); // mod
                    else return x;
                }
            }
            
            double parseFactor() {
                if (eat('+')) return +parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus
                
                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (eat('(')) {
                        x = parseExpression();
                        if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
                        if (func.equals("sqrt")) x = Math.sqrt(x);
                        else if (func.equals("sin")) x = Math.sin(x);
                        else if (func.equals("cos")) x = Math.cos(x);
                        else if (func.equals("tan")) x = Math.tan(x);
                        else if (func.equals("sign")) x = Math.signum(x);
                        else if (func.equals("abs")) x = Math.abs(x);
                        else if (func.equals("log")) x = Math.log10(x);
                        else if (func.equals("round")) x = Math.round(x);
                        else if (func.equals("floor")) x = Math.floor(x);
                        else if (func.equals("ceil")) x = Math.ceil(x);
                        else throw new RuntimeException("Unknown function: " + func);
                    } else {
                        if (func.equals("pi")) x = 3.14159265359;
                        else if (func.equals("e")) x = 2.71828182846;
                        else if (variabels.contains(func)) x = canvas.getvalue(func);
                        else throw new RuntimeException("Unknown variable: " + func);
                    }
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }
                
                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation
                
                return x;
            }
        }.parse();
    }

    public final static void clearall() {
        variabels = new ArrayList<String>();
        functions = new ArrayList<String>();
        points = new ArrayList<String>();
    }

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
