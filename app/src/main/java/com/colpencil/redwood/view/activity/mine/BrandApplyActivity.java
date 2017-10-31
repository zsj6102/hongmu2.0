package com.colpencil.redwood.view.activity.mine;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.colpencil.redwood.R;
import com.colpencil.redwood.base.PhotoBaseActivity;
import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.BrandApplyInfo;
import com.colpencil.redwood.bean.CatBean;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.PostTypeInfo;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.function.Compressor;
import com.colpencil.redwood.function.tools.StringUtils;
import com.colpencil.redwood.function.tools.Validator;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CategoryDialog;
import com.colpencil.redwood.function.widgets.dialogs.PhotoDialog;
import com.colpencil.redwood.function.widgets.dialogs.SelectPlaceDialog;
import com.colpencil.redwood.function.widgets.dialogs.SetUserImageDialog;
import com.colpencil.redwood.present.mine.ApplyPresenter;
import com.colpencil.redwood.services.BrandApplyService;
import com.colpencil.redwood.view.impl.ApplayView;
import com.jph.takephoto.model.TResult;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Ui.SelectableRoundedImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.colpencil.redwood.R.id.tv_city;


@ActivityFragmentInject(
        contentViewId = R.layout.activity_brand_apply)
public class BrandApplyActivity extends ColpencilActivity implements View.OnClickListener, ApplayView, SetUserImageDialog.OnPhotoDialogClickListener, PhotoDialog.OnPhotoDialogClick {

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    //身份证正面
    @Bind(R.id.iv_positive)
    ImageView iv_positive;
    //身份证反面
    @Bind(R.id.iv_negative)
    ImageView iv_negative;
    //营业执照
    @Bind(R.id.iv_license)
    ImageView iv_license;
    @Bind(R.id.layout_region)
    LinearLayout layoutRegion;
    @Bind(R.id.layout_tv_biz_type)
    LinearLayout layoutTvBizType;
    @Bind(R.id.tv_biz_type)
    TextView tvBizType;
    @Bind(tv_city)
    TextView tvCity;
    @Bind(R.id.edt_name)
    EditText edtName;
    @Bind(R.id.id_number)
    EditText idNumber;
    @Bind(R.id.edt_address)
    EditText edtAddress;
    @Bind(R.id.bankcard_number)
    EditText bankcardNumber;
    @Bind(R.id.bankcard_type)
    EditText bankcardType;
    @Bind(R.id.edt_cardholder)
    EditText edtCardholder;
    @Bind(R.id.edt_store_name)
    EditText edtStoreName;
    @Bind(R.id.edt_license)
    EditText edtLicense;
    @Bind(R.id.edt_storename)
    EditText edtStore;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    private AddresBean bean;
    private ApplyPresenter presenter;
    private String sec_id = "";
    private CategoryDialog dialog;
    private int touch = 0;

