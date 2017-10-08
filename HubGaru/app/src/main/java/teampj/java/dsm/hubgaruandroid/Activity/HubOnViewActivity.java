package teampj.java.dsm.hubgaruandroid.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.LocaleDisplayNames;
import android.icu.text.SimpleDateFormat;
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
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teampj.java.dsm.hubgaruandroid.Adapter.CommentAdapter;
import teampj.java.dsm.hubgaruandroid.Model.CommentItem;
import teampj.java.dsm.hubgaruandroid.Model.UserInfoItem;
import teampj.java.dsm.hubgaruandroid.Network.Service.HubService;
import teampj.java.dsm.hubgaruandroid.R;

public class HubOnViewActivity extends AppCompatActivity {

    static int hubLike;
    static boolean likeBtnStatus = false;
    static boolean musicStatus = false;
    private Button teamMainBtn, enterBtn;
    private EditText commentText;
    private ImageButton likeBtn, statusBtn;
    private TextView likeNum, teamNameInfo, editDatInfo, songNameInfo;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private ArrayList<UserInfoItem> userInfos;
    private ArrayList<CommentItem> commentItems;

    private int TEAMCODE;
    private String hubId, songTitle, teamName, editDate, sCommentText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hub_on_view);

        Intent intent = getIntent();
        TEAMCODE = intent.getIntExtra("TEAMCODE",0);

        likeNum = (TextView) findViewById(R.id.thumbsNum);
        statusBtn = (ImageButton) findViewById(R.id.play_pauseBtn);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        likeBtn = (ImageButton) findViewById(R.id.likeBtn);
        recyclerView = (RecyclerView) findViewById(R.id.commentRecycler);
        teamMainBtn = (Button) findViewById(R.id.toMainBtn);
        teamNameInfo = (TextView) findViewById(R.id.teamName);
        editDatInfo = (TextView) findViewById(R.id.editDate);
        songNameInfo = (TextView) findViewById(R.id.songTitle);
        mediaPlayer = MediaPlayer.create(this,R.raw.seecha);
        enterBtn = (Button) findViewById(R.id.enterBtn);
        commentText = (EditText) findViewById(R.id.commentEditText);

//        infoSet
        hubId = intent.getStringExtra("id");
        songTitle = intent.getStringExtra("songTItle");
        teamName = intent.getStringExtra("teamName");
        editDate = intent.getStringExtra("date");

        teamNameInfo.setText(teamName);
        editDatInfo.setText(editDate);
        songNameInfo.setText(songTitle);

        mediaPlayer.setLooping(true);

        recyclerView.hasFixedSize();
        manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        getComments();

        getLike();
        likeNum.setText(String.valueOf(hubLike));

//        teamMainBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent main_to_team = new Intent(getApplicationContext(), TeamMainActivity.class);
//                main_to_team.putExtra("TEAMCODE",TEAMCODE);
//                startActivity(main_to_team);
//            }
//        });

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sCommentText = commentText.getText().toString();

                postComment(sCommentText);
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

//        playBtn onClick
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
//                    new MyThread().start();
                    mediaPlayer.seekTo(0);
                } else {
                    mediaPlayer.start();
                    statusBtn.setImageResource(R.drawable.pause);
//                    Thread();
                }
            }
        });

//        likeBtn onClick
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

        class MyThread extends Thread {
            @Override
            public void run() {
                while (musicStatus) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                }
            }
        }
    }

    public void postComment(final String comment) {
        Date todayDate = Calendar.getInstance().getTime();
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("YYYY-MM-dd");
        final String todayString = formatter.format(todayDate);
        Log.d(hubId + ", " + comment + ", " + TabLayoutActivity.getId() + ", " + todayString, "parameterCheck");
        HubService.getRetrofit(getApplicationContext())
                .addComment(hubId, comment, TabLayoutActivity.getId(), todayString)
                .enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 201) {
                    commentText.setText(null);
                    commentItems.add(new CommentItem(TabLayoutActivity.getPicture(), TabLayoutActivity.getName(), comment, todayString));
                    adapter.notifyDataSetChanged();
                }
                else if(response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "fail " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getComments() {
        HubService.getRetrofit(getApplicationContext())
                .getComments(hubId)
                .enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == 201) {
                    JsonArray jsonObject = response.body().getAsJsonArray("comment");
                    JsonArray jsonElements = jsonObject.getAsJsonArray();
                    commentItems = getArrayList(jsonElements);
                    adapter = new CommentAdapter(commentItems, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } else if(response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                Log.d(t.toString(), "commentErrKey");
            }
        });
    }

    public ArrayList<CommentItem> getArrayList(JsonArray jsonElements) {
        ArrayList<CommentItem> arrayList = new ArrayList<>();
        ArrayList<String> userIds = new ArrayList<>();

        for(int i = 0; i < jsonElements.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonElements.get(i);

            String userId = jsonObject.getAsJsonPrimitive("id").getAsString();
            userIds.add(userId);
        }

        getUserInfos(userIds);
        Log.d(userInfos.toString(), "userInfoCheck");

//        for(int i = 0; i < jsonElements.size(); i++) {
//            JsonObject jsonObject = (JsonObject) jsonElements.get(i);
//
//            String date = jsonObject.getAsJsonPrimitive("date").getAsString();
//            String comment = jsonObject.getAsJsonPrimitive("comment").getAsString();
//
//            String name = userInfos.get(i).getName();
//            String pic = userInfos.get(i).getPicture();
//
//            arrayList.add(new CommentItem(pic, name, comment, date));
//        }
        return arrayList;
    }

    public void getUserInfos(ArrayList<String> ids) {
        for(int i = 0; i < ids.size(); i++) {
            HubService.getRetrofit(getApplicationContext()).getInfo(ids.get(i)).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Toast.makeText(getApplicationContext(), "Sucess", Toast.LENGTH_SHORT).show();
                    JsonObject element = response.body().getAsJsonObject("user");

                    String picture = "http://www.freeiconspng.com/uploads/person-icon--icon-search-engine-3.png";
                    String name = element.getAsJsonPrimitive("name").getAsString();

//                    Log.d(picture + ", " + name, "detailinfoCheck");
                    userInfos.add(new UserInfoItem(name, picture));
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT);
                }
            });
        }
    }

    public void getLike() {

        HubService.getRetrofit(getApplicationContext()).getLike(hubId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonPrimitive object = response.body().getAsJsonPrimitive("good");
                Log.d("key", object.toString());
                hubLike = object.getAsInt();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void plusLike() {
        HubService.getRetrofit(getApplicationContext()).plus(hubId).enqueue(new Callback<Void>() {
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
        HubService.getRetrofit(getApplicationContext()).minus(hubId).enqueue(new Callback<Void>() {
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

//    public void Thread() {
//        Runnable task = new Runnable() {
//            @Override
//            public void run() {
//                while (mediaPlayer.isPlaying()) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
////                    seekBar.setPressed(mediaPlayer.getCurrentPosition());
//                }
//            }
//        };
//    }
}
