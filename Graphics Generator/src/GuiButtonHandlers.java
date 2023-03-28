import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

public class GuiButtonHandlers {
  
  /**
   * This method holds the stuff that happens when the "Unlock drive" button is pressed
   */
  public static void driveUnlock() {
    Desktop desktop = null;
    System.out.println("called");
    // TODO: remove this, as it is not used
    //PopupErrorMessage popup = new PopupErrorMessage();
    //popup.setVisible(true);
    try {
      if (Desktop.isDesktopSupported()) {
        desktop = Desktop.getDesktop();
      }
      desktop.open(new File("C:\\Users\\wbdwy\\Desktop\\Unlock.lnk"));
    } catch (IOException e) {      
      JOptionPane.showMessageDialog(null, "There was an error running WD Drive Unlock");
    }
  }
}
