import java.util.Properties;

class Configuration {
   private Properties config;
   private PlayGround drawpane;
   private ControlPanel controlpane;
   private InfoPanel infopane;
   private String[] list_of_objects = new String[40];
   public static final String YES = "Yes";
   public static final String NO = "No";
   public static final String HOW_PARTS_WORK = "How Parts Work";
   public static final String HOW_PIECES_WORK = "How Pieces Work";
   public static final String LOCALPARTS = "Local parts";
   public static final String GLOBALPARTS = "Global parts";
   public static final String LOCALPIECES = "Local pieces";
   public static final String GLOBALPIECES = "Global pieces";
   public static final String BARS = "Bars";
   public static final String COVERS = "Covers";
   public static final String MATS = "Mats";
   public static final String ERASE = "Delete";
   public static final String COPY = "Copy";
   public static final String JOIN = "Join";
   public static final String CUT = "Cut";
   public static final String FILL = "Fill";
   public static final String REPEAT = "Repeat";
   public static final String LABEL = "Label";
   public static final String GROUP = "Group";
   public static final String UNGROUP = "Ungroup";
   public static final String VSHADE = "Vshade";
   public static final String HSHADE = "Hshade";
   public static final String PARTS = "Parts";
   public static final String VPARTS = "Vparts";
   public static final String HPARTS = "Hparts";
   public static final String BREAK_PARTS = "Break Parts";
   public static final String ERASE_PARTS = "Erase Parts";
   public static final String JOIN_PARTS = "Join Parts";
   public static final String FILL_PARTS = "Fill Parts";
   public static final String PULLOUT_PARTS = "Pullout Parts";
   public static final String NPARTS = "Nparts";
   public static final String PIECES = "Pieces";
   public static final String VPIECES = "Vpieces";
   public static final String HPIECES = "Hpieces";
   public static final String BREAK_PIECES = "Break Pieces";
   public static final String ERASE_PIECES = "Erase Pieces";
   public static final String JOIN_PIECES = "Join Pieces";
   public static final String FILL_PIECES = "Fill Pieces";
   public static final String PULLOUT_PIECES = "Pullout Pieces";
   public static final String NPIECES = "Npieces";
   public static final String SETUNIT = "Set Unit";
   public static final String MEASURE = "Measure";
   public static final String NBARS = "Nbars";
   public static final String UNDO = "Undo";
   public static final String PRINT = "Print";
   public static final String LOAD = "Load";
   public static final String SAVE = "Save";
   public static final String NEW = "New";

