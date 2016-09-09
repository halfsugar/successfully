package com.example.administrator.shoujiguanjia.ui;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import com.example.administrator.shoujiguanjia.R;
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private ToggleButton tb_notif;
    private NotificationManager manager;
    private int numMessages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        initToolBar();
        initView();
    }
    @Override
    public void initView() {
        findViewById(R.id.gywm).setOnClickListener(this);
        initMainButton();
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.gywm:
                myStartActivity(AboutActivity.class);
                break;
        }
    }
    private void initMainButton() {
        final NotificationCompat.Builder builder= new NotificationCompat.Builder(this);
        tb_notif = (ToggleButton) findViewById(R.id.tb_toggle_notification);
        final SharedPreferences state = getSharedPreferences("state", Context.MODE_PRIVATE);
        tb_notif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                state.edit().putBoolean("state",isChecked).apply();
                if (isChecked) {
                    builder.setTicker("您有一条新消息！")
                            .setContentTitle("手机管家")
                            .setContentText("手机管家的通知...")
                            .setSmallIcon(R.drawable.ic_launcher);
                    Notification build = builder.build();
                    manager.notify(110,build);
                }
                else {
                    manager.cancel(110);
                }
            }
        });
        boolean state1 = state.getBoolean("state", false);
        tb_notif.setChecked(state1);
    }
}
