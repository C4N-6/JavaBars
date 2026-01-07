import java.awt.Graphics;

class ProtoBar extends MWObject {
   public String label;
   public int width;
   public int height;
   public int oldx;
   public int oldy;
   public int lastx;
   public int lasty;

   ProtoBar() {
      super(0, 0);
      this.width = 0;
      this.height = 0;
      this.oldx = 0;
      this.oldy = 0;
   }

   ProtoBar(int x, int y, int width, int height) {
      super(x, y);
      this.width = width;
      this.height = height;
      this.oldx = this.x;
      this.oldy = this.y;
   }

   @Override
   public void draw(Graphics g) {
      g.drawRect(this.x, this.y, this.width, this.height);
   }

   @Override
   public void erase(Graphics g) {
   }

   @Override
   public MWObject copy() {
      return null;
   }

   @Override
   public boolean inside(int mx, int my) {
      return this.x <= mx && mx <= this.x + this.width && this.y <= my && my <= this.y + this.height;
   }

   public void update(int mx, int my) {
      this.oldx = Math.max(mx, this.oldx);
      this.oldy = Math.max(my, this.oldy);
      this.x = Math.min(mx, this.x);
      this.y = Math.min(my, this.y);
   }

   @Override
   public void move(int x, int y) {
      this.oldx = this.oldx + (x - this.x);
      this.oldy = this.oldy + (y - this.y);
      this.x = x;
      this.y = y;
   }

   @Override
   public void translate(int dx, int dy) {
      this.move(this.x + dx, this.y + dy);
   }

   public void setLabel(String text) {
      this.label = text;
   }
}
