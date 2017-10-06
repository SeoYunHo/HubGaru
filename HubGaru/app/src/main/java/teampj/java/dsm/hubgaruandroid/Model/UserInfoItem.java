package teampj.java.dsm.hubgaruandroid.Model;

/**
 * Created by user on 2017-09-24.
 */

public class UserInfoItem {
    private String name;
    private String position;
    private String phone;
    private String picture;
    private String intro;

    public UserInfoItem(String name, String position, String phone) {
        this.name = name;
        this.position = position;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
