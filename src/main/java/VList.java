import java.awt.Graphics;
import java.util.Vector;

class VList extends Vector {
   VList() {
   }

   VList(int size) {
      super(size);
   }

   VList(int size, int increment) {
      super(size, increment);
   }

   public void drawVListForwards(Graphics g) {
      for (int i = 0; i < this.size(); i++) {
         ((MWObject)this.elementAt(i)).draw(g);
      }
   }

   public void drawVListBackwards(Graphics g) {
      for (int i = this.size() - 1; i >= 0; i--) {
         ((MWObject)this.elementAt(i)).draw(g);
      }
   }

   public void moveVList(int x, int y) {
      for (int i = 0; i < this.size(); i++) {
         ((MWObject)this.elementAt(i)).move(x, y);
      }
   }

   public void translateVList(int x, int y) {
      for (int i = 0; i < this.size(); i++) {
         ((MWObject)this.elementAt(i)).translate(x, y);
      }
   }

   public void union(VList vl) {
      for (int i = 0; i < vl.size(); i++) {
         Object v = vl.elementAt(i);
         if (!this.contains(v)) {
            this.addElement(v);
         }
      }
   }
}
