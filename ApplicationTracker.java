import java.sql.*;
import java.util.Scanner;
import java.math.*;

/**
 * Main back end code for the job application tracker
 * Current functionality includes adding applications and displaying the database
 * 
 * Some API interaction code based on tutorial from JavaPoint
 * https://www.javatpoint.com/example-to-connect-to-the-mysql-database
 * 
 * @author Christopher Plagge
 *
 */
public class ApplicationTracker {
  private static Statement stmt;
  //Array of labels in the SQL table
  private static String[] labels = {"JobTitle", "Date", "Website", "Location", "Rejected"};
  
  /**
   * Establishes the connection with the MySQL database
   * Error thrown if the connection cannot be established
   */
  public static void setup() {
    try {
      Connection con = DriverManager.getConnection(CONFIG.url, CONFIG.username, CONFIG.password);
      stmt = con.createStatement();
    } catch (Exception ex) {
      System.out.println("Connection failed");
      ex.printStackTrace();
    }
  }
  
  /**
   * Adds applications to the database established in setup
   * 
   * @param scnr - Scanner from front end to be used for user inputs
   * @throws IllegalArgumentException - If error occurs on insertion or date is invalid
   */
  public static void addApplication(Scanner scnr) throws IllegalArgumentException{
    
    //Takes in info about job
    scnr.nextLine();
    System.out.println("Type job title");
    String jobTitle = scnr.nextLine();
    System.out.println("Type application date");
    String jobDate = scnr.nextLine();
    System.out.println("Type website link");
    String website = scnr.nextLine();
    System.out.println("Type city of internship");
    String address = scnr.nextLine();
    
    //Confirm date has a valid input
    Integer jobDateInt;
    try {
      jobDateInt = Integer.valueOf(jobDate);
    } catch (Exception e) {
      throw new IllegalArgumentException();
    }
    
    //Make new job application
    JobApplication insertJob = new JobApplication(jobTitle, jobDateInt, website, address);
    
    //Add job to database
    //Throws error if input is database insert fails
    System.out.println("Inserting records into the table...");          
    String sql = "INSERT INTO applications VALUES (" + insertJob.getTitle() + ", " 
        + insertJob.getDate() + ", " + insertJob.getURL() + ", " 
        + insertJob.getLocation() + ", false);";
    try {
      stmt.executeUpdate(sql);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    System.out.println("Inserted records");
  }
  
  /**
   * Runs the display method for showing database
   * Depending on user input, different output options are shown
   * 
   * @param scnr
   */
  public static void show(Scanner scnr) {
    int userInput;
    System.out.println();
    System.out.println("Enter input to classify your search");
    System.out.println("1) Show all \n2) Search by name \n3) Search by date \n4) Show outstanding");
    
    userInput = scnr.nextInt();
    
    //Depending on user input, different output methods are called
    switch (userInput) {
      case 1:
        System.out.println();
        ApplicationTracker.showAll();
        System.out.println();
        break;
      case 2:
        System.out.println();
        System.out.println("Enter job name");
        String jobName = scnr.nextLine();
        jobName = scnr.nextLine();
        ApplicationTracker.showName(jobName);
        System.out.println();
        break;
      case 3:
        System.out.println();
        System.out.println("Enter application date");
        String date = scnr.nextLine();
        date = scnr.nextLine();
        ApplicationTracker.showDate(date);
        System.out.println();
        break;
    }
  }
  
  /**
   * Outputs all jobs in the database
   */
  private static void showAll() {
    //Sets a sql command and runs it
    String sql = "SELECT * FROM applications;";
    ResultSet data;
    try {
      data = stmt.executeQuery(sql);
      //Parses through the result set returned by the query
      while(data.next()) {
        String output = generateOutput(data, labels);
        System.out.println(output);
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  /**
   * Shows details of specific job based on title
   * 
   * @param jobTitle - title of job to be found
   */
  private static void showName(String jobTitle) {
    //SQL code to be used
    String sql = "SELECT * FROM applications WHERE JobTitle = '" + jobTitle + "'";
    ResultSet data;
    try {
      data = stmt.executeQuery(sql);
      while(data.next()) {
        if(data.getString("JobTitle").equals(jobTitle)) {
          String output = 
              generateOutput(data, new String[] {"JobTitle", "Date", "Location", "Rejected"});
          System.out.println(output);
        }
        else {
          System.out.println("Job name couldn't be found");
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  /**
   * Shows details of specific jobs based on date applied
   * 
   * @param date - applied to date of jobs to be found
   */
  private static void showDate(String date) {
    //SQL code to be used
    String sql = "SELECT * FROM applications WHERE Date = '" + date + "'";
    ResultSet data;
    try {
      data = stmt.executeQuery(sql);
      while(data.next()) {
        //Formats output into a string
        if(data.getString("Date").equals(date)) {
          String output = 
              generateOutput(data, new String[] {"JobTitle", "Date", "Location", "Rejected"});
          System.out.println(output);
        }
        else {
          System.out.println("Job application date couldn't be found");
        }
      }
      
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  /**
   * Formats the output into a single string with specific data chosen
   * Displays different data based on which SQL database labels are selected
   * 
   * @param data - ResultSet containing the value returned by the sql query
   * @param labels - Labels in the database for which the data is required
   * @return String with formatted output of data
   */
  private static String generateOutput(ResultSet data, String[] labels) {
    try {
      String output = "";
      //Formats output into a string
      for(int i = 0; i < labels.length; i++) {
        //Parses the date data and formats it
        if (labels[i].equals("Date")) {
          output += data.getString(labels[i]).substring(0, 2);
          output += "/";
          output += data.getString(labels[i]).substring(2, 4);
          output += "/";
          output += data.getString(labels[i]).substring(4);
        }
        //Outputs current status of the job application
        else if (labels[i].equals("Rejected")) {
          if(data.getBoolean(labels[i])) {
            output += "Rejected";
          }
          else {
            output += "Outstanding";
          }
        }
        //Adds all data without special requirements
        else {
          output += data.getString(labels[i]);
        }
        //Fenceposting for proper formatting
        if(i != labels.length - 1) {
          output += ",  ";
        }
      }
      return output;
    } catch(SQLException excpt) {
      excpt.printStackTrace();
      return "Error occurred handling exception";
    }
  }
}
