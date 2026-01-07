import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

class PlayGround extends BorderDrawingPanel implements ActionListener, MouseMotionListener {
   private boolean FirstMiddleRepeatExecution;
   private FillDialog filldialog;
   private ErrorDialog saveErrDlog;
   private ErrorDialog loadErrDlog;
   private FileDialog saveDialog;
   private FileDialog loadDialog;
   private Color fillcolor;
   private JToggleButton lastpressed;
   public ControlPanel controlpane;
   public InfoPanel infopane;
   public Counter counter;
   public Configuration configure;
   private TextDialog labeldialog;
   protected ConfirmDialog newdialog;
   private ErrorDialog joindialog;
   private ErrorDialog joinpartsdialog;
   private ErrorDialog joinpiecesdialog;
   public VList barlist = new VList();
   public VList coverlist = new VList();
   public VList matlist = new VList();
   public VList trashlist = new VList();
   public VList grouplist = new VList();
   public static final int NUMBER_OF_ACTIONS = 12;
   public static final String CURRENT_OBJECT = "Current Object";
   public static final String CURRENT_TEXT = "Current Text";
   public static final String CLIPBOARD = "Clipboard";
   public static final String LABELOK = "labelOk";
   public static final String UNIT_BAR = "Unit Bar";
   public int TOL = 2;

   PlayGround() {
      super("TIMA: Bars");
      this.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent evt) {
            PlayGround.this.mouseClicked(evt);
         }

         @Override
         public void mousePressed(MouseEvent evt) {
            PlayGround.this.mousePressed(evt);
         }

