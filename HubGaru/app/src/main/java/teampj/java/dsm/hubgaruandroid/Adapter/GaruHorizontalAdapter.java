package teampj.java.dsm.hubgaruandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import teampj.java.dsm.hubgaruandroid.Activity.TeamMainActivity;
import teampj.java.dsm.hubgaruandroid.Model.GaruItem;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-10-07.
 */

public class GaruHorizontalAdapter extends RecyclerView.Adapter<GaruHorizontalAdapter.ViewHolder>{

    List<GaruItem> items;
    Context context;

    public GaruHorizontalAdapter(Context context, List<GaruItem> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public GaruHorizontalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.garu_horizontal_item, parent, false);
        return new GaruHorizontalAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final GaruHorizontalAdapter.ViewHolder holder, int position) {
        String url = items.get(position).getTeamPic();
        Glide.with(context).load(url).into(holder.teamPic);
        holder.teamName.setText(items.get(position).getTeamName());
        holder.teamInfo.setText(items.get(position).getTeamIntro());

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
        ImageView teamPic;

        View view;
        public ViewHolder(View itemView) {
            super(itemView);

            teamName = (TextView) itemView.findViewById(R.id.garuName);
            teamPic = (ImageView) itemView.findViewById(R.id.garuPic);
            teamInfo = (TextView) itemView.findViewById(R.id.garuIntro);
            view = (CardView) itemView.findViewById(R.id.garu);
        }
    }
}
