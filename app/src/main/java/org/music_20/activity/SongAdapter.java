package org.music_20.activity;

import android.content.Context;

import org.music_20.R;
import org.music_20.base.CommonClickListener;
import org.music_20.base.CommonRecycleAdapter;
import org.music_20.base.CommonViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/20.
 */

public class SongAdapter extends CommonRecycleAdapter<Data> {
    private CommonClickListener commonClickListener;
    //歌曲显示的布局图片不一样，布局文件不一样
    SongAdapter(Context context){
        super(context, R.layout.viewholder);

    }
    public SongAdapter(Context context, CommonClickListener commonClickListener) {
        super(context, R.layout.viewholder);
        this.commonClickListener = commonClickListener;
    }

    public SongAdapter(Context context, List<Data> datas) {
        super(context, datas, R.layout.viewholder);
    }

    public SongAdapter(Context context, List<Data> datas, int layoutID, CommonClickListener commonClickListener) {
        super(context, datas, layoutID);
        this.commonClickListener = commonClickListener;
    }
    //设置item中每个view显示的数据
    @Override
    protected void bindData(CommonViewHolder holder, Data data) {
        holder.setText(R.id.vh_tv,data.getName());
        holder.setText(R.id.vh_tv2,data.getSize());
    }
}
