package teampj.java.dsm.hubgaruandroid.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by dsm2016 on 2017-09-04.
 */

public class TeamRecoardThemeActivity extends AppCompatActivity {

    private ImageView play;
    private ImageView pause;
    private ImageView stop;
    private TextView wyan;
    private TextView ittaga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_theme);

        play = (ImageView)findViewById(R.id.tr_play_btn);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        pause = (ImageView)findViewById(R.id.tr_pause_btn);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        stop = (ImageView)findViewById(R.id.tr_stop_btn);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        wyan = (TextView) findViewById(R.id.wyanwyan);
        wyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamRecoardThemeActivity.this.finish();
            }
        });

        ittaga = (TextView)findViewById(R.id.ittaga);
        ittaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamRecoardThemeActivity.this.finish();
            }
        });


    }
    public void onBackPressed() {TeamRecoardThemeActivity.this.finish();}
}
