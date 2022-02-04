package com.example.nkplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

public class Videoplayer_activity extends AppCompatActivity {
private TextView videonameTV,videoTimeTV;
private ImageButton backIB,forwardIB,playpuseIB;
private SeekBar videoSeekBar;
private VideoView videoView;
private RelativeLayout controlsRL,videoRL;
boolean isopen = true;
private  String videoName, videopath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);
     videoName = getIntent().getStringExtra("videoname");
     videopath = getIntent().getStringExtra("videpath");
        videonameTV = findViewById(R.id.TVVideosTitle);
        videoTimeTV = findViewById(R.id.idTVTime);
        backIB = findViewById(R.id.idIBBack);
        playpuseIB = findViewById(R.id.idIBPlay);
        forwardIB = findViewById(R.id.idIBForward);
        videoSeekBar = findViewById(R.id.idSeekBarprogress);
        videoView = findViewById(R.id.idvideoview);
        controlsRL = findViewById(R.id.RLControls);
        videoRL = findViewById(R.id.idRLvideo);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoSeekBar.setMax(videoView.getDuration());
                videoView.start();
            }
        });
        videonameTV.setText(videoName);
        backIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.seekTo(videoView.getCurrentPosition() - 10000);
            }
        });
        forwardIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.seekTo(videoView.getCurrentPosition() + 10000);
            }
        });
        playpuseIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (videoView.isPlaying()){
                   videoView.pause();
                   playpuseIB.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
               }else {
                   videoView.start();
                   playpuseIB.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
               }
            }
        });

        videoRL.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                if (isopen){
                    hidecontrols();
                    isopen = false;
                }else{
                    showControls();
                    isopen = true;
                }
            }
        });
        sethandel();
        initializeseekbar();
    }
    private void sethandel(){
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (videoView.getDuration()>0){
                    int curpos = videoView.getCurrentPosition();
                    videoSeekBar.setProgress(curpos);
                    videoTimeTV.setText(""+converTime(videoView.getDuration()-curpos));
                }
                handler.postDelayed(this,0);

            }
        };
        handler.postDelayed(runnable,500);
    }

    private String converTime(int ms) {
        String time;
        int x, seconds, minutes, hours;
        x = ms / 1000;
        seconds = x % 60;
        x /= 60;
        minutes = x % 60;
        x /= 60;
        hours = x % 24;
        if (hours != 0) {
            time = String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);

        } else {
            time = String.format("%02d", minutes) + ":" + String.format("%02d",seconds);
        }
        return time;
    }
    private void initializeseekbar(){
        videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromuser) {
                if (videoSeekBar.getId()==R.id.idSeekBarprogress){
                    if (fromuser){
                        videoView.seekTo(progress);
                        videoView.start();
                        int curpos = videoView.getCurrentPosition();
                        videoTimeTV.setText(""+converTime(videoView.getDuration()-curpos));

                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void showControls() {
        controlsRL.setVisibility(View.VISIBLE);

        final Window window = this.getWindow();
        if (window == null) {
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        View decorView = window.getDecorView();
        if (decorView!=null){
            int uiOption = decorView.getSystemUiVisibility();
            if (Build.VERSION.SDK_INT>=14){
                uiOption&= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }
            if (Build.VERSION.SDK_INT>=16){
                uiOption&= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            if (Build.VERSION.SDK_INT>=19){
                uiOption&= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            decorView.setSystemUiVisibility(uiOption);
                }
            }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void hidecontrols() {
        controlsRL.setVisibility(View.GONE);

        final Window window = this.getWindow();
        if (window==null){
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        View decorView = window.getDecorView();
        if (decorView!=null){
            int uiOption = decorView.getSystemUiVisibility();
            {

                if (Build.VERSION.SDK_INT >= 14) {
                    uiOption |= View.SYSTEM_UI_FLAG_LOW_PROFILE;

                }
                if (Build.VERSION.SDK_INT > 16) {
                    uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

                }
                if (Build.VERSION.SDK_INT > 19) {
                    uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                }
                }

            decorView.setSystemUiVisibility(uiOption);
        }
    }
}