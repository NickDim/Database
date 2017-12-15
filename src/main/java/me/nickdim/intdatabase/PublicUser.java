package me.nickdim.intdatabase;

public class PublicUser {

  private int PK;
  private String fName;
  private String lName;

  public PublicUser(User user) {
    this.PK = user.getPK();
    this.fName = user.getfName();
    this.lName = user.getlName();
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
