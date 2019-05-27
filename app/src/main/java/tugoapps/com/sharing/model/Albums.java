package tugoapps.com.sharing.model;

public class Albums {

  public int userID;
  public int id;
  public String title;

  public Albums() {
  }

  public Albums(int userID, int id, String title) {
    this.userID = userID;
    this.id = id;
    this.title = title;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
