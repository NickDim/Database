public class User {

  private int PK;
  private String fName;
  private String lName;
  private String email;

  public User(int PK, String fName, String lName, String email) {
    this.PK = PK;
    this.fName = fName;
    this.lName = lName;
    this.email = email;
  }

  public int getPK() {
    return PK;
  }

  public String getfName() {
    return fName;
  }

  public String getEmail() {
    return email;
  }

  public String getlName() {
    return lName;
  }

  public void setfName(String fName) {
    this.fName = fName;
  }

  public void setlName(String lName) {
    this.lName = lName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
