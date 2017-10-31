package com.colpencil.redwood.view.activity.mine;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.CatBean;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.Info.StoreInfo;
import com.colpencil.redwood.bean.PostTypeInfo;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.Settled;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CategoryDialog;
import com.colpencil.redwood.function.widgets.dialogs.SetUserImageDialog;
import com.colpencil.redwood.function.widgets.dialogs.UpdateUserInforDialog;
import com.colpencil.redwood.present.mine.ModifyStorePresent;
import com.colpencil.redwood.view.impl.IModifyStoreView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.SelectableRoundedImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;


@ActivityFragmentInject(contentViewId = R.layout.activity_storeinfo)
public class ModifyStoreActivity extends ColpencilActivity implements IModifyStoreView, SetUserImageDialog.OnPhotoDialogClickListener, UpdateUserInforDialog.OnUpdateInforDialogClickListener {

    @Bind(R.id.tv_main_title)
    TextView tvMainTitle;
    @Bind(R.id.iv_storeInfo)
    SelectableRoundedImageView ivStoreInfo;
    @Bind(R.id.tv_storeName)
    TextView tvStoreName;
    @Bind(R.id.tv_postCate)
    TextView tvPostCate;
    @Bind(R.id.tv_storeaddress)
    TextView tvStoreaddress;
    @Bind(R.id.tv_backAddress)
    TextView tvBackAddress;
    @Bind(R.id.tv_storeIntro)
    TextView tvStoreIntro;
    @Bind(R.id.tv_storeSign)
    TextView tvStoreSign;
    @Bind(R.id.tv_notice)
    TextView tvNotice;
    @Bind(R.id.tv_promise)
    TextView tvPromise;
    @Bind(R.id.tv_ruzhufei)
    TextView tvRuzhufei;
    private ModifyStorePresent present;
    private int typeFlag;//处理类型
    private SetUserImageDialog setStoreImageDialog;
    private Uri muri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "storelog.jpg"));
    private File photoFile;
    private Bitmap logo;//
    HashMap<String, RequestBody> params = new HashMap<>();
    private String sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    private String sec_id = "";
    private CategoryDialog dialog;

    String s = "";
    private List<PostTypeInfo> list = new ArrayList<>();


    private UpdateUserInforDialog updateUserInforDialog;
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    private String store_address;
    private String store_address_detail;
    private int store_regionid;
    private String return_address;
    private String return_address_detail;
    private int return_region;
    private String return_name;
    private String return_phone;
    /**
     * 修改的标识
     */
    private String updateFlag;

    @Override
    protected void initViews(View view) {
        tvMainTitle.setText("店铺设置");
        showLoading("");
        present.getStoreInfo();
        present.loadRegion(0);
        params.put("token", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(this).getString("token")));
        params.put("member_id", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(this).getInt("member_id") + ""));

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
                if (msg.getType() == 70) {//刷新列表数据
                    ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "修改店铺地址成功");
                    present.getStoreInfo();
                    showLoading("");
                } else if (msg.getType() == 71) {
                    present.getStoreInfo();
                    showLoading("");
                    ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "修改退货地址成功");
                } else if (msg.getType() == 45) {//支付宝支付成功
                    showMsg("支付成功", msg.getType());
                } else if (msg.getType() == 46) {//支付宝支付失败
                    showMsg("支付失败", msg.getType());
                } else if (msg.getType() == 47) {//微信支付成功
                    showMsg("支付成功", msg.getType());
                } else if (msg.getType() == 48) {//微信支付失败
                    showMsg("支付失败", msg.getType());
                } else if (msg.getType() == 54) {
                    showMsg("支付成功", msg.getType());
                } else if (msg.getType() == 55) {
                    showMsg("支付失败", msg.getType());
                }

            }
        };
        observable.subscribe(subscriber);
    }

    private void showMsg(String msg, int type) {
        ToastTools.showShort(this, msg);
        if (type == 45 || type == 47 || type == 54) {//支付成功之后刷新数据
            present.getStoreInfo();
            showLoading("");
        }
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new ModifyStorePresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadFail(String msg) {

    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @OnClick(R.id.updateImg_storeInfor)
    void changeImg() {
        typeFlag = 0;
        if (setStoreImageDialog == null) {
            setStoreImageDialog = new SetUserImageDialog(ModifyStoreActivity.this);
            setStoreImageDialog.setOnPhotoDialogClickListener(this);
        }
        setStoreImageDialog.show();

    }

    @OnClick(R.id.layoutStore)
    void changeName() {
        typeFlag = 1;
        updateFlag = "店铺名称";
        showUpdateDialog(tvStoreName.getText().toString());
    }

    @OnClick(R.id.layoutCate)
    void cateClick() {
        typeFlag = 2;
        if (dialog == null) {
            dialog = new CategoryDialog(ModifyStoreActivity.this, R.style.PostDialogTheme, list);
        }
        dialog.setTitle("请选择经营品类");
        dialog.setListener(new CategoryDialog.PostClickListener() {
            @Override
            public void closeClick() {
                if (sec_id.equals("")) {
                    ToastTools.showShort(ModifyStoreActivity.this, "请选择经营品类");
                } else {
                    params.put("biz_type", OkhttpUtils.toRequestBody(sec_id));
                    params.put("upd_type", OkhttpUtils.toRequestBody("2"));
                    present.getModifyStatus(params);
                    dialog.dismiss();
                }
            }

            @Override
            public void itemUnClick() {
                tvPostCate.setText("请选择经营品类");
                sec_id = "";
            }

            @Override
            public void itemClick(List<Integer> position) {
                sec_id = "";
                s = "";
                for (int i = 0; i < position.size(); i++) {
                    if (i != position.size() - 1) {
                        s = s + list.get(position.get(i)).getTypename() + ",";
                        sec_id = sec_id + list.get(position.get(i)).getSec_id() + ",";
                    } else {
                        s = s + list.get(position.get(i)).getTypename();
                        sec_id = sec_id + list.get(position.get(i)).getSec_id();
                    }
                }


            }
        });
        dialog.show();
    }

    @OnClick(R.id.layoutStoreAddress)
    void storeAddres() {
        Intent intent = new Intent(this, ModifyAddressActivity.class);
        intent.putExtra("type", "store");
        intent.putExtra("address", store_address);
        intent.putExtra("address_detail", store_address_detail);
        intent.putExtra("id", store_regionid);
        intent.putExtra("return_man", return_name);
        intent.putExtra("return_phone", return_phone);
        startActivity(intent);
    }

    @OnClick(R.id.layoutbackAddress)
    void backAddres() {
        Intent intent = new Intent(this, ModifyAddressActivity.class);
        intent.putExtra("type", "back");
        intent.putExtra("address", return_address);
        intent.putExtra("address_detail", return_address_detail);
        intent.putExtra("return_man", return_name);
        intent.putExtra("return_phone", return_phone);
        intent.putExtra("id", return_region);
        startActivity(intent);
    }

    @OnClick(R.id.layoutIntro)
    void introClick() {
        typeFlag = 5;
        updateFlag = "店铺介绍";
        showUpdateDialog(tvStoreIntro.getText().toString());
    }

    @OnClick(R.id.layoutSign)
    void signClick() {
        typeFlag = 6;
        updateFlag = "签名";
        showUpdateDialog(tvStoreSign.getText().toString());

    }

    @OnClick(R.id.layoutNotice)
    void noticClick() {
        typeFlag = 7;
        updateFlag = "通知";
        showUpdateDialog(tvNotice.getText().toString());
    }

    @OnClick(R.id.layoutPromise)
    void marginClick() {
        if (tvPromise.getText().equals("已认证")) {
            return;
        } else {
            Intent intent = new Intent(this, MarginOrderActivity.class);
            startActivity(intent);
        }

    }

    @OnClick(R.id.layoutRuzhu)
    void settledClick() {
        Intent intent = new Intent(this, SettledOrderActivity.class);
        startActivity(intent);
    }

    private void showUpdateDialog(String content) {
        updateUserInforDialog = new UpdateUserInforDialog(this, updateFlag, content);
        updateUserInforDialog.setOnUpdateInforDialogClickListener(this);
        updateUserInforDialog.show();
    }

    @Override
    public void loadStoreInfo(ResultInfo<StoreInfo> info) {
        hideLoading();
        ImageLoaderUtils.loadImage(this, info.getData().getStore_logo(), ivStoreInfo);
        tvStoreName.setText(info.getData().getStore_name());
        tvPostCate.setText(info.getData().getBiz_type());
        tvStoreaddress.setText(info.getData().getAttr());
        sec_id = info.getData().getGoods_cat_id();
        if (info.getData().getReturn_attr() != null) {
            tvBackAddress.setText(info.getData().getReturn_attr());
        }
        if (info.getData().getDescription() != null) {
            tvStoreIntro.setText(info.getData().getDescription());
        }
        if (info.getData().getSign() != null) {
            tvStoreSign.setText(info.getData().getSign());
        }
        if (info.getData().getStore_banner() != null) {
            tvNotice.setText(info.getData().getStore_banner());
        }
        present.loadCatList(0);
        return_name = info.getData().getReturn_man();
        return_phone = info.getData().getReturn_mobile();
        store_address = info.getData().getStore_address();
        store_address_detail = info.getData().getStore_address_detail();
        store_regionid = info.getData().getStore_regionid();
        return_address = info.getData().getReturn_address();
        return_address_detail = info.getData().getReturn_address_detail();
        return_region = info.getData().getReturn_region();
        if (info.getData().getHas_margin() == 0) {
            tvPromise.setText("未认证");
        } else {
            tvPromise.setText("已认证");
        }
        if (info.getData().getHas_settle() == 0) {
            tvRuzhufei.setText("未缴交");
        } else {
            tvRuzhufei.setText(info.getData().getSettleInfo());
        }
    }

    @Override
    public void loadRegion(AddresBean regionInfo) {

    }

    @Override
    public void loadCat(CatListBean catListBean) {
        if (!ListUtils.listIsNullOrEmpty(catListBean.getData())) {
            list.clear();
            for (CatBean vo : catListBean.getData()) {
                PostTypeInfo info = new PostTypeInfo();
                info.setTypename(vo.getCat_name());
                info.setSec_id(String.valueOf(vo.getCat_id()));
                String[] type = sec_id.split(",");
                if (type != null && type.length > 0) {
                    for (int i = 0; i < type.length; i++) {
                        if (vo.getCat_id() == Integer.parseInt(type[i])) {
                            info.setChoose(true);
                        }
                    }
                }
                list.add(info);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
    }

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
                        logo = BitmapFactory.decodeStream(getContentResolver().openInputStream(muri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (logo != null) {
                        deleteOldFile();
                        photoFile = new File(sdCardPath + "/" + "updatestorelog.jpg");
                        try {
                            FileOutputStream fop = new FileOutputStream(photoFile);
                            logo.compress(Bitmap.CompressFormat.JPEG, 100, fop);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        params.put("id_img\";filename=\"1.png", RequestBody.create(MediaType.parse("image/png"), photoFile));
                        params.put("upd_type", OkhttpUtils.toRequestBody(0 + ""));
                        present.getModifyStatus(params);
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
     * 删除老文件
     */
    void deleteOldFile() {
        photoFile = new File(sdCardPath + "/" + "updatestorelog.jpg");
        if (photoFile.exists()) {
            photoFile.delete();
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

    @Override
    public void onTakePhoto() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, muri);
        startActivityForResult(openCameraIntent, 2);
        setStoreImageDialog.dismiss();
    }

    @Override
    public void onSelectPhoto() {
        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent1, 1);
        setStoreImageDialog.dismiss();
    }

    @Override
    public void onCancelClick() {
        setStoreImageDialog.dismiss();
    }

    @Override
    public void modifyResult(ResultInfo<StoreInfo> info) {
        if (typeFlag == 0) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "修改店铺logo成功");
            ImageLoaderUtils.loadImage(this, info.getData().getStore_logo(), ivStoreInfo);
        } else if (typeFlag == 1) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "修改店铺名成功");
            tvStoreName.setText(info.getData().getStore_name());
        } else if (typeFlag == 2) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "修改品类成功");
            tvPostCate.setText(info.getData().getBiz_type());
        } else if (typeFlag == 5) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "修改店铺介绍成功");
            tvStoreIntro.setText(info.getData().getDescription());
        } else if (typeFlag == 6) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "修改签名成功");
            tvStoreSign.setText(info.getData().getSign());
        } else if (typeFlag == 7) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "修改通知成功");
            tvNotice.setText(info.getData().getStore_banner());
        }

    }

    @Override
    public void onUpdateSure(String content) {
        switch (typeFlag) {
            case 1:
                if (TextUtils.isEmpty(content)) {
                    ToastTools.showShort(this, "店铺名不能为空");
                } else {
                    params.put("store_name", OkhttpUtils.toRequestBody(content));
                    params.put("upd_type", OkhttpUtils.toRequestBody("1"));
                    present.getModifyStatus(params);
                    updateUserInforDialog.dismiss();
                }
                break;

            case 5:
                if (TextUtils.isEmpty(content)) {
                    ToastTools.showShort(this, "店铺介绍不能为空");
                } else {
                    params.put("description", OkhttpUtils.toRequestBody(content));
                    params.put("upd_type", OkhttpUtils.toRequestBody("5"));
                    present.getModifyStatus(params);
                    updateUserInforDialog.dismiss();
                }
                break;
            case 6:
                if (TextUtils.isEmpty(content)) {
                    ToastTools.showShort(this, "签名不能为空");
                } else {
                    params.put("sign", OkhttpUtils.toRequestBody(content));
                    params.put("upd_type", OkhttpUtils.toRequestBody("6"));
                    present.getModifyStatus(params);
                    updateUserInforDialog.dismiss();
                }
                break;
            case 7:
                if (TextUtils.isEmpty(content)) {
                    ToastTools.showShort(this, "通知不能为空");
                } else {
                    params.put("store_banner", OkhttpUtils.toRequestBody(content));
                    params.put("upd_type", OkhttpUtils.toRequestBody("7"));
                    present.getModifyStatus(params);
                    updateUserInforDialog.dismiss();
                }
                break;
        }
    }
}
