package teampj.java.dsm.hubgaruandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import teampj.java.dsm.hubgaruandroid.Model.TeamSettings;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by dsm2016 on 2017-08-31.
 */

public class TeamSettingActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private Button cancleBtn;
    private Button applyBtn;
    private TeamSettings teamSet = new TeamSettings();
    private TextView alamset;
    private TextView setset;

    private int TEAMCODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_settings);

        Intent intent = getIntent();
        TEAMCODE = intent.getIntExtra("TEAMCODE",0);

        cancleBtn = (Button) findViewById(R.id.drawer_btn);
        cancleBtn.setText("취소");
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamSettingActivity.this.finish();
            }
        });
        applyBtn = (Button) findViewById(R.id.setting_btn);
        applyBtn.setText("확인");
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamSettingActivity.this.finish();
                databaseReference.child(String.valueOf(TEAMCODE)).child("Settings").setValue(teamSet);
            }
        });

        alamset = (TextView) findViewById(R.id.alam_set);
        alamset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alamset.getText().equals("     알림 off")) {
                    alamset.setText("     알림 on");
                    teamSet.setAlam(true);
                } else {
                    alamset.setText("     알림 off");
                    teamSet.setAlam(false);
                }
            }
        });

        setset = (TextView) findViewById(R.id.set_set);
        setset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setset.getText().equals("     설정 뭐넣지 off")) {
                    setset.setText("     설정 뭐넣지 on");
                    teamSet.setAlam(true);
                } else {
                    setset.setText("     설정 뭐넣지 off");
                    teamSet.setAlam(false);
                }
            }
        });

        SetRealTimeDataBase();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TeamSettingActivity.this.finish();
    }

    public void SetRealTimeDataBase(){
        databaseReference.child(String.valueOf(TEAMCODE)).child("Settings").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //teamSet = dataSnapshot.getValue(TeamSettings.class);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
}
