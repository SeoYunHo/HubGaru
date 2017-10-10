package teampj.java.dsm.hubgaruandroid.Adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import teampj.java.dsm.hubgaruandroid.Holder.TeamChatViewHolder;
import teampj.java.dsm.hubgaruandroid.Model.TeamChatItem;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by dsm2016 on 2017-09-02.
 */

public class TeamChatAdapter extends RecyclerView.Adapter<TeamChatViewHolder>{
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ArrayList<TeamChatItem> items = new ArrayList<TeamChatItem>();
    ArrayList<Bitmap> images = new ArrayList<Bitmap>();

    public void add(TeamChatItem newItem) {
        items.add(newItem);
        notifyDataSetChanged();
    }
    public void add(TeamChatItem newItem, Bitmap bitmap){
        items.add(newItem);
        images.add(bitmap);
        notifyDataSetChanged();
    }

    @Override
    public TeamChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.content_team_item_chat, parent, false);
        return new TeamChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TeamChatViewHolder holder, int position) {
        TeamChatItem item = items.get(position);
        if(item.getIsPhoto()){
            holder.nameView.setText(item.getNameStr());
            holder.imageView.setMaxWidth(100);
            holder.imageView.setMaxHeight(100);
            holder.imageView.setImageBitmap(images.get(position));
            holder.timeView.setText(item.getDateTime());
        }
        else{
            holder.nameView.setText(item.getNameStr());
            holder.descView.setText(item.getDescStr());
            holder.timeView.setText(item.getDateTime());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
