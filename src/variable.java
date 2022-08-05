import java.awt.Graphics;

public class variable extends element{
    final static boolean drawable = false;
    String value;
    public variable(String name, String value) {
        this.name = name;
        this.value = value;
    }
    public double getvalue() {
        return (double)eng.eval(value);
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getExp() {
        return value;
    }
    @Override
    public void draw(int start, int end, float zoom, int scrx, int scry, Graphics g) {
    }
}
