package org.music_20.database.modify;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.music_20.activity.Folder;
import org.music_20.activity.Song;
import org.music_20.database.helper.DB_GetPlayListHelper;
import org.music_20.database.DB_Info;
import org.music_20.database.DB_Interface;
import org.music_20.database.helper.DataBaseHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/22.
 */

public class DB_ModifyPlayList {

    private String mTableName;
    private SQLiteDatabase database;
    private static int i = 0;
    private String LIST_CONTENT, list_name;
    private Context context;

    public DB_ModifyPlayList(Context context, String list_name) {
        this.context = context;
        this.list_name = list_name;
        DB_Interface db_interface = new DB_GetPlayListHelper();
        DataBaseHelper dataBaseHelper = db_interface.getDBhelper(context, DB_Info.TABLE_NAME);
        database = dataBaseHelper.getReadableDatabase();//会先以读写方式打开数据库，如果失败就会以只读打开。getWritable只会以读写方式打开，磁盘满了打开失败会报错
        mTableName = DB_Info.TABLE_NAME;
        if (list_name != null) {
            LIST_CONTENT = getListContent();
        }
    }

    public void add_Table() {
        String list_content = "playlist_" + i + "";
        ContentValues cv = new ContentValues();
        cv.put(DB_Info.TABLE_PALY_LIST_KEY_1, list_name);
        cv.put(DB_Info.TABLE_PALY_LIST_KEY_2, list_content);
        database.insert(mTableName, null, cv);
        add_ListContent(list_content);
        i++;
    }

    private void add_ListContent(String list_content) {
        Log.v("gpp", LIST_CONTENT + "");
        String sql = "CREATE TABLE IF NOT EXISTS " + list_content + " ( _ID INTEGER PRIMARY KEY," + DB_Info.TABLE_KEY_1 + " TEXT NOT NULL ," + DB_Info.TABLE_KEY_2 + " TEXT NOT NULL," + DB_Info.TABLE_KEY_3 + " TEXT NOT NULL," + DB_Info.TABLE_KEY_4 + " TEXT NOT NULL)";
        database.execSQL(sql);
        database.close();
    }

    public String getListContent() {
        String List_Content = "";
        Cursor cursor = database.query(mTableName, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex(DB_Info.TABLE_PALY_LIST_KEY_1);//获取list_name所在的列下标
            String values = cursor.getString(columnIndex);//根据所在列，拿到游标所在的数据values
            if (list_name.equals(values)) {
                int columnIndex1 = cursor.getColumnIndex(DB_Info.TABLE_PALY_LIST_KEY_2);
                List_Content = cursor.getString(columnIndex1);
            }
        }
        if (cursor != null) cursor.close();//查询完关闭游标
        return List_Content;
    }

    public ArrayList<Folder> getPlayList() {
        ArrayList<Folder> list = new ArrayList<>();
        Cursor cursor = database.query(mTableName, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Folder folder = new Folder();
            int columnIndex = cursor.getColumnIndex(DB_Info.TABLE_PALY_LIST_KEY_1);//获取list_name所在的列下标
            String values = cursor.getString(columnIndex);//根据所在列，拿到游标所在的数据values
            folder.setName(values);
            list.add(folder);
        }
        if (cursor != null) cursor.close();//查询完关闭游标
        return list;
    }

    public void add_songToList(Song song) {
        ContentValues cv = new ContentValues();
        String values_1 = song.getName();
        String values_2 = song.getPath();
        String values_3 = song.getType();
        String values_4= song.getSize();

        cv.put(DB_Info.TABLE_KEY_1, values_1);
        cv.put(DB_Info.TABLE_KEY_2, values_2);
        cv.put(DB_Info.TABLE_KEY_3, values_3);
        cv.put(DB_Info.TABLE_KEY_4, values_4);

        database.insert(LIST_CONTENT, null, cv);
        Log.v("gpp", "添加歌曲到表:" + LIST_CONTENT);
        Log.v("gpp", "添加歌曲:" + values_1 + "+||+" + values_2 + "+||+" + values_3+ "+||+" + values_4);
        database.close();
    }

    public ArrayList getSongList() {
        ArrayList<Song> list = new ArrayList<>();
        Cursor cursor = database.query(LIST_CONTENT, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int name = cursor.getColumnIndex(DB_Info.TABLE_KEY_1);
            int path = cursor.getColumnIndex(DB_Info.TABLE_KEY_2);
            int type = cursor.getColumnIndex(DB_Info.TABLE_KEY_3);
            int size = cursor.getColumnIndex(DB_Info.TABLE_KEY_4);

            String getName = cursor.getString(name);
            String getPath = cursor.getString(path);
            String gettype = cursor.getString(type);
            String getsize = cursor.getString(size);

            Song song = new Song(getName, getPath,gettype,getsize);
            list.add(song);
        }
        if (cursor != null) cursor.close();//查询完关闭游标
        return list;
    }
}
