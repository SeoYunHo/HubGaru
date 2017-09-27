package teampj.java.dsm.hubgaruandroid.Model;

import java.util.List;

/**
 * Created by user on 2017-09-27.
 */

public class GaruItem {
    String teamName;
    String teamCode;
    String teamPic;
    String[] teamMember;

    public GaruItem() {
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getTeamPic() {
        return teamPic;
    }

    public void setTeamPic(String teamPic) {
        this.teamPic = teamPic;
    }

    public String[] getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(String[] teamMember) {
        this.teamMember = teamMember;
    }
}
