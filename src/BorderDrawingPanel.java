import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

class BorderDrawingPanel extends JPanel {
   BorderDrawingPanel(String title) {
      super(true);
   }

   protected void excludeBorder(Graphics g, Border border, int width, int height) {
      Rectangle r = AbstractBorder.getInteriorRectangle(this, border, 0, 0, width, height);
      g.clipRect(r.x, r.y, r.width, r.height);
   }

   @Override
   public Graphics getGraphics() {
      Dimension d = this.getSize();
      Graphics g = super.getGraphics();
      Border border = this.getBorder();
      if (border != null) {
         this.excludeBorder(g, border, d.width, d.height);
      }

      return g;
   }
}
