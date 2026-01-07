import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.Border;

class MWFrame extends JFrame {
   private static final long serialVersionUID = 1L;
   protected int screenwidth;
   protected int screenheight;

   MWFrame(String title, int dx, int dy) {
      super(title);
      this.screenwidth = dx;
      this.screenheight = dy;
      this.setSize(this.screenwidth, this.screenheight);
      this.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            System.exit(0);
         }
      });
      Container cp = this.getContentPane();
      JPanel p = new JPanel(true);
      cp.setBackground(UIManager.getColor("control"));
      cp.add(p);
      PlayGround pl = new PlayGround();
      pl.setSize(2000, 2000);
      Border b = new EdgedBorder(UIManager.getColor("control"));
      pl.setBackground(Color.white);
      pl.setBorder(b);
      JScrollPane sp = new JScrollPane(pl);
      p.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 1;
      gbc.weightx = 1.0;
      gbc.weighty = 1.0;
      gbc.gridwidth = 7;
      gbc.gridheight = 3;
      gbc.fill = 1;
      p.add(sp, gbc);
      ControlPanel cpanel = new ControlPanel(this.screenwidth, this.screenheight);
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.weightx = 0.0;
      gbc.weighty = 0.0;
      gbc.gridheight = 1;
      gbc.gridwidth = 7;
      p.add(cpanel, gbc);
      InfoPanel ipanel = new InfoPanel(this.screenwidth, this.screenheight);
      pl.setControlPane(cpanel);
      pl.setInfoPane(ipanel);
      cpanel.setDrawingPane(pl);
      ipanel.setDrawingPane(pl);
      ipanel.setControlPane(cpanel);
      pl.initDialogs(this);
      Configuration config = new Configuration();
      pl.setConfiguration(config);
      cpanel.setConfiguration(config);
      ipanel.setConfiguration(config);
      config.setDrawingPane(pl);
      config.setControlPane(cpanel);
      config.setInfoPane(ipanel);
      this.setInitialConfiguration(cpanel, ipanel, config);
      Counter counter = new Counter();
      pl.setCounter(counter);
      cpanel.setCounter(counter);
      ipanel.setCounter(counter);
      counter.setDrawingPane(pl);
      counter.setControlPane(cpanel);
      counter.setInfoPane(ipanel);
   }

   public void setInitialConfiguration(ControlPanel cpanel, InfoPanel ipanel, Configuration config) {
      cpanel.localparts.setSelected(true);
      config.putClientProperty("How Parts Work", "Local parts");
      cpanel.localpieces.setSelected(true);
      config.putClientProperty("How Pieces Work", "Local pieces");
   }
}
