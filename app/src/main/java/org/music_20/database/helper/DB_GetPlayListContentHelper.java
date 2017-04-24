package org.music_20.database.helper;

import android.content.Context;
import android.util.Log;

import org.music_20.database.DB_Info;
import org.music_20.database.DB_Interface;
import org.music_20.database.create.DB_CreatePlayListContent;

/**
 * Created by Administrator on 2017/4/24.
 */

public class DB_GetPlayListContentHelper implements DB_Interface {

    @Override
    public DataBaseHelper getDBhelper(Context context, String table_name) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, DB_Info.DBNAME,DB_Info.VERSION);
        DB_CreatePlayListContent db_createPlayListContent= new DB_CreatePlayListContent(context,table_name);
        dataBaseHelper.setAbstractCreateDB(db_createPlayListContent);
        return dataBaseHelper;
    }
}

