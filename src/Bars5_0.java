import java.awt.Dimension;
import java.awt.Toolkit;

public class Bars5_0 {
   public static void main(String[] args) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      Dimension d = tk.getScreenSize();
      MWFrame f = new MWFrame("JavaBars", d.width, d.height);
      f.show();
   }
}
