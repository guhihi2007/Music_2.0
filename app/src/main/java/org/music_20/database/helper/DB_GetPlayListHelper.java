package org.music_20.database.helper;

import android.content.Context;

import org.music_20.database.DB_Info;
import org.music_20.database.DB_Interface;
import org.music_20.database.create.DB_CreatePlayList;
import org.music_20.database.helper.DataBaseHelper;

/**
 * Created by Administrator on 2017/4/22.
 */

public class DB_GetPlayListHelper implements DB_Interface {
    @Override
    public DataBaseHelper getDBhelper(Context context, String table_name) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, DB_Info.DBNAME,DB_Info.VERSION);
        DB_CreatePlayList db_createPlayList= new DB_CreatePlayList(table_name);
        dataBaseHelper.setAbstractCreateDB(db_createPlayList);
        return dataBaseHelper;
    }
}
