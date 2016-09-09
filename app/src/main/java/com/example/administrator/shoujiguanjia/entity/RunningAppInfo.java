package com.example.administrator.shoujiguanjia.entity;
import android.graphics.drawable.Drawable;
public class RunningAppInfo {
    public  String label;
    public String packageName;
    public String memorySize;
    public  Drawable  appIcon;
    public  boolean isSysApp;
    int importance;
    boolean isSelect;
    public boolean isSelect() {
        return isSelect;
    }
    public void setSelect(boolean select) {
        isSelect = select;
    }
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public String getMemorySize() {
        return memorySize;
    }
    public void setMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }
    public Drawable getAppIcon() {
        return appIcon;
    }
    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }
    public boolean isSysApp() {
        return isSysApp;
    }
    public void setSysApp(boolean sysApp) {
        isSysApp = sysApp;
    }
    public int getImportance() {
        return importance;
    }
    public void setImportance(int importance) {
        this.importance = importance;
    }
    public RunningAppInfo(String label, String packageName, String memorySize, Drawable appIcon, boolean isSysApp, int importance) {
        this.packageName = packageName;
        this.label=label;
        this.memorySize = memorySize;
        this.appIcon = appIcon;
        this.isSysApp = isSysApp;
        this.importance = importance;
    }
}

