package teampj.java.dsm.hubgaruandroid.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import teampj.java.dsm.hubgaruandroid.Model.HubItem;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-08-22.
 */

public class HubListAdapter extends RecyclerView.Adapter<HubListAdapter.ViewHolder> {

    List<HubItem> items;
    Context context;

    public HubListAdapter(Context context, List<HubItem> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hub_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url = items.get(position).getPicSrc();
        Glide.with(context).load(url).into(holder.profilePic);
        holder.dateText.setText(items.get(position).getDate());
        holder.titleText.setText(items.get(position).getSongTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView dateText;
        ImageView profilePic;
        public ViewHolder(View itemView) {
            super(itemView);

            titleText = (TextView) itemView.findViewById(R.id.songTitle);
            dateText = (TextView) itemView.findViewById(R.id.date);
            profilePic = (ImageView) itemView.findViewById(R.id.profilePic);
        }
    }
}
