package teampj.java.dsm.hubgaruandroid.Model;

/**
 * Created by user on 2017-10-10.
 */

public class CommentItem2 {
    private String pic;
    private String name;
    private String comment;
    private String date;

    public CommentItem2(String pic, String name, String comment, String date) {
        this.pic = pic;
        this.name = name;
        this.comment = comment;
        this.date = date;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
