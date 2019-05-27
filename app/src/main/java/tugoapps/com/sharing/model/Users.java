package tugoapps.com.sharing.model;

import com.google.gson.annotations.SerializedName;

public class Users {
  public int id;
  public String name;
  @SerializedName("phone")
  public String phoneNumber;

  public Users() {
  }

  public Users(int id, String name, String phoneNumber) {
    this.id = id;
    this.name = name;
    this.phoneNumber = phoneNumber;
  }
}
