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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.colpencil.redwood.bean.CatBean;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.FamousApplyInfo;
import com.colpencil.redwood.bean.PostTypeInfo;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.Compressor;
import com.colpencil.redwood.function.tools.StringUtils;
import com.colpencil.redwood.function.tools.Validator;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CategoryDialog;
import com.colpencil.redwood.function.widgets.dialogs.PhotoDialog;
import com.colpencil.redwood.function.widgets.dialogs.SelectPlaceDialog;
import com.colpencil.redwood.function.widgets.dialogs.SetUserImageDialog;
import com.colpencil.redwood.present.mine.ApplyPresenter;
import com.colpencil.redwood.services.FamousApplyService;
import com.colpencil.redwood.view.adapters.ImageSelectAdapter;
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
import butterknife.OnClick;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.colpencil.redwood.R.id.award_img_layout;
import static com.colpencil.redwood.R.id.detail_recycler;
import static com.umeng.socialize.utils.DeviceConfig.context;
import static com.unionpay.mobile.android.global.a.C;
import static com.unionpay.mobile.android.global.a.F;
import static com.unionpay.mobile.android.global.a.v;


@ActivityFragmentInject(
        contentViewId = R.layout.activity_famous_apply)
public class FamousApplyActivity extends ColpencilActivity implements OnClickListener, ImageSelectAdapter.OnRecyclerViewItemClickListener, ApplayView, SetUserImageDialog.OnPhotoDialogClickListener, PhotoDialog.OnPhotoDialogClick {

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.iv_positive)
    ImageView iv_positive;
    @Bind(R.id.iv_negative)
    ImageView iv_negative;
    //    @Bind(R.id.iv_certificate)
    //    ImageView iv_certificate;
    public static final int REQUEST_CODE_SELECT = 100;//获奖作品9张
    public static final int REQUEST_CODE_AWARD = 102;//获奖证书3张
    public static final int REQUEST_CODE_PREVIEW = 101;
    public static final int REUQEST_CODE_AWDPRE = 103;
    @Bind(R.id.detail_award_winning)
    RecyclerView detailAwardWinning;
    @Bind(R.id.edt_name)
    EditText edtName;
    @Bind(R.id.id_number)
    EditText idNumber;

    @Bind(R.id.edt_address)
    EditText edtAddress;
    @Bind(R.id.tv_biz_type)
    TextView tvBizType;
    @Bind(R.id.bankcard_number)
    EditText bankcardNumber;
    @Bind(R.id.bankcard_type)
    EditText bankcardType;
    @Bind(R.id.edt_cardholder)
    EditText edtCardholder;
    @Bind(detail_recycler)
    RecyclerView detailRecycler;
    @Bind(R.id.tv_city)
    TextView tvCity;
    @Bind(R.id.tv_commit)
    TextView tvCommit;
    @Bind(R.id.edt_refername)
    EditText edtRefername;
    @Bind(R.id.edt_workroom)
    EditText edtWorkroom;
    @Bind(R.id.tv_workroom_region)
    TextView tvWorkroomRegion;
    @Bind(R.id.edt_workroom_address)
    EditText edtWorkroomAddress;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.layout_tv_biz_type)
    LinearLayout layoutTvBizType;
    @Bind(R.id.layout_work_room)
    LinearLayout layoutWorkRoom;
    @Bind(R.id.edt_shopstore)
    EditText edtShopstore;
    @Bind(R.id.tv_shopstore_region)
    TextView tvShopstoreRegion;
    @Bind(R.id.layout_shop_store)
    LinearLayout layoutShopStore;
    @Bind(R.id.edt_shopstore_address)
    EditText edtShopstoreAddress;
    @Bind(R.id.layout_region)
    LinearLayout layoutRegion;
    @Bind(R.id.add_img_layout)
    LinearLayout addLinearlayout;
    @Bind(R.id.award_img_layout)
    LinearLayout awardLinearlayout;
    @Bind(R.id.add_text)
    TextView addtv;
    @Bind(R.id.award_text)
    TextView awardtv;

    private int maxImgCount = 9;               //允许选择图片最大数
    private ImagePicker imagePicker;
    private ImageSelectAdapter adapter;
    private ArrayList<ImageItem> defaultDataArray = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();
    private ImageSelectAdapter adapterAward;
    private ArrayList<ImageItem> awardArray = new ArrayList<>();
    private List<File> fileAwardList = new ArrayList<>();
