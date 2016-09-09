package com.example.administrator.shoujiguanjia.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;
public abstract class MyBaseAdapter<E> extends BaseAdapter {
    private List<E> data;
    public Context context;
    public LayoutInflater inflater;
    public List<E> getData() {
        return data;
    }
    public void setData(List<E> data) {
        this.data = data;
    }
    public MyBaseAdapter(List<E> data, Context context) {
        this.data = data;
        this.context = context;
        inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return MyGetView(position,convertView,parent);
    }
    public abstract View MyGetView(int position, View convertView, ViewGroup parent);
}
