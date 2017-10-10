package teampj.java.dsm.hubgaruandroid.Activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by dsm2016 on 2017-09-23.
 */

public class TeamRecordThemeCheckActivity extends AppCompatActivity {

    static final String RECORDED_FILE = "/sdcard/recorded.mp4";

    MediaPlayer player;
    int playbackPosition;

    private ImageView play;
    private ImageView pause;
    private ImageView stop;
    private SeekBar seekbar;
    private TextView zomanim;
    private TextView okay;

    boolean recPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_theme_check);
        try{
            //이따가
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"음악파일 로딩실패..",Toast.LENGTH_SHORT).show();
        }

        play = (ImageView)findViewById(R.id.trc_play_btn);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"재생!",Toast.LENGTH_SHORT).show();
            }
        });

        pause = (ImageView)findViewById(R.id.trc_pause_btn);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recPlaying){
                    Toast.makeText(getApplicationContext(),"잠깐만",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"어어계속",Toast.LENGTH_SHORT).show();
                }
            }
        });

        stop = (ImageView)findViewById(R.id.trc_stop_btn);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"그마안!",Toast.LENGTH_LONG).show();
            }
        });

        zomanim = (TextView)findViewById(R.id.zomanindtzom);
        zomanim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamRecordThemeCheckActivity.this.finish();
            }
        });

        okay = (TextView) findViewById(R.id.okayokay);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamRecordThemeCheckActivity.this.finish();
            }
        });
    }
    public void onBackPressed() {TeamRecordThemeCheckActivity.this.finish();}
}
