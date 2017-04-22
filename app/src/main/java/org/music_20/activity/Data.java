package org.music_20.activity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/19.
 */

public class Data implements Serializable {
    private String name;
    private String path,size,type;
    private int count;

    public Data() {
    }

    public Data(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public Data(String name, String path,int count,String type) {
        this(name, path);
        this.count = count;
    }

    public Data(String name, String path,String size, String type) {
        this(name, path);
        this.size=size;
        this.type=type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
