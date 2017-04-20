package org.music_20.activity;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 */

class vpAdatper extends PagerAdapter {
    private Context context;
    private List<View> list;
    @Override
    public int getCount() {
        return list.size();
    }

    public vpAdatper(Context context,List<View> list) {
        super();
        this.context=context;
        this.list=list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
