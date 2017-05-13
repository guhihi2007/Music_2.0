package org.music_20.activity;


import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/19.
 */

public abstract class Data<T> implements Serializable {
    protected String name,path, type;

    public Data(String name) {
        this.name = name;
    }

    public Data(String name, String path, String type) {
        this.name = name;
        this.path = path;
        this.type = type;
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


    public String getSize() {
        return giveSize();
    }
    protected abstract String giveSize();

    public int getCount() {
        return giveCount();
    }
    protected abstract int giveCount();

    public String getFall_name() {
        return givefall_name();
    }
    protected abstract String givefall_name();


    public String getType() {
        return giveType();
    }
    protected abstract String giveType();

}
