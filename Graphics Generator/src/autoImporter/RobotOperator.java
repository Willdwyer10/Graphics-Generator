package autoImporter;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class RobotOperator {
  
  private final int sleepTimeNormal = 500; // the amount of time to wait before performing another action
  private final int sleepTimeShort = 50; // the amount of time to wait before performing another action

  private final ClickCoordinate clickCreateNewMessage = new ClickCoordinate(120, 175);
  private final ClickCoordinate clickInsert = new ClickCoordinate(120, 30);
  private final ClickCoordinate clickImport = new ClickCoordinate(200, 150);
  
  private Robot robot;
  
  public RobotOperator(int numBands) throws AWTException {
    robot = new Robot();
    
    // JUST FOR TESTING PURPOSES
    //this.getColorAndPixel();

    
    // things that only happen once
    
    // click inside the screen once to make ensure that ignite is the "active" window
    this.clickCreateNewMessage.click(robot);
    this.sleep(this.sleepTimeNormal);
    
    // once ignite is the "active" window, click on "create new message"
    this.clickCreateNewMessage.click(robot);
    this.sleep(this.sleepTimeNormal);
    
    // ensures the "create message" screen is loaded before proceeding
    this.waitToLoad(470,  150, new Color(0, 0, 198));
    
    
    // everything that happens from here on out will be happen multiple times
    for(int i = numBands - 1; i > -1; i--) {
      
      // clicks the "insert" button
      this.clickInsert.click(robot);
      this.sleep(this.sleepTimeShort);
      
      // clicks the "import" button
      this.clickImport.click(robot);
      this.sleep(this.sleepTimeNormal*2);
      
      // checks if the "Failed to load image" message is there or not, and if so it hits enter once
      if(robot.getPixelColor(635, 390).equals(new Color(13, 137, 218))) {
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
      }
      
      // hits "browse" button
      robot.keyPress(KeyEvent.VK_ENTER);
      robot.keyRelease(KeyEvent.VK_ENTER);
      
      // reverse tabs up to the first image in the directory
      robot.keyPress(KeyEvent.VK_SHIFT);
      robot.keyPress(KeyEvent.VK_TAB);
      robot.keyRelease(KeyEvent.VK_TAB);
      robot.keyRelease(KeyEvent.VK_SHIFT);
      
      // hits space to select that first image
      robot.keyPress(KeyEvent.VK_SPACE);
      robot.keyRelease(KeyEvent.VK_SPACE);
      
      // arrow to the wanted image the necessary number of times
      for(int j = 0; j < i; j++) {
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_RIGHT);
      }
      
      // Navigate to the "File name:" box
      robot.keyPress(KeyEvent.VK_TAB);
      robot.keyRelease(KeyEvent.VK_TAB);
      //sleep(this.sleepTimeNormal);
      
      // Right arrow in order to get the very right side of the file name
      robot.keyPress(KeyEvent.VK_RIGHT);
      robot.keyPress(KeyEvent.VK_RIGHT);
      //sleep(this.sleepTimeNormal);
      
      // Move left past the file extension ".jpg"
      for(int j = 0; j < 4; j++) {
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_LEFT);
        //sleep(this.sleepTimeNormal);
      }
      
      // Select the name of the image
      robot.keyPress(KeyEvent.VK_CONTROL);
      robot.keyPress(KeyEvent.VK_SHIFT);
      for(int j = 0; j < 10; j++) {
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_LEFT);
        //sleep(this.sleepTimeNormal);
      }
      
      // copy the name
      robot.keyPress(KeyEvent.VK_C);
      robot.keyRelease(KeyEvent.VK_C);
      
      robot.keyRelease(KeyEvent.VK_CONTROL);
      robot.keyRelease(KeyEvent.VK_SHIFT);
      //sleep(this.sleepTimeNormal);
      
      // opens the image
      robot.keyPress(KeyEvent.VK_ENTER);
      robot.keyRelease(KeyEvent.VK_ENTER);
      //sleep(this.sleepTimeNormal);

      // navigates from "browse" to "import"
      robot.keyPress(KeyEvent.VK_TAB);
      robot.keyRelease(KeyEvent.VK_ENTER);
      //sleep(this.sleepTimeNormal);

      // hits "import"
      robot.keyPress(KeyEvent.VK_ENTER);
      robot.keyRelease(KeyEvent.VK_ENTER);
      //sleep(this.sleepTimeNormal);
      
      // ctrl + s
      robot.keyPress(KeyEvent.VK_CONTROL);
      robot.keyPress(KeyEvent.VK_S);
      robot.keyRelease(KeyEvent.VK_S);
      robot.keyRelease(KeyEvent.VK_CONTROL);
      this.waitToLoad(190,  110, new Color(165, 25, 46));

      // ctrl + v to paste the copied title
      robot.keyPress(KeyEvent.VK_CONTROL);
      robot.keyPress(KeyEvent.VK_V);
      robot.keyRelease(KeyEvent.VK_V);
      robot.keyRelease(KeyEvent.VK_CONTROL);
      //sleep(this.sleepTimeNormal);

      // tabs down to the "save" button
      for(int j = 0; j < 4; j++) {
        robot.keyPress(KeyEvent.VK_TAB);
        //sleep(this.sleepTimeNormal);
      }
      
      // hits save
      robot.keyPress(KeyEvent.VK_ENTER);
      robot.keyRelease(KeyEvent.VK_ENTER);
      sleep(this.sleepTimeNormal);
      
      // ensures the name of this graphic is not already taken
      if(robot.getPixelColor(506, 367).equals(new Color(17, 144, 221))) {
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
      }

      // creates a new image for the next image, but only if this isn't the last image being created
      if(i > 0) {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_N);
        robot.keyPress(KeyEvent.VK_N);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        sleep(this.sleepTimeShort);
      }
    }
    
    JOptionPane.showMessageDialog(null, "All graphics have been imported!", "Import Graphics Completed", -1);
  }
  
  /**
   * Simply pauses the program momentarily
   * 
   * @param ms - how long you would like to delay/wait/sleep for
   */
  private void sleep(long ms) {
    try {Thread.sleep(ms);} catch (Exception ignored) {}
  }
  
  /**
   * 
   * @param x - the x coordinate of pixel we will wait on to be updated until proceeding
   * @param y - the y coordinate of pixel we will wait on to be updated until proceeding
   * @param color - the color the pixel should be once the new screen is loaded
   */
  private void waitToLoad(int x, int y, Color color) {
    while(!robot.getPixelColor(x, y).equals(color)) {
    //  System.out.println("still not ready!!!");
      this.sleep(20);
    }
    //System.out.println("ready!!!");
  }
  
  /**
   * this is a helper method used in this programs development in order to conveniently get the 
   * color at any pixel of interest
   */
  /*private void getColorAndPixel() {
    Color color = null;
    while(true) {
      Point p = MouseInfo.getPointerInfo().getLocation();
      color = robot.getPixelColor(p.x, p.y);
      System.out.println("x:"+p.x+" y:"+p.y+" color:("+color.getRed()+", "+color.getGreen()+", "+color.getBlue()+")");
      this.sleep(50);
    }
  }*/
}
