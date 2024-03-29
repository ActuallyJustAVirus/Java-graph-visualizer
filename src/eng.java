// import java.util.HashMap;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.awt.*;
import java.util.ArrayList;
// import java.util.Arrays;
/*
function
point
variable
eqution
*/

public class eng {
    private static ArrayList<String> variabels = new ArrayList<String>();
    private static ArrayList<String> functions = new ArrayList<String>();

    public static element getType(String input) {
        if (input.contains("=")) {
            String[] elements = input.split("=");
            String name = elements[0];
            input = elements[1];
            if (name.contains("(x)")) {
                name = name.substring(0, name.length()-3);
                if (exists(name)) throw new RuntimeException("Function '"+name+"' already exist");
                return createfunction(name,input);
            }
            // Object type = eng.eval(input,0);
            if (exists(name)) throw new RuntimeException("Variable '"+name+"' already exist");
            return createvariable(name, input);
        } else {
            String name = "";
            ArrayList<Character> nameL = new ArrayList<Character>();
            for (int j = 0;;) {
                for (char i = 'a'; i <= 'z'; i++) {
                    if (nameL.size()-1 == j) nameL.set(j, i); else nameL.add(j, i);
                    name = "";
                    for (int k = 0; k <= nameL.size()-1; k++) {
                        name += nameL.get(k);
                    }
                    if (!exists(name)) break;
                }
                if (!exists(name)) break;
                nameL.set(nameL.size()-1, 'a');
                for (int i = nameL.size()-2; ; i--) {
                    if (i < 0) {
                        j++;
                        break;
                    }
                    char c = nameL.get(i);
                    if (c < 'z'&&c >= 'a') {c++; nameL.set(i, c); break;} 
                    else nameL.set(i, 'a');
                }
            }
            if (eng.havex(input)) return createfunction(name,input);
            // Object type = eng.eval(input, 0);
            return createvariable(name,input);
        }
    }

    public final static function createfunction(String name, String input) {
        functions.add(name);
        return new function(name, input);
    }

