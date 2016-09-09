package com.example.administrator.shoujiguanjia.ui;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.shoujiguanjia.R;
import com.example.administrator.shoujiguanjia.view.ClearArcView;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ClearArcView c;
    public long totalMem;
    public long availMem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
        initView();
        c.setAngleWithAnim(60);
    }
    public void initView(){
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        findViewById(R.id.telmgr).setOnClickListener(this);
        findViewById(R.id.phonemgr).setOnClickListener(this);
        findViewById(R.id.rocket).setOnClickListener(this);
        findViewById(R.id.sdclean).setOnClickListener(this);
        findViewById(R.id.softmgr).setOnClickListener(this);
        findViewById(R.id.filemgr).setOnClickListener(this);
        findViewById(R.id.iv_homeclear).setOnClickListener(this);
        c = (ClearArcView) findViewById(R.id.homeclear_arc);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.share:
                showToast("share");
                break;
            case R.id.delete:

                showToast("delete");
                break;
            case R.id.save:
                Toast.makeText(MainActivity.this,"save",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                myStartActivity(SettingActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.phonemgr:
                myStartActivity(PhonemgrActivity.class);
                break;
            case R.id.rocket:
                myStartActivity(Clean_Process_Activity.class);
                break;
            case R.id.telmgr:
                myStartActivity(ShowTelNumTypeActivity.class);
                break;
            case R.id.sdclean:
                break;
            case R.id.softmgr:
                myStartActivity(SoftManagerActivity.class);
                break;
            case R.id.filemgr:
                myStartActivity(FileMgrActivity.class);
                break;
            case R.id.iv_homeclear:
                c.setAngleWithAnim(90);
                break;
        }
    }
}