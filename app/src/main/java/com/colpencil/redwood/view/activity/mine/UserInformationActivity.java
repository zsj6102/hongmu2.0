package com.colpencil.redwood.view.activity.mine;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.tools.DataCleanManager;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.widgets.dialogs.SetUserImageDialog;
import com.colpencil.redwood.function.widgets.dialogs.UpdateUserInforDialog;
import com.colpencil.redwood.present.mine.UserInformationPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.login.PwdActivity;
import com.colpencil.redwood.view.impl.UserInformationView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.SelectableRoundedImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;


/**
 * 描述：用户个人信息
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/25 14 24
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_userinformation
)
public class UserInformationActivity extends ColpencilActivity implements UserInformationView, View.OnClickListener, SetUserImageDialog.OnPhotoDialogClickListener, UpdateUserInforDialog.OnUpdateInforDialogClickListener {

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;

    @Bind(R.id.updatePwd_userInfor)
    RelativeLayout updatePwd_userInfor;

    @Bind(R.id.updateImg_userInfor)
    RelativeLayout updateImg_userInfor;

    @Bind(R.id.userInforName)
    LinearLayout userInforName;

    @Bind(R.id.userInforWeChat)
    RelativeLayout userInforWeChat;

    @Bind(R.id.userInforQQ)
    RelativeLayout userInforQQ;

    @Bind(R.id.userInforEmail)
    RelativeLayout userInforEmail;

    @Bind(R.id.iv_userInfor)
    SelectableRoundedImageView iv_userInfor;

    @Bind(R.id.tv_userInforId)
    TextView tv_userInforId;

    @Bind(R.id.tv_userInforName)
    TextView tv_userInforName;

    @Bind(R.id.tv_userInforGrade)
    TextView tv_userInforGrade;

    @Bind(R.id.tv_userInforPhone)
    TextView tv_userInforPhone;

    @Bind(R.id.tv_userInforWeChat)
    TextView tv_userInforWeChat;

    @Bind(R.id.tv_userInforQQ)
    TextView tv_userInforQQ;

    @Bind(R.id.tv_userInforEmail)
    TextView tv_userInforEmail;

    @Bind(R.id.outLogin_userInfor)
    Button outLogin_userInfor;

    @Bind(R.id.sex_male)
    ImageView iv_male;

    @Bind(R.id.sex_female)
    ImageView iv_female;

    @Bind(R.id.ll_clear)
    LinearLayout ll_clear;

    @Bind(R.id.tv_cache)
    TextView tv_cache;

    private SetUserImageDialog setUserImageDialog;

    private Bitmap head;// 头像Bitmap

    private File photoFile;

    private String sdCardPath = Environment.getExternalStorageDirectory()
            .getAbsolutePath();

    private Uri muri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg"));

    private UserInformationPresenter presenter;

    private UpdateUserInforDialog updateUserInforDialog;
    /**
     * 修改的标识
     */
    private String updateFlag;
    private Observable<RxBusMsg> observable;

    private Subscriber subscriber;

    private LoginBean mLoginBean;

    private int typeFlag;//处理类型

    private String updateMsg;//修改的内容

    @Override
    protected void initViews(View view) {
        initData();
    }

    private void initData() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        tv_main_title.setText("个人中心");
        iv_back.setOnClickListener(this);
        updatePwd_userInfor.setOnClickListener(this);
        updateImg_userInfor.setOnClickListener(this);
        userInforName.setOnClickListener(this);
        userInforWeChat.setOnClickListener(this);
        userInforQQ.setOnClickListener(this);
        userInforEmail.setOnClickListener(this);
        outLogin_userInfor.setOnClickListener(this);
        ll_clear.setOnClickListener(this);
        try {
            tv_cache.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading(Constants.progressName);
        presenter.loadUserInfor();
        observable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 4) {
                    //个人中心需要重新请求数据
                    finish();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new UserInformationPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.updatePwd_userInfor://修改密码
                Intent intent = new Intent(UserInformationActivity.this, PwdActivity.class);
                intent.putExtra("key", "updatePwd");
                startActivity(intent);
                break;
            case R.id.updateImg_userInfor://用户修改头像
                typeFlag = -1;
                if (setUserImageDialog == null) {
                    setUserImageDialog = new SetUserImageDialog(UserInformationActivity.this);
                    setUserImageDialog.setOnPhotoDialogClickListener(this);
                }
                setUserImageDialog.show();
                break;
            case R.id.userInforName://修改用户名
                typeFlag = 1;
                updateFlag = "用户名";
                showUpdateDialog(tv_userInforName.getText().toString());
                break;
            case R.id.userInforWeChat://修改微信
                typeFlag = 2;
                updateFlag = "微信";
                showUpdateDialog(tv_userInforWeChat.getText().toString());
                break;
            case R.id.userInforQQ://修改QQ
                typeFlag = 3;
                updateFlag = "QQ";
                showUpdateDialog(tv_userInforQQ.getText().toString());
                break;
            case R.id.userInforEmail:
                typeFlag = 4;
                updateFlag = "邮箱";
                showUpdateDialog(tv_userInforEmail.getText().toString());
                break;
            case R.id.outLogin_userInfor://退出登录
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(53);
                RxBus.get().post("rxBusMsg", rxBusMsg);
                SharedPreferencesUtil.getInstance(this).setString(StringConfig.MOBILEPHONE, "");
                SharedPreferencesUtil.getInstance(this).setString(StringConfig.PASSWORD, "");
                SharedPreferencesUtil.getInstance(this).setBoolean(StringConfig.ISLOGIN, false);
                finish();
                break;
            case R.id.ll_clear:
                DataCleanManager.clearAllCache(this);
                try {
                    tv_cache.setText(DataCleanManager.getTotalCacheSize(this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void showUpdateDialog(String content) {
        updateUserInforDialog = new UpdateUserInforDialog(this, updateFlag, content);
        updateUserInforDialog.setOnUpdateInforDialogClickListener(this);
        updateUserInforDialog.show();
    }

    //意图回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }
                break;
            case 2://拍照
                if (resultCode == RESULT_OK) {
                    if (resultCode == RESULT_OK) {
                        cropPhoto(muri);// 裁剪图片
                    }
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    try {
                        head = BitmapFactory.decodeStream(getContentResolver().openInputStream(muri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (head != null) {
                        deleteOldFile();
                        photoFile = new File(sdCardPath + "/" + "userphoto.jpg");
                        try {
                            FileOutputStream fop = new FileOutputStream(photoFile);
                            head.compress(Bitmap.CompressFormat.JPEG, 100, fop);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        presenter.updateImg(photoFile);
                    } else {
                        ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "选择图片失败");
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", false);
        intent.putExtra("output", muri);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, 3);
    }

    /**
     * 删除老文件
     */
    void deleteOldFile() {
        photoFile = new File(sdCardPath + "/" + "userphoto.jpg");
        if (photoFile.exists()) {
            photoFile.delete();
        }
    }

    /**
     * 拍照选择
     */
    @Override
    public void onTakePhoto() {
        //权限设置
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String fileName = String.valueOf(System.currentTimeMillis());
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, muri);
        startActivityForResult(openCameraIntent, 2);
        setUserImageDialog.dismiss();
    }

    /**
     * 从相册中选择
     */
    @Override
    public void onSelectPhoto() {
        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent1, 1);
        setUserImageDialog.dismiss();
    }

    /**
     * 取消图片选择
     */
    @Override
    public void onCancelClick() {
        setUserImageDialog.dismiss();
    }

    @Override
    public void loadUserInfor(LoginBean loginBean) {
        mLoginBean = loginBean;
        ImageLoaderUtils.loadImage(this, mLoginBean.getData().getHeadPath(), iv_userInfor);
        tv_userInforId.setText("" + SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"));
        tv_userInforName.setText(mLoginBean.getData().getUserName());
        tv_userInforGrade.setText(mLoginBean.getData().getCustomerGrade());
        tv_userInforPhone.setText(mLoginBean.getData().getPhone());
        tv_userInforWeChat.setText(mLoginBean.getData().getWeChatNo());
        tv_userInforQQ.setText(mLoginBean.getData().getQqNo());
        tv_userInforEmail.setText(mLoginBean.getData().getEmail());
        if (mLoginBean.getData().getSex() == 0) {
            iv_male.setImageResource(R.mipmap.select_no);
            iv_female.setImageResource(R.mipmap.select_yes_red);
        } else {
            iv_male.setImageResource(R.mipmap.select_yes_red);
            iv_female.setImageResource(R.mipmap.select_no);
        }
        hideLoading();
    }

    @Override
    public void updateSuccess(LoginBean loginBean) {
        mLoginBean = loginBean;
        ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), mLoginBean.getMsg());
        //设置头像
        if (typeFlag == -1) {
            ImageLoaderUtils.loadImage(this, loginBean.getFacePath(), iv_userInfor);
        } else if (typeFlag == 1) {
            tv_userInforName.setText(updateMsg + "");
        } else if (typeFlag == 2) {
            tv_userInforWeChat.setText(updateMsg + "");
        } else if (typeFlag == 3) {
            tv_userInforQQ.setText(updateMsg + "");
        } else if (typeFlag == 4) {
            tv_userInforEmail.setText(updateMsg + "");
        } else {
            if (mLoginBean.getSex() == 0) {
                iv_male.setImageResource(R.mipmap.select_no);
                iv_female.setImageResource(R.mipmap.select_yes_red);
            } else {
                iv_male.setImageResource(R.mipmap.select_yes_red);
                iv_female.setImageResource(R.mipmap.select_no);
            }
        }
        if (typeFlag == -1 || typeFlag == 1) {//通过MeFragment进行修改
            RxBusMsg rxBusMsg = new RxBusMsg();
            rxBusMsg.setType(6);
            RxBus.get().post("rxBusMsg", rxBusMsg);
        }
        hideLoading();
    }

    @Override
    public void fail(LoginBean loginBean) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), loginBean.getMsg());
        if (loginBean.getCode() == 3) {//未登录
            Intent intent = new Intent(UserInformationActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 修改用户个人信息
     */
    @Override
    public void onUpdateSure(String content) {
        if (TextUtils.isEmpty(content)) {
            if (typeFlag == 1) {
                ToastTools.showShort(this, "用户名不能为空");
            } else if (typeFlag == 2) {
                ToastTools.showShort(this, "微信不能为空");
            } else if (typeFlag == 3) {
                ToastTools.showShort(this, "QQ不能为空");
            } else {
                ToastTools.showShort(this, "邮箱不能为空");
            }
        } else {
            showLoading(Constants.progressName);
            updateMsg = content;
            presenter.updateUserInfor(content, typeFlag);
            updateUserInforDialog.dismiss();
        }
    }

    @OnClick(R.id.sex_male)
    void maleClick() {
        typeFlag = 5;
        iv_male.setImageResource(R.mipmap.select_yes_red);
        iv_female.setImageResource(R.mipmap.select_no);
        presenter.updateUserInfor("1", 5);
    }

    @OnClick(R.id.sex_female)
    void femaleClick() {
        typeFlag = 5;
        iv_male.setImageResource(R.mipmap.select_no);
        iv_female.setImageResource(R.mipmap.select_yes_red);
        presenter.updateUserInfor("0", 5);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
    }
}
