package com.example.administrator.shoujiguanjia.ui;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.shoujiguanjia.R;
import com.example.administrator.shoujiguanjia.adapter.FileAdapter;
import com.example.administrator.shoujiguanjia.biz.FileManager;
import com.example.administrator.shoujiguanjia.entity.FileInfo;
import com.example.administrator.shoujiguanjia.util.FileTypeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileMgrSecondaryActivity extends BaseActivity {
    private String type;
    private ListView listView;
    private Button btnDel;
    private TextView fileCount,fileSize;
    private FileManager fileManager;
    private long size;
    private String s;
    private int count;
    private ArrayList<FileInfo> datas;
    private FileAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_mgr_secondary);
        initToolBar();
        getIntentData();
        initView();
        setListener();
    }
    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mime="*/*";
                List<FileInfo> fileInfos = adapter.getData();
                FileInfo info = fileInfos.get(position);
                String url = info.getFileUrl();
                String name = info.getName();
                String end = name.substring(name.lastIndexOf(".") + 1);
                String[][] mimeType = FileTypeUtil.MIME_Table;
                w:
                for (int x = 0; x < mimeType.length; x++)
                    for (int y = 0; y < mimeType[x].length; y++) {
                        if (end.equals(mimeType[x][0])) {
                            mime = mimeType[x][1];
                            break w;
                        }
                    }
                Intent intent =new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://"+url),mime);
                startActivity(intent);
            }
        });
    }
    private void getIntentData() {
        type= getIntent().getBundleExtra("bundle").getString("type");
    }
    @Override
    public void initView() {
        fileManager = FileManager.getInstance(this);
        listView =(ListView)findViewById(R.id.filemgr_secondary_lv);
        btnDel  = (Button) findViewById(R.id.filemgr_secondary_bt);
        fileCount  = (TextView) findViewById(R.id.filemgr_secondary_tv1);
        fileSize  = (TextView) findViewById(R.id.filemgr_secondary_tv2);

        adapter = new FileAdapter(datas,this);
        listView.setAdapter(adapter);

        if(type.equals(FileTypeUtil.TYPE_ANY)){
            size = fileManager.getAllSize();
            count= fileManager.getAll().size();
            s = Formatter.formatFileSize(this,size);
            fileSize.setText(s);
            fileCount.setText(count+"");
            datas = fileManager.getAll();
        }
        else if(type.equals(FileTypeUtil.TYPE_APK)){
            size = fileManager.getExeSize();
            count= fileManager.getExe().size();
            s = Formatter.formatFileSize(this,size);
            fileSize.setText(s);
            fileCount.setText(count+"");
            datas = fileManager.getExe();
        } else if(type.equals(FileTypeUtil.TYPE_ZIP)){
            size = fileManager.getZipSize();
            count= fileManager.getZip().size();
            s = Formatter.formatFileSize(this,size);
            fileSize.setText(s);
            fileCount.setText(count+"");
            datas = fileManager.getZip();
        } else if(type.equals(FileTypeUtil.TYPE_AUDIO)){
            size = fileManager.getAudioSize();
            count= fileManager.getAudio().size();
            s = Formatter.formatFileSize(this,size);
            fileSize.setText(s);
            fileCount.setText(count+"");
            datas = fileManager.getAudio();
        } else if(type.equals(FileTypeUtil.TYPE_VIDEO)){
            size = fileManager.getVideoSize();
            count= fileManager.getVideo().size();
            s = Formatter.formatFileSize(this,size);
            fileSize.setText(s);
            fileCount.setText(count+"");
            datas = fileManager.getVideo();
        } else if(type.equals(FileTypeUtil.TYPE_IMAGE)){
            size = fileManager.getPicSize();
            count= fileManager.getPic().size();
            s = Formatter.formatFileSize(this,size);
            fileSize.setText(s);
            fileCount.setText(count+"");
            datas = fileManager.getPic();
        } else if(type.equals(FileTypeUtil.TYPE_TXT)){
            size = fileManager.getDocSize();
            count= fileManager.getDoc().size();
            s = Formatter.formatFileSize(this,size);
            fileSize.setText(s);
            fileCount.setText(count+"");
            datas = fileManager.getDoc();
        }
        adapter.setData(datas);
        adapter.notifyDataSetChanged();
    }
    public void deleteFile(View view){
        ArrayList<FileInfo> datas = (ArrayList<FileInfo>) adapter.getData();
        if(datas ==null|| datas.size()<=0){
            return;
        }
        ArrayList<FileInfo> delInfo = new ArrayList<>();
        for (FileInfo info : this.datas) {
            if(info.isChecked()){
                delInfo.add(info);            }
        }
        for (int x=0;x<delInfo.size();x++) {
            FileInfo info = delInfo.get(x);
            File file =new File(info.getFileUrl());
            int s = (int) file.length();
            if(file.delete()){
                datas.remove(info);
                fileManager.getAll().remove(info);
                type=info.getType();
                fileManager.setAllSize(fileManager.getAllSize()-s);
                if(type.equals(FileTypeUtil.TYPE_TXT)){
                    fileManager.getDoc().remove(info);
                    fileManager.setDocSize( fileManager.getDocSize()-s);
                }else  if(type.equals(FileTypeUtil.TYPE_IMAGE)){
                    fileManager.getPic().remove(info);
                    fileManager.setPicSize( fileManager.getPicSize()-s);
                }else  if(type.equals(FileTypeUtil.TYPE_AUDIO)){
                    fileManager.getAudio().remove(info);
                    fileManager.setAudioSize(fileManager.getAudioSize()-s);
                }else  if(type.equals(FileTypeUtil.TYPE_VIDEO)){
                    fileManager.getVideo().remove(info);
                    fileManager.setVideoSize(fileManager.getVideoSize()-s);
                }else  if(type.equals(FileTypeUtil.TYPE_ZIP)){
                    fileManager.getZip().remove(info);
                    fileManager.setZipSize(fileManager.getZipSize()-s);
                }else  if(type.equals(FileTypeUtil.TYPE_APK)){
                    fileManager.getExe().remove(info);
                    fileManager.setExeSize(fileManager.getExeSize()-s);
                }
            }
        }
        fileCount.setText(datas.size()+"");
        if(type.equals(FileTypeUtil.TYPE_TXT)){
            fileSize.setText(Formatter.formatFileSize(this, fileManager.getDocSize()));
        }else  if(type.equals(FileTypeUtil.TYPE_IMAGE)){
            fileSize.setText(Formatter.formatFileSize(this,fileManager.getPicSize()));
        }else  if(type.equals(FileTypeUtil.TYPE_AUDIO)){
            fileSize.setText(Formatter.formatFileSize(this,fileManager.getAudioSize()));
        }else  if(type.equals(FileTypeUtil.TYPE_VIDEO)){
            fileSize.setText(Formatter.formatFileSize(this,fileManager.getVideoSize()));
        }else  if(type.equals(FileTypeUtil.TYPE_ZIP)){
            fileSize.setText(Formatter.formatFileSize(this,fileManager.getZipSize()));
        }else  if(type.equals(FileTypeUtil.TYPE_APK)){
            fileSize.setText(Formatter.formatFileSize(this,fileManager.getExeSize()));
        }else  if(type.equals(FileTypeUtil.TYPE_ANY)){
            fileSize.setText(Formatter.formatFileSize(this,fileManager.getAllSize()));
        }
        adapter.notifyDataSetChanged();
    }
}