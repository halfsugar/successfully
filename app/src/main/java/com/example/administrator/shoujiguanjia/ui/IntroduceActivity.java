package com.example.administrator.shoujiguanjia.ui;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.example.administrator.shoujiguanjia.R;
import com.example.administrator.shoujiguanjia.fragment.IntroduceFragment;

public class IntroduceActivity extends BaseActivity implements View.OnClickListener {
    public  int resIds[]={
            R.mipmap.adware_style_applist,
            R.mipmap.adware_style_banner,
            R.mipmap.adware_style_creditswall};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        findViewById(R.id.tv_skip).setOnClickListener(this);
        SharedPreferences preferences =
                getSharedPreferences("lead_config", Context.MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean("isFirstRun", true);
        if (!isFirstRun) {
            myStartActivity(WelcomeActivity.class);
            finish();
        }
        else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstRun", false);
            editor.apply();
            initView();
            Intent serviceIntent = new Intent(this,MusicService.class);
            startService(serviceIntent);
        }
    }
    @Override
    public void initView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        MyFragmentStateAdapter adapter=new MyFragmentStateAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        final LinearLayout tv_container = (LinearLayout) findViewById(R.id.container);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                int count = tv_container.getChildCount();
                for (int i = 0; i <count ; i++) {
                    View childAt = tv_container.getChildAt(i);
                    childAt.setBackgroundDrawable(getResources().getDrawable(R.drawable.adware_style_default));
                }
                View view = tv_container.getChildAt(position);
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.adware_style_selected));
            }
        });
    }
    @Override
    public void onClick(View v) {
        Intent musicIntent = new Intent(this,MusicService.class);
        stopService(musicIntent);
        myStartActivity(WelcomeActivity.class);
        finish();
    }
    class MyFragmentStateAdapter extends FragmentStatePagerAdapter{
        public MyFragmentStateAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            IntroduceFragment fragment=new IntroduceFragment();
            Bundle bubdle=new Bundle();
            bubdle.putInt("position",position);
            fragment.setArguments(bubdle);
            return fragment;
        }
        @Override
        public int getCount() {
            return resIds.length;
        }
    }
}