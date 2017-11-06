package teampj.java.dsm.hubgaruandroid.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teampj.java.dsm.hubgaruandroid.Model.GaruItem;
import teampj.java.dsm.hubgaruandroid.Network.Service.HubService;
import teampj.java.dsm.hubgaruandroid.R;

public class GaruCreateActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    private EditText newGaruName;
    private EditText newGaruIntro;
    private ImageView newGaruImage;
    private Button cancleBtn;
    private Button createBtn;

    private Uri GaruImage;
    private File imageFile;

    String imageURL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_garu);

        newGaruName = (EditText)findViewById(R.id.new_garu_name);
        newGaruIntro = (EditText)findViewById(R.id.new_garu_intro);
        newGaruImage = (ImageView)findViewById(R.id.new_garu_iimage);

        Button cancleBtn = (Button) findViewById(R.id.cancle_garu_btn);
        Button createBtn = (Button) findViewById(R.id.create_garu_btn);

        newGaruImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(i, 2);
            }
        });

        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { GaruCreateActivity.this.finish(); }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uid = TabLayoutActivity.getId();
                final String garuName = newGaruName.getText().toString();
                final String garuIntro = newGaruIntro.getText().toString();
                final String teamPic = imageFile.getName();

                Toast.makeText(getApplicationContext(),"파일 업로드중...",Toast.LENGTH_SHORT).show();
                final Uri pfile = GaruImage;
                String filename = imageFile.getName();
                StorageReference filepath = storageReference.child("Garu").child("Songs").child(pfile.getLastPathSegment());
                filepath.putFile(pfile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "파일업로드 완료!", Toast.LENGTH_SHORT).show();
                        imageURL = taskSnapshot.getDownloadUrl().toString();


                        HubService.getRetrofit(getApplicationContext())
                                .makeGaru(uid, garuName, garuIntro, "trash", imageURL)
                                .enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.code() == 201) {
                                            GaruCreateActivity.this.finish();
                                        } else if (response.code() == 500) {
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                    }
                                });
                    }
                });
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            GaruImage = uri;
            try{
                //Bitmap bm = BitmapFactory.decodeFile(path, bmOptions);
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imageFile = saveBitmap(bm, uri.getPath());
                newGaruImage.setImageBitmap(bm);
            }
            catch(Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File saveBitmap(Bitmap bitmap, String path) {
        File file = null;
        if (bitmap != null) {
            file = new File(path);
            try {
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(path); //here is set your file path where you want to save or also here you can set file object directly

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream); // bitmap is your Bitmap instance, if you want to compress it you can compress reduce percentage
                    // PNG is a lossless format, the compression factor (100) is ignored
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
