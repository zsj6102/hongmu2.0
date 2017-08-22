package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.CatBean;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.FastStoreInfo;
import com.colpencil.redwood.bean.PostTypeInfo;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CategoryDialog;
import com.colpencil.redwood.present.mine.ApplyPresenter;
import com.colpencil.redwood.services.PublishStoreService;
import com.colpencil.redwood.view.adapters.ImageSelectAdapter;
import com.colpencil.redwood.view.impl.ApplayView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;
import me.shaohui.advancedluban.OnMultiCompressListener;


@ActivityFragmentInject(contentViewId = R.layout.activity_publish_fast)
public class PublishStoreActivity extends ColpencilActivity implements View.OnClickListener, ImageSelectAdapter.OnRecyclerViewItemClickListener, ApplayView {

    @Bind(R.id.detail_recycler)
    RecyclerView recycler;

    @Bind(R.id.ll_category)
    LinearLayout llCategory;
    @Bind(R.id.post_news_category)
    TextView postNewsCategory;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_main_title)
    TextView tvMainTitle;
    @Bind(R.id.post_title)
    EditText postTitle;
    @Bind(R.id.edt_store_price)
    EditText edtStorePrice;
    @Bind(R.id.edt_store_left)
    EditText edtStoreLeft;
    @Bind(R.id.post_content)
    EditText postContent;
    @Bind(R.id.add_cangku)
    TextView addCangku;
    @Bind(R.id.up_sell)
    TextView upSell;
    @Bind(R.id.iv_cover_add)
    ImageView ivCoverAdd;

    private ImagePicker imagePicker;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ImageSelectAdapter adapter;
    private ApplyPresenter presenter;
    private int maxImgCount = 9;
    private ArrayList<ImageItem> defaultDataArray = new ArrayList<>();
    private ArrayList<ImageItem> coverDataArray = new ArrayList<>();
    private boolean isCover = true;     //ture为封面，false为多选图片
    private CategoryDialog dialog;
    String s = "";
    private String sec_id = "";
    private List<PostTypeInfo> list = new ArrayList<>();
    private String type;
    private String store_id;
    private List<File> fileList = new ArrayList<>();
    private File mFile;

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new ApplyPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    private void initImagePicker() {
        //是否按矩形区域保存
        imagePicker = ImagePicker.getInstance();
        imagePicker.clear();
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        //保存文件的高度。单位像素
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews(View view) {
        tvMainTitle.setText("发布商品");
        type = getIntent().getStringExtra("type");
        store_id = getIntent().getStringExtra("id");
//        name = getIntent().getStringExtra("name");
        edtStorePrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        llCategory.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        addCangku.setOnClickListener(this);
        upSell.setOnClickListener(this);
        initImagePicker();
        presenter.loadCatList(0);
        initAdapter();
    }


    private void initAdapter() {

        adapter = new ImageSelectAdapter(this, defaultDataArray, maxImgCount);
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new GridLayoutManager(this, 4));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        isCover = false;
        imagePicker.setMultiMode(true);
        switch (position) {
            case Constants.IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - defaultDataArray.size());
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);

                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_category:
                if (dialog == null) {
                    dialog = new CategoryDialog(PublishStoreActivity.this, R.style.PostDialogTheme, list);
                }
                dialog.setTitle("请选择经营品类");
                dialog.setListener(new CategoryDialog.PostClickListener() {
                    @Override
                    public void closeClick() {
                        dialog.dismiss();
                    }

                    @Override
                    public void itemUnClick() {
                        postNewsCategory.setText("请选择经营品类");
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
                        postNewsCategory.setText(s);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.add_cangku:    //加入仓库
                commit("warehouse");
                break;
            case R.id.up_sell: //上架销售
                commit("shelves");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.iv_cover_add)
    public void coverClick() {
        isCover = true;
        imagePicker.setFocusWidth(1240);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(780);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(888);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(512);
        imagePicker.setMultiMode(false);
        if (!ListUtils.listIsNullOrEmpty(coverDataArray)) {  //预览

            Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
            intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, coverDataArray);
            intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);

            startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
        } else {    //选择图片
            imagePicker.setSelectLimit(1);
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SELECT);
        }
    }

    private void commit(String s) {
        if (mFile == null) {
            Toast.makeText(this, "请上传封面", Toast.LENGTH_SHORT).show();
            return;
        }
        if(fileList.size() == 0){
            Toast.makeText(this, "请上传照片", Toast.LENGTH_SHORT).show();
            return;
        }
        if(sec_id.equals("")){
            ToastTools.showShort(this,"请选择速拍类别");
            return;
        }
        if(postTitle.getText().toString().trim().equals("")){
            ToastTools.showShort(this,"请输入拍品名称");
            return;
        }
        if(edtStorePrice.getText().toString().trim().equals("")){
            ToastTools.showShort(this,"请输入拍品价格");
            return;
        }
        if(edtStoreLeft.getText().toString().trim().equals("")){
            ToastTools.showShort(this,"请输入库存");
            return;
        }
        if(s.equals("warehouse")){
            showLoading("加入仓库中");
        }else{
            showLoading("正在上架销售");
        }
        FastStoreInfo info = new FastStoreInfo();
        info.setName(postTitle.getText().toString());
        info.setCat_id(sec_id);
        info.setCover(mFile);
        info.setStore_id(store_id);
        info.setImages(fileList);
        info.setGoods_type("supai");
        info.setStore(edtStoreLeft.getText().toString());
        info.setPrice(edtStorePrice.getText().toString());
        info.setIntro(postContent.getText().toString());
        info.setWarehouseOrshelves(s);
        Intent intent = new Intent(PublishStoreActivity.this, PublishStoreService.class);
        intent.putExtra("type",type);
        intent.putExtra("data", info);
        startService(intent);
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                hideLoading();
                PublishStoreActivity.this.finish();
            }
        };
        timer.start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回

            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (isCover == false) {
                    defaultDataArray.addAll(images);
                } else {
                    coverDataArray.addAll(images);
                }
                if (isCover == false) { // 多选的
                    adapter.setImages(defaultDataArray);
                    List<File> files = new ArrayList<>();
                    fileList.clear();
                    for (int i = 0; i < defaultDataArray.size(); i++) {
                        files.add(new File(defaultDataArray.get(i).path));
                    }
                    compress(files);
                    adapter.notifyDataSetChanged();
                } else { // 手持身份证的
                    if (coverDataArray.size() != 0) {
                        imagePicker.getImageLoader().displayImage(PublishStoreActivity.this, coverDataArray.get(0).path, ivCoverAdd, 0, 0);
                        File mfile = new File(coverDataArray.get(0).path);
                        //                        DialogTools.showLoding(this, "温馨提示", "获取中。。。");
                        Luban.compress(this, mfile).putGear(Luban.THIRD_GEAR).launch(new OnCompressListener() {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(File file) {
                                mFile = file;
                                //                                        DialogTools.dissmiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                //                                        DialogTools.dissmiss();
                            }
                        });
                    } else {
                        mFile = null;
                        ivCoverAdd.setImageResource(R.mipmap.publish_store_coveradd);
                    }
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (isCover == false) {
                    defaultDataArray.clear();
                    defaultDataArray.addAll(images);
                } else {
                    coverDataArray.clear();
                    coverDataArray.addAll(images);
                }
                if (isCover == false) {
                    adapter.setImages(defaultDataArray);
                    adapter.notifyDataSetChanged();
                    fileList.clear();
                    List<File> files = new ArrayList<>();
                    for (int i = 0; i < defaultDataArray.size(); i++) {
                        files.add(new File(defaultDataArray.get(i).path));
                    }
                    compress(files);
                } else {
                    if (coverDataArray.size() != 0) {
                        imagePicker.getImageLoader().displayImage(PublishStoreActivity.this, coverDataArray.get(0).path, ivCoverAdd, 0, 0);
                        File mfile = new File(coverDataArray.get(0).path);
                        //                        DialogTools.showLoding(this, "温馨提示", "获取中。。。");
                        Luban.compress(this, mfile).putGear(Luban.THIRD_GEAR).launch(new OnCompressListener() {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(File file) {
                                mFile = file;
                                //                                        DialogTools.dissmiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                //                                        DialogTools.dissmiss();
                            }
                        });
                    } else {
                        mFile = null;
                        ivCoverAdd.setImageResource(R.mipmap.publish_store_coveradd);
                    }
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
                    PublishStoreActivity.this.fileList.addAll(fileList);
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }
    }

    @Override
    public void applyError(String message) {

    }

    @Override
    public void load(AddresBean regionInfo) {

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
