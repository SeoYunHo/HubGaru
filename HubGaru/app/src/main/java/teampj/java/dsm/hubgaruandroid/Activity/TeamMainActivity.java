package teampj.java.dsm.hubgaruandroid.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

import teampj.java.dsm.hubgaruandroid.Adapter.TeamMemberAdapter;
import teampj.java.dsm.hubgaruandroid.Model.TeamChatItem;
import teampj.java.dsm.hubgaruandroid.Model.TeamMemberItem;
import teampj.java.dsm.hubgaruandroid.Model.TeamRequestItem;
import teampj.java.dsm.hubgaruandroid.R;
import teampj.java.dsm.hubgaruandroid.Adapter.TeamChatAdapter;
import teampj.java.dsm.hubgaruandroid.Adapter.TeamRequestAdapter;

public class TeamMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    private DrawerLayout teamMainDrawer;
    private FrameLayout teamMainContainer;
    private NavigationView teamMainNavView;
    private RecyclerView contentView;
    private RecyclerView.LayoutManager contentLayoutManager;

    private LinearLayout teamPage;
    private RecyclerView hubView;
    private RecyclerView memberView;

    private LinearLayout chattingBar;
    private EditText chatEditText;
    private Button imageSendBtn;
    private Button chatSendBtn;

    private Button drawerBtn;
    private Button setttingBtn;

    private LinearLayout newRequestActionBar;
    private Button newRequestBtn;

    private TeamRequestAdapter R_adapter = new TeamRequestAdapter();
    private TeamChatAdapter C_adapter = new TeamChatAdapter();
    private TeamMemberAdapter M_adapter = new TeamMemberAdapter();

    private int TEAMCODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_main);

        Intent intent = getIntent();
        TEAMCODE = intent.getIntExtra("TEAMCODE",0);

        teamMainDrawer = (DrawerLayout) findViewById(R.id.team_main_drawer_layout);
        teamMainContainer = (FrameLayout)findViewById(R.id.team_content_layout);
        teamMainNavView = (NavigationView)findViewById(R.id.team_main_nav_view);
        contentView = (RecyclerView) findViewById(R.id.team_content_view);
        contentLayoutManager = new LinearLayoutManager(this);
        contentView.setLayoutManager(contentLayoutManager);

        teamPage = (LinearLayout) findViewById(R.id.team_page);

        hubView = (RecyclerView) findViewById(R.id.team_hub_view);
        hubView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));

        memberView = (RecyclerView) findViewById(R.id.team_member_view);
        memberView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
        memberView.setAdapter(M_adapter);

        chattingBar = (LinearLayout) findViewById(R.id.team_chat_bar);
        chatEditText = (EditText) findViewById(R.id.chatText);
        imageSendBtn = (Button) findViewById(R.id.sendImageBtn);
        imageSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, 2);
            }
        });
        chatSendBtn = (Button) findViewById(R.id.sendChatBtn);
        chatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                TeamChatItem chatItem = new TeamChatItem("조치원",chatEditText.getText().toString(), calendar.getTime().toString().substring(0,24));
                databaseReference.child(String.valueOf(TEAMCODE)).child("Chat").push().setValue(chatItem);
            }
        });

        drawerBtn = (Button)findViewById(R.id.drawer_btn);
        drawerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_to_setting = new Intent(TeamMainActivity.this, TeamSettingActivity.class);
                main_to_setting.putExtra("TEAMCODE",TEAMCODE);
                startActivity(main_to_setting);
            }
        });
        setttingBtn = (Button)findViewById(R.id.setting_btn);
        setttingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamMainDrawer.openDrawer(Gravity.LEFT);
            }
        });

        newRequestActionBar = (LinearLayout) findViewById(R.id.team_action_bar);
        newRequestBtn = (Button) findViewById(R.id.button1);
        newRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_to_request = new Intent(TeamMainActivity.this, TeamRequestActivity.class);
                main_to_request.putExtra("TEAMCODE",TEAMCODE);
                startActivity(main_to_request);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, teamMainDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        teamMainDrawer.setDrawerListener(toggle);
        toggle.syncState();

        teamMainNavView.setNavigationItemSelectedListener(this);

        SetRealTimeDataBase();
        chattingBar.setVisibility(View.GONE);
        newRequestActionBar.setVisibility(View.GONE);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here
        int id = item.getItemId();

        teamPage.setVisibility(View.GONE);
        chattingBar.setVisibility(View.GONE);
        newRequestActionBar.setVisibility(View.GONE);

        if (id == R.id.nav_alam) {

        } else if (id == R.id.nav_request) {
            newRequestActionBar.setVisibility(View.VISIBLE);
            contentView.setAdapter(R_adapter);

        } else if (id == R.id.nav_chat) {
            chattingBar.setVisibility(View.VISIBLE);
            contentView.setAdapter(C_adapter);

        } else if (id == R.id.nav_invite) {

        }

        teamMainDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent team_to_main = new Intent(TeamMainActivity.this, TabLayoutActivity.class);
        startActivity(team_to_main);
        TeamMainActivity.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == RESULT_OK){
            final Uri uri = data.getData();
            final StorageReference filepath = storageReference.child("Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(TeamMainActivity.this, "업로드 완료", Toast.LENGTH_SHORT).show();
                    Calendar calendar = Calendar.getInstance();
                    TeamChatItem chatItem = new TeamChatItem("조치원",filepath.getName(), calendar.getTime().toString().substring(0,24),true);
                    databaseReference.child(String.valueOf(TEAMCODE)).child("Chat").push().setValue(chatItem);
                }
            });
        }
    }

    public void SetRealTimeDataBase(){
        databaseReference.child(String.valueOf(TEAMCODE)).child("Request_s").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TeamRequestItem requestItem = dataSnapshot.getValue(TeamRequestItem.class);
                R_adapter.add(requestItem);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
        databaseReference.child(String.valueOf(TEAMCODE)).child("Chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final TeamChatItem chatItem = dataSnapshot.getValue(TeamChatItem.class);
                if(chatItem.getIsPhoto()){
                    String childName = "Photos/"+chatItem.getDescStr();
                    storageReference.child(childName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            try{
                                URL url = new URL(uri.toString());
                                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                                connection.setRequestMethod("GET");
                                connection.connect();
                                InputStream input = connection.getInputStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(input);
                                C_adapter.add(chatItem, bitmap);
                                //Bitmap bitmap;
                                //bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                                //C_adapter.add(chatItem, bitmap);
                            }catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(TeamMainActivity.this, "실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    C_adapter.add(chatItem);
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
        databaseReference.child(String.valueOf(TEAMCODE)).child("Member").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TeamMemberItem memberItem = dataSnapshot.getValue(TeamMemberItem.class);
                M_adapter.add(memberItem);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
}
