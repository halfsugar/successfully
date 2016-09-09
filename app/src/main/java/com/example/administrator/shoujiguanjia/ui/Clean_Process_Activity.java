package com.example.administrator.shoujiguanjia.ui;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.administrator.shoujiguanjia.R;
import com.example.administrator.shoujiguanjia.adapter.MyAppProcessAdapter;
import com.example.administrator.shoujiguanjia.entity.RunningAppInfo;
import com.example.administrator.shoujiguanjia.running_pro.AndroidAppProcess;
import com.example.administrator.shoujiguanjia.running_pro.ProcessManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class Clean_Process_Activity extends BaseActivity implements View.OnClickListener {
    public ActivityManager activityManager;
    List<RunningAppInfo> runningAppInfos;
    MyAppProcessAdapter appAdapter;
    ProgressBar pb_memory;
    TextView tv_memory;
    ProgressBar pb;
    public long totalMem;
    public long availMem;
    public ActivityManager.MemoryInfo memoryinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);
        initToolBar();
        initView();
    }
    @Override
    public void initView() {
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        TextView tv_brand = (TextView) findViewById(R.id.tv_brand);
        TextView tv_phone_type = (TextView) findViewById(R.id.tv_phone_type);
        tv_memory = (TextView) findViewById(R.id.tv_memory);
        ListView list_process = (ListView) findViewById(R.id.list_process);
        pb = (ProgressBar) findViewById(R.id.pb);
        pb_memory = (ProgressBar) findViewById(R.id.pb_phone_memory_use_rate);
        findViewById(R.id.btn_clean_process).setOnClickListener(this);
        findViewById(R.id.btn_show_process).setOnClickListener(this);
        ( (CheckBox)findViewById(R.id.checkbox)).setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        List<RunningAppInfo> data = appAdapter.getData();
                        for (RunningAppInfo runningAppInfo : data) {
                            runningAppInfo.setSelect(isChecked);
                        }
                        appAdapter.notifyDataSetChanged();
                    }
                });
        String brand = Build.BRAND;
        String type= "Android" + getPhoneSystemVersion();
        int sdkInt = Build.VERSION.SDK_INT;
        if(sdkInt>=21){
            runningAppInfos =getRunningAppInfo21();
        }
        else{
            runningAppInfos = getRunningAppInfo();
        }
        appAdapter = new MyAppProcessAdapter(runningAppInfos, this);
        list_process.setAdapter(appAdapter);
        tv_brand.setText(brand);
        tv_phone_type.setText(type);
        setMemoryInfo();
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setMemoryInfo(){
        memoryinfo = new  ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryinfo);
        availMem = memoryinfo.availMem;
        totalMem = memoryinfo.totalMem;
        String strTotalMem = Formatter.formatFileSize(this, totalMem);
        String strUseMem = Formatter.formatFileSize(this, totalMem - availMem);
        tv_memory.setText(strUseMem+"/"+strTotalMem);
        pb_memory.setMax((int) totalMem);
        pb_memory.setProgress((int) (totalMem - availMem));
    }
    private String getPhoneSystemVersion() {
        return Build.VERSION.CODENAME + Build.VERSION.RELEASE;
    }
    public List<RunningAppInfo> getRunningAppInfo21() {
        List<RunningAppInfo> runningAppInfos = new ArrayList<>();
        List<AndroidAppProcess> processInfos = ProcessManager.getRunningAppProcesses();
        Iterator<AndroidAppProcess> iterator = processInfos.iterator();
        while (iterator.hasNext()) {
            AndroidAppProcess appProcess = iterator.next();
            int pid = appProcess.pid;
            String processName = appProcess.getPackageName();
            Debug.MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(new int[]{pid});
            Debug.MemoryInfo memoryInfo = processMemoryInfo[0];
            long totalPrivateDirty = memoryInfo.getTotalPrivateDirty() * 1024;
            String memorySize = Formatter.formatShortFileSize(this, totalPrivateDirty);
            try {
                PackageManager pm = getPackageManager();
                PackageInfo packageInfo = pm.
                        getPackageInfo(processName, PackageManager.GET_UNINSTALLED_PACKAGES);
                Drawable drawable = packageInfo.applicationInfo.loadIcon(pm);
                String label = (String) packageInfo.applicationInfo.loadLabel(pm);
                boolean isSysApp = (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1;
                RunningAppInfo runningAppInfo = new RunningAppInfo(label,processName, memorySize, drawable, isSysApp, 0);
                runningAppInfos.add(runningAppInfo);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return runningAppInfos;
    }
    public List<RunningAppInfo> getRunningAppInfo() {
        List<RunningAppInfo> runningAppInfos = new ArrayList<>();
        List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        Iterator<ActivityManager.RunningAppProcessInfo> iterator = processInfos.iterator();
        while (iterator.hasNext()) {
            ActivityManager.RunningAppProcessInfo appProcess = iterator.next();
            int pid = appProcess.pid;
            String processName = appProcess.processName;
            Debug.MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(new int[]{pid});
            Debug.MemoryInfo memoryInfo = processMemoryInfo[0];
            long totalPrivateDirty = memoryInfo.getTotalPrivateDirty() * 1024;
            String memorySize = Formatter.formatShortFileSize(this, totalPrivateDirty);
            try {
                PackageManager pm = getPackageManager();
                PackageInfo packageInfo = pm.
                        getPackageInfo(processName, PackageManager.GET_UNINSTALLED_PACKAGES);
                Drawable drawable = packageInfo.applicationInfo.loadIcon(pm);
                String label = (String) packageInfo.applicationInfo.loadLabel(pm);
                boolean isSysApp = (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1;
                RunningAppInfo runningAppInfo = new RunningAppInfo(label,processName, memorySize, drawable, isSysApp, 0);
                runningAppInfos.add(runningAppInfo);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return runningAppInfos;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_clean_process:
                List<RunningAppInfo> killProcess =new ArrayList<>();
                if(runningAppInfos!=null){
                    for (int i = 0; i < runningAppInfos.size(); i++) {
                        RunningAppInfo runningAppInfo = runningAppInfos.get(i);
                        boolean select = runningAppInfo.isSelect();
                        if (select){
                            activityManager.killBackgroundProcesses(runningAppInfo.getPackageName());
                            killProcess.add(runningAppInfo);
                        }
                    }
                }
                runningAppInfos.removeAll(killProcess);
                appAdapter.notifyDataSetChanged();
                setMemoryInfo();
                break;
            case R.id.btn_show_process:
                Button b = (Button) v;
                String text = (String) b.getText();
                List<RunningAppInfo> process= new ArrayList<>();
                boolean isShowSysApp = text.equals("显示系统进程");
                if(isShowSysApp){
                    for (RunningAppInfo runningAppInfo : runningAppInfos) {
                        boolean isSysApp = runningAppInfo.isSysApp();
                        if (isSysApp){
                            process.add(runningAppInfo);
                        }
                    }
                }
                else if(!isShowSysApp){
                    for (RunningAppInfo runningAppInfo : runningAppInfos) {
                        boolean isSysApp = runningAppInfo.isSysApp();
                        if (!isSysApp){
                            process.add(runningAppInfo);
                        }
                    }
                }
                b.setText(isShowSysApp?"只显示用户进程":"显示系统进程");
                appAdapter.setData(process);
                appAdapter.notifyDataSetChanged();
                break;
        }
    }
}