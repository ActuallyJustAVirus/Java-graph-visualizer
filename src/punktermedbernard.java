import javax.swing.JComponent;
import java.awt.*;

public class punktermedbernard extends JComponent {
    static final int size = 5;
    double x;
    double y;
    Color color;
    public punktermedbernard(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    public void drawdot(float zoom,int scrx,int scry,Graphics g) {
        g.setColor(color);
        g.fillOval((int) ((x)*zoom-scrx-size/2),(int) ((-y)*zoom-scry-size/2), size, size);
    }
}
