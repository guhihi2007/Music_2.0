package org.music_20.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import org.music_20.base.CommonClickListener;
import org.music_20.base.InitView;
import org.music_20.R;
import org.music_20.database.modify.DB_ModifyPlayList;
import org.music_20.service.CoreService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements InitView, View.OnClickListener, CommonClickListener {

    private ImageView play, next, previous, model, list;
    private ViewPager vp;
    private List<View> viewList;
    private RecyclerView RLView_1, RLView_2, RLView_3, RLView_4;
    private View View_1, View_2, View_3, View_4;
    private ArrayList<Folder> dblist;
    private CoreService coreService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setListener();
        startCoreService();
    }


    @Override
    public void findView() {
//        addsonglist();
        play = (ImageView) findViewById(R.id.play);
        next = (ImageView) findViewById(R.id.next);
        previous = (ImageView) findViewById(R.id.previous);
        model = (ImageView) findViewById(R.id.model);
        list = (ImageView) findViewById(R.id.list);

        vp = (ViewPager) findViewById(R.id.vp);
        viewList = new ArrayList<>();

        LayoutInflater inflater = getLayoutInflater();
        View_1 = inflater.inflate(R.layout.vp_1, null);
        View_2 = inflater.inflate(R.layout.vp_2, null);
        View_3 = inflater.inflate(R.layout.vp_3, null);
        View_4 = inflater.inflate(R.layout.vp_4, null);

        RLView_1 = (RecyclerView) View_1.findViewById(R.id.RL_1);
        RLView_2 = (RecyclerView) View_2.findViewById(R.id.RL_2);
        RLView_3 = (RecyclerView) View_3.findViewById(R.id.RL_3);
        RLView_4 = (RecyclerView) View_4.findViewById(R.id.RL_4);

    }

    @Override
    public void setListener() {
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        model.setOnClickListener(this);
        list.setOnClickListener(this);

        viewList.add(View_1);
        viewList.add(View_2);
        viewList.add(View_3);
        viewList.add(View_4);

//        CommonRecycleAdapter adapter = new SearchAdapter(this,this);
//        adapter.setItemListener(new RecycleAdapter.ItemListener() {
//            @Override
//            public void OnItemClickListener(View view, int position) {
//                adapter.setSelectItem(position);
//            }
//
//            @Override
//            public boolean OnItemLongClickListener(View view, int position) {
//                adapter.setShowcheckbox();
//                adapter.setSelectItem(position);
//                adapter.notifyDataSetChanged();
//                return true;
//            }
//        });
        RLView_1.setLayoutManager(new LinearLayoutManager(this));
//        RLView_1.setAdapter(adapter);
        RLView_1.setHasFixedSize(true);
        RLView_1.setItemAnimator(new DefaultItemAnimator());
        RLView_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
//        RLView_1.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
//        RLView_2.setLayoutManager(new LinearLayoutManager(this));
//        RLView_2.setAdapter(new RecycleAdapter(this, strings));
//
//        RLView_3.setLayoutManager(new LinearLayoutManager(this));
//        RLView_3.setAdapter(new RecycleAdapter(this, strings));
        vp.setAdapter(new vpAdatper(this, viewList));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                Log.v("gpp", "点击play_btn");
                break;
            case R.id.next:
                break;
            case R.id.previous:
                break;
            case R.id.model:
                break;
            case R.id.list:
                Log.v("gpp", "点击list_btn");
                DB_ModifyPlayList dbModifyPlayList = new DB_ModifyPlayList(this, null);
                dblist = dbModifyPlayList.getPlayList();//从数据库取播放列表数据
                Log.v("gpp", "读取DB播放列表:" + (dblist == null ? null : dblist.size()));
                Intent intent = new Intent();
                intent.setClass(this, ListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dblist", dblist);//用bundle存储传递到下一个activity
                intent.putExtras(bundle);
                intent.putExtra("title_name", "播放列表");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void OnCommonClickListener(View v, int position) {

    }

    @Override
    public boolean OnCommonLongClickListener(View v, int position) {
        return false;
    }

    private void startCoreService() {
        final Intent server = new Intent();
        server.setClass(this, CoreService.class);
        bindService(server, conn,BIND_AUTO_CREATE);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CoreService.CoreServiceBinder serviceBinder= (CoreService.CoreServiceBinder)service;
            coreService= serviceBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


}
