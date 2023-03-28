import java.util.Arrays;

public class Driver {
  public static void main (String[] args) {
    Band oneName = new Band("Black & White", 8, 8);
    //System.out.println(oneName);
    System.out.println(Arrays.deepToString(oneName.getSeperateNameSections()));
  }
  
  public static void testRun() {
    System.out.println("it ran this other method!!");
  }
}
