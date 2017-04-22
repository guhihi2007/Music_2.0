package org.music_20.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import org.music_20.R;

/**
 * Created by Administrator on 2017/4/22.
 */

public class CheckDialog extends Dialog {
    private TextView checkdialog_tv;
    private String msg;
    public CheckDialog(Context context,String msg) {
        super(context, R.style.CheckDialog);
        this.msg=msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkdialog);
        checkdialog_tv=(TextView)findViewById(R.id.checkdialog_tv);
        checkdialog_tv.setText(msg);
        setCanceledOnTouchOutside(false);
    }
}
