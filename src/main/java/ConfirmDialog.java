import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class ConfirmDialog extends JDialog {
   public static final int YES_OPTION = 1;
   public static final int NO_OPTION = 0;
   public static final int CANCEL_OPTION = -1;
   public static final int CLOSED_OPTION = -999;
   private Button yes = new Button("Yes");
   private Button no = new Button("No");
   private Button cancel = new Button("Cancel");
   protected int option;

   ConfirmDialog(JFrame f, String title, String text) {
      super(f, title, true);
      this.yes.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            ConfirmDialog.this.setVisible(false);
            ConfirmDialog.this.option = 1;
         }
      });
      this.no.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            ConfirmDialog.this.setVisible(false);
            ConfirmDialog.this.option = 0;
         }
      });
      this.cancel.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            ConfirmDialog.this.setVisible(false);
            ConfirmDialog.this.option = -1;
         }
      });
      this.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent evt) {
            ConfirmDialog.this.setVisible(false);
            ConfirmDialog.this.option = -999;
         }

         @Override
         public void windowOpened(WindowEvent evt) {
            ConfirmDialog.this.cancel.requestFocus();
         }
      });
      JLabel txt = new JLabel(text);
      txt.setForeground(Color.black);
      txt.setFont(new Font("SansSerif", 1, 10));
      JPanel p1 = new JPanel();
      p1.add(txt);
      JPanel p2 = new JPanel();
      p2.add(this.yes);
      p2.add(this.no);
      p2.add(this.cancel);
      this.getContentPane().setLayout(new BorderLayout());
      this.getContentPane().add(p1, "Center");
      this.getContentPane().add(p2, "South");
   }

   public int getOption() {
      return this.option;
   }
}
