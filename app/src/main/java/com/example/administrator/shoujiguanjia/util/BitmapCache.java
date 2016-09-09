package com.example.administrator.shoujiguanjia.util;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
public class BitmapCache {
    static private BitmapCache cache;
    private Hashtable<Integer, MySoftRef> hashRefs;
    private ReferenceQueue<Bitmap> q;
    private class MySoftRef extends SoftReference<Bitmap> {
        private Integer _key = 0;

        public MySoftRef(Bitmap bmp, ReferenceQueue<Bitmap> q, int key) {
            super(bmp, q);
            _key = key;
        }
    }
    private BitmapCache() {
        hashRefs = new Hashtable<Integer, MySoftRef>();
        q = new ReferenceQueue<Bitmap>();
    }
    public static BitmapCache getInstance() {
        if (cache == null) {
            cache = new BitmapCache();
        }
        return cache;
    }
    public  void addCacheBitmap(Bitmap bmp, Integer key) {
        cleanCache();// �����������
        MySoftRef ref = new MySoftRef(bmp, q, key);
        hashRefs.put(key, ref);
    }
    public Bitmap getBitmap(int resId, Context context) {
        Bitmap bmp = null;
        if (hashRefs.containsKey(resId)) {
            MySoftRef ref = (MySoftRef) hashRefs.get(resId);
            bmp = (Bitmap) ref.get();
        }
        if (bmp == null) {
            bmp = BitmapFactory.decodeStream(context.getResources()
                    .openRawResource(resId));
            this.addCacheBitmap(bmp, resId);
        }
        return bmp;
    }
    private void cleanCache() {
        MySoftRef ref = null;
        while ((ref = (MySoftRef) q.poll()) != null) {
            hashRefs.remove(ref._key);
        }
    }
    public void clearCache() {
        cleanCache();
        hashRefs.clear();
        System.gc();
        System.runFinalization();
    }
}