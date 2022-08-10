import java.awt.event.*;
import java.awt.*;
import javax.swing.JPanel;

public class canvas extends JPanel {
    static boolean showGrid = true;
    static boolean showAxis = true;
    double scrx = 0;
    double scry = 0;
    float zoom = 50;
    boolean M = false;
    boolean M3 = false;
    boolean UP = false;
    boolean DOWN = false;
    boolean LEFT = false;
    boolean RIGHT = false;
    boolean PAGE_DOWN = false;
    boolean PAGE_UP = false;
    double scroll = 0;
    int mousedownx = -1;
    int mousedowny = -1;

    
    
    public canvas() {
        MouseWheelListener mousewheellister = new Mymousewheellistener();
		addMouseWheelListener(mousewheellister);
        MouseListener mouselister = new Mymouselistener();
		addMouseListener(mouselister);
        KeyListener listener = new MyKeyListener();
        addKeyListener(listener);
        setFocusable(true);
        // System.out.println(f(-2));
        // double val = graphs[0].f(1);
        // System.out.println(val);
    }
    public void paint(Graphics g) {  
        int paintx = (int) (scrx*zoom)-getWidth()/2;
        int painty = (int) (scry*zoom)-getHeight()/2;
        g.clearRect(0, 0, getWidth(), getHeight());
        setBackground(Color.white);

        double gridsize = Math.pow(10,Math.round(Math.log10((50/zoom))));
        if (showGrid) {
            g.setColor(Color.lightGray);
            for (int i = -1; i < Math.ceil(getWidth()/(zoom*gridsize)); i++) {
                int x = (int) (zoom*gridsize*(i+1)-(paintx%(zoom*gridsize)));
                g.drawLine(x, 0,x, getHeight());
            }
            for (int i = -1; i < Math.ceil(getHeight()/(zoom*gridsize)); i++) {
                int y = (int) (zoom*gridsize*(i)-(painty%(zoom*gridsize)));
                g.drawLine(0, y,getWidth(),y);
            }
        }

        if (showAxis) {
            g.setColor(Color.black);
            g.drawLine(-paintx,0,-paintx,getHeight());//x
            g.drawLine(0,-painty,getWidth(),-painty);//y
        }

        for (element element : list.elements) {
            if (!element.hide) {
                element.draw(paintx, paintx+getWidth(), zoom, paintx, painty, g);
            }
        }

        g.setColor(Color.black);
        g.drawString("zoom: "+zoom, 10, 25);
        g.drawString("gridsize: "+gridsize, 10, 40);
        g.drawString("scrx: "+scrx+" scry: "+scry, 10, 55);
        g.drawString("scroll: "+scroll, 10, 70);
        // infeild.repaint();
    }
    



    // public double f(double x) {
    //     return (Math.cos(x)*x-x*x);
    // }
    // public double fm(double x) {
    //     return (Math.cos(x)-x*Math.sin(x)-2*x);
    // }
    // public double newton(double x) {
    //     return (x-f(x)/fm(x));
    // }

    

    public class Mymousewheellistener implements MouseWheelListener {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            double rot = e.getPreciseWheelRotation();
            if (rot < 0){
                System.out.println("mouse wheel Up"+rot);
            } else{
                System.out.println("mouse wheel Down"+rot);
            }
            if (e.isControlDown()) System.out.println("ctrl");
            if (e.isShiftDown()) System.out.println("shift");
            scroll += rot;
            // if (!e.isControlDown()){
            // }
            
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
                case KeyEvent.VK_PAGE_UP:
                    PAGE_UP = true;
                    break;
                case KeyEvent.VK_PAGE_DOWN:
                    PAGE_DOWN = true;
                    break;
                case KeyEvent.VK_UP:
                    UP = true;
                    break;
                case KeyEvent.VK_DOWN:
                    DOWN = true;
                    break;
                case KeyEvent.VK_LEFT:
                    LEFT = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    RIGHT = true;
                    break;
                case KeyEvent.VK_PLUS:
                    list.name();
                    break;
                case KeyEvent.VK_HOME:
                    scrx = 0;
                    scry = 0;
                    break;
                default:
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_PAGE_UP:
                    PAGE_UP = false;
                    break;
                case KeyEvent.VK_PAGE_DOWN:
                    PAGE_DOWN = false;
                    break;
                case KeyEvent.VK_UP:
                    UP = false;
                    break;
                case KeyEvent.VK_DOWN:
                    DOWN = false;
                    break;
                case KeyEvent.VK_LEFT:
                    LEFT = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    RIGHT = false;
                    break;
                default:
                    System.out.println("test");
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
            scrx += (mousedownx-mousex)/zoom;
            mousedownx = mousex;
            scry += (mousedowny-mousey)/zoom;
            mousedowny = mousey;
            // repaint();
        } else {
            mousedownx = -1;
            mousedowny = -1;
        }
        if (DOWN) scry += 15/zoom;
        if (UP) scry -= 15/zoom;
        if (LEFT) scrx -= 15/zoom;
        if (RIGHT) scrx += 15/zoom;
        if (PAGE_DOWN) {
            zoom /= 1.1;
            if (zoom<0.001) zoom = 0.001f;
        }
        if (PAGE_UP) {
            zoom *= 1.1;
            if (zoom>1.0E10) zoom = 1.0E10f;
        }
        if (scroll != 0) {
            zoom *= Math.pow(1.1, -scroll);
            if (zoom>1.0E10) zoom = 1.0E10f;
            if (zoom<0.001) zoom = 0.001f;
            scroll = 0;
        }
        // if (M3) {
        //     PointerInfo mouse = MouseInfo.getPointerInfo();
        //     Point mousePoint = mouse.getLocation();
        //     Point screen = getLocationOnScreen();
        //     dotx = (mousePoint.getX()-screen.getX()+scrx)/zoom;
        // }
        repaint();
    }
    
}