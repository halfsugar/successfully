package com.example.administrator.shoujiguanjia.ui;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import java.io.IOException;
public class MusicService extends Service {
    MediaPlayer mediaPlayer ;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AssetManager assetManager = getAssets();
        try {
            AssetFileDescriptor fileDescriptor =assetManager.openFd("k.mp3");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                    fileDescriptor.getStartOffset(),
                    fileDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    public void onDestroy() {
        mediaPlayer.stop();
        super.onDestroy();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
