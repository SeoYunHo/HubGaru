package teampj.java.dsm.hubgaruandroid.Model;

/**
 * Created by user on 2017-09-24.
 */

public class UserInfoItem {
    private String name;
    private String position;
    private String email;

    public UserInfoItem(String name, String position, String email) {
        this.name = name;
        this.position = position;
        this.email = email;
    }

    public UserInfoItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
