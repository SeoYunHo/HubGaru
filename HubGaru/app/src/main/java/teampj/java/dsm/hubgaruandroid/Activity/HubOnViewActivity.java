package teampj.java.dsm.hubgaruandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-09-20.
 */

public class HubOnViewActivity extends AppCompatActivity {

    private Button teamMainBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hub_on_view);

        teamMainBtn = (Button) findViewById(R.id.toMainBtn);
        teamMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_to_team = new Intent(getApplicationContext(), TeamMainActivity.class);
                startActivity(main_to_team);
            }
        });
    }
}
