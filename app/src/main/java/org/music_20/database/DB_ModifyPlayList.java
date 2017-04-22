package org.music_20.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.music_20.activity.Folder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/22.
 */

public class DB_ModifyPlayList {

    private String mTableName;
    private SQLiteDatabase database;
    public static final String Table_Column_Name = "LIST_NAME";

    public DB_ModifyPlayList(Context context) {
        DB_Interface db_interface = new DB_GetPlayListHelper();
        DataBaseHelper dataBaseHelper = db_interface.getDBhelper(context);
        database = dataBaseHelper.getReadableDatabase();//会先以读写方式打开数据库，如果失败就会以只读打开。getWritable只会以读写方式打开，磁盘满了打开失败会报错
        mTableName = DB_CreatePlayList.TABLE_NAME;
    }

    public void addPlayList(String list_name) {
//        Log.v("gpp", "addPlayList() ");
        ContentValues cv = new ContentValues();
        cv.put(Table_Column_Name, list_name);
        database.insert(mTableName, null, cv);
        database.close();
    }

    public ArrayList<Folder> getPlayList() {
//        Log.v("gpp", "getPlayList() ");
        Cursor cursor;
        ArrayList<Folder> list = new ArrayList<>();
        cursor = database.query(mTableName, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Folder folder = new Folder();
            int columnIndex = cursor.getColumnIndex(Table_Column_Name);//获取list_name所在的列下标
            String values = cursor.getString(columnIndex);//根据所在列，拿到游标所在的数据values
            folder.setName(values);
            list.add(folder);
        }
        if (cursor!=null)cursor.close();//查询完关闭游标
        return list;
    }
}
