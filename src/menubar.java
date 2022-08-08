import java.awt.event.*;
import javax.swing.KeyStroke;
import javax.swing.JCheckBoxMenuItem;
// import javax.swing.JFileChooser;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class menubar extends JMenuBar{
    JMenu file = new JMenu("File");
    JMenuItem saveItem = new JMenuItem("Save",KeyEvent.VK_S);
    JMenuItem openItem = new JMenuItem("Open",KeyEvent.VK_O);
    JMenuItem newItem = new JMenuItem("New",KeyEvent.VK_N);

    JMenu view = new JMenu("View");
    JCheckBoxMenuItem gridItem = new JCheckBoxMenuItem("Grid",true);
    JCheckBoxMenuItem axisItem = new JCheckBoxMenuItem("Axis",true);

    JMenu edit = new JMenu("Edit");
    JMenuItem colorItem = new JMenuItem("Change color",KeyEvent.VK_C);

    // JMenu view = new JMenu("View");
    public menubar(){
        file.add(saveItem);file.add(openItem);file.add(newItem);
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                list.elements = new element[] {new variable("verylongbadname","0")};
                eng.clearall();
                list.clearall();
            }
        });

        view.add(gridItem);view.add(axisItem);
        gridItem.setMnemonic(KeyEvent.VK_G);
        gridItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){  
                canvas.showGrid = gridItem.getState();
            }
        });
        axisItem.setMnemonic(KeyEvent.VK_A);
        axisItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){  
                canvas.showAxis = axisItem.getState();
            }
        });

        edit.add(colorItem);

        add(file);add(view);add(edit);
    }
}
