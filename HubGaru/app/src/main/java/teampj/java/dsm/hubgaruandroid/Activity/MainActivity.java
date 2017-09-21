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

    }
}
