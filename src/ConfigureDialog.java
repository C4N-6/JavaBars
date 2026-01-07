import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

class ConfigureDialog extends JDialog implements ActionListener {
   public static final String OK = "Ok";
   public static final String CANCEL = "Cancel";
   private JTabbedPane configurepane;

   ConfigureDialog(JFrame f) {
      super(f, "Configure", true);
      this.getContentPane().setLayout(new BorderLayout());
      this.configurepane = new JTabbedPane();
      this.makeTabs(this.configurepane);
      this.getContentPane().add(this.configurepane, "Center");
      JPanel p = new JPanel();
      JButton ok = new JButton("Ok");
      ok.setActionCommand("Ok");
      JButton cancel = new JButton("Cancel");
      cancel.setActionCommand("Cancel");
      ok.add(p);
      cancel.add(p);
      this.getContentPane().add(p);
      this.setVisible(false);
   }

   @Override
   public void actionPerformed(ActionEvent evt) {
      String command = evt.getActionCommand();
      if (command == "Ok") {
         this.setVisible(false);
      } else if (command == "Cancel") {
         this.setVisible(false);
      }
   }

   public void makeTabs(JTabbedPane jtp) {
      JPanel gp = this.makeGeneralPane();
      jtp.addTab("All Actions", gp);
   }

   public JPanel makeGeneralPane() {
      return new JPanel();
   }
}
