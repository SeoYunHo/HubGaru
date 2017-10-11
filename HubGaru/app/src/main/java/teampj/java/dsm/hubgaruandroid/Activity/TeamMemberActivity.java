package teampj.java.dsm.hubgaruandroid.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by dsm2016 on 2017-10-10.
 */

public class TeamMemberActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_in);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TeamMemberActivity.this.finish();
    }
}
