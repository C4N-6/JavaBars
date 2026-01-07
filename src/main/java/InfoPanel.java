import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

class InfoPanel extends JPanel {
   private PlayGround playground;
   private ControlPanel controlpane;
   protected ButtonGroup infogroup;
   protected JTextField nbars;
   protected JTextField nparts;
   protected JTextField npieces;
   protected JToggleButton nbarsbutton;
   protected JToggleButton npartsbutton;
   protected JToggleButton npiecesbutton;
   protected JLabel nbarslabel;
   protected JLabel npartslabel;
   protected JLabel npieceslabel;
   private Font buttonfont;
   protected Configuration configure;
   protected Counter counter;
   public static final String BARS_IN_PLAYGROUND = "Bars in Playground";
   public static final String BARS_ON_MAT = "Bars on Mat";
   public static final String BARS_UNDER_COVER = "Bars under Cover";
   public static final String BARS_IN_GROUP = "Bars in Group";
   public static final String PARTS_IN_BAR = "Parts in Bar";
   public static final String PARTS_IN_PLAYGROUND = "Parts in Playground";
   public static final String PARTS_ON_MAT = "Parts on Mat";
   public static final String PARTS_UNDER_COVER = "Parts under Cover";
   public static final String PARTS_IN_GROUP = "Parts in Group";
   public static final String PIECES_IN_BAR = "Pieces in Bar";
   public static final String PIECES_IN_PLAYGROUND = "Pieces in Playground";
   public static final String PIECES_ON_MAT = "Pieces on Mat";
   public static final String PIECES_UNDER_COVER = "Pieces under Cover";
   public static final String PIECES_IN_GROUP = "Pieces in Group";
   public static final String NBARS = "Number of Bars";
   public static final String NPARTS = "Number of Parts";
   public static final String NPIECES = "Number of Pieces";

   InfoPanel(int screenwidth, int screenheight) {
      super(true);
      this.buttonfont = new Font("SansSerif", 1, screenheight <= 480 ? 8 : 10);
      this.setLayout(new BoxLayout(this, 1));
      JPanel p1 = new JPanel();
      p1.setLayout(new GridLayout(1, 3));
      JPanel p2 = new JPanel();
      p2.setLayout(new GridLayout(1, 3));
      JPanel p3 = new JPanel();
      p3.setLayout(new GridLayout(1, 3));
      JPanel p = new JPanel();
      p.setLayout(new GridLayout(3, 1));
      this.nbars = new JTextField("0", 4);
      this.nparts = new JTextField("0", 4);
      this.npieces = new JTextField("0", 4);
      this.nbars.setEditable(false);
      this.nparts.setEditable(false);
      this.npieces.setEditable(false);
      this.nbarsbutton = this.initButton("Bars", "Number of Bars", this.buttonfont);
      this.npartsbutton = this.initButton("Parts", "Number of Parts", this.buttonfont);
      this.npiecesbutton = this.initButton("Pieces", "Number of Pieces", this.buttonfont);
      this.nbarslabel = new JLabel("Number of Bars");
      this.nbarslabel.setForeground(Color.red);
      this.nbarslabel.setFont(this.buttonfont);
      this.npartslabel = new JLabel("Number of Parts");
      this.npartslabel.setForeground(Color.red);
      this.npartslabel.setFont(this.buttonfont);
      this.npieceslabel = new JLabel("Number of Pieces");
      this.npieceslabel.setForeground(Color.red);
      this.npieceslabel.setFont(this.buttonfont);
      p1.setLayout(new GridLayout(1, 2));
      p1.add(this.nbarsbutton);
      p1.add(this.nbars);
      p2.setLayout(new GridLayout(1, 2));
      p2.add(this.npartsbutton);
      p2.add(this.nparts);
      p3.setLayout(new GridLayout(1, 2));
      p3.add(this.npiecesbutton);
      p3.add(this.npieces);
      JPanel first_row = new JPanel();
      first_row.add(this.nbarslabel);
      first_row.add(p1);
      JPanel second_row = new JPanel();
      second_row.add(this.npartslabel);
      second_row.add(p2);
      JPanel third_row = new JPanel();
      third_row.add(this.npieceslabel);
      third_row.add(p3);
      this.setLayout(new BoxLayout(this, 1));
      this.add(first_row);
      this.add(second_row);
      this.add(third_row);
   }

   public void displayBarCount(int n, String title) {
      this.nbarslabel.setText(title);
      this.nbars.setText(Integer.toString(n));
      this.revalidate();
   }

   public void displayPartCount(int n, String title) {
      this.npartslabel.setText(title);
      this.nparts.setText(Integer.toString(n));
      this.revalidate();
   }

   public void displayPieceCount(int n, String title) {
      this.npieceslabel.setText(title);
      this.npieces.setText(Integer.toString(n));
      this.revalidate();
   }

   public JToggleButton initButton(String title, String actioncommand, Font f) {
      JToggleButton jtb = new JToggleButton(title);
      jtb.setActionCommand(actioncommand);
      jtb.setFont(f);
      jtb.setMargin(new Insets(2, 2, 2, 2));
      return jtb;
   }

   public ButtonGroup getInfoPaneButtons() {
      return this.infogroup;
   }

   public void setDrawingPane(PlayGround pl) {
      this.playground = pl;
   }

   public PlayGround getDrawingPane() {
      return this.playground;
   }

   public void setConfiguration(Configuration config) {
      this.configure = config;
   }

   public Configuration getConfiguration() {
      return this.configure;
   }

   public void setCounter(Counter counter) {
      this.counter = counter;
   }

   public Counter getCounter() {
      return this.counter;
   }

   public void setControlPane(ControlPanel controlpane) {
      this.controlpane = controlpane;
      controlpane.getControlPaneButtons().add(this.nbarsbutton);
      controlpane.getControlPaneButtons().add(this.npartsbutton);
      controlpane.getControlPaneButtons().add(this.npiecesbutton);
   }

   public ControlPanel getControlPane() {
      return this.controlpane;
   }
}
