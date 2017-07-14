package com.colpencil.redwood.view.activity.discovery;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.tools.DialogTools;
import com.colpencil.redwood.function.tools.MyImageLoader;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.CustomActionPresenter;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.activity.home.MyWebViewActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.MyCustomActivity;
import com.colpencil.redwood.view.adapters.ImageSelectAdapter;
import com.colpencil.redwood.view.adapters.NullAdapter;
import com.colpencil.redwood.view.impl.ICustomActionView;
import com.jaeger.library.StatusBarUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.BaseListView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;

/**
 * @author 曾 凤
 * @Description: 定制相关信息填写
 * @Email 20000263@qq.com
 * @date 2016/7/8
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_customaction
)
public class CustomActionActivity extends ColpencilActivity
        implements ICustomActionView, ImageSelectAdapter.OnRecyclerViewItemClickListener {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;

    @Bind(R.id.listview_customaction)
    BaseListView listview_customaction;
    @Bind(R.id.tv_shoppingCartFinish)
    TextView tv_right;

    private CustomActionPresenter presenter;
    private HeaderViewHolder holder;

    private View headerView;
    private Banner banner;
    private ImageSelectAdapter adapter;
    private ArrayList<ImageItem> defaultDataArray = new ArrayList<>();
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private List<BannerVo> bannerVoList = new ArrayList<>();
    private ImagePicker imagePicker;
    private int maxImgCount = 9;               //允许选择图片最大数
    private CommonDialog dialog;
    private List<File> fileList = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        tv_right.setText("最新官方定制");
        tv_right.setTextColor(getResources().getColor(R.color.text_color_first));
        initHeader();
        initImagePicker();
        initGridView();
        initData();
    }

    private void initHeader() {
        headerView = LayoutInflater.from(this).inflate(R.layout.banner_header, null);
        banner = (Banner) headerView.findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);//指示器模式
        banner.setIndicatorGravity(BannerConfig.CENTER);//指示器位置
        banner.setImageLoader(new MyImageLoader());
        //添加轮播布局
        listview_customaction.addHeaderView(headerView);
        //添加底布局
        View bottomView = View.inflate(CustomActionActivity.this, R.layout.body_customaction, null);
        holder = new HeaderViewHolder(bottomView);
        listview_customaction.addHeaderView(bottomView);
        listview_customaction.setAdapter(new NullAdapter(CustomActionActivity.this, new ArrayList<String>(), R.layout.item_null));
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                if (!ListUtils.listIsNullOrEmpty(bannerVoList)) {
                    BannerVo vo = bannerVoList.get(position - 1);
                    if ("good".endsWith(vo.getType())) {    //如果是商品
                        Intent intent = new Intent(CustomActionActivity.this, GoodDetailActivity.class);
                        intent.putExtra("goodsId", vo.getGoodsId());
                        startActivity(intent);
                    } else {    //这个是html
                        Intent intent = new Intent(CustomActionActivity.this, MyWebViewActivity.class);
                        intent.putExtra("type", "banner");
                        intent.putExtra("webviewurl", vo.getHtmlurl());
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void initImagePicker() {
        imagePicker = App.getInstance().getImagePicker();
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
    }

    private void initGridView() {
        adapter = new ImageSelectAdapter(this, defaultDataArray, maxImgCount);
        adapter.setOnItemClickListener(this);
        holder.gridView.setLayoutManager(new GridLayoutManager(this, 4));
        holder.gridView.setHasFixedSize(true);
        holder.gridView.setAdapter(adapter);
    }

    /**
     * 数据初始化
     */
    private void initData() {
        showLoading("正在加载数据中...");
        tv_main_title.setText(R.string.customActionActivity);
        presenter.loadData("25");//获取网络数据
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CustomActionPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @OnClick(R.id.tv_shoppingCartFinish)
    void rightOnClick() {
        Intent intent = new Intent(this, CustomListActivity.class);
        startActivity(intent);
    }

    /**
     * 隐藏键盘操作
     */
    public void key() {
        // 虚拟键盘隐藏 判断view是否为空
        if (getWindow().peekDecorView() != null) {
            //隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(getWindow().peekDecorView().getWindowToken(), 0);
        }
    }

    @Override
    public void loadData(List<BannerVo> bannerVos) {
        hideLoading();
        if (!ListUtils.listIsNullOrEmpty(bannerVos)) {
            bannerVoList.addAll(bannerVos);
            List<String> imgUrls = new ArrayList<>();
            for (BannerVo bannerVo : bannerVos) {
                imgUrls.add(bannerVo.getUrl());
            }
            if (imgUrls.size() > 1) {
                banner.isAutoPlay(true);
            }
            banner.setImages(imgUrls);
            banner.start();
            banner.setVisibility(View.VISIBLE);
        } else {
            banner.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.tv_customApply_sumbit)
    void submitOnClick() {
        String name = holder.et_name.getText().toString();
        String mobile = holder.et_mobile.getText().toString();
        String qq = holder.et_qq.getText().toString();
        String wechat = holder.et_wechat.getText().toString();
        String desc = holder.et_desc.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastTools.showShort(this, "联系人不能为空");
            return;
        } else if (TextUtils.isEmpty(mobile)) {
            ToastTools.showShort(this, "手机号码不能为空");
            return;
        } else if (TextUtils.isEmpty(qq)) {
            ToastTools.showShort(this, "qq不能为空");
            return;
        } else if (TextUtils.isEmpty(wechat)) {
            ToastTools.showShort(this, "微信号不能为空");
            return;
        } else if (TextUtils.isEmpty(desc)) {
            ToastTools.showShort(this, "描述不能为空");
            return;
        } else if (fileList.size() <= 0) {
            ToastTools.showShort(this, "最少选择一张图片");
            return;
        } else {
            showLoading("正在提交中...");
            presenter.sumbitCustom(name, mobile, qq, wechat, desc, fileList);
        }
    }

    @Override
    public void sumbitCustomResult(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 1) {
            if (dialog == null) {
                dialog = new CommonDialog(this, "申请成功，请耐心等待工作人员联系您", "好的", "");
            }
            dialog.setCancelVisiable(View.GONE);
            dialog.setListener(new DialogOnClickListener() {
                @Override
                public void sureOnClick() {
                    dialog.dismiss();
                    Intent intent = new Intent(CustomActionActivity.this, MyCustomActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void cancleOnClick() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else if (result.getCode() == 3) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(StringConfig.REQUEST_CODE, 100);
            startActivityForResult(intent, Constants.REQUEST_LOGIN);
        }
    }

    @Override
    public void loadBannerError() {
        banner.setVisibility(View.GONE);
    }

    //意图回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
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
            //预览图片返回
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
        } else if (resultCode == Constants.REQUEST_LOGIN) {
            submitOnClick();
        }
    }

    public void compress(List<File> list) {
        if (list.size() > 0) {
            DialogTools.showLoding(this, "温馨提示", "获取中。。。");
            Luban.compress(this, list)
                    .putGear(Luban.THIRD_GEAR)
                    .launch(new OnMultiCompressListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(List<File> fileList) {
                            CustomActionActivity.this.fileList.addAll(fileList);
                            DialogTools.dissmiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            DialogTools.dissmiss();
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        holder.unbind();
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

    class HeaderViewHolder {

        @Bind(R.id.contact_name)
        EditText et_name;
        @Bind(R.id.contact_mobile)
        EditText et_mobile;
        @Bind(R.id.contact_qq)
        EditText et_qq;
        @Bind(R.id.contact_wechat)
        EditText et_wechat;
        @Bind(R.id.contact_description)
        EditText et_desc;
        @Bind(R.id.custom_gridview)
        RecyclerView gridView;

        public HeaderViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void unbind() {
            ButterKnife.unbind(this);
        }

    }

}
