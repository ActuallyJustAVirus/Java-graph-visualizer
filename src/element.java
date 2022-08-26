import java.awt.*;

public class element {
    boolean hide;
    protected String name;
    protected String expression;
    Object lastvalue;
    Color color;
    public element(String name, String expression) {
        this.name = name;
        this.expression = expression;
        lastvalue = eng.eval(expression, 0);
        if (lastvalue instanceof Double) {
            hide = true;
        } else {
            hide = false;
            color = Color.black;//new Color((int)(Math.random() * 0x1000000));
        }
    }
    
    public void draw(int start,int end, float zoom,int scrx,int scry,Graphics g){
        if (lastvalue instanceof segment) {
            g.setColor(color);
            lastvalue = eng.eval(expression, 0);
            segment s = (segment)lastvalue;
            point p1 = s.getstart();
            point p2 = s.getend();
            g.drawLine((int)(p1.getX()*zoom-scrx), (int)(-p1.getY()*zoom-scry), (int)(p2.getX()*zoom-scrx), (int)(-p2.getY()*zoom-scry));
        } else if (lastvalue instanceof point) {
            g.setColor(color);
            lastvalue = eng.eval(expression, 0);
            point p = (point)lastvalue;
            g.fillOval((int) ((p.getX())*zoom-scrx-3),(int) (-(p.getY())*zoom-scry-3), 6, 6);
        } else if (lastvalue instanceof circle) {
            g.setColor(color);
            lastvalue = eng.eval(expression, 0);
            circle circle = (circle)lastvalue;
            point c = circle.getmid();
            double r = circle.getradius();
            double scrr = r*zoom;
            g.drawOval((int)(c.getX()*zoom-scrr-scrx),(int) (-c.getY()*zoom-scrr-scry), (int)(scrr*2), (int)(scrr*2));
        } else if (lastvalue instanceof vector) {
            g.setColor(color);
            lastvalue = eng.eval(expression, 0);
            vector vector = (vector)lastvalue;
            g.drawLine(-scrx, -scry, (int)(vector.getX()*zoom-scrx), (int)(-vector.getY()*zoom-scry));
        }

    }
    public String getName(){
        return name;
    }
    public String getExp(){
        return expression;
    }
    public Object getvalue(){
        return lastvalue;
    }
}
