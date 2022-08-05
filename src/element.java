import java.awt.*;

public abstract class element {
    static boolean drawable;
    String name;
    Color color;
    
    public abstract void draw(int start,int end, float zoom,int scrx,int scry,Graphics g);
    public abstract String getName();
    public abstract String getExp();
}
