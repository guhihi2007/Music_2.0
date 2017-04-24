package org.music_20.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.music_20.base.InitView;
import org.music_20.R;
import org.music_20.database.modify.DB_ModifyPlayList;
import org.music_20.database.modify.DB_ModifyPlayListContent;


/**
 * Created by Administrator on 2017/4/20.
 */

public class AddListAcitvity extends Activity implements InitView, View.OnClickListener {

    public static final int RequestCode=1;
    private ImageView serach_btn, action_back;
    private TextView action_tv;
    private Intent intent;
    private Handler handler;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlist);
        intent = getIntent();
        findView();
        setListener();
    }

    @Override
    public void findView() {
        action_tv = (TextView) findViewById(R.id.action_tv);
        action_back = (ImageView) findViewById(R.id.back_btn);
        serach_btn = (ImageView) findViewById(R.id.search_btn);
        editText = (EditText) findViewById(R.id.add_list_et);
    }

    @Override
    public void setListener() {
        action_tv.setText("新建列表");
        serach_btn.setImageResource(R.mipmap.ok);
        action_back.setOnClickListener(this);
        serach_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.search_btn:
                Intent back = new Intent();
                String input_name = editText.getText().toString();
                if (isEmpty(input_name)) {
                    Toast.makeText(this, "列表名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                back.putExtra("list_name", input_name);
                DB_ModifyPlayList modifyPlayList = new DB_ModifyPlayList(this,input_name);
                modifyPlayList.add_Table();//添加新数据播放列表名称到数据库
                this.setResult(RequestCode, back);
                this.finish();
                break;
        }
    }

    private boolean isEmpty(String string) {
        if (string == null || string.equals("")) {
            return true;
        } else return false;
    }
}

