import java.awt.*;

public class punktermedbernard extends element {
    final static int size = 6;
    final static boolean drawable = true;
    String x;
    String y;
    public punktermedbernard(String name, String x, String y, Color color) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.color = color;
    }
    @Override
    public void draw(int start,int end, float zoom,int scrx,int scry,Graphics g){
        g.setColor(color);
        g.fillOval((int) (((double)eng.eval(x,0))*zoom-scrx-size/2),(int) (-((double)eng.eval(y,0))*zoom-scry-size/2), size, size);
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getExp() {
        return "("+x+","+y+")";
    }
    public point getvalue() {
        return new point((double)eng.eval(x,0), (double)eng.eval(y, 0));
    }
}
