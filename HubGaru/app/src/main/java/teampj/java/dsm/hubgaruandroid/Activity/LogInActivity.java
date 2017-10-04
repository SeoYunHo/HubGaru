package teampj.java.dsm.hubgaruandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teampj.java.dsm.hubgaruandroid.Network.Service.HubService;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-10-03.
 */

public class LogInActivity extends AppCompatActivity {
    ImageView backgrountImg, loginIcon;
    Button loginBtn;
    TextInputEditText idText, pwText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        idText = (TextInputEditText) findViewById(R.id.idText);
        pwText = (TextInputEditText) findViewById(R.id.pwText);
        loginIcon = (ImageView) findViewById(R.id.loginIcon);
        backgrountImg = (ImageView) findViewById(R.id.backgroundImage);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        backgrountImg.setAlpha(127);

        Glide.with(this).load(R.drawable.splash_icon)
                .apply(RequestOptions.bitmapTransform(new CircleCrop(this)))
                .into(loginIcon);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sId = idText.getText().toString();
                String sPw = pwText.getText().toString();

                HubService.getRetrofit(getApplicationContext()).singIn(sId, sPw).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 201) {
                            Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogInActivity.this, TabLayoutActivity.class);
                            startActivity(intent);
                            finish();
                        } else if(response.code() == 204) {
                            Toast.makeText(getApplicationContext(), "Fail" + response.code(), Toast.LENGTH_SHORT).show();
                        } else  {
                            Toast.makeText(getApplicationContext(), "Fail" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

    }
}