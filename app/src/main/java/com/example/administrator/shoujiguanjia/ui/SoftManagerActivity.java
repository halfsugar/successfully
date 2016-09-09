package com.example.administrator.shoujiguanjia.ui;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.administrator.shoujiguanjia.R;
import com.example.administrator.shoujiguanjia.biz.MemoryManager;
import com.example.administrator.shoujiguanjia.view.PiechartView;
public class SoftManagerActivity extends BaseActivity implements View.OnClickListener {
    final   public static int All_SOFT=2;
    final   public static int SYSTEM_SOFT=1;
    final   public static int USER_SOFT=0;
    private PiechartView p;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_softmgr_spacemem);
        initToolBar();
        initView();
        p.setPiechartProportionWithAnim(45.0f,20.0f);
    }
    @Override
    public void initView() {
        findViewById(R.id.all_soft).setOnClickListener(this);
        findViewById(R.id.system_soft).setOnClickListener(this);
        findViewById(R.id.user_soft).setOnClickListener(this);
        p = (PiechartView) findViewById(R.id.piechart);
        ProgressBar pb_phone_space = (ProgressBar) findViewById(R.id.pb_phone_space);
        ProgressBar pb_sdcard_space = (ProgressBar) findViewById(R.id.pb_sdcard_space);
        long outSDCardSize= MemoryManager.getPhoneOutSDCardSize(this)/1024/1024;
        pb_sdcard_space.setMax((int)outSDCardSize);
        long outSDCardFreeSize=MemoryManager.getPhoneOutSDCardFreeSize(this)/1024/1024;
        pb_sdcard_space.setProgress((int)(outSDCardSize-outSDCardFreeSize));
        TextView tv_out = (TextView) findViewById(R.id.tv_sdcard_space_msg);
        tv_out.setText(outSDCardSize-outSDCardFreeSize+"/"+outSDCardSize+"M");
        long phoneSelfSDCardSize = MemoryManager.getPhoneSelfSDCardSize()/1024/1024;
        long phoneAllFreeSize = MemoryManager.getPhoneSelfSDCardFreeSize()/1024/1024;
        pb_phone_space.setMax((int) phoneSelfSDCardSize);
        int progress= (int) (phoneSelfSDCardSize-phoneAllFreeSize);
        pb_phone_space.setProgress(progress);
        TextView tv_internal = (TextView) findViewById(R.id.tv_phone_space_msg);
        tv_internal.setText(progress+"/"+phoneSelfSDCardSize+"M");
    }
    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.all_soft:
                intent.setClass(this,ShowSoftActivity.class);
                intent.putExtra("flags",All_SOFT);
                break;
            case R.id.system_soft:
                intent.setClass(this,ShowSoftActivity.class);
                intent.putExtra("flags",SYSTEM_SOFT);
                break;
            case R.id.user_soft:
                intent.setClass(this,ShowSoftActivity.class);
                intent.putExtra("flags",USER_SOFT);
                break;
        }
        startActivity(intent);
    }
}
