package teampj.java.dsm.hubgaruandroid.Activity;

import android.content.Intent;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import teampj.java.dsm.hubgaruandroid.Model.TeamChatItem;
import teampj.java.dsm.hubgaruandroid.Model.TeamRequestItem;
import teampj.java.dsm.hubgaruandroid.R;
import teampj.java.dsm.hubgaruandroid.Adapter.TeamChatAdapter;
import teampj.java.dsm.hubgaruandroid.Adapter.TeamRequestAdapter;

public class TeamMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private DrawerLayout teamMainDrawer;
    private FrameLayout teamMainContainer;
    private NavigationView teamMainNavView;
    private RecyclerView contentView;
    private RecyclerView.LayoutManager contentLayoutManager;

    private LinearLayout chattingBar;
    private EditText chatEditText;
    private Button chatSendBtn;

    private Button drawerBtn;
    private Button setttingBtn;

    private LinearLayout newRequestActionBar;
    private Button newRequestBtn;

    private TeamRequestAdapter R_adapter = new TeamRequestAdapter();
    private TeamChatAdapter C_adapter = new TeamChatAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_main);

        teamMainDrawer = (DrawerLayout) findViewById(R.id.team_main_drawer_layout);
        teamMainContainer = (FrameLayout)findViewById(R.id.team_content_layout);
        teamMainNavView = (NavigationView)findViewById(R.id.team_main_nav_view);
        contentView = (RecyclerView) findViewById(R.id.team_content_view);

        chattingBar = (LinearLayout) findViewById(R.id.team_chat_bar);
        chatEditText = (EditText) findViewById(R.id.chatText);
        chatSendBtn = (Button) findViewById(R.id.sendChatBtn);
        chatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                TeamChatItem chatItem = new TeamChatItem("김지수",chatEditText.getText().toString(), calendar.getTime().toString().substring(0,24));
                databaseReference.child("Chat").push().setValue(chatItem);
            }
        });

        drawerBtn = (Button)findViewById(R.id.drawer_btn);
        drawerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeamMainActivity.this, TeamSettingActivity.class));
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
                startActivity(new Intent(TeamMainActivity.this, TeamRequestActivity.class));
            }
        });

        contentLayoutManager = new LinearLayoutManager(this);
        contentView.setLayoutManager(contentLayoutManager);

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

    public void SetRealTimeDataBase(){
        databaseReference.child("Request_s").addChildEventListener(new ChildEventListener() {
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
        databaseReference.child("Chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TeamChatItem chatItem = dataSnapshot.getValue(TeamChatItem.class);
                C_adapter.add(chatItem);
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
