package teampj.java.dsm.hubgaruandroid.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import teampj.java.dsm.hubgaruandroid.Holder.TeamMemberViewHolder;
import teampj.java.dsm.hubgaruandroid.Model.TeamMemberItem;
import teampj.java.dsm.hubgaruandroid.R;

public class TeamMemberAdapter extends RecyclerView.Adapter<TeamMemberViewHolder>{
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ArrayList<TeamMemberItem> items = new ArrayList<TeamMemberItem>();

    public void add(TeamMemberItem newItem) {
        items.add(newItem);
        notifyDataSetChanged();
    }

    @Override
    public TeamMemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.content_team_page_item_member, parent, false);
        return new TeamMemberViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TeamMemberViewHolder holder, int position) {
        TeamMemberItem item = items.get(position);
        holder.iconView.setText(item.getmIcon());
        holder.nameView.setText(item.getmName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
