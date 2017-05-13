package org.music_20.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import org.music_20.R;

/**
 * Created by Administrator on 2017/5/8.
 */

public class Test extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
//        TextView textView =(TextView)findViewById(R.id.notify_tv);
    }
}
