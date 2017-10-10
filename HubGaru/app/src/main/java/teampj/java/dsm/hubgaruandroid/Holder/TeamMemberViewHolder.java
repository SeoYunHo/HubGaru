package teampj.java.dsm.hubgaruandroid.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by dsm2016 on 2017-10-09.
 */

public class TeamMemberViewHolder extends RecyclerView.ViewHolder{
    public TextView iconView, nameView;
    public TeamMemberViewHolder(View itemView){
        super(itemView);
        iconView = (TextView) itemView.findViewById(R.id.member_icon);
        nameView = (TextView) itemView.findViewById(R.id.member_text);
    }
}
