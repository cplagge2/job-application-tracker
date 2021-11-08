import java.util.Scanner;

/**
 * Front end to run the application tracker
 * Formats output and allows for an easy front end user experience
 * 
 * @author Christopher Plagge
 *
 */
public class ApplicationTrackerFront {
  private static Scanner scnr = new Scanner(System.in);
  
  /**
   * Runs the front end of the job application tracking system
   * Based on user input, back end methods are called
   * 
   * @param args - unused
   */
  public static void main(String[] args) {
    //Makes a new tracker
    ApplicationTracker.setup();
    boolean stopCheck = false;
    int userInput;
    //Outputs the interface
    System.out.println("Welcome to the Job Application Database!");
    System.out.println("Enter a number to navigate");
    //Runs the interface looping until the user calls to quit
    while (!stopCheck) {
      System.out.println("1) Show the database \n2) Add new application \n3) Quit");
      userInput = scnr.nextInt();
      switch (userInput) {
        case 1:
          System.out.println();
          ApplicationTracker.show(scnr);
          System.out.println();
          break;
        case 2:
          System.out.println();
          ApplicationTracker.addApplication(scnr);
          System.out.println();
          break;
        case 3:
          stopCheck = true;
          break;
      }
          
    }
  
  }
}
