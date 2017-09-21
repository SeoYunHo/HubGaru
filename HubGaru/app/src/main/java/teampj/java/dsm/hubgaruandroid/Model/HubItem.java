package teampj.java.dsm.hubgaruandroid.Model;

/**
 * Created by user on 2017-08-23.
 */

public class HubItem {

    private String picUri;
    private String date;
    private String songTitle;
    private String musicUri;
    private int like;
    private Object listView;

    public HubItem() {
    }

    public HubItem(String picUri, String date, String songTitle, String musicUri, int like, Object listView) {
        this.picUri = picUri;
        this.date = date;
        this.songTitle = songTitle;
        this.musicUri = musicUri;
        this.like = like;
        this.listView = listView;
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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public Object getListView() {
        return listView;
    }

    public void setListView(Object listView) {
        this.listView = listView;
    }
}
