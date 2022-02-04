package com.example.nkplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class videoNkadapter extends RecyclerView.Adapter<videoNkadapter.ViewHolder>{

    private ArrayList<videomodel>videomodelArrayList;
    private Context context;
    private videoClickInterface videoClickInterface;

    public videoNkadapter(ArrayList<videomodel> videomodelArrayList, Context context, videoNkadapter.videoClickInterface videoClickInterface) {
        this.videomodelArrayList = videomodelArrayList;
        this.context = context;
        this.videoClickInterface = videoClickInterface;
    }

    @NonNull
    @Override
    public videoNkadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_nk_item,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull videoNkadapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
videomodel videomodel = videomodelArrayList.get(position);
holder.thumbnailIV.setImageBitmap(videomodel.getThumbnail());
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        videoClickInterface.onVideoClick(position);
    }
});

    }

    @Override
    public int getItemCount() {
        return videomodelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView thumbnailIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailIV = itemView.findViewById(R.id.idIVThumbNail);
        }
    }
    public interface videoClickInterface{
        void onVideoClick(int position);
    }
}


