import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

class Bar extends ProtoBar {
   public int x;
   public int y;
   public int width;
   public int height;
   public int[] fwidth = new int[3];
   public int[] fheight = new int[3];
   public VList parts = new VList();
   public VList pieces = new VList();
   private Color color = Color.red;
   private String label;
   private boolean permanent = false;
   private boolean highlight = false;
   public int oldx;
   public int oldy;
   public int lastx;
   public int lasty;
   public static final Color HIGHLIGHT_COLOR = new Color(100, 190, 210);
   public String last_cut_direction;

   Bar() {
      this.x = 0;
      this.y = 0;
      this.width = 0;
      this.height = 0;
      this.fwidth[0] = 0;
      this.fwidth[1] = 1;
      this.fwidth[2] = 1;
      this.fheight[0] = 0;
      this.fheight[1] = 1;
      this.fheight[2] = 1;
      this.oldx = 0;
      this.oldy = 0;
   }

   Bar(int x, int y, int width, int height) {
      super(x, y, width, height);
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.fwidth[0] = width;
      this.fwidth[1] = 1;
      this.fwidth[2] = 1;
      this.fheight[0] = height;
      this.fheight[1] = 1;
      this.fheight[2] = 1;
      this.oldx = x + width;
      this.oldy = y + height;
   }

   Bar(int x, int y, int basewidth, int width_numerator, int width_denominator, int baseheight, int height_numerator, int height_denominator) {
      super(x, y, basewidth * width_numerator / width_denominator, baseheight * height_numerator / height_denominator);
      this.x = x;
      this.y = y;
      this.width = basewidth * width_numerator / width_denominator;
      this.fwidth[0] = basewidth;
      this.fwidth[1] = width_numerator;
      this.fwidth[2] = width_denominator;
      this.height = baseheight * height_numerator / height_denominator;
      this.fheight[0] = baseheight;
      this.fheight[1] = height_numerator;
      this.fheight[2] = height_denominator;
      this.oldx = x + this.width;
      this.oldy = y + this.height;
   }

