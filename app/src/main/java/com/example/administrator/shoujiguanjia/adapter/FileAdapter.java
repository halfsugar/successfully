package com.example.administrator.shoujiguanjia.adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.shoujiguanjia.R;
import com.example.administrator.shoujiguanjia.entity.FileInfo;

import java.util.ArrayList;

public class FileAdapter extends MyBaseAdapter<FileInfo> implements CompoundButton.OnCheckedChangeListener{

    private ViewHolder vh=null;

    public FileAdapter(ArrayList<FileInfo> data, Context context) {
        super(data, context);
    }
    @Override
    public View MyGetView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.activity_filemgr_secondary_listitem, null);
            vh.cb = (CheckBox) convertView.findViewById(R.id.filemgr_secondary_listitem_cb);
            vh.iv = (ImageView) convertView.findViewById(R.id.filemgr_secondary_listitem_iv);
            vh.tv1 = (TextView) convertView.findViewById(R.id.filemgr_secondary_listitem_lable1);
            vh.tv2 = (TextView) convertView.findViewById(R.id.filemgr_secondary_listitem_lastmodify);
            vh.tv3 = (TextView) convertView.findViewById(R.id.filemgr_secondary_listitem_size);
            convertView.setTag(vh);
        }else {
            vh  = (ViewHolder) convertView.getTag();
        }
        FileInfo info = getData().get(position);
        vh.cb.setTag(position);
        vh.cb.setChecked(info.isChecked());
        int res = context.getResources().getIdentifier(info.getFileIcon(),
                "drawable", context.getPackageName());
        vh.cb.setOnCheckedChangeListener(this);
        vh.iv.setImageResource(res);
        vh.tv1.setText(info.getName());
        vh.tv2.setText(info.getLastModify());
        vh.tv3.setText(info.getSize());
        return convertView;
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        int position = (int) buttonView.getTag();
        FileInfo info = getData().get(position);
        info.setChecked(isChecked);
    }
    class  ViewHolder{
        CheckBox cb;
        ImageView iv;
        TextView tv1,tv2,tv3;
    }
}
