package teampj.java.dsm.hubgaruandroid.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import teampj.java.dsm.hubgaruandroid.R;
import teampj.java.dsm.hubgaruandroid.Model.TeamRequestItem;
import teampj.java.dsm.hubgaruandroid.Holder.TeamRequestViewHolder;

/**
 * Created by dsm2016 on 2017-09-04.
 */

public class TeamRequestAdapter extends RecyclerView.Adapter<TeamRequestViewHolder>{
    ArrayList<TeamRequestItem> items = new ArrayList<TeamRequestItem>();

    public void add(String nName, String time, String rName, String rDesc) {
        TeamRequestItem newItem = new TeamRequestItem(nName, time, rName, rDesc);
        items.add(newItem);
        notifyDataSetChanged();
    }

    @Override
    public TeamRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.content_team_item_request, parent, false);
        return new TeamRequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TeamRequestViewHolder holder, int position) {
        TeamRequestItem item = items.get(position);
        holder.nameView.setText(item.getNickName());
        holder.timeView.setText(item.getDateTime());
        holder.rNameView.setText(item.getRequestName());
        holder.rDescView.setText(item.getRequestDesc());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}