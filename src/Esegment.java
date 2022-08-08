import java.awt.*;

public class Esegment extends element {
    final static boolean drawable = true;
    String expression;
    public Esegment(String name, String expression) {
        this.expression = expression;
        this.name = name;
        color = Color.BLACK;
        hide = false;
    }
    @Override
    public void draw(int start,int end, float zoom,int scrx,int scry,Graphics g){
        g.setColor(color);
        segment s = (segment)eng.eval(expression, 0);
        point p1 = s.getstart();
        point p2 = s.getend();
        g.drawLine((int)(p1.getX()*zoom-scrx), (int)(-p1.getY()*zoom-scry), (int)(p2.getX()*zoom-scrx), (int)(-p2.getY()*zoom-scry));
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getExp() {
        return expression;
    }
    public segment getvalue() {
        return (segment)eng.eval(expression,0);
    }
}
