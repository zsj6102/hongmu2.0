package com.colpencil.redwood.function.tools;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpImgThread extends Thread {

    private Handler handler;
    private List<String> urls;

    public HttpImgThread(Handler handler, List<String> urls) {
        this.handler = handler;
        this.urls = urls;
    }

    @Override
    public void run() {
        super.run();
        List<String> files = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            try {
                URL url = new URL(urls.get(i));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                File parent = Environment.getExternalStorageDirectory();
                File file = new File(parent, i + "");
                FileOutputStream fos = new FileOutputStream(file);
                InputStream in = conn.getInputStream();
                byte ch[] = new byte[2 * 1024];
                int len;
                if (fos != null) {
                    while ((len = in.read(ch)) != -1) {
                        fos.write(ch, 0, len);
                    }
                    in.close();
                    fos.close();
                }
                String filePath = file.getAbsolutePath();
                files.add(filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Message msg = new Message();
        msg.what = 0;
        msg.obj = files;
        handler.sendMessage(msg);

    }
}
