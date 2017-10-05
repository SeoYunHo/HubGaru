package teampj.java.dsm.hubgaruandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teampj.java.dsm.hubgaruandroid.Network.Service.HubService;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-10-03.
 */

public class SignUpActivity extends AppCompatActivity {

    private Button signup;
    TextInputEditText id, pw, userIntro, name, position, phoneNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        signup = (Button) findViewById(R.id.signupBtn);
        id = (TextInputEditText) findViewById(R.id.sIdText);
        pw = (TextInputEditText) findViewById(R.id.sPwText);
        userIntro = (TextInputEditText) findViewById(R.id.sUserIntro);
        name = (TextInputEditText) findViewById(R.id.sNameText);
        position = (TextInputEditText) findViewById(R.id.sPositionText);
        phoneNum = (TextInputEditText) findViewById(R.id.sPhoneNum);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sId = id.getText().toString();
                String sPw = pw.getText().toString();
                String sName = name.getText().toString();
                String sIntro = userIntro.getText().toString();
                String sPosition = position.getText().toString();
                String sPhoneNum = phoneNum.getText().toString();

                SignUp(sId, sPw, sName, sIntro, sPosition, sPhoneNum);

            }
        });

    }

    public void SignUp(String id, String pw, String name, String intro, String position, String phoneNum) {
        HubService.getRetrofit(getApplicationContext())
                .singUp(id, pw, name, intro, position, phoneNum)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
    }
}