    /**
     * 0表示身份证正面，1表示身份证反面，2表示营业执照
     */
    private int type = 0;
    @Bind(R.id.updateImg_userInfor)
    RelativeLayout updateImg_userInfor;
    private SetUserImageDialog setUserImageDialog;
    private Bitmap head;// 头像Bitmap
    @Bind(R.id.iv_userInfor)
    SelectableRoundedImageView iv_userInfor;
    private File photoFile;
    String photoFilePath;
    private Uri muri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "store.jpg"));
    private List<PostTypeInfo> list = new ArrayList<>();
    String s = "";
    //    private String photoZ; //身份证正面
    //    private String photoF; //身份证反面
    //    private String photoL;//营业执照
    private ArrayList<ImageItem> photoFDataArray = new ArrayList<>();
    private ArrayList<ImageItem> photoZDataArray = new ArrayList<>();
    private ArrayList<ImageItem> photoLDataArray = new ArrayList<>();
    private File zFile;
    private File fFile;
    private File lFile;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private PhotoDialog photoDialog;
    private String sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    protected void initViews(View view) {
        tv_title.setText("品牌商家申请");
        iv_back.setOnClickListener(this);
        iv_positive.setOnClickListener(this);
        iv_negative.setOnClickListener(this);
        iv_license.setOnClickListener(this);
        layoutRegion.setOnClickListener(this);
        layoutTvBizType.setOnClickListener(this);
        updateImg_userInfor.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        ImagePicker.getInstance().setMultiMode(false);
        ImagePicker.getInstance().setCrop(false);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new ApplyPresenter();
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        presenter.loadRegion(0);
        presenter.loadCatList(0);
        ButterKnife.bind(this);
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
    private void showDialog() {
        if (photoDialog == null) {
            photoDialog = new PhotoDialog(BrandApplyActivity.this);
            photoDialog.setOnPhotoDialogClickListener(this);
        }
        photoDialog.show();
    }

    private void toIntent(ArrayList<ImageItem> array) {
        Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, array);
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                Intent intent = new Intent(BrandApplyActivity.this, BusinessActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_positive:
                type = 0;
               if(!ListUtils.listIsNullOrEmpty(photoZDataArray)){
                   toIntent(photoZDataArray);
               }else{
                   showDialog();
               }
                break;
            case R.id.iv_negative:
                type = 1;
                if(!ListUtils.listIsNullOrEmpty(photoFDataArray)){
                    toIntent(photoFDataArray);
                }else{
                    showDialog();
                }
                break;
            case R.id.iv_license:
                type = 2;
                if(!ListUtils.listIsNullOrEmpty(photoLDataArray)){
                    toIntent(photoLDataArray);
                }else{
                    showDialog();
                }
                break;
            case R.id.layout_tv_biz_type:
                if (dialog == null) {
                    dialog = new CategoryDialog(BrandApplyActivity.this, R.style.PostDialogTheme, list);
                }
                dialog.setTitle("请选择经营品类");
                dialog.setListener(new CategoryDialog.PostClickListener() {
                    @Override
                    public void closeClick() {
                        dialog.dismiss();
                    }

                    @Override
                    public void itemUnClick() {
                        tvBizType.setText("请选择经营品类");
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
                        tvBizType.setText(s);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.layout_region:
                if (bean != null && bean.getData().size() != 0) {
                    new SelectPlaceDialog(this, tvCity, bean, 0).show();
                } else {
                    touch = 1;
                    showLoading("加载中");
                }
                break;
            case R.id.tv_submit:
                submit();
                break;
            case R.id.updateImg_userInfor://用户修改头像

                if (setUserImageDialog == null) {
                    setUserImageDialog = new SetUserImageDialog(BrandApplyActivity.this);
                    setUserImageDialog.setOnPhotoDialogClickListener(this);
                }
                setUserImageDialog.show();
                break;
        }

    }

    @Override
    public void onCamera() {
        ImagePicker.getInstance().setSelectLimit(1);
        Intent intent = new Intent(BrandApplyActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    @Override
    public void onSelect() {
        ImagePicker.getInstance().setSelectLimit(1);
        Intent intent1 = new Intent(BrandApplyActivity.this, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
        //                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
        startActivityForResult(intent1, REQUEST_CODE_SELECT);
    }

    @Override
    public void onCancel() {
        photoDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(photoDialog!=null){
            photoDialog.dismiss();
        }
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
                        iv_userInfor.setImageBitmap(head);
                        deleteOldFile();
                        photoFile = new File(sdCardPath + "/" + "userphoto.jpg");

                        try {
                            FileOutputStream fop = new FileOutputStream(photoFile);
                            head.compress(Bitmap.CompressFormat.JPEG, 100, fop);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        photoFilePath = photoFile.getAbsolutePath();
                    } else {
                        ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "选择图片失败");
                    }
                }
                break;
            default:
                break;
        }
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if(type == 0){
                    photoZDataArray.addAll(images);
                }else if(type == 1){
                    photoFDataArray.addAll(images);
                }else{
                    photoLDataArray.addAll(images);
                }
                if (photoFDataArray.size() != 0) {
                    ImagePicker.getInstance().getImageLoader().displayImage(BrandApplyActivity.this, photoFDataArray.get(0).path, iv_negative, 0, 0);
                    File mfile = new File(photoFDataArray.get(0).path);
                    try {
                        fFile = new Compressor(this).compressToFile(mfile, "f.jpg");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    fFile = null;
                    iv_negative.setImageResource(R.mipmap.negative_card);
                }

                if (photoZDataArray.size() != 0) {
                    ImagePicker.getInstance().getImageLoader().displayImage(BrandApplyActivity.this, photoZDataArray.get(0).path, iv_positive, 0, 0);
                    File mfile = new File(photoZDataArray.get(0).path);
                    try {
                        zFile = new Compressor(this).compressToFile(mfile, "z.jpg");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    zFile = null;
                    iv_positive.setImageResource(R.mipmap.positive_card);
                }
                if(photoLDataArray.size()!=0){
                    ImagePicker.getInstance().getImageLoader().displayImage(BrandApplyActivity.this, photoLDataArray.get(0).path, iv_license, 0, 0);
                    File mfile = new File(photoLDataArray.get(0).path);
                    try {
                        lFile = new Compressor(this).compressToFile(mfile, "l.jpg");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    lFile = null;
                    iv_license.setImageResource(R.mipmap.license_add);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (type == 0) {
                    photoZDataArray.clear();
                    photoZDataArray.addAll(images);
                } else if(type == 1){
                    photoFDataArray.clear();
                    photoFDataArray.addAll(images);
                }else{
                    photoLDataArray.clear();
                    photoLDataArray.addAll(images);
                }
                if (photoFDataArray.size() != 0) {
                    ImagePicker.getInstance().getImageLoader().displayImage(BrandApplyActivity.this, photoFDataArray.get(0).path, iv_negative, 0, 0);
                    File mfile = new File(photoFDataArray.get(0).path);
                    try {
                        fFile = new Compressor(this).compressToFile(mfile, "f.jpg");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    fFile = null;
                    iv_negative.setImageResource(R.mipmap.negative_card);
                }

                if (photoZDataArray.size() != 0) {
                    ImagePicker.getInstance().getImageLoader().displayImage(BrandApplyActivity.this, photoZDataArray.get(0).path, iv_positive, 0, 0);
                    File mfile = new File(photoZDataArray.get(0).path);
                    try {
                        zFile = new Compressor(this).compressToFile(mfile, "z.jpg");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    zFile = null;
                    iv_positive.setImageResource(R.mipmap.positive_card);
                }
                if(photoLDataArray.size()!=0){
                    ImagePicker.getInstance().getImageLoader().displayImage(BrandApplyActivity.this, photoLDataArray.get(0).path, iv_license, 0, 0);
                    File mfile = new File(photoLDataArray.get(0).path);
                    try {
                        lFile = new Compressor(this).compressToFile(mfile, "l.jpg");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    lFile = null;
                    iv_license.setImageResource(R.mipmap.license_add);
                }
            }
        }
    }

    void deleteOldFile() {
        photoFile = new File(sdCardPath + "/" + "userphoto.jpg");
        if (photoFile.exists()) {
            photoFile.delete();
        }
    }

    private void submit() {
        if (TextStringUtils.isEmpty(edtName.getText().toString())) {
            Toast.makeText(BrandApplyActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StringUtils.isNull(Validator.validateIDStr(idNumber.getText().toString()))) {
            Toast.makeText(BrandApplyActivity.this, Validator.validateIDStr(idNumber.getText().toString()), Toast.LENGTH_SHORT).show();
            return;
        }
        if (zFile==null) {
            Toast.makeText(BrandApplyActivity.this, "请上传身份证正面照", Toast.LENGTH_SHORT).show();
            return;
        }
        if (fFile==null) {
            Toast.makeText(BrandApplyActivity.this, "请上传身份证反面照", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(photoFilePath)) {
            Toast.makeText(BrandApplyActivity.this, "请上传店铺照片", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tvCity.getText().toString().equals("请点击选择所在的区域")) {
            Toast.makeText(BrandApplyActivity.this, "请选择所在的区域", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(edtAddress.getText().toString())) {
            Toast.makeText(BrandApplyActivity.this, "请输入详细地址", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(edtStore.getText().toString())) {
            Toast.makeText(BrandApplyActivity.this, "请输入店铺名称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tvBizType.getText().toString().equals("请点击选择经营品类") || sec_id.equals("")) {
            Toast.makeText(BrandApplyActivity.this, "请选择经营品类", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(bankcardNumber.getText().toString())) {
            Toast.makeText(BrandApplyActivity.this, "请填写银行卡号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(bankcardType.getText().toString())) {
            Toast.makeText(BrandApplyActivity.this, "请填写卡类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(edtCardholder.getText().toString())) {
            Toast.makeText(BrandApplyActivity.this, "请填写持卡人", Toast.LENGTH_SHORT).show();
            return;
        }
        if (lFile == null) {
            Toast.makeText(BrandApplyActivity.this, "请上传营业执照", Toast.LENGTH_SHORT).show();
            return;
        }
        showLoading("正在提交");
        BrandApplyInfo info = new BrandApplyInfo();
        info.setStore_type(2 + "");
        info.setReal_name(edtName.getText().toString());
        info.setId_img(zFile);
        info.setStore_file(photoFile);
        info.setStore_name(edtStore.getText().toString());
        info.setId_img_opposite(fFile);
        info.setLicense_img(lFile);
        info.setLicense(edtLicense.getText().toString());
        info.setPhysical_store_name(edtStoreName.getText().toString());
        info.setRegion_id(SharedPreferencesUtil.getInstance(this).getInt("region_id0") + "");
        info.setAddress(edtAddress.getText().toString());
        info.setBiz_type(sec_id);
        info.setCard(bankcardNumber.getText().toString());
        info.setCard_type(bankcardType.getText().toString());
        info.setCardholder(edtCardholder.getText().toString());
        info.setId_number(idNumber.getText().toString());
        Intent intent = new Intent(BrandApplyActivity.this, BrandApplyService.class);
        intent.putExtra("data", info);
        startService(intent);
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                hideLoading();
                Intent mIntent = new Intent(BrandApplyActivity.this, BusinessActivity.class);
                startActivity(mIntent);
                BrandApplyActivity.this.finish();
            }
        };
        timer.start();
    }

    @Override
    public void applyError(String message) {
        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void load(AddresBean regionInfo) {
        hideLoading();
        bean = regionInfo;
        if (touch == 1) {
            new SelectPlaceDialog(this, tvCity, bean, 0).show();
        }
    }

    @Override
    public void loadPro(ResultInfo<String> result) {

    }

    @Override
    public void loadCat(CatListBean catListBean) {
        if (!ListUtils.listIsNullOrEmpty(catListBean.getData())) {
            list.clear();
            for (CatBean vo : catListBean.getData()) {
                PostTypeInfo info = new PostTypeInfo();
                info.setTypename(vo.getCat_name());
                info.setSec_id(String.valueOf(vo.getCat_id()));
                info.setChoose(false);
                list.add(info);
            }
        }
    }

    @Override
    public void onTakePhoto() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String fileName = String.valueOf(System.currentTimeMillis());
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, muri);
        startActivityForResult(openCameraIntent, 2);
        setUserImageDialog.dismiss();
    }

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
    public void onSelectPhoto() {
        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent1, 1);
        setUserImageDialog.dismiss();
    }

    @Override
    public void onCancelClick() {
        setUserImageDialog.dismiss();
    }
}
