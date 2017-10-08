package teampj.java.dsm.hubgaruandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by dsm2016 on 2017-09-04.
 */

public class TeamCreateActivity extends AppCompatActivity {

    private EditText newTeamName;
    private CheckBox isPublic;
    private CheckBox isPrivate;
    private EditText newTeamInfo;

    //팀 제작 처리 스크립트 필요

    private LinearLayout rec_gogo;
    private Button goodbtn;
    private Button nononobtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_create);

        newTeamName = (EditText)findViewById(R.id.new_team_name);
        isPublic = (CheckBox)findViewById(R.id.isPublicCB);
        isPrivate = (CheckBox)findViewById(R.id.isPrivateCB);
        newTeamInfo = (EditText)findViewById(R.id.new_team_info);

        rec_gogo = (LinearLayout) findViewById(R.id.record_theme_gogo);
        rec_gogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ct_to_rt = new Intent(TeamCreateActivity.this, TeamRecordThemeActivity.class);
                startActivityForResult(ct_to_rt,0);
            }
        });

        goodbtn = (Button) findViewById(R.id.goodgoodgoodgogoggo);
        goodbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ct_to_team = new Intent(TeamCreateActivity.this, TeamMainActivity.class);
                startActivity(ct_to_team);
                TeamCreateActivity.this.finish();
            }
        });

        nononobtn = (Button)findViewById(R.id.nonononono);
        nononobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ct_to_main = new Intent(TeamCreateActivity.this, TabLayoutActivity.class);
                startActivity(ct_to_main);
                TeamCreateActivity.this.finish();
            }
        });
    }
    public void onBackPressed() {TeamCreateActivity.this.finish();}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == RESULT_OK){
            //받아온 음악파일 저장
        }
    }
}
