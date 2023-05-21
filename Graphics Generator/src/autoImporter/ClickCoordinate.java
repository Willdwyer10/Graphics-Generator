package autoImporter;

import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 * A simple object to hold a coordinate set
 * 
 * @author william
 *
 */
public class ClickCoordinate {
  private int x;
  private int y;
  
  /**
   * default constructor
   */
  public ClickCoordinate() {
    this(0, 0);
  }
  
  /**
   * normal constructor
   * 
   * @param x - the x coordinate
   * @param y - the y coordinate
   */
  public ClickCoordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  /**
   * Accessor method for the x coordinate
   * 
   * @return x - the x coordinate of this Coordinate
   */
  public int getX() {
    return this.x;
  }
  
  /**
   * Accessor method for the y coordinate
   * 
   * @return y - the y coordinate of this Coordinate
   */
  public int getY() {
    return this.y;
  }
  
  public void click(Robot robot) {
    robot.mouseMove(this.x, this.y);
    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    
  }
  
}