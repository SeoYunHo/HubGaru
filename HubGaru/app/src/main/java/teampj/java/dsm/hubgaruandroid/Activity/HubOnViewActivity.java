package teampj.java.dsm.hubgaruandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import teampj.java.dsm.hubgaruandroid.Network.Service.HubService;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-09-20.
 */

public class HubOnViewActivity extends AppCompatActivity {

    static boolean likeBtnStatus = false;
    private Button teamMainBtn;
    private ImageButton likeBtn;
    private TextView likeNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hub_on_view);

        likeNum = (TextView) findViewById(R.id.thumbsNum);

        teamMainBtn = (Button) findViewById(R.id.toMainBtn);
        teamMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_to_team = new Intent(getApplicationContext(), TeamMainActivity.class);
                startActivity(main_to_team);
            }
        });

        likeBtn = (ImageButton) findViewById(R.id.likeBtn);
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tmpNum;
                String tmpSNum;

                if(likeBtnStatus == false) {
                    likeBtnStatus = true;
                    likeBtn.setImageResource(R.drawable.thumb1);
                    tmpNum = Integer.parseInt(likeNum.getText().toString());
                    tmpNum++;
                    tmpSNum = String.valueOf(tmpNum);
                    likeNum.setText(tmpSNum);
                } else {
                    likeBtnStatus = false;
                    likeBtn.setImageResource(R.drawable.thumb3);
                    tmpNum = Integer.parseInt(likeNum.getText().toString());
                    tmpNum--;
                    tmpSNum = String.valueOf(tmpNum);
                    likeNum.setText(tmpSNum);
                }
            }
        });
    }
}
