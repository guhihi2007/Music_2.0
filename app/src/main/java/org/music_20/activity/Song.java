package org.music_20.activity;

/**
 * Created by Administrator on 2017/4/21.
 */

public class Song extends Data {
    public Song() {
    }

    public Song(String name, String path) {
        super(name, path);
    }

    public Song(String name, String path, String size) {
        super(name, path, size);
    }

    public Song(String name, String path, String size, int count) {
        super(name, path, size, count);
    }
}
