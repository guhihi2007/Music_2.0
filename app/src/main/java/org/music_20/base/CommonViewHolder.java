package org.music_20.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.music_20.R;

/**
 * Created by Administrator on 2017/4/19.
 */

public class CommonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private CommonClickListener commonClickListener;
    private SparseArray<View> viewSparseArray;
    private View MyItemView;

    public CommonViewHolder(View itemView) {
        super(itemView);
        this.MyItemView = itemView;
        //存储view
        viewSparseArray = new SparseArray<>();
        //给itemview添加监听
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    public void setCommonClickListener(CommonClickListener commonClickListener) {
        //给全局变量commonClickListener赋值
        this.commonClickListener = commonClickListener;
    }

    public <T extends View> T getView(int viewId) {
        //根据id获取view做相应的设置（如setText（）），没有的话添加到array
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = MyItemView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }

    public CommonViewHolder setText(int id, CharSequence sequence) {
        View view = getView(id);
        if (view instanceof TextView) {
            TextView tv = (TextView) getView(id);
            tv.setText(sequence);
        }
        if (view instanceof EditText) {
            EditText text = (EditText) getView(id);
            text.setText(sequence);
        }
        return this;
    }

    public CommonViewHolder setImageRes(int id, int resId) {
        ImageView iv = (ImageView) getView(id);
        iv.setImageResource(resId);
        return this;
    }

    public CommonViewHolder setVisibility(int id, int isVisiable) {
        CheckBox cb = (CheckBox) getView(id);
        if (cb != null)
            cb.setVisibility(isVisiable);
        return this;
    }
    public CommonViewHolder setChecked(int id, boolean isChecked) {
        CheckBox cb = (CheckBox) getView(id);
        if (cb != null)
            cb.setChecked(isChecked);
        return this;
    }

    @Override
    public void onClick(View v) {
        commonClickListener.OnCommonClickListener(v, getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        return commonClickListener != null && commonClickListener.OnCommonLongClickListener(v, getAdapterPosition());
    }
}
