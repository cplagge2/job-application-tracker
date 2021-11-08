/**
 * Creates a job application class used for storing application data
 * 
 * @author cjpla
 *
 */
public class JobApplication {
  private String jobTitle;
  private Integer date;
  private String websiteURL;
  private String location;
  private Boolean rejected = false;
  
  /**
   * Constructor for the JobApplication class
   * 
   * @param title - job title
   * @param date - application date
   * @param URL - company website url
   * @param location - Location of the job
   * @throws IllegalArgumentException - if any input is invalid
   */
  public JobApplication(String title, Integer date, String URL, String location) 
      throws IllegalArgumentException{
    if(title == null || URL == null || location == null) {
      throw new IllegalArgumentException();
    }
    
    //Confirms valid date input
    String lengthCheck = date.toString();
    if(lengthCheck.length() != 8){
      throw new IllegalArgumentException();
    }
    
    //Sets private fields
    this.jobTitle = title;
    this.date = date;
    this.websiteURL = URL;
    this.location = location;
  }
  
  /**
   * Accessor for title field
   * 
   * @return Title of job
   */
  public String getTitle() {
    String title = "'" + jobTitle + "'";
    return title;
  }
  
  /**
   * Accessor for date field
   * 
   * @return Date of job application
   */
  public Integer getDate() {
    return date;
  }
  
  /**
   * Accessor for the company website url
   * 
   * @return URL of company website
   */
  public String getURL() {
    String url = "'" + websiteURL + "'";
    return url;
  }
  
  /**
   * Accessor for the location field
   * 
   * @return Location of the job
   */
  public String getLocation() {
    String jobLocation= "'" + location + "'";
    return jobLocation;
  }
  
  /**
   * Accessor for the status of the job application
   * True if rejected from job, or false if outstanding
   * 
   * @return Current status of the job
   */
  public Boolean getStatus() {
    return rejected;
  }
}