         @Override
         public void mouseReleased(MouseEvent evt) {
            PlayGround.this.mouseReleased(evt);
         }
      });
      this.addMouseMotionListener(this);
   }

   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      this.matlist.drawVListForwards(g);
      this.barlist.drawVListForwards(g);
      this.coverlist.drawVListForwards(g);
   }

   public int findbar(int mx, int my) {
      int len = this.barlist.size();

      for (int i = len - 1; i >= 0; i--) {
         Bar b = (Bar)this.barlist.elementAt(i);
         if (b.inside(mx, my)) {
            return i;
         }
      }

      return -1;
   }

   public int findcover(int mx, int my) {
      int len = this.coverlist.size();

      for (int i = len - 1; i >= 0; i--) {
         Cover c = (Cover)this.coverlist.elementAt(i);
         if (c.inside(mx, my)) {
            return i;
         }
      }

      return -1;
   }

   public int findmat(int mx, int my) {
      int len = this.matlist.size();

      for (int i = len - 1; i >= 0; i--) {
         Mat m = (Mat)this.matlist.elementAt(i);
         if (m.inside(mx, my)) {
            return i;
         }
      }

      return -1;
   }

   public int findgroup(int mx, int my) {
      int len = this.grouplist.size();

      for (int i = len - 1; i >= 0; i--) {
         Group g = (Group)this.grouplist.elementAt(i);
         if (g.inside(mx, my)) {
            return i;
         }
      }

      return -1;
   }

   public void undo() {
   }

   public void redo() {
   }

   public void setControlPane(ControlPanel cp) {
      this.controlpane = cp;
   }

   public void setInfoPane(InfoPanel ip) {
      this.infopane = ip;
   }

   @Override
   public void actionPerformed(ActionEvent evt) {
      Object src = evt.getSource();
      if (src instanceof JToggleButton) {
         if (this.lastpressed == null) {
            this.lastpressed = (JToggleButton)src;
         } else if (this.lastpressed == src) {
            this.controlpane.selectNoButtons();
            this.lastpressed = null;
         } else if (this.lastpressed.getActionCommand() == "Copy") {
            this.finalCopy();
         } else {
            this.lastpressed = (JToggleButton)src;
         }

         String command = ((JToggleButton)src).getActionCommand();
         if (command == "Copy" && this.lastpressed == null) {
            this.finalCopy();
         } else if (command == "Repeat" && this.lastpressed == null) {
            this.finalRepeat();
         } else if (command == "Group" && this.lastpressed == null) {
            this.finalGroup();
         } else if (command == "Fill" && this.lastpressed != null) {
            this.filldialog.setVisible(true);
         } else if (command == "Fill Parts" && this.lastpressed != null) {
            this.filldialog.setVisible(true);
         } else if (command == "Fill Pieces" && this.lastpressed != null) {
            this.filldialog.setVisible(true);
         }
      } else if (src instanceof JButton) {
         String command = ((JButton)src).getActionCommand();
         if (command == "Save") {
            this.save();
         } else if (command == "Load") {
            this.load();
         }
      }
   }

   public void finalGroup() {
      this.putClientProperty("Current Object", null);
   }

   public void finalRepeat() {
      Bar b = (Bar)this.getClientProperty("Current Object");
      b.setHighlight(false);
      b.draw(this.getGraphics());
      this.putClientProperty("Current Object", null);
   }

   public void finalCopy() {
      MWObject o = (MWObject)this.getClientProperty("Current Object");
      if (o != null) {
         o.setHighlight(false);
         this.repaint();
         this.putClientProperty("Current Object", null);
      }
   }

   public void mouseClicked(MouseEvent evt) {
      int mx = evt.getX();
      int my = evt.getY();
      String command = this.controlpane.getControlPaneButtons().getSelection().getActionCommand();
      if (command == "Copy") {
         Object o = this.getClientProperty("Current Object");
         if (o != null) {
            this.middleCopy(mx, my);
         } else {
            this.initCopy(mx, my);
         }
      } else if (command == "Erase") {
         this.initErase(mx, my);
      } else if (command == "Vertical pieces") {
         this.initVPieces(mx, my);
      } else if (command == "Horizontal pieces") {
         this.initHPieces(mx, my);
      } else if (command == "Vertical parts") {
         this.initVParts(mx, my);
      } else if (command == "Horizontal parts") {
         this.initHParts(mx, my);
      } else if (command == "Ungroup") {
         this.initUngroup(mx, my);
      } else if (command == "Group") {
         this.initGroup(mx, my);
      } else if (command == "Repeat") {
         if (this.getClientProperty("Current Object") == null) {
            this.initRepeat(mx, my);
         } else {
            this.middleRepeat(mx, my);
         }
      } else if (command == "Label") {
         this.initLabel(mx, my);
      } else if (command == "Pullout Parts") {
         this.middlePulloutSingleParts(mx, my);
      } else if (command == "Pullout Pieces") {
         this.middlePulloutSinglePieces(mx, my);
      } else if (command == "Fill") {
         this.fillBar(mx, my);
      } else if (command == "Set Unit") {
         this.initUnitBar(mx, my);
      } else if (command == "Measure") {
         this.initMeasure(mx, my);
      } else if (command == "Break Parts") {
         this.initBreakParts(mx, my);
      } else if (command == "Break Pieces") {
         this.initBreakPieces(mx, my);
      } else if (command == "Cut") {
         this.initCut(mx, my);
      } else if (command == "Erase Parts") {
         this.initEraseParts(mx, my);
      } else if (command == "Erase Pieces") {
         this.initErasePieces(mx, my);
      } else if (command == "Join Parts") {
         this.initJoinParts(mx, my);
      } else if (command == "Join Pieces") {
         this.initJoinPieces(mx, my);
      } else if (command == "Number of Bars") {
         this.counter.initCountBars(mx, my);
      } else if (command == "Number of Parts") {
         this.counter.initCountParts(mx, my);
      } else if (command == "Number of Pieces") {
         this.counter.initCountPieces(mx, my);
      } else if (command == "Fill Parts") {
         this.initFillParts(mx, my);
      } else if (command == "Fill Pieces") {
         this.initFillPieces(mx, my);
      } else if (command == "Join") {
         this.initJoin(mx, my);
      }
   }

   public void initFillParts(int mx, int my) {
      int i = this.findbar(mx, my);
      if (i >= 0) {
         Bar b = (Bar)this.barlist.elementAt(i);
         int j = b.findpart(mx, my);
         if (j >= 0) {
            Part p = (Part)b.parts.elementAt(j);
            p.setColor(this.fillcolor);
            p.draw(this.getGraphics());
         }
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void initFillPieces(int mx, int my) {
      int i = this.findbar(mx, my);
      if (i >= 0) {
         Bar b = (Bar)this.barlist.elementAt(i);
         int j = b.findpiece(mx, my);
         if (j >= 0) {
            Piece p = (Piece)b.pieces.elementAt(j);
            p.setColor(this.fillcolor);
            p.draw(this.getGraphics());
         }
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void initGroup(int mx, int my) {
      int i = this.findbar(mx, my);
      if (i >= 0) {
         if (this.getClientProperty("Current Object") instanceof Group) {
            Bar b = (Bar)this.barlist.elementAt(i);
            Group gp = (Group)this.getClientProperty("Current Object");
            gp.add(b);
            b.setHighlight(true);
            b.draw(this.getGraphics());
         } else if (this.getClientProperty("Current Object") == null) {
            Group gp = new Group();
            Bar b = (Bar)this.barlist.elementAt(i);
            gp.add(b);
            b.setHighlight(true);
            b.draw(this.getGraphics());
            this.grouplist.addElement(gp);
            this.putClientProperty("Current Object", gp);
         }
      } else {
         this.putClientProperty("Current Object", null);
         this.controlpane.selectNoButtons();
      }
   }

   public void initEraseParts(int mx, int my) {
      int i = this.findbar(mx, my);
      if (i >= 0) {
         Bar b = (Bar)this.barlist.elementAt(i);
         Graphics g = this.getGraphics();
         b.erase(g);
         b.parts.removeAllElements();
         b.draw(g);
         g.dispose();
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void initErasePieces(int mx, int my) {
      int i = this.findbar(mx, my);
      if (i >= 0) {
         Bar b = (Bar)this.barlist.elementAt(i);
         Graphics g = this.getGraphics();
         b.erase(g);
         b.pieces.removeAllElements();
         b.draw(g);
         g.dispose();
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void initJoinParts(int mx, int my) {
      int i = this.findbar(mx, my);
      if (i >= 0) {
         Bar b = (Bar)this.barlist.elementAt(i);
         Object o = this.getClientProperty("Current Object");
         if (b == o && b.parts.contains(this.getClientProperty("Clipboard"))) {
            this.middleJoinPartsPartTwo(mx, my);
         } else if (o == null) {
            this.middleJoinPartsPartOne(b, mx, my);
         }
      } else {
         this.putClientProperty("Current Object", null);
         this.putClientProperty("Clipboard", null);
         this.controlpane.selectNoButtons();
      }
   }

   public void middleJoinPartsPartOne(Bar b, int mx, int my) {
      int i = b.findpart(mx, my);
      if (i >= 0) {
         Part p = (Part)b.parts.elementAt(i);
         this.putClientProperty("Current Object", b);
         this.putClientProperty("Clipboard", p);
         p.setHighlight(true);
         p.draw(this.getGraphics());
      }
   }

   public void middleJoinPartsPartTwo(int mx, int my) {
      Bar b = (Bar)this.getClientProperty("Current Object");
      Part p1 = (Part)this.getClientProperty("Clipboard");
      int i = b.findpart(mx, my);
      if (i >= 0) {
         Part p2 = (Part)b.parts.elementAt(i);
         if (p1 == p2) {
            p1.setHighlight(false);
         } else {
            String orientation = this.adjacentParts(p1, p2);
            if (orientation == "Left") {
               b.parts.removeElement(p1);
               b.parts.removeElement(p2);
               if (p1.fwidth[0] == p2.fwidth[0]) {
                  int gcf = MyMath.GCF(p1.fwidth[1] * p2.fwidth[2] + p2.fwidth[1] * p1.fwidth[2], p1.fwidth[2] * p2.fwidth[2]);
                  b.addPart(
                     new Part(
                        p1.x,
                        p1.y,
                        p1.fwidth[0],
                        (p1.fwidth[1] * p2.fwidth[2] + p2.fwidth[1] * p1.fwidth[2]) / gcf,
                        p1.fwidth[2] * p2.fwidth[2] / gcf,
                        p1.fheight[0],
                        p1.fheight[1],
                        p1.fheight[2]
                     )
                  );
               } else {
                  b.addPart(new Part(p1.x, p1.y, p2.x + p2.width - p1.x, p1.height));
               }
            } else if (orientation == "Right") {
               b.parts.removeElement(p1);
               b.parts.removeElement(p2);
               if (p1.fwidth[0] == p2.fwidth[0]) {
                  int gcf = MyMath.GCF(p1.fwidth[1] * p2.fwidth[2] + p2.fwidth[1] * p1.fwidth[2], p1.fwidth[2] * p2.fwidth[2]);
                  b.addPart(
                     new Part(
                        p2.x,
                        p2.y,
                        p1.fwidth[0],
                        (p1.fwidth[1] * p2.fwidth[2] + p2.fwidth[1] * p1.fwidth[2]) / gcf,
                        p1.fwidth[2] * p2.fwidth[2] / gcf,
                        p1.fheight[0],
                        p1.fheight[1],
                        p1.fheight[2]
                     )
                  );
               } else {
                  b.addPart(new Part(p2.x, p2.y, p1.x + p1.width - p2.x, p2.height));
               }
            } else if (orientation == "Above") {
               b.parts.removeElement(p1);
               b.parts.removeElement(p2);
               if (p1.fheight[0] == p2.fheight[0]) {
                  int gcf = MyMath.GCF(p1.fheight[1] * p2.fheight[2] + p2.fheight[1] * p1.fheight[2], p1.fheight[2] * p2.fheight[2]);
                  b.addPart(
                     new Part(
                        p1.x,
                        p1.y,
                        p1.fwidth[0],
                        p1.fwidth[1],
                        p1.fwidth[2],
                        p1.fheight[0],
                        (p1.fheight[1] * p2.fheight[2] + p2.fheight[1] * p1.fheight[2]) / gcf,
                        p1.fheight[2] * p2.fheight[2] / gcf
                     )
                  );
               } else {
                  b.addPart(new Part(p1.x, p1.y, p1.width, p2.y + p2.height - p1.y));
               }
            } else if (orientation == "Below") {
               b.parts.removeElement(p1);
               b.parts.removeElement(p2);
               if (p1.fheight[0] == p2.fheight[0]) {
                  int gcf = MyMath.GCF(p1.fheight[1] * p2.fheight[2] + p2.fheight[1] * p1.fheight[2], p1.fheight[2] * p2.fheight[2]);
                  b.addPart(
                     new Part(
                        p2.x,
                        p2.y,
                        p1.fwidth[0],
                        p1.fwidth[1],
                        p1.fwidth[2],
                        p1.fheight[0],
                        (p1.fheight[1] * p2.fheight[2] + p2.fheight[1] * p1.fheight[2]) / gcf,
                        p1.fheight[2] * p2.fheight[2] / gcf
                     )
                  );
               } else {
                  b.addPart(new Part(p2.x, p2.y, p2.width, p1.y + p1.height - p2.y));
               }
            } else {
               p1.setHighlight(false);
               this.joinpartsdialog.setVisible(true);
            }
         }

         b.draw(this.getGraphics());
      } else {
         p1.setHighlight(false);
         p1.draw(this.getGraphics());
      }

      this.putClientProperty("Current Object", null);
      this.putClientProperty("Clipboard", null);
   }

   public String adjacentParts(Part p1, Part p2) {
      if (p2.x - 1 <= p1.x + p1.width && p1.x + p1.width <= p2.x + 1 && p1.y == p2.y && p1.height == p2.height) {
         return "Left";
      } else if (p1.x - 1 <= p2.x + p2.width && p2.x + p2.width <= p1.x + 1 && p2.y == p1.y && p2.height == p1.height) {
         return "Right";
      } else if (p2.y - 1 <= p1.y + p1.height && p1.y + p1.height <= p2.y + 1 && p1.x == p2.x && p1.width == p2.width) {
         return "Above";
      } else {
         return p1.y - 1 <= p2.y + p2.height && p2.y + p2.height <= p1.y + 1 && p2.x == p1.x && p2.width == p1.width ? "Below" : "None";
      }
   }

   public void initJoinPieces(int mx, int my) {
      int i = this.findbar(mx, my);
      if (i >= 0) {
         Bar b = (Bar)this.barlist.elementAt(i);
         Object o = this.getClientProperty("Current Object");
         if (b == o && b.pieces.contains(this.getClientProperty("Clipboard"))) {
            this.middleJoinPiecesPartTwo(mx, my);
         } else if (o == null) {
            this.middleJoinPiecesPartOne(b, mx, my);
         }
      } else {
         this.putClientProperty("Current Object", null);
         this.putClientProperty("Clipboard", null);
         this.controlpane.selectNoButtons();
      }
   }

   public void middleJoinPiecesPartOne(Bar b, int mx, int my) {
      int i = b.findpiece(mx, my);
      if (i >= 0) {
         Piece p = (Piece)b.pieces.elementAt(i);
         this.putClientProperty("Current Object", b);
         this.putClientProperty("Clipboard", p);
         p.setHighlight(true);
         p.draw(this.getGraphics());
      }
   }

   public void middleJoinPiecesPartTwo(int mx, int my) {
      Bar b = (Bar)this.getClientProperty("Current Object");
      Piece p1 = (Piece)this.getClientProperty("Clipboard");
      int i = b.findpiece(mx, my);
      if (i >= 0) {
         Piece p2 = (Piece)b.pieces.elementAt(i);
         if (p1 == p2) {
            p1.setHighlight(false);
         } else {
            String orientation = this.adjacentPieces(p1, p2);
            if (orientation == "Left") {
               b.pieces.removeElement(p1);
               b.pieces.removeElement(p2);
               b.addPiece(new Piece(p1.x, p1.y, p1.width + p2.width, p1.height));
            } else if (orientation == "Right") {
               b.pieces.removeElement(p1);
               b.pieces.removeElement(p2);
               b.addPiece(new Piece(p2.x, p2.y, p2.width + p1.width, p2.height));
            } else if (orientation == "Above") {
               b.pieces.removeElement(p1);
               b.pieces.removeElement(p2);
               b.addPiece(new Piece(p1.x, p1.y, p1.width, p1.height + p2.height));
            } else if (orientation == "Below") {
               b.pieces.removeElement(p1);
               b.pieces.removeElement(p2);
               b.addPiece(new Piece(p2.x, p2.y, p2.width, p2.height + p1.height));
            } else {
               p1.setHighlight(false);
               this.joinpiecesdialog.setVisible(true);
            }
         }

         b.draw(this.getGraphics());
      } else {
         p1.setHighlight(false);
         p1.draw(this.getGraphics());
      }

      this.putClientProperty("Current Object", null);
      this.putClientProperty("Clipboard", null);
   }

   public String adjacentPieces(Piece p1, Piece p2) {
      if (p1.x + p1.width == p2.x && p1.y == p2.y && p1.height == p2.height) {
         return "Left";
      } else if (p2.x + p2.width == p1.x && p2.y == p1.y && p2.height == p1.height) {
         return "Right";
      } else if (p1.y + p1.height == p2.y && p1.x == p2.x && p1.width == p2.width) {
         return "Above";
      } else {
         return p2.y + p2.height == p1.y && p2.x == p1.x && p2.width == p1.width ? "Below" : "None";
      }
   }

   public void initCut(int mx, int my) {
      int i = this.findbar(mx, my);
      if (i >= 0) {
         Bar b = (Bar)this.barlist.elementAt(i);
         this.putClientProperty("Current Object", b);
         b.lastx = mx;
         b.lasty = my;
      } else {
         this.putClientProperty("Current Object", null);
         this.controlpane.selectNoButtons();
      }
   }

   public void initUnitBar(int mx, int my) {
      int i = this.findbar(mx, my);
      if (i >= 0) {
         Bar b = (Bar)this.barlist.elementAt(i);
         if (this.getClientProperty("Unit Bar") != null) {
            Bar u = (Bar)this.getClientProperty("Unit Bar");
            u.setLabel("");
            u.draw(this.getGraphics());
         }

         this.putClientProperty("Unit Bar", b);
         b.setLabel("Unit Bar");
         b.draw(this.getGraphics());
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void initMeasure(int mx, int my) {
      int i = this.findbar(mx, my);
      if (i >= 0) {
         if (this.getClientProperty("Unit Bar") != null) {
            Bar b = (Bar)this.barlist.elementAt(i);
            Bar unit = (Bar)this.getClientProperty("Unit Bar");
            if (b.fwidth[0] == unit.fwidth[0] && b.fheight[0] == unit.fheight[0]) {
               int numerator = b.fwidth[1] * unit.fwidth[2] * b.fheight[1] * unit.fheight[2];
               int denominator = b.fwidth[2] * unit.fwidth[1] * b.fheight[2] * unit.fheight[1];
               int gcf = MyMath.GCF(numerator, denominator);
               this.displayMeasure(b, numerator / gcf, denominator / gcf);
            } else if (b.fwidth[0] != unit.fwidth[0]) {
               int var10000 = b.fheight[0];
               var10000 = unit.fheight[0];
            }
         }
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void fillBar(int mx, int my) {
      int index = this.findbar(mx, my);
      if (index >= 0) {
         Bar b = (Bar)this.barlist.elementAt(index);
         b.setColor(this.fillcolor);
         boolean old_permanent = b.isPermanent();
         b.setPermanent(true);
         this.repaint();
         b.setPermanent(old_permanent);
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void setFillColor(Color c) {
      this.fillcolor = c;
   }

   public void middlePulloutSingleParts(int mx, int my) {
      if (this.getClientProperty("Current Object") != null) {
         Bar b = (Bar)this.getClientProperty("Current Object");
         int j = b.findpart(mx, my);
         if (j >= 0) {
            Part p = (Part)b.parts.elementAt(j);
            Bar bprime = new Bar(p.x, p.y, p.fwidth[0], p.fwidth[1], p.fwidth[2], p.fheight[0], p.fheight[1], p.fheight[2]);
            bprime.setPermanent(true);
            bprime.setColor(p.getColor());
            this.barlist.addElement(bprime);
            bprime.draw(this.getGraphics());
         } else {
            this.controlpane.selectNoButtons();
            this.putClientProperty("Current Object", null);
            this.putClientProperty("Clipboard", null);
         }
      }
   }

   public void middlePulloutSinglePieces(int mx, int my) {
      if (this.getClientProperty("Current Object") != null) {
         Bar b = (Bar)this.getClientProperty("Current Object");
         int j = b.findpiece(mx, my);
         if (j >= 0) {
            Piece p = (Piece)b.pieces.elementAt(j);
            Bar bprime = new Bar(p.x, p.y, p.width, p.height);
            bprime.setPermanent(true);
            bprime.setColor(p.getColor());
            this.barlist.addElement(bprime);
            bprime.draw(this.getGraphics());
         } else {
            this.controlpane.selectNoButtons();
            this.putClientProperty("Current Object", null);
            this.putClientProperty("Clipboard", null);
         }
      }
   }

   public void initLabel(int mx, int my) {
      ProtoBar o = new ProtoBar();
      int i = this.findcover(mx, my);
      if (i >= 0) {
         o = (Cover)this.coverlist.elementAt(i);
      } else {
         i = this.findbar(mx, my);
         if (i >= 0) {
            o = (Bar)this.barlist.elementAt(i);
         } else {
            i = this.findmat(mx, my);
            if (i >= 0) {
               o = (Mat)this.matlist.elementAt(i);
            }
         }
      }

      if (i >= 0) {
         this.getLabelDialog().show();
         o.setLabel(this.getLabelDialog().getText());
         o.draw(this.getGraphics());
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void initUngroup(int mx, int my) {
      int i = this.findgroup(mx, my);
      if (i >= 0) {
         Group gp = (Group)this.grouplist.elementAt(i);
         this.grouplist.removeElement(gp);
         gp.setHighlight(false);
         this.repaint();
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void initBreakParts(int mx, int my) {
      int i = this.findbar(mx, my);
      if (i >= 0) {
         Bar b = (Bar)this.barlist.elementAt(i);
         if (b != this.getClientProperty("Unit Bar")) {
         }

         Graphics g = this.getGraphics();
         if (b.haveParts()) {
            b.erase(g);
            this.barlist.removeElement(b);

            for (int j = 0; j < b.parts.size(); j++) {
               Part p = (Part)b.parts.elementAt(j);
               Bar bprime = new Bar(p.x, p.y, p.fwidth[0], p.fwidth[1], p.fwidth[2], p.fheight[0], p.fheight[1], p.fheight[2]);
               bprime.setPermanent(true);
               if (p.isFilled()) {
                  bprime.setColor(p.getColor());
               } else {
                  bprime.setColor(b.getColor());
               }

               if (b.havePieces()) {
                  for (int k = 0; k < b.pieces.size(); k++) {
                     Piece pi = (Piece)b.pieces.elementAt(k);
                     Rectangle r = new Rectangle(pi.x, pi.y, pi.width, pi.height);
                     Rectangle rprime = new Rectangle(bprime.x, bprime.y, bprime.width, bprime.height);
                     if (!rprime.intersection(r).isEmpty()) {
                        Rectangle newpiece = rprime.intersection(r);
                        Piece piprime = new Piece(newpiece.x, newpiece.y, newpiece.width, newpiece.height);
                        bprime.addPiece(piprime);
                     }
                  }
               }

               this.barlist.addElement(bprime);
               bprime.draw(g);
            }

            g.dispose();
         }
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void initBreakPieces(int mx, int my) {
      int i = this.findbar(mx, my);
      if (i >= 0) {
         Bar b = (Bar)this.barlist.elementAt(i);
         if (b != this.getClientProperty("Unit Bar")) {
         }

         Graphics g = this.getGraphics();
         if (b.havePieces()) {
            b.erase(g);
            this.barlist.removeElement(b);

            for (int j = 0; j < b.pieces.size(); j++) {
               Piece p = (Piece)b.pieces.elementAt(j);
               Bar bprime = new Bar(p.x, p.y, p.width, p.height);
               bprime.setPermanent(true);
               if (p.isFilled()) {
                  bprime.setColor(p.getColor());
               } else {
                  bprime.setColor(b.getColor());
               }

               if (b.haveParts()) {
                  for (int k = 0; k < b.parts.size(); k++) {
                     Part pi = (Part)b.parts.elementAt(k);
                     Rectangle r = new Rectangle(pi.x, pi.y, pi.width, pi.height);
                     Rectangle rprime = new Rectangle(bprime.x, bprime.y, bprime.width, bprime.height);
                     if (!rprime.intersection(r).isEmpty()) {
                        Rectangle newpiece = rprime.intersection(r);
                        Part piprime = new Part(newpiece.x, newpiece.y, newpiece.width, newpiece.height);
                        bprime.addPart(piprime);
                     }
                  }
               }

               this.barlist.addElement(bprime);
               bprime.draw(g);
            }

            g.dispose();
         }
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void initRepeat(int mx, int my) {
      int i = this.findbar(mx, my);
      this.FirstMiddleRepeatExecution = true;
      if (this.getClientProperty("Current Object") == null && i >= 0) {
         Bar b = (Bar)this.barlist.elementAt(i);
         if (b != this.getClientProperty("Unit Bar")) {
         }

         this.putClientProperty("Current Object", b);
         b.setHighlight(true);
         b.draw(this.getGraphics());
         if (!b.haveParts()) {
            b.addPart(new Part(b.x, b.y, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
         }

         this.putClientProperty("Clipboard", (Bar)b.copy());
         b.lastx = mx;
         b.lasty = my;
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void middleRepeat(int mx, int my) {
      if (this.getClientProperty("Current Object") != null) {
         Bar b = (Bar)this.getClientProperty("Current Object");
         Bar bprime = (Bar)this.getClientProperty("Clipboard");
         int x0;
         int y0;
         if (Math.abs(b.lastx - mx) < Math.abs(b.lasty - my)) {
            if (b.lasty < my) {
               bprime.move(b.x, b.y + b.height);
               x0 = b.x + 1;
               y0 = b.y + b.height;
            } else {
               bprime.move(b.x, b.y - bprime.height);
               x0 = b.x + 1;
               y0 = b.y - bprime.height;
            }
         } else if (b.lastx < mx) {
            bprime.move(b.x + b.width, b.y);
            x0 = b.x + b.width;
            y0 = b.y + 1;
         } else {
            bprime.move(b.x - bprime.width, b.y);
            x0 = b.x - bprime.width;
            y0 = b.y + 1;
         }

         boolean flag = b.joinBarsRepeat(bprime, x0, y0);
         if (flag) {
            if (b.haveParts()) {
               if (bprime.haveParts()) {
                  b.parts.union(bprime.copyParts());
               } else {
                  b.addPart(
                     new Part(bprime.x, bprime.y, bprime.fwidth[0], bprime.fwidth[1], bprime.fwidth[2], bprime.fheight[0], bprime.fheight[1], bprime.fheight[2])
                  );
               }
            } else {
               b.addPart(new Part(b.x, b.y, b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], b.fheight[1], b.fheight[2]));
               b.addPart(
                  new Part(bprime.x, bprime.y, bprime.fwidth[0], bprime.fwidth[1], bprime.fwidth[2], bprime.fheight[0], bprime.fheight[1], bprime.fheight[2])
               );
            }

            if (bprime.havePieces()) {
               b.pieces.union(bprime.copyPieces());
            }
         }

         b.draw(this.getGraphics());
      }
   }

   public void initVPieces(int mx, int my) {
      int i = this.findbar(mx, my);
      if (i >= 0) {
         Bar b = (Bar)this.barlist.elementAt(i);
         if (b != this.getClientProperty("Unit Bar")) {
            String command = this.getConfiguration().getClientProperty("How Pieces Work");
            if (!b.havePieces()) {
               Piece p1 = new Piece(b.x, b.y, mx - b.x, b.height);
               Piece p2 = new Piece(mx, b.y, b.x + b.width - mx, b.height);
               p1.setColor(b.getColor());
               p2.setColor(b.getColor());
               b.pieces.addElement(p1);
               b.pieces.addElement(p2);
            } else if (command == "Local pieces") {
               int j = b.findpiece(mx, my);
               if (j >= 0) {
                  Piece p = (Piece)b.pieces.elementAt(j);
                  b.pieces.removeElementAt(j);
                  Piece p1 = new Piece(p.x, p.y, mx - p.x, p.height);
                  Piece p2 = new Piece(mx, p.y, p.x + p.width - mx, p.height);
                  p1.setColor(p.getColor());
                  p2.setColor(p.getColor());
                  b.pieces.addElement(p1);
                  b.pieces.addElement(p2);
               }
            } else {
               VList newpieces = new VList();

               for (int j = 0; j < b.pieces.size(); j++) {
                  Piece p = (Piece)b.pieces.elementAt(j);
                  if (p.x < mx && mx < p.x + p.width) {
                     Piece p1 = new Piece(p.x, p.y, mx - p.x, p.height);
                     Piece p2 = new Piece(mx, p.y, p.x + p.width - mx, p.height);
                     p1.setColor(p.getColor());
                     p2.setColor(p.getColor());
                     newpieces.addElement(p1);
                     newpieces.addElement(p2);
                  } else {
                     newpieces.addElement(p);
                  }
               }

               b.pieces = newpieces;
            }

            b.draw(this.getGraphics());
         }
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void initHPieces(int mx, int my) {
      int i = this.findbar(mx, my);
      if (i >= 0) {
         Bar b = (Bar)this.barlist.elementAt(i);
         if (b != this.getClientProperty("Unit Bar")) {
            String command = this.getConfiguration().getClientProperty("How Pieces Work");
            if (!b.havePieces()) {
               Piece p1 = new Piece(b.x, b.y, b.width, my - b.y);
               Piece p2 = new Piece(b.x, my, b.width, b.y + b.height - my);
               p1.setColor(b.getColor());
               p2.setColor(b.getColor());
               b.pieces.addElement(p1);
               b.pieces.addElement(p2);
            } else if (command == "Local pieces") {
               int j = b.findpiece(mx, my);
               if (j >= 0) {
                  Piece p = (Piece)b.pieces.elementAt(j);
                  b.pieces.removeElementAt(j);
                  Piece p1 = new Piece(p.x, p.y, p.width, my - p.y);
                  Piece p2 = new Piece(p.x, my, p.width, p.y + p.height - my);
                  p1.setColor(p.getColor());
                  p2.setColor(p.getColor());
                  b.pieces.addElement(p1);
                  b.pieces.addElement(p2);
               }
            } else {
               VList newpieces = new VList();

               for (int j = 0; j < b.pieces.size(); j++) {
                  Piece p = (Piece)b.pieces.elementAt(j);
                  if (p.y < my && my < p.y + p.height) {
                     Piece p1 = new Piece(p.x, p.y, p.width, my - p.y);
                     Piece p2 = new Piece(p.x, my, p.width, p.y + p.height - my);
                     p1.setColor(p.getColor());
                     p2.setColor(p.getColor());
                     newpieces.addElement(p1);
                     newpieces.addElement(p2);
                  } else {
                     newpieces.addElement(p);
                  }
               }

               b.pieces = newpieces;
            }

            b.draw(this.getGraphics());
         }
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void initVParts(int mx, int my) {
      int i = this.findbar(mx, my);
      int n = (Integer)this.controlpane.getClientProperty("Number of Parts");
      if (i >= 0) {
         Bar b = (Bar)this.barlist.elementAt(i);
         if (b != this.getClientProperty("Unit Bar")) {
            String command = this.getConfiguration().getClientProperty("How Parts Work");
            if (!b.haveParts()) {
               float dx = (float)b.width / n;

               for (int k = 0; k < n; k++) {
                  int numerator = b.fwidth[1];
                  int denominator = n * b.fwidth[2];
                  int gcf = MyMath.GCF(numerator, denominator);
                  Part p1 = new Part(b.x + (int)(k * dx), b.y, b.fwidth[0], numerator / gcf, denominator / gcf, b.fheight[0], b.fheight[1], b.fheight[2]);
                  p1.setColor(b.getColor());
                  b.parts.addElement(p1);
               }
            } else if (command == "Local parts") {
               int j = b.findpart(mx, my);
               Part p = (Part)b.parts.elementAt(j);
               b.parts.removeElementAt(j);
               float dx = (float)p.width / n;

               for (int k = 0; k < n; k++) {
                  int gcf = MyMath.GCF(n * p.fwidth[2], p.fwidth[1]);
                  Part p1 = new Part(p.x + (int)(k * dx), p.y, p.fwidth[0], p.fwidth[1] / gcf, p.fwidth[2] * n / gcf, p.fheight[0], p.fheight[1], p.fheight[2]);
                  p1.setColor(p.getColor());
                  b.parts.addElement(p1);
               }
            } else {
               float dx = (float)b.width / n;

               for (int k = 1; k < n; k++) {
                  int x0 = b.x + (int)(k * dx);

                  for (int j = 0; j < b.parts.size(); j++) {
                     Part p = (Part)b.parts.elementAt(j);
                     if (p.x < x0 && x0 < p.x + p.width) {
                        int lcm = MyMath.LCM(n, p.fwidth[2]);
                        Part p1;
                        Part p2;
                        if (MyMath.GCF(p.fwidth[0], b.width) != 1) {
                           int numerator1 = Math.round((float)((x0 - p.x) * lcm) / p.fwidth[0]);
                           int numerator2 = lcm / p.fwidth[2] * p.fwidth[1] - numerator1;
                           int gcf1 = MyMath.GCF(numerator1, lcm);
                           int gcf2 = MyMath.GCF(numerator2, lcm);
                           p1 = new Part(p.x, p.y, p.fwidth[0], numerator1 / gcf1, lcm / gcf1, p.fheight[0], p.fheight[1], p.fheight[2]);
                           p1.setColor(p.getColor());
                           p2 = new Part(x0, p.y, p.fwidth[0], numerator2 / gcf2, lcm / gcf2, p.fheight[0], p.fheight[1], p.fheight[2]);
                           p2.setColor(p.getColor());
                        } else {
                           int numerator1 = x0 - p.x;
                           int numerator2 = p.x + p.width - x0;
                           int gcf1 = MyMath.GCF(numerator1, b.width);
                           int gcf2 = MyMath.GCF(numerator2, b.width);
                           p1 = new Part(p.x, p.y, b.width, numerator1 / gcf1, b.width / gcf1, p.fheight[0], p.fheight[1], p.fheight[2]);
                           p1.setColor(p.getColor());
                           p2 = new Part(x0, p.y, b.width, numerator2 / gcf2, b.width / gcf2, p.fheight[0], p.fheight[1], p.fheight[2]);
                           p2.setColor(p.getColor());
                        }

                        b.parts.removeElementAt(j);
                        b.parts.insertElementAt(p1, j);
                        b.parts.addElement(p2);
                     }
                  }
               }
            }

            b.draw(this.getGraphics());
         }
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void initHParts(int mx, int my) {
      int i = this.findbar(mx, my);
      int n = (Integer)this.controlpane.getClientProperty("Number of Parts");
      if (i >= 0) {
         Bar b = (Bar)this.barlist.elementAt(i);
         if (b != this.getClientProperty("Unit Bar")) {
            String command = this.getConfiguration().getClientProperty("How Parts Work");
            if (!b.haveParts()) {
               float dy = (float)b.height / n;

               for (int k = 0; k < n; k++) {
                  int numerator = b.fheight[1];
                  int denominator = n * b.fheight[2];
                  int gcf = MyMath.GCF(numerator, denominator);
                  Part p1 = new Part(b.x, b.y + (int)(k * dy), b.fwidth[0], b.fwidth[1], b.fwidth[2], b.fheight[0], numerator / gcf, denominator / gcf);
                  p1.setColor(b.getColor());
                  b.parts.addElement(p1);
               }
            } else if (command == "Local parts") {
               int j = b.findpart(mx, my);
               Part p = (Part)b.parts.elementAt(j);
               b.parts.removeElementAt(j);
               float dy = (float)p.height / n;

               for (int k = 0; k < n; k++) {
                  int gcf = MyMath.GCF(n * p.fheight[2], p.fheight[1]);
                  Part p1 = new Part(p.x, p.y + (int)(k * dy), p.fwidth[0], p.fwidth[1], p.fwidth[2], p.fheight[0], p.fheight[1] / gcf, p.fheight[2] * n / gcf);
                  p1.setColor(p.getColor());
                  b.parts.addElement(p1);
               }
            } else {
               float dy = (float)b.height / n;

               for (int k = 1; k < n; k++) {
                  int y0 = b.y + (int)(k * dy);

                  for (int j = 0; j < b.parts.size(); j++) {
                     Part p = (Part)b.parts.elementAt(j);
                     if (p.y < y0 && y0 < p.y + p.height) {
                        int lcm = MyMath.LCM(n, p.fheight[2]);
                        Part p1;
                        Part p2;
                        if (MyMath.GCF(p.fheight[0], b.height) != 1) {
                           int numerator1 = Math.round((float)((y0 - p.y) * lcm) / p.fheight[0]);
                           int numerator2 = lcm / p.fheight[2] * p.fheight[1] - numerator1;
                           int gcf1 = MyMath.GCF(numerator1, lcm);
                           int gcf2 = MyMath.GCF(numerator2, lcm);
                           p1 = new Part(p.x, p.y, p.fwidth[0], p.fwidth[1], p.fwidth[2], p.fheight[0], numerator1 / gcf1, lcm / gcf1);
                           p1.setColor(p.getColor());
                           p2 = new Part(p.x, y0, p.fwidth[0], p.fwidth[1], p.fwidth[2], p.fheight[0], numerator2 / gcf2, lcm / gcf2);
                           p2.setColor(p.getColor());
                        } else {
                           int numerator1 = y0 - p.y;
                           int numerator2 = p.y + p.height - y0;
                           int gcf1 = MyMath.GCF(numerator1, b.height);
                           int gcf2 = MyMath.GCF(numerator2, b.height);
                           p1 = new Part(p.x, p.y, p.fwidth[0], p.fwidth[1], p.fwidth[2], b.height, numerator1 / gcf1, b.height / gcf1);
                           p1.setColor(p.getColor());
                           p2 = new Part(p.x, y0, p.fwidth[0], p.fwidth[1], p.fwidth[2], b.height, numerator2 / gcf2, b.height / gcf2);
                           p2.setColor(p.getColor());
                        }

                        b.parts.removeElementAt(j);
                        b.parts.insertElementAt(p1, j);
                        b.parts.addElement(p2);
                     }
                  }
               }
            }

            b.draw(this.getGraphics());
         }
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void initErase(int mx, int my) {
      int i = this.findgroup(mx, my);
      if (i >= 0) {
         Group gp = (Group)this.grouplist.elementAt(i);
         gp.erase(this.getGraphics());
         this.repaint();
         this.grouplist.removeElementAt(i);
      } else {
         i = this.findcover(mx, my);
         if (i >= 0) {
            Cover c = (Cover)this.coverlist.elementAt(i);
            c.erase(this.getGraphics());
            this.repaint();
            this.coverlist.removeElementAt(i);
         } else {
            i = this.findbar(mx, my);
            if (i >= 0) {
               Bar b = (Bar)this.barlist.elementAt(i);
               b.erase(this.getGraphics());
               this.repaint();
               this.trashlist.addElement(b);
               this.barlist.removeElementAt(i);
            } else {
               i = this.findmat(mx, my);
               if (i >= 0) {
                  Mat m = (Mat)this.matlist.elementAt(i);
                  m.erase(this.getGraphics());
                  this.repaint();
                  this.trashlist.addElement(m);
                  this.matlist.removeElementAt(i);
               } else {
                  this.controlpane.selectNoButtons();
               }
            }
         }
      }
   }

   public void initCopy(int mx, int my) {
      int i = this.findgroup(mx, my);
      if (i >= 0) {
         Group gp = (Group)this.grouplist.elementAt(i);
         this.putClientProperty("Current Object", gp);
      } else {
         i = this.findcover(mx, my);
         if (i >= 0) {
            Cover c = (Cover)this.coverlist.elementAt(i);
            this.putClientProperty("Current Object", c);
            c.setHighlight(true);
            c.draw(this.getGraphics());
         } else {
            i = this.findbar(mx, my);
            if (i >= 0) {
               Bar b = (Bar)this.barlist.elementAt(i);
               this.putClientProperty("Current Object", b);
               b.setHighlight(true);
               b.draw(this.getGraphics());
            } else {
               i = this.findmat(mx, my);
               if (i >= 0) {
                  Mat m = (Mat)this.matlist.elementAt(i);
                  this.putClientProperty("Current Object", m);
                  m.setHighlight(true);
                  m.draw(this.getGraphics());
               } else {
                  this.controlpane.selectNoButtons();
               }
            }
         }
      }
   }

   public void middleCopy(int mx, int my) {
      Object o = this.getClientProperty("Current Object");
      if (o instanceof Bar) {
         Bar b = (Bar)o;
         Bar bprime = (Bar)b.copy();
         bprime.move(mx, my);
         bprime.setHighlight(false);
         bprime.draw(this.getGraphics());
         this.barlist.addElement(bprime);
      } else if (o instanceof Cover) {
         Cover c = (Cover)o;
         Cover cprime = (Cover)c.copy();
         cprime.move(mx, my);
         cprime.setHighlight(false);
         cprime.draw(this.getGraphics());
         this.coverlist.addElement(cprime);
      } else if (o instanceof Mat) {
         Mat m = (Mat)o;
         Mat mprime = (Mat)m.copy();
         mprime.move(mx, my);
         mprime.setHighlight(false);
         mprime.draw(this.getGraphics());
         this.matlist.addElement(mprime);
      }
   }

   public void mousePressed(MouseEvent evt) {
      int mx = evt.getX();
      int my = evt.getY();
      String command = this.controlpane.getControlPaneButtons().getSelection().getActionCommand();
      if (command == null) {
         this.initDrag(mx, my);
      } else if (command == "Make Bar") {
         this.initMakeBar(mx, my);
      } else if (command == "Make Cover") {
         this.initMakeCover(mx, my);
      } else if (command == "Make Mat") {
         this.initMakeMat(mx, my);
      } else if (command == "Vertical shade") {
         this.initVShade(mx, my);
      } else if (command == "Horizontal shade") {
         this.initHShade(mx, my);
      } else if (command == "Cut") {
         this.middleCutPartOne(mx, my);
      } else if (command == "Pullout Parts") {
         this.initPullout(mx, my);
      } else if (command == "Pullout Pieces") {
         this.initPullout(mx, my);
      } else if (command == "Erase") {
         this.initAreaErase(mx, my);
      }
   }

   public void initAreaErase(int mx, int my) {
      Rectangle r = new Rectangle(mx, my, 0, 0);
      this.putClientProperty("Clipboard", r);
   }

   public void middleCutPartOne(int mx, int my) {
      if (this.getClientProperty("Current Object") != null) {
         Bar b = (Bar)this.getClientProperty("Current Object");
         Graphics g = this.getGraphics();
         g.setColor(Color.lightGray);
         g.setXORMode(this.getBackground());
         if (Math.abs(b.lastx - mx) > Math.abs(b.lasty - my)) {
            b.last_cut_direction = "Horizontal";
            g.drawLine(b.x, my, b.x + b.width, my);
         } else {
            b.last_cut_direction = "Vertical";
            g.drawLine(mx, b.y, mx, b.y + b.height);
         }

         g.dispose();
         b.lastx = mx;
         b.lasty = my;
      }
   }

   public void initPullout(int mx, int my) {
      int i = this.findbar(mx, my);
      if (i >= 0) {
         Rectangle r = new Rectangle(mx, my, 0, 0);
         this.putClientProperty("Clipboard", r);
         this.putClientProperty("Current Object", this.barlist.elementAt(i));
      } else {
         this.controlpane.selectNoButtons();
      }
   }

   public void initVShade(int mx, int my) {
   }

   public void initHShade(int mx, int my) {
   }

   public void initJoin(int mx, int my) {
      int i = this.findbar(mx, my);
      if (i >= 0) {
         Object o = this.getClientProperty("Current Object");
         if (o != null) {
            Bar b1 = (Bar)o;
            Bar b2 = (Bar)this.barlist.elementAt(i);
            boolean flag = b2.joinBars(b1, mx, my);
            if (flag) {
               this.barlist.removeElement(b1);
               this.repaint();
               b2.setLabel("");
               this.putClientProperty("Current Object", null);
            } else {
               b1.setHighlight(false);
               this.putClientProperty("Current Object", null);
               this.getJoinDialog().setVisible(true);
            }
         } else {
            Bar b1 = (Bar)this.barlist.elementAt(i);
            b1.setHighlight(true);
            b1.draw(this.getGraphics());
            this.putClientProperty("Current Object", b1);
         }
      } else {
         Object o = this.getClientProperty("Current Object");
         if (o != null) {
            Bar b1 = (Bar)o;
            b1.setHighlight(false);
            b1.draw(this.getGraphics());
            this.putClientProperty("Current Object", null);
         }

         this.controlpane.selectNoButtons();
      }
   }

   public void initDrag(int mx, int my) {
      int i = this.findgroup(mx, my);
      if (i >= 0) {
         this.initDragGroup(mx, my, i);
      } else {
         i = this.findcover(mx, my);
         if (i >= 0) {
            this.initDragCover(mx, my, i);
         } else {
            i = this.findbar(mx, my);
            if (i >= 0) {
               this.initDragBar(mx, my, i);
            } else {
               i = this.findmat(mx, my);
               if (i >= 0) {
                  this.initDragMat(mx, my, i);
               } else {
                  this.controlpane.selectNoButtons();
               }
            }
         }
      }
   }

   public void initDragGroup(int mx, int my, int index) {
      Group g = (Group)this.grouplist.elementAt(index);
      this.putClientProperty("Current Object", g);
      Graphics gr = this.getGraphics();
      this.grouplist.removeElementAt(index);
      g.erase(gr);
      g.setPermanent(false);
      gr.setXORMode(this.getBackground());
      g.draw(gr);
      g.lastx = mx;
      g.lasty = my;
   }

   public void initDragBar(int mx, int my, int index) {
      Bar b = (Bar)this.barlist.elementAt(index);
      this.putClientProperty("Current Object", b);
      Graphics g = this.getGraphics();
      this.barlist.removeElementAt(index);
      b.erase(g);
      b.setPermanent(false);
      g.setXORMode(this.getBackground());
      b.draw(g);
      b.lastx = mx;
      b.lasty = my;
   }

   public void initDragCover(int mx, int my, int index) {
      Cover c = (Cover)this.coverlist.elementAt(index);
      this.coverlist.removeElement(c);
      this.putClientProperty("Current Object", c);
      Graphics g = this.getGraphics();
      c.erase(g);
      c.setPermanent(false);
      g.setXORMode(this.getBackground());
      c.draw(g);
      c.lastx = mx;
      c.lasty = my;
   }

   public void initDragMat(int mx, int my, int index) {
      Mat m = (Mat)this.matlist.elementAt(index);
      this.matlist.removeElement(m);
      this.putClientProperty("Current Object", m);
      Graphics g = this.getGraphics();
      m.erase(g);
      m.setPermanent(false);
      g.setXORMode(this.getBackground());
      m.draw(g);
      m.lastx = mx;
      m.lasty = my;
   }

   public void initMakeBar(int mx, int my) {
      Bar b = new Bar();
      b.x = mx;
      b.y = my;
      b.oldx = mx;
      b.oldy = my;
      b.fwidth[0] = 0;
      b.fwidth[1] = 1;
      b.fwidth[2] = 1;
      b.fheight[0] = 0;
      b.fheight[1] = 1;
      b.fheight[2] = 1;
      this.putClientProperty("Current Object", b);
   }

   public void initMakeCover(int mx, int my) {
      Cover c = new Cover();
      c.x = mx;
      c.y = my;
      c.oldx = mx;
      c.oldy = my;
      this.putClientProperty("Current Object", c);
   }

   public void initMakeMat(int mx, int my) {
      Mat m = new Mat();
      m.x = mx;
      m.y = my;
      m.oldx = mx;
      m.oldy = my;
      this.putClientProperty("Current Object", m);
   }

   public void mouseReleased(MouseEvent evt) {
      int mx = evt.getX();
      int my = evt.getY();
      String command = this.controlpane.getControlPaneButtons().getSelection().getActionCommand();
      if (command == null) {
         this.finalDrag(mx, my);
      } else if (command == "Make Bar") {
         this.finalMakeProtoBar();
      } else if (command == "Make Cover") {
         this.finalMakeProtoBar();
      } else if (command == "Make Mat") {
         this.finalMakeProtoBar();
      } else if (command == "Pullout Parts") {
         this.finalPulloutParts(mx, my);
      } else if (command == "Pullout Pieces") {
         this.finalPulloutPieces(mx, my);
      } else if (command == "Cut") {
         this.finalCut(mx, my);
      } else if (command == "Erase") {
         this.finalAreaErase(mx, my);
      }
   }

   public void finalAreaErase(int mx, int my) {
      if (this.getClientProperty("Clipboard") != null) {
         Rectangle r = (Rectangle)this.getClientProperty("Clipboard");
         int len = this.barlist.size();
         VList removed = new VList();

         for (int i = 0; i < len; i++) {
            Bar b = (Bar)this.barlist.elementAt(i);
            Rectangle rb = new Rectangle(b.x, b.y, b.width, b.height);
            Rectangle rprime = rb.intersection(r);
            if (rprime.equals(rb)) {
               removed.addElement(b);
               b.erase(this.getGraphics());
            }
         }

         len = removed.size();

         for (int ix = 0; ix < len; ix++) {
            this.barlist.removeElement(removed.elementAt(ix));
         }

         removed.removeAllElements();
         len = this.coverlist.size();

         for (int ix = 0; ix < len; ix++) {
            Cover c = (Cover)this.coverlist.elementAt(ix);
            Rectangle rc = new Rectangle(c.x, c.y, c.width, c.height);
            Rectangle rprime = rc.intersection(r);
            if (rprime.equals(rc)) {
               removed.addElement(c);
               c.erase(this.getGraphics());
            }
         }

         len = removed.size();

         for (int ixx = 0; ixx < len; ixx++) {
            this.coverlist.removeElement(removed.elementAt(ixx));
         }

         removed.removeAllElements();
         len = this.matlist.size();

         for (int ixx = 0; ixx < this.matlist.size(); ixx++) {
            Mat m = (Mat)this.matlist.elementAt(ixx);
            Rectangle rm = new Rectangle(m.x, m.y, m.width, m.height);
            Rectangle rprime = rm.intersection(r);
            if (rprime.equals(rm)) {
               removed.addElement(m);
               m.erase(this.getGraphics());
            }
         }

         len = removed.size();

         for (int ixxx = 0; ixxx < len; ixxx++) {
            this.matlist.removeElement(removed.elementAt(ixxx));
         }

         this.repaint();
      }
   }

   public void finalCut(int mx, int my) {
      if (this.getClientProperty("Current Object") != null) {
         Bar b = (Bar)this.getClientProperty("Current Object");
         if (b.inside(mx, my)) {
            new Bar();
            new Bar();
            Bar g;
            Bar b2;
            if (b.last_cut_direction == "Vertical") {
               if (!b.haveParts()) {
                  g = new Bar(b.x, b.y, b.lastx - b.x, b.height);
                  b2 = new Bar(b.lastx, b.y, b.x + b.width - b.lastx, b.height);
               } else {
                  int i = 1;
                  Part p = (Part)b.parts.elementAt(i);

                  while ((p.x - this.TOL >= mx || mx >= p.x + this.TOL) && i < b.parts.size()) {
                     p = (Part)b.parts.elementAt(++i);
                  }

                  g = new Bar(b.x, b.y, p.x - b.x, b.height);
                  b2 = new Bar(p.x, b.y, b.x + b.width - p.x, b.height);
               }
            } else if (!b.haveParts()) {
               g = new Bar(b.x, b.y, b.width, b.lasty - b.y);
               b2 = new Bar(b.x, b.lasty, b.width, b.y + b.height - b.lasty);
            } else {
               int j = 1;
               Part p = (Part)b.parts.elementAt(j);

               while ((p.y - this.TOL >= my || my >= p.y + this.TOL) && j < b.parts.size()) {
                  p = (Part)b.parts.elementAt(++j);
               }

               g = new Bar(b.x, b.y, b.width, p.y - b.y);
               b2 = new Bar(b.x, p.y, b.width, b.y + b.height - p.y);
            }

            Rectangle r1 = new Rectangle(g.x, g.y, g.width, g.height);
            Rectangle r2 = new Rectangle(b2.x, b2.y, b2.width, b2.height);
            if (b.haveParts()) {
               for (int i = 0; i < b.parts.size(); i++) {
                  Part p = (Part)b.parts.elementAt(i);
                  Rectangle r = new Rectangle(p.x, p.y, p.width, p.height);
                  Rectangle r_r1 = r.intersection(r1);
                  Rectangle r_r2 = r.intersection(r2);
                  if (!r_r1.isEmpty()) {
                     g.addPart(new Part(r_r1.x, r_r1.y, r_r1.width, r_r1.height));
                  }

                  if (!r_r2.isEmpty()) {
                     b2.addPart(new Part(r_r2.x, r_r2.y, r_r2.width, r_r2.height));
                  }
               }
            }

            if (b.havePieces()) {
               for (int i = 0; i < b.pieces.size(); i++) {
                  Piece px = (Piece)b.pieces.elementAt(i);
                  Rectangle rx = new Rectangle(px.x, px.y, px.width, px.height);
                  Rectangle r_r1x = rx.intersection(r1);
                  Rectangle r_r2x = rx.intersection(r2);
                  if (!r_r1x.isEmpty()) {
                     g.addPiece(new Piece(r_r1x.x, r_r1x.y, r_r1x.width, r_r1x.height));
                  }

                  if (!r_r2x.isEmpty()) {
                     b2.addPiece(new Piece(r_r2x.x, r_r2x.y, r_r2x.width, r_r2x.height));
                  }
               }
            }

            this.barlist.removeElement(b);
            this.barlist.addElement(g);
            this.barlist.addElement(b2);
            g.setPermanent(true);
            b2.setPermanent(true);
            Graphics gx = this.getGraphics();
            b.erase(gx);
            g.draw(gx);
            b2.draw(gx);
            gx.dispose();
            this.putClientProperty("Current Object", null);
         } else {
            Graphics gx = this.getGraphics();
            gx.setXORMode(this.getBackground());
            if (b.last_cut_direction == "Vertical") {
               gx.drawLine(mx, b.y, mx, b.y + b.height);
            } else if (b.last_cut_direction == "Horizontal") {
               gx.drawLine(b.x, my, b.x + b.width, my);
            }
         }
      }
   }

   public void finalPulloutParts(int mx, int my) {
      if (this.getClientProperty("Current Object") != null && this.getClientProperty("Clipboard") != null) {
         Bar b = (Bar)this.getClientProperty("Current Object");
         Rectangle r = (Rectangle)this.getClientProperty("Clipboard");
         if (!r.isEmpty() && !b.parts.isEmpty()) {
            Group g = new Group();

            for (int i = 0; i < b.parts.size(); i++) {
               Part p = (Part)b.parts.elementAt(i);
               Rectangle rprime = new Rectangle(p.x, p.y, p.width, p.height);
               if (rprime.intersects(r)) {
                  Bar bprime = new Bar(p.x, p.y, p.fwidth[0], p.fwidth[1], p.fwidth[2], p.fheight[0], p.fheight[1], p.fheight[2]);
                  bprime.setColor(p.getColor());
                  bprime.setPermanent(true);
                  g.add(bprime);
                  this.barlist.addElement(bprime);
                  bprime.setHighlight(true);
               }
            }

            if (!g.isEmpty()) {
               g.draw(this.getGraphics());
               this.grouplist.addElement(g);
            }
         }

         if (b.parts.isEmpty()) {
            b.draw(this.getGraphics());
         }

         if (r.isEmpty()) {
            int j = b.findpart(mx, my);
            if (j >= 0) {
               Part p = (Part)b.parts.elementAt(j);
               Bar bprime = new Bar(p.x, p.y, p.fwidth[0], p.fwidth[1], p.fwidth[2], p.fheight[0], p.fheight[1], p.fheight[2]);
               bprime.setPermanent(true);
               bprime.setColor(p.getColor());
               this.barlist.addElement(bprime);
               bprime.draw(this.getGraphics());
            }
         }
      }

      this.putClientProperty("Current Object", null);
      this.putClientProperty("Clipboard", null);
   }

   public void finalPulloutPieces(int mx, int my) {
      if (this.getClientProperty("Current Object") != null && this.getClientProperty("Clipboard") != null) {
         Bar b = (Bar)this.getClientProperty("Current Object");
         Rectangle r = (Rectangle)this.getClientProperty("Clipboard");
         if (!r.isEmpty() && !b.pieces.isEmpty()) {
            Group g = new Group();

            for (int i = 0; i < b.pieces.size(); i++) {
               Piece p = (Piece)b.pieces.elementAt(i);
               Rectangle rprime = new Rectangle(p.x, p.y, p.width, p.height);
               if (rprime.intersects(r)) {
                  Bar bprime = new Bar(p.x, p.y, p.width, p.height);
                  bprime.setColor(p.getColor());
                  bprime.setPermanent(true);
                  g.add(bprime);
                  this.barlist.addElement(bprime);
                  bprime.setHighlight(true);
               }
            }

            if (!g.isEmpty()) {
               g.draw(this.getGraphics());
               this.grouplist.addElement(g);
            }
         }

         if (b.pieces.isEmpty()) {
            b.draw(this.getGraphics());
         }

         if (r.isEmpty()) {
            int j = b.findpiece(mx, my);
            if (j >= 0) {
               Piece p = (Piece)b.pieces.elementAt(j);
               Bar bprime = new Bar(p.x, p.y, p.width, p.height);
               bprime.setPermanent(true);
               bprime.setColor(p.getColor());
               this.barlist.addElement(bprime);
               bprime.draw(this.getGraphics());
            }
         }
      }

      this.putClientProperty("Current Object", null);
      this.putClientProperty("Clipboard", null);
   }

   public void finalDrag(int mx, int my) {
      MWObject o = (MWObject)this.getClientProperty("Current Object");
      String command = this.controlpane.getControlPaneButtons().getSelection().getActionCommand();
      if (o != null) {
         if (o instanceof Group) {
            this.grouplist.addElement(o);
         } else if (o instanceof Bar) {
            this.barlist.addElement(o);
         } else if (o instanceof Cover) {
            this.coverlist.addElement(o);
         } else if (o instanceof Mat) {
            this.matlist.addElement(o);
         }

         o.setPermanent(true);
         this.repaint();
         this.putClientProperty("Current Object", null);
      }
   }

   public void finalMakeProtoBar() {
      ProtoBar pb = (ProtoBar)this.getClientProperty("Current Object");
      Graphics g = this.getGraphics();
      g.setPaintMode();
      pb.setPermanent(true);
      this.repaint();
      g.dispose();
      if (pb instanceof Bar) {
         this.barlist.addElement(pb);
         Bar b = (Bar)pb;
         b.fwidth[0] = b.width;
         b.fheight[0] = b.height;
      } else if (pb instanceof Cover) {
         this.coverlist.addElement(pb);
      } else if (pb instanceof Mat) {
         this.matlist.addElement(pb);
      }

      this.putClientProperty("Current Object", null);
   }

   @Override
   public void mouseMoved(MouseEvent evt) {
   }

   @Override
   public void mouseDragged(MouseEvent evt) {
      int mx = evt.getX();
      int my = evt.getY();
      String command = this.controlpane.getControlPaneButtons().getSelection().getActionCommand();
      if (command == null || command == "Join") {
         this.middleDrag(mx, my);
      } else if (command == "Make Bar") {
         this.middleMakeBar(mx, my);
      } else if (command == "Make Cover") {
         this.middleMakeCover(mx, my);
      } else if (command == "Make Mat") {
         this.middleMakeMat(mx, my);
      } else if (command == "Cut") {
         this.middleCutPartTwo(mx, my);
      } else if (command == "Pullout Parts") {
         this.middlePulloutGroupParts(mx, my);
      } else if (command == "Pullout Pieces") {
         this.middlePulloutGroupPieces(mx, my);
      } else if (command == "Erase") {
         this.middleAreaErase(mx, my);
      }
   }

   public void middleAreaErase(int mx, int my) {
      Rectangle r = (Rectangle)this.getClientProperty("Clipboard");
      Graphics g = this.getGraphics();
      g.setXORMode(this.getBackground());
      g.drawRect(r.x, r.y, r.width, r.height);
      r.width = mx > r.x ? mx - r.x : r.x - mx;
      r.height = my > r.y ? my - r.y : r.y - my;
      g.drawRect(r.x, r.y, r.width, r.height);
   }

   public void middlePulloutGroupParts(int mx, int my) {
      Rectangle r = (Rectangle)this.getClientProperty("Clipboard");
      Graphics g = this.getGraphics();
      g.setXORMode(this.getBackground());
      g.drawRect(r.x, r.y, r.width, r.height);
      r.width = mx > r.x ? mx - r.x : r.x - mx;
      r.height = my > r.y ? my - r.y : r.y - my;
      g.drawRect(r.x, r.y, r.width, r.height);
   }

   public void middlePulloutGroupPieces(int mx, int my) {
      Rectangle r = (Rectangle)this.getClientProperty("Clipboard");
      Graphics g = this.getGraphics();
      g.setXORMode(this.getBackground());
      g.drawRect(r.x, r.y, r.width, r.height);
      r.width = mx > r.x ? mx - r.x : r.x - mx;
      r.height = my > r.y ? my - r.y : r.y - my;
      g.drawRect(r.x, r.y, r.width, r.height);
   }

   public void middleCutPartTwo(int mx, int my) {
      if (this.getClientProperty("Current Object") != null) {
         Bar b = (Bar)this.getClientProperty("Current Object");
         Graphics g = this.getGraphics();
         g.setColor(Color.lightGray);
         g.setXORMode(this.getBackground());
         if (b.last_cut_direction == "Vertical") {
            g.drawLine(b.lastx, b.y, b.lastx, b.y + b.height);
            g.drawLine(mx, b.y, mx, b.y + b.height);
         } else if (b.last_cut_direction == "Horizontal") {
            g.drawLine(b.x, b.lasty, b.x + b.width, b.lasty);
            g.drawLine(b.x, my, b.x + b.width, my);
         }

         b.lastx = mx;
         b.lasty = my;
      }
   }

   public void middleDrag(int mx, int my) {
      if (this.getClientProperty("Current Object") != null) {
         Object o = this.getClientProperty("Current Object");
         if (o instanceof Bar) {
            this.middleDragBar(mx, my, (Bar)o);
         } else if (o instanceof Cover) {
            this.middleDragCover(mx, my, (Cover)o);
         } else if (o instanceof Mat) {
            this.middleDragMat(mx, my, (Mat)o);
         } else if (o instanceof Group) {
            this.middleDragGroup(mx, my, (Group)o);
         }
      }
   }

   public void middleDragGroup(int mx, int my, Group g) {
      Graphics gr = this.getGraphics();
      gr.setXORMode(this.getBackground());
      g.draw(gr);
      g.translate(mx - g.lastx, my - g.lasty);
      g.lastx = mx;
      g.lasty = my;
      g.draw(gr);
   }

   public void middleDragBar(int mx, int my, Bar b) {
      Graphics g = this.getGraphics();
      g.setXORMode(this.getBackground());
      b.draw(g);
      b.translate(mx - b.lastx, my - b.lasty);
      b.lastx = mx;
      b.lasty = my;
      b.draw(g);
   }

   public void middleDragCover(int mx, int my, Cover c) {
      Graphics g = this.getGraphics();
      g.setXORMode(this.getBackground());
      c.draw(g);
      c.translate(mx - c.lastx, my - c.lasty);
      c.lastx = mx;
      c.lasty = my;
      c.draw(g);
   }

   public void middleDragMat(int mx, int my, Mat m) {
      Graphics g = this.getGraphics();
      g.setXORMode(this.getBackground());
      m.draw(g);
      m.translate(mx - m.lastx, my - m.lasty);
      m.lastx = mx;
      m.lasty = my;
      m.draw(g);
   }

   public void middleMakeBar(int mx, int my) {
      Bar b = (Bar)this.getClientProperty("Current Object");
      Graphics g = this.getGraphics();
      g.setXORMode(this.getBackground());
      b.draw(g);
      b.update(mx, my);
      b.draw(g);
      g.dispose();
   }

   public void middleMakeCover(int mx, int my) {
      Cover c = (Cover)this.getClientProperty("Current Object");
      Graphics g = this.getGraphics();
      g.setXORMode(this.getBackground());
      c.draw(g);
      c.update(mx, my);
      c.draw(g);
      g.dispose();
   }

   public void middleMakeMat(int mx, int my) {
      Mat m = (Mat)this.getClientProperty("Current Object");
      Graphics g = this.getGraphics();
      g.setXORMode(this.getBackground());
      m.draw(g);
      m.oldx = mx;
      m.oldy = my;
      m.draw(g);
      g.dispose();
   }

   public void initDialogs(MWFrame f) {
      this.labeldialog = new TextDialog(f, "isim", true, "", "dogruisim");
      new JLabel("isim gir:");
      this.labeldialog.setBounds(f.screenwidth / 2 - 90, f.screenheight / 2 - 50, 180, 100);
      this.joindialog = new ErrorDialog(f, "Birlesmez!", true, "SADECE AYNI GENiSLiK VE YUKSEKLiGE SAHiP BÜTÜNLER BiRLESiR");
      this.joindialog.setBounds(f.screenwidth / 2 - 150, f.screenheight / 2 - 50, 300, 100);
      this.filldialog = new FillDialog(f, this);
      this.saveDialog = new FileDialog(f, "Kaydet", 1);
      this.loadDialog = new FileDialog(f, "Yükle", 0);
      this.saveErrDlog = new ErrorDialog(f, "File output error", true, "The microworld has not been saved");
      this.saveErrDlog.setBounds(f.screenwidth / 2 - 120, f.screenheight / 2 - 50, 240, 100);
      this.loadErrDlog = new ErrorDialog(f, "File format error", true, "This file does not contain a saved microworld");
      this.loadErrDlog.setBounds(f.screenwidth / 2 - 120, f.screenheight / 2 - 50, 240, 100);
      this.joinpartsdialog = new ErrorDialog(f, "Can't Join", true, "You can only join parts that are adjacent and are the same width or height");
      this.joinpartsdialog.setBounds(f.screenwidth / 2 - 200, f.screenheight / 2 - 50, 400, 100);
      this.joinpiecesdialog = new ErrorDialog(f, "Can't Join", true, "You can only join pieces that are adjacent and are the same width or height");
      this.joinpiecesdialog.setBounds(f.screenwidth / 2 - 200, f.screenheight / 2 - 50, 400, 100);
      this.newdialog = new ConfirmDialog(f, "New Playground", "This will erase everything in the playground.  Do you want to continue?");
      this.newdialog.setBounds(f.screenwidth / 2 - 200, f.screenheight / 2 - 50, 400, 100);
   }

   public TextDialog getLabelDialog() {
      return this.labeldialog;
   }

   public JDialog getJoinDialog() {
      return this.joindialog;
   }

   public Configuration getConfiguration() {
      return this.configure;
   }

   public void setConfiguration(Configuration config) {
      this.configure = config;
   }

   public void setCounter(Counter count) {
      this.counter = count;
   }

   public Counter getCounter() {
      return this.counter;
   }

   public void displayMeasure(Bar b, int measured, int whole) {
      b.setLabel(measured + "/" + whole);
      b.draw(this.getGraphics());
   }

   public void save() {
      byte[] TIMA_signature = new byte[]{53, 87, 11, -35};
      this.saveDialog.show();
      if (this.saveDialog.getFile() != null) {
         File saveFile = new File(this.saveDialog.getDirectory(), this.saveDialog.getFile());

         try {
            FileOutputStream ostream = new FileOutputStream(saveFile);
            ostream.write(TIMA_signature);
            ObjectOutputStream oos = new ObjectOutputStream(ostream);
            oos.writeObject(new String("TIMA Bars saved microworld"));
            oos.writeObject(new Integer(this.barlist.size()));

            for (int i = 0; i < this.barlist.size(); i++) {
               oos.writeObject(this.barlist.elementAt(i));
            }

            oos.flush();
            ostream.close();
         } catch (Exception var6) {
            this.saveErrDlog.setVisible(true);
         }
      }
   }

   public void load() {
      byte[] TIMA_signature = new byte[]{53, 87, 11, -35};
      byte[] signature = new byte[4];
      Integer listSize = null;
      FileInputStream istream = null;
      ObjectInputStream ois = null;
      this.loadDialog.show();
      if (this.loadDialog.getFile() != null) {
         try {
            File loadFile = new File(this.loadDialog.getDirectory(), this.loadDialog.getFile());
            istream = new FileInputStream(loadFile);
            istream.read(signature);

            for (int j = 0; j < signature.length; j++) {
               if (signature[j] != TIMA_signature[j]) {
                  System.out.println("bad signature!");
                  this.loadErrDlog.setVisible(true);
                  return;
               }
            }
         } catch (IOException var10) {
            this.loadErrDlog.setVisible(true);
            return;
         }

         try {
            ois = new ObjectInputStream(istream);
            String Preamble = (String)ois.readObject();
            System.out.println(Preamble);
            listSize = (Integer)ois.readObject();
         } catch (Exception var8) {
            this.loadErrDlog.setVisible(true);
            return;
         }

         this.barlist.removeAllElements();

         try {
            for (int i = 0; i < listSize; i++) {
               Bar B = (Bar)ois.readObject();
               this.barlist.addElement(B);
            }

            istream.close();
         } catch (Exception var9) {
            this.loadErrDlog.setVisible(true);
            return;
         }

         this.repaint();
      }
   }
}
