package com.colpencil.redwood.view;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;

import com.colpencil.redwood.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 陈 宝
 * @Description:下载音乐的服务
 * @Email 1041121352@qq.com
 * @date 2016/9/8
 */
public class DownLoadService extends IntentService {

    private static final int NOTIFICATION_ID = 1000;

    public DownLoadService() {
        this("download");
    }

    public DownLoadService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String path = intent.getStringExtra("path");
        String title = intent.getStringExtra("title");
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
                    "_redwood/" + title + ".mp3");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);
            sendNotification("音乐开始下载...", "音乐下载", "音乐准备下载...");
            InputStream is = getInputStream(path);
            byte[] buffer = new byte[1024 * 200];
            int length;
            while ((length = is.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
                fos.flush();
            }
            fos.close();
            clearNotification();
            sendNotification("音乐下载完成", "音乐下载完成", "音乐下载完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除通知
     */
    public void clearNotification() {
        //1.   NotificationManager
        NotificationManager manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(NOTIFICATION_ID);
    }

    private InputStream getInputStream(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        InputStream is = conn.getInputStream();
        return is;
    }

    /**
     * 发送通知
     *
     * @param ticker
     * @param title
     * @param text
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void sendNotification(String ticker, String title, String text) {
        //1.   NotificationManager
        NotificationManager manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        //2.  Builder
        Notification.Builder builder = new Notification.Builder(this);
        builder.setTicker(ticker)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.mipmap.logo);
        Notification n = builder.build();
        //3.  notify()
        manager.notify(NOTIFICATION_ID, n);
    }
}
