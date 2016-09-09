package com.example.administrator.shoujiguanjia.ui;
import android.os.Bundle;
import com.example.administrator.shoujiguanjia.R;
public class AboutActivity extends BaseActivity {
    @Override
    public void initView() {}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initToolBar();
    }
}