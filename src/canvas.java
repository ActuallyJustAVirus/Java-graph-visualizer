// import java.text.DecimalFormat;
import java.awt.event.*;
// import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
// import java.util.function.Function;
// import java.util.function.Function;
import java.awt.*;

import javax.swing.JPanel;
// import javax.swing.event.MouseInputListener;
import javax.swing.JTextField;

public class canvas extends JPanel {
    int scrx = -300;
    int scry = -300;
    float zoom = 50;
    boolean M = false;
    boolean M3 = false;
    boolean UP = false;
    boolean DOWN = false;
    double dotx = 4;
    double doty = 0;    
    int mousedownx = -1;
    int mousedowny = -1;
    graph[] graphs = {new graph("cos(x)*x-x*x", Color.red),new graph("cos(x)-x*sin(x)-2*x", Color.blue)};
    JTextField infeild = new JTextField("????????????????????????????????????????");  
    
    public canvas() {
        MouseWheelListener mousewheellister = new Mymousewheellistener();
		addMouseWheelListener(mousewheellister);
        MouseListener mouselister = new Mymouselistener();
		addMouseListener(mouselister);
        KeyListener listener = new MyKeyListener();
        addKeyListener(listener);
        setFocusable(true);
        // System.out.println(f(-2));
        infeild.setBounds(10,10, 150,20);
        infeild.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){                  
                    // zoom = Integer.valueOf(infeild.getText());
                    String text = infeild.getText();
                    if (!text.substring(0,1).equals("c")) {
                        graphs = addgraph(graphs, new graph(text,new Color((int)(Math.random() * 0x1000000))));
                        // System.out.println("b");
                    } else {
                        graph[] graphss = {new graph("0",Color.black)};
                        graphs = graphss;
                    }
                }
            }
        );
        add(infeild);
        // double val = graphs[0].f(1);
        // System.out.println(val);
    }
    public void paint(Graphics g) {  
        g.clearRect(0, 0, getWidth(), getHeight());
        setBackground(Color.white);

        double gridsize = Math.pow(10,Math.round(Math.log10((50/zoom))));//String.valueOf((int)Math.floor(100/zoom)).length()-1
        g.setColor(Color.lightGray);
        for (int i = -1; i < Math.ceil(getWidth()/(zoom*gridsize)); i++) {
            int x = (int) (zoom*gridsize*(i+1)-(scrx%(zoom*gridsize)));
            g.drawLine(x, 0,x, getHeight());
        }
        for (int i = -1; i < Math.ceil(getHeight()/(zoom*gridsize)); i++) {
            int y = (int) (zoom*gridsize*(i)-(scry%(zoom*gridsize)));
            g.drawLine(0, y,getWidth(),y);
        }

        g.setColor(Color.black);
        g.drawLine(-scrx,0,-scrx,getHeight());//x
        g.drawLine(0,-scry,getWidth(),-scry);//y

        for (graph graph : graphs) {
            graph.drawgraph(scrx, scrx+getWidth(), zoom, scrx, scry, g);
        }

        g.setColor(Color.green);
        int dotsize = 5;
        g.fillOval((int) ((dotx)*zoom-scrx-dotsize/2),(int) ((doty)*zoom-scry-dotsize/2), dotsize, dotsize);
        g.setColor(Color.black);
        g.drawString(String.valueOf(dotx), 10, 10);
        g.drawString("zoom: "+String.valueOf(zoom), 10, 25);
        g.drawString("gridsize: "+String.valueOf(gridsize), 10, 40);
        g.drawString("scrx: "+String.valueOf(scrx)+" scry: "+String.valueOf(scry), 10, 55);
        infeild.repaint();
    }



    public double f(double x) {
        // x = x/zoom;
        // Function ff = new Function("x^2");
        // Function<Double, Double> ff = xx -> xx / 2;
        return (Math.cos(x)*x-x*x);//(Math.cos(x)*x-x*x) //upper cirkel (2+Math.sqrt(4-Math.pow((x-2),2)))
    }
    public double fm(double x) {
        // x = x/zoom;
        // String xx = String.format("%.10f", x);
        // xx = xx.replace(",",".");
        // String test = "cos("+xx+")-"+xx+"*sin("+xx+")-2*"+xx;
        return (Math.cos(x)-x*Math.sin(x)-2*x);//(Math.cos(x)-x*Math.sin(x)-2*x) // cirkel (2-Math.sqrt(4-Math.pow((x-2),2)))
    }
    public double newton(double x) {
        return (x-f(x)/fm(x));
    }

    public graph[] addgraph(graph[] old,graph toadd){
        ArrayList<graph> gamer = new ArrayList<graph>(Arrays.asList(old));
        gamer.add(toadd);
        old = gamer.toArray(old);
        return old;
    }

    public class Mymousewheellistener implements MouseWheelListener {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.isControlDown()){
                if (e.getWheelRotation() < 0){
                    System.out.println("mouse wheel Up");
                }
                else{
                    System.out.println("mouse wheel Down");
                }
            }
            else{
                getParent().dispatchEvent(e);
            }
            
        }
    }
    
    public class Mymouselistener implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) {
            // System.out.println("press");
            switch (e.getButton()) {
                case 1:
                    M = true;
                    break;
                case 3:
                    M3 = true;
                    break;
                default:
                    System.out.println(e.getButton());
                    break;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // System.out.println("reales");
            switch (e.getButton()) {
                case 1:
                    M = false;
                    break;
                case 3:
                    M3 = false;
                    break;
                default:
                    System.out.println(e.getButton());
                    break;
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // System.out.println("enter");
        }
        @Override
        public void mouseExited(MouseEvent e) {
            // System.out.println("exit");
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            switch (e.getButton()) {
                case 2:
                    dotx = newton(dotx);
                    break;
                default:
                    // System.out.println(e.getButton());
                    break;
            }
        }
    }

    public class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // System.out.println(KeyEvent.getKeyText(e.getKeyCode()));
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    UP = true;
                    break;
                case KeyEvent.VK_DOWN:
                    DOWN = true;
                    break;
                default:
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    UP = false;
                    break;
                case KeyEvent.VK_DOWN:
                    DOWN = false;
                    break;
                default:
                    break;
            }
        }
    }



    public void tick() {
        if (M) {
            PointerInfo mouse = MouseInfo.getPointerInfo();
            Point mousePoint = mouse.getLocation();
            int mousex = (int) (mousePoint.getX());
            int mousey = (int) (mousePoint.getY());
            if (mousedownx == -1) {
                mousedownx = mousex;
                mousedowny = mousey;
            }   
            scrx += mousedownx-mousex;
            mousedownx = mousex;
            scry += mousedowny-mousey;
            mousedowny = mousey;
            repaint();
        } else {
            mousedownx = -1;
            mousedowny = -1;
        }
        if (DOWN) {
            zoom /= 1.1;
            // scrx *= 1.1;
            // scry *= 1.1;
            if (zoom<0.001) {
                zoom = 0.001f;
            }
        }
        if (UP) {
            zoom *= 1.1;
            // scrx /= 1.1;
            // scry /= 1.1;
        }
        if (M3) {
            PointerInfo mouse = MouseInfo.getPointerInfo();
            Point mousePoint = mouse.getLocation();
            Point screen = getLocationOnScreen();
            dotx = (mousePoint.getX()-screen.getX()+scrx)/zoom;
        }
        repaint();
    }
    
}
// class graph {
//     public double f(double x) {
//         x = x/50;
//         return (Math.cos(x)*x-x*x)*50;
//     }
// }
