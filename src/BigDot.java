import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

class BigDot extends Canvas {
   Color myColor = Color.red;
   int mySize;

   public BigDot(Color theColor) {
      this.myColor = theColor;
      this.mySize = 2 * Palette.dotSize + Palette.spacerSize;
   }

   @Override
   public Dimension minimumSize() {
      return new Dimension(2 * Palette.dotSize + 3 * Palette.spacerSize, 2 * Palette.dotSize + 3 * Palette.spacerSize);
   }

   @Override
   public Dimension preferredSize() {
      return this.minimumSize();
   }

   public void setColor(Color theColor) {
      this.myColor = theColor;
   }

   @Override
   public void paint(Graphics g) {
      g.setColor(this.myColor);
      g.drawRect(Palette.spacerSize, Palette.spacerSize, this.mySize, this.mySize);
      g.fillRect(Palette.spacerSize, Palette.spacerSize, this.mySize, this.mySize);
   }
}
