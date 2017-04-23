package org.music_20.activity;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.music_20.R;
import org.music_20.base.CommonClickListener;
import org.music_20.base.CommonRecycleAdapter;
import org.music_20.base.CommonViewHolder;
import org.music_20.base.MultiItemViewType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/19.
 */

public class SearchAdapter extends CommonRecycleAdapter<Data> implements MultiItemViewType<Data> {

    private CommonClickListener commonClickListener;
    private boolean showbox = false;//是否显示checkbox
    private Map<Integer, Song> ChoseList;
    public SearchAdapter(Context context, CommonClickListener commonClickListener) {
        super(context);
        this.commonClickListener = commonClickListener;
        this.multiItemViewType = this;
        ChoseList = new HashMap<>();
    }

    public SearchAdapter(Context context, List data, CommonClickListener commonClickListener) {
        super(context, data);
        this.commonClickListener = commonClickListener;
        this.multiItemViewType = this;
    }

    //修改了第二个textview的显示
    @Override
    protected void bindData(final CommonViewHolder holder, final Data data, final int position) {
        String type = ".mp3";
        if (type.equals(data.getType())) {
            holder.setText(R.id.vh_tv, data.getFall_name());//MP3格式是tv的显示
            holder.setText(R.id.vh_tv2, data.getSize());
        } else {
            holder.setText(R.id.vh_tv, data.getName());//文件夹格式是的显示
            holder.setText(R.id.vh_tv2, data.getCount() + "项");
        }
        if (showbox) {
            if (((CheckBox) holder.getView(R.id.vh_cb)) != null) {
                holder.setVisibility(R.id.vh_cb, View.VISIBLE);
                //给Checkbox设置被选监听
                ((CheckBox) holder.getView(R.id.vh_cb)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Log.v("gpp", "CheckBox被点击" + datas.get(position).getName());
                        if (isChecked) {
                            Log.v("gpp", "添加到ChoseList");
                            ChoseList.put(position, (Song) datas.get(position));
                        } else {
                            Log.v("gpp", "从ChoseList删除");
                            ChoseList.remove(position);
                        }
                        //状态存入SparseArray记录
                        boxarray.put(position, isChecked);
                        //状态为空时
                        if (boxarray.get(position) == null) boxarray.put(position, false);
                    }
                });
            } else {
                holder.setVisibility(R.id.vh_cb, View.INVISIBLE);
            }
        }
        holder.setCommonClickListener(commonClickListener);
    }

    public void setShowbox() {
        showbox = !showbox;
    }


    public void setSelected(int position) { //点击item时选中checkbox，已选中时取反
        if (boxarray.get(position)) {
            boxarray.put(position, false);
        } else {
            boxarray.put(position, true);
        }
        notifyItemChanged(position);//通知状态改变
    }

    public Map<Integer, Song> getChoseMap() {
        return ChoseList;
    }

    public SparseArray<Boolean> getBoxarray() {
        return boxarray;
    }

    public boolean getHaxbox(){
        return hasCheckbox;
    }

    @Override
    public int getLayouId(Data item, int position) {
        String type = ".mp3";
        if (type.equals(item.getType())) {
            hasCheckbox=true;
            return R.layout.music_layout;
        } else {
            return R.layout.folder_layout;
        }
    }


}
