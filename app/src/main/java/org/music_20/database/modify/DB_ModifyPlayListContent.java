package org.music_20.database.modify;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.music_20.activity.Song;
import org.music_20.database.DB_Info;
import org.music_20.database.DB_Interface;
import org.music_20.database.helper.DB_GetPlayListContentHelper;
import org.music_20.database.helper.DataBaseHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/24.
 */

public class DB_ModifyPlayListContent {

    private SQLiteDatabase database;
    private String mTABLE_NAME;
    private Context context;
    private String playlist_name;

    public DB_ModifyPlayListContent(Context context, String playlist_name) {
        this.context=context;
        this.playlist_name=playlist_name;
        Log.v("gpp", " ModifyPlayListContent:"+mTABLE_NAME);
        DB_Interface db_interface = new DB_GetPlayListContentHelper();
        DataBaseHelper helper = db_interface.getDBhelper(context, mTABLE_NAME);
        database = helper.getReadableDatabase();
    }

    public void addSongToList(Song song) {
        String sql = "CREATE TABLE IF NOT EXISTS "+mTABLE_NAME + " ( _ID INTEGER PRIMARY KEY," + DB_Info.TABLE_KEY_1 + " TEXT NOT NULL ," + DB_Info.TABLE_KEY_2 + " TEXT NOT NULL," + DB_Info.TABLE_KEY_3 + " TEXT NOT NULL)";
        database.execSQL(sql);

        ContentValues cv = new ContentValues();
        String values_1=song.getName();
        String values_2=song.getPath();
        String values_3=song.getSize();

        cv.put(DB_Info.TABLE_KEY_1, values_1);
        cv.put(DB_Info.TABLE_KEY_2, values_2);
        cv.put(DB_Info.TABLE_KEY_3, values_3);
        database.insert(mTABLE_NAME, null, cv);
        Log.v("gpp", "添加歌曲到表:" + mTABLE_NAME);
        Log.v("gpp", "添加歌曲:" + values_1 + "+||+" + values_2 + "+||+" + values_3);
        database.close();
    }

    public void deleteSongFromList(Song song) {
//        String name = song.getName();
//        String sql = "DELETE  FROM " + mTABLE_NAME + " WHERE " + DB_Info.TABLE_KEY_1 + "= " + name + "";
//        Cursor cursor = database.query(mTABLE_NAME, null, null, null, null, null, null);
//        while (cursor.moveToNext()) {
//            int index = cursor.getColumnIndex(DB_Info.TABLE_KEY_1);
//            String getName = cursor.getString(index);
//            if (name.equals(getName)) {
////                database.execSQL(sql);
//            }
//        }
//        if (cursor != null) cursor.close();//查询完关闭游标
    }

    public ArrayList<Song> getSongFromList() {
        ArrayList<Song> list = new ArrayList<>();
        Cursor cursor = database.query(mTABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int name = cursor.getColumnIndex(DB_Info.TABLE_KEY_1);
            int path = cursor.getColumnIndex(DB_Info.TABLE_KEY_2);
            int size = cursor.getColumnIndex(DB_Info.TABLE_KEY_3);
            String getName = cursor.getString(name);
            String getPath = cursor.getString(path);
            String getSize = cursor.getString(size);
//            Song song = new Song(getName, getPath, getSize);
//            list.add(song);
        }
        return list;
    }

}
