package org.music_20.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.music_20.activity.Data;
import org.music_20.activity.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public abstract class CommonRecycleAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
    //数据支持泛型List<T>
    protected List<T> datas;
    private LayoutInflater layoutInflater;
    protected int LayoutID;
    protected MultiItemViewType<T> multiItemViewType;
    protected SparseArray<Boolean> boxarray = new SparseArray<>();
    protected boolean hasCheckbox;
    protected View footView;
    public CommonRecycleAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    //构造方法中传入datas,layoutID,layoutID就是viewholder的布局文件id
    public CommonRecycleAdapter(Context context, List<T> datas, int layoutID) {
        this.layoutInflater = LayoutInflater.from(context);
        this.datas = datas;
        this.LayoutID = layoutID;
    }
    public CommonRecycleAdapter(Context context, List<T> datas) {
        this.layoutInflater = LayoutInflater.from(context);
        this.datas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        if (multiItemViewType != null) {
            return multiItemViewType.getLayouId(datas.get(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (multiItemViewType != null) {
            LayoutID = viewType;
        }
        View view = layoutInflater.inflate(LayoutID, parent, false);
        CommonViewHolder commonViewHolder = new CommonViewHolder(view);
        return commonViewHolder;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
        initNOselected();//设置box初始状态
        this.notifyDataSetChanged();
    }
    public void setSong(List<Song> datas) {
        this.datas = (List<T>) datas;
        initNOselected();//设置box初始状态
        this.notifyDataSetChanged();
    }
    public void addData(List<T> datas) {
//        this.datas.clear();
        this.datas = datas;
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }

    //设置数据，调用抽象方法bindData，继承这个类实现bindData方法
    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        bindData(holder, datas.get(position),position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    //抽象方法
    protected abstract void bindData(CommonViewHolder holder, T data,int position);


    private void initNOselected() {
        if (datas!=null)
            for (int i=0;i<datas.size();i++) {
                boxarray.put(i,false);
            }
    }
}
