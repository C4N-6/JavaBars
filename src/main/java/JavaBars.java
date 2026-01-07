import java.awt.Dimension;
import java.awt.Toolkit;

public class JavaBars {
  public static void main(String[] args) {
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    JavaBarFrame window = new JavaBarFrame("JavaBars", d.width, d.height);
    window.setVisible(true);
  }
}
