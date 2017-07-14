package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.tools.DialogTools;
import com.colpencil.redwood.present.mine.EvaluationPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.ImageSelectAdapter;
import com.colpencil.redwood.view.impl.IEvaluationView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;

/**
 * 描述：进行评价
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 16 57
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_evaluation
)
public class EvaluationActivity extends ColpencilActivity
        implements IEvaluationView, ImageSelectAdapter.OnRecyclerViewItemClickListener {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;

    @Bind(R.id.evaluation_goodName)
    TextView evaluation_goodName;

    @Bind(R.id.tv_shoppingCartFinish)
    TextView tv_shoppingCartFinish;

    @Bind(R.id.evaluation_gridView)
    RecyclerView gridView;

    @Bind(R.id.et_evaluation)
    EditText et_content;

    private EvaluationPresenter presenter;

    private ImageSelectAdapter adapter;
    private ArrayList<ImageItem> defaultDataArray = new ArrayList<>();
    private int order_id;
    private String goods_id;
    private int maxImgCount = 4;               //允许选择图片最大数
    private ImagePicker imagePicker;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private List<File> fileList = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        order_id = getIntent().getIntExtra("order_id", 0);
        goods_id = getIntent().getIntExtra("goods_id", 0) + "";
        initImagePicker();
        initData();
    }

    private void initImagePicker() {
        imagePicker = App.getInstance().getImagePicker();
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
    }

    /**
     * 数据初始化
     */
    private void initData() {
        tv_main_title.setText("评价");
        tv_shoppingCartFinish.setText("提交");
        evaluation_goodName.setText("商品:" + getIntent().getStringExtra("goodName"));
        adapter = new ImageSelectAdapter(this, defaultDataArray, maxImgCount);
        adapter.setOnItemClickListener(this);
        gridView.setLayoutManager(new GridLayoutManager(this, 4));
        gridView.setHasFixedSize(true);
        gridView.setAdapter(adapter);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new EvaluationPresenter();
        return presenter;
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
        }
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @OnClick(R.id.iv_back)
    void backOnClick() {
        finish();
    }

    @OnClick(R.id.tv_shoppingCartFinish)
    void rightOnClick() {
        String content = et_content.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastTools.showShort(this, "评论内容不能为空...");
            return;
        } else if (content.length() > 100) {
            ToastTools.showShort(this, "评论内容不能超过100字...");
            return;
        } else {
            showLoading(Constants.progressName);
            presenter.submitComment(order_id, goods_id, content, fileList, 0);
        }
    }

    @Override
    public void submitResult(ResultCodeInt result) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), result.getMsg());
        if (result.getCode() == 1) {//评价成功
            RxBusMsg rxBusMsg = new RxBusMsg();
            rxBusMsg.setType(35);
            RxBus.get().post("rxBusMsg", rxBusMsg);
        } else if (result.getCode() == 3) {//用户未登录
            Intent intent = new Intent(EvaluationActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }

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

    public void compress(List<File> list) {
        if (list.size() > 0) {
            DialogTools.showLoding(this, "温馨提示", "获取中。。。");
            Luban.compress(this,list)
                    .putGear(Luban.THIRD_GEAR)
                    .launch(new OnMultiCompressListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(List<File> fileList) {
                            DialogTools.dissmiss();
                            EvaluationActivity.this.fileList.addAll(fileList);
                        }

                        @Override
                        public void onError(Throwable e) {
                            DialogTools.dissmiss();
                        }
                    });
        }
    }
}
