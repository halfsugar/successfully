package com.example.administrator.shoujiguanjia.ui;
import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.administrator.shoujiguanjia.adapter.TelNumAdapter;
import com.example.administrator.shoujiguanjia.db.DBRead;
import com.example.administrator.shoujiguanjia.entity.TelNumInfo;
import java.io.File;
import java.util.ArrayList;
public class ShowTelNumActivity extends ListActivity implements AdapterView.OnItemClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int idx = intent.getIntExtra("idx", 1);
        File file = (File) intent.getSerializableExtra("file");
        ArrayList<TelNumInfo> telNumInfos = DBRead.readTelNum(file, idx);
        TelNumAdapter adapter = new TelNumAdapter(telNumInfos, this);
        ListView listView = getListView();
        listView.setOnItemClickListener(this);
        getListView().setAdapter(adapter);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        TelNumInfo tel = (TelNumInfo) parent.getItemAtPosition(position);
        intent.setData(Uri.parse("tel:" + tel.getTelNum()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }
}