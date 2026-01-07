import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class TextDialog extends JDialog {
   private JTextArea textarea;
   private String textvalue;
   protected Button ok;
   protected Button cancel;

   TextDialog(JFrame f, String title, boolean modal, String initialtext, String okcommand) {
      super(f, title, modal);
      this.textarea = new JTextArea(initialtext);
      this.ok = new Button("Ok");
      this.cancel = new Button("Cancel");
      this.ok.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            TextDialog.this.textvalue = TextDialog.this.textarea.getText();
            TextDialog.this.setVisible(false);
         }
      });
      this.cancel.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            TextDialog.this.setVisible(false);
         }
      });
      this.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent evt) {
            TextDialog.this.setVisible(false);
         }

         @Override
         public void windowOpened(WindowEvent evt) {
            TextDialog.this.textarea.requestFocus();
         }
      });
      JPanel p1 = new JPanel();
      p1.setLayout(new GridLayout(1, 2));
      p1.add(this.ok);
      p1.add(this.cancel);
      this.getContentPane().setLayout(new BorderLayout());
      this.getContentPane().add(this.textarea, "Center");
      this.getContentPane().add(p1, "South");
   }

   public String getText() {
      return this.textvalue;
   }
}
