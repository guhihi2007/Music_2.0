package org.music_20.activity;

import android.content.Context;

import org.music_20.R;
import org.music_20.base.CommonClickListener;
import org.music_20.base.CommonRecycleAdapter;
import org.music_20.base.CommonViewHolder;
import org.music_20.base.MultiItemViewType;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public class SearchAdapter extends CommonRecycleAdapter<Data> implements MultiItemViewType<Data> {

    private CommonClickListener commonClickListener;

    public SearchAdapter(Context context, CommonClickListener commonClickListener) {
        super(context);
        this.commonClickListener = commonClickListener;
        this.multiItemViewType = this;
    }

    public SearchAdapter(Context context, List data, CommonClickListener commonClickListener) {
        super(context, data);
        this.commonClickListener = commonClickListener;
        this.multiItemViewType = this;
    }

    //文件夹的布修改了imageview的图片背景
    //修改了第二个textview的显示
    @Override
    protected void bindData(CommonViewHolder holder, Data data) {
        String type = ".mp3";
        if (type.equals(data.getType())) {
            holder.setText(R.id.vh_tv, data.getFall_name());
            holder.setText(R.id.vh_tv2,data.getSize());
        } else {
            holder.setText(R.id.vh_tv, data.getName());
            holder.setText(R.id.vh_tv2, data.getCount() + "项");
        }
        holder.setCommonClickListener(commonClickListener);
    }

    @Override
    public int getLayouId(Data item, int position) {
        String type = ".mp3";
        if (type.equals(item.getType())) {
            return R.layout.music_layout;
        } else {
            return R.layout.folder_layout;
        }
    }
}
