package teampj.java.dsm.hubgaruandroid.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by dsm2016 on 2017-09-04.
 */

public class TeamRecordThemeActivity extends AppCompatActivity {

    static final String RECORDED_FILE = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/recorded.mp4";

    MediaRecorder recorder;

    private ImageView play;
    private ImageView pause;
    private ImageView stop;
    public TextView timeView;
    private TextView wyan;
    private TextView ittaga;

    boolean recPlaying;

    Timer timer;
    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_theme);

        timeView = (TextView)findViewById(R.id.rec_time);

        play = (ImageView)findViewById(R.id.tr_play_btn);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recorder != null){
                    recorder.stop();
                    recorder.release();
                    recorder = null;
                }
                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                recorder.setOutputFile(RECORDED_FILE);
                try{
                    Toast.makeText(getApplicationContext(),"자 자 부르자",Toast.LENGTH_LONG).show();
                    recorder.prepare();
                    recorder.start();
                    recPlaying = true;

                    final long startTime = System.currentTimeMillis();

                    timer = new Timer();
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            TeamRecordThemeActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    long countTIme = System.currentTimeMillis() - startTime;
                                    String mintime = String.valueOf(countTIme/60000);
                                    String timeString;
                                    if(((countTIme%60000)/1000) < 10){
                                        timeString = String.valueOf("0"+mintime + ":0" +(countTIme%60000)/1000);
                                    }else{
                                        timeString = String.valueOf("0"+mintime + ":" +(countTIme%60000)/1000);
                                    }
                                    timeView.setText(timeString);
                                }
                            });
                        }
                    };
                    timer.schedule(timerTask, 1000, 1000);
                }catch (Exception e){ }
            }
        });

        pause = (ImageView)findViewById(R.id.tr_pause_btn);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recPlaying){
                    //recorder.pause();
                    recPlaying = false;
                    Toast.makeText(getApplicationContext(),"스탑스탑!",Toast.LENGTH_SHORT).show();
                }
                else {
                    //recorder.resume();
                    recPlaying = true;
                    Toast.makeText(getApplicationContext(),"계속하라",Toast.LENGTH_SHORT).show();
                }
            }
        });

        stop = (ImageView)findViewById(R.id.tr_stop_btn);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recorder == null) return;

                recorder.stop();
                recorder.release();
                recorder = null;

                if(timerTask != null){
                    timerTask.cancel();
                }

                Toast.makeText(getApplicationContext(),"여기까지",Toast.LENGTH_LONG).show();
            }
        });

        wyan = (TextView) findViewById(R.id.wyanwyan);
        wyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rt_to_rtc = new Intent(TeamRecordThemeActivity.this, TeamRecordThemeCheckActivity.class);
                startActivity(rt_to_rtc);
                TeamRecordThemeActivity.this.finish();
            }
        });

        ittaga = (TextView)findViewById(R.id.ittaga);
        ittaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamRecordThemeActivity.this.finish();
            }
        });


    }
    public void onBackPressed() {TeamRecordThemeActivity.this.finish();}
}