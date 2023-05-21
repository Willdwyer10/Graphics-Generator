package customTypes;

/**
 * represents a band that will be playing on a specific date
 * 
 * @author william
 */
public class Band {
  private String name;
  private int month;
  private int date;
  
  /**
   * default constructor that sets all values to default values;
   */
  public Band() {
    this("default", -1, -1);
  }
  
  /**
   * constructor that sets the name, month, and date based on the parameters passed in
   * 
   * @param name - the name of the band
   * @param month - the month the band is playing
   * @param date - the date the band is playing on
   */
  public Band(String name, int month, int date) {
    if(month < -1 || month > 12) 
               throw new IllegalArgumentException("invalid month -- month < -1 || month > 12");
    if(date < -1 || date > 31)
               throw new IllegalArgumentException("invalid date -- date < -1 || date > 31");
    if(name.isBlank() || name == null)
               throw new IllegalArgumentException("invalid name -- name.isBlank() || name == null");
    this.name = name;
    this.month = month;
    this.date = date;
  }
  
  /**
   * mutator method for name
   * 
   * @param name - the new name of the Band
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * mutator method for month
   * 
   * @param month - the new month the Band is playing
   */
  public void setMonth(int month) {
    this.month = month;
  }
  
  /**
   * mutator method for date
   * 
   * @param date - the new date the Band is playing
   */
  public void setDate(int date) {
    this.date = date;
  }
  
  /**
   * accessor method for name
   * 
   * @return the name of the Band
   */
  public String getName() {
    return this.name;
  }
  
  /**
   * accessor method for month
   * 
   * @return the month the Band is playing
   */
  public int getMonth() {
    return this.month;
  }
  
  /**
   * accessor method for date
   * 
   * @return the date the Band is playing
   */
  public int getDate() {
    return this.date;
  }
  
  /**
   * returns a nicely-formatted string that is used as the filename of the graphic. 
   * NOTE: any spaces or special characters are removed from the name of the band
   * 
   * Example:
   *    
   *    M_8_8_BlackWhite
   *    
   *    for a band named "Black & White" playing August 8
   * 
   * @return a string that can be used as the Band's graphic's filename
   */
  public String generateFileName() {
    // removes all applicable special characters as well as spaces
    String nameNice = this.name.replaceAll("[\\&\\/\\\"\\(\\)\\'\\*]", "").replaceAll(" ", "");
    return "M_" + this.month + "_" + this.date + "_" + nameNice;
  }
  
  /**
   * helper method to count the number of words
   * NOTE: this counts words by assuming each word contains one, and only one, upper case letter
   * 
   * @return the number of words in the name
   */
  private int numWords() {
    int numUpperCase = 0;
    for (int i = 0; i < this.name.length(); i++) {
      if(Character.isUpperCase(this.name.charAt(i))) numUpperCase ++;
      
    }
    return numUpperCase;
  }
  
