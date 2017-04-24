package org.music_20.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2017/4/22.
 */

public abstract class AbstractCreateDB {
    public abstract void CreateDB(SQLiteDatabase db);

    public abstract void UpgradeDB(SQLiteDatabase db, int oldVersion);
}
