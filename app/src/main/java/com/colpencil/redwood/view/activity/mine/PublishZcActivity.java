package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CatBean;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.FastStoreInfo;
import com.colpencil.redwood.bean.PostTypeInfo;
import com.colpencil.redwood.bean.SizeColorInfo;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CategoryDialog;
import com.colpencil.redwood.present.mine.PublishPresenter;
import com.colpencil.redwood.services.PublishStoreService;
import com.colpencil.redwood.view.adapters.ImageSelectAdapter;
import com.colpencil.redwood.view.impl.PublishView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;


@ActivityFragmentInject(contentViewId = R.layout.activity_publish_zc)
public class PublishZcActivity extends ColpencilActivity implements ImageSelectAdapter.OnRecyclerViewItemClickListener, PublishView {

    @Bind(R.id.tv_main_title)
    TextView tvMainTitle;
    @Bind(R.id.detail_recycler)
    RecyclerView detailRecycler;
    @Bind(R.id.tv_submit_publish)
    TextView tvSubmitPublish;
    @Bind(R.id.post_news_category)
    TextView postNewsCategory;
    @Bind(R.id.post_title)
    EditText postTitle;
    @Bind(R.id.edt_store_price)
    EditText edtStorePrice;
    @Bind(R.id.edt_maket_price)
    EditText edtMaketPrice;
    @Bind(R.id.edt_store_left)
    EditText edtStoreLeft;
    private ImageSelectAdapter adapter;
    private PublishPresenter presenter;
    private ArrayList<ImageItem> defaultDataArray = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();
    private ImagePicker imagePicker;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private int maxImgCount = 9;               //
    private CategoryDialog dialog;
    String s = "";
    private String sec_id = "";
    private List<PostTypeInfo> list = new ArrayList<>();

    @Override
    public void onItemClick(View view, int position) {
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
    protected void initViews(View view) {
        tvMainTitle.setText("发布商品");
        tvSubmitPublish.setVisibility(View.VISIBLE);
        presenter.loadCatList(0);
        presenter.loadSize(0);
        initAdapter();
        initImagePicker();
    }

    private void initAdapter() {
        adapter = new ImageSelectAdapter(this, defaultDataArray, maxImgCount);
        adapter.setOnItemClickListener(this);
        detailRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        detailRecycler.setHasFixedSize(true);
        detailRecycler.setAdapter(adapter);
    }

    private void initImagePicker() {
        //是否按矩形区域保存
        imagePicker = ImagePicker.getInstance();
        imagePicker.clear();
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        //保存文件的高度。单位像素
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new PublishPresenter();
        return presenter;
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @OnClick(R.id.ll_category)
    void category() {
        if (dialog == null) {
            dialog = new CategoryDialog(PublishZcActivity.this, R.style.PostDialogTheme, list);
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
    }

    @OnClick(R.id.add_cangku)
    void submit() {
        FastStoreInfo info = new FastStoreInfo();
        info.setCat_id(sec_id);      //商品分类id
        info.setImages(fileList);     //图片
        info.setGoods_type("zhuangchang");  //Goodstype
        info.setWarehouseOrshelves("shelves");  //直接提交没有加入仓库
        info.setMktprice(edtMaketPrice.getText().toString());  //市场价
        info.setPrice(edtStorePrice.getText().toString());   //销售价
        info.setStore(edtStoreLeft.getText().toString());    //库存
        info.setName(postTitle.getText().toString());       //名称

        Intent intent = new Intent(PublishZcActivity.this, PublishStoreService.class);
        intent.putExtra("data", info);
        startService(intent);
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                hideLoading();
                PublishZcActivity.this.finish();
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

                defaultDataArray.addAll(images);
                adapter.setImages(defaultDataArray);
                List<File> files = new ArrayList<>();
                fileList.clear();
                for (int i = 0; i < defaultDataArray.size(); i++) {
                    files.add(new File(defaultDataArray.get(i).path));
                }
                compress(files);
                adapter.notifyDataSetChanged();

            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);

                defaultDataArray.clear();
                defaultDataArray.addAll(images);


                adapter.setImages(defaultDataArray);
                adapter.notifyDataSetChanged();
                fileList.clear();
                List<File> files = new ArrayList<>();
                for (int i = 0; i < defaultDataArray.size(); i++) {
                    files.add(new File(defaultDataArray.get(i).path));
                }
                compress(files);

            }
        }
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    public void compress(List<File> list) {
        if (list.size() > 0) {
            Luban.compress(this, list).putGear(Luban.THIRD_GEAR).launch(new OnMultiCompressListener() {
                @Override
                public void onStart() {
                }

                @Override
                public void onSuccess(List<File> fileList) {
                    PublishZcActivity.this.fileList.addAll(fileList);
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void applyError(String message) {

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
    public void loadSize(SizeColorInfo info) {

    }
}
