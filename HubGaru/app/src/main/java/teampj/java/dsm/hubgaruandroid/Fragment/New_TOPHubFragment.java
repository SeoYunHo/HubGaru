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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import teampj.java.dsm.hubgaruandroid.Adapter.HubListAdapter;
import teampj.java.dsm.hubgaruandroid.Model.HubItem;
import teampj.java.dsm.hubgaruandroid.Network.Service.HubService;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-08-22.
 */

public class New_TOPHubFragment extends Fragment {
    Context context;

    private MultiSnapRecyclerView newRecyclerView, topRecyclerView;
    private RecyclerView.LayoutManager topLayoutManager,newLayoutManager;
    private  ArrayList<HubItem> arrayList;
    private HubListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_top_hub, container, false);

        newLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        topLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        topRecyclerView = (MultiSnapRecyclerView) view.findViewById(R.id.topHub_Recycler);
        topRecyclerView.hasFixedSize();
        topRecyclerView.setLayoutManager(newLayoutManager);
        getTopHubs();

        newRecyclerView = (MultiSnapRecyclerView) view.findViewById(R.id.newHub_Recycler);
        newRecyclerView.hasFixedSize();
        newRecyclerView.setLayoutManager(topLayoutManager);
        getNewHubs();
        /*
        * 허브 JSONArray로 받아오기
        * Set 데이터
        */
//        HubService.getRetrofit(view.getContext()).getHub().enqueue(new Callback<JSONArray>() {
//            @Override
//            public void onResponse(Call<JSONArray> call, Response<JSONArray> response) {
//                String tmp = response.toString();
//                Toast.makeText(getContext(), tmp, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<JSONArray> call, Throwable t) {
//                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
//                Log.d(t.toString(), "errorThrown");
//            }
//        });

        return view;
    }

    public void getNewHubs() {
        HubService.getRetrofit(getContext()).getHub().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonObject = response.body().getAsJsonArray("hub");
                JsonArray jsonElements = jsonObject.getAsJsonArray();
                arrayList = getArrayList(jsonElements);
                adapter = new HubListAdapter(getContext(), arrayList);
                newRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void getTopHubs() {
        HubService.getRetrofit(getContext()).getHub().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonObject = response.body().getAsJsonArray("hub");
                JsonArray jsonElements = jsonObject.getAsJsonArray();
                arrayList = getArrayList(jsonElements);
                adapter = new HubListAdapter(getContext(), arrayList);
                topRecyclerView.setAdapter(adapter);
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

    /*public List<HubItem> getList() {
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
    }*/
}
