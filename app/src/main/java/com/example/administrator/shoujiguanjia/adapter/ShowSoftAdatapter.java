package com.example.administrator.shoujiguanjia.adapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.shoujiguanjia.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class ShowSoftAdatapter extends MyBaseAdapter<PackageInfo> {
    private final PackageManager packageManager;
    private  Context context;
    public HashMap<String, Boolean> getIschecks() {
        return ischecks;
    }
    HashMap<String,Boolean> ischecks=new HashMap<>();
    public ShowSoftAdatapter(List<PackageInfo> data, Context context) {
        super(data, context);
        packageManager = context.getPackageManager();
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View MyGetView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_soft,null);
        PackageInfo packageInfo = getData().get(position);
        int versionCode = packageInfo.versionCode;
        final String packageName = packageInfo.packageName;
        CharSequence lable = packageInfo.applicationInfo.loadLabel(packageManager);
        Drawable drawable = packageInfo.applicationInfo.loadIcon(packageManager);
        TextView tv_application_name = (TextView) convertView.findViewById(R.id.tv_application_name);
        tv_application_name.setText(lable);
        ImageView iv = (ImageView) convertView.findViewById(R.id.filemgr_secondaray_listitem_iv);
        iv.setBackground(drawable);
        TextView tv_code = (TextView) convertView.findViewById(R.id.tv_code);
        tv_code.setText(versionCode+"");
        TextView tv_package_name = (TextView) convertView.findViewById(R.id.tv_package_name);
        tv_package_name.setText(packageName);
        final CheckBox box = (CheckBox) convertView.findViewById(R.id.checkbox);
        final Boolean isCheck = ischecks.get(packageName);
        box.setChecked(isCheck==null?false:isCheck);
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ischecks.put(packageName,isChecked);
            }
        });
        return convertView;
    }
    public void setAllItemChecked(boolean isChecked) {
        List<PackageInfo> data = getData();
        ArrayList<String> packageNames =new ArrayList<>();
        for(PackageInfo packageInfo:data){
            packageNames.add(packageInfo.packageName);
        }
        for (int i = 0; i <data.size(); i++) {
            ischecks.put(packageNames.get(i),isChecked);
        }
    }
}
