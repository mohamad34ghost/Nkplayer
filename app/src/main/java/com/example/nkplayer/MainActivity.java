package com.example.nkplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements videoNkadapter.videoClickInterface{

private RecyclerView videoNK;
private ArrayList<videomodel> videomodelArrayList;
private videoNkadapter videoNkadapter;
private static final int STORAGE_PERMISSION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoNK = findViewById(R.id.idrvvideo);
        videomodelArrayList = new ArrayList<>();
        videoNkadapter = new videoNkadapter(videomodelArrayList,this,this::onVideoClick);
    videoNK.setLayoutManager(new GridLayoutManager(this,2));
    videoNK.setAdapter(videoNkadapter);
    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
    }else{
        getVideos();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==STORAGE_PERMISSION){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "permissions Granted", Toast.LENGTH_SHORT).show();
                getVideos();
            }else{
                Toast.makeText(this, "The App not work whitout permissions..", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void getVideos(){
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = contentResolver.query(uri,null,null,null,null);
        if (cursor!=null && cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String videoTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                @SuppressLint("Range") String videopath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                Bitmap videoThumbnail = ThumbnailUtils.createVideoThumbnail(videopath,MediaStore.Images.Thumbnails.MINI_KIND);
                videomodelArrayList.add(new videomodel(videoTitle,videopath,videoThumbnail));

            }while (cursor.moveToNext());

        }
        videoNkadapter.notifyDataSetChanged();
    }



    @Override
    public void onVideoClick(int position) {
        Intent i = new Intent(MainActivity.this,Videoplayer_activity.class);
        i.putExtra("videoname",videomodelArrayList.get(position).getVideoname());
        i.putExtra("videpath",videomodelArrayList.get(position).getVideopath());
startActivity(i);
    }
}