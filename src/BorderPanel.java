import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

class BorderPanel extends JPanel {
   public BorderPanel(String title) {
      this.setBorder(new TitledBorder(LineBorder.createGrayLineBorder(), title));
   }

   public BorderPanel() {
      this.setBorder(new TitledBorder(LineBorder.createGrayLineBorder()));
   }
}
