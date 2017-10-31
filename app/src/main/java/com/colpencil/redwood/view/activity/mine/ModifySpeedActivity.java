package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CatBean;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.CoverSpecialItem;
import com.colpencil.redwood.bean.FastStoreInfo;
import com.colpencil.redwood.bean.Info.ApplyGoodInfo;
import com.colpencil.redwood.bean.PostTypeInfo;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.PostDialog;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;
import me.shaohui.advancedluban.OnMultiCompressListener;



/**
 * 修改速拍商品
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_publish_fast)
public class ModifySpeedActivity extends ColpencilActivity implements IModifyGoodsView, View.OnClickListener, ImageSelectAdapter.OnRecyclerViewItemClickListener {
    @Bind(R.id.layout_add)
    LinearLayout layout_add;
    @Bind(R.id.layout_sub)
    LinearLayout layout_sub;
    @Bind(R.id.tv_main_title)
    TextView tvMainTitle;
    @Bind(R.id.iv_cover_add)
    ImageView ivCoverAdd;
    @Bind(R.id.detail_recycler)
    RecyclerView detailRecycler;
    @Bind(R.id.post_title)
    EditText postTitle;
    @Bind(R.id.edt_store_price)
    EditText edtStorePrice;
    @Bind(R.id.edt_store_left)
    EditText edtStoreLeft;
    @Bind(R.id.post_content)
    EditText postContent;
    @Bind(R.id.post_news_category)
    TextView tvcate;
    @Bind(R.id.ll_category)
    LinearLayout llCategory;
    private ModifyGoodPresenter presenter;
    private int goodid;
    private PostDialog dialog;
    private String array = "";
    private boolean isCover = true;
    List<Integer> ll = new ArrayList<>();
    private List<PostTypeInfo> list = new ArrayList<>();
    private ArrayList<ImageItem> defaultDataArray = new ArrayList<>();
    private ArrayList<ImageItem> coverDataArray = new ArrayList<>();
    private int maxImgCount = 9;
    private List<File> fileList = new ArrayList<>();
    private ImagePicker imagePicker;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ImageSelectAdapter adapter;
    private String cover;
    private List<String> filse = new ArrayList<>();
    private File mFile;
    private ApplyGoodInfo info;
    private String sec_id = "";
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
                    adapter = new ImageSelectAdapter(ModifySpeedActivity.this, defaultDataArray, maxImgCount);
                    adapter.setOnItemClickListener(ModifySpeedActivity.this);
                    detailRecycler.setLayoutManager(new GridLayoutManager(ModifySpeedActivity.this, 4));
                    detailRecycler.setHasFixedSize(true);
                    detailRecycler.setAdapter(adapter);
                    break;
                case 1:
                    cover = (String) msg.obj;
                    coverDataArray.clear();
                    ImageItem item = new ImageItem();
                    item.path = cover;
                    coverDataArray.add(item);
                    mFile = new File(coverDataArray.get(0).path);
                    hideLoading();
//                    Bitmap bitmap = BitmapFactory.decodeFile(coverDataArray.get(0).path);
//                    ivCoverAdd.setImageBitmap(bitmap);
//                    imagePicker.clear();
                   imagePicker.getImageLoader().displayImage(ModifySpeedActivity.this, coverDataArray.get(0).path, ivCoverAdd, 0, 0);
                    break;
            }
        }
    };

    @Override
    protected void initViews(View view) {
        tvMainTitle.setText("编辑商品");
        layout_add.setVisibility(View.GONE);
        layout_sub.setVisibility(View.VISIBLE);
        llCategory.setOnClickListener(this);
        edtStorePrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        goodid = getIntent().getExtras().getInt("goodid");
        presenter.getGoodInfo(goodid);
        showLoading("");
        initImagePicker();

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
    public void bindView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @Override
    public void loadGood(ResultInfo<ApplyGoodInfo> result) {
        info = result.getData();
        postTitle.setText(info.getName());
        edtStorePrice.setText(info.getPrice() + "");
        edtStoreLeft.setText(info.getStore() + "");
        postContent.setText(info.getIntro());
        tvcate.setText(info.getCat_name());
        sec_id = info.getCat_id() + "";
        List<String> str = new ArrayList<>();

        for (int i = 0; i < info.getImagelist().size(); i++) {
            str.add(info.getImagelist().get(i).getThumbnail());
            ll.add(info.getImagelist().get(i).getId());
        }
        Map<String, String> map = new HashMap<>();
        map.put("store_id", info.getStore_id() + "");
        presenter.loadPro(map);
        getImges(str, ll);
        getCover(info.getCover());
        presenter.loadCatList(0);
    }

    @Override
    public void loadZcList(ResultInfo<List<CoverSpecialItem>> resultInfo) {

    }

    @Override
    public void loadError(String msg) {

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

    public void getCover(final String coverurl) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(coverurl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    File parent = Environment.getExternalStorageDirectory();
                    String s = System.currentTimeMillis()+"";
                    File file = new File(parent, s);
                    if (file.exists()) {
                        file.delete();
                    }
                    file = new File(parent,s);
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
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = filePath;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
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
            intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
            startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
        } else {    //选择图片
            imagePicker.setSelectLimit(1);
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SELECT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_category:
                if (dialog == null) {
                    dialog = new PostDialog(ModifySpeedActivity.this, R.style.PostDialogTheme, list);
                }
                dialog.setTitle("请选择经营品类");
                dialog.setListener(new PostDialog.PostClickListener() {

                    @Override
                    public void closeClick() {
                        dialog.dismiss();
                    }

                    @Override
                    public void itemUnClick() {
                        tvcate.setText("请选择经营品类");
                        sec_id = "";
                    }

                    @Override
                    public void itemClick(int position) {

                        tvcate.setText(list.get(position).getTypename());
                        sec_id = list.get(position).getSec_id();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
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
    public void onItemClick(View view, int position) {
        isCover = false;
        imagePicker.setMultiMode(true);
        switch (position) {
            case Constants.IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - defaultDataArray.size());
                Intent intent = new Intent(ModifySpeedActivity.this, ImageGridActivity.class);
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
                        imagePicker.getImageLoader().displayImage(ModifySpeedActivity.this, coverDataArray.get(0).path, ivCoverAdd, 0, 0);
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
                        imagePicker.getImageLoader().displayImage(ModifySpeedActivity.this, coverDataArray.get(0).path, ivCoverAdd, 0, 0);
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
                    ModifySpeedActivity.this.fileList.addAll(fileList);
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }
    }

    @OnClick(R.id.btn_addtocar)
    void submit() {
        if (mFile == null) {
            Toast.makeText(this, "请上传封面", Toast.LENGTH_SHORT).show();
            return;
        }
        if (fileList.size() == 0) {
            Toast.makeText(this, "请上传照片", Toast.LENGTH_SHORT).show();
            return;
        }
        if (sec_id.equals("")) {
            ToastTools.showShort(this, "请选择速拍类别");
            return;
        }
        if (postTitle.getText().toString().trim().equals("")) {
            ToastTools.showShort(this, "请输入拍品名称");
            return;
        }
        if (edtStorePrice.getText().toString().trim().equals("")) {
            ToastTools.showShort(this, "请输入拍品价格");
            return;
        }
        if (edtStoreLeft.getText().toString().trim().equals("")) {
            ToastTools.showShort(this, "请输入库存");
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
        showLoading("保存中");
        FastStoreInfo storeInfo = new FastStoreInfo();
        storeInfo.setGoods_id(info.getGoods_id());
        storeInfo.setCat_id(sec_id);
        storeInfo.setName(postTitle.getText().toString());
        storeInfo.setPrice(edtStorePrice.getText().toString());
        storeInfo.setStore(edtStoreLeft.getText().toString());
        storeInfo.setIntro(postContent.getText().toString());
        storeInfo.setCover(mFile);
        storeInfo.setImage(fileList);
        storeInfo.setArray_id(array);
        Intent intent = new Intent(ModifySpeedActivity.this, ModifiyStoreService.class);
        intent.putExtra("data", storeInfo);
        startService(intent);
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                hideLoading();
                ModifySpeedActivity.this.finish();
            }
        };
        timer.start();
    }

    @Override
    public void loadPro(ResultInfo<String> result) {
        if (result.getCode() == 0) {
            String str;
            str = "顶藏将在此价格上提取" + result.getData() + "的佣金";
            edtStorePrice.setHint(str);
        }
    }

}
