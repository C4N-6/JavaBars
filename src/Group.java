import java.awt.Color;
import java.awt.Graphics;

class Group extends MWObject {
   public int x;
   public int y;
   public int lastx;
   public int lasty;
   private boolean permanent;
   private boolean highlight;
   private Color color;
   protected VList group = new VList();

   Group() {
      super(0, 0);
   }

   Group(MWObject o) {
      super(0, 0);
      this.add(o);
   }

   public void add(MWObject o) {
      this.group.addElement(o);
   }

   public void remove(MWObject o) {
      this.group.removeElement(o);
   }

   public int size() {
      return this.group.size();
   }

   @Override
   public void draw(Graphics g) {
      this.group.drawVListForwards(g);
   }

   @Override
   public void erase(Graphics g) {
      for (int i = 0; i < this.group.size(); i++) {
         ((MWObject)this.group.elementAt(i)).erase(g);
      }
   }

   @Override
   public MWObject copy() {
      Group gprime = new Group();

      for (int i = 0; i < this.group.size(); i++) {
         gprime.add(((MWObject)this.group.elementAt(i)).copy());
      }

      return gprime;
   }

   @Override
   public void move(int x, int y) {
      this.group.moveVList(x, y);
      this.x = x;
      this.y = y;
   }

   @Override
   public void translate(int dx, int dy) {
      this.group.translateVList(dx, dy);
      this.x += dx;
      this.y += dy;
   }

   @Override
   public boolean inside(int x, int y) {
      boolean flag = false;

      for (int i = 0; i < this.group.size(); i++) {
         flag = flag || ((MWObject)this.group.elementAt(i)).inside(x, y);
      }

      return flag;
   }

   @Override
   public void setPermanent(boolean flag) {
      for (int i = 0; i < this.group.size(); i++) {
         ((MWObject)this.group.elementAt(i)).setPermanent(flag);
      }
   }

   @Override
   public boolean isPermanent() {
      boolean flag = true;

      for (int i = 0; i < this.group.size(); i++) {
         flag = flag && ((MWObject)this.group.elementAt(i)).isPermanent();
      }

      return flag;
   }

   public void setColor() {
   }

   @Override
   public Color getColor() {
      return Color.white;
   }

   @Override
   public void setHighlight(boolean flag) {
      this.highlight = flag;

      for (int i = 0; i < this.group.size(); i++) {
         ((MWObject)this.group.elementAt(i)).setHighlight(flag);
      }
   }

   @Override
   public boolean isHighlighted() {
      boolean flag = true;

      for (int i = 0; i < this.group.size(); i++) {
         flag = flag && ((MWObject)this.group.elementAt(i)).isHighlighted();
      }

      return flag;
   }

   public boolean isEmpty() {
      return this.group.isEmpty();
   }
}
