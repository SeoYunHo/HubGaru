package teampj.java.dsm.hubgaruandroid.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teampj.java.dsm.hubgaruandroid.Adapter.CommentAdapter;
import teampj.java.dsm.hubgaruandroid.Model.CommentItem;
import teampj.java.dsm.hubgaruandroid.Network.Service.HubService;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-09-20.
 */

public class HubOnViewActivity extends AppCompatActivity {

    static boolean likeBtnStatus = false;
    static boolean musicStatus = false;
    private Button teamMainBtn, enterBtn;
    private EditText commentText;
    private ImageButton likeBtn, statusBtn;
    private TextView likeNum;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;

    private int TEAMCODE;
    private String teamId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hub_on_view);

        Intent intent = getIntent();
        TEAMCODE = intent.getIntExtra("TEAMCODE",0);
        teamId = intent.getStringExtra("id");
        Toast.makeText(getApplicationContext(), teamId.toString(), Toast.LENGTH_LONG).show();

        likeNum = (TextView) findViewById(R.id.thumbsNum);
        statusBtn = (ImageButton) findViewById(R.id.play_pauseBtn);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        likeBtn = (ImageButton) findViewById(R.id.likeBtn);
        mediaPlayer = MediaPlayer.create(this,R.raw.seecha);
        mediaPlayer.setLooping(true);

        recyclerView = (RecyclerView) findViewById(R.id.commentRecycler);
        recyclerView.hasFixedSize();
        manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new CommentAdapter(setCommentItem(), getApplicationContext()));

        teamMainBtn = (Button) findViewById(R.id.toMainBtn);
        teamMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_to_team = new Intent(getApplicationContext(), TeamMainActivity.class);
                main_to_team.putExtra("TEAMCODE",TEAMCODE);
                startActivity(main_to_team);
            }
        });

        class MyThread extends Thread {
            @Override
            public void run() {
                while (musicStatus) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                }
            }
        }

        statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicStatus = false) {
                    musicStatus = true;
                } else {
                    musicStatus = false;
                }
                if (mediaPlayer.isPlaying()) {
                    statusBtn.setImageResource(R.drawable.play);
                    mediaPlayer.stop();
                    try {
                        mediaPlayer.prepare();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    new MyThread().start();
                    mediaPlayer.seekTo(0);
                } else {
                    mediaPlayer.start();
                    statusBtn.setImageResource(R.drawable.pause);
                    Thread();
                }
            }
        });

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser)
                    mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                TODO : buttons onClick post

                int tmpNum;
                String tmpSNum;

                if(likeBtnStatus == false) {
                    likeBtnStatus = true;
                    likeBtn.setImageResource(R.drawable.thumb1);
                    tmpNum = Integer.parseInt(likeNum.getText().toString());
                    tmpNum++;
                    plusLike();
                    tmpSNum = String.valueOf(tmpNum);
                    likeNum.setText(tmpSNum);
                }
                else {
                    likeBtnStatus = false;
                    likeBtn.setImageResource(R.drawable.thumb3);
                    tmpNum = Integer.parseInt(likeNum.getText().toString());
                    tmpNum--;
                    minusLike();
                    tmpSNum = String.valueOf(tmpNum);
                    likeNum.setText(tmpSNum);
                }
            }
        });

//        enterBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String tmp = commentText.getText().toString();
//
//            }
//        });

    }

    public int getLike() {

        int likeNum = 0;

        HubService.getRetrofit(getApplicationContext()).getLike(teamId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        return likeNum;
    }

    public void plusLike() {
        HubService.getRetrofit(getApplicationContext()).plus(teamId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 201) {
                    Toast.makeText(getApplicationContext(), "Sucess", Toast.LENGTH_LONG).show();
                } else if(response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void minusLike() {
        HubService.getRetrofit(getApplicationContext()).minus(teamId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 201) {
                    Toast.makeText(getApplicationContext(), "Sucess", Toast.LENGTH_LONG).show();
                } else if(response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Thread() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer.isPlaying()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    seekBar.setPressed(mediaPlayer.getCurrentPosition());
                }
            }
        };
    }

    public List<CommentItem> setCommentItem() {
        List<CommentItem> commentItems = new ArrayList<>();

        CommentItem item1 = new CommentItem();
        CommentItem item2 = new CommentItem();
        CommentItem item3 = new CommentItem();
        CommentItem item4 = new CommentItem();

        item1.setName("DONG HEE");
        item1.setComment("Good enough");
        item1.setEditDate("today");
        item1.setProfilePic("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTf-a5nSmSFfub2_k06nnM4PpgXZLapp-qhCcS9HABklUdux10uvQ");
        commentItems.add(item1);

        item2.setName("SU MIN");
        item2.setComment("Worst");
        item2.setEditDate("yesterday");
        item2.setProfilePic("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTf-a5nSmSFfub2_k06nnM4PpgXZLapp-qhCcS9HABklUdux10uvQ");
        commentItems.add(item2);

        item3.setName("SOMEONE");
        item3.setComment("Lovely");
        item3.setEditDate("tomorrow");
        item3.setProfilePic("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTf-a5nSmSFfub2_k06nnM4PpgXZLapp-qhCcS9HABklUdux10uvQ");
        commentItems.add(item3);

        item4.setName("SOMEONE2");
        item4.setComment("Lovely");
        item4.setEditDate("tomorrow");
        item4.setProfilePic("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTf-a5nSmSFfub2_k06nnM4PpgXZLapp-qhCcS9HABklUdux10uvQ");
        commentItems.add(item4);

        return commentItems;
    }
}
