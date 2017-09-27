package teampj.java.dsm.hubgaruandroid.Model;

/**
 * Created by user on 2017-09-26.
 */

public class CommentItem {
    private String profilePic;
    private String name;
    private String comment;
    private String editDate;

    public CommentItem(String profilePic, String name, String comment, String editDate) {
        this.profilePic = profilePic;
        this.name = name;
        this.comment = comment;
        this.editDate = editDate;
    }

    public CommentItem() {
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
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

    public String getEditDate() {
        return editDate;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }
}
