package com.example.administrator.shoujiguanjia.util;
import android.content.Context;
import android.content.res.AssetManager;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
public class MyAssetManager {
    public static File copyAssetsFileToSDFile(Context context){
        AssetManager assets = context.getAssets();
        File file=null;
        try {
            InputStream stream = assets.open("db/commonnum.db");
            File filesDir=context.getFilesDir();
            file =new File(filesDir,"commonnum.db");
            readAssetDBDataToFile(stream,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    private static void readAssetDBDataToFile(InputStream stream, File file) {
        BufferedInputStream buffin=null;
        BufferedOutputStream buffout=null;
        try {
            buffin=new BufferedInputStream(stream);
            buffout=new BufferedOutputStream(new FileOutputStream(file));
            int  temp=0;
            while ((temp=buffin.read())!=-1){
                buffout.write(temp);
            }
        }  catch (IOException e) {
            throw new  RuntimeException("文件失败");
        }
        finally {
            if(buffin!=null){
                try {
                    buffin.close();
                } catch (IOException e) {
                    throw  new RuntimeException("读取流失败！");
                }
            }
            if (buffout!=null){
                try {
                    buffout.close();
                } catch (IOException e) {
                    throw new RuntimeException("写入流失败！");
                }
            }
        }
    }
}
