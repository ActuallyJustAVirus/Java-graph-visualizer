import java.text.DecimalFormat;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
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
    graph[] graphs = {new graph("cos(x)*x-x*x", Color.pink)};
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
                    if (text.substring(0,0) != "c") {
                        graphs = addgraph(graphs, new graph(text,Color.red));
                        // System.out.println("b");
                    } else {
                        graph[] graphss = {new graph("x*2",Color.MAGENTA)};
                        graphs = graphss;
                    }
                }
            }
        );
        add(infeild);
    }
    public void paint(Graphics g) {  
        g.clearRect(0, 0, getWidth(), getHeight());
        setBackground(Color.white);

        g.setColor(Color.lightGray);
        for (int i = -1; i < Math.ceil(getWidth()/zoom); i++) {
            int x = (int) (zoom*(i+1)-(scrx%zoom));
            g.drawLine(x, 0,x, getHeight());
        }
        for (int i = -1; i < Math.ceil(getHeight()/zoom); i++) {
            int y = (int) (zoom*(i)-(scry%zoom));
            g.drawLine(0, y,getWidth(),y);
        }

        g.setColor(Color.black);
        g.drawLine(-scrx,0,-scrx,getHeight());//x
        g.drawLine(0,-scry,getWidth(),-scry);//y

        g.setColor(Color.red);
        for (graph graph : graphs) {
            graph.drawgraph(scrx, scrx+getWidth(), zoom, scrx, scry, g);
        }

        g.setColor(Color.blue);
        int lastx = 0;
        int lasty = 0;
        for (int i = scrx; i < scrx+getWidth(); i++) {
            int x = i-scrx;
            int y = (int)-(fm(i/zoom)*zoom+scry);
            if (y != 0) {
                g.drawLine(x,y,lastx,lasty);
                lastx = x;
                lasty = y;
            } else {
                lastx = i-scrx+1;
                lasty = (int)-(fm((i+1)/zoom)*zoom+scry);
            }
        }

        g.setColor(Color.green);
        int size = 5;
        g.fillOval((int) ((dotx)*zoom-scrx-size/2),(int) ((doty)*zoom-scry-size/2), size, size);
        g.setColor(Color.black);
        g.drawString(String.valueOf(dotx), 10, 10);
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
        String xx = String.format("%.10f", x);
        xx = xx.replace(",",".");
        String test = "cos("+xx+")-"+xx+"*sin("+xx+")-2*"+xx;
        return math.eval(test);//(Math.cos(x)-x*Math.sin(x)-2*x) // cirkel (2-Math.sqrt(4-Math.pow((x-2),2)))
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
            if (zoom<1) {
                zoom = 1;
            }
        }
        if (UP) {
            zoom *= 1.1;
        }
        dotx = newton(dotx);
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