import java.awt.*;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class list extends JComponent {
    static listelement[] elements= {};
    static JComponent pan = new JPanel();
    public static void name() {
        for (element element : canvas.elements) {
            System.out.println(element.name);
        }
    }
    public list() {
        pan.add(new input());
        for (element element : canvas.elements) {
            pan.add(new listelement(element.getName(), element.getFun(), element.color));
        }
        // pan.add(new JLabel("text"));pan.add(new JLabel("text"));pan.add(new JLabel("text"));
        pan.setLayout(new GridLayout(20,1));   
        setLayout (new GridLayout());   
        JScrollPane pans = new JScrollPane(pan);
        add(pans);
    }
    public static void clearall() {
        pan.removeAll();
        pan.add(new input());
        pan.setLayout(new GridLayout(20,1));
    }
    public static void add(element element) {
        pan.add(new listelement(element.getName(), element.getFun(), element.color));
        // pan.repaint();
    }
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(220,50);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(220,50);
    }
}
class input extends JComponent {

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(1, 1,getWidth()-2,getHeight()-2);
        g.drawRect(5, 5,getWidth()-10,getHeight()-30);
    }
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(200,50);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200,50);
    }
}

class listelement extends JComponent{
    Color color;
    String name;
    String fun;

    public listelement(String name,String fun, Color color) {
        this.color = color;
        this.name = name;
        this.fun = fun;
        // add(new JLabel(text));
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(1,1,getWidth()-2,getHeight()-2);
        if (color == null) {
            g.setColor(Color.black);
            g.drawOval(15, 15, 20, 20);
        } else {
            g.setColor(color);
            g.fillOval(15, 15, 20, 20);
            g.setColor(Color.black);
        }
        g.drawString(name+"="+fun, 45, 29);
    }
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(200,50);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200,50);
    }
}