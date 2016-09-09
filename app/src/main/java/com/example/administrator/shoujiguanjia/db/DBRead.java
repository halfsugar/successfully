package com.example.administrator.shoujiguanjia.db;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.shoujiguanjia.entity.TelClassList;
import com.example.administrator.shoujiguanjia.entity.TelNumInfo;

import java.io.File;
import java.util.ArrayList;
public class DBRead{
    public static ArrayList<TelClassList> readTelClassList(File file){
        ArrayList<TelClassList> data=new ArrayList();
        SQLiteDatabase database=SQLiteDatabase.openOrCreateDatabase(file,null);
        Cursor cursor = database.rawQuery("select * from classlist", null);
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex("name"));
            int idx=cursor.getInt(cursor.getColumnIndex("idx"));
            TelClassList tel =new TelClassList(name,idx);
            data.add(tel);
        }
        cursor.close();
        return data;
    }
    public static ArrayList<TelNumInfo> readTelNum(File file , int idx){
        ArrayList<TelNumInfo> data=new ArrayList();
        SQLiteDatabase database=SQLiteDatabase.openOrCreateDatabase(file,null);
        Cursor cursor = database.rawQuery("select * from table"+idx, null);
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex("name"));
            long num=cursor.getLong(cursor.getColumnIndex("number"));
            TelNumInfo tel =new TelNumInfo(name,num);
            data.add(tel);

        }
        cursor.close();
        return data;
    }
}