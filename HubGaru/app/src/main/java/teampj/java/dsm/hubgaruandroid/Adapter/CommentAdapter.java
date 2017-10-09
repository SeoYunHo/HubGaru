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
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teampj.java.dsm.hubgaruandroid.Model.CommentItem;
import teampj.java.dsm.hubgaruandroid.Network.Service.HubService;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-09-26.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    List<CommentItem> items;
    Context context;
    ArrayList<CommentItem> mDataSet;

    public CommentAdapter(ArrayList<CommentItem> dataSet, Context context) {
        this.mDataSet = dataSet;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        HubService.getRetrofit(context).getInfo(mDataSet.get(position).getId()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject user = response.body().get("user").getAsJsonObject();
                holder.nameText.setText(user.get("name").getAsString());
                holder.commentText.setText(mDataSet.get(position).getComment());
                holder.dateText.setText(mDataSet.get(position).getDate());
                Glide.with(context).load("").into(holder.profilImage);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
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
