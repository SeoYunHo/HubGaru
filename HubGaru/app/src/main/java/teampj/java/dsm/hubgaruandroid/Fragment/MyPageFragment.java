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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teampj.java.dsm.hubgaruandroid.Activity.TabLayoutActivity;
import teampj.java.dsm.hubgaruandroid.Adapter.GaruAdapter;
import teampj.java.dsm.hubgaruandroid.Adapter.GaruHorizontalAdapter;
import teampj.java.dsm.hubgaruandroid.Adapter.HubListAdapter;
import teampj.java.dsm.hubgaruandroid.Model.GaruItem;
import teampj.java.dsm.hubgaruandroid.Model.HubItem;
import teampj.java.dsm.hubgaruandroid.Model.UserInfoItem;
import teampj.java.dsm.hubgaruandroid.Network.Service.HubService;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-08-22.
 */

public class MyPageFragment extends Fragment{

    // name, position, email

    private ImageView profilePic;
//    private ImageButton editBtn;
    private RecyclerView recyclerView;
    private GaruHorizontalAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private  ArrayList<GaruItem> arrayList;
    private TextView nameText, positionText, phoneNumText;
    Uri uri;

    final int REQ_CODE = 100;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_page, container, false);

//        editBtn = (ImageButton) view.findViewById(R.id.infoEditBtn);
        nameText = (TextView) view.findViewById(R.id.nameText);
        positionText = (TextView) view.findViewById(R.id.positionText);
        phoneNumText = (TextView) view.findViewById(R.id.phoneText);
        profilePic = (ImageView) view.findViewById(R.id.profilePic);

        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.myPageRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(manager);

        getGaru();

        Glide.with(getActivity())
                .load("https://i.pinimg.com/736x/e3/b5/3a/e3b53a8f65f9567014a7079435038946--adorable-animals-adorable-kittens.jpg")
                .apply(RequestOptions.bitmapTransform(new CircleCrop(getActivity())))
                .into(profilePic);

        nameText.setText(TabLayoutActivity.getName());
        positionText.setText(TabLayoutActivity.getPart());
        phoneNumText.setText(TabLayoutActivity.getPhone());

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTakeAlbumAction();
            }
        });

//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dialog infoEditDialog = new InfoEditDialog(getContext());
//                infoEditDialog.show();
//            }
//        });

        return view;
    }

    public void getGaru() {
        HubService.getRetrofit(getContext()).getGaru().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonObject = response.body().getAsJsonArray("garu");
                JsonArray jsonElements = jsonObject.getAsJsonArray();
                arrayList = getArrayList(jsonElements);
                adapter = new GaruHorizontalAdapter(getContext(), arrayList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

//    public void getGaru() {
//        HubService.getRetrofit(getContext()).getMyGaru(TabLayoutActivity.getId()).enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                JsonArray jsonObject = response.body().getAsJsonArray("garu");
//                JsonArray jsonElements = jsonObject.getAsJsonArray();
//                arrayList = getArrayList(jsonElements);
//                adapter = new GaruHorizontalAdapter(getContext(), arrayList);
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//
//            }
//        });
//    }

    public ArrayList<GaruItem> getArrayList(JsonArray jsonElements) {
        ArrayList<GaruItem> arrayList = new ArrayList<>();

        for(int i = 0; i < jsonElements.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonElements.get(i);

            String garuId = jsonObject.getAsJsonPrimitive("garuId").getAsString();
            String leaderId = jsonObject.getAsJsonPrimitive("leaderId").getAsString();
            String name = jsonObject.getAsJsonPrimitive("name").getAsString();
            String intro = jsonObject.getAsJsonPrimitive("intro").getAsString();
            String img = jsonObject.getAsJsonPrimitive("img").getAsString();

            arrayList.add(new GaruItem(name, garuId, img, intro, leaderId));
        }
        return arrayList;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        uri  = data.getData();
        String realPath = getRealPath(getContext(), uri);
        try {
            Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
            profilePic.setImageBitmap(image_bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        post해서 리스폰스로 사진을 받는다 그리고 글라이드에 추가해서 동그라미 모양으로!
    }

    /*class InfoEditDialog extends Dialog {

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

            yesBtn = (Button) findViewById(R.id.yesBtn);
            noBtn = (Button) findViewById(R.id.noBtn);

            yesBtn.setOnClickListener(new View.OnClickListener() {

                //TODO: yesBtn에서는 서버로 POST

                @Override
                public void onClick(View v) {

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

        public InfoEditDialog (Context context) {
            super(context);

            this.context = context;
        }
    }*/

    public String getRealPath(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }
    }

    private void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQ_CODE);
    }
}
