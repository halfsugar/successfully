package com.example.administrator.shoujiguanjia.ui;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.example.administrator.shoujiguanjia.R;
public  abstract class BaseActivity extends AppCompatActivity{
    private final boolean isOpenTestToast=false;
    Toolbar toolbar;
    ActionBar actionBar;
    public  void showTestToast(String msg){
        if (isOpenTestToast){
            Toast.makeText(BaseActivity.this,msg,Toast.LENGTH_LONG).show();
        }
    }
    public  void showToast(String msg){
        Toast.makeText(BaseActivity.this,msg,Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initToolBar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    public void myStartActivity(Class clazz)
    {
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
    public void myStartActivity(Class clazz,Bundle bundle)
    {
        Intent intent = new Intent(this,clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public abstract void initView();
}