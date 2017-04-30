package org.music_20.database;

/**
 * Created by Administrator on 2017/4/24.
 */

public class DB_Info {
    public static final String DBNAME = "Music";
    public static final int VERSION = 1;//版本号
    /**
     * 表一:播放列表
     */
    public static final String TABLE_NAME = "Play_List";//播放列表表名
    public static final String TABLE_PALY_LIST_KEY_1 = "LIST_NAME";//播放列表,表名键
    public static final String TABLE_PALY_LIST_KEY_2 = "LIST_CONTENT";//播放列表,表名键

    /**
     * 表二:播放列表下的歌曲
     */
    public static final String TABLE_KEY_0="_ID";
    public static final String TABLE_KEY_1="song_name";//每个播放列表，歌曲名键
    public static final String TABLE_KEY_2="song_path";//每个播放列表，歌曲路径键
    public static final String TABLE_KEY_3="song_type";//每个播放列表，歌曲大小键
    public static final String TABLE_KEY_4="song_size";//每个播放列表，歌曲大小键

}
