package teampj.java.dsm.hubgaruandroid.Model;

/**
 * Created by dsm2016 on 2017-10-09.
 */

public class TeamMemberItem {
    private String mIcon;
    private String mName;

    public TeamMemberItem(){ }

    public TeamMemberItem(String mIcon, String mName){
        this.mIcon = mIcon;
        this.mName = mName;
    }

    public String getmIcon() {
        return mIcon;
    }
    public String getmName() {
        return mName;
    }
}
