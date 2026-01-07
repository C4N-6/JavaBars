import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;

class EdgedBorder implements Border {
   private Color edgeColor;
   private Color lightShadow;
   private Color darkShadow;
   private static final int OUTER_PAD = 2;
   private static final int BORDER_SIZE = 2;
   private static final int INNER_PAD = 2;
   private static final int BORDER_PAD = 6;

   EdgedBorder(Color edgeColor) {
      this.edgeColor = edgeColor;
   }

   public void setShadowColors(Color lightShadow, Color darkShadow) {
      this.lightShadow = lightShadow;
      this.darkShadow = darkShadow;
   }

   @Override
   public Insets getBorderInsets(Component comp) {
      return new Insets(6, 6, 6, 6);
   }

   @Override
   public void paintBorder(Component comp, Graphics g, int x, int y, int width, int height) {
      Color saveColor = g.getColor();
      int totalBorder = 12;
      if (this.edgeColor == null) {
         this.edgeColor = comp.getBackground();
         this.lightShadow = null;
         this.darkShadow = null;
      }

      if (this.lightShadow == null) {
         this.lightShadow = this.edgeColor.brighter();
         this.darkShadow = this.edgeColor.darker();
      }

      g.setColor(this.edgeColor);
      g.fillRect(x, y, width, 6);
      g.fillRect(x, y + height - 6, width, 6);
      g.fillRect(x, y + 6, 6, height - totalBorder);
      g.fillRect(x + width - 6, y + 6, 6, height - totalBorder);
      g.setColor(this.lightShadow);
      g.drawRect(x + 2 + 1, y + 2 + 1, width - 4 - 1, height - 4 - 1);
      g.setColor(this.darkShadow);
      g.drawRect(x + 2, y + 2, width - 4 - 1, height - 4 - 1);
      g.setColor(saveColor);
   }

   @Override
   public boolean isBorderOpaque() {
      return true;
   }
}
