package com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP;

import android.content.Intent;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;

import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SettingPrefUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.StringUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.UITools;
import com.property.colpencil.colpencilandroidlibrary.Ui.MyProgressDialog;
import com.property.colpencil.colpencilandroidlibrary.Ui.SwipeBack.SwipeBackActivityBase;
import com.property.colpencil.colpencilandroidlibrary.Ui.SwipeBack.SwipeBackActivityHelper;
import com.property.colpencil.colpencilandroidlibrary.Ui.SwipeBack.SwipeBackLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.SwipeBack.Utils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;
import rx.Subscriber;
import solid.ren.skinlibrary.base.SkinBaseActivity;

/**
 * @author 汪 亮
 * @Description:所有activity的基类
 * @Email DramaScript@outlook.com
 * @date 16/6/23
 */
public abstract class ColpencilActivity<T extends ColpencilPresenter<ColpencilBaseView>> extends AutoLayoutActivity implements ColpencilBase, ColpencilBaseView, SwipeBackActivityBase {

    private rx.Observable<RxBusLMsg> observable;
    private Subscriber subscriber;
    /**
     * 主线程id
     */
    private long mUIThreadId;
    //日志的标识
    protected String TAG = "";

    // present基类
    protected ColpencilPresenter mPresenter;
    //基层view对象
    protected View mRootView;

    /**
     * 第一次点击返回的系统时间
     */
    private long mFirstClickTime = 0;
    private ColpencilFrame mAm;

    private SwipeBackActivityHelper mHelper;

    /**
     * 布局的id
     */
    protected int mContentViewId;

    private MyProgressDialog dialog;

    public void onPause() {
        super.onPause();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        mUIThreadId = android.os.Process.myTid();
        mAm = ColpencilFrame.getInstance();
        mAm.addActivity(this);
        TAG = StringUtil.getClassName(this);
        mPresenter = getPresenter();
        new MyBaseActivity();
        if (mPresenter != null && this instanceof ColpencilBaseView) {
            //绑定view的操作
            mPresenter.attach((ColpencilBaseView) this);
        }
        mRootView = createView(null, null, savedInstanceState);

        changeScale();
        //        ObserverUtils.getInstance().addObserver(this);
        setContentView(mRootView);
        //驱动更新view的操作
        bindView(savedInstanceState);
        initBus();
    }

    private void initBus() {
        observable = RxLBus.get().register("rxBusMsg", RxBusLMsg.class);
        subscriber = new Subscriber<RxBusLMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusLMsg msg) {
                if (msg.getType() == 0) {
                    changeScale();
                    recreate();
                }
                if (msg.getType() == 10000) {
                    if (SharedPreferencesUtil.getInstance(ColpencilActivity.this.getApplicationContext()).getBoolean("night", false)) {
                        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);//切换夜间模式
                    } else {
                        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);//切换夜间模式
                    }
                    recreate();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    static class MyBaseActivity extends SkinBaseActivity {

    }
    //    @Override
    //    public void finish() {
    //        super.finish();
    //    }

    @Override
    public void onResume() {
        super.onResume();
        int mode = SettingPrefUtils.getSwipeBackEdgeMode(this);
        SwipeBackLayout mSwipeBackLayout = mHelper.getSwipeBackLayout();
        switch (mode) {
            case 0:

                mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
                break;
            case 1:
                mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_RIGHT);
                break;
            case 2:
                mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_BOTTOM);
                break;
            case 3:
                mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
                break;
        }
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getClass().isAnnotationPresent(ActivityFragmentInject.class)) {
            ActivityFragmentInject annotation = getClass().getAnnotation(ActivityFragmentInject.class);
            mContentViewId = annotation.contentViewId();
        } else {
            throw new RuntimeException("Class must add annotations of ActivityFragmentInitParams.class");
        }
        View view = UITools.inflate(this, mContentViewId);
        ButterKnife.bind(this, view);
        initViews(view);
        return view;
    }

    protected abstract void initViews(View view);

    @Override
    public View getView() {
        return mRootView;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        mUIThreadId = android.os.Process.myTid();
        super.onNewIntent(intent);
    }

    /**
     * 获取UI线程ID
     *
     * @return UI线程ID
     */
    public long getUIThreadId() {
        return mUIThreadId;
    }


    private void changeScale() {
        Resources res = getResources();
        Configuration config = res.getConfiguration();
        if (SharedPreferencesUtil.getInstance(getApplicationContext()).getFloat("scale") == 0.0f) {
            config.fontScale = 1.0f;
        } else {
            config.fontScale = SharedPreferencesUtil.getInstance(getApplicationContext()).getFloat("scale");
        }

        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    @Override
    protected void onDestroy() {
        ColpencilFrame.getInstance().finishActivity(this);
        if (mPresenter != null && this instanceof ColpencilBaseView) {
            //防止present在activity之后还持有view做一些耗时操作，从而造成内存溢出的后果
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
        RxLBus.get().unregister("rxBusMsg", observable);
    }

    @Override
    public void showLoading(String msg) {
        if (dialog == null)
            dialog = MyProgressDialog.createDialog(this, msg);
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void showError(String msg, View.OnClickListener onClickListener) {

    }

    @Override
    public void showEmpty(String msg, View.OnClickListener onClickListener) {

    }

    @Override
    public void showEmpty(String msg, View.OnClickListener onClickListener, int imageId) {

    }

    @Override
    public void showNetError(View.OnClickListener onClickListener) {

    }

    /**
     * 双击退出，间隔时间为2000ms
     *
     * @return
     */
    public boolean onDoubleClickExit() {
        return onDoubleClickExit(2000);
    }

    /**
     * 退出应用程序
     *
     * @param isBackground 是否开开启后台运行,如果为true则为后台运行
     */
    public void exitApp(Boolean isBackground) {
        mAm.exitApp(isBackground);
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        mAm.exitApp(false);
    }

    /**
     * 双击退出
     */
    private boolean onDoubleClickExit(long timeSpace) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - mFirstClickTime > timeSpace) {
            ToastTools.showShort(this, "再按一次退出");
            mFirstClickTime = currentTimeMillis;
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelp.getInstance().handlePermissionCallback(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PermissionHelp.getInstance().handleSpecialPermissionCallback(this, requestCode, resultCode, data);
    }

}