    public final static element createvariable(String name, String input) {
        variabels.add(name);
        return new element(name,input);
    }
    public static boolean havex(final String str){
        return new Object() {
            int pos = -1, ch;
            
            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }
            
            boolean parse() {
                for (int i = 0; i < str.length(); i++) {
                    nextChar();
                    int startPos = this.pos;
                    if (isletter(ch)) { // functions
                        while (isletter(ch)) nextChar();
                        String func = str.substring(startPos, this.pos);
                        if (func.equals("x")) return true;
                    }
                }
                return false;
            }

            boolean isletter(int ch){
                if ((ch >= 'a' && ch <= 'z')||(ch >= 'A' && ch <= 'Z')) {
                    return true;
                }
                return false;
            }
        }.parse();
    }

    public static Object eval(final String str, final double xx) { // original code by boann
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
            
            Object parse() {
                nextChar();
                Object x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            boolean isletter(int ch){
                if ((ch >= 'a' && ch <= 'z')||(ch >= 'A' && ch <= 'Z')) {
                    return true;
                }
                return false;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)` | number
            //        | functionName `(` expression `)` | functionName factor
            //        | factor `^` factor
            
            Object parseExpression() {
                Object x = parseTerm();
                if (x instanceof Double) {
                    double dx = (double)x;
                    for (;;) {
                        if      (eat('+')) dx += (double)parseTerm(); // addition
                        else if (eat('-')) dx -= (double)parseTerm(); // subtraction
                        else return dx;
                    }
                }
                return x;
            }
            
            Object parseTerm() {
                Object x = parseFactor();
                if (x instanceof Double) {
                    double dx = (double)x;
                    for (;;) {
                        if      (eat('*')) dx *= (double)parseFactor(); // multiplication
                        else if (eat('/')) dx /= (double)parseFactor(); // division
                        else if (eat('%')) dx %= (double)parseFactor(); // mod
                        else return dx;
                    }
                }
                return x;
            }
            
            Object parseFactor() {
                if (eat('+')) return +(double)parseFactor(); // unary plus
                if (eat('-')) return -(double)parseFactor(); // unary minus
                
                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = (double)parseExpression();
                    if (eat(',')) {
                        double y = (double)parseExpression();
                        if (!eat(')')) throw new RuntimeException("Missing ')'");
                        return new point(x, y);
                    }
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (isletter(ch)) { // functions
                    while (isletter(ch)) nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (eat('(')) {
                        ArrayList<Object> in = new ArrayList<Object>();
                        for (;;){
                            in.add(parseExpression());
                            if (!eat(',')) break;
                        }
                        if (!eat(')')) throw new RuntimeException("Missing ')' after point argument to " + func);
                        switch (func) {
                            case "getx": x = ((point)in.get(0)).getX(); break;
                            case "gety": x = ((point)in.get(0)).getY(); break;
                            case "max": x = Math.max((Double)in.get(0),(Double)in.get(1)); break;
                            case "min": x = Math.min((Double)in.get(0),(Double)in.get(1)); break;
                            case "vector": return new vector((Double)in.get(0),(Double)in.get(1));
                            case "segment": return new segment((point)in.get(0),(point)in.get(1));
                            case "circle": return new circle((point)in.get(0),(Double)in.get(1));
                            case "sqrt": x = Math.sqrt((Double)in.get(0)); break;
                            case "sin": x = Math.sin((Double)in.get(0)); break;
                            case "cos": x = Math.cos((Double)in.get(0)); break;
                            case "tan": x = Math.tan((Double)in.get(0)); break;
                            case "sign": x = Math.signum((Double)in.get(0)); break;
                            case "abs": x = Math.abs((Double)in.get(0)); break;
                            case "log": x = Math.log10((Double)in.get(0)); break;
                            case "round": x = Math.round((Double)in.get(0)); break;
                            case "floor": x = Math.floor((Double)in.get(0)); break;
                            case "ceil": x = Math.ceil((Double)in.get(0)); break;
                            default: // throw new RuntimeException("Missing function called: " + func);
                                if (functions.contains(func)) x = list.getvalue(func,(double)in.get(0));
                                else throw new RuntimeException("Unknown function: " + func);
                                break;
                        }
                    } else {
                        if (func.equals("pi")) x = 3.14159265359;
                        else if (func.equals("e")) x = 2.71828182846;
                        else if (func.equals("x")) x = xx;
                        else if (variabels.contains(func)) return list.getvalue(func);
                        // else if (points.contains(func)) return list.getpoint(func);
                        else throw new RuntimeException("Unknown variable: " + func);
                    }
                } else {
                    throw new RuntimeException("Unexpected2: " + (char)ch);
                }
                
                if (eat('^')) x = Math.pow(x, (double)parseFactor()); // exponentiation
                
                return x;
            }
        }.parse();
    }

    public final static void clearall() {
        variabels = new ArrayList<String>();
        functions = new ArrayList<String>();
    }

    private final static boolean exists(String name) {
        if (variabels.contains(name)) return true;
        if (functions.contains(name)) return true;
        return false;
    }
}
final class point {
    private double x;
    private double y;
    public point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() {return x;}
    public double getY() {return y;}
    public String draw() {
        return "("+x+","+y+")";
    }
}
final class segment {
    private point x;
    private point y;
    public segment(point x, point y) {
        this.x = x;
        this.y = y;
    }
    public point getstart() {return x;}
    public point getend() {return y;}
    public String draw() {
        return "segment("+x.draw()+","+y.draw()+")";
    }
}
final class circle {
    private double r;
    private point c;
    public circle(point c,double r) {
        this.c = c;
        this.r = r;
    }
    public point getmid() {return c;}
    public double getradius() {return r;}
    public String draw() {
        return "circle("+c.draw()+","+r+")";
    }
}
final class vector {
    private double x;
    private double y;
    public vector(double x,double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() {return x;}
    public double getY() {return y;}
    public String draw() {
        return "vector("+x+","+y+")";
    }
}