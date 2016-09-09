package com.example.administrator.shoujiguanjia.util;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
public class BitmapUtil {
    public static Bitmap loadBitmap(String pathName, SizeMessage sizeMessage) {
        int targetw = sizeMessage.getW();
        int targeth = sizeMessage.getH();
        Context context = sizeMessage.getContext();
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        int resw = options.outWidth;
        int resh = options.outHeight;
        if (resw <= targetw && resh <= targeth) {
            options.inSampleSize = 1;
        }
        else {
            int scalew = resw / targetw;
            int scaleh = resh / targeth;
            int scale = scalew > scaleh ? scalew : scaleh;
            options.inSampleSize = scale;
        }
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);
        return bitmap;
    }
    public static Bitmap loadBitmap(int resID, SizeMessage sizeMessage) {
        int targetw = sizeMessage.getW();
        int targeth = sizeMessage.getH();
        Context context = sizeMessage.getContext();
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resID, options);
        int resw = options.outWidth;
        int resh = options.outHeight;
        if (resw <= targetw && resh <= targeth) {
            options.inSampleSize = 1;
        }
        else {
            int scalew = resw / targetw;
            int scaleh = resh / targeth;
            int scale = scalew > scaleh ? scalew : scaleh;
            options.inSampleSize = scale;
        }
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID, options);
        return bitmap;
    }
    public static class SizeMessage {
        private int w;
        private int h;
        private Context context;
        public SizeMessage(Context context, boolean isPx, int w, int h) {
            this.context = context;
            if (!isPx) {
                w = DeviceUtil.dp2px(context, w);
                h = DeviceUtil.dp2px(context, h);
            }
            this.w = w;
            this.h = h;
        }
        public Context getContext() {
            return context;
        }
        public int getW() {
            return w;
        }
        public void setW(int w) {
            this.w = w;
        }
        public int getH() {
            return h;
        }
        public void setH(int h) {
            this.h = h;
        }
    }
}