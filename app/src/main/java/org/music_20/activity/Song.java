package org.music_20.activity;

/**
 * Created by Administrator on 2017/4/22.
 */

public class Song extends Data<Song> {
    private String fall_name, size;
    public Song(String name, String path, String size) {
        super(name, path, ".mp3");
        this.size = size;
    }
    public Song(String name, String path, String size, String fall_name) {
        this(name, path, ".mp3");
        this.size = size;
        this.fall_name = fall_name;
    }
    public String getFall_name() {
        return fall_name;
    }
    @Override
    protected String givefall_name() {
        return fall_name;
    }


    @Override
    public String getSize() {
        return size;
    }

    @Override
    protected String giveSize() {
        return size;
    }

    @Override
    protected int giveCount() {
        return 0;
    }
    @Override
    protected String giveType() {
        return ".mp3";
    }
}
