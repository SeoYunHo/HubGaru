package teampj.java.dsm.hubgaruandroid.Model;

import android.graphics.drawable.Drawable;

/**
 * Created by dsm2016 on 2017-09-02.
 */

public class TeamChatItem {
    private String nameStr;
    private String descStr;
    private String dateTime;

    public TeamChatItem(){ }

    public TeamChatItem(String name, String desc, String time){
        this.nameStr = name;
        this.descStr = desc;
        this.dateTime = time;
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
}
