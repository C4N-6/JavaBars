import java.awt.Color;
import java.awt.Graphics;

class Cover extends ProtoBar {
   public int x;
   public int y;
   public int width;
   public int height;
   private String label;
   public int oldx;
   public int oldy;
   private Color color = Color.lightGray;
   private boolean permanent = false;
   private boolean highlight = false;
   public int lastx;
   public int lasty;
   public static final Color HIGHLIGHT_COLOR = new Color(100, 190, 210);

   Cover() {
   }

   Cover(int x, int y, int width, int height) {
      super(x, y, width, height);
   }

   @Override
   public void draw(Graphics g) {
      Color oldColor = g.getColor();
      if (this.isPermanent()) {
         if (this.highlight) {
            g.setColor(HIGHLIGHT_COLOR);

            for (int j = 1; j <= 4; j++) {
               g.drawRect(this.x - j, this.y - j, this.width + 2 * j, this.height + 2 * j);
            }
         }

         g.setColor(this.color);
         g.fillRect(this.x, this.y, this.width, this.height);
         g.setColor(Color.black);
         g.drawRect(this.x + 1, this.y + 1, this.width - 2, this.height - 2);
      } else {
         this.width = this.oldx > this.x ? this.oldx - this.x : this.x - this.oldx;
         this.height = this.oldy > this.y ? this.oldy - this.y : this.y - this.oldy;
         this.x = Math.min(this.x, this.oldx);
         this.y = Math.min(this.y, this.oldy);
         g.setColor(Color.black);
         g.drawRect(this.x + 1, this.y + 1, this.width - 2, this.height - 2);
      }

      g.setColor(oldColor);
   }

   @Override
   public void erase(Graphics g) {
      Color oldColor = g.getColor();
      g.setColor(Color.white);
      if (this.highlight) {
         g.fillRect(this.x - 4, this.y - 4, this.width + 8, this.height + 8);
      } else {
         g.fillRect(this.x, this.y, this.width, this.height);
      }

      g.setColor(oldColor);
   }

   @Override
   public void update(int mx, int my) {
      this.oldx = Math.max(mx, this.oldx);
      this.oldy = Math.max(my, this.oldy);
      this.x = Math.min(mx, this.x);
      this.y = Math.min(my, this.y);
   }

   @Override
   public MWObject copy() {
      Cover c = new Cover(this.x, this.y, this.width, this.height);
      c.x = this.x;
      c.y = this.y;
      c.width = this.width;
      c.height = this.height;
      c.setColor(this.getColor());
      c.setLabel(this.getLabel());
      c.setPermanent(this.isPermanent());
      c.setHighlight(this.isHighlighted());
      c.oldx = this.oldx;
      c.oldy = this.oldy;
      return c;
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
      this.oldx += dx;
      this.oldy += dy;
      this.x += dx;
      this.y += dy;
   }

   @Override
   public boolean inside(int x, int y) {
      return this.x <= x && x <= this.x + this.width && this.y <= y && y <= this.y + this.height;
   }

   @Override
   public void setLabel(String s) {
      this.label = s;
   }

   public String getLabel() {
      return this.label;
   }

   @Override
   public void setHighlight(boolean flag) {
      this.highlight = flag;
   }

   @Override
   public boolean isHighlighted() {
      return this.highlight;
   }

   @Override
   public void setColor(Color color) {
      this.color = color;
   }

   @Override
   public Color getColor() {
      return this.color;
   }
}
