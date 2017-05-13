package org.music_20.database.create;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.music_20.database.AbstractCreateDB;
import org.music_20.database.DB_Info;
import org.music_20.database.modify.DB_ModifyPlayList;

/**
 * Created by Administrator on 2017/4/24.
 */

public class DB_CreatePlayListContent extends AbstractCreateDB {
    private String  playlist_name,TABLE_NAME;
    private Context context;
    public DB_CreatePlayListContent(Context context,String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
        this.context=context;
        Log.v("gpp", " CreatePlayListContent:"+TABLE_NAME);
//        this.TABLE_NAME=getTABLE_NAME();
    }

    @Override
    public void CreateDB(SQLiteDatabase db) {
        Log.v("gpp", "创建PlayListContent表:" + TABLE_NAME);
//        String sql = " CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + DB_Info.TABLE_KEY_1 + " TEXT PRIMARY KEY NOT NULL ," + DB_Info.TABLE_KEY_2 + " TEXT NOT NULL ," + DB_Info.TABLE_KEY_3 + " TEXT NOT NULL)";
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME + " ( _ID INTEGER PRIMARY KEY," + DB_Info.TABLE_KEY_1 + " TEXT NOT NULL ," + DB_Info.TABLE_KEY_2 + " TEXT NOT NULL," + DB_Info.TABLE_KEY_3 + " TEXT NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void UpgradeDB(SQLiteDatabase db, int oldVersion) {
    }
}
