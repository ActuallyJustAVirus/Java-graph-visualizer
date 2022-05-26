import java.awt.*;    
// import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

public class toolbar extends JComponent{
    public toolbar() {
        add(new tool("Move","rsc/move.png"));
        add(new tool("Make a Point","rsc/point.png"));
        // setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setLayout(new FlowLayout(FlowLayout.LEFT));
    }
    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);
        g.fillOval(getX(), getY(), getWidth(), getHeight());
    }
}
class tool extends JButton{
    public tool(String name, String imagepath) {
        setName(name);
        setIcon(new ImageIcon(imagepath));
        // putClientProperty("JComponent.sizeVariant", "large");
        // setSize(100, 100);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillOval(getX(), getY(), getWidth(), getHeight());
    }
}