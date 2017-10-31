package com.colpencil.redwood.function.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 陈 宝
 * @Description:图片处理工具类
 * @Email 1041121352@qq.com
 * @date 2016/10/12
 */
public class ImageUtils {
   private ImageUtils(){

   }
    public static File compressImage(File imageFile, int reqWidth, int reqHeight, Bitmap.CompressFormat compressFormat,int quality,String destinationPath)
        throws IOException{
        FileOutputStream fileOutputStream = null;
        File file = new File(destinationPath).getParentFile();
        if(!file.exists()){
            file.mkdirs();
        }
        try{
            fileOutputStream = new FileOutputStream(destinationPath);
            decodeSampleBitmapFromFile(imageFile,reqWidth,reqHeight).compress(compressFormat,quality,fileOutputStream);
        }finally {
            if(fileOutputStream!=null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        return new File(destinationPath);
    }
    static Bitmap decodeSampleBitmapFromFile(File imageFile,int reqWidth,int reqHeight) throws IOException{
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(),options);
        options.inSampleSize = caculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap scaledBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(),options);
        ExifInterface exif;
        exif = new ExifInterface(imageFile.getAbsolutePath());
        int orietation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,0);
        Matrix matrix = new Matrix();
        if(orietation == 6){
            matrix.postRotate(90);
        }else if(orietation == 3){
            matrix.postRotate(180);
        }else if(orietation == 8){
            matrix.postRotate(270);
        }
        scaledBitmap = Bitmap.createBitmap(scaledBitmap,0,0,scaledBitmap.getWidth(),scaledBitmap.getHeight(),matrix,true);
        return scaledBitmap;
    }
    private static int caculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if(height>reqHeight || width > reqWidth){
            final int hafHeight = height /2;
            final int halWidth = width /2;
            while((hafHeight/inSampleSize) >= reqHeight && (halWidth / inSampleSize) >= reqWidth){
                inSampleSize *=2;
            }
        }
        return inSampleSize;
    }
}
