package com.example.administrator.shoujiguanjia.fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.administrator.shoujiguanjia.R;
import com.example.administrator.shoujiguanjia.ui.IntroduceActivity;
public class IntroduceFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView imageView = (ImageView) inflater.inflate(R.layout.fragment_introduce_layout,null);
        Bundle bundle = getArguments();
        int i = bundle.getInt("position");
        IntroduceActivity activity =(IntroduceActivity) getActivity();
        imageView.setBackgroundResource(activity.resIds[i]);
        return imageView;
    }
}