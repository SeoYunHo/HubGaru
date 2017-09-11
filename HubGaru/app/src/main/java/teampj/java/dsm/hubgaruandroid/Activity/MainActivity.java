package teampj.java.dsm.hubgaruandroid.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import teampj.java.dsm.hubgaruandroid.R;

public class MainActivity extends AppCompatActivity {

    private Button team_main_button;
    private Button team_create_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        team_create_button = (Button) findViewById(R.id.team_create_button);
        team_create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_to_tc = new Intent(MainActivity.this, TeamCreateActivity.class);
                startActivity(main_to_tc);
                MainActivity.this.finish();
            }
        });

        team_main_button = (Button) findViewById(R.id.team_main_button);
        team_main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_to_team = new Intent(MainActivity.this, TeamMainActivity.class);
                startActivity(main_to_team);
                MainActivity.this.finish();
            }
        });
    }
}
