package teampj.java.dsm.hubgaruandroid.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by dsm2016 on 2017-10-10.
 */

public class TeamHubViewHolder extends RecyclerView.ViewHolder{
    public ImageView imageView;
    public TextView nameView;
    public TeamHubViewHolder(View itemView){
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.hubPic);
        nameView = (TextView) itemView.findViewById(R.id.hub_text);
    }
}