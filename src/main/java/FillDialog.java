import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

class FillDialog extends JDialog implements ActionListener, MouseListener {
   Color selectedColor;
   BigDot selectedColorDot;
   Palette myPalette;
   JPanel infoPane;
   JPanel palettePane;
   JPanel buttonPane;
   JButton okButton;
   JButton cancelButton;
   PlayGround playground;

   public FillDialog(Frame f, PlayGround p) {
      super(f, "Select fill color");
      this.playground = p;
      this.selectedColor = Color.red;
      this.getContentPane().setLayout(new GridLayout(3, 1));
      this.infoPane = new JPanel();
      this.infoPane.setBorder(new BevelBorder(0));
      this.infoPane.setLayout(new GridLayout(2, 1));
      this.infoPane.setBackground(Color.white);
      this.infoPane.add(new JLabel("Click on the colored dots to the left", 0));
      this.infoPane.add(new JLabel("Then click OK", 0));
      this.getContentPane().add(this.infoPane);
      this.palettePane = new JPanel();
      this.palettePane.setBorder(new EtchedBorder());
      this.palettePane.setLayout(new BorderLayout());
      this.myPalette = new Palette();
      this.myPalette.addMouseListener(this);
      this.palettePane.add("West", this.myPalette);
      this.palettePane.add("Center", new JLabel("Selected color: "));
      this.selectedColorDot = new BigDot(this.selectedColor);
      this.palettePane.add("East", this.selectedColorDot);
      this.getContentPane().add(this.palettePane);
      this.buttonPane = new JPanel();
      this.okButton = new JButton("OK");
      this.cancelButton = new JButton("Cancel");
      this.okButton.addActionListener(this);
      this.cancelButton.addActionListener(this);
      this.buttonPane.add(this.okButton);
      this.buttonPane.add(this.cancelButton);
      this.getContentPane().add(this.buttonPane);
      this.setSize(325, 200);
      this.setLocation(100, 100);
   }

   @Override
   public void mousePressed(MouseEvent e) {
      int j = e.getX() / (Palette.dotSize + Palette.spacerSize);
      int i = e.getY() / (Palette.dotSize + Palette.spacerSize);
      if (i < 2 && j < 6 && e.getX() >= j * Palette.dotSize + (j + 1) * Palette.spacerSize && e.getY() >= i * Palette.dotSize + (i + 1) * Palette.spacerSize) {
         this.selectedColor = this.myPalette.getColor(i, j);
         this.selectedColorDot.setColor(this.selectedColor);
         this.selectedColorDot.repaint();
      }
   }

   @Override
   public void mouseReleased(MouseEvent e) {
   }

   @Override
   public void mouseEntered(MouseEvent e) {
   }

   @Override
   public void mouseExited(MouseEvent e) {
   }

   public void mouseDragged(MouseEvent e) {
   }

   @Override
   public void mouseClicked(MouseEvent e) {
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == this.okButton) {
         this.playground.setFillColor(this.selectedColor);
      }

      this.setVisible(false);
   }
}
