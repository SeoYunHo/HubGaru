package teampj.java.dsm.hubgaruandroid.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teampj.java.dsm.hubgaruandroid.Activity.GaruCreateActivity;
import teampj.java.dsm.hubgaruandroid.Adapter.GaruAdapter;
import teampj.java.dsm.hubgaruandroid.Adapter.HubListVerticalAdapter;
import teampj.java.dsm.hubgaruandroid.Model.GaruItem;
import teampj.java.dsm.hubgaruandroid.Model.HubItem;
import teampj.java.dsm.hubgaruandroid.Network.Service.HubService;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-08-22.
 */

public class GaruFragment extends Fragment {

    private FloatingActionButton createTeamBtn;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private SearchView searchView;
    private GaruAdapter adapter;
    private  ArrayList<GaruItem> arrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.garu_hub, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.myhubRecyclerView);
        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(new GaruAdapter(getContext(), getList()));
        getGaru();

        HubService.getRetrofit(getContext()).getGaru().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                JsonArray array = response.body().getAsJsonArray("garu");
                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        createTeamBtn = (FloatingActionButton) view.findViewById(R.id.team_create_button);
        createTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_to_tc = new Intent(getActivity(), GaruCreateActivity.class);
                startActivity(main_to_tc);
            }
        });

        return view;
    }

    public void getGaru() {
        HubService.getRetrofit(getContext()).getGaru().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonObject = response.body().getAsJsonArray("garu");
                JsonArray jsonElements = jsonObject.getAsJsonArray();
                arrayList = getArrayList(jsonElements);
                adapter = new GaruAdapter(getContext(), arrayList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

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

  /*  public List<GaruItem> getList() {

        List<GaruItem> items = new ArrayList<>();

        GaruItem garuItem1 = new GaruItem();
        GaruItem garuItem2 = new GaruItem();
        GaruItem garuItem3 = new GaruItem();

        garuItem1.setLeader("DONGHEE");
        garuItem1.setTeamIntro("I love java");
        garuItem1.setTeamName("SUPER");
        garuItem1.setTeamPic("https://i.pinimg.com/736x/86/26/f1/8626f17d9d099df368ac7fcc95c7faec--baby-girl-nursery-themes-nursery-decor.jpg");
        items.add(garuItem1);

        garuItem2.setLeader("DONGHEE");
        garuItem2.setTeamIntro("I love java");
        garuItem2.setTeamName("SUPER");
        garuItem2.setTeamPic("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRW4KWg10FKWM_Yoeejy51TwuXWyB4ME7fpKIGWSXG_4pTZdoyB");
        items.add(garuItem2);

        garuItem3.setLeader("DONGHEE");
        garuItem3.setTeamIntro("I love java");
        garuItem3.setTeamName("SUPER");
        garuItem3.setTeamPic("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTf-a5nSmSFfub2_k06nnM4PpgXZLapp-qhCcS9HABklUdux10uvQ");
        items.add(garuItem3);

        return items;
    }*/

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