package teampj.java.dsm.hubgaruandroid.Model;

import java.util.List;

/**
 * Created by user on 2017-09-27.
 */

public class GaruItem {
    String teamName;
    String teamCode;
    String teamPic;
    String teamIntro;
    String leader;

    public GaruItem(String teamName, String teamCode, String teamPic, String teamIntro, String leader) {
        this.teamName = teamName;
        this.teamCode = teamCode;
        this.teamPic = teamPic;
        this.teamIntro = teamIntro;
        this.leader = leader;
    }

    public GaruItem() {
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getTeamIntro() {
        return teamIntro;
    }

    public void setTeamIntro(String teamIntro) {
        this.teamIntro = teamIntro;
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

}
