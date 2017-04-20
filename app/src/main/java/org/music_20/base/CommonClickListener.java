package org.music_20.base;

import android.view.View;

/**
 * Created by Administrator on 2017/4/19.
 */

public interface CommonClickListener {
    //监听接口回调
    void OnCommonClickListener(View v,int position);
    boolean OnCommonLongClickListener(View v,int position);
}
