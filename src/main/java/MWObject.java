import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

abstract class MWObject implements Serializable {
   public int x;
   public int y;
   private boolean permanent;
   private boolean highlight;
   private Color color;

   MWObject(int x, int y) {
      this.x = x;
      this.y = y;
   }

   public abstract void draw(Graphics var1);

   public abstract void erase(Graphics var1);

   public abstract MWObject copy();

   public abstract void move(int var1, int var2);

   public abstract void translate(int var1, int var2);

   public abstract boolean inside(int var1, int var2);

   public void setPermanent(boolean flag) {
      this.permanent = flag;
   }

   public boolean isPermanent() {
      return this.permanent;
   }

   public void setColor(Color c) {
      this.color = c;
   }

   public Color getColor() {
      return this.color;
   }

   public void setHighlight(boolean flag) {
      this.highlight = flag;
   }

   public boolean isHighlighted() {
      return this.highlight;
   }
}
