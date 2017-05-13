package org.music_20.database;

import android.content.Context;

import org.music_20.database.helper.DataBaseHelper;

/**
 * Created by Administrator on 2017/4/22.
 */

public interface DB_Interface {
    DataBaseHelper getDBhelper(Context context, String table_name);
}
