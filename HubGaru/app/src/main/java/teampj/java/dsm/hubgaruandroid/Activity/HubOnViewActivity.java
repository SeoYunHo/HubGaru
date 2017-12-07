package teampj.java.dsm.hubgaruandroid.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
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
import teampj.java.dsm.hubgaruandroid.Model.CommentItem2;
import teampj.java.dsm.hubgaruandroid.Model.UserInfoItem;
import teampj.java.dsm.hubgaruandroid.Network.Service.HubService;
import teampj.java.dsm.hubgaruandroid.R;

public class HubOnViewActivity extends AppCompatActivity {

    static int hubLike;
    static boolean likeBtnStatus = false;
    private Button teamMainBtn, enterBtn;
    private EditText commentText;
    private ImageButton likeBtn, statusBtn;
    private TextView likeNum, teamNameInfo, editDatInfo, songNameInfo;
    private MediaPlayer mediaPlayer;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private ArrayList<CommentItem> commentItems;

    private int TEAMCODE;
    private String hubId, songTitle, teamName, editDate, sCommentText, sSongUrl;

    final String baseurl = "http://52.15.75.60:8080/file/";
    Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hub_on_view);

        Intent intent = getIntent();
        TEAMCODE = intent.getIntExtra("TEAMCODE",0);

        likeNum = (TextView) findViewById(R.id.thumbsNum);
        statusBtn = (ImageButton) findViewById(R.id.play_pauseBtn);
        likeBtn = (ImageButton) findViewById(R.id.likeBtn);
        recyclerView = (RecyclerView) findViewById(R.id.commentRecycler);
        teamMainBtn = (Button) findViewById(R.id.toMainBtn);
        teamNameInfo = (TextView) findViewById(R.id.teamName);
        editDatInfo = (TextView) findViewById(R.id.editDate);
        songNameInfo = (TextView) findViewById(R.id.songTitle);
        enterBtn = (Button) findViewById(R.id.enterBtn);
        commentText = (EditText) findViewById(R.id.commentEditText);

//        infoSet
        hubId = intent.getStringExtra("id");
        songTitle = intent.getStringExtra("songTItle");
        teamName = intent.getStringExtra("teamName");
        editDate = intent.getStringExtra("date");
//        sSongUrl = intent.getStringExtra("file");
        sSongUrl = songTitle;
        commentItems = new ArrayList<CommentItem>();

        teamNameInfo.setText(teamName);
        editDatInfo.setText(editDate);
        songNameInfo.setText(songTitle);

        uri = Uri.parse(baseurl + sSongUrl);
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(getApplicationContext(), uri);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();

        recyclerView.hasFixedSize();
        manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        test();

        getLike();

        likeNum.setText(String.valueOf(hubLike));

        teamMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_to_team = new Intent(getApplicationContext(), TeamMainActivity.class);
                main_to_team.putExtra("TEAMCODE",TEAMCODE);
                startActivity(main_to_team);
            }
        });

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sCommentText = commentText.getText().toString();
                postComment(sCommentText);
            }
        });

//        playBtn onClick
        statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    statusBtn.setImageResource(R.drawable.play);
                } else {
                    try {
                        uri = Uri.parse(baseurl + sSongUrl);
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(getApplicationContext(), uri);
                        mediaPlayer.prepare();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                    statusBtn.setImageResource(R.drawable.pause);
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
    }

    public void postComment(final String comment) {
        Date todayDate = Calendar.getInstance().getTime();
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("YYYY-MM-DD");
        final String todayString = formatter.format(todayDate);

        HubService.getRetrofit(getApplicationContext())
                .addComment(hubId, comment, TabLayoutActivity.getId(), todayString)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 201) {
                            commentText.setText(null);
                            commentItems.add(new CommentItem(comment, TabLayoutActivity.getId(), todayString));
                            Toast.makeText(getApplicationContext(), "sucess", Toast.LENGTH_SHORT).show();
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

    public void test(){
        HubService.getRetrofit(getApplicationContext()).getComments(hubId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonArray = response.body().get("comment").getAsJsonArray();
                Log.d("response code", response.code()+"");
                Log.d("response body", response.body()+"");
                for(int i=0; i<jsonArray.size(); i++) {
                    CommentItem commentItem = new CommentItem();
                    JsonObject result = jsonArray.get(i).getAsJsonObject();
                    commentItem.setId(result.get("id").getAsString());
                    commentItem.setComment(result.get("comment").getAsString());
                    commentItem.setDate(result.get("date").getAsString());

                    commentItems.add(i, commentItem);
                }
                adapter = new CommentAdapter(commentItems, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
