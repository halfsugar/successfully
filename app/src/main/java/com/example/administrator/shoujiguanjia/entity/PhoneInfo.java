package com.example.administrator.shoujiguanjia.entity;
public class PhoneInfo {
    int Icon;
    String Text;
    String Title;
    public PhoneInfo(int icon, String text, String title) {
        Icon = icon;
        Text = text;
        Title = title;
    }
    public int getIcon() {
        return Icon;
    }
    public void setIcon(int icon) {
        Icon = icon;
    }
    public String getText() {
        return Text;
    }
    public void setText(String text) {
        Text = text;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
}
