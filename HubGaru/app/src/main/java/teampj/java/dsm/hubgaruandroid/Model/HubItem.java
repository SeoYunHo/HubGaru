package teampj.java.dsm.hubgaruandroid.Model;

/**
 * Created by user on 2017-08-23.
 */

public class HubItem {

    private String garuName;
    private String TEAMCODE;
    private String picUri;
    private String date;
    private String songTitle;
    private String musicUri;

    public HubItem(String garuName, String TEAMCODE, String picUri, String date, String songTitle, String musicUri) {
        this.garuName = garuName;
        this.TEAMCODE = TEAMCODE;
        this.picUri = picUri;
        this.date = date;
        this.songTitle = songTitle;
        this.musicUri = musicUri;
    }

    public String getGaruName() {
        return garuName;
    }

    public void setGaruName(String garuName) {
        this.garuName = garuName;
    }

    public String getTEAMCODE() {
        return TEAMCODE;
    }

    public void setTEAMCODE(String TEAMCODE) {
        this.TEAMCODE = TEAMCODE;
    }

    public String getPicUri() {
        return picUri;
    }

    public void setPicUri(String picUri) {
        this.picUri = picUri;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getMusicUri() {
        return musicUri;
    }

    public void setMusicUri(String musicUri) {
        this.musicUri = musicUri;
    }
}
