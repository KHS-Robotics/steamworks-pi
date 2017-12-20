import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.tables.*;

public class Main {
  public static void main(String[] args) {
    // Loads our OpenCV library. This MUST be included
    System.loadLibrary("opencv_java310");

    // Connect NetworkTables, and get access to the publishing table
    NetworkTable.setClientMode();
    NetworkTable.setTeam(4342);
    NetworkTable.setNetworkIdentity("rasperry-pi-3");
    NetworkTable.initialize();

    System.out.println("Hello, World!");
  }
}