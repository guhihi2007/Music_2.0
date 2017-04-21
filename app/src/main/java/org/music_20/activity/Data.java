package org.music_20.activity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/19.
 */

public class Data implements Serializable {
    public String name;
    public String path,size;
    public int count;

    public Data() {
    }

    public Data(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public Data(String name, String path,String size) {
        this(name, path);
        this.size = size;
    }

    public Data(String name, String path,String size, int count) {
        this(name, path, size);
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int size) {
        this.count = count;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
