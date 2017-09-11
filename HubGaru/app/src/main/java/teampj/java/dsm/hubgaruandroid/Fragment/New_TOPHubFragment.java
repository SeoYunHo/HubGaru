package teampj.java.dsm.hubgaruandroid.Fragment;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;

import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.ArrayList;
import java.util.List;

import teampj.java.dsm.hubgaruandroid.Adapter.HubListAdapter;
import teampj.java.dsm.hubgaruandroid.Model.HubItem;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-08-22.
 */

public class New_TOPHubFragment extends Fragment {
Context context;

    private MultiSnapRecyclerView topRecyclerView;
    private MultiSnapRecyclerView newRecyclerView;
    private RecyclerView.LayoutManager newLayoutManager;
    private RecyclerView.LayoutManager topLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_top_hub, container, false);

        EditText searchText = (EditText) view.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchText.setTextColor(ContextCompat.getColor(getActivity(), R.color.fontColor));
        searchText.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.fontColor));

        newLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        topLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        topRecyclerView = (MultiSnapRecyclerView) view.findViewById(R.id.topHub_Recycler);
        topRecyclerView.hasFixedSize();
        topRecyclerView.setLayoutManager(newLayoutManager);
        topRecyclerView.setAdapter(new HubListAdapter(getContext(), getList()));

        newRecyclerView = (MultiSnapRecyclerView) view.findViewById(R.id.newHub_Recycler);
        newRecyclerView.hasFixedSize();
        newRecyclerView.setLayoutManager(topLayoutManager);
        newRecyclerView.setAdapter(new HubListAdapter(getContext(), getList()));

        return view;
    }

    public List<HubItem> getList() {
        List<HubItem> hubItems = new ArrayList<>();
        HubItem hubItem1 = new HubItem();
        HubItem hubItem2 = new HubItem();
        HubItem hubItem3 = new HubItem();

        hubItem1.setDate("yy-mm-dd");
        hubItem1.setSongTitle("title");
        hubItem1.setPicSrc("");
        hubItems.add(hubItem1);

        hubItem2.setDate("yy-mm-dd");
        hubItem2.setSongTitle("2nd");
        hubItem2.setPicSrc("");
        hubItems.add(hubItem2);

        hubItem3.setDate("yy-mm-dd");
        hubItem3.setSongTitle("3rd");
        hubItem3.setPicSrc("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==\n");
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
