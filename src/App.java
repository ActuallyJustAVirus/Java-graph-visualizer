import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.event.*;
import java.awt.*;
import javax.swing.event.MouseInputListener;

public class App {
    canvas canvas = new canvas();
    JFrame frame = new JFrame(":D");
    JTextField infeild = new JTextField("50");  
    ImageIcon img = new ImageIcon("rsc/icon.png");
    public static void main(String[] args) throws Exception {
        App app = new App();
        while (true) {
            app.tick();
            Thread.sleep(50);
        }
    }
    private void tick() {
        canvas.tick();
    }
    public App() {
        infeild.setBounds(10,10, 150,20);
        infeild.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){                  
                    canvas.zoom = Integer.valueOf(infeild.getText());
                    canvas.repaint();
                }
            }
        );
        
        frame.setIconImage(img.getImage());
        frame.add(infeild);
        frame.add(canvas);
        frame.setSize(614, 637);
		frame.setVisible(true);
        // frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
