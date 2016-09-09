package com.example.administrator.shoujiguanjia.ui;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.administrator.shoujiguanjia.R;
import com.example.administrator.shoujiguanjia.adapter.TelClassAdapter;
import com.example.administrator.shoujiguanjia.db.DBRead;
import com.example.administrator.shoujiguanjia.entity.TelClassList;
import com.example.administrator.shoujiguanjia.util.MyAssetManager;

import java.io.File;
import java.util.ArrayList;
public class ShowTelNumTypeActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private TelClassAdapter adapter;
    private ArrayList<TelClassList> data;
    private File file;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tel_type);
        initToolBar();
        initView();
    }
    @Override
    public void initView() {
        listView = (ListView) findViewById(R.id.list);
        file = MyAssetManager.copyAssetsFileToSDFile(this);
        data = DBRead.readTelClassList(file);
        adapter = new TelClassAdapter(data, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TelClassList tel = (TelClassList) parent.getItemAtPosition(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("file", file);
        bundle.putInt("idx", tel.getIdx());
        myStartActivity(ShowTelNumActivity.class,bundle);
    }
    class QueryTask extends AsyncTask<Void, Void, ArrayList<TelClassList>> {
        @Override
        protected ArrayList<TelClassList> doInBackground(Void... params) {
            file = MyAssetManager.copyAssetsFileToSDFile(ShowTelNumTypeActivity.this);
            return DBRead.readTelClassList(file);
        }
        @Override
        protected void onPostExecute(ArrayList<TelClassList> telClassLists) {
            if (telClassLists != null)
                showTestToast("onpostex"+telClassLists.size());
            data = telClassLists;
        }
    }
}
