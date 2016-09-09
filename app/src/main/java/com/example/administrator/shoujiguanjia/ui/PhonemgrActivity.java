package com.example.administrator.shoujiguanjia.ui;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.administrator.shoujiguanjia.R;
import com.example.administrator.shoujiguanjia.adapter.PhonemgrAdapter;
import com.example.administrator.shoujiguanjia.biz.MemoryManager;
import com.example.administrator.shoujiguanjia.entity.PhoneInfo;
import com.example.administrator.shoujiguanjia.entity.PhoneManager;
import java.util.ArrayList;
import java.util.List;
public class PhonemgrActivity extends BaseActivity {
    private TextView tv_battery;
    private BatteryBroadcastReceiver broadcastReceiver;
    private Integer currentBattery;
    private ListView exListView;
    private PhonemgrAdapter adapter;
    private ProgressDialog dialog;
    private List<PhoneInfo> phoneInfos =new ArrayList<>();
    PhoneManager manager =  new PhoneManager();
    private LinearLayout layout_battery;
    private ProgressBar pb_battery;
    private Integer temperatureBattery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonemgr);
        initToolBar();initView();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
    @Override
    public void initView() {
        layout_battery = (LinearLayout) findViewById(R.id.ll_layout_battery);
        tv_battery = (TextView) findViewById(R.id.tv_battery);
        pb_battery = (ProgressBar) findViewById(R.id.pb_battery);
        broadcastReceiver = new BatteryBroadcastReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver, filter);
        dialog = new ProgressDialog(this);
        dialog.setMessage("数据加载中...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        exListView = (ListView) findViewById(R.id.listviewLoad);
        asyncLoadData();
        adapter =new PhonemgrAdapter(phoneInfos,this);
        exListView.setAdapter(adapter);
    }
    private void asyncLoadData() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                    initAdapterData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();
    }
    public class BatteryBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                Bundle bundle = intent.getExtras();
                int maxBattery = (Integer) bundle.get(BatteryManager.EXTRA_SCALE);
                currentBattery = (Integer) bundle.get(BatteryManager.EXTRA_LEVEL);
                temperatureBattery = (Integer)bundle.get(BatteryManager.EXTRA_TEMPERATURE);
                pb_battery.setMax(maxBattery);
                pb_battery.setProgress(currentBattery);
                int current100 = currentBattery * 100 / maxBattery;
                tv_battery.setText(current100 + "%");
            }
        }
    }
    public void  setPhoneInfo( int icon,String title, String text){
        PhoneInfo phoneInfo = new PhoneInfo(icon,title,text);
        phoneInfos.add(phoneInfo);

    }
    public void initAdapterData() {
        setPhoneInfo(R.drawable.setting_info_icon_version, "设备名称:" + manager.getPhoneBrand(),"系统版本:" + manager.getPhoneSystemVersion());
        setPhoneInfo(R.drawable.setting_info_icon_space, "全部运行的内存:" + MemoryManager.getPhoneTotalRamMemory()/1024/1024+"M" ,
                "剩余的运行内存:" + MemoryManager.getPhoneFreeRamMemory(this)/1024/1024+"M");
        setPhoneInfo(R.drawable.setting_info_icon_cpu, "cpu名称:" + manager.getPhoneCpuName(),"cpu数量:" + manager.getPhoneCpuNumber());
        setPhoneInfo(R.drawable.setting_info_icon_camera, "手机分辩率:" + manager.getWindowPixel(PhonemgrActivity.this),"相机分辩率:" );
        setPhoneInfo(R.drawable.setting_info_icon_root, "基带版本:" + manager.getRadio(),"是否 ROOT:" + (manager.isRoot() ?"是":"否"));
        adapter.setData(phoneInfos);
    }
}
