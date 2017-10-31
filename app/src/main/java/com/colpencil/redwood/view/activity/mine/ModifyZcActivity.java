package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import com.colpencil.redwood.bean.Info.ApplyGoodInfo;
import com.colpencil.redwood.bean.PostTypeInfo;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.Compressor;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.PostDialog;
import com.colpencil.redwood.function.widgets.dialogs.ZcDialog;
import com.colpencil.redwood.present.mine.ModifyGoodPresenter;
import com.colpencil.redwood.services.ModifiyStoreService;
import com.colpencil.redwood.view.adapters.ImageSelectAdapter;
import com.colpencil.redwood.view.impl.IModifyGoodsView;
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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;
import rx.Observable;
import rx.Subscriber;


/**
 * 修改专场商品
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_publish_zc)
public class ModifyZcActivity extends ColpencilActivity implements IModifyGoodsView, ImageSelectAdapter.OnRecyclerViewItemClickListener {
    @Bind(R.id.layout_add)
    LinearLayout layout_add;
    @Bind(R.id.layout_sub)
    LinearLayout layout_sub;
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
    private ModifyGoodPresenter presenter;
    private int goodid;
    private ApplyGoodInfo info;
    private String sec_id = "";
    private String zc_id = "";
    private PostDialog dialog;
    private ZcDialog dialog2;
    List<Integer> ll = new ArrayList<>();
    private List<String> filse = new ArrayList<>();
    private ArrayList<ImageItem> defaultDataArray = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();
    private ImageSelectAdapter adapter;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private int maxImgCount = 9;
    private List<PostTypeInfo> list = new ArrayList<>();
    private ImagePicker imagePicker;
    private List<CoverSpecialItem> zcList = new ArrayList<>();
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    private String array = "";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    filse = (ArrayList<String>) msg.obj;
                    for (int i = 0; i < filse.size(); i++) {
                        ImageItem item = new ImageItem();
                        item.path = filse.get(i);
                        defaultDataArray.add(item);
                    }
                    fileList.clear();
                    List<File> files = new ArrayList<>();
                    for (int i = 0; i < defaultDataArray.size(); i++) {
                        files.add(new File(defaultDataArray.get(i).path));
                    }
                    compress(files);
                    adapter = new ImageSelectAdapter(ModifyZcActivity.this, defaultDataArray, maxImgCount);
                    adapter.setOnItemClickListener(ModifyZcActivity.this);
                    detailRecycler.setLayoutManager(new GridLayoutManager(ModifyZcActivity.this, 4));
                    detailRecycler.setHasFixedSize(true);
                    detailRecycler.setAdapter(adapter);
                    hideLoading();
                    break;

            }
        }
    };

    public void compress(List<File> list) {
        if (list.size() > 0) {
//            Luban.compress(this, list).putGear(Luban.THIRD_GEAR).launch(new OnMultiCompressListener() {
//                @Override
//                public void onStart() {
//                }
//
//                @Override
//                public void onSuccess(List<File> fileList) {
//                    ModifyZcActivity.this.fileList.addAll(fileList);
//                }
//
//                @Override
//                public void onError(Throwable e) {
//
//                }
//            });
            for(int i = 0; i < list.size();i++){
                try{

                    ModifyZcActivity.this.fileList.add(new Compressor(this).compressToFile(list.get(i), "avatar" + i + ".jpg"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    protected void initViews(View view) {
        tvMainTitle.setText("编辑商品");
        SharedPreferencesUtil.getInstance(App.getInstance()).setString("good", "");
        edtStorePrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        edtMaketPrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        tvSubmitPublish.setVisibility(View.VISIBLE);
        layout_add.setVisibility(View.GONE);
        layout_sub.setVisibility(View.VISIBLE);
        goodid = getIntent().getExtras().getInt("goodid");
        presenter.getGoodInfo(goodid);
        llzcLayout.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
        initImagePicker();
        initBus();
    }
    private void initBus(){
        observable = RxBus.get().register("rxBusMsg",RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>(){
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
    private void initImagePicker() {
        //是否按矩形区域保存
        imagePicker = ImagePicker.getInstance();
        imagePicker.clear();
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        //保存文件的高度。单位像素
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new ModifyGoodPresenter();
        return presenter;
    }

    @Override
    public void onItemClick(View view, int position) {
        imagePicker.setMultiMode(true);
        switch (position) {
            case Constants.IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - defaultDataArray.size());
                Intent intent = new Intent(ModifyZcActivity.this, ImageGridActivity.class);
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
    @OnClick(R.id.layout_add_subcribe)
    void add() {
        Intent intent = new Intent(ModifyZcActivity.this, GoodNoteActivity.class);
        startActivity(intent);
    }
    @Override
    public void loadGood(ResultInfo<ApplyGoodInfo> result) {
        info = result.getData();
        postTitle.setText(info.getName());
        edtStorePrice.setText(info.getPrice() + "");
        edtStoreLeft.setText(info.getStore() + "");
        postNewsCategory.setText(info.getCat_name());
        edtMaketPrice.setText(info.getMktprice() + "");
        tv_zc.setText(info.getSpe_section_name());
        if (info.getIntro() != null) {
            tvDescription.setText("已添加");
            SharedPreferencesUtil.getInstance(App.getInstance()).setString("good", info.getIntro());
        } else {
            SharedPreferencesUtil.getInstance(App.getInstance()).setString("good", "");
            tvDescription.setText("未添加");
        }

        sec_id = info.getCat_id() + "";
        zc_id = info.getSpe_section_id() + "";
        List<String> str = new ArrayList<>();

        for (int i = 0; i < info.getImagelist().size(); i++) {
            str.add(info.getImagelist().get(i).getThumbnail());
            ll.add(info.getImagelist().get(i).getId());
        }
        Map<String, String> map = new HashMap<>();
        map.put("store_id", info.getStore_id() + "");
        presenter.loadPro(map);
        presenter.loadZcList(Integer.parseInt(sec_id));
        getImges(str, ll);

        presenter.loadCatList(0);

    }

    public void getImges(final List<String> urls, final List<Integer> ll) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> files = new ArrayList<>();
                for (int i = 0; i < urls.size(); i++) {
                    try {
                        URL url = new URL(urls.get(i));
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setConnectTimeout(5000);
                        conn.setRequestMethod("GET");
                        File parent = Environment.getExternalStorageDirectory();
                        File file = new File(parent, "item" + ll.get(i));
                        if (file.exists()) {
                            file.delete();
                        }
                        file = new File(parent, "item" + ll.get(i));
                        FileOutputStream fos = new FileOutputStream(file);
                        InputStream in = conn.getInputStream();
                        byte ch[] = new byte[2 * 1024];
                        int len;
                        if (fos != null) {
                            while ((len = in.read(ch)) != -1) {
                                fos.write(ch, 0, len);
                            }
                            in.close();
                            fos.close();
                        }
                        String filePath = file.getAbsolutePath();
                        files.add(filePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Message msg = new Message();
                msg.what = 0;
                msg.obj = files;
                handler.sendMessage(msg);
            }
        }).start();
    }
    @OnClick(R.id.btn_addtocar)
    void submit(){
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
        List<Integer> array_id = new ArrayList<>();

        for (int i = 0; i < fileList.size(); i++) {
            Iterator<String> it = filse.iterator();
            while (it.hasNext()) {
                String filename = it.next();
                if (filename.equals(fileList.get(i).getAbsolutePath())) {
                    it.remove();
                }
            }
        }
        Iterator<File> it = fileList.iterator();
        while (it.hasNext()) {
            File file = it.next();
            if (file.getAbsolutePath().contains("item")) {
                it.remove();
            }
        }

        for (int i = 0; i < filse.size(); i++) {
            for (int k = 0; k < ll.size(); k++) {
                if (filse.get(i).contains("item" + ll.get(k))) {
                    array_id.add(ll.get(k));
                }
            }
        }
        for (int i = 0; i < array_id.size(); i++) {
            if (i != array_id.size() - 1) {
                array = array + array_id.get(i) + ",";
            } else {
                array = array + array_id.get(i);
            }
        }
        showLoading("提交中...");

        FastStoreInfo storeInfo = new FastStoreInfo();
        storeInfo.setCat_id(sec_id);      //商品分类id
        storeInfo.setImage(fileList);     //图片
        storeInfo.setGoods_id(info.getGoods_id());
        storeInfo.setMktprice(edtMaketPrice.getText().toString());  //市场价
        storeInfo.setPrice(edtStorePrice.getText().toString());   //销售价
        storeInfo.setStore(edtStoreLeft.getText().toString());    //库存
        storeInfo.setName(postTitle.getText().toString());       //名称
        storeInfo.setSpe_section_id(zc_id);
        if(!SharedPreferencesUtil.getInstance(App.getInstance()).getString("good").equals("")){
            info.setIntro(SharedPreferencesUtil.getInstance(App.getInstance()).getString("good"));
        }
        storeInfo.setArray_id(array);
        storeInfo.setSpe_section_id(zc_id);
        Intent intent = new Intent(ModifyZcActivity.this, ModifiyStoreService.class);
        intent.putExtra("data", storeInfo);
        startService(intent);
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                hideLoading();
                ModifyZcActivity.this.finish();
            }
        };
        timer.start();
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
            dialog2 = new ZcDialog(ModifyZcActivity.this, R.style.PostDialogTheme, zcList);
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
            dialog = new PostDialog(ModifyZcActivity.this, R.style.PostDialogTheme, list);
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

    @Override
    public void loadError(String msg) {

    }

    @Override
    public void loadCat(CatListBean catListBean) {
        if (!ListUtils.listIsNullOrEmpty(catListBean.getData())) {
            list.clear();
            for (CatBean vo : catListBean.getData()) {
                PostTypeInfo info = new PostTypeInfo();
                info.setTypename(vo.getCat_name());
                info.setSec_id(String.valueOf(vo.getCat_id()));
                if (String.valueOf(vo.getCat_id()).equals(sec_id)) {
                    info.setChoose(true);
                } else {
                    info.setChoose(false);
                }
                list.add(info);
            }
        }
    }

    @Override
    public void loadPro(ResultInfo<String> result) {
        if (result.getCode() == 0) {
            String str;
            str = "顶藏将在此价格上提取" + result.getData() + "的佣金";
            edtStorePrice.setHint(str);
        }
    }

    @Override
    public void loadZcList(ResultInfo<List<CoverSpecialItem>> resultInfo) {
        if (!ListUtils.listIsNullOrEmpty(resultInfo.getData())) {
            zcList.clear();
            for (CoverSpecialItem vo : resultInfo.getData()) {
                if (String.valueOf(vo.getSpecial_id()).equals(zc_id)) {
                    vo.setChoose(true);
                } else {
                    vo.setChoose(false);
                }
                zcList.add(vo);
            }
        }
    }
}
