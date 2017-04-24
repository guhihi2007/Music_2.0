package org.music_20.database.create;

import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.music_20.database.AbstractCreateDB;

/**
 * Created by Administrator on 2017/4/22.
 */

public class DB_CreatePlayList extends AbstractCreateDB {

    private String TABLE_NAME;
    public DB_CreatePlayList(String TABLE_NAME) {
        this.TABLE_NAME=TABLE_NAME;
    }

    @Override
    public void CreateDB(SQLiteDatabase db) {
        Log.v("gpp", "数据库创建表:"+TABLE_NAME);
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME + " ( _ID INTEGER PRIMARY KEY,LIST_NAME TEXT NOT NULL,LIST_CONTENT TEXT NOT NULL)";
        db.execSQL(sql);
    }
    @Override
    public void UpgradeDB(SQLiteDatabase db, int oldVersion) {

    }
}
