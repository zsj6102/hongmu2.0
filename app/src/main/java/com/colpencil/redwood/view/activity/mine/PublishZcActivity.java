package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CatBean;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.CoverSpecialItem;
import com.colpencil.redwood.bean.FastStoreInfo;
import com.colpencil.redwood.bean.PostTypeInfo;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.SizeColorInfo;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.Compressor;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.PostDialog;
import com.colpencil.redwood.function.widgets.dialogs.ZcDialog;
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
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;
import rx.Observable;
import rx.Subscriber;

/**
 * 发布商品专场
 */
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
    @Bind(R.id.ll_zc_content)
    LinearLayout llzcLayout;
    @Bind(R.id.zc_tv)
    TextView tv_zc;
    @Bind(R.id.line_view)
    View line;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    private ImageSelectAdapter adapter;
    private PublishPresenter presenter;
    private ArrayList<ImageItem> defaultDataArray = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();
    private ImagePicker imagePicker;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private int maxImgCount = 9;               //
    private PostDialog dialog;
    private ZcDialog dialog2;
    private String store_id;
    private String sec_id = "";
    private String zc_id = "";
    private List<PostTypeInfo> list = new ArrayList<>();
    private List<CoverSpecialItem> zcList = new ArrayList<>();
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;

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
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    protected void initViews(View view) {
        store_id = getIntent().getStringExtra("id");
        tvMainTitle.setText("发布商品");
        SharedPreferencesUtil.getInstance(App.getInstance()).setString("good", "");
        tvSubmitPublish.setVisibility(View.VISIBLE);
        presenter.loadCatList(0);
        presenter.loadSize(0);
        edtStorePrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        edtMaketPrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        Map<String, String> map = new HashMap<>();
        map.put("store_id", store_id + "");
        presenter.loadPro(map);
        llzcLayout.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
        initAdapter();
        initImagePicker();
        initBus();
    }

    private void initBus() {
        observable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                if(rxBusMsg.getType() == 100){
                    if(SharedPreferencesUtil.getInstance(App.getInstance()).getString("good") != ""){
                        tvDescription.setText("已添加");
                    }else{
                        tvDescription.setText("未添加");
                    }

                }
            }
        };
        observable.subscribe(subscriber);
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

    @Override
    public void loadZcList(ResultInfo<List<CoverSpecialItem>> resultInfo) {
        if (resultInfo.getCode() == 0) {
            zcList.clear();
            zcList.addAll(resultInfo.getData());
        }

    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @OnClick(R.id.ll_zc_content)
    void zcClick() {
        if (sec_id.equals("")) {
            ToastTools.showShort(this, "请先选择商品分类");
            return;
        }

        if (dialog2 == null) {
            dialog2 = new ZcDialog(PublishZcActivity.this, R.style.PostDialogTheme, zcList);
        }
        dialog2.setTitle("请选择专场名称");
        dialog2.setListener(new ZcDialog.PostClickListener() {

            @Override
            public void closeClick() {
                dialog2.dismiss();
            }

            @Override
            public void itemUnClick() {
                tv_zc.setText("请选择专场名称");
                zc_id = "";
            }

            @Override
            public void itemClick(int position) {

                tv_zc.setText(zcList.get(position).getSpecial_name());
                zc_id = zcList.get(position).getSpecial_id() + "";
                dialog2.dismiss();
            }
        });
        dialog2.show();
    }

    @OnClick(R.id.ll_category)
    void category() {
        if (dialog == null) {
            dialog = new PostDialog(PublishZcActivity.this, R.style.PostDialogTheme, list);
        }
        dialog.setTitle("请选择商品分类");
        dialog.setListener(new PostDialog.PostClickListener() {

            @Override
            public void closeClick() {
                dialog.dismiss();
            }

            @Override
            public void itemUnClick() {
                postNewsCategory.setText("请选择商品分类");
                sec_id = "";
            }

            @Override
            public void itemClick(int position) {

                postNewsCategory.setText(list.get(position).getTypename());
                sec_id = list.get(position).getSec_id();
                tv_zc.setText("请选择专场名称");
                zc_id = "";
                presenter.loadZcList(Integer.parseInt(sec_id));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.add_cangku)
    void submit() {
        if (fileList.size() == 0) {
            Toast.makeText(this, "请上传照片", Toast.LENGTH_SHORT).show();
            return;
        }
        if (sec_id.equals("")) {
            ToastTools.showShort(this, "请选择商品分类");
            return;
        }
        if (postTitle.getText().toString().trim().equals("")) {
            ToastTools.showShort(this, "请输入商品名称");
            return;
        }
        if (edtStorePrice.getText().toString().trim().equals("")) {
            ToastTools.showShort(this, "请输入销售价");
            return;
        }
        if (edtMaketPrice.getText().toString().trim().equals("")) {
            ToastTools.showShort(this, "请输入市场价");
            return;
        }
        if (edtStoreLeft.getText().toString().trim().equals("")) {
            ToastTools.showShort(this, "请输入库存");
            return;
        }
        if (ListUtils.listIsNullOrEmpty(zcList)) {
            ToastTools.showShort(this, "获取专场失败，无法发布");
            return;
        }
        if (zc_id.equals("")) {
            ToastTools.showShort(this, "请选择专场名称");
            return;
        }
        showLoading("提交中...");

        FastStoreInfo info = new FastStoreInfo();
        info.setCat_id(sec_id);      //商品分类id
        info.setImages(fileList);     //图片
        info.setGoods_type("zhuanchang");  //Goodstype
        info.setWarehouseOrshelves("shelves");  //直接提交没有加入仓库
        info.setMktprice(edtMaketPrice.getText().toString());  //市场价
        info.setPrice(edtStorePrice.getText().toString());   //销售价
        info.setStore(edtStoreLeft.getText().toString());    //库存
        info.setName(postTitle.getText().toString());       //名称
        if(!SharedPreferencesUtil.getInstance(App.getInstance()).getString("good").equals("")){
            info.setIntro(SharedPreferencesUtil.getInstance(App.getInstance()).getString("good"));
        }
        info.setStore_id(store_id);
        info.setSpe_section_id(zc_id);
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

    @OnClick(R.id.layout_add_subcribe)
    void add() {
        Intent intent = new Intent(PublishZcActivity.this, GoodNoteActivity.class);
        startActivity(intent);
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

    @Override
    public void loadPro(ResultInfo<String> result) {
        if (result.getCode() == 0) {
            String str;
            str = "顶藏将在此价格上提取" + result.getData() + "的佣金";
            edtStorePrice.setHint(str);
        }
    }

    public void compress(List<File> list) {
        if (list.size() > 0) {
//            Luban.compress(this, list).putGear(Luban.THIRD_GEAR).launch(new OnMultiCompressListener() {
//                @Override
//                public void onStart() {
//                }
//
//                @Override
//                public void onSuccess(List<File> fileList) {
//                    PublishZcActivity.this.fileList.addAll(fileList);
//                }
//
//                @Override
//                public void onError(Throwable e) {
//
//                }
//            });
            for(int i = 0; i < list.size();i++){
                try{
                    PublishZcActivity.this.fileList.add(new Compressor(this).compressToFile(list.get(i), "avatar" + i + ".jpg"));
                }catch (Exception e){
                    e.printStackTrace();
                }
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
