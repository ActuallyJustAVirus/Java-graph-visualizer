import java.awt.*;

public class graph extends element{
    final static boolean drawable = true;
    String function;
    public graph(String name, String function, Color color){
        this.function = function;
        this.color = color;
        this.name = name;
        f(1);
    }
    @Override
    public void draw(int start,int end, float zoom,int scrx,int scry,Graphics g){
        g.setColor(color);
        int lastx = 0;
        int lasty = 0;
        for (int i = start; i < end; i++) {
            int x = i-scrx;
            int y = (int)Math.round(-(f(i/zoom)*zoom+scry));
            if (y != 0) {
                g.drawLine(x,y,lastx,lasty);
                lastx = x;
                lasty = y;
            } else {
                lastx = i-scrx+1;
                lasty = (int)Math.round(-(f((i+1)/zoom)*zoom+scry));
            }
        }
    }
    public double f(double x){
        String strx = "("+String.format("%.10f", x)+")";
        strx = strx.replace(",","."); //ch >= 'a' && ch <= 'z'
        String fun = getreplace(function, strx);
        return eng.eval(fun);
    }
    @Override
    public String getName() {
        return name+"(x)";
    }
    @Override
    public String getFun() {
        return function;
    }
    private boolean isletter(char ch){
        if ((ch >= 'a' && ch <= 'z')||(ch >= 'A' && ch <= 'Z')) {
            return true;
        }
        return false;
    }
    private String replace(int index, String string,String replaceval) {
        return string.substring(0,index)+replaceval+string.substring(index+1);
    }
    private String getreplace(String fun, String strx) {
        char[] chars = fun.toCharArray();
        for (int j = 0; j < chars.length; j++) {
            if (chars[j]=='x') {
                if (j+1>=chars.length) {
                    if (!isletter(chars[j-1])) {
                        fun = replace(j, fun, strx);
                        return getreplace(fun, strx);
                    }
                } else {
                    if (!isletter(chars[j-1]) && !isletter(chars[j+1]) ) {
                        fun = replace(j, fun, strx);
                        return getreplace(fun, strx);
                    }
                }   
            }
        }
        return fun;
    }
}