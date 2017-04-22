package org.music_20.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Administrator on 2017/4/22.
 */

public class DB_CreatePlayList extends AbstractCreateDB {
    public static final String TABLE_NAME = "Play_List";
    public static final int VERSION = 1;//版本号
    public static final String DBNAME = "Music";

    @Override
    public void CreateDB(SQLiteDatabase db) {
        Log.v("gpp", "CreateDB");
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME + " ( _ID INTEGER PRIMARY KEY,LIST_NAME TEXT NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void UpgradeDB(SQLiteDatabase db, int oldVersion) {

    }
}
