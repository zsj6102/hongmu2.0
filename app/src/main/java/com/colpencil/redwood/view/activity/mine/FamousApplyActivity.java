package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CategoryDialog;
import com.colpencil.redwood.function.widgets.dialogs.SelectPlaceDialog;
import com.colpencil.redwood.present.mine.ApplyPresenter;
import com.colpencil.redwood.services.FamousApplyService;
import com.colpencil.redwood.view.adapters.ImageSelectAdapter;
import com.colpencil.redwood.view.impl.ApplayView;
import com.jph.takephoto.model.TResult;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;

import static com.umeng.socialize.utils.DeviceConfig.context;



@ActivityFragmentInject(
        contentViewId = R.layout.activity_famous_apply)
public class FamousApplyActivity extends PhotoBaseActivity implements OnClickListener, ImageSelectAdapter.OnRecyclerViewItemClickListener, ApplayView {

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
    @Bind(R.id.detail_recycler)
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
    private int maxImgCount = 9;               //允许选择图片最大数
    private ImagePicker imagePicker;
    private ImageSelectAdapter adapter;
    private ArrayList<ImageItem> defaultDataArray = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();
    private ImageSelectAdapter adapterAward;
    private ArrayList<ImageItem> awardArray = new ArrayList<>();
    private List<File> fileAwardList = new ArrayList<>();
    private String photoZ; //身份证正面
    private String photoF; //身份证反面
    private int type = 0;
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
        tvType.setText("主创类别");
        tvBizType.setText("请点击选择主创类别");
        layoutWorkRoom.setOnClickListener(this);
        //        iv_certificate.setOnClickListener(this);
        SharedPreferencesUtil.getInstance(context).setInt("region_id",-1);
        initImagePicker();
        initAdapter();
        initAdapterAward();
    }

    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance() ;
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
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (type == 0) {
            Glide.with(this).load(result.getImages().get(0).getCompressPath()).into(iv_positive);
            photoZ = result.getImages().get(0).getCompressPath();
        } else if (type == 1) {
            Glide.with(this).load(result.getImages().get(0).getCompressPath()).into(iv_negative);
            photoF = result.getImages().get(0).getCompressPath();
        } else {
            //            Glide.with(this).load(result.getImages().get(0).getCompressPath()).into(iv_certificate);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            Intent intent = new Intent(FamousApplyActivity.this,BusinessActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.iv_positive) {
            type = 0;
            openSelect(false, 1);
        } else if (id == R.id.iv_negative) {
            type = 1;
            openSelect(false, 1);
        } else if (id == R.id.layout_region) {
            if (bean != null && bean.getData().size() != 0) {
                new SelectPlaceDialog(this, tvCity, bean,0).show();
            } else {
                touch = 1;
                regiontype = 0;
                showLoading("加载中");
            }

        } else if (id == R.id.layout_work_room) {
            if (bean != null && bean.getData().size() != 0) {
                new SelectPlaceDialog(this, tvWorkroomRegion, bean,1).show();
            } else {
                touch = 1;
                regiontype = 1;
                showLoading("加载中");
            }
        } else if(id == R.id.layout_shop_store){
            if (bean != null && bean.getData().size() != 0) {
                new SelectPlaceDialog(this, tvShopstoreRegion, bean,2).show();
            } else {
                touch = 1;
                regiontype = 2;
                showLoading("加载中");
            }
        }else if (id == R.id.tv_commit) {
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

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case Constants.IMAGE_ITEM_ADD:
                if (view.getParent().equals(detailRecycler)) {
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
            default:
                //打开预览
                if (view.getParent().equals(detailRecycler)) {
                    Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                    intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                    intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);

                    startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);

                } else {
                    Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                    intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapterAward.getImages());
                    intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);

                    startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                defaultDataArray.addAll(images);
                adapter.setImages(defaultDataArray);
                fileList.clear();
                List<File> files = new ArrayList<>();
                for (int i = 0; i < defaultDataArray.size(); i++) {
                    files.add(new File(defaultDataArray.get(i).path));
                }
                compress(files);
            }
            if (data != null && requestCode == REQUEST_CODE_AWARD) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                awardArray.addAll(images);
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
                defaultDataArray.clear();
                defaultDataArray.addAll(images);
                adapter.setImages(defaultDataArray);
                fileList.clear();
                List<File> files = new ArrayList<>();
                for (int i = 0; i < defaultDataArray.size(); i++) {
                    files.add(new File(defaultDataArray.get(i).path));
                }
                compress(files);
            }
            if (data != null && requestCode == REUQEST_CODE_AWDPRE) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                awardArray.clear();
                awardArray.addAll(images);
                adapterAward.setImages(awardArray);
                fileAwardList.clear();
                List<File> files = new ArrayList<>();
                for (int i = 0; i < awardArray.size(); i++) {
                    files.add(new File(awardArray.get(i).path));
                }
                compress2(files);
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
        if (TextStringUtils.isEmpty(idNumber.getText().toString())) {
            Toast.makeText(FamousApplyActivity.this, "身份证号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(photoZ)) {
            Toast.makeText(FamousApplyActivity.this, "请上传身份证正面照", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(photoF)) {
            Toast.makeText(FamousApplyActivity.this, "请上传身份证反面照", Toast.LENGTH_SHORT).show();
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
        if(tvWorkroomRegion.getText().toString().equals("请点击选择工作室区域")){
            region_id1 = "";
        }else{
            region_id1 = sp.getInstance(this).getInt("region_id1")+"";
        }
        if(tvShopstoreRegion.getText().toString().equals("请点击选择常驻店铺区域")){
            region_id2 = "";
        }else{
            region_id2 = sp.getInstance(this).getInt("region_id2")+"";
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
        info.setRegion_id(sp.getInstance(this).getInt("region_id0")+"");
        info.setReal_name(edtName.getText().toString());
        info.setId_img(new File(photoZ));
        info.setId_img_opposite(new File(photoF));

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
                Intent mIntent = new Intent(FamousApplyActivity.this,BusinessActivity.class);
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
            new SelectPlaceDialog(this, tvCity, bean,0).show();
        }
        if (touch == 1 && regiontype == 1) {
            new SelectPlaceDialog(this, tvCity, bean,1).show();
        }
        if (touch == 1 && regiontype == 2) {
            new SelectPlaceDialog(this, tvCity, bean,2).show();
        }
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
}
