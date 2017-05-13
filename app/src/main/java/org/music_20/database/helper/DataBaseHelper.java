package org.music_20.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.music_20.database.AbstractCreateDB;

/**
 * Created by Administrator on 2017/4/22.
 */

public class DataBaseHelper extends SQLiteOpenHelper {


    private AbstractCreateDB abstractCreateDB;

    public DataBaseHelper(Context context, String name,int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        abstractCreateDB.CreateDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        abstractCreateDB.UpgradeDB(db,oldVersion);

    }

    public void setAbstractCreateDB(AbstractCreateDB abstractCreateDB) {
        this.abstractCreateDB = abstractCreateDB;
    }
}
