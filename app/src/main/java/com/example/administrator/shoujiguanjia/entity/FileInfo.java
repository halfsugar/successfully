package com.example.administrator.shoujiguanjia.entity;
public class FileInfo {
    private String fileIcon;
    private String fileUrl;
    public String getFileUrl() {
        return fileUrl;
    }
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    private String type;
    public FileInfo(String fileIcon, String fileUrl, String type, String name, String lastModify, String size, Boolean isChecked) {
        this.fileIcon = fileIcon;
        this.fileUrl = fileUrl;
        this.type = type;
        this.name = name;
        this.lastModify = lastModify;
        this.size = size;
        this.isChecked = isChecked;
    }
    public String getFileIcon() {
        return fileIcon;
    }
    public void setFileIcon(String fileIcon) {
        this.fileIcon = fileIcon;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    private  String name;
    private  String lastModify;
    private  String size;
    private  Boolean isChecked;
    public  FileInfo(){
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastModify() {
        return lastModify;
    }
    public void setLastModify(String lastModify) {
        this.lastModify = lastModify;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public Boolean isChecked() {
        return isChecked;
    }
    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
