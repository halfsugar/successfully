package com.example.administrator.shoujiguanjia.ui;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import com.example.administrator.shoujiguanjia.R;
import com.example.administrator.shoujiguanjia.adapter.ShowSoftAdatapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class ShowSoftActivity extends BaseActivity  {
    public final int OK=1;
    private ShowSoftAdatapter adapter;
    private ProgressDialog dialog;
    private ArrayList<String> myPackageName;
    private MyReceiver receiver;
    private Handler h=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==OK){
                if(msg.obj instanceof List){
                    List<PackageInfo> data = (List<PackageInfo>) msg.obj;
                    dialog.dismiss();
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_soft);
        initToolBar();
        initView();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
    @Override
    public void initView() {
        receiver=new MyReceiver();
        IntentFilter filter=new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        registerReceiver(receiver,filter);
        dialog = new ProgressDialog(this);
        dialog.setMessage("数据加载中...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        ListView list_soft = (ListView) findViewById(R.id.list_soft);
        Intent intent = getIntent();
        int flag= intent.getIntExtra("flags",-1);
        asyncLoadData(flag);
        List<PackageInfo> data=new ArrayList<>();
        adapter= new ShowSoftAdatapter(data,this);
        list_soft.setAdapter(adapter);
        CheckBox checkAll = (CheckBox) findViewById(R.id.checkbox);
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                adapter.setAllItemChecked(isChecked);
                adapter.notifyDataSetChanged();

            }
        });
        findViewById(R.id.btn_uninstall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPackageName = getMyPackageName(adapter.getIschecks());
                for (String packageName : myPackageName) {
                    Intent intent=new Intent(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:"+packageName));
                    startActivity(intent);
                }
            }
        });
    }
    private void asyncLoadData(final int flag) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<PackageInfo> data = selectInstalledpackage(flag);
                Message m = new Message();
                m.what=OK;
                m.obj=data;
                h.sendMessage(m);
            }
        }.start();
    }
    private ArrayList<String> getMyPackageName(HashMap<String, Boolean> ischecks) {
        ArrayList<String> packageNames = new ArrayList<>();
        Set<Map.Entry<String, Boolean>> entries = ischecks.entrySet();
        Iterator<Map.Entry<String, Boolean>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Boolean> next = iterator.next();
            String key = next.getKey();
            Boolean value = next.getValue();
            if(value){
                packageNames.add(key);
            }
        }
        return packageNames;
    }
    private List<PackageInfo> selectInstalledpackage(int flag) {
        List<PackageInfo> alldata=this.getPackageManager().getInstalledPackages(
                PackageManager.GET_UNINSTALLED_PACKAGES
        );
        if(flag==SoftManagerActivity.All_SOFT){
            return alldata;
        }
        List<PackageInfo> data= new ArrayList<>();
        Iterator<PackageInfo> iterator=alldata.iterator();
        while (iterator.hasNext()){
            PackageInfo packageInfo = iterator.next();
            int flags=packageInfo.applicationInfo.flags;
            switch (flag){
                case  SoftManagerActivity.SYSTEM_SOFT:
                    if((flags& ApplicationInfo.FLAG_SYSTEM)==1){
                        data.add(packageInfo);
                    }
                    break;
                case  SoftManagerActivity.USER_SOFT:
                    if((flags& ApplicationInfo.FLAG_SYSTEM)!=1){
                        data.add(packageInfo);
                    }
                    break;
            }
        }
        return  data;
    }
    class  MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            for (String s : myPackageName) {
                adapter.getIschecks().remove(s);
            }
            adapter.notifyDataSetChanged();
        }
    }
}