package teampj.java.dsm.hubgaruandroid.Model;

import android.graphics.drawable.Drawable;
import android.media.Image;

/**
 * Created by dsm2016 on 2017-09-02.
 */

public class TeamChatItem {
    private String nameStr;
    private String descStr;
    private String dateTime;
    private boolean isPhoto;

    public TeamChatItem(){ }

    public TeamChatItem(String name, String desc, String time){
        this.nameStr = name;
        this.descStr = desc;
        this.dateTime = time;
        this.isPhoto = false;
    }

    public TeamChatItem(String name, String desc, String time, boolean isP){
        this.nameStr = name;
        this.descStr = desc;
        this.dateTime = time;
        this.isPhoto = isP;
    }

    public String getNameStr() {
        return this.nameStr;
    }
    public String getDescStr() {
        return this.descStr;
    }
    public String getDateTime() {
        return dateTime;
    }
    public boolean getIsPhoto() {
        return isPhoto;
    }
}
