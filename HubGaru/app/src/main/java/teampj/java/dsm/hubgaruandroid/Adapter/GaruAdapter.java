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

import java.util.List;

import teampj.java.dsm.hubgaruandroid.Activity.HubOnViewActivity;
import teampj.java.dsm.hubgaruandroid.Activity.TeamMainActivity;
import teampj.java.dsm.hubgaruandroid.Model.GaruItem;
import teampj.java.dsm.hubgaruandroid.Model.HubItem;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-08-22.
 */

public class GaruAdapter extends RecyclerView.Adapter<GaruAdapter.ViewHolder> {

    List<GaruItem> items;
    Context context;

    public GaruAdapter(Context context, List<GaruItem> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.garu_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String url = items.get(position).getTeamPic();
        Log.d(url, "garuPicId");
        String baseUrl = "";
        Glide.with(context).load(baseUrl + url).into(holder.teamPic);
        holder.teamName.setText(items.get(position).getTeamName());
        holder.teamInfo.setText(items.get(position).getTeamIntro());
        holder.teamMem.setText(items.get(position).getLeader());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TeamMainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView teamName;
        TextView teamInfo;
        TextView teamMem;
        ImageView teamPic;

        View view;
        public ViewHolder(View itemView) {
            super(itemView);

            teamName = (TextView) itemView.findViewById(R.id.teamName);
            teamMem = (TextView) itemView.findViewById(R.id.teamMem);
            teamPic = (ImageView) itemView.findViewById(R.id.teamPic);
            teamInfo = (TextView) itemView.findViewById(R.id.garuInfo);
            view = (CardView) itemView.findViewById(R.id.garuView);
        }
    }
}
