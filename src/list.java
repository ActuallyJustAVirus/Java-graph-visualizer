import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class list extends JComponent {
    static listelement[] elements= {};
    public static void name() {
        for (element element : canvas.elements) {
            System.out.println(element.name);
        }
    }
    public list() {
        // for (element element : canvas.elements) {
        //     add(new listelement(element.name, element.color));
        // }
        JComponent pan = new JPanel();
        pan.add(new input());
        pan.add(new input());
        pan.add(new JLabel("text"));pan.add(new JLabel("text"));pan.add(new JLabel("text"));
        pan.setLayout(new GridLayout(10,1));   
        setLayout (new GridLayout());   
        JScrollPane pans = new JScrollPane(pan);
        add(pans, BorderLayout.CENTER);
        // repaint();
    }
}
class input extends JComponent {

    @Override
    public void paint(Graphics g) {
        setBackground(Color.blue);
        super.paint(g);
        g.drawRect(getX()+5, getY()+5,getWidth()-10,getHeight()-10);
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
    public listelement(String text, Color color) {
        add(new JLabel(text));
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawOval(getX(), getY(), 20, 20);
    }
}