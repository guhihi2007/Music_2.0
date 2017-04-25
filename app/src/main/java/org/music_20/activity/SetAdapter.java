package org.music_20.activity;

import android.content.Context;

import org.music_20.R;
import org.music_20.base.CommonClickListener;
import org.music_20.base.CommonRecycleAdapter;
import org.music_20.base.CommonViewHolder;
import org.music_20.base.MultiItemViewType;

/**
 * Created by Administrator on 2017/4/25.
 */

class SetAdapter extends CommonRecycleAdapter<Data> implements MultiItemViewType{
    private CommonClickListener commonClickListener;
    public SetAdapter(Context context, CommonClickListener commonClickListener) {
        super(context);
        this.commonClickListener = commonClickListener;
        this.multiItemViewType = this;
    }

    @Override
    protected void bindData(CommonViewHolder holder, Data data, int position) {
        holder.setText(R.id.set_et,data.getName());
        holder.setCommonClickListener(commonClickListener);
    }

    @Override
    public int getLayouId(Object item, int position) {
        return R.layout.set_layout;
    }
}
