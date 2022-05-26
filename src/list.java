import javax.swing.JComponent;

public class list extends JComponent {
    public static void name() {
        for (element element : canvas.elements) {
            if (element instanceof graph) {
                graph graph = (graph) element;
                System.out.println(graph.name);
            }
        }
    }   
}
