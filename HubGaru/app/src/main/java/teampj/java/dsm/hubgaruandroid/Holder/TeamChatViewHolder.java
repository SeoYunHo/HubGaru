package teampj.java.dsm.hubgaruandroid.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by dsm2016 on 2017-09-03.
 */

public class TeamChatViewHolder extends RecyclerView.ViewHolder{
    public TextView nameView, descView;
    public TeamChatViewHolder(View itemView){
        super(itemView);
        nameView = (TextView) itemView.findViewById(R.id.nicknameView);
        descView = (TextView) itemView.findViewById(R.id.descView);
    }
}