  /**
   * This method returns a perfect size array (length 2) that contains the name 
   * of the band split into two (or one) line(s) to most-optimally fill the space on the graphic
   * 
   * @return a perfect size array of length 2 that contains the two strings in uppercase format
   */
  //TODO: FIGURE OUT a better algorithm for seperating the words
  public String[] getSeperateNameSections() {
    String[] nameSections = new String[2]; // will be returned
    
    // case where there is just one word in the name
    if(this.numWords() == 1) {
      nameSections[0] = this.name;
    }
    
    // case where there are 2 words in the name
    else if(this.numWords() == 2) {
      int numSpaces = 0;
      for(int i = 0; i < this.name.length(); i++) {
        if(this.name.charAt(i) == ' ') numSpaces++;
      }
      
      // case where there are 2 words in the name combined by just a single space
      if(numSpaces == 1) {
        nameSections[0] = this.name.substring(0, this.name.indexOf(" "));
        nameSections[1] = 
                  this.name.substring(this.name.indexOf(" ") + 1, this.name.length());
      }
      
      // case where there are 2 words in the name combined by something other than a single space
      else {
        for(int i = (this.name.length() / 2) - 1; i < this.name.length(); i++) {
          if(this.name.charAt(i) == '/' || this.name.charAt(i) == '&') {
            nameSections[0] = this.name.substring(0, this.name.indexOf(" "));
            nameSections[1] = this.name.substring(this.name.indexOf(" ") + 1, this.name.length());
            break;
          }
          
          else {
            nameSections[0] = this.name.substring(0, this.name.indexOf(" ") + 2);
            nameSections[1] = this.name.substring(this.name.indexOf(" ") + 3, this.name.length());
          }
        }
      }
    }
    
    //case where there are three words
    else if (this.numWords() == 3) {
      int spacePosition1 = 0;
      int spacePosition2 = 0;
      for(int i = 0; i < this.name.length(); i++) {
        if(this.name.charAt(i) == ' ') {
          spacePosition1 = i;
          break;
        }
      }
      for(int i = spacePosition1 + 1; i < this.name.length(); i++) {
        if(this.name.charAt(i) == ' ') {
          spacePosition2 = i;
          break;
        }
      }
      
      int numSpaces = 0;
      for(int i = this.name.length()/2; i < this.name.length(); i++) {
        if(this.name.charAt(i) == ' ') numSpaces++;
      }
      
      
      if(numSpaces > 1) {
        nameSections[0] = this.name.substring(0, spacePosition1);
        nameSections[1] = this.name.substring(spacePosition1 + 1, this.name.length());
      } else {
        nameSections[0] = this.name.substring(0, spacePosition2);
        nameSections[1] = this.name.substring(spacePosition2 + 1, this.name.length());
      }
    }
    
    // Case where there are four words
    else if (this.numWords() == 4) {
      int spacePosition1 = 0;
      int spacePosition2 = 0;
      int spacePosition3 = 0;
      for(int i = 0; i < this.name.length(); i++) {
        if(this.name.charAt(i) == ' ') {
          spacePosition1 = i;
          break;
        }
      }
      
      for(int i = spacePosition1 + 1; i < this.name.length(); i++) {
        if(this.name.charAt(i) == ' ') {
          spacePosition2 = i;
          break;
        }
      }
      
      for(int i = spacePosition2 + 1; i < this.name.length(); i++) {
        if(this.name.charAt(i) == ' ') {
          spacePosition3 = i;
          break;
        }
      }
      
      int numSpaces = 0;
      for(int i = this.name.length()/2; i < this.name.length(); i++) {
        if(this.name.charAt(i) == ' ') numSpaces++;
      }
      
      if(numSpaces > 2) {
        nameSections[0] = this.name.substring(0, spacePosition1);
        nameSections[1] = this.name.substring(spacePosition1 + 1, this.name.length());
      } else {
        nameSections[0] = this.name.substring(0, spacePosition3);
        nameSections[1] = this.name.substring(spacePosition3 + 1, this.name.length());
      }
    }
    
    // default case with any other case
    else {
      int breakPoint = this.name.length();
      for(int i = (this.name.length() * 3) / 8; i < this.name.length(); i++) {
        if(this.name.charAt(i) == ' ') {
          breakPoint = i;
          break;
        }
      }
      nameSections[0] = this.name.substring(0, breakPoint);
      if(breakPoint != this.name.length()) 
              nameSections[1] = this.name.substring(breakPoint + 1, this.name.length());
    }
    
    if(nameSections[0] != null) nameSections[0] = nameSections[0].toUpperCase();
    if(nameSections[1] != null) nameSections[1] = nameSections[1].toUpperCase();
    return nameSections;
  }
  
  /**
   * returns a simple string containing all the information of the band
   * 
   * Example:
   *    
   *    Name: Black & White  ||  Month: 8  ||  Date: 8
   *    
   *    for a band named "Black & White" playing August 8
   * 
   * @return a nicely formatted string of the Band's information
   */
  @Override
  public String toString() {
    return "Name: " + this.name + "  ||  Month: " + this.month + "  ||  Date: " + this.date;
  }
}
