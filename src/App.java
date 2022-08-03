import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

// import javax.swing.JTextField;
// import java.awt.event.*;
import java.awt.*;
// import javax.swing.event.MouseInputListener;

public class App {
    canvas canvas = new canvas();
    JFrame frame = new JFrame("GeoMaster :D");
    toolbar toolbar = new toolbar();
    list list = new list();
    menubar menubar = new menubar();
    // JTextField infeild = new JTextField("50");  
    ImageIcon img = new ImageIcon("rsc/icon.png");
    public static void main(String[] args) throws Exception {
        App app = new App();
        while (true) {
            app.tick();
            Thread.sleep(60);
        }
    }
    private void tick() {
        canvas.tick();
        list.repaint();
    }
    public App() {
        frame.setIconImage(img.getImage());
        // canvas.add(infeild);
        frame.add(canvas, BorderLayout.CENTER);
        frame.add(toolbar, BorderLayout.NORTH);
        frame.add(list, BorderLayout.WEST);
        frame.setJMenuBar(menubar);
        frame.setSize(800,700);
		frame.setVisible(true);
        // frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
