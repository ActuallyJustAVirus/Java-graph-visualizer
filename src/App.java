import javax.swing.ImageIcon;
import javax.swing.JFrame;
// import javax.swing.JTextField;
// import java.awt.event.*;
// import java.awt.*;
// import javax.swing.event.MouseInputListener;

public class App {
    canvas canvas = new canvas();
    JFrame frame = new JFrame("Graph thing :D");
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
    }
    public App() {
              
        frame.setIconImage(img.getImage());
        // canvas.add(infeild);
        frame.add(canvas);
        frame.setSize(614, 637);
		frame.setVisible(true);
        // frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
