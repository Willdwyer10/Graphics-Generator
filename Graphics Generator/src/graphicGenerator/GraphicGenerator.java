package graphicGenerator;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import customTypes.Band;
import customTypes.Location;

/**
 * This data type holds the instance methods that allow graphics to be generated given a txt file
 * and a Location to create the graphics for
 * 
 * @author wbdwy
 *
 */
public class GraphicGenerator {
  private ArrayList<Band> bands; // the list of bands playing at a specific location
  private int graphicWidth; // the width of the graphics to be made for this location
  private int graphicHeight; // the height of the graphics to be made for this location
  private File bandInfoFile; // the File containing the Band's information
  private File graphicsSaveLocation; // the File that has the directory to export images to
  private boolean doneCreating; // this flag is updated when graphics are ready to be Ignited
  
  /**
   * Constructor that generates the graphics and list of bands and such
   * 
   * @param location - the Location for the graphics to be created for
   * @param bandInfoFile - the txt file containing the information of the Bands playing
   * @throws FileNotFoundException @see readBandInfo, which throws this exception
   */
  public GraphicGenerator(Location location, File bandInfoFile, File graphicsSaveLocation) throws FileNotFoundException {
    if(!bandInfoFile.getName().substring(bandInfoFile.getName().length()-3).equals("txt")) {
      JOptionPane.showMessageDialog(null, "Please ensure the txt file is a valid, existent file.", "Generate Graphics Error", 2);
    }
    else if(!graphicsSaveLocation.isDirectory()) {
      JOptionPane.showMessageDialog(null, "Please ensure the graphics save location is a valid, existent folder.", "Generate Graphics Error", 2);
    } else {
      this.bands = new ArrayList<Band>(0);
      this.graphicWidth = location.getGraphicWidth();
      this.graphicHeight = location.getGraphicHeight();
      this.bandInfoFile = bandInfoFile;
      this.graphicsSaveLocation = graphicsSaveLocation;
      
      // adds all the bands (as Band objects) to the bands ArrayList
      this.readBandInfo();
      
      // creates the scaled background image to be used for the 
      
      // creates and exports all the graphics for the bands in this ArrayList
      try {
        this.createExportAllGraphics();
      } catch (IOException e) {
        System.out.println("there was an error when calling createExportAllGraphics()");
        e.printStackTrace();
      }
      
      // updates the flag that the graphics are ready to be imported into Ignite
      this.doneCreating = true;
    }
  }
  
  /**
   * Reads all the bands from the bandInfoFile and adds them to the bands ArrayList as Band objects
   * @throws FileNotFoundException if the txtFile is not found (even though it should be possible)
   */
  private void readBandInfo() throws FileNotFoundException {
    Scanner inFS = new Scanner(this.bandInfoFile);
    
    int month;
    int date;
    String name;
    
    // going through line by line and adding the bands based on the info in the txt file
    while(inFS.hasNext()) {
      // gets month
      inFS.useDelimiter("/");
      month = Integer.parseInt(inFS.next());
      
      // uses one character (the "/" in this case)
      inFS.useDelimiter("");
      inFS.next();
      
      // gets date
      inFS.useDelimiter(" ");
      date = Integer.parseInt(inFS.next());
      
      // uses one character (the " " in this case)
      inFS.useDelimiter("");
      inFS.next();
      
      // gets name
      inFS.useDelimiter("\n");
      name = inFS.nextLine();
      
      this.bands.add(new Band(name, month, date));
    }
    
    inFS.close();
  }
  
  /**
   * Creates all of the graphics and exports them to the designated file location
   * @throws IOException 
   */
  private void createExportAllGraphics() throws IOException {
    for(Band band : this.bands)
      createExportSingleGraphic(band);
  }
  
