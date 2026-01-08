import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class ControlPanel extends JPanel implements ActionListener, ListSelectionListener {
  protected JToggleButton barbutton;
  protected JToggleButton coverbutton;
  protected JToggleButton matbutton;
  protected JToggleButton ungroupbutton;
  protected JToggleButton erasebutton;
  protected JToggleButton copybutton;
  protected JToggleButton joinbutton;
  protected JToggleButton fillbutton;
  protected JToggleButton repeatbutton;
  protected JToggleButton pulloutpartsbutton;
  protected JToggleButton pulloutpiecesbutton;
  protected JToggleButton vpartsbutton;
  protected JToggleButton hpartsbutton;
  protected JToggleButton vpiecesbutton;
  protected JToggleButton hpiecesbutton;
  protected JToggleButton labelbutton;
  protected JToggleButton vshadebutton;
  protected JToggleButton hshadebutton;
  protected JToggleButton cutbutton;
  protected JToggleButton setunitbutton;
  protected JToggleButton measurebutton;
  protected JToggleButton breakpartsbutton;
  protected JToggleButton breakpiecesbutton;
  protected JToggleButton erasepartsbutton;
  protected JToggleButton erasepiecesbutton;
  protected JToggleButton joinpartsbutton;
  protected JToggleButton joinpiecesbutton;
  protected JToggleButton groupbutton;
  protected JToggleButton fillpartsbutton;
  protected JToggleButton fillpiecesbutton;
  protected JToggleButton nbarsbutton;
  protected JToggleButton npartsbutton;
  protected JToggleButton npiecesbutton;
  private JToggleButton lastpressed;
  private JToggleButton nullbutton;
  protected JList partscounter;
  protected Counter counter;
  protected JButton newbutton;
  protected JButton loadbutton;
  protected JButton savebutton;
  protected JButton undobutton;
  protected JButton printbutton;
  protected JButton configbutton;
  protected JRadioButton localparts;
  protected JRadioButton globalparts;
  protected JRadioButton localpieces;
  protected JRadioButton globalpieces;
  protected ButtonGroup actiongroup;
  protected ButtonGroup partslocalorglobal;
  protected ButtonGroup pieceslocalorglobal;
  protected ButtonGroup shadepartsorpieces;
  private PlayGround playground;
  private Font buttonfont;
  protected Configuration configure;
  protected JTextField nbars;
  protected JTextField nparts;
  protected JTextField npieces;
  protected JLabel nbarslabel;
  protected JLabel npartslabel;
  protected JLabel npieceslabel;
  public static final String MAKE_BAR = "Make Bar";
  public static final String MAKE_COVER = "Make Cover";
  public static final String MAKE_MAT = "Make Mat";
  public static final String ERASE = "Erase";
  public static final String COPY = "Copy";
  public static final String REPEAT = "Repeat";
  public static final String UNGROUP = "Ungroup";
  public static final String GROUP = "Group";
  public static final String CUT = "Cut";
  public static final String JOIN = "Join";
  public static final String LABEL = "Label";
  public static final String FILL = "Fill";
  public static final String PULLOUT_PARTS = "Pullout Parts";
  public static final String PULLOUT_PIECES = "Pullout Pieces";
  public static final String BREAK_PARTS = "Break Parts";
  public static final String BREAK_PIECES = "Break Pieces";
  public static final String ERASE_PARTS = "Erase Parts";
  public static final String ERASE_PIECES = "Erase Pieces";
  public static final String JOIN_PARTS = "Join Parts";
  public static final String JOIN_PIECES = "Join Pieces";
  public static final String FILL_PARTS = "Fill Parts";
  public static final String FILL_PIECES = "Fill Pieces";
  public static final String VSHADE = "Vertical shade";
  public static final String HSHADE = "Horizontal shade";
  public static final String VPARTS = "Vertical parts";
  public static final String HPARTS = "Horizontal parts";
  public static final String VPIECES = "Vertical pieces";
  public static final String HPIECES = "Horizontal pieces";
  public static final String SET_UNIT = "Set Unit";
  public static final String MEASURE = "Measure";
  public static final String NEW = "New";
  public static final String CONFIGURE = "Configure";
  public static final String SAVE = "Save";
  public static final String LOAD = "Load";
  public static final String UNDO = "Undo";
  public static final String PRINT = "Print";
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
  public static final String NUMBER_OF_PARTS = "Number of Parts";
  public static final String UNIT_BAR = "Unit Bar";

  ControlPanel(int screenwidth, int screenheight) {
    super(true);
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        ControlPanel.this.mouseClicked(evt);
      }
    });
    this.actiongroup = new ButtonGroup();
    this.nullbutton = new JToggleButton();
    this.nullbutton.setVisible(false);
    this.actiongroup.add(this.nullbutton);
    JPanel labelpanel = new JPanel();
    JPanel make_objects = new JPanel();
    make_objects.setBackground(new Color(100, 100, 200));
    JPanel actions = new JPanel();
    actions.setBackground(new Color(200, 100, 100));
    new BorderPanel("Pull Out");
    BorderPanel parts = new BorderPanel("Parts");
    BorderPanel pieces = new BorderPanel("Pieces");
    BorderPanel shade = new BorderPanel("Shade");
    shade.setLayout(new GridLayout(2, 1));
    JPanel measure = new JPanel();
    measure.setBackground(Color.blue);
    JPanel misc = new JPanel();
    misc.setLayout(new BoxLayout(misc, 0));
    misc.setBackground(Color.white);
    this.buttonfont = new Font("SansSerif", 1, screenheight <= 480 ? 8 : 10);
    JLabel barslabel = new JLabel("TIMA: Bars");
    barslabel.setFont(new Font("SansSerif", 3, 15));
    barslabel.setForeground(Color.red);
    labelpanel.add(barslabel);
    this.barbutton = this.initButton("Bar", "Make Bar", this.buttonfont);
    this.coverbutton = this.initButton("Cover", "Make Cover", this.buttonfont);
    this.matbutton = this.initButton("Mat", "Make Mat", this.buttonfont);
    make_objects.add(this.barbutton);
    make_objects.add(this.coverbutton);
    make_objects.add(this.matbutton);
    this.actiongroup.add(this.barbutton);
    this.actiongroup.add(this.coverbutton);
    this.actiongroup.add(this.matbutton);
    this.erasebutton = this.initButton("Erase", "Erase", this.buttonfont);
    this.copybutton = this.initButton("Copy", "Copy", this.buttonfont);
    this.joinbutton = this.initButton("Join", "Join", this.buttonfont);
    this.cutbutton = this.initButton("Cut", "Cut", this.buttonfont);
    this.fillbutton = this.initButton("Fill", "Fill", this.buttonfont);
    this.repeatbutton = this.initButton("Repeat", "Repeat", this.buttonfont);
    this.labelbutton = this.initButton("Label", "Label", this.buttonfont);
    this.groupbutton = this.initButton("Group", "Group", this.buttonfont);
    this.ungroupbutton = this.initButton("Ungroup", "Ungroup", this.buttonfont);
    actions.add(this.erasebutton);
    actions.add(this.copybutton);
    actions.add(this.joinbutton);
    actions.add(this.cutbutton);
    actions.add(this.fillbutton);
    actions.add(this.repeatbutton);
    actions.add(this.labelbutton);
    actions.add(this.groupbutton);
    actions.add(this.ungroupbutton);
    this.actiongroup.add(this.erasebutton);
    this.actiongroup.add(this.copybutton);
    this.actiongroup.add(this.joinbutton);
    this.actiongroup.add(this.groupbutton);
    this.actiongroup.add(this.ungroupbutton);
    this.actiongroup.add(this.cutbutton);
    this.actiongroup.add(this.fillbutton);
    this.actiongroup.add(this.repeatbutton);
    this.actiongroup.add(this.labelbutton);
    this.vshadebutton = this.initButton("Up/Down", "Vertical shade", this.buttonfont);
    this.hshadebutton = this.initButton("Left/Right", "Horizontal shade", this.buttonfont);
    shade.add(this.vshadebutton);
    shade.add(this.hshadebutton);
    this.actiongroup.add(this.vshadebutton);
    this.actiongroup.add(this.hshadebutton);
    JPanel p3 = new JPanel();
    p3.setLayout(new GridLayout(2, 1));
    this.vpartsbutton = this.initButton("Up/Down", "Vertical parts", this.buttonfont);
    this.vpartsbutton.setMargin(new Insets(1, 1, 1, 1));
    this.hpartsbutton = this.initButton("Left/Right", "Horizontal parts", this.buttonfont);
    this.hpartsbutton.setMargin(new Insets(1, 1, 1, 1));
    p3.add(this.vpartsbutton);
    p3.add(this.hpartsbutton);
    this.actiongroup.add(this.vpartsbutton);
    this.actiongroup.add(this.hpartsbutton);
    Vector<Integer> v = new Vector<Integer>();

    for (int i = 99; i >= 1; i--) {
      v.addElement(new Integer(i));
    }

    this.partscounter = new JList<Integer>(v);
    this.partscounter.addListSelectionListener(this);
    JScrollPane countpane = new JScrollPane(this.partscounter);
    this.partscounter.setPrototypeCellValue(new Integer(99));
    this.partscounter.setSelectedValue(new Integer(1), true);
    this.putClientProperty("Number of Parts", new Integer(1));
    this.partscounter.setVisibleRowCount(3);
    this.partslocalorglobal = new ButtonGroup();
    JPanel p4 = new JPanel();
    p4.setLayout(new GridLayout(2, 1));
    this.localparts = new JRadioButton("Parts");
    this.localparts.setActionCommand("Local parts");
    this.localparts.setFont(this.buttonfont);
    this.localparts.setMargin(new Insets(1, 1, 1, 1));
    this.localparts.addActionListener(this);
    this.globalparts = new JRadioButton("Bar");
    this.globalparts.setFont(this.buttonfont);
    this.globalparts.setMargin(new Insets(1, 1, 1, 1));
    this.globalparts.setActionCommand("Global parts");
    this.globalparts.addActionListener(this);
    p4.add(this.localparts);
    p4.add(this.globalparts);
    this.partslocalorglobal.add(this.localparts);
    this.partslocalorglobal.add(this.globalparts);
    JPanel p5 = new JPanel();
    p5.setLayout(new GridLayout(2, 3));
    this.breakpartsbutton = this.initButton("Break", "Break Parts", this.buttonfont);
    this.breakpartsbutton.setMargin(new Insets(1, 1, 1, 1));
    this.erasepartsbutton = this.initButton("Clear", "Erase Parts", this.buttonfont);
    this.erasepartsbutton.setMargin(new Insets(1, 1, 1, 1));
    this.joinpartsbutton = this.initButton("Combine", "Join Parts", this.buttonfont);
    this.joinpartsbutton.setMargin(new Insets(1, 1, 1, 1));
    this.fillpartsbutton = this.initButton("Fill", "Fill Parts", this.buttonfont);
    this.fillpartsbutton.setMargin(new Insets(1, 1, 1, 1));
    this.pulloutpartsbutton = this.initButton("Pullout", "Pullout Parts", this.buttonfont);
    this.pulloutpartsbutton.setMargin(new Insets(1, 1, 1, 1));
    this.npartsbutton = this.initButton("Count", "Number of Parts", this.buttonfont);
    this.npartsbutton.setMargin(new Insets(1, 1, 1, 1));
    this.actiongroup.add(this.breakpartsbutton);
    this.actiongroup.add(this.erasepartsbutton);
    this.actiongroup.add(this.joinpartsbutton);
    this.actiongroup.add(this.fillpartsbutton);
    this.actiongroup.add(this.pulloutpartsbutton);
    this.actiongroup.add(this.npartsbutton);
    p5.add(this.breakpartsbutton);
    p5.add(this.erasepartsbutton);
    p5.add(this.joinpartsbutton);
    p5.add(this.fillpartsbutton);
    p5.add(this.pulloutpartsbutton);
    parts.add(p3);
    parts.add(countpane);
    parts.add(p4);
    parts.add(p5);
    JPanel p6 = new JPanel();
    p6.setLayout(new GridLayout(2, 1));
    this.vpiecesbutton = this.initButton("Up/Down", "Vertical pieces", this.buttonfont);
    this.hpiecesbutton = this.initButton("Left/Right", "Horizontal pieces", this.buttonfont);
    p6.add(this.vpiecesbutton);
    p6.add(this.hpiecesbutton);
    this.actiongroup.add(this.vpiecesbutton);
    this.actiongroup.add(this.hpiecesbutton);
    this.pieceslocalorglobal = new ButtonGroup();
    JPanel p7 = new JPanel();
    p7.setLayout(new GridLayout(2, 1));
    this.localpieces = new JRadioButton("Pieces");
    this.localpieces.setFont(this.buttonfont);
    this.localpieces.setMargin(new Insets(2, 2, 2, 2));
    this.localpieces.setActionCommand("Local pieces");
    this.localpieces.addActionListener(this);
    this.globalpieces = new JRadioButton("Bar");
    this.globalpieces.setFont(this.buttonfont);
    this.globalpieces.setMargin(new Insets(2, 2, 2, 2));
    this.globalpieces.setActionCommand("Global pieces");
    this.globalpieces.addActionListener(this);
    p7.add(this.localpieces);
    p7.add(this.globalpieces);
    this.pieceslocalorglobal.add(this.localpieces);
    this.pieceslocalorglobal.add(this.globalpieces);
    JPanel p8 = new JPanel();
    p8.setLayout(new GridLayout(2, 2));
    this.breakpiecesbutton = this.initButton("Break", "Break Pieces", this.buttonfont);
    this.breakpiecesbutton.setMargin(new Insets(2, 2, 2, 2));
    this.erasepiecesbutton = this.initButton("Clear", "Erase Pieces", this.buttonfont);
    this.erasepiecesbutton.setMargin(new Insets(2, 2, 2, 2));
    this.joinpiecesbutton = this.initButton("Combine", "Join Pieces", this.buttonfont);
    this.joinpiecesbutton.setMargin(new Insets(2, 2, 2, 2));
    this.fillpiecesbutton = this.initButton("Fill", "Fill Pieces", this.buttonfont);
    this.fillpiecesbutton.setMargin(new Insets(2, 2, 2, 2));
    this.pulloutpiecesbutton = this.initButton("Pullout", "Pullout Pieces", this.buttonfont);
    this.pulloutpiecesbutton.setMargin(new Insets(2, 2, 2, 2));
    this.npiecesbutton = this.initButton("Count", "Number of Pieces", this.buttonfont);
    this.npiecesbutton.setMargin(new Insets(2, 2, 2, 2));
    this.actiongroup.add(this.breakpiecesbutton);
    this.actiongroup.add(this.erasepiecesbutton);
    this.actiongroup.add(this.joinpiecesbutton);
    this.actiongroup.add(this.fillpiecesbutton);
    this.actiongroup.add(this.pulloutpiecesbutton);
    this.actiongroup.add(this.npiecesbutton);
    p8.add(this.breakpiecesbutton);
    p8.add(this.erasepiecesbutton);
    p8.add(this.joinpiecesbutton);
    p8.add(this.fillpiecesbutton);
    p8.add(this.pulloutpiecesbutton);
    pieces.add(p6);
    pieces.add(p7);
    pieces.add(p8);
    this.setunitbutton = this.initButton("Set Unit Bar", "Set Unit", this.buttonfont);
    this.measurebutton = this.initButton("Measure", "Measure", this.buttonfont);
    this.nbarsbutton = this.initButton("Count Bars", "Number of Bars", this.buttonfont);
    measure.add(this.setunitbutton);
    measure.add(this.measurebutton);
    this.actiongroup.add(this.setunitbutton);
    this.actiongroup.add(this.measurebutton);
    this.actiongroup.add(this.nbarsbutton);
    this.undobutton = new JButton("Undo");
    this.undobutton.setActionCommand("Undo");
    this.undobutton.setFont(this.buttonfont);
    this.printbutton = new JButton("Print");
    this.printbutton.setActionCommand("Print");
    this.printbutton.setFont(this.buttonfont);
    this.loadbutton = new JButton("Load");
    this.loadbutton.setActionCommand("Load");
    this.loadbutton.setFont(this.buttonfont);
    this.savebutton = new JButton("Save");
    this.savebutton.setActionCommand("Save");
    this.savebutton.setFont(this.buttonfont);
    this.configbutton = new JButton("Configure");
    this.configbutton.setFont(this.buttonfont);
    this.configbutton.addActionListener(this);
    this.configbutton.setActionCommand("Configure");
    this.undobutton.setEnabled(false);
    this.printbutton.setEnabled(false);
    this.newbutton = new JButton("New");
    this.newbutton.setActionCommand("New");
    this.newbutton.setFont(this.buttonfont);
    this.newbutton.addActionListener(this);
    misc.add(this.newbutton);
    misc.add(this.savebutton);
    misc.add(this.loadbutton);
    misc.add(this.undobutton);
    misc.add(this.printbutton);
    misc.add(this.configbutton);
    this.setLayout(new BoxLayout(this, 1));
    JPanel object_count = new JPanel();
    this.nbarslabel = new JLabel("Number of Bars");
    this.nbarslabel.setForeground(Color.red);
    this.nbarslabel.setFont(this.buttonfont);
    this.npartslabel = new JLabel("Number of Parts");
    this.npartslabel.setForeground(Color.red);
    this.npartslabel.setFont(this.buttonfont);
    this.npieceslabel = new JLabel("Number of Pieces");
    this.npieceslabel.setForeground(Color.red);
    this.npieceslabel.setFont(this.buttonfont);
    this.nbars = new JTextField("0", 4);
    this.nparts = new JTextField("0", 4);
    this.npieces = new JTextField("0", 4);
    this.nbars.setEditable(false);
    this.nparts.setEditable(false);
    this.npieces.setEditable(false);
    object_count.add(this.nbarslabel);
    object_count.add(this.nbars);
    object_count.add(this.npartslabel);
    object_count.add(this.nparts);
    object_count.add(this.npieceslabel);
    object_count.add(this.npieces);
    make_objects.setLayout(new BoxLayout(make_objects, 0));
    actions.setLayout(new BoxLayout(actions, 0));
    measure.setLayout(new BoxLayout(measure, 0));
    JPanel first_row = new JPanel();
    first_row.setLayout(new BoxLayout(first_row, 0));
    first_row.add(make_objects);
    first_row.add(Box.createHorizontalStrut(7));
    first_row.add(actions);
    first_row.add(Box.createHorizontalStrut(7));
    first_row.add(measure);
    first_row.add(Box.createHorizontalGlue());
    JPanel second_row = new JPanel();
    second_row.setLayout(new GridLayout(1, 2));
    second_row.add(parts);
    second_row.add(pieces);
    JPanel third_row = new JPanel();
    third_row.setLayout(new BoxLayout(third_row, 0));
    third_row.add(misc);
    third_row.add(Box.createHorizontalGlue());
    JPanel fourth_row = new JPanel();
    fourth_row.setLayout(new BoxLayout(fourth_row, 0));
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

  public JToggleButton initButton(String title, String actioncommand, Font buttonfont) {
    JToggleButton jtb = new JToggleButton(title);
    jtb.setActionCommand(actioncommand);
    jtb.setFont(buttonfont);
    jtb.setMargin(new Insets(2, 2, 2, 2));
    return jtb;
  }

  public void setDrawingPane(PlayGround pl) {
    this.playground = pl;
    this.setActionListener(this.actiongroup, this.playground);
    this.savebutton.addActionListener(this.playground);
    this.loadbutton.addActionListener(this.playground);
    this.undobutton.addActionListener(this.playground);
    this.printbutton.addActionListener(this.playground);
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

  public void setActionListener(ButtonGroup bg, ActionListener l) {
    Enumeration e = bg.getElements();

    while (e.hasMoreElements()) {
      Object b = e.nextElement();
      if (b instanceof JButton) {
        ((JButton) b).addActionListener(l);
      } else if (b instanceof JToggleButton) {
        ((JToggleButton) b).addActionListener(l);
      }
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent evt) {
    Object o = evt.getSource();
    this.putClientProperty("Number of Parts", ((JList) o).getSelectedValue());
  }

  public ButtonGroup getControlPaneButtons() {
    return this.actiongroup;
  }

  public void mouseClicked(MouseEvent evt) {
    Object src = evt.getSource();
    if (src instanceof JToggleButton) {
      if (this.lastpressed == null) {
        this.lastpressed = (JToggleButton) src;
      } else if (this.lastpressed == src) {
        this.selectNoButtons();
      } else {
        this.lastpressed = (JToggleButton) src;
      }
    }
  }

  public void selectNoButtons() {
    this.nullbutton.setSelected(true);
  }

  @Override
  public void actionPerformed(ActionEvent evt) {
    String command = evt.getActionCommand();
    if (command == "Local parts") {
      this.getConfiguration().putClientProperty("How Parts Work", "Local parts");
    } else if (command == "Global parts") {
      this.getConfiguration().putClientProperty("How Parts Work", "Global parts");
    } else if (command == "Local pieces") {
      this.getConfiguration().putClientProperty("How Pieces Work", "Local pieces");
    } else if (command == "Global pieces") {
      this.getConfiguration().putClientProperty("How Pieces Work", "Global pieces");
    } else if (command == "Configure") {
      this.getConfiguration().show();
    } else if (command == "New") {
      this.getDrawingPane().newdialog.show();
      int n = this.getDrawingPane().newdialog.getOption();
      switch (n) {
        case -999:
        case -1:
        case 0:
        default:
          break;
        case 1:
          this.getDrawingPane().barlist.removeAllElements();
          this.getDrawingPane().coverlist.removeAllElements();
          this.getDrawingPane().matlist.removeAllElements();
          this.getDrawingPane().putClientProperty("Unit Bar", null);
          this.selectNoButtons();
          this.getDrawingPane().repaint();
      }
    }
  }
}
