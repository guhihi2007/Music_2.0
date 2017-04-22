package org.music_20.activity;

/**
 * Created by Administrator on 2017/4/22.
 */

public class Folder extends Data<Folder> {
    private int count;
    public Folder(String name, String path,int count,String type) {
        super(name, path,type);
        this.count = count;
    }

    @Override
    protected String giveSize() {
        return null;
    }

    @Override
    protected int giveCount() {
        return count;
    }

    @Override
    protected String givefall_name() {
        return null;
    }

    @Override
    protected String giveType() {
        return ".dir";
    }

}
