public class PublicUser {

  private int PK;
  private String fName;
  private String lName;

  public PublicUser(int PK, String fName, String lName) {
    this.PK = PK;
    this.fName = fName;
    this.lName = lName;
  }

  public int getPK() {
    return PK;
  }

  public String gefName() {
    return fName;
  }

  public String getlName() {
    return lName;
  }

  public void setPK(int PK) {
    this.PK = PK;
  }

  public void setfName(String fName) {
    this.fName = fName;
  }

  public void setlName(String lName) {
    this.lName = lName;
  }
}
