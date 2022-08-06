// import java.util.HashMap;
// import java.util.ArrayList;
// import java.util.Arrays;
import java.awt.*;
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
    private static ArrayList<String> points = new ArrayList<String>();
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
            Object type = eng.eval(input,0);
            if (type instanceof point) {
                if (exists(name)) throw new RuntimeException("Point '"+name+"' already exist");
                return createPoint(name,input);
            }
            if (type instanceof Double) {
                if (exists(name)) throw new RuntimeException("Variable '"+name+"' already exist");
                return createvariable(name, input);
            }
            throw new RuntimeException("unknown type");
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
                    if (c < 'z'&&c >= 'a') {c++; nameL.set(i, c); break;} else nameL.set(i, 'a');
                }
            }
            if (input.contains("x")&&!input.contains("getx")) return createfunction(name,input);
            Object type = eng.eval(input, 0);
            if (type instanceof point) return createPoint(name, input);
            if (type instanceof segment) return createSegment(name, input);
            return createvariable(name,input);
        }
    }
    
    public final static punktermedbernard createPoint(String name, String input) {
        Color color = new Color((int)(Math.random() * 0x1000000));
        String[] vals = input.split(",");
        vals[0] = vals[0].substring(1);
        vals[1] = vals[1].substring(0,vals[1].length()-1);
        points.add(name);
        return new punktermedbernard(name, vals[0], vals[1], color);
    }
    public final static Esegment createSegment(String name, String input) {
        // points.add(name);
        return new Esegment(name, input);
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
            
            // point getPoint() {
            //     double x;
            //     double y;
            //     int startPos = this.pos;
            //     if (isletter(ch)) { // functions
            //         while (isletter(ch)) nextChar();
            //         String var = str.substring(startPos, this.pos);
            //         if (points.contains(var)) return list.getpoint(var);
            //         else throw new RuntimeException("Unknown point: " + var);
            //     }
            //     if (eat('(')) x = (double)parseExpression(); else throw new RuntimeException("Missing '(' for point");
            //     if (eat(',')) y = (double)parseExpression(); else throw new RuntimeException("Missing ',' for point");
            //     if (!eat(')')) throw new RuntimeException("Missing ')' for point");
            //     return new point(x, y);
            // }

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
                        switch (func) {
                            case "getx":
                            case "gety":// (point)
                                point p = (point)parseExpression();
                                if (!eat(')')) throw new RuntimeException("Missing ')' after point argument to " + func);
                                switch (func) {
                                    case "getx": x = p.getX(); break;
                                    case "gety": x = p.getY(); break;
                                    default: throw new RuntimeException("Missing point function: " + func);
                                }
                                break;
                            case "max":
                            case "min":// (double,double)
                                x = (double)parseExpression();
                                double y;
                                if (eat(',')) y = (double)parseExpression(); else throw new RuntimeException("Missing ',' in argument to " + func);
                                if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
                                switch (func) {
                                    case "max": x = Math.max(x,y); break;
                                    case "min": x = Math.min(x,y); break;
                                    default: throw new RuntimeException("No -1");
                                }
                                break;
                            case "segment":// (point,point)
                                point p1 = (point)parseExpression();
                                point p2;
                                if (eat(',')) p2 = (point)parseExpression(); else throw new RuntimeException("Missing ',' in argument to " + func);
                                if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
                                switch (func) {
                                    case "segment": return new segment(p1,p2);
                                    default: throw new RuntimeException("Missing function: " + func);
                                }
                            // case "sqrt":
                            // case "sin":
                            // case "cos":
                            // case "tan":
                            // case "sign":
                            // case "abs":
                            // case "log":
                            // case "round":
                            // case "floor":
                            // case "ceil": // (double)
                            default:
                                x = (double)parseExpression();
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
                                else if (functions.contains(func)) x = list.getvalue(func,x);
                                else throw new RuntimeException("Unknown function: " + func);
                                break;
                        }
                    } else {
                        if (func.equals("pi")) x = 3.14159265359;
                        else if (func.equals("e")) x = 2.71828182846;
                        else if (func.equals("x")) x = xx;
                        else if (variabels.contains(func)) x = list.getvalue(func);
                        else if (points.contains(func)) return list.getpoint(func);
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
        points = new ArrayList<String>();
    }

    private final static boolean exists(String name) {
        if (points.contains(name)) return true;
        if (variabels.contains(name)) return true;
        if (functions.contains(name)) return true;
        return false;
    }

    // public static <T> boolean contains(final T[] array, final T v) {
    //     if (array instanceof graph[]) {
    //     }
    //     for (final T e : array){
    //         if (e == v || v != null && v.equals(e)){
    //             return true;
    //         }
    //     }
    //     return false;
    // }
}
final class point {
    private double x;
    private double y;
    public point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
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
    public point getstart() {
        return x;
    }
    public point getend() {
        return y;
    }
    public String draw() {
        return "segment("+x.draw()+","+y.draw()+")";
    }
}