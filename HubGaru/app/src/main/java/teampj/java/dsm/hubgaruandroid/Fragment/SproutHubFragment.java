package teampj.java.dsm.hubgaruandroid.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import teampj.java.dsm.hubgaruandroid.Activity.MainActivity;
import teampj.java.dsm.hubgaruandroid.Activity.TeamCreateActivity;
import teampj.java.dsm.hubgaruandroid.Adapter.HubListVerticalAdapter;
import teampj.java.dsm.hubgaruandroid.Model.HubItem;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-08-22.
 */

public class SproutHubFragment extends Fragment {

    private Button createTeamBtn;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sprout_hub, container, false);

        EditText searchText = (EditText) view.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchText.setTextColor(ContextCompat.getColor(getActivity(), R.color.fontColor));
        searchText.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.fontColor));

        recyclerView = (RecyclerView) view.findViewById(R.id.myhubRecyclerView);
        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new HubListVerticalAdapter(getContext(), getList()));


        createTeamBtn = (Button) view.findViewById(R.id.team_create_button);
        createTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_to_tc = new Intent(getActivity(), TeamCreateActivity.class);
                startActivity(main_to_tc);
            }
        });

        return view;
    }

    public List<HubItem> getList() {
        List<HubItem> hubItems = new ArrayList<>();
        HubItem hubItem1 = new HubItem();
        HubItem hubItem2 = new HubItem();
        HubItem hubItem3 = new HubItem();

        hubItem1.setDate("yy-mm-dd");
        hubItem1.setSongTitle("title");
        hubItem1.setPicUri("https://i.pinimg.com/736x/86/26/f1/8626f17d9d099df368ac7fcc95c7faec--baby-girl-nursery-themes-nursery-decor.jpg");
        hubItems.add(hubItem1);

        hubItem2.setDate("yy-mm-dd");
        hubItem2.setSongTitle("2nd");
        hubItem2.setPicUri("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRW4KWg10FKWM_Yoeejy51TwuXWyB4ME7fpKIGWSXG_4pTZdoyB");
        hubItems.add(hubItem2);

        hubItem3.setDate("yy-mm-dd");
        hubItem3.setSongTitle("3rd");
        hubItem3.setPicUri("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTf-a5nSmSFfub2_k06nnM4PpgXZLapp-qhCcS9HABklUdux10uvQ");
        hubItems.add(hubItem3);

        return hubItems;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem searchViewMenuItem = menu.findItem(R.id.main_menu_action_search);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchViewMenuItem);
        int searchImgId = android.support.v7.appcompat.R.id.search_button; // I used the explicit layout ID of searchview's ImageView
        ImageView v = (ImageView) mSearchView.findViewById(searchImgId);
        v.setImageResource(R.drawable.search);
        super.onPrepareOptionsMenu(menu);
    }
}
