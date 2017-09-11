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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import teampj.java.dsm.hubgaruandroid.R;
import teampj.java.dsm.hubgaruandroid.Adapter.TeamChatAdapter;
import teampj.java.dsm.hubgaruandroid.Adapter.TeamRequestAdapter;

public class TeamMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout teamMainDrawer;
    private FrameLayout teamMainContainer;
    private NavigationView teamMainNavView;
    private RecyclerView contentView;
    private RecyclerView.LayoutManager contentLayoutManager;
    private Button newRequestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_main);

        teamMainDrawer = (DrawerLayout) findViewById(R.id.team_main_drawer_layout);
        teamMainContainer = (FrameLayout)findViewById(R.id.team_content_layout);
        teamMainNavView = (NavigationView)findViewById(R.id.team_main_nav_view);
        contentView = (RecyclerView) findViewById(R.id.team_content_view);
        newRequestBtn = (Button) findViewById(R.id.button1);

        contentLayoutManager = new LinearLayoutManager(this);
        contentView.setLayoutManager(contentLayoutManager);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, teamMainDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        teamMainDrawer.setDrawerListener(toggle);
        toggle.syncState();

        teamMainNavView.setNavigationItemSelectedListener(this);

        newRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeamMainActivity.this, TeamRequestActivity.class));
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here
        int id = item.getItemId();

        if (id == R.id.nav_alam) {

        } else if (id == R.id.nav_request) {
            TeamRequestAdapter adapter = new TeamRequestAdapter();
            adapter.add("김지수","17-9-4 | 11:34AM","요청샘플","아무래도 좋으니까 빨리 다음걸로");
            contentView.setAdapter(adapter);

        } else if (id == R.id.nav_chat) {
            TeamChatAdapter adapter = new TeamChatAdapter();
            adapter.add("김지수","아 이제 체팅 했네");
            adapter.add("김지수","어느 세월에 다 하나");
            adapter.add("김지수","내가 그러긴 했지만\n솔직히 일주일을 좀 아니지 않나...");

            contentView.setAdapter(adapter);
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
}