//    private String photoZ; //身份证正面
//    private String photoF; //身份证反面
    private int type = -1;
    private ApplyPresenter presenter;
    private AddresBean bean;
    private int touch = 0;
    private String sec_id = "";
    private CategoryDialog dialog;
    String s = "";
    private List<PostTypeInfo> list = new ArrayList<>();
    SharedPreferencesUtil sp;
    int regiontype = 0;   //个人住地址 店的地址等等用这个来区分
    private String region_id1 = "";
    private String region_id2 = "";
    private String sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    @Bind(R.id.updateImg_userInfor)
    RelativeLayout updateImg_userInfor;
    private SetUserImageDialog setUserImageDialog;
    private Bitmap head;// 头像Bitmap
    @Bind(R.id.iv_userInfor)
    SelectableRoundedImageView iv_userInfor;
    private File photoFile;
    String photoFilePath;
    private Uri muri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "store2.jpg"));
    @Bind(R.id.edt_storename)
    EditText edtStore;
    private ArrayList<ImageItem> photoFDataArray = new ArrayList<>();
    private ArrayList<ImageItem> photoZDataArray = new ArrayList<>();
    private File zFile;
    private File fFile;
    private PhotoDialog photoDialog;
    @Override
    protected void initViews(View view) {
        tv_title.setText("名师名匠申请");
        iv_back.setOnClickListener(this);
        iv_positive.setOnClickListener(this);
        iv_negative.setOnClickListener(this);
        layoutRegion.setOnClickListener(this);
        layoutShopStore.setOnClickListener(this);
        tvCommit.setOnClickListener(this);
        layoutTvBizType.setOnClickListener(this);
        tvType.setText("主创类别：");
        tvBizType.setText("请点击选择主创类别");
        layoutWorkRoom.setOnClickListener(this);
        updateImg_userInfor.setOnClickListener(this);
        //        iv_certificate.setOnClickListener(this);
        SharedPreferencesUtil.getInstance(context).setInt("region_id", -1);
        String text1 = "最多添加" + maxImgCount + "张";
        String text2 = "最多添加" + (maxImgCount - 6) + "张";
        addtv.setText(text1);
        awardtv.setText(text2);
        initImagePicker();
        initAdapter();
        initAdapterAward();
    }

    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setSelectLimit(maxImgCount);
    }

    private void initAdapterAward() {
        adapterAward = new ImageSelectAdapter(this, awardArray, 3);
        adapterAward.setOnItemClickListener(this);
        detailAwardWinning.setLayoutManager(new GridLayoutManager(this, 4));
        detailAwardWinning.setHasFixedSize(true);
        detailAwardWinning.setAdapter(adapterAward);

    }

    private void initAdapter() {
        adapter = new ImageSelectAdapter(this, defaultDataArray, maxImgCount);
        adapter.setOnItemClickListener(this);
        detailRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        detailRecycler.setHasFixedSize(true);
        detailRecycler.setAdapter(adapter);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new ApplyPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onCamera() {
        ImagePicker.getInstance().setSelectLimit(1);
        Intent intent = new Intent(FamousApplyActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    @Override
    public void onSelect() {
        ImagePicker.getInstance().setSelectLimit(1);
        Intent intent1 = new Intent(FamousApplyActivity.this, ImageGridActivity.class);
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
    private void showDialog() {
        if (photoDialog == null) {
            photoDialog = new PhotoDialog(FamousApplyActivity.this);
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
        int id = view.getId();
        if (id == R.id.iv_back) {
            Intent intent = new Intent(FamousApplyActivity.this, BusinessActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.iv_positive) {
            type = 0;
            if (!ListUtils.listIsNullOrEmpty(photoZDataArray)) {
                toIntent(photoZDataArray);
            } else {
                showDialog();
            }
        } else if (id == R.id.iv_negative) {
            type = 1;
            if (!ListUtils.listIsNullOrEmpty(photoFDataArray)) {
                toIntent(photoFDataArray);
            } else {
                showDialog();
            }
        } else if (id == R.id.layout_region) {
            if (bean != null && bean.getData().size() != 0) {
                new SelectPlaceDialog(this, tvCity, bean, 0).show();
            } else {
                touch = 1;
                regiontype = 0;
                showLoading("加载中");
            }

        } else if (id == R.id.layout_work_room) {
            if (bean != null && bean.getData().size() != 0) {
                new SelectPlaceDialog(this, tvWorkroomRegion, bean, 1).show();
            } else {
                touch = 1;
                regiontype = 1;
                showLoading("加载中");
            }
        } else if (id == R.id.layout_shop_store) {
            if (bean != null && bean.getData().size() != 0) {
                new SelectPlaceDialog(this, tvShopstoreRegion, bean, 2).show();
            } else {
                touch = 1;
                regiontype = 2;
                showLoading("加载中");
            }
        } else if (id == R.id.tv_commit) {
            commit();
        } else if (id == R.id.layout_tv_biz_type) {
            if (dialog == null) {
                dialog = new CategoryDialog(FamousApplyActivity.this, R.style.PostDialogTheme, list);
            }
            dialog.setTitle("请选择主创类别");
            dialog.setListener(new CategoryDialog.PostClickListener() {
                @Override
                public void closeClick() {
                    dialog.dismiss();
                }

                @Override
                public void itemUnClick() {
                    tvBizType.setText("请点击选择主创类别");
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
        } else if (id == R.id.updateImg_userInfor) {
            //用户修改头像

            if (setUserImageDialog == null) {
                setUserImageDialog = new SetUserImageDialog(FamousApplyActivity.this);
                setUserImageDialog.setOnPhotoDialogClickListener(this);
            }
            setUserImageDialog.show();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        presenter.loadRegion(0);
        presenter.loadCatList(0);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.add_img_layout)
    void clikcAdd() {
        type = 2;
        this.onItemClick(addtv, -3);
    }

    @OnClick(R.id.award_img_layout)
    void clickAward() {
        this.onItemClick(awardtv, -3);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case Constants.IMAGE_ITEM_ADD:
                if (view.getParent().equals(detailRecycler)) {
                    type = 2;
                    imagePicker.setSelectLimit(maxImgCount - defaultDataArray.size());
                    Intent intent = new Intent(this, ImageGridActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SELECT);
                } else {
                    imagePicker.setSelectLimit(maxImgCount - awardArray.size() - 6);
                    Intent intent = new Intent(this, ImageGridActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_AWARD);
                }
                //打开选择,本次允许选择的数量
                break;
            case Constants.THREE_ADD:
                if (view.equals(addtv)) {
                    type = 2;
                    imagePicker.setSelectLimit(maxImgCount - defaultDataArray.size());
                    Intent intent = new Intent(this, ImageGridActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SELECT);
                } else {
                    imagePicker.setSelectLimit(maxImgCount - awardArray.size() - 6);
                    Intent aintent = new Intent(this, ImageGridActivity.class);
                    startActivityForResult(aintent, REQUEST_CODE_AWARD);
                }
                break;

            default:
                //打开预览
                if (view.getParent().equals(detailRecycler)) {
                    type = 2;
                    Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                    intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                    intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                    intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                    startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);

                } else {
                    Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                    intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapterAward.getImages());
                    intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                    intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                    startActivityForResult(intentPreview, REUQEST_CODE_AWDPRE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(photoDialog!=null){
            photoDialog.dismiss();
        }
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {

                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if(type == 0){
                    photoZDataArray.addAll(images);
                }else if(type == 1){
                    photoFDataArray.addAll(images);
                }else if(type == 2){
                    defaultDataArray.addAll(images);
                    adapter.setImages(defaultDataArray);
                    if (defaultDataArray.size() == 0) {
                        addLinearlayout.setVisibility(View.VISIBLE);
                        detailRecycler.setVisibility(View.GONE);
                    }else{
                        addLinearlayout.setVisibility(View.GONE);
                        detailRecycler.setVisibility(View.VISIBLE);
                    }
                    fileList.clear();
                    List<File> files = new ArrayList<>();
                    for (int i = 0; i < defaultDataArray.size(); i++) {
                        files.add(new File(defaultDataArray.get(i).path));
                    }
                    compress(files);
                }
                if (photoFDataArray.size() != 0) {
                    ImagePicker.getInstance().getImageLoader().displayImage(FamousApplyActivity.this, photoFDataArray.get(0).path, iv_negative, 0, 0);
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
                    ImagePicker.getInstance().getImageLoader().displayImage(FamousApplyActivity.this, photoZDataArray.get(0).path, iv_positive, 0, 0);
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

            }
            if (data != null && requestCode == REQUEST_CODE_AWARD) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                awardArray.addAll(images);
                if(awardArray.size() == 0){
                    detailAwardWinning.setVisibility(View.GONE);
                    awardLinearlayout.setVisibility(View.VISIBLE);
                }else{
                    detailAwardWinning.setVisibility(View.VISIBLE);
                    awardLinearlayout.setVisibility(View.GONE);
                }
                adapterAward.setImages(awardArray);
                fileAwardList.clear();
                List<File> files = new ArrayList<>();
                for (int i = 0; i < awardArray.size(); i++) {
                    files.add(new File(awardArray.get(i).path));
                }
                compress2(files);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if(type == 0){
                    photoZDataArray.clear();
                    photoZDataArray.addAll(images);
                }else if(type == 1){
                    photoFDataArray.clear();
                    photoFDataArray.addAll(images);
                }else if(type == 2){
                    defaultDataArray.clear();
                    defaultDataArray.addAll(images);
                    if (defaultDataArray.size() == 0) {
                        addLinearlayout.setVisibility(View.VISIBLE);
                        detailRecycler.setVisibility(View.GONE);
                    }else{
                        addLinearlayout.setVisibility(View.GONE);
                        detailRecycler.setVisibility(View.VISIBLE);
                    }
                    adapter.setImages(defaultDataArray);
                    fileList.clear();
                    List<File> files = new ArrayList<>();
                    for (int i = 0; i < defaultDataArray.size(); i++) {
                        files.add(new File(defaultDataArray.get(i).path));
                    }
                    compress(files);
                }
                if (photoFDataArray.size() != 0) {
                    ImagePicker.getInstance().getImageLoader().displayImage(FamousApplyActivity.this, photoFDataArray.get(0).path, iv_negative, 0, 0);
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
                    ImagePicker.getInstance().getImageLoader().displayImage(FamousApplyActivity.this, photoZDataArray.get(0).path, iv_positive, 0, 0);
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
            }
            if (data != null && requestCode == REUQEST_CODE_AWDPRE) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                awardArray.clear();
                awardArray.addAll(images);
                if(awardArray.size() == 0){
                    detailAwardWinning.setVisibility(View.GONE);
                    awardLinearlayout.setVisibility(View.VISIBLE);
                }else{
                    detailAwardWinning.setVisibility(View.VISIBLE);
                    awardLinearlayout.setVisibility(View.GONE);
                }
                adapterAward.setImages(awardArray);
                fileAwardList.clear();
                List<File> files = new ArrayList<>();
                for (int i = 0; i < awardArray.size(); i++) {
                    files.add(new File(awardArray.get(i).path));
                }
                compress2(files);
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                cropPhoto(data.getData());// 裁剪图片
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                if (resultCode == RESULT_OK) {
                    cropPhoto(muri);// 裁剪图片
                }
            }
        } else if (requestCode == 3) {
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
        }
    }

    public void compress(List<File> list) {
        if (list.size() > 0) {
            Luban.compress(this, list).putGear(Luban.THIRD_GEAR).launch(new OnMultiCompressListener() {
                @Override
                public void onStart() {
                }

                @Override
                public void onSuccess(List<File> fileList) {
                    FamousApplyActivity.this.fileList.addAll(fileList);
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }
    }

    public void compress2(List<File> list) {
        if (list.size() > 0) {
            //            DialogTools.showLoding(this, "温馨提示", "获取中。。。");
            Luban.compress(this, list).putGear(Luban.THIRD_GEAR).launch(new OnMultiCompressListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(List<File> fileList) {

                    FamousApplyActivity.this.fileAwardList.addAll(fileList);
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }
    }

    private void commit() {

        if (TextStringUtils.isEmpty(edtName.getText().toString())) {
            Toast.makeText(FamousApplyActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StringUtils.isNull(Validator.validateIDStr(idNumber.getText().toString()))) {
            Toast.makeText(FamousApplyActivity.this,Validator.validateIDStr(idNumber.getText().toString()), Toast.LENGTH_SHORT).show();
            return;
        }
        if (zFile==null) {
            Toast.makeText(FamousApplyActivity.this, "请上传身份证正面照", Toast.LENGTH_SHORT).show();
            return;
        }
        if (fFile == null) {
            Toast.makeText(FamousApplyActivity.this, "请上传身份证反面照", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextStringUtils.isEmpty(photoFilePath)){
            Toast.makeText(FamousApplyActivity.this, "请上传店铺照片", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextStringUtils.isEmpty(edtStore.getText().toString())){
            Toast.makeText(FamousApplyActivity.this, "请输入店铺名称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tvCity.getText().toString().equals("请点击选择所在的区域")) {
            Toast.makeText(FamousApplyActivity.this, "请选择所在的区域", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(edtAddress.getText().toString())) {
            Toast.makeText(FamousApplyActivity.this, "请输入详细地址", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tvBizType.getText().toString().equals("请点击选择主创类别") || sec_id.equals("")) {
            Toast.makeText(FamousApplyActivity.this, "请选择主创类别", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(bankcardNumber.getText().toString())) {
            Toast.makeText(FamousApplyActivity.this, "请填写银行卡号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(bankcardType.getText().toString())) {
            Toast.makeText(FamousApplyActivity.this, "请填写卡类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(edtCardholder.getText().toString())) {
            Toast.makeText(FamousApplyActivity.this, "请填写持卡人", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tvWorkroomRegion.getText().toString().equals("请点击选择工作室区域")) {
            region_id1 = "";
        } else {
            region_id1 = sp.getInstance(this).getInt("region_id1") + "";
        }
        if (tvShopstoreRegion.getText().toString().equals("请点击选择常驻店铺区域")) {
            region_id2 = "";
        } else {
            region_id2 = sp.getInstance(this).getInt("region_id2") + "";
        }
        showLoading("正在提交");
        FamousApplyInfo info = new FamousApplyInfo();
        info.setPics(fileList);
        info.setPrize_pics(fileAwardList);
        info.setAddress(edtAddress.getText().toString());
        info.setBiz_type(sec_id);
        info.setCard(bankcardNumber.getText().toString());
        info.setCard_type(bankcardType.getText().toString());
        info.setCardholder(edtCardholder.getText().toString());
        info.setPs_region_id(region_id2);
        info.setStudio_region_id(region_id1);
        info.setRegion_id(sp.getInstance(this).getInt("region_id0") + "");
        info.setReal_name(edtName.getText().toString());
        info.setId_img(zFile);
        info.setId_img_opposite(fFile);
        info.setStore_file(photoFile);
        info.setStore_name(edtStore.getText().toString());
        info.setId_number(idNumber.getText().toString());
        info.setStore_type(3 + "");
        info.setStudio_name(edtWorkroom.getText().toString());
        info.setStudio_address(edtWorkroomAddress.getText().toString());
        info.setPs_name(edtShopstore.getText().toString());
        info.setPs_address(edtShopstoreAddress.getText().toString());
        info.setReferrer(edtRefername.getText().toString());
        final Intent intent = new Intent(FamousApplyActivity.this, FamousApplyService.class);
        intent.putExtra("data", info);
        startService(intent);
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                hideLoading();
                Intent mIntent = new Intent(FamousApplyActivity.this, BusinessActivity.class);
                startActivity(mIntent);
                FamousApplyActivity.this.finish();
            }
        };
        timer.start();

    }


    @Override
    public void applyError(String message) {
        Toast.makeText(FamousApplyActivity.this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void load(AddresBean regionInfo) {
        hideLoading();
        bean = regionInfo;
        if (touch == 1 && regiontype == 0) {
            new SelectPlaceDialog(this, tvCity, bean, 0).show();
        }
        if (touch == 1 && regiontype == 1) {
            new SelectPlaceDialog(this, tvCity, bean, 1).show();
        }
        if (touch == 1 && regiontype == 2) {
            new SelectPlaceDialog(this, tvCity, bean, 2).show();
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

    void deleteOldFile() {
        photoFile = new File(sdCardPath + "/" + "userphoto.jpg");
        if (photoFile.exists()) {
            photoFile.delete();
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
