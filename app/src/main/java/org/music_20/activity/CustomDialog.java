package org.music_20.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.music_20.R;

/**
 * Created by Administrator on 2017/5/2.
 */

public class CustomDialog extends AlertDialog {

    private Button cancel,ok;
    private TextView title,msg_tv;
    protected CustomDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customdialog_layout);
        setCanceledOnTouchOutside(true);//点击对话框以外区域是否可以取消
        cancel=(Button)findViewById(R.id.custom_cancel);
        ok=(Button)findViewById(R.id.custom_ok);
        msg_tv=(TextView)findViewById(R.id.custom_msg);
    }
    public void setMsg(String msg){
        msg_tv.setText(msg);
    }
    public void setCancelListener(View.OnClickListener cancelListener){
        cancel.setOnClickListener(cancelListener);
    }
    public void setokListener(View.OnClickListener okListener){
        ok.setOnClickListener(okListener);
    }

}
