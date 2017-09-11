package teampj.java.dsm.hubgaruandroid.Model;

/**
 * Created by user on 2017-08-23.
 */

public class HubItem {

    private String picSrc;
    private String date;
    private String songTitle;

    public HubItem() {
    }

    public HubItem(String picSrc, String date, String songTitle) {
        this.picSrc = picSrc;
        this.date = date;
        this.songTitle = songTitle;
    }

    public HubItem(String date, String songTitle) {
        this.date = date;
        this.songTitle = songTitle;
    }

    public String getPicSrc() {
        return picSrc;
    }

    public void setPicSrc(String picSrc) {
        this.picSrc = picSrc;
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

}
