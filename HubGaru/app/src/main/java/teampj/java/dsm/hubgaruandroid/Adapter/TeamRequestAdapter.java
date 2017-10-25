package teampj.java.dsm.hubgaruandroid.Adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import teampj.java.dsm.hubgaruandroid.Activity.TeamMainActivity;
import teampj.java.dsm.hubgaruandroid.R;
import teampj.java.dsm.hubgaruandroid.Model.TeamRequestItem;
import teampj.java.dsm.hubgaruandroid.Holder.TeamRequestViewHolder;

/**
 * Created by dsm2016 on 2017-09-04.
 */

public class TeamRequestAdapter extends RecyclerView.Adapter<TeamRequestViewHolder>{
    ArrayList<TeamRequestItem> items = new ArrayList<TeamRequestItem>();

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private Context context;

    public void add(TeamRequestItem newItem) {
        items.add(newItem);
        notifyDataSetChanged();
    }

    @Override
    public TeamRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.content_team_item_request, parent, false);
        return new TeamRequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TeamRequestViewHolder holder, int position) {
        final TeamRequestItem item = items.get(position);
        holder.nameView.setText(item.getNickName());
        holder.timeView.setText(item.getDateTime());
        holder.rNameView.setText(item.getRequestName());
        holder.rDescView.setText(item.getRequestDesc());
        holder.rFileView.setText(item.getRequestFile());
        holder.rFileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageReference.child("Requests").child(item.getRequestFile()).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>(){
                    @Override
                    public void onSuccess(final byte[] bytes) {
                        storageReference.child("Requests").child(item.getRequestFile()).getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                            @Override
                            public void onSuccess(StorageMetadata storageMetadata) {
                                String filename = storageReference.child("Requests").child(item.getRequestFile()).getName();
                                String path =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+filename;
                                try{
                                    Toast.makeText(context, filename + "다운로드 중..", Toast.LENGTH_SHORT).show();
                                    FileOutputStream fos = new FileOutputStream(path);
                                    fos.write(bytes);
                                    fos.close();

                                    Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                                    viewIntent.setDataAndType(Uri.parse(path), storageMetadata.getContentType());
                                    context.startActivity(Intent.createChooser(viewIntent, null));
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    public void setContext(Context context){
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}