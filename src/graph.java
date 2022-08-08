import java.awt.*;

public class graph extends element{
    final static boolean drawable = true;
    String expression;
    public graph(String name, String function, Color color){
        hide = false;
        this.expression = function;
        this.color = color;
        this.name = name;
        f(1);
    }
    @Override
    public void draw(int start,int end, float zoom,int scrx,int scry,Graphics g){
        g.setColor(color);
        int lastx = -1;
        int lasty = -1;
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
        return (double)eng.eval(expression,x);
    }
    @Override
    public String getName() {
        return name+"(x)";
    }
    @Override
    public String getExp() {
        return expression;
    }
}