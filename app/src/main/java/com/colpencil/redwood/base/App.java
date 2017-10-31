package com.colpencil.redwood.base;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.colpencil.redwood.CustomAttr.RadioButtonAttr;
import com.colpencil.redwood.CustomAttr.TabLayoutIndicatorAttr;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.dao.DaoMaster;
import com.colpencil.redwood.dao.DaoSession;
import com.colpencil.redwood.function.tools.GlideImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.sina.weibo.sdk.utils.LogUtil;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.umeng.socialize.PlatformConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

import solid.ren.skinlibrary.SkinConfig;
import solid.ren.skinlibrary.base.SkinBaseApplication;


/**
 * @author 曾 凤
 * @Description: Applaction 配置
 * @Email 20000263@qq.com
 * @date 2016/7/6
 */
public class App extends  SkinBaseApplication{
    static final String TAG = "MyApplication";
    private static DaoSession daoSession;
    private static DaoMaster daoMaster;

    static App instance;

    private ImagePicker imagePicker;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("程序启动", "====");
        instance = this;
        //初始化内存泄漏工具
        //        LeakCanary.install(this);
        //必须初始化框架操作
        ColpencilFrame.init(this);
        //初始化日志工具
        ColpencilLogger.init();
        //配置快速编译
        //        FreelineCore.init(this);
        //选择图片
        //WeChat appid appsecret
        PlatformConfig.setWeixin("wx8ff267ef571440e5", "c559520928df2ad6f26cff454c17a489");
        //SINA  appid appsecret
        PlatformConfig.setSinaWeibo("2541113237", "ea105010d7e525fcea65b0b5f92bd654");
        //Tencent qq appid appsecret
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setQQZone("1105578038", "tiNdPzp92qOS6GJu");

        //设置imagepicker
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素

       /* Thread.currentThread().setUncaughtExceptionHandler(
                new MyExecptionHandler());*/


        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                LogUtil.i(TAG, "onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };

        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                LogUtil.i(TAG, "onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                LogUtil.i(TAG, "onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                LogUtil.i(TAG, "onDownloadProgress:" + i);
            }
        });
        QbSdk.initX5Environment(this, cb);
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 获取imagepicker对象
     *
     * @return
     */
    public ImagePicker getImagePicker() {
        return imagePicker;
    }

    private void openStrict() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectCustomSlowCalls() //API等级11，使用StrictMode.noteSlowCode
                .detectDiskReads().detectDiskWrites().detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyDialog() //弹出违规提示对话框
                .penaltyLog() //在Logcat 中打印违规异常信息
                .penaltyFlashScreen() //API等级11
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects() //API等级11
                .penaltyLog().penaltyDeath().build());
    }

    static class MyApp extends SkinBaseApplication {
        @Override
        public void onCreate() {
            super.onCreate();
        }

        public void init() {
            SkinConfig.setCanChangeStatusColor(true);
            SkinConfig.setCanChangeFont(true);
            SkinConfig.setDebug(true);
            SkinConfig.addSupportAttr("tabLayoutIndicator", new TabLayoutIndicatorAttr());
            SkinConfig.addSupportAttr("button", new RadioButtonAttr());
            SkinConfig.enableGlobalSkinApply();
        }
    }

    private class MyExecptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            SharedPreferencesUtil.getInstance(getInstance()).getBoolean(StringConfig.ISLOGIN, false);
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                Field[] fileds = Build.class.getDeclaredFields();
                for (Field filed : fileds) {
                    System.out.println(filed.getName() + "--" + filed.get(null));
                    sw.write(filed.getName() + "--" + filed.get(null) + "\n");
                }
                ex.printStackTrace(pw);
                File file = new File(Environment.getExternalStorageDirectory(), "log.txt");
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(sw.toString().getBytes());
                fos.close();
                pw.close();
                sw.close();
                android.os.Process.killProcess(android.os.Process.myPid());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
