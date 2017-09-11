package teampj.java.dsm.hubgaruandroid.Model;

import java.io.File;

/**
 * Created by dsm2016 on 2017-09-04.
 */

public class TeamRequestItem {
    private String nickName;
    private String dateTime;
    private String requestName;
    private String requestDesc;

    public TeamRequestItem(String nName, String time, String rName, String rDesc){
        this.nickName = nName;
        this.dateTime = time;
        this.requestName = rName;
        this.requestDesc = rDesc;
    }

    public String getNickName() {
        return nickName;
    }
    public String getDateTime() {
        return dateTime;
    }
    public String getRequestName() {
        return requestName;
    }
    public String getRequestDesc() {
        return requestDesc;
    }
}
