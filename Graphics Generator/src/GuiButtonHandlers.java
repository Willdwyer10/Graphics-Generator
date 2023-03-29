import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

public class GuiButtonHandlers {
  
  /**
   * This method represents the events that happen when the "Unlock drive" button is pressed
   */
  public static void driveUnlock() {
    Desktop desktop = null;    
    try {
      if (Desktop.isDesktopSupported()) {
        desktop = Desktop.getDesktop();
      }
      desktop.open(new File("C:\\Users\\wbdwy\\Desktop\\Unlock.lnk"));
    } catch (IOException e) {      
      JOptionPane.showMessageDialog(null, "There was an error running WD Drive Unlock", "Unlock Drive Error", 2);
    }
  }
  
  /**
   * This method represents the events that happen when the "run ignite" button is pressed
   */
  public static void runIgnite() {
    Desktop desktop = null;    
    try {
      if (Desktop.isDesktopSupported()) {
        desktop = Desktop.getDesktop();
      }
      desktop.open(new File("C:\\Program Files\\Ignite12\\Ignite_x64.exe"));
    } catch (IOException e) {      
      JOptionPane.showMessageDialog(null, "There was an error opening Ignite", "Open Ignite Error", 2);
    } catch (RuntimeException e) {
      JOptionPane.showMessageDialog(null, "There was an error opening Ignite", "Open Ignite Error", 2);

    }
  }
  
  public static void txtFileChooser() {
    TxtSelector selector = new TxtSelector();
  }
}
