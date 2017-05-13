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

public class ListAdapter extends CommonRecycleAdapter<Data> implements MultiItemViewType<Data> {

    private CommonClickListener commonClickListener;

    public ListAdapter(Context context, CommonClickListener commonClickListener) {
        super(context);
        this.commonClickListener = commonClickListener;
        this.multiItemViewType = this;
    }

    @Override
    public int getLayouId(Data item, int position) {
        return R.layout.list_layout;
    }

    @Override
    protected void bindData(CommonViewHolder holder, Data data,int positon) {
        holder.setText(R.id.music_item_tv,data.getName());
        holder.setCommonClickListener(commonClickListener);
    }
}
