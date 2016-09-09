package com.example.administrator.shoujiguanjia.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.shoujiguanjia.R;
import com.example.administrator.shoujiguanjia.biz.MemoryManager;
import com.example.administrator.shoujiguanjia.entity.PhoneInfo;
import com.example.administrator.shoujiguanjia.util.CommonUtil;

import java.util.List;

public class PhonemgrAdapter extends MyBaseAdapter<PhoneInfo> {
    private String title;
    private String text;
    public PhonemgrAdapter(@NonNull List<PhoneInfo> data, @NonNull Context context) {
        super(data, context);

    }
    @Override
    public View MyGetView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView =inflater.inflate(R.layout.layout_phonemgr_listitem, null);
        }
        PhoneInfo phoneInfo = (PhoneInfo) getItem(position);
        ImageView icon = (ImageView) convertView.findViewById(R.id.iv_phonemgr_icon);
        TextView title = (TextView)convertView.findViewById(R.id.tv_phonemgr_title);
        TextView text = (TextView)convertView.findViewById(R.id.tv_phonemgr_text);
        icon.setImageResource(phoneInfo.getIcon());
        title.setText(phoneInfo.getTitle());
        text.setText(phoneInfo.getText());
        return convertView;
    }
    public void initAdapterData() {
        title = "全部运行内存" + CommonUtil.getFileSize(MemoryManager.getPhoneTotalRamMemory());
        text = "剩余运行内存" + CommonUtil.getFileSize(MemoryManager.getPhoneFreeRamMemory(context));
    }
}
