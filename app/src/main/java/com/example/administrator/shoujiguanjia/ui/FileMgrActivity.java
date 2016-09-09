package com.example.administrator.shoujiguanjia.ui;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.shoujiguanjia.R;
import com.example.administrator.shoujiguanjia.biz.FileManager;
import com.example.administrator.shoujiguanjia.util.FileTypeUtil;

public class FileMgrActivity extends BaseActivity implements FileManager.OnFileManagerListener{

    private TextView title;
    private TextView tvAll,tvDoc,tvVideo,tvAudio,tvPic,tvZip,tvExe;
    private ImageView ivAll,ivDoc,ivVideo,ivAudio,ivPic,ivZip,ivExe;
    private ProgressBar pbAll,pbDoc,pbVideo,pbAudio,pbPic,pbZip,pbExe;
    private FileManager fileManager;
    private String allsize;
    private static final int ON_END = 0;
    private static final int ON_SUCCESS =1;
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ON_END:
                    //隐藏进度条
                    pbAll.setVisibility(View.GONE);
                    pbDoc .setVisibility(View.GONE);
                    pbVideo.setVisibility(View.GONE);
                    pbAudio.setVisibility(View.GONE);
                    pbPic .setVisibility(View.GONE);
                    pbZip .setVisibility(View.GONE);
                    pbExe .setVisibility(View.GONE);
                    //显示箭头图标
                    ivAll.setVisibility(View.VISIBLE);
                    ivDoc.setVisibility(View.VISIBLE);
                    ivVideo.setVisibility(View.VISIBLE);
                    ivAudio.setVisibility(View.VISIBLE);
                    ivPic.setVisibility(View.VISIBLE);
                    ivZip.setVisibility(View.VISIBLE);
                    ivExe.setVisibility(View.VISIBLE);
                    title.setText(allsize);
                    break;
                case ON_SUCCESS :
                    Bundle bundle = msg.getData();
                    String type = bundle.getString("type");
                    int size = bundle.getInt("size");
                    if(type.equals(FileTypeUtil.TYPE_TXT)){
                        tvDoc.setText(Formatter.formatFileSize(FileMgrActivity.this,size));
                    }else  if(type.equals(FileTypeUtil.TYPE_IMAGE)){
                        tvPic.setText(Formatter.formatFileSize(FileMgrActivity.this,size));
                    }else  if(type.equals(FileTypeUtil.TYPE_AUDIO)){
                        tvAudio.setText(Formatter.formatFileSize(FileMgrActivity.this,size));
                    }else  if(type.equals(FileTypeUtil.TYPE_VIDEO)){
                        tvVideo.setText(Formatter.formatFileSize(FileMgrActivity.this,size));
                    }else  if(type.equals(FileTypeUtil.TYPE_ZIP)){
                        tvZip.setText(Formatter.formatFileSize(FileMgrActivity.this,size));
                    }else  if(type.equals(FileTypeUtil.TYPE_APK)){
                        tvExe.setText(Formatter.formatFileSize(FileMgrActivity.this,size));
                    }else  if(type.equals(FileTypeUtil.TYPE_ANY)){
                        allsize=  Formatter.formatFileSize(FileMgrActivity.this,size);
                        tvAll.setText(allsize);
                    }
                    break;
            }

        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        new  Thread(){
            @Override
            public void run() {
                fileManager.clear();
                fileManager.searchAllFile(fileManager.file);
            }
        }.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_mgr);
        initToolBar();
        initView();
    }
    @Override
    public void initView() {
        title = (TextView) findViewById(R.id.filemgr_tv2);
        tvAll = (TextView) findViewById(R.id.filemgr_allsize);
        tvDoc = (TextView) findViewById(R.id.filemgr_docsize);
        tvVideo = (TextView) findViewById(R.id.filemgr_vidiosize);
        tvAudio = (TextView) findViewById(R.id.filemgr_audiosize);
        tvPic = (TextView) findViewById(R.id.filemgr_picsize);
        tvZip = (TextView) findViewById(R.id.filemgr_zipsize);
        tvExe = (TextView) findViewById(R.id.filemgr_exesize);

        ivAll = (ImageView) findViewById(R.id.filemgr_allimg);
        ivDoc = (ImageView) findViewById(R.id.filemgr_docimg);
        ivVideo = (ImageView) findViewById(R.id.filemgr_vidioimg);
        ivAudio = (ImageView) findViewById(R.id.filemgr_audioimg);
        ivPic = (ImageView) findViewById(R.id.filemgr_picimg);
        ivZip = (ImageView) findViewById(R.id.filemgr_zipimg);
        ivExe = (ImageView) findViewById(R.id.filemgr_exeimg);
        pbAll = (ProgressBar) findViewById(R.id.filemgr_allpb);
        pbDoc = (ProgressBar) findViewById(R.id.filemgr_docpb);
        pbVideo = (ProgressBar) findViewById(R.id.filemgr_vidiopb);
        pbAudio = (ProgressBar) findViewById(R.id.filemgr_audiopb);
        pbPic = (ProgressBar) findViewById(R.id.filemgr_picpb);
        pbZip = (ProgressBar) findViewById(R.id.filemgr_zippb);
        pbExe = (ProgressBar) findViewById(R.id.filemgr_exepb);
        fileManager = FileManager.getInstance(this);
        fileManager.setOnFileManagerListener(FileMgrActivity.this);
    }
    public  void  doClick(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.filemgr_allclick:
                bundle.putString("type", FileTypeUtil.TYPE_ANY);
                myStartActivity(FileMgrSecondaryActivity.class,bundle);
                break;
            case R.id.filemgr_docclick:
                bundle.putString("type",FileTypeUtil.TYPE_TXT);
                myStartActivity(FileMgrSecondaryActivity.class,bundle);
                break;
            case R.id.filemgr_vidioclick:
                bundle.putString("type",FileTypeUtil.TYPE_VIDEO);
                myStartActivity(FileMgrSecondaryActivity.class,bundle);
                break;
            case R.id.filemgr_audioclick:
                bundle.putString("type",FileTypeUtil.TYPE_AUDIO);
                myStartActivity(FileMgrSecondaryActivity.class,bundle);
                break;
            case R.id.filemgr_picclick:
                bundle.putString("type",FileTypeUtil.TYPE_IMAGE);
                myStartActivity(FileMgrSecondaryActivity.class,bundle);
                break;
            case R.id.filemgr_zipclick:
                bundle.putString("type",FileTypeUtil.TYPE_ZIP);
                myStartActivity(FileMgrSecondaryActivity.class,bundle);
                break;
            case R.id.filemgr_execlick:
                bundle.putString("type",FileTypeUtil.TYPE_APK);
                myStartActivity(FileMgrSecondaryActivity.class,bundle);
                break;
        }
    }
    @Override
    public void onSuccess(final int size ,final String type) {
        Message msg = handler.obtainMessage();
        msg.what= ON_SUCCESS;
        Bundle bundle = new Bundle();
        bundle.putInt("size",size);
        bundle.putString("type",type);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }
    @Override
    public void onEnd() {
        handler.sendEmptyMessage(ON_END);
    }
}