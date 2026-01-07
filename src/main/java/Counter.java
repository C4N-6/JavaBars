import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Counter implements ActionListener {
   protected PlayGround drawpane;
   protected ControlPanel controlpane;
   protected InfoPanel infopane;

   public void initCountBars(int mx, int my) {
      int i = this.findgroup(mx, my);
      if (i >= 0) {
         Group grp = (Group)this.drawpane.grouplist.elementAt(i);
         int n = this.countBarsInGroup(grp);
         this.controlpane.displayBarCount(n, "Bars in Group");
      } else {
         i = this.findcover(mx, my);
         if (i >= 0) {
            Cover c = (Cover)this.drawpane.coverlist.elementAt(i);
            int n = this.countBarsInCover(c);
            this.controlpane.displayBarCount(n, "Bars under Cover");
         } else {
            i = this.findbar(mx, my);
            if (i >= 0) {
               this.controlpane.displayBarCount(1, "Bars in Bar");
            } else {
               i = this.findmat(mx, my);
               if (i >= 0) {
                  Mat m = (Mat)this.drawpane.matlist.elementAt(i);
                  int n = this.countBarsInMat(m);
                  this.controlpane.displayBarCount(n, "Bars on Mat");
               } else {
                  int n = this.drawpane.barlist.size();
                  this.controlpane.displayBarCount(n, "Bars in Playground");
               }
            }
         }
      }
   }

   public int countBarsInGroup(Group grp) {
      int n = 0;

      for (int i = 0; i < grp.size(); i++) {
         Object o = grp.group.elementAt(i);
         if (o instanceof Bar) {
            n++;
         }
      }

      return n;
   }

   public int countBarsInCover(Cover c) {
      int n = 0;
      Rectangle rc = new Rectangle(c.x, c.y, c.width, c.height);

      for (int i = 0; i < this.drawpane.barlist.size(); i++) {
         Bar b = (Bar)this.drawpane.barlist.elementAt(i);
         Rectangle r = new Rectangle(b.x, b.y, b.width, b.height);
         if (r.intersection(rc).equals(r)) {
            n++;
         }
      }

      return n;
   }

   public int countBarsInMat(Mat m) {
      int n = 0;
      Rectangle rm = new Rectangle(m.x, m.y, m.width, m.height);

      for (int i = 0; i < this.drawpane.barlist.size(); i++) {
         Bar b = (Bar)this.drawpane.barlist.elementAt(i);
         Rectangle r = new Rectangle(b.x, b.y, b.width, b.height);
         if (r.intersection(rm).equals(r)) {
            n++;
         }
      }

      return n;
   }

   public void initCountParts(int mx, int my) {
      int i = this.findgroup(mx, my);
      if (i >= 0) {
         Group grp = (Group)this.drawpane.grouplist.elementAt(i);
         this.controlpane.displayPartCount(this.countPartsInGroup(grp), "Parts in Group");
      } else {
         i = this.findcover(mx, my);
         if (i >= 0) {
            Cover c = (Cover)this.drawpane.coverlist.elementAt(i);
            this.controlpane.displayPartCount(this.countPartsInCover(c), "Parts under Cover");
         } else {
            i = this.findbar(mx, my);
            if (i >= 0) {
               Bar b = (Bar)this.drawpane.barlist.elementAt(i);
               this.controlpane.displayPartCount(b.parts.size(), "Parts in Bar");
            } else {
               i = this.findmat(mx, my);
               if (i >= 0) {
                  Mat m = (Mat)this.drawpane.matlist.elementAt(i);
                  this.controlpane.displayPartCount(this.countPartsInMat(m), "Parts on Mat");
               } else {
                  this.controlpane.displayPartCount(this.countPartsInPlayground(), "Parts in Playground");
               }
            }
         }
      }
   }

   public int countPartsInGroup(Group grp) {
      int n = 0;

      for (int i = 0; i < grp.group.size(); i++) {
         Object o = grp.group.elementAt(i);
         if (o instanceof Bar) {
            n += ((Bar)o).parts.size();
         }
      }

      return n;
   }

   public int countPartsInCover(Cover c) {
      int n = 0;
      Rectangle rc = new Rectangle(c.x, c.y, c.width, c.height);

      for (int i = 0; i < this.drawpane.barlist.size(); i++) {
         Bar b = (Bar)this.drawpane.barlist.elementAt(i);
         Rectangle r = new Rectangle(b.x, b.y, b.width, b.height);
         if (!r.intersection(rc).isEmpty() && b.haveParts()) {
            for (int j = 0; j < b.parts.size(); j++) {
               Part p = (Part)b.parts.elementAt(i);
               Rectangle rp = new Rectangle(p.x, p.y, p.width, p.height);
               if (rp.intersection(rc).equals(rp)) {
                  n++;
               }
            }
         }
      }

      return n;
   }

   public int countPartsInMat(Mat m) {
      int n = 0;
      Rectangle rm = new Rectangle(m.x, m.y, m.width, m.height);

      for (int i = 0; i < this.drawpane.barlist.size(); i++) {
         Bar b = (Bar)this.drawpane.barlist.elementAt(i);
         Rectangle r = new Rectangle(b.x, b.y, b.width, b.height);
         if (!r.intersection(rm).isEmpty() && b.haveParts()) {
            for (int j = 0; j < b.parts.size(); j++) {
               Part p = (Part)b.parts.elementAt(i);
               Rectangle rp = new Rectangle(p.x, p.y, p.width, p.height);
               if (rp.intersection(rm).equals(rp)) {
                  n++;
               }
            }
         }
      }

      return n;
   }

   public int countPartsInPlayground() {
      int n = 0;

      for (int i = 0; i < this.drawpane.barlist.size(); i++) {
         Bar b = (Bar)this.drawpane.barlist.elementAt(i);
         if (b.haveParts()) {
            n += b.parts.size();
         }
      }

      return n;
   }

   public void initCountPieces(int mx, int my) {
      int i = this.findgroup(mx, my);
      if (i >= 0) {
         Group grp = (Group)this.drawpane.grouplist.elementAt(i);
         this.controlpane.displayPieceCount(this.countPiecesInGroup(grp), "Pieces in Group");
      } else {
         i = this.findcover(mx, my);
         if (i >= 0) {
            Cover c = (Cover)this.drawpane.coverlist.elementAt(i);
            this.controlpane.displayPieceCount(this.countPiecesInCover(c), "Pieces under Cover");
         } else {
            i = this.findbar(mx, my);
            if (i >= 0) {
               Bar b = (Bar)this.drawpane.barlist.elementAt(i);
               this.controlpane.displayPieceCount(b.pieces.size(), "Pieces in Bar");
            } else {
               i = this.findmat(mx, my);
               if (i >= 0) {
                  Mat m = (Mat)this.drawpane.matlist.elementAt(i);
                  this.controlpane.displayPieceCount(this.countPiecesInMat(m), "Pieces on Mat");
               } else {
                  this.controlpane.displayPieceCount(this.countPiecesInPlayground(), "Pieces in Playground");
               }
            }
         }
      }
   }

   public int countPiecesInGroup(Group grp) {
      int n = 0;

      for (int i = 0; i < grp.group.size(); i++) {
         Object o = grp.group.elementAt(i);
         if (o instanceof Bar) {
            n += ((Bar)o).pieces.size();
         }
      }

      return n;
   }

   public int countPiecesInCover(Cover c) {
      int n = 0;
      Rectangle rc = new Rectangle(c.x, c.y, c.width, c.height);

      for (int i = 0; i < this.drawpane.barlist.size(); i++) {
         Bar b = (Bar)this.drawpane.barlist.elementAt(i);
         Rectangle r = new Rectangle(b.x, b.y, b.width, b.height);
         if (!r.intersection(rc).isEmpty() && b.havePieces()) {
            for (int j = 0; j < b.pieces.size(); j++) {
               Piece p = (Piece)b.pieces.elementAt(j);
               Rectangle rp = new Rectangle(p.x, p.y, p.width, p.height);
               if (rp.intersection(rc).equals(rp)) {
                  n++;
               }
            }
         }
      }

      return n;
   }

   public int countPiecesInMat(Mat m) {
      int n = 0;
      Rectangle rm = new Rectangle(m.x, m.y, m.width, m.height);

      for (int i = 0; i < this.drawpane.barlist.size(); i++) {
         Bar b = (Bar)this.drawpane.barlist.elementAt(i);
         Rectangle r = new Rectangle(b.x, b.y, b.width, b.height);
         if (!r.intersection(rm).isEmpty() && b.havePieces()) {
            for (int j = 0; j < b.pieces.size(); j++) {
               Piece p = (Piece)b.pieces.elementAt(j);
               Rectangle rp = new Rectangle(p.x, p.y, p.width, p.height);
               if (rp.intersection(rm).equals(rp)) {
                  n++;
               }
            }
         }
      }

      return n;
   }

   public int countPiecesInPlayground() {
      int n = 0;

      for (int i = 0; i < this.drawpane.barlist.size(); i++) {
         Bar b = (Bar)this.drawpane.barlist.elementAt(i);
         if (b.havePieces()) {
            n += b.pieces.size();
         }
      }

      return n;
   }

   public void setDrawingPane(PlayGround pl) {
      this.drawpane = pl;
   }

   public void setControlPane(ControlPanel cp) {
      this.controlpane = cp;
   }

   public void setInfoPane(InfoPanel ip) {
      this.infopane = ip;
   }

   @Override
   public void actionPerformed(ActionEvent evt) {
   }

   public int findbar(int mx, int my) {
      int len = this.drawpane.barlist.size();

      for (int i = len - 1; i >= 0; i--) {
         Bar b = (Bar)this.drawpane.barlist.elementAt(i);
         if (b.inside(mx, my)) {
            return i;
         }
      }

      return -1;
   }

   public int findcover(int mx, int my) {
      int len = this.drawpane.coverlist.size();

      for (int i = len - 1; i >= 0; i--) {
         Cover c = (Cover)this.drawpane.coverlist.elementAt(i);
         if (c.inside(mx, my)) {
            return i;
         }
      }

      return -1;
   }

   public int findmat(int mx, int my) {
      int len = this.drawpane.matlist.size();

      for (int i = len - 1; i >= 0; i--) {
         Mat m = (Mat)this.drawpane.matlist.elementAt(i);
         if (m.inside(mx, my)) {
            return i;
         }
      }

      return -1;
   }

   public int findgroup(int mx, int my) {
      int len = this.drawpane.grouplist.size();

      for (int i = len - 1; i >= 0; i--) {
         Group g = (Group)this.drawpane.grouplist.elementAt(i);
         if (g.inside(mx, my)) {
            return i;
         }
      }

      return -1;
   }
}
