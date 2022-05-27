import java.awt.*;

public class punktermedbernard extends element {
    final static int size = 5;
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
        g.fillOval((int) ((eng.eval(x))*zoom-scrx-size/2),(int) (-(eng.eval(y))*zoom-scry-size/2), size, size);
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getFun() {
        return "("+x+","+y+")";
    }
}
