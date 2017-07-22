package com.colpencil.redwood.view.activity.cyclopedia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.CyclopediaInfo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.PostTypeInfo;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.PostDialog;
import com.colpencil.redwood.present.home.PostNewsPresenter;
import com.colpencil.redwood.services.CyclopediaService;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.MineCycloActivity;
import com.colpencil.redwood.view.activity.mine.WebViewActivity;
import com.colpencil.redwood.view.adapters.ImageSelectAdapter;
import com.colpencil.redwood.view.impl.IPostNewsView;
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
import butterknife.OnClick;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;
import me.shaohui.advancedluban.OnMultiCompressListener;

/**
 * @author 陈宝
 * @Description:发布百科
 * @Email DramaScript@outlook.com
 * @date 2016/8/22
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_post_cyclopedia)
public class PostCyclopediaActivity extends ColpencilActivity
        implements IPostNewsView, ImageSelectAdapter.OnRecyclerViewItemClickListener {

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.iv_add_cover)
    ImageView iv_cover;
    @Bind(R.id.post_news_category)
    TextView tv_category;
    @Bind(R.id.post_title)
    EditText et_title;
    @Bind(R.id.post_content)
    EditText et_content;
    @Bind(R.id.detail_recycler)
    RecyclerView recycler;
    @Bind(R.id.read_rule)
    TextView tv_rule;

    private List<PostTypeInfo> list = new ArrayList<>();
    private PostNewsPresenter presenter;
    private ImageSelectAdapter adapter;
    private ArrayList<ImageItem> defaultDataArray = new ArrayList<>();
    private ArrayList<ImageItem> coverDataArray = new ArrayList<>();
    private boolean isCover = true;     //ture为封面，false为多选图片
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private PostDialog dialog;
    private String sec_id;
    private int maxImgCount = 9;               //允许选择图片最大数
    private ImagePicker imagePicker;
    private List<File> fileList;
    private int game = 0;
    private File mFile;

    @Override
    protected void initViews(View view) {
        initContent();
        initImagePicker();
        initAdapter();
        initData();
    }

    private void initContent() {
        tv_title.setText("发布百科");
        tv_rule.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        game = getIntent().getIntExtra("game", 0);
    }

    private void initImagePicker() {
        imagePicker = App.getInstance().getImagePicker();
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
    }

    private void initAdapter() {
        adapter = new ImageSelectAdapter(this, defaultDataArray, maxImgCount);
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new GridLayoutManager(this, 4));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
    }

    private void initData() {
        presenter.loadTag(2);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new PostNewsPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void submitResult(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 3) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(StringConfig.REQUEST_CODE, Constants.REQUEST_LOGIN);
            startActivityForResult(intent, Constants.REQUEST_LOGIN);
        } else if (result.getCode() == 0) {
            Intent mIntent = new Intent(this, MineCycloActivity.class);
            mIntent.putExtra("type", "cyclopedia");
            startActivity(mIntent);
            finish();
        }
        ToastTools.showShort(this, result.getMessage());
    }

    @Override
    public void loadTag(List<CategoryVo> categoryVos) {
        if (!ListUtils.listIsNullOrEmpty(categoryVos)) {
            list.clear();
            for (CategoryVo vo : categoryVos) {
                PostTypeInfo info = new PostTypeInfo();
                info.setTypename(vo.getCat_name());
                info.setSec_id(vo.getCat_id());
                info.setChoose(false);
                list.add(info);
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        isCover = false;
        switch (position) {
            case Constants.IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                imagePicker.setSelectLimit(maxImgCount - defaultDataArray.size());
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, defaultDataArray);
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (isCover == false) {
                    defaultDataArray.addAll(images);
                } else {
                    coverDataArray.addAll(images);
                }
                if (isCover == false) { // 多选的
                    adapter.setImages(defaultDataArray);
                    adapter.notifyDataSetChanged();
                    fileList = new ArrayList<>();
                    List<File> files = new ArrayList<>();
                    for (int i = 0; i < defaultDataArray.size(); i++) {
                        files.add(new File(defaultDataArray.get(i).path));
                    }
                    compress(files);
                } else { // 手持身份证的
                    if (coverDataArray.size() != 0) {
                        imagePicker.getImageLoader().displayImage(PostCyclopediaActivity.this,coverDataArray.get(0).path,iv_cover,0,0);
                        File mfile = new File(coverDataArray.get(0).path);
                        //                        DialogTools.showLoding(this, "温馨提示", "获取中。。。");
                        Luban.compress(this, mfile)
                                .putGear(Luban.THIRD_GEAR)
                                .launch(new OnCompressListener() {
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
                        iv_cover.setImageResource(R.mipmap.add_photo);
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
                    fileList = new ArrayList<>();
                    List<File> files = new ArrayList<>();
                    for (int i = 0; i < defaultDataArray.size(); i++) {
                        files.add(new File(defaultDataArray.get(i).path));
                    }
                    compress(files);
                } else {
                    if (coverDataArray.size() != 0) {
                        imagePicker.getImageLoader().displayImage(PostCyclopediaActivity.this,coverDataArray.get(0).path,iv_cover,0,0);
                        File mfile = new File(coverDataArray.get(0).path);
                        //                        DialogTools.showLoding(this, "温馨提示", "获取中。。。");
                        Luban.compress(this, mfile)
                                .putGear(Luban.THIRD_GEAR)
                                .launch(new OnCompressListener() {
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
                        iv_cover.setImageResource(R.mipmap.add_photo);
                    }
                }
            }
        } else if (resultCode == Constants.REQUEST_LOGIN) {
            postOnClick();
        }

    }

    public void compress(List<File> list) {
        if (list.size() > 0) {
            //            DialogTools.showLoding(this, "温馨提示", "获取中。。。");
            Luban.compress(this, list)
                    .putGear(Luban.THIRD_GEAR)
                    .launch(new OnMultiCompressListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(List<File> fileList) {
                            //                            DialogTools.dissmiss();
                            PostCyclopediaActivity.this.fileList.addAll(fileList);
                        }

                        @Override
                        public void onError(Throwable e) {
                            //                            DialogTools.dissmiss();
                        }
                    });
        }
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    @OnClick(R.id.iv_add_cover)
    void coverClick() {
        isCover = true;
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

    @OnClick(R.id.btn_post)
    void postOnClick() {
        String title = et_title.getText().toString();
        String content = et_content.getText().toString();
        if (TextUtils.isEmpty(sec_id)) {
            ToastTools.showShort(PostCyclopediaActivity.this, "请先选择分类");
            return;
        } else if (TextUtils.isEmpty(title)) {
            ToastTools.showShort(PostCyclopediaActivity.this, "请输入标题");
            return;
        } else if (title.length() > 20) {
            ToastTools.showShort(PostCyclopediaActivity.this, "标题不能超过20个字");
            return;
        } else if (TextUtils.isEmpty(content)) {
            ToastTools.showShort(PostCyclopediaActivity.this, "请输入内容");
            return;
        } else if (ListUtils.listIsNullOrEmpty(coverDataArray)) {
            ToastTools.showShort(PostCyclopediaActivity.this, "封面图片不能为空");
            return;
        } else {
            CyclopediaInfo info = new CyclopediaInfo();
            info.setTitle(title);
            info.setContent(content);
            info.setSec_id(sec_id);
            info.setCoverFile(mFile);
            info.setType(3);
            info.setFileList(fileList);
            info.setGame(game);
            Intent intent = new Intent(PostCyclopediaActivity.this, CyclopediaService.class);
            intent.putExtra("data", info);
            startService(intent);
            showLoading("正在发布");
            CountDownTimer timer = new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    hideLoading();
                    Intent mIntent = new Intent(PostCyclopediaActivity.this, MineCycloActivity.class);
                    mIntent.putExtra("type", "cyclopedia");
                    startActivity(mIntent);
                    finish();
                }
            };
            timer.start();
        }
    }

    @OnClick(R.id.post_news_category)
    void categoryClick() {
        if (dialog == null) {
            dialog = new PostDialog(PostCyclopediaActivity.this, R.style.PostDialogTheme, list);
        }
        dialog.setTitle("请选百科类型");
        dialog.setListener(new PostDialog.PostClickListener() {

            @Override
            public void closeClick() {
                dialog.dismiss();
            }

            @Override
            public void itemUnClick() {
                tv_category.setText("请选择百科类别");
                sec_id = "";
            }

            @Override
            public void itemClick(int position) {
                tv_category.setText(list.get(position).getTypename());
                sec_id = list.get(position).getSec_id();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.read_rule)
    void ruleClick() {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("key", "rule");
        startActivity(intent);
    }

}