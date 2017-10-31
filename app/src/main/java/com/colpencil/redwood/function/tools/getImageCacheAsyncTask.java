package com.colpencil.redwood.function.tools;

import android.content.Context;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class getImageCacheAsyncTask  extends AsyncTask<String, Void, File> {
    private final Context context;
    private String newPath;
    public getImageCacheAsyncTask(Context context,String newPath ) {
        this.context = context;
    }

    @Override
    protected File doInBackground(String... params) {

        String imgUrl =  params[0];
        try {
            return Glide.with(context)
                    .load(imgUrl)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(File result) {
        if (result == null) {
            return;
        }
        //此path就是对应文件的缓存路径
        String path = result.getPath();
        copyFile(path, newPath);
    }
    public static void copyFile(String oldPath, String newPath) {
        try {
            int byteRead;
            File oldFile = new File(oldPath);
            if (oldFile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ( (byteRead = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制文件操作出错");
            e.printStackTrace();
        }
    }
}
