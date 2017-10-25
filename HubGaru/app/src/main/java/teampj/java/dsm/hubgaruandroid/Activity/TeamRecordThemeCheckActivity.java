package teampj.java.dsm.hubgaruandroid.Activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by dsm2016 on 2017-09-23.
 */

public class TeamRecordThemeCheckActivity extends AppCompatActivity {

    static final String RECORDED_FILE = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/recorded.mp4";

    MediaPlayer player;
    int playbackPosition;

    private ImageView play;
    private ImageView pause;
    private ImageView stop;
    private SeekBar seekbar;
    private TextView zomanim;
    private TextView okay;
    private TextView recTimeView;

    boolean recPlaying;
    Timer timer;
    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_theme_check);
         player = new MediaPlayer();
        try{
            player.setDataSource(TeamRecordThemeCheckActivity.this, Uri.parse(RECORDED_FILE));
            player.prepare();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"음악파일 로딩실패..",Toast.LENGTH_SHORT).show();
        }

        recTimeView = (TextView)findViewById(R.id.rec_check_time);

        seekbar = (SeekBar)findViewById(R.id.rec_seekbar);

        /////////////////////////////////////////////////////////
        seekbar.setVisibility(View.GONE);
        //별 기능 못하는거 같으니까 일단 안 보이게 해두자/////////

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(seekBar.getProgress());
            }s
        });

        play = (ImageView)findViewById(R.id.trc_play_btn);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"재생!",Toast.LENGTH_SHORT).show();
                player.start();

                final long startTime = System.currentTimeMillis();

                class SeekBarThread extends Thread {
                    @Override
                    public void run() {
                        while(player.isPlaying()){
                            seekbar.setProgress(player.getCurrentPosition());
                        }
                    }
                }

                timer = new Timer();
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        TeamRecordThemeCheckActivity.this.runOnUiThread(new Runnable() {
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
                                if(!player.isPlaying()){
                                    timerTask.cancel();
                                }
                                recTimeView.setText(timeString);
                            }
                        });
                    }
                };
                timer.schedule(timerTask, 1000, 1000);
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
                if(timerTask != null){
                    timerTask.cancel();
                }
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
                HubCreateActivity.setMusicfile_R();
                TeamRecordThemeCheckActivity.this.finish();
            }
        });
    }
    public void onBackPressed() {TeamRecordThemeCheckActivity.this.finish();}
}
