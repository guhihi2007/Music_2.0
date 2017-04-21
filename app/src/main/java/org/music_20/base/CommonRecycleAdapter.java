package org.music_20.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.music_20.activity.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public abstract class CommonRecycleAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
    //数据支持泛型List<T>
    private List<T> datas;
    private LayoutInflater layoutInflater;
    private int LayoutID;

    public CommonRecycleAdapter(Context context,int layoutID){
        this.layoutInflater = LayoutInflater.from(context);
        this.LayoutID = layoutID;
    }

    //构造方法中传入datas,layoutID,layoutID就是viewholder的布局文件id
    public CommonRecycleAdapter(Context context, List<T> datas, int layoutID) {
        this.layoutInflater = LayoutInflater.from(context);
        this.datas = datas;
        this.LayoutID = layoutID;
    }
    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(LayoutID, parent, false);
        CommonViewHolder commonViewHolder = new CommonViewHolder(view);
        return commonViewHolder;
    }
    public void setDatas(List<T> datas){
        this.datas=datas;
        this.notifyDataSetChanged();
    }
    public void addData(List<T> datas) {
        this.datas.clear();
        this.datas = datas;
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }

    //设置数据，调用抽象方法bindData，继承这个类实现bindData方法
    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        bindData(holder, datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    //抽象方法
    protected abstract void bindData(CommonViewHolder holder, T data);
}
