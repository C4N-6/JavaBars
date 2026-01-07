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

class ErrorDialog extends JDialog {
   protected Button ok;
   protected JLabel text;

   ErrorDialog(JFrame f, String title, boolean modal, String txt) {
      super(f, title, modal);
      this.getContentPane().setLayout(new BorderLayout());
      this.ok = new Button("Ok");
      this.ok.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            ErrorDialog.this.setVisible(false);
         }
      });
      this.addWindowListener(new WindowAdapter() {
         public void WindowClosing(WindowEvent evt) {
            ErrorDialog.this.setVisible(false);
         }

         public void WindowOpened(WindowEvent evt) {
            ErrorDialog.this.ok.requestFocus();
         }
      });
      JPanel p1 = new JPanel();
      this.text = new JLabel(txt);
      this.text.setFont(new Font("SansSerif", 1, 10));
      this.text.setForeground(Color.black);
      p1.add(this.text);
      JPanel p2 = new JPanel();
      p2.add(this.ok);
      this.getContentPane().add(p1, "Center");
      this.getContentPane().add(p2, "South");
   }
}
