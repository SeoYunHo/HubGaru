package teampj.java.dsm.hubgaruandroid.Activity;

import java.io.File;
import java.util.Calendar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import teampj.java.dsm.hubgaruandroid.Model.TeamRequestItem;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by dsm2016 on 2017-08-31.
 */

public class TeamRequestActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private EditText requestName;
    private Spinner requestKind;
    private EditText requestInfo;
    private LinearLayout fileGogo;
    private Button requestGoButton;
    private Button requestAwayButton;

    private ImageView fileIcon;
    private TextView fileName;

    private File newFile = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_new_request);

        requestName = (EditText)findViewById(R.id.new_request_name);
        requestKind = (Spinner)findViewById(R.id.new_reaquest_kind);
        requestInfo = (EditText)findViewById(R.id.new_request_info);

        ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.requestkind,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestKind.setAdapter(adapter);

        fileIcon = (ImageView)findViewById(R.id.new_file_icon);
        fileName = (TextView)findViewById(R.id.new_file_name);

        fileGogo = (LinearLayout)findViewById(R.id.filegogo);
        fileGogo.setOnClickListener(new View.OnClickListener() {
            File a = null;
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("*/*");
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(i.createChooser(i,"Open"),0);
            }
        });

        requestGoButton = (Button) findViewById(R.id.request_gogo);
        requestGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                Uri file = Uri.fromFile(newFile);
                TeamRequestItem teamRequestItem = new TeamRequestItem("김지수",calendar.getTime().toString().substring(0,22),requestName.getText().toString(), requestInfo.getText().toString(),file);
                databaseReference.child("Request").push().setValue(teamRequestItem);
                TeamRequestActivity.this.finish();
            }
        });

        requestAwayButton = (Button) findViewById(R.id.request_goaway);
        requestAwayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamRequestActivity.this.finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedFile = data.getData();
        newFile = new File(selectedFile.getPath());
        fileName.setText(newFile.getName());
    }

    public void onBackPressed() {TeamRequestActivity.this.finish();}
}