  /**
   * Creates a scaled version of the background image, 
   * 
   * @param originalImage - the image to create a scaled version of
   * @param targetWidth - the width to scale the image to
   * @param targetHeight - the height to scale the image to
   * @return a new, scaled version of the originalImage
   */
  private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
    Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
    BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
    outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
    return outputImage;
  }
  
  /**
   * Creates a single graphic and exports it to the designated file location
   * 
   * @param graphicsSaveLocation - the folder to save all the graphics to once they is generated
   * @param band - the Band to create and export the graphic of
   * @throws IOException 
   */
  private void createExportSingleGraphic(Band band) throws IOException {    
   // loads the background image
    // THESE TWO LINES BELOW HERE WORK, BUT DON'T CHANGE THE SIZE
    BufferedImage image = ImageIO.read(new File("Music Backgrounds\\MusicBackground1.jpg"));

    image = resizeImage(image, this.graphicWidth, this.graphicHeight);
    Graphics2D g2d = image.createGraphics();
            
    // sets these parameters based on the size of the location
    // TODO: set these based on the specific location
    int xPadding = 60;
    int maxNameFontSize = 200;
    int maxDateFontSize = (int) (maxNameFontSize*0.9);
    
    // adds the text to the g2d object based on the specific parameters
    this.drawText(g2d, image, band, xPadding, maxNameFontSize, maxDateFontSize);
    
    // creates the output file for this specific graphic
    File outputFile = new File(graphicsSaveLocation + "\\"+ band.generateFileName() + ".jpg");
        
    // writes the image to the age
    ImageIO.write(image, "jpg", outputFile);
  }
  
  /**
   * Draws the name and date of the Band onto the Image
   * 
   * @param g2d - the Graphics2D object that the text is drawn onto
   * @param buffImg - the image that is being drawn onto
   * @param band - the Band that is currently having a graphic created
   * @param xPadding - the amount of spacing on either side of the text
   * @param maxNameFontSize - the maximum font size for the name (to prevent overcrowding)
   * @param maxDateFontSize - the maximum font size for the date (to prevent overcrowding)
   */
  private void drawText(Graphics2D g2d, BufferedImage buffImg, Band band, int xPadding, int maxNameFontSize, int maxDateFontSize) {
    Font bandNameFont;
    Font bandNameTempFont1 = new Font("Britannic Bold", Font.BOLD, 10);
    Font bandNameTempFont2 = new Font("Britannic Bold", Font.BOLD, 10);
    
    FontMetrics bandNameMetrics;
    int bandNameMaxLineSize1 = -1;
    int bandNameMaxLineSize2 = -1;

    String[] bandNameSections = band.getSeperateNameSections();
    
    // Finds the max size of line 1 of the band name
    {
      bandNameMetrics = g2d.getFontMetrics(bandNameTempFont1);
      while(bandNameMetrics.stringWidth(bandNameSections[0]) < (buffImg.getWidth() - xPadding * 2)) {
        int newSize = bandNameTempFont1.getSize() + 10;
        bandNameTempFont1 = new Font(bandNameTempFont1.getFontName(), Font.BOLD, newSize);
        bandNameMetrics = g2d.getFontMetrics(bandNameTempFont1);
      }
      bandNameMaxLineSize1 = bandNameTempFont1.getSize();
    }
    
    // finds the max size of line 2 (if applicable)
    if(bandNameSections[1] != null) {
      bandNameMetrics = g2d.getFontMetrics(bandNameTempFont2);
      while(bandNameMetrics.stringWidth(bandNameSections[1]) < (buffImg.getWidth() - xPadding *2)) {
        int newSize = bandNameTempFont2.getSize() + 10;
        bandNameTempFont2 = new Font(bandNameTempFont2.getFontName(), Font.BOLD, newSize);
        bandNameMetrics = g2d.getFontMetrics(bandNameTempFont2);
      }
      bandNameMaxLineSize2 = bandNameTempFont2.getSize();
    }
    
    // Decides which size to use (if only 1 line, uses the first size automatically)
    if(bandNameSections[1] == null) {
      bandNameFont = new Font(bandNameTempFont1.getFontName(), Font.BOLD, bandNameMaxLineSize1);
    } else {
      if(bandNameMaxLineSize1 > bandNameMaxLineSize2) {
        bandNameFont = new Font(bandNameTempFont2.getFontName(), Font.BOLD, bandNameMaxLineSize2);
      } else {
        bandNameFont = new Font(bandNameTempFont1.getFontName(), Font.BOLD, bandNameMaxLineSize1);
      }
    }
    
    // Checks that the font is not larger than the largest allowable font size
    if(bandNameFont.getSize() > maxNameFontSize) {
      bandNameFont = new Font(bandNameFont.getFontName(), Font.BOLD, maxNameFontSize);
    }
    
    bandNameMetrics = g2d.getFontMetrics(bandNameFont);
    
    
    
    // Now moving on to the stuff for the month and date
    Font bandDateFont = new Font("Britannic Bold", Font.BOLD, 10);; //= new Font("Britannic Bold", Font.BOLD, 10);
    FontMetrics bandDateMetrics = g2d.getFontMetrics(bandDateFont); //= g2d.getFontMetrics(bandDateFont);
    
    String dateText = "";
    
    switch(band.getMonth()) {
      case 1: dateText = dateText + "Jan. "; break;
      case 2: dateText = dateText + "Feb. "; break;
      case 3: dateText = dateText + "March "; break;
      case 4: dateText = dateText + "April "; break;
      case 5: dateText = dateText + "May "; break;
      case 6: dateText = dateText + "June "; break;
      case 7: dateText = dateText + "July "; break;
      case 8: dateText = dateText + "August "; break;
      case 9: dateText = dateText + "Sept. "; break;
      case 10: dateText = dateText + "Oct. "; break;
      case 11: dateText = dateText + "Nov. "; break;
      case 12: dateText = dateText + "Dec. "; break;
    }
    
    dateText = dateText + Integer.toString(band.getDate());
    
    switch(band.getDate()) {
      case 1, 21, 31: dateText = dateText + "st"; break;
      case 2, 22: dateText = dateText + "nd"; break;
      case 3, 23: dateText = dateText + "rd"; break;
      default: dateText = dateText + "th"; break;
    }
    
    while(bandDateMetrics.stringWidth(dateText) < (buffImg.getWidth() - xPadding *2)) {
      int newSize = bandDateFont.getSize() + 10;
      bandDateFont = new Font(bandDateFont.getFontName(), Font.BOLD, newSize);
      bandDateMetrics = g2d.getFontMetrics(bandDateFont);
    }
    
    if(bandDateFont.getSize() > maxDateFontSize) {
      bandDateFont = new Font(bandDateFont.getFontName(), Font.BOLD, maxDateFontSize);
      bandDateMetrics = g2d.getFontMetrics(bandDateFont);
    }
    
    
    
    // Now we can actually draw the images, but first we gotta calculate vertical spacing
    int whitespaceDouble = (buffImg.getHeight() - 
        (bandNameMetrics.getHeight()*2 + bandDateMetrics.getHeight())) / 4;
    int[] yNameLineDouble = new int[2];
    yNameLineDouble[0] = whitespaceDouble + (int) (bandNameMetrics.getHeight()*0.85);
    yNameLineDouble[1] = yNameLineDouble[0] + whitespaceDouble + bandNameMetrics.getHeight();
    int yDateLineDouble = yNameLineDouble[1] + whitespaceDouble + bandDateMetrics.getHeight();
    
    int whitespaceSingle = (buffImg.getHeight() - 
        (bandNameMetrics.getHeight() + bandDateMetrics.getHeight())) / 3;
    int yNameLineSingle = whitespaceSingle + bandNameMetrics.getHeight();
    int yDateLineSingle = yNameLineSingle + whitespaceSingle + bandDateMetrics.getHeight();
    
    // Now let's draw the names
    g2d.setFont(bandNameFont);
    g2d.setColor(Color.WHITE);
    
    if(bandNameSections[1] != null) {
      for(int i = 0; i < bandNameSections.length; i++) {
        int x = (buffImg.getWidth() - bandNameMetrics.stringWidth(bandNameSections[i])) / 2;
        int y = yNameLineDouble[i];
        g2d.drawString(bandNameSections[i], x, y);
      }
    } else {
      int x = (buffImg.getWidth() - bandNameMetrics.stringWidth(bandNameSections[0])) / 2;
      int y = yNameLineSingle;
      g2d.drawString(bandNameSections[0], x, y);
    }
    
    // Now let's draw the dates
    g2d.setFont(bandDateFont);
    g2d.setColor(new Color(175, 208, 250));
    int dateX = (buffImg.getWidth() - bandDateMetrics.stringWidth(dateText)) / 2;

    if(bandNameSections[1] != null) {
      int y = yDateLineDouble;
      g2d.drawString(dateText, dateX, y);
    } else {
      int y = yDateLineSingle;
      g2d.drawString(dateText, dateX, y);
    }
    
    
  }
  
  /**
   * Returns the number of bands in this specific generation run
   * 
   * @return the number of bands in this specific generation run, or -1 if not yet completed
   */
  public int getNumBands() {
    if(this.doneCreating) return this.bands.size(); 
    return -1;
  }
}
