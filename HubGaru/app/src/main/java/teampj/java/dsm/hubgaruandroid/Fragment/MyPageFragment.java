package teampj.java.dsm.hubgaruandroid.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Dialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import teampj.java.dsm.hubgaruandroid.Adapter.HubListAdapter;
import teampj.java.dsm.hubgaruandroid.Model.HubItem;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-08-22.
 */

public class MyPageFragment extends Fragment{

    private ImageView profilePic;
    private ImageButton editBtn;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private TextView nameText, positionText, emailText;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_page, container, false);

        editBtn = (ImageButton) view.findViewById(R.id.infoEditBtn);
        profilePic = (ImageView) view.findViewById(R.id.profilePic);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.myPageRecyclerView);
        recyclerView.hasFixedSize();

        nameText = (TextView) view.findViewById(R.id.nameText);
        positionText = (TextView) view.findViewById(R.id.positionText);
        emailText = (TextView) view.findViewById(R.id.emailText);

        Glide.with(getActivity())
                .load("https://i.pinimg.com/736x/e3/b5/3a/e3b53a8f65f9567014a7079435038946--adorable-animals-adorable-kittens.jpg")
                .apply(RequestOptions.bitmapTransform(new CircleCrop(getActivity())))
                .into(profilePic);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new HubListAdapter(getActivity(), getList()));

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivity(intent);
            }
        });


        final String name, position, email;

        name = nameText.getText().toString();
        position = positionText.getText().toString();
        email = emailText.getText().toString();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog infoEditDialog = new InfoEditDialog(getContext(), name, position, email);
                infoEditDialog.show();
            }
        });



        return view;
    }

    public ArrayList<HubItem> getList() {
        ArrayList<HubItem> list = new ArrayList();

        list.add(new HubItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSLFIXdoiuMqHW2Firazadnushw_TzEccmtnUjGEsBVxPWI76gWlA", "yy-mm-dd", "title1"));
        list.add(new HubItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQsdzJWVjLxYgo-oZWbT08C3vJEPtM1pRRSGkyvYzNMHiQGxANQ", "yy-mm-dd", "title2"));

        return list;
    }

    class InfoEditDialog extends Dialog {

        private Context context;
        private TextInputEditText positionEText, emailEText, nameEText;
        private Button yesBtn, noBtn;
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

            nameEText = (TextInputEditText) findViewById(R.id.nameEditText);
            positionEText = (TextInputEditText) findViewById(R.id.positionEditText);
            emailEText = (TextInputEditText) findViewById(R.id.emailEditText);
            yesBtn = (Button) findViewById(R.id.yesBtn);
            noBtn = (Button) findViewById(R.id.noBtn);

            String tmp[] = new String[3];

            nameEText.setHint(name);
            positionEText.setHint(position);
            emailEText.setHint(email);

            yesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    name = nameEText.getText().toString();
                    position = positionEText.getText().toString();
                    email = emailText.getText().toString();

                    if(nameEText == null)
                        nameText.setText(nameEText.getHint());
                    else
                        nameText.setText(name);

                    if(positionEText == null)
                        positionText.setText(positionEText.getHint());
                    else
                        positionText.setText(position);

                    if(emailEText == null)
                        emailText.setText(email);
                    else
                        emailText.setText(email);

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

        public InfoEditDialog(@NonNull Context context, String position, String email, String name) {
            super(context);
            this.context = context;
            this.name = name;
            this.position = position;
            this.email = email;
        }
    }
}
