import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class list extends JComponent {
    static element[] elements = {
        eng.createfunction("f", "cos(x)*x-x*x"),
        eng.createfunction("fm", "cos(x)-x*sin(x)-2*x"),
        eng.createPoint("A", "(1,1)"),
        eng.createvariable("test", "-2.3")
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
            pan.add(new listelement(element.getName(), element.getExp(), element.color));
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
    }
    public static void add(element element) {
        pan.add(new listelement(element.getName(), element.getExp(), element.color));
    }

    public static double getvalue(String name) {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] instanceof variable) {
                variable var = (variable) elements[i];
                if (var.name.equals(name)) {
                    return var.getvalue();
                } 
            }
        }
        throw new RuntimeException("No variable called: " + name);
    }
    public static double getvalue(String name,Double x) {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] instanceof graph) {
                graph var = (graph) elements[i];
                if (var.name.equals(name)) {
                    return var.f(x);
                } 
            }
        }
        throw new RuntimeException("No function called: " + name);
    }
    public static point getpoint(String name) {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] instanceof punktermedbernard) {
                punktermedbernard var = (punktermedbernard) elements[i];
                if (var.name.equals(name)) {
                    return var.getvalue();
                } 
            }
        }
        throw new RuntimeException("No point called: " + name);
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
        infeild.setBounds(5,5, 210,20);
        infeild.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String text = infeild.getText();
                    // System.out.println(newElement);
                    if (!text.equals("clear")) {
                        element newElement = eng.getType(text);
                        list.elements = addelement(list.elements, newElement);
                        list.add(newElement);
                        list.pans.revalidate();
                        list.pans.repaint();
                        // System.out.println("b");
                    } else {
                        list.elements = new element[] {new variable("verylongbadname","0")};
                        eng.clearall();
                        list.clearall();
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
        g.drawRect(1, 1,getWidth()-2,getHeight()-2);
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
    Color color;
    String name;
    String fun;

    public listelement(String name,String fun, Color color) {
        this.color = color;
        this.name = name;
        this.fun = fun;
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
    /**
     * Circle
     */
    public class Circle extends JComponent{
        public Circle() {
            
        }
        @Override
        public void paint(Graphics g) {
            // TODO Auto-generated method stub
            super.paint(g);
        }
        
    }
}