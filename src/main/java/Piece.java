import java.awt.Color;
import java.awt.Graphics;

class Piece extends MWObject {
   public int x;
   public int y;
   public int width;
   public int height;
   private boolean permanent = true;
   private boolean highlight = false;
   private boolean filled = false;
   private Color color = Color.red;
   public static final Color PIECES_COLOR = Color.white;
   public static final Color HIGHLIGHT_COLOR = new Color(100, 190, 210);

   Piece() {
      super(0, 0);
      this.x = 0;
      this.y = 0;
      this.width = 0;
      this.height = 0;
   }

   Piece(int x, int y, int width, int height) {
      super(x, y);
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   @Override
   public void draw(Graphics g) {
      Color oldColor = g.getColor();
      if (this.highlight) {
         this.drawHighlight(g);
      }

      if (this.filled) {
         g.setColor(this.color);
         g.fillRect(this.x, this.y, this.width, this.height);
      }

      g.setColor(PIECES_COLOR);
      g.drawRect(this.x, this.y, this.width, this.height);
      g.setColor(oldColor);
   }

   public void drawHighlight(Graphics g) {
      Color oldcolor = g.getColor();
      g.setColor(HIGHLIGHT_COLOR);

      for (int i = 1; i <= 3; i++) {
         g.drawRect(this.x + i, this.y + i, this.width - 2 * i, this.height - 2 * i);
      }

      g.setColor(oldcolor);
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
      Piece p = new Piece(this.x, this.y, this.width, this.height);
      p.setColor(this.color);
      return p;
   }

   @Override
   public void move(int x, int y) {
      this.x = x;
      this.y = y;
   }

   @Override
   public void translate(int dx, int dy) {
      this.x += dx;
      this.y += dy;
   }

   @Override
   public boolean inside(int x, int y) {
      return this.x <= x && x <= this.x + this.width && this.y <= y && y <= this.y + this.height;
   }

   @Override
   public void setPermanent(boolean flag) {
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
      if (this.color != color) {
         this.color = color;
         this.filled = true;
      }
   }

   @Override
   public Color getColor() {
      return this.color;
   }

   public boolean isFilled() {
      return this.filled;
   }
}
