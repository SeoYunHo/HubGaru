package teampj.java.dsm.hubgaruandroid.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

    ImageView profilePic;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_page, container, false);

        profilePic = (ImageView) view.findViewById(R.id.profilePic);
        recyclerView = (RecyclerView) view.findViewById(R.id.myPageRecyclerView);
        recyclerView.hasFixedSize();
        manager = new LinearLayoutManager(getActivity());

        Glide.with(getActivity())
                .load("https://i.pinimg.com/736x/e3/b5/3a/e3b53a8f65f9567014a7079435038946--adorable-animals-adorable-kittens.jpg")
                .apply(RequestOptions.bitmapTransform(new CircleCrop(getActivity())))
                .into(profilePic);

        recyclerView.setAdapter(new HubListAdapter(getActivity(), getList()));

        return view;
    }

    public ArrayList<HubItem> getList() {
        ArrayList<HubItem> list = new ArrayList();

        list.add(new HubItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSLFIXdoiuMqHW2Firazadnushw_TzEccmtnUjGEsBVxPWI76gWlA", "yy-mm-dd", "title1"));
        list.add(new HubItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQsdzJWVjLxYgo-oZWbT08C3vJEPtM1pRRSGkyvYzNMHiQGxANQ", "yy-mm-dd", "title2"));

        return list;
    }
}
