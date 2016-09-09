package com.example.administrator.shoujiguanjia.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.shoujiguanjia.R;
import com.example.administrator.shoujiguanjia.entity.RunningAppInfo;

import java.util.List;

public class MyAppProcessAdapter extends MyBaseAdapter {
    private  Context context;
    public MyAppProcessAdapter(@NonNull List<RunningAppInfo> data, @NonNull Context context) {
        super(data, context);
        this.context=context;
    }
    @Override
    public View MyGetView(int position, View convertView, ViewGroup parent) {
        final   VH vh;
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.item_process, null);
            ImageView icon = (ImageView) convertView.findViewById(R.id.filemgr_secondaray_listitem_iv);
            TextView appName = (TextView) convertView.findViewById(R.id.tv_application_name);
            TextView memorySize = (TextView) convertView.findViewById(R.id.tv_memory_size);
            TextView appType = (TextView) convertView.findViewById(R.id.tv_app_type);
            CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            vh=new VH(icon,  appName,  memorySize,  appType,checkbox);
            convertView.setTag(vh);
        }
        else {
            vh = (VH) convertView.getTag();
        }
        RunningAppInfo runningAppInfo = (RunningAppInfo) getData().get(position);
        vh.icon.setImageDrawable(runningAppInfo.appIcon);
        vh.appName.setText(runningAppInfo.label);
        vh.memorySize.setText(String.format("内存：%s",runningAppInfo.memorySize));
        vh.appType.setText(runningAppInfo.isSysApp?"系统进程":"用户进程");
        vh.checkbox.setTag(runningAppInfo);
        vh.checkbox.setChecked(runningAppInfo.isSelect());
        vh.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CheckBox cb= (CheckBox) buttonView;
                RunningAppInfo appInfo = (RunningAppInfo) cb.getTag();
                appInfo.setSelect(isChecked);
            }
        });
        return convertView ;
    }
    class  VH{
        ImageView icon;
        TextView appName;
        TextView memorySize;
        TextView appType;
        CheckBox checkbox;
        public VH(ImageView icon, TextView appName, TextView memorySize, TextView appType,CheckBox checkbox) {
            this.icon = icon;
            this.appName = appName;
            this.memorySize = memorySize;
            this.appType = appType;
            this.checkbox=checkbox;
        }
    }
}