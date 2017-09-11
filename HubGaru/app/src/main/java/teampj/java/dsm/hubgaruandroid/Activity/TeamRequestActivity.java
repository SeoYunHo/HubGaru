package teampj.java.dsm.hubgaruandroid.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by dsm2016 on 2017-08-31.
 */

public class TeamRequestActivity extends AppCompatActivity {

    private EditText requestName;
    private Spinner requestKind;
    private EditText requestInfo;
    private LinearLayout fileGogo;
    private Button requestGoButton;
    private Button requestAwayButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_new_request);

        requestName = (EditText)findViewById(R.id.new_request_name);
        requestKind = (Spinner)findViewById(R.id.new_reaquest_kind);
        requestInfo = (EditText)findViewById(R.id.new_request_info);

        fileGogo = (LinearLayout)findViewById(R.id.filegogo);
        fileGogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //파일관리자 열어 저장하는 액션 필요
            }
        });

        requestGoButton = (Button) findViewById(R.id.request_gogo);
        requestGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamRequestActivity.this.finish();
            }
        });

        requestAwayButton = (Button) findViewById(R.id.request_goaway);
        requestAwayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamRequestActivity.this.finish();
            }
        });
    }
    public void onBackPressed() {TeamRequestActivity.this.finish();}
}
