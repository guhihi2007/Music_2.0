package org.music_20.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.music_20.Gpp;
import org.music_20.R;
import org.music_20.base.CommonClickListener;
import org.music_20.base.CommonRecycleAdapter;
import org.music_20.base.CommonViewHolder;
import org.music_20.base.MultiItemViewType;
import org.music_20.database.modify.DB_ModifyPlayList;

/**
 * Created by Administrator on 2017/4/25.
 */

class SetAdapter extends CommonRecycleAdapter<Data> implements MultiItemViewType {
    private CommonClickListener commonClickListener;

    public SetAdapter(Context context, CommonClickListener commonClickListener) {
        super(context);
        this.commonClickListener = commonClickListener;
        this.multiItemViewType = this;
    }

    @Override
    protected void bindData(final CommonViewHolder holder, final Data data, final int position) {
        holder.setText(R.id.set_et, data.getName());
        holder.getView(R.id.set_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomDialog dialog = new CustomDialog(context);
                dialog.show();
                dialog.setCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setokListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Gpp.v("SetAdapterList大小:" + datas.size());
                        Gpp.v("SetAdapterposition:" + position);
                        DB_ModifyPlayList dmp = new DB_ModifyPlayList(context, datas.get(position).getName());
                        dmp.deleteTable();
                        datas.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            }
        });
//        holder.setCommonClickListener(commonClickListener);
    }

    @Override
    public int getLayouId(Object item, int position) {
        return R.layout.set_layout;
    }
}
