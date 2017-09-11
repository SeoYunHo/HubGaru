package teampj.java.dsm.hubgaruandroid.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import teampj.java.dsm.hubgaruandroid.R;
import teampj.java.dsm.hubgaruandroid.Model.TeamChatItem;
import teampj.java.dsm.hubgaruandroid.Holder.TeamChatViewHolder;

/**
 * Created by dsm2016 on 2017-09-02.
 */

public class TeamChatAdapter extends RecyclerView.Adapter<TeamChatViewHolder>{
    ArrayList<TeamChatItem> items = new ArrayList<TeamChatItem>();

    public void add(String name, String desc) {
        TeamChatItem newItem = new TeamChatItem(name, desc);
        items.add(newItem);
        notifyDataSetChanged();
    }

    @Override
    public TeamChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.content_team_item_chat, parent, false);
        return new TeamChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TeamChatViewHolder holder, int position) {
        TeamChatItem item = items.get(position);
        holder.nameView.setText(item.getName());
        holder.descView.setText(item.getDesc());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
