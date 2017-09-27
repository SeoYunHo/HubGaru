package teampj.java.dsm.hubgaruandroid.Model;

/**
 * Created by dsm2016 on 2017-09-26.
 */

public class TeamSettings {
    boolean Alam;
    boolean Set;

    public TeamSettings() {}
    public TeamSettings(boolean a,boolean s){
        this.Alam = a;
        this.Set = s;
    }

    public void setAlam(boolean alam) {
        Alam = alam;
    }
    public void setSet(boolean set) {
        Set = set;
    }

    public boolean getAlam() {
        return this.Alam;
    }
    public boolean getSet() {
        return this.Set;
    }
}
