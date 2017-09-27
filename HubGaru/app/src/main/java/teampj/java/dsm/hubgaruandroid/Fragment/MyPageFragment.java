package teampj.java.dsm.hubgaruandroid.Fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Dialog;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import teampj.java.dsm.hubgaruandroid.Adapter.HubListAdapter;
import teampj.java.dsm.hubgaruandroid.Model.HubItem;
import teampj.java.dsm.hubgaruandroid.Model.UserInfoItem;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-08-22.
 */

public class MyPageFragment extends Fragment{

    // name, position, email

    private ImageView profilePic;
    private ImageButton editBtn;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private TextView[] tInfos;
    Uri uri;

    final int REQ_CODE = 100;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_page, container, false);

        editBtn = (ImageButton) view.findViewById(R.id.infoEditBtn);
        profilePic = (ImageView) view.findViewById(R.id.profilePic);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.myPageRecyclerView);
        recyclerView.hasFixedSize();

        tInfos = new TextView[3];
        tInfos[0] = (TextView) view.findViewById(R.id.nameText);
        tInfos[1] = (TextView) view.findViewById(R.id.positionText);
        tInfos[2] = (TextView) view.findViewById(R.id.emailText);

        Glide.with(getActivity())
                .load("https://i.pinimg.com/736x/e3/b5/3a/e3b53a8f65f9567014a7079435038946--adorable-animals-adorable-kittens.jpg")
                .apply(RequestOptions.bitmapTransform(new CircleCrop(getActivity())))
                .into(profilePic);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new HubListAdapter(getActivity(), getList()));

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTakeAlbumAction();
            }
        });

        final String name, position, email;

        name = tInfos[0].getText().toString();
        position = tInfos[1].getText().toString();
        email = tInfos[2].getText().toString();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog infoEditDialog = new InfoEditDialog(getContext(), getInfo());
                infoEditDialog.show();
            }
        });

        return view;
    }

    private void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //임시로 사용할 파일의 경로 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQ_CODE);
    }

    private void doTakeAlbumAction() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQ_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        uri  = data.getData();

    }

    /*public int exifOrientationToDegrees(int exifOrientation) {
            if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
                return 90;
            } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
                return 180;
            } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
                return 270;
            }
            return 0;
        }


        private void SelectPic() {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent, REQ_CODE);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            Uri uri  = data.getData();
            String imagePath = getRealPathFromURI(uri);
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int exifDegree = exifOrientationToDegrees(exifOrientation);

            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
            profilePic.setImageBitmap(rotate(bitmap, exifDegree));//이미
        }

        public String getRealPathFromURI(Uri contentUri) {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);

        }

        public Bitmap rotate(Bitmap src, float degree) {

            // Matrix 객체 생성
            Matrix matrix = new Matrix();
            // 회전 각도 셋팅
            matrix.postRotate(degree);
            // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
            return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                    src.getHeight(), matrix, true);
        }*/
    public UserInfoItem getInfo() {

        //TODO: Retrofit으로 userInfo 가져오기

        UserInfoItem info = new UserInfoItem();

        info.setEmail("***@***.***");
        info.setName("DongHee");
        info.setPosition("director");

        return info;
    }

    public ArrayList<HubItem> getList() {

        //TODO: Retrofit으로 hubItems 가져오기

        ArrayList<HubItem> list = new ArrayList();

        list.add(new HubItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSLFIXdoiuMqHW2Firazadnushw_TzEccmtnUjGEsBVxPWI76gWlA", "yy-mm-dd", "title1"));
        list.add(new HubItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQsdzJWVjLxYgo-oZWbT08C3vJEPtM1pRRSGkyvYzNMHiQGxANQ", "yy-mm-dd", "title2"));

        return list;
    }

    class InfoEditDialog extends Dialog {

        private Context context;
        private TextInputEditText[] editTexts;
        private Button yesBtn, noBtn;
        private UserInfoItem item;
        String name, position, email;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //뒷배경 흐리게
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            layoutParams.dimAmount = 0.8f;
            getWindow().setAttributes(layoutParams);

            setContentView(R.layout.edit_my_info);

            editTexts = new TextInputEditText[3];
            editTexts[0] = (TextInputEditText) findViewById(R.id.nameEditText);
            editTexts[1] = (TextInputEditText) findViewById(R.id.positionEditText);
            editTexts[2] = (TextInputEditText) findViewById(R.id.emailEditText);

            yesBtn = (Button) findViewById(R.id.yesBtn);
            noBtn = (Button) findViewById(R.id.noBtn);

            getInfo();

            editTexts[0].setHint(item.getName());
            editTexts[1].setHint(item.getEmail());
            editTexts[2].setHint(item.getPosition());

            yesBtn.setOnClickListener(new View.OnClickListener() {

                //TODO: yesBtn에서는 서버로 POST

                @Override
                public void onClick(View v) {
                    for(int i = 0; i < editTexts.length; i++) {
                        tInfos[i].setText(editTexts[i].getText().toString());
                    }

                    dismiss();
                }
            });

            noBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        public InfoEditDialog (Context context, UserInfoItem item) {
            super(context);

            this.context = context;
            this.item = item;
        }
    }
}
