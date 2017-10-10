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

            arrayList.add(new HubItem(garuId, hubId, img, "date", name, song));
        }
        return arrayList;
    }
}
