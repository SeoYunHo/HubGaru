package teampj.java.dsm.hubgaruandroid.Model;

import android.graphics.drawable.Drawable;

/**
 * Created by dsm2016 on 2017-09-02.
 */

public class TeamChatItem {
    private String nameStr;
    private String descStr;

    public TeamChatItem(String name, String desc){
        this.nameStr = name;
        this.descStr = desc;
    }

    public String getName() {
        return this.nameStr;
    }
    public String getDesc() {
        return this.descStr;
    }
}