   Configuration() {
      this.config = new Properties();
      this.config.put("Bars", "Yes");
      this.config.put("Covers", "Yes");
      this.config.put("Mats", "Yes");
      this.config.put("Delete", "Yes");
      this.config.put("Copy", "Yes");
      this.config.put("Join", "Yes");
      this.config.put("Cut", "Yes");
      this.config.put("Fill", "Yes");
      this.config.put("Repeat", "Yes");
      this.config.put("Label", "Yes");
      this.config.put("Group", "Yes");
      this.config.put("Ungroup", "Yes");
      this.config.put("Vshade", "Yes");
      this.config.put("Hshade", "Yes");
      this.config.put("Parts", "Yes");
      this.config.put("Vparts", "Yes");
      this.config.put("Hparts", "Yes");
      this.config.put("Break Parts", "Yes");
      this.config.put("Erase Parts", "Yes");
      this.config.put("Join Parts", "Yes");
      this.config.put("Fill Parts", "Yes");
      this.config.put("Pullout Parts", "Yes");
      this.config.put("Nparts", "Yes");
      this.config.put("Pieces", "Yes");
      this.config.put("Vpieces", "Yes");
      this.config.put("Hpieces", "Yes");
      this.config.put("Break Pieces", "Yes");
      this.config.put("Erase Pieces", "Yes");
      this.config.put("Join Pieces", "Yes");
      this.config.put("Fill Pieces", "Yes");
      this.config.put("Pullout Pieces", "Yes");
      this.config.put("Npieces", "Yes");
      this.config.put("Set Unit", "Yes");
      this.config.put("Measure", "Yes");
      this.config.put("Nbars", "Yes");
      this.config.put("Undo", "Yes");
      this.config.put("Print", "Yes");
      this.config.put("Load", "Yes");
      this.config.put("Save", "Yes");
      this.config.put("New", "Yes");
      this.list_of_objects[0] = "Bars";
      this.list_of_objects[1] = "Covers";
      this.list_of_objects[2] = "Mats";
      this.list_of_objects[3] = "Delete";
      this.list_of_objects[4] = "Copy";
      this.list_of_objects[5] = "Join";
      this.list_of_objects[6] = "Cut";
      this.list_of_objects[7] = "Fill";
      this.list_of_objects[8] = "Repeat";
      this.list_of_objects[9] = "Label";
      this.list_of_objects[10] = "Group";
      this.list_of_objects[11] = "Ungroup";
      this.list_of_objects[12] = "Vshade";
      this.list_of_objects[13] = "Hshade";
      this.list_of_objects[14] = "Parts";
      this.list_of_objects[15] = "Vparts";
      this.list_of_objects[16] = "Hparts";
      this.list_of_objects[17] = "Break Parts";
      this.list_of_objects[18] = "Erase Parts";
      this.list_of_objects[19] = "Join Parts";
      this.list_of_objects[20] = "Fill Parts";
      this.list_of_objects[21] = "Pullout Parts";
      this.list_of_objects[22] = "Nparts";
      this.list_of_objects[23] = "Pieces";
      this.list_of_objects[24] = "Vpieces";
      this.list_of_objects[25] = "Hpieces";
      this.list_of_objects[26] = "Break Pieces";
      this.list_of_objects[27] = "Erase Pieces";
      this.list_of_objects[28] = "Join Pieces";
      this.list_of_objects[29] = "Fill Pieces";
      this.list_of_objects[30] = "Pullout Pieces";
      this.list_of_objects[31] = "Npieces";
      this.list_of_objects[32] = "Set Unit";
      this.list_of_objects[33] = "Measure";
      this.list_of_objects[34] = "Nbars";
      this.list_of_objects[35] = "Undo";
      this.list_of_objects[36] = "Print";
      this.list_of_objects[37] = "Load";
      this.list_of_objects[38] = "Save";
      this.list_of_objects[39] = "New";
      this.config.put("How Parts Work", "Local parts");
      this.config.put("How Pieces Work", "Local pieces");
   }

   public void activateAction(String name) {
      this.config.put(name, "Yes");
      if (name == "Parts") {
         this.config.put("Vparts", "Yes");
         this.config.put("Hparts", "Yes");
         this.config.put("Break Parts", "Yes");
         this.config.put("Erase Parts", "Yes");
         this.config.put("Join Parts", "Yes");
         this.config.put("Fill Parts", "Yes");
         this.config.put("Pullout Parts", "Yes");
         this.config.put("Nparts", "Yes");
      }

      if (name == "Pieces") {
         this.config.put("Vpieces", "Yes");
         this.config.put("Hpieces", "Yes");
         this.config.put("Break Pieces", "Yes");
         this.config.put("Erase Pieces", "Yes");
         this.config.put("Join Pieces", "Yes");
         this.config.put("Fill Pieces", "Yes");
         this.config.put("Pullout Pieces", "Yes");
         this.config.put("Npieces", "Yes");
      }
   }

   public void deactivateAction(String name) {
      this.config.put(name, "No");
      if (name == "Parts") {
         this.config.put("Vparts", "No");
         this.config.put("Hparts", "No");
         this.config.put("Break Parts", "No");
         this.config.put("Erase Parts", "No");
         this.config.put("Join Parts", "No");
         this.config.put("Fill Parts", "No");
         this.config.put("Pullout Parts", "No");
         this.config.put("Nparts", "No");
      }

      if (name == "Pieces") {
         this.config.put("Vpieces", "No");
         this.config.put("Hpieces", "No");
         this.config.put("Break Pieces", "No");
         this.config.put("Erase Pieces", "No");
         this.config.put("Join Pieces", "No");
         this.config.put("Fill Pieces", "No");
         this.config.put("Pullout Pieces", "No");
         this.config.put("Npieces", "No");
      }
   }

   public void setDrawingPane(PlayGround pl) {
      this.drawpane = pl;
   }

   public PlayGround getDrawingPane() {
      return this.drawpane;
   }

   public void setControlPane(ControlPanel cp) {
      this.controlpane = cp;
   }

   public ControlPanel getControlPane() {
      return this.controlpane;
   }

   public void setInfoPane(InfoPanel ip) {
      this.infopane = ip;
   }

   public InfoPanel getInfoPane() {
      return this.infopane;
   }

   public void putClientProperty(String propname, String value) {
      this.config.put(propname, value);
   }

   public String getClientProperty(String propname) {
      return this.config.getProperty(propname);
   }

   public void show() {
   }
}
