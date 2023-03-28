/**
 * represents a location, namely information regarding the location's sign's information
 * 
 * @author william
 */
public class Location {
  private int graphicWidth;
  private int graphicHeight;
  
  /**
   * default constructor, sets the width and height to -1, a value that don't make sense in context
   */
  public Location() {
    this(-1, -1);
  }
  
  /**
   * constructor that sets the graphic width and height based on the corresponding parameters
   * 
   * @param graphicWidth - the width of the graphics to be cretaed for this location
   * @param graphicHeight - the height of the graphics to be created for this location
   */
  public Location(int graphicWidth, int graphicHeight) throws IllegalArgumentException {
    if(graphicWidth < -1) throw new IllegalArgumentException("graphicWidth parameter is < -1");
    if(graphicHeight < -1) throw new IllegalArgumentException("graphicHeight parameter is < -1");
    this.graphicWidth = graphicWidth;
    this.graphicHeight = graphicHeight;
  }
  
  /**
   * mutator method for the width of graphics to be created for this location
   * 
   * @param graphicWidth - the new width of the graphic
   */
  public void setGraphicWidth(int graphicWidth) {
    this.graphicWidth = graphicWidth;
  }
  
  /**
   * mutator method for the height of graphics to be created for this location
   * 
   * @param graphicHeight - the new height of the graphic
   */
  public void setGraphicHeight(int graphicHeight) {
    this.graphicHeight = graphicHeight;
  }
  
  /**
   * accessor method for the width of graphics to be created for this location
   * 
   * @return this.graphicWidth, the width of graphics made for this location
   */
  public int getGraphicWidth() {
    return this.graphicWidth;
  }
  
  /**
   * accessor method for the height of graphics to be created for this location
   * 
   * @return this.graphicHeight, the height of graphics made for this location
   */
  public int getGraphicHeight() {
    return this.graphicHeight;
  }
}
