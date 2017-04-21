package org.music_20.activity;

import android.content.Context;

import org.music_20.R;
import org.music_20.base.CommonClickListener;
import org.music_20.base.CommonRecycleAdapter;
import org.music_20.base.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public class DireAdapter extends CommonRecycleAdapter<Data> {

    private CommonClickListener commonClickListener;
    private ArrayList<Data> dirs ;//= new ArrayList<>()
    public DireAdapter(Context context, CommonClickListener commonClickListener) {
        super(context,R.layout.viewholder);
        this.commonClickListener = commonClickListener;
    }

    public DireAdapter(Context context, List<Data> data) {
        super(context, data, R.layout.viewholder);
    }


    public DireAdapter(Context context, List data, CommonClickListener commonClickListener) {
        super(context, data, R.layout.viewholder);
        this.commonClickListener = commonClickListener;
    }

    //文件夹的布修改了imageview的图片背景
    //修改了第二个textview的显示
    @Override
    protected void bindData(CommonViewHolder holder, Data data) {
        holder.setImageRes(R.id.vh_im, R.mipmap.dire);
        holder.setText(R.id.vh_tv, data.getName());
        holder.setText(R.id.vh_tv2, data.getCount() + "项");
        holder.setCommonClickListener(commonClickListener);
    }
}
