// import java.awt.event.*;
// import java.util.function.Function;
// import java.util.function.Function;
import java.awt.*;

// import javax.swing.JPanel;
// import java.util.function.Function;

import javax.swing.JComponent;

public class graph extends JComponent{
    String function;
    Color color;
    public graph(String function, Color color){
        this.function = function;
        this.color = color;
    }
    public void drawgraph(int start,int end, float zoom,int scrx,int scry,Graphics g){
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
        String xx = String.format("%.10f", x);
        xx = xx.replace(",",".");
        String fun = function.replace("x",String.valueOf(xx));
        return math.eval(fun);
    }
}