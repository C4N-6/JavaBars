import java.awt.Color;
import java.awt.Graphics;

class Mat extends ProtoBar {
   public int x;
   public int y;
   public int width;
   public int height;
   private String label;
   public int oldx;
   public int oldy;
   public int lastx;
   public int lasty;
   private Color color = new Color(250, 150, 150);
   private boolean permanent = false;
   private boolean highlight = false;
   public static final Color HIGHLIGHT_COLOR = new Color(100, 190, 210);

   Mat() {
   }

   Mat(int x, int y, int width, int height) {
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
   public MWObject copy() {
      Mat m = new Mat(this.x, this.y, this.width, this.height);
      m.x = this.x;
      m.y = this.y;
      m.width = this.width;
      m.height = this.height;
      m.setColor(this.getColor());
      m.setLabel(this.getLabel());
      m.setPermanent(this.isPermanent());
      m.setHighlight(this.isHighlighted());
      m.oldx = this.oldx;
      m.oldy = this.oldy;
      return m;
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
   public boolean inside(int x, int y) {
      return this.x <= x && x <= this.x + this.width && this.y <= y && y <= this.y + this.height;
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
   public void update(int mx, int my) {
      this.oldx = Math.max(mx, this.oldx);
      this.oldy = Math.max(my, this.oldy);
      this.x = Math.min(mx, this.x);
      this.y = Math.min(my, this.y);
   }

   @Override
   public void setLabel(String s) {
      this.label = s;
   }

   public String getLabel() {
      return this.label;
   }

   @Override
   public Color getColor() {
      return this.color;
   }

   @Override
   public void setColor(Color c) {
      this.color = c;
   }
}