   @Override
   public void draw(Graphics g) {
      Color oldColor = g.getColor();
      if (this.isPermanent()) {
         g.setColor(this.color);
         g.fillRect(this.x, this.y, this.width, this.height);
         g.setColor(Color.black);
         g.drawRect(this.x + 1, this.y + 1, this.width - 2, this.height - 2);
         if (this.haveParts()) {
            this.parts.drawVListForwards(g);
         }

         if (this.havePieces()) {
            this.pieces.drawVListForwards(g);
         }

         if (this.highlight) {
            this.drawHighlight(g);
         }

         if (this.label != null) {
            g.drawString(this.label, this.x + 10, this.y + 12);
         }
      } else {
         g.setColor(this.color);
         g.drawRect(this.x + 1, this.y + 1, this.width - 2, this.height - 2);
      }

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
   public void update(int mx, int my) {
      this.oldx = Math.max(mx, this.oldx);
      this.oldy = Math.max(my, this.oldy);
      this.x = Math.min(mx, this.x);
      this.y = Math.min(my, this.y);
      this.fwidth[0] = this.oldx - this.x;
      this.fheight[0] = this.oldy - this.y;
      this.width = this.fwidth[0] * this.fwidth[1] / this.fwidth[2];
      this.height = this.fheight[0] * this.fheight[1] / this.fheight[2];
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
      Bar b = new Bar(this.x, this.y, this.width, this.height);
      b.x = this.x;
      b.y = this.y;
      b.width = this.width;
      b.height = this.height;
      b.setColor(this.getColor());
      b.parts = this.copyParts();
      b.pieces = this.copyPieces();
      b.fwidth[0] = this.fwidth[0];
      b.fwidth[1] = this.fwidth[1];
      b.fwidth[2] = this.fwidth[2];
      b.fheight[0] = this.fheight[0];
      b.fheight[1] = this.fheight[1];
      b.fheight[2] = this.fheight[2];
      b.setPermanent(this.isPermanent());
      b.setHighlight(this.isHighlighted());
      b.oldx = this.oldx;
      b.oldy = this.oldy;
      if (this.getLabel() != "Unit Bar") {
         b.setLabel(this.getLabel());
      }

      return b;
   }

   public VList copyParts() {
      VList plist = new VList(this.parts.size());

      for (int i = 0; i < this.parts.size(); i++) {
         Part p = (Part)this.parts.elementAt(i);
         Part p1 = new Part(p.x, p.y, p.fwidth[0], p.fwidth[1], p.fwidth[2], p.fheight[0], p.fheight[1], p.fheight[2]);
         p1.setColor(p.getColor());
         plist.addElement(p1);
      }

      return plist;
   }

   public VList copyPieces() {
      VList plist = new VList(this.pieces.size());

      for (int i = 0; i < this.pieces.size(); i++) {
         Piece p = (Piece)this.pieces.elementAt(i);
         Piece p1 = new Piece(p.x, p.y, p.width, p.height);
         p1.setColor(p.getColor());
         plist.addElement(p1);
      }

      return plist;
   }

   @Override
   public void move(int x, int y) {
      if (this.haveParts()) {
         this.parts.translateVList(x - this.x, y - this.y);
      }

      if (this.havePieces()) {
         this.pieces.translateVList(x - this.x, y - this.y);
      }

      this.oldx = this.oldx + (x - this.x);
      this.oldy = this.oldy + (y - this.y);
      this.x = x;
      this.y = y;
   }

   @Override
   public void translate(int dx, int dy) {
      if (this.haveParts()) {
         this.parts.translateVList(dx, dy);
      }

      if (this.havePieces()) {
         this.pieces.translateVList(dx, dy);
      }

      this.oldx += dx;
      this.oldy += dy;
      this.x += dx;
      this.y += dy;
   }

   public void joinFragments(Bar b) {
      if (b.haveParts()) {
         if (this.haveParts()) {
            for (int i = 0; i < b.parts.size(); i++) {
               this.addPart((Part)b.parts.elementAt(i));
            }
         } else {
            this.addPart(new Part(this.x, this.y, this.width, this.height));

            for (int i = 0; i < b.parts.size(); i++) {
               this.addPart((Part)b.parts.elementAt(i));
            }
         }
      } else if (this.haveParts()) {
         this.addPart(new Part(b.x, b.y, b.width, b.height));
      } else if (this.height == b.height && this.width == b.width) {
         this.addPart(new Part(this.x, this.y, this.width, this.height));
         this.addPart(new Part(b.x, b.y, b.width, b.height));
      }

      if (b.havePieces()) {
         if (this.havePieces()) {
            for (int i = 0; i < b.pieces.size(); i++) {
               this.addPiece((Piece)b.pieces.elementAt(i));
            }
         } else {
            this.addPiece(new Piece(this.x, this.y, this.width, this.height));

            for (int i = 0; i < b.pieces.size(); i++) {
               this.addPiece((Piece)b.pieces.elementAt(i));
            }
         }
      } else if (this.havePieces()) {
         this.addPiece(new Piece(b.x, b.y, b.width, b.height));
      } else if (!b.haveParts() && !this.haveParts() && (this.width != b.width || this.height != b.height)) {
         this.addPiece(new Piece(this.x, this.y, this.width, this.height));
         this.addPiece(new Piece(b.x, b.y, b.width, b.height));
      }
   }

   public boolean makeSingleBar(Bar b, int mx, int my) {
      if (my - this.y < (float)this.height / this.width * (mx - this.x) && my - this.y < (float)this.height / this.width * (this.x + this.width - mx)) {
         if (!this.isSameFractionalWidth(b) && this.width != b.width) {
            return false;
         } else {
            this.y = this.y - b.height;
            this.calcFractionalHeight(b);
            return true;
         }
      } else if (my - this.y > (float)this.height / this.width * (mx - this.x) && my - this.y < (float)this.height / this.width * (this.x + this.width - mx)) {
         if (!this.isSameFractionalHeight(b) && this.height != b.height) {
            return false;
         } else {
            this.x = this.x - b.width;
            this.calcFractionalWidth(b);
            return true;
         }
      } else if (!(my - this.y > (float)this.height / this.width * (this.x + this.width - mx))
         || !(my - this.y > (float)this.height / this.width * (mx - this.x))) {
         if (!(my - this.y > (float)this.height / this.width * (this.x + this.width - mx)) || !(my - this.y < (float)this.height / this.width * (mx - this.x))) {
            return false;
         } else if (!this.isSameFractionalHeight(b) && this.height != b.height) {
            return false;
         } else {
            this.calcFractionalWidth(b);
            return true;
         }
      } else if (!this.isSameFractionalWidth(b) && this.width != b.width) {
         return false;
      } else {
         this.calcFractionalHeight(b);
         return true;
      }
   }

   public boolean isSameFractionalWidth(Bar b) {
      return this.fwidth[0] == b.fwidth[0] && this.fwidth[1] == b.fwidth[1] && this.fwidth[2] == b.fwidth[2];
   }

   public boolean isSameFractionalHeight(Bar b) {
      return this.fheight[0] == b.fheight[0] && this.fheight[1] == b.fheight[1] && this.fheight[2] == b.fheight[2];
   }

   public void calcFractionalWidth(Bar b) {
      if (this.fwidth[0] == b.fwidth[0]) {
         int numerator = this.fwidth[1] * b.fwidth[2] + this.fwidth[2] * b.fwidth[1];
         int denominator = this.fwidth[2] * b.fwidth[2];
         int gcf = MyMath.GCF(numerator, denominator);
         this.fwidth[1] = numerator / gcf;
         this.fwidth[2] = denominator / gcf;
         this.width = this.fwidth[1] * this.fwidth[0] / this.fwidth[2];
      } else if (this.fwidth[0] != b.fwidth[0]) {
         this.fwidth[0] = this.width + b.width;
         this.fwidth[1] = 1;
         this.fwidth[2] = 1;
         this.width = this.fwidth[0];
      }
   }

   public void calcFractionalHeight(Bar b) {
      if (this.fheight[0] == b.fheight[0]) {
         int numerator = this.fheight[1] * b.fheight[2] + this.fheight[2] * b.fheight[1];
         int denominator = this.fheight[2] * b.fheight[2];
         int gcf = MyMath.GCF(numerator, denominator);
         this.fheight[1] = numerator / gcf;
         this.fheight[2] = denominator / gcf;
         this.height = this.fheight[1] * this.fheight[0] / this.fheight[2];
      } else if (this.fheight[0] != b.fheight[0]) {
         this.fheight[0] = this.height + b.height;
         this.fheight[1] = 1;
         this.fheight[2] = 1;
         this.height = this.fheight[0];
      }
   }

   public boolean joinBars(Bar b, int mx, int my) {
      if (my - this.y < (float)this.height / this.width * (mx - this.x) && my - this.y < (float)this.height / this.width * (this.x + this.width - mx)) {
         if (!this.isSameFractionalWidth(b) && this.width != b.width) {
            return false;
         } else {
            int y0 = this.y;
            this.y = this.y - b.height;
            if (b.haveParts()) {
               b.parts.translateVList(this.x - b.x, this.y - b.y);
               if (this.haveParts()) {
                  this.parts.union(b.parts);
               } else {
                  this.parts = b.parts;
                  this.addPart(new Part(this.x, y0, this.fwidth[0], this.fwidth[1], this.fwidth[2], this.fheight[0], this.fheight[1], this.fheight[2]));
               }
            } else if (this.haveParts()) {
               this.addPart(new Part(this.x, this.y, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
            } else if (!this.havePieces() && !b.havePieces()) {
               this.addPart(new Part(this.x, this.y, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
               this.addPart(
                  new Part(this.x, this.y + b.height, this.fwidth[0], this.fwidth[1], this.fwidth[2], this.fheight[0], this.fheight[1], this.fheight[2])
               );
            }

            if (b.havePieces()) {
               b.pieces.translateVList(this.x - b.x, this.y - b.y);
               if (this.havePieces()) {
                  this.pieces.union(b.pieces);
               } else {
                  this.pieces = b.pieces;
                  this.addPiece(new Piece(this.x, y0, this.width, this.height));
               }
            } else if (this.havePieces()) {
               this.addPiece(new Piece(this.x, this.y, b.width, b.height));
            }

            this.calcFractionalHeight(b);
            return true;
         }
      } else if (my - this.y > (float)this.height / this.width * (mx - this.x) && my - this.y < (float)this.height / this.width * (this.x + this.width - mx)) {
         if (!this.isSameFractionalHeight(b) && this.height != b.height) {
            return false;
         } else {
            int x0 = this.x;
            this.x = this.x - b.width;
            if (b.haveParts()) {
               b.parts.translateVList(this.x - b.x, this.y - b.y);
               if (this.haveParts()) {
                  this.parts.union(b.parts);
               } else {
                  this.parts = b.parts;
                  this.addPart(new Part(x0, this.y, this.fwidth[0], this.fwidth[1], this.fwidth[2], this.fheight[0], this.fheight[1], this.fheight[2]));
               }
            } else if (this.haveParts()) {
               this.addPart(new Part(this.x, this.y, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
            } else if (!this.havePieces() && !b.havePieces()) {
               this.addPart(new Part(this.x, this.y, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
               this.addPart(
                  new Part(this.x + b.width, this.y, this.fwidth[0], this.fwidth[1], this.fwidth[2], this.fheight[0], this.fheight[1], this.fheight[2])
               );
            }

            if (b.havePieces()) {
               b.pieces.translateVList(this.x - b.x, this.y - b.y);
               if (this.havePieces()) {
                  this.pieces.union(b.pieces);
               } else {
                  this.pieces = b.pieces;
                  this.addPiece(new Piece(x0, this.y, this.width, this.height));
               }
            } else if (this.havePieces()) {
               this.addPiece(new Piece(this.x, this.y, b.width, b.height));
            }

            this.calcFractionalWidth(b);
            return true;
         }
      } else if (!(my - this.y > (float)this.height / this.width * (this.x + this.width - mx))
         || !(my - this.y > (float)this.height / this.width * (mx - this.x))) {
         if (!(my - this.y > (float)this.height / this.width * (this.x + this.width - mx)) || !(my - this.y < (float)this.height / this.width * (mx - this.x))) {
            return false;
         } else if (!this.isSameFractionalHeight(b) && this.height != b.height) {
            return false;
         } else {
            if (b.haveParts()) {
               b.parts.translateVList(this.x + this.width - b.x, this.y - b.y);
               if (this.haveParts()) {
                  this.parts.union(b.parts);
               } else {
                  this.parts = b.parts;
                  this.addPart(new Part(this.x, this.y, this.fwidth[0], this.fwidth[1], this.fwidth[2], this.fheight[0], this.fheight[1], this.fheight[2]));
               }
            } else if (this.haveParts()) {
               this.addPart(new Part(this.x + this.width, this.y, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
            } else if (!this.havePieces() && !b.havePieces()) {
               this.addPart(new Part(this.x + this.width, this.y, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
               this.addPart(new Part(this.x, this.y, this.fwidth[0], this.fwidth[1], this.fwidth[2], this.fheight[0], this.fheight[1], this.fheight[2]));
            }

            if (b.havePieces()) {
               b.pieces.translateVList(this.x + this.width - b.x, this.y - b.y);
               if (this.havePieces()) {
                  this.pieces.union(b.pieces);
               } else {
                  this.pieces = b.pieces;
                  this.addPiece(new Piece(this.x, this.y, this.width, this.height));
               }
            } else if (this.havePieces()) {
               this.addPiece(new Piece(this.x + this.width, this.y, b.width, b.height));
            }

            this.calcFractionalWidth(b);
            return true;
         }
      } else if (!this.isSameFractionalWidth(b) && this.width != b.width) {
         return false;
      } else {
         if (b.haveParts()) {
            b.parts.translateVList(this.x - b.x, this.y + this.height - b.y);
            if (this.haveParts()) {
               this.parts.union(b.parts);
            } else {
               this.parts = b.parts;
               this.addPart(new Part(this.x, this.y, this.fwidth[0], this.fwidth[1], this.fwidth[2], this.fheight[0], this.fheight[1], this.fheight[2]));
            }
         } else if (this.haveParts()) {
            this.addPart(new Part(this.x, this.y + this.height, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
         } else if (!this.havePieces() && !b.havePieces()) {
            this.addPart(new Part(this.x, this.y + this.height, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
            this.addPart(new Part(this.x, this.y, this.fwidth[0], this.fwidth[1], this.fwidth[2], this.fheight[0], this.fheight[1], this.fheight[2]));
         }

         if (b.havePieces()) {
            b.pieces.translateVList(this.x - b.x, this.y + this.height - b.y);
            if (this.havePieces()) {
               this.pieces.union(b.pieces);
            } else {
               this.pieces = b.pieces;
               this.addPiece(new Piece(this.x, this.y, this.width, this.height));
            }
         } else if (this.havePieces()) {
            this.addPiece(new Piece(this.x, this.y + this.height, b.width, b.height));
         }

         this.calcFractionalHeight(b);
         return true;
      }
   }

   public boolean joinBarsRepeat(Bar b, int mx, int my) {
      if (my - this.y < (float)this.height / this.width * (mx - this.x) && my - this.y < (float)this.height / this.width * (this.x + this.width - mx)) {
         if (!this.isSameFractionalWidth(b) && this.width != b.width) {
            return false;
         } else {
            int y0 = this.y;
            this.y = this.y - b.height;
            if (b.haveParts()) {
               b.parts.translateVList(this.x - b.x, this.y - b.y);
               if (!this.haveParts()) {
                  this.parts = b.parts;
                  this.addPart(new Part(this.x, y0, this.fwidth[0], this.fwidth[1], this.fwidth[2], this.fheight[0], this.fheight[1], this.fheight[2]));
               }
            } else if (this.haveParts()) {
               this.addPart(new Part(this.x, this.y, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
            } else if (!this.havePieces() && !b.havePieces()) {
               this.addPart(new Part(this.x, this.y, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
               this.addPart(
                  new Part(this.x, this.y + b.height, this.fwidth[0], this.fwidth[1], this.fwidth[2], this.fheight[0], this.fheight[1], this.fheight[2])
               );
            }

            if (b.havePieces()) {
               b.pieces.translateVList(this.x - b.x, this.y - b.y);
               if (this.havePieces()) {
                  this.pieces.union(b.pieces);
               } else {
                  this.pieces = b.pieces;
                  this.addPiece(new Piece(this.x, y0, this.width, this.height));
               }
            } else if (this.havePieces()) {
               this.addPiece(new Piece(this.x, this.y, b.width, b.height));
            }

            this.calcFractionalHeight(b);
            return true;
         }
      } else if (my - this.y > (float)this.height / this.width * (mx - this.x) && my - this.y < (float)this.height / this.width * (this.x + this.width - mx)) {
         if (!this.isSameFractionalHeight(b) && this.height != b.height) {
            return false;
         } else {
            int x0 = this.x;
            this.x = this.x - b.width;
            if (b.haveParts()) {
               b.parts.translateVList(this.x - b.x, this.y - b.y);
               if (!this.haveParts()) {
                  this.parts = b.parts;
                  this.addPart(new Part(x0, this.y, this.fwidth[0], this.fwidth[1], this.fwidth[2], this.fheight[0], this.fheight[1], this.fheight[2]));
               }
            } else if (this.haveParts()) {
               this.addPart(new Part(this.x, this.y, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
            } else if (!this.havePieces() && !b.havePieces()) {
               this.addPart(new Part(this.x, this.y, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
               this.addPart(
                  new Part(this.x + b.width, this.y, this.fwidth[0], this.fwidth[1], this.fwidth[2], this.fheight[0], this.fheight[1], this.fheight[2])
               );
            }

            if (b.havePieces()) {
               b.pieces.translateVList(this.x - b.x, this.y - b.y);
               if (this.havePieces()) {
                  this.pieces.union(b.pieces);
               } else {
                  this.pieces = b.pieces;
                  this.addPiece(new Piece(x0, this.y, this.width, this.height));
               }
            } else if (this.havePieces()) {
               this.addPiece(new Piece(this.x, this.y, b.width, b.height));
            }

            this.calcFractionalWidth(b);
            return true;
         }
      } else if (!(my - this.y > (float)this.height / this.width * (this.x + this.width - mx))
         || !(my - this.y > (float)this.height / this.width * (mx - this.x))) {
         if (!(my - this.y > (float)this.height / this.width * (this.x + this.width - mx)) || !(my - this.y < (float)this.height / this.width * (mx - this.x))) {
            return false;
         } else if (!this.isSameFractionalHeight(b) && this.height != b.height) {
            return false;
         } else {
            if (b.haveParts()) {
               b.parts.translateVList(this.x + this.width - b.x, this.y - b.y);
               if (!this.haveParts()) {
                  this.parts = b.parts;
                  this.addPart(new Part(this.x, this.y, this.fwidth[0], this.fwidth[1], this.fwidth[2], this.fheight[0], this.fheight[1], this.fheight[2]));
               }
            } else if (this.haveParts()) {
               this.addPart(new Part(this.x + this.width, this.y, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
            } else if (!this.havePieces() && !b.havePieces()) {
               this.addPart(new Part(this.x + this.width, this.y, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
               this.addPart(new Part(this.x, this.y, this.fwidth[0], this.fwidth[1], this.fwidth[2], this.fheight[0], this.fheight[1], this.fheight[2]));
            }

            if (b.havePieces()) {
               b.pieces.translateVList(this.x + this.width - b.x, this.y - b.y);
               if (this.havePieces()) {
                  this.pieces.union(b.pieces);
               } else {
                  this.pieces = b.pieces;
                  this.addPiece(new Piece(this.x, this.y, this.width, this.height));
               }
            } else if (this.havePieces()) {
               this.addPiece(new Piece(this.x + this.width, this.y, b.width, b.height));
            }

            this.calcFractionalWidth(b);
            return true;
         }
      } else if (!this.isSameFractionalWidth(b) && this.width != b.width) {
         return false;
      } else {
         if (b.haveParts()) {
            b.parts.translateVList(this.x - b.x, this.y + this.height - b.y);
            if (!this.haveParts()) {
               this.parts = b.parts;
               this.addPart(new Part(this.x, this.y, this.fwidth[0], this.fwidth[1], this.fwidth[2], this.fheight[0], this.fheight[1], this.fheight[2]));
            }
         } else if (this.haveParts()) {
            this.addPart(new Part(this.x, this.y + this.height, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
         } else if (!this.havePieces() && !b.havePieces()) {
            this.addPart(new Part(this.x, this.y + this.height, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
            this.addPart(new Part(this.x, this.y, this.fwidth[0], this.fwidth[1], this.fwidth[2], this.fheight[0], this.fheight[1], this.fheight[2]));
         }

         if (b.havePieces()) {
            b.pieces.translateVList(this.x - b.x, this.y + this.height - b.y);
            if (this.havePieces()) {
               this.pieces.union(b.pieces);
            } else {
               this.pieces = b.pieces;
               this.addPiece(new Piece(this.x, this.y, this.width, this.height));
            }
         } else if (this.havePieces()) {
            this.addPiece(new Piece(this.x, this.y + this.height, b.width, b.height));
         }

         this.calcFractionalHeight(b);
         return true;
      }
   }

   @Override
   public boolean inside(int x, int y) {
      return this.x <= x && x <= this.x + this.width && this.y <= y && y <= this.y + this.height;
   }

   public boolean haveParts() {
      return !this.parts.isEmpty();
   }

   public boolean havePieces() {
      return !this.pieces.isEmpty();
   }

   public void addPart(Part p) {
      this.parts.addElement(p);
   }

   public void addPiece(Piece p) {
      this.pieces.addElement(p);
   }

   public int findpart(int mx, int my) {
      if (this.haveParts()) {
         for (int i = 0; i < this.parts.size(); i++) {
            Part p = (Part)this.parts.elementAt(i);
            if (p.inside(mx, my)) {
               return i;
            }
         }
      }

      return -1;
   }

   public int findpiece(int mx, int my) {
      if (this.havePieces()) {
         for (int i = 0; i < this.pieces.size(); i++) {
            Piece p = (Piece)this.pieces.elementAt(i);
            if (p.inside(mx, my)) {
               return i;
            }
         }
      }

      return -1;
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
   public boolean isPermanent() {
      return this.permanent;
   }

   @Override
   public void setPermanent(boolean value) {
      this.permanent = value;
   }

   @Override
   public void setColor(Color color) {
      this.color = color;
   }

   @Override
   public Color getColor() {
      return this.color;
   }

   public boolean isNear(Bar o, int tolerance) {
      if (tolerance > 0) {
         Rectangle halo = new Rectangle(this.x - tolerance, this.y - tolerance, this.width + 2 * tolerance, this.height + 2 * tolerance);
         Rectangle shape = new Rectangle(this.x, this.y, this.width, this.height);
         Rectangle oshape = new Rectangle(o.x, o.y, o.width, o.height);
         if (halo.intersects(oshape) && !shape.intersects(oshape)) {
            return true;
         }
      }

      return false;
   }
}
