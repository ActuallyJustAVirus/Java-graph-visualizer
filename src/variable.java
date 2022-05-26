public class variable extends element{
    final static boolean drawable = false;
    String value;
    public variable(String name, String value) {
        this.name = name;
        this.value = value;
    }
    public double getvalue() {
        return eng.eval(value);
    }
}
