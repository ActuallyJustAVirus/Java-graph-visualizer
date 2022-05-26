import java.awt.*;    
// import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

public class toolbar extends JComponent{
    public toolbar() {
        add(new tool("Move","rsc/move.png"));
        // setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setLayout(new FlowLayout(FlowLayout.LEFT));
    }
}
class tool extends JButton{
    public tool(String name, String imagepath) {
        // this.setName(name);
        this.setIcon(new ImageIcon(imagepath));
    }
    
}