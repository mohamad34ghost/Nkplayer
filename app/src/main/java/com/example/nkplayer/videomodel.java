package com.example.nkplayer;

import android.graphics.Bitmap;

public class videomodel {
    private String videoname;
    private String videopath;
    private Bitmap thumbnail;

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

    public String getVideopath() {
        return videopath;
    }

    public void setVideopath(String videopath) {
        this.videopath = videopath;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public videomodel(String videoname, String videopath, Bitmap thumbnail) {
        this.videoname = videoname;
        this.videopath = videopath;
        this.thumbnail = thumbnail;
    }
}
