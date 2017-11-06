package teampj.java.dsm.hubgaruandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import teampj.java.dsm.hubgaruandroid.Activity.HubOnViewActivity;
import teampj.java.dsm.hubgaruandroid.Model.HubItem;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-08-22.
 */

public class HubListVerticalAdapter extends RecyclerView.Adapter<HubListVerticalAdapter.ViewHolder> {

    ArrayList<HubItem> items;
    Context context;

    public HubListVerticalAdapter(Context context, ArrayList<HubItem> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_hub_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String url = items.get(position).getPicUri();
        String baseUrl = "";
        Glide.with(context).load(baseUrl + url).into(holder.profilePic);
        holder.dateText.setText(items.get(position).getDate());
        holder.titleText.setText(items.get(position).getSongTitle());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HubOnViewActivity.class);
                intent.putExtra("TEAMCODE",position);
                intent.putExtra("id",items.get(position).getTEAMCODE());
                intent.putExtra("songTitle", items.get(position).getSongTitle());
                intent.putExtra("teamName", items.get(position).getGaruName());
                intent.putExtra("date", items.get(position).getDate());
                intent.putExtra("file", items.get(position).getMusicUri());
                Log.d(items.get(position).getMusicUri(), "fileNameCheck");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView dateText;
        ImageView profilePic;
        View view;
        public ViewHolder(View itemView) {
            super(itemView);

            titleText = (TextView) itemView.findViewById(R.id.songTitle);
            dateText = (TextView) itemView.findViewById(R.id.date);
            profilePic = (ImageView) itemView.findViewById(R.id.profilePic);
            view = (CardView) itemView.findViewById(R.id.verticalCarView);
        }
    }
}
