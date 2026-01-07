import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

class Palette extends Canvas {
   private Color[][] myColor = new Color[][]{
      {Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.lightGray},
      {Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow}
   };
   public static int dotSize = 20;
   public static int spacerSize = 5;

   public Palette() {
      this.setSize(6 * dotSize + 7 * spacerSize, 2 * dotSize + 3 * spacerSize);
   }

   @Override
   public Dimension minimumSize() {
      return new Dimension(6 * dotSize + 7 * spacerSize, 2 * dotSize + 3 * spacerSize);
   }

   @Override
   public Dimension preferredSize() {
      return this.minimumSize();
   }

   public Color getColor(int i, int j) {
      return this.myColor[i][j];
   }

   @Override
   public void paint(Graphics g) {
      for (int i = 0; i < 2; i++) {
         for (int j = 0; j < 6; j++) {
            g.setColor(this.myColor[i][j]);
            g.fillRect((j + 1) * spacerSize + j * dotSize, (i + 1) * spacerSize + i * dotSize, dotSize, dotSize);
         }
      }
   }
}
