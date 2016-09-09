package com.example.administrator.shoujiguanjia.entity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import java.io.File;
import java.io.FileFilter;
import java.util.List;
public class PhoneManager {
    String PhoneBrand;
    String PhoneName1;
    String PhoneSystemVersion;
    String PhoneCpuName;
    String PhoneCpuNumber;
    String Resolution;
    String MaxPhotoSize;
    String PhoneSystemBasebandVersion;
    boolean root;
    public String getPhoneBrand() {
        return Build.BRAND;
    }
    public String getPhoneName1() {
        return PhoneName1;
    }
    public String getPhoneSystemVersion() {
        return Build.MODEL;
    }
    public String getPhoneCpuName() {
        return Build.CPU_ABI;
    }
    public int getPhoneCpuNumber() {
        File file = new File("sys/devices/system/cpu");
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().matches("cpu[0-9]")) {
                    return true;
                }
                return false;
            }
        });
        return  files.length;
    }
    public String getRadio() {
        return Build.RADIO;
    }
    public String getWindowPixel(Context context) {
        WindowManager service = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = service.getDefaultDisplay();
        DisplayMetrics metric =new DisplayMetrics();
        display.getMetrics(metric);
        return metric.widthPixels+"*"+metric.heightPixels;
    }
    public String getCameraPixrl() {
        Camera camera = Camera.open();
        Camera.Parameters param = camera.getParameters();
        List<Camera.Size> list = param.getSupportedPictureSizes();
        int max=0;
        int []sizes =new int[2];
        for (Camera.Size size : list) {
            if(max<size.width*size.height){
                max=size.width*size.height;
                sizes[0]=size.width;
                sizes[1]=size.height;
            }
        }
        camera.release();
        camera = null;
        return sizes[0]+"*"+sizes[1];
    }
    public boolean isRoot() {
        if(!new File("/system/bin/su").exists()&&!new File("/system/xbin/su").exists()){
            return  false;
        }
        else {
            return true;
        }
    }
}