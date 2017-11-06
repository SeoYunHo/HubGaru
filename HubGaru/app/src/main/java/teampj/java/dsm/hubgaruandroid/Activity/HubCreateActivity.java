package teampj.java.dsm.hubgaruandroid.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teampj.java.dsm.hubgaruandroid.Network.Service.HubService;
import teampj.java.dsm.hubgaruandroid.R;

public class HubCreateActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    private EditText newHubName;
    private ImageView newHubImage;
    private LinearLayout recordBtn;
    private static TextView fileView;
    private TextView getFileBtn;
    private Button cancleBtn;
    private Button createBtn;

    private Uri HubImage;

    private File imageFile;
    private static File musicFile;

    private byte[] imageBytes;
    private static byte[] musicBytes;

    private String fileURL;
    private String pfileURL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hub);

        newHubName = (EditText)findViewById(R.id.new_hub_name);

        newHubImage = (ImageView) findViewById(R.id.new_hub_image);
        newHubImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("*/*");
                startActivityForResult(i, 2);
            }
        });

        recordBtn = (LinearLayout)findViewById(R.id.record_theme_gogo);
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ch_to_rt = new Intent(HubCreateActivity.this, TeamRecordThemeActivity.class);
                startActivity(ch_to_rt);
            }
        });

        fileView = (TextView) findViewById(R.id.fileNameView);

        getFileBtn = (TextView) findViewById(R.id.rec_file_btn);
        getFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("audio/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 1);
            }
        });
        cancleBtn = (Button)findViewById(R.id.cancle_hub_btn) ;
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { HubCreateActivity.this.finish(); }
        });

        createBtn = (Button)findViewById(R.id.create_hub_btn);
        createBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String garuID = "907";
                    final String hubName = newHubName.getText().toString();
                    final String music = musicFile.getName();
                    final String teamPic = imageFile.getName();

                    Toast.makeText(getApplicationContext(),"파일 업로드중...",Toast.LENGTH_SHORT).show();
                    final Uri file = Uri.fromFile(musicFile);
                    String filename = musicFile.getName();
                    StorageReference filepath = storageReference.child("Hub").child("Songs").child(file.getLastPathSegment());
                    filepath.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(),"파일업로드 완료!",Toast.LENGTH_SHORT).show();
                            fileURL = taskSnapshot.getDownloadUrl().toString();

                            Uri pfile = HubImage;
                            String pfilename = musicFile.getName();
                            StorageReference pfilepath = storageReference.child("Hub").child("Images").child(pfile.getLastPathSegment());
                            pfilepath.putFile(pfile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    pfileURL = taskSnapshot.getDownloadUrl().toString();

                                    HubService.getRetrofit(getApplicationContext())
                                            .makeHub(garuID, hubName, fileURL+".mp3", pfileURL+".jpg","2017-11-02")
                                            .enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                    if(response.code() == 201){
                                                        HubCreateActivity.this.finish();
                                                    } else if (response.code() == 500) {
                                                        Toast.makeText(getApplicationContext(),"허브업로드실패",Toast.LENGTH_SHORT).show();
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
        });
    }

    public static void setMusicfile_R(){
        musicFile = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/recorded.mp4");
        musicBytes = fileTobyte(musicFile);
        fileView.setText(musicFile.getName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            HubImage = uri;
            try{
                //Bitmap bm = BitmapFactory.decodeFile(path, bmOptions);
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imageFile = saveBitmap(bm, uri.getPath());
                imageBytes = fileTobyte(imageFile);

                newHubImage.setImageBitmap(bm);
            }
            catch(Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"!",Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == 1 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            musicFile = new File(uri.getPath());
            musicBytes = fileTobyte(musicFile);
            fileView.setText(musicFile.getName());
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

    private static byte[] fileTobyte(File file){
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}