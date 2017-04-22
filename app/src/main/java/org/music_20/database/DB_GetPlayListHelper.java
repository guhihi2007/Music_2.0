package org.music_20.database;

import android.content.Context;
import android.util.Log;

/**
 * Created by Administrator on 2017/4/22.
 */

public class DB_GetPlayListHelper implements DB_Interface {
    @Override
    public DataBaseHelper getDBhelper(Context context) {
//        Log.v("gpp", "getDBhelper() ");
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context,DB_CreatePlayList.DBNAME,DB_CreatePlayList.VERSION);
        DB_CreatePlayList db_createPlayList= new DB_CreatePlayList();
        dataBaseHelper.setAbstractCreateDB(db_createPlayList);
        return dataBaseHelper;
    }
}
