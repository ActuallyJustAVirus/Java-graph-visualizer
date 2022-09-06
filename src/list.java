import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;
import javax.swing.*;
import javax.swing.JScrollPane;

public class list extends JComponent {
    static element[] elements = {
        eng.createfunction("f", "cos(x)*x-x*x"),
        eng.createfunction("fm", "cos(x)-x*sin(x)-2*x"),
        eng.createvariable("A", "(1,1)"),
        eng.createvariable("test", "-2.3")
        // eng.createvariable("B", "(test,2)"),
        // eng.createvariable("a", "segment(A,B)"),
        // eng.createvariable("c", "circle(A,3)")
    };
    static listelement[] listelements= {};
    static JComponent pan = new JPanel();
    static JScrollPane pans;
    public static void name() {
        for (element element : elements) {
            System.out.println(element.name);
        }
    }
    public list() {
        pan.add(new input());
        for (element element : elements) {
            pan.add(new listelement(element));
        }
        // pan.add(new JLabel("text"));pan.add(new JLabel("text"));pan.add(new JLabel("text"));
        pan.setLayout(new GridLayout(20,1));   
        setLayout (new GridLayout());   
        pans = new JScrollPane(pan);
        add(pans);
    }
    public static void clearall() {
        pan.removeAll();
        pan.add(new input());
        pan.setLayout(new GridLayout(20,1));
        pan.revalidate();
        pan.repaint();
    }
    public static void add(element element) {
        pan.add(new listelement(element));
        int gridrow = 20;
        if (pan.getComponentCount() > gridrow) gridrow = pan.getComponentCount();
        pan.setLayout(new GridLayout(gridrow,1));
    }

    public static Object getvalue(String name) {
        for (int i = 0; i < elements.length; i++) {
            if (!(elements[i] instanceof function)) {
                element var = elements[i];
                if (var.name.equals(name)) {
                    return var.getvalue();
                } 
            }
        }
        throw new RuntimeException("No variable called: " + name);
    }
    public static double getvalue(String name,Double x) {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] instanceof function) {
                function var = (function) elements[i];
                if (var.name.equals(name)) {
                    return var.f(x);
                } 
            }
        }
        throw new RuntimeException("No function called: " + name);
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
    JTextField infeild = new JTextField();  
    public input(){
        infeild.setBounds(5,5, 190,20);
        infeild.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String text = infeild.getText();
                    if (text.substring(0, 1).equals("/")) {
                        text = text.substring(1);
                        switch (text) {
                            case "clear":
                                list.elements = new element[] {new element("verylongbadname","0")};
                                eng.clearall();
                                list.clearall();
                                break;
                            default:
                                throw new RuntimeException("Unknown command: " + text);
                        }
                    } else {
                        element newElement = eng.getType(text);
                        list.elements = addelement(list.elements, newElement);
                        list.add(newElement);
                        list.pans.revalidate();
                        list.pans.repaint();
                    }
                }
            }
        );
        add(infeild);
    }

    public element[] addelement(element[] old,element toadd){
        ArrayList<element> gamer = new ArrayList<element>(Arrays.asList(old));
        gamer.add(toadd);
        old = gamer.toArray(old);
        return old;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(0,0,getWidth()-1,getHeight()-1);
        // g.drawRect(5, 5,getWidth()-10,getHeight()-30);
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
    element element;
    Circle circle;

    public listelement(element element) {
        this.element = element;
        circle = new Circle(element.color);
        add(circle);
        setLayout(null);
        circle.setBounds(14, 14, 20, 20);
        circle.addMouseListener(new MouseInputListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (circle.full) {
                    circle.full = false;
                    element.hide = true;
                } else {
                    circle.full = true;
                    element.hide = false;
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {   
            }
            @Override
            public void mouseEntered(MouseEvent e) {   
            }
            @Override
            public void mouseExited(MouseEvent e) {   
            }
            @Override
            public void mouseDragged(MouseEvent e) {   
            }
            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(0,0,getWidth()-1,getHeight()-1);
        g.setColor(Color.black);
        if (element.color == null) {
            g.drawOval(15, 15, 20, 20);
        } else {
            circle.repaint();
        }
        g.drawString(element.getName()+"="+element.getExp(), 45, 29);
        if (!(element instanceof function)) {
            if (element.lastvalue instanceof Double) g.drawString("> "+element.lastvalue, 45, 44);
            
            // else g.drawString("> "+element.lastvalue.draw(), 45, 44);
        }
    }
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(200,50);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200,50);
    }
    /**
     * Circle
     */
    public class Circle extends JComponent{
        Color color;
        boolean full = true;

        public Circle(Color color) {
            this.color = color;
        }
        @Override
        public void paint(Graphics g) {
            g.setColor(color);
            if (color == null) return;
            if (full) {
                g.fillOval(0, 0, 20, 20);
            } else {
                g.drawOval(0, 0, 20, 20);
                g.drawOval(1, 1, 18, 18);
            }
        }
    }
}

class bettermouselistner implements MouseInputListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("clicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("exited");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Dragged");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("moved");
    }
}