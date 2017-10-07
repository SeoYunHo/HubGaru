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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teampj.java.dsm.hubgaruandroid.Activity.MainActivity;
import teampj.java.dsm.hubgaruandroid.Activity.TabLayoutActivity;
import teampj.java.dsm.hubgaruandroid.Activity.TeamMainActivity;
import teampj.java.dsm.hubgaruandroid.Adapter.HubListAdapter;
import teampj.java.dsm.hubgaruandroid.Adapter.HubListVerticalAdapter;
import teampj.java.dsm.hubgaruandroid.Model.HubItem;
import teampj.java.dsm.hubgaruandroid.Network.Service.HubService;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-08-22.
 */

public class MyHubFragment extends Fragment{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private HubListVerticalAdapter adapter;
    private  ArrayList<HubItem> arrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_hub, container, false);

        EditText searchText = (EditText) view.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchText.setTextColor(ContextCompat.getColor(getActivity(), R.color.fontColor));
        searchText.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.fontColor));

        recyclerView = (RecyclerView) view.findViewById(R.id.myhubRecyclerView);
        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(manager);
//        getHubs();
        getNewHubs();

        return view;
    }

    public void getNewHubs() {
        HubService.getRetrofit(getContext()).getHub().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonObject = response.body().getAsJsonArray("hub");
                JsonArray jsonElements = jsonObject.getAsJsonArray();
                arrayList = getArrayList(jsonElements);
                adapter = new HubListVerticalAdapter(getContext(), arrayList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void getHubs() {
        HubService.getRetrofit(getContext())
                .getMyHub(TabLayoutActivity.getId())
                .enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonObject = response.body().getAsJsonArray("hub");
                JsonArray jsonElements = jsonObject.getAsJsonArray();
                arrayList = getArrayList(jsonElements);
                adapter = new HubListVerticalAdapter(getContext(), arrayList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public ArrayList<HubItem> getArrayList(JsonArray jsonElements) {
        ArrayList<HubItem> arrayList = new ArrayList<>();

        for(int i = 0; i < jsonElements.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonElements.get(i);
            String hubId = jsonObject.getAsJsonPrimitive("hubId").getAsString();
            String garuId = jsonObject.getAsJsonPrimitive("garuId").getAsString();
            String name = jsonObject.getAsJsonPrimitive("name").getAsString();
            String img = jsonObject.getAsJsonPrimitive("img").getAsString();
            String song = jsonObject.getAsJsonPrimitive("file").getAsString();

            arrayList.add(new HubItem(hubId, img, "date", name, song));
        }
        return arrayList;
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
