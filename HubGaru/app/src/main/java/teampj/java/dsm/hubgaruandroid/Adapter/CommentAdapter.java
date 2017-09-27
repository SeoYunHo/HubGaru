package teampj.java.dsm.hubgaruandroid.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import teampj.java.dsm.hubgaruandroid.Model.CommentItem;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-09-26.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    List<CommentItem> items;
    Context context;

    public CommentAdapter(List<CommentItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url = items.get(position).getProfilePic();
        Glide.with(context).load(url).
                apply(RequestOptions.bitmapTransform(new CircleCrop(context))).into(holder.profilImage);
        holder.nameText.setText(items.get(position).getName());
        holder.commentText.setText(items.get(position).getComment());
        holder.dateText.setText(items.get(position).getEditDate());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView commentText;
        TextView dateText;
        ImageView profilImage;
        public ViewHolder(View itemView) {
            super(itemView);

            nameText = (TextView) itemView.findViewById(R.id.commentNameText);
            commentText = (TextView) itemView.findViewById(R.id.commentText);
            dateText = (TextView) itemView.findViewById(R.id.dateText);
            profilImage = (ImageView) itemView.findViewById(R.id.commentProfile);
        }
    }
}
