package teampj.java.dsm.hubgaruandroid.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by dsm2016 on 2017-09-04.
 */

public class TeamRequestViewHolder extends RecyclerView.ViewHolder{
    public TextView nameView, timeView, rNameView, rDescView, rFileView;
    public TeamRequestViewHolder(View itemView){
        super(itemView);
        nameView = (TextView) itemView.findViewById(R.id.nicknameView);
        timeView = (TextView) itemView.findViewById(R.id.timeView_r);
        rNameView = (TextView) itemView.findViewById(R.id.rNameView);
        rDescView = (TextView) itemView.findViewById(R.id.rDescView);
        rFileView = (TextView) itemView.findViewById(R.id.rFilenameView);
    }
}
