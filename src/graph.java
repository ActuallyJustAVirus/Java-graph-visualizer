import java.awt.*;

public class graph extends element{
    final static boolean drawable = true;
    String function;
    public graph(String name, String function, Color color){
        this.function = function;
        this.color = color;
        this.name = name;
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
        strx = strx.replace(",",".");
        String fun = function.replace("x",String.valueOf(strx));
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
}