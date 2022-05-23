import javax.swing.JComponent;
import java.awt.*;

public class punktermedbernard extends JComponent {
    static final int size = 5;
    String x;
    String y;
    Color color;
    public punktermedbernard(String x, String y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    public void drawdot(float zoom,int scrx,int scry,Graphics g) {
        g.setColor(color);
        g.fillOval((int) ((math.eval(x))*zoom-scrx-size/2),(int) (-(math.eval(y))*zoom-scry-size/2), size, size);
    }
}
