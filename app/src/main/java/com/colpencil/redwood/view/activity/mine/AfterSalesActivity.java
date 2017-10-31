package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.RefundReason;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.tools.DialogTools;
import com.colpencil.redwood.function.widgets.dialogs.ReasonDialog;
import com.colpencil.redwood.present.mine.AfterSalesPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.ImageSelectAdapter;
import com.colpencil.redwood.view.impl.IAfterSalesView;
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
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;

/**
 * 描述：申请售后
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 16 54
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_aftersales
)
public class AfterSalesActivity extends ColpencilActivity
        implements IAfterSalesView, ImageSelectAdapter.OnRecyclerViewItemClickListener {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;
    @Bind(R.id.iv_afterSalesTypeFirst)
    ImageView iv_afterSalesTypeFirst;
    @Bind(R.id.iv_afterSalesTypeSecond)
    ImageView iv_afterSalesTypeSecond;
    @Bind(R.id.tv_afterSalesReason)
    TextView tv_afterSalesReason;
    @Bind(R.id.afterSales_gridView)
    RecyclerView afterSales_gridView;
    @Bind(R.id.et_afterSales)
    EditText et_afterSales;
    @Bind(R.id.tv_shoppingCartFinish)
    TextView tv_shoppingCartFinish;
    @Bind(R.id.et_people_return)
    EditText et_returnName;
    @Bind(R.id.et_people_bankname)
    EditText et_bankName;
    @Bind(R.id.et_people_banknum)
    EditText et_bankNum;
    @Bind(R.id.ll_bottom)
    LinearLayout ll_bottom;


    private int typeReason = 2;//售后类型
    private ReasonDialog reasonDialog;

    private List<RefundReason> returnReason = new ArrayList<>();

    private List<RefundReason> exchangeReason = new ArrayList<>();

    private AfterSalesPresenter presenter;

    private int order_id;//订单id

    private int reasonId = -1;

    private ArrayList<ImageItem> defaultDataArray = new ArrayList<>();
    private int maxImgCount = 5;               //允许选择图片最大数
    private ImagePicker imagePicker;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ImageSelectAdapter adapter;
    private List<File> fileList = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        order_id = getIntent().getIntExtra("order_id", 0);
        //获取两两种理由集合
        showLoading(Constants.progressName);
        presenter.loadReason(1);
        presenter.loadReason(2);
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
        tv_shoppingCartFinish.setText("提交");
        adapter = new ImageSelectAdapter(this, defaultDataArray, maxImgCount);
        adapter.setOnItemClickListener(this);
        afterSales_gridView.setLayoutManager(new GridLayoutManager(this, 4));
        afterSales_gridView.setHasFixedSize(true);
        afterSales_gridView.setAdapter(adapter);
        tv_main_title.setText("申请售后");
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new AfterSalesPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    /**
     * 提交售后申请
     */
    private void submit() {
        if (reasonId == -1) {
            ToastTools.showShort(this, "请选售后理由");
        } else if (TextStringUtils.isEmpty(et_afterSales.getText().toString()) == true) {
            ToastTools.showShort(this, "请填写补充说明");
        } else {
            HashMap<String, String> map = new HashMap<>();
            if (typeReason == 1) {
                map.put("get_name", et_returnName.getText().toString());
                map.put("bank_name", et_bankName.getText().toString());
                map.put("bank_num", et_bankNum.getText().toString());
            }
            map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
            map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            map.put("item_id", getIntent().getIntExtra("item_id", 0) + "");
            map.put("order_id", order_id + "");
            map.put("after_sale_type", typeReason + "");
            map.put("applyReasonId", reasonId + "");
            map.put("reason", et_afterSales.getText().toString());
            showLoading(Constants.progressName);
            presenter.sumbtRefundMoney(map, fileList);
        }
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    @OnClick(R.id.ll_afterSalesTypeFirst)
    void returnGoods() {
        if (typeReason != 1) {
            typeReason = 1;
            reasonId = -1;
            iv_afterSalesTypeFirst.setImageResource(R.mipmap.select_yes_red);
            iv_afterSalesTypeSecond.setImageResource(R.mipmap.select_no);
            tv_afterSalesReason.setText("");
            ll_bottom.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.ll_afterSalesTypeSecond)
    void changeGoods() {
        if (typeReason != 2) {
            typeReason = 2;
            reasonId = -1;
            iv_afterSalesTypeSecond.setImageResource(R.mipmap.select_yes_red);
            iv_afterSalesTypeFirst.setImageResource(R.mipmap.select_no);
            tv_afterSalesReason.setText("");
            ll_bottom.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.select_afterSalesReason)
    void chooseReason() {
        if (typeReason == 1) {
            reasonDialog = new ReasonDialog(AfterSalesActivity.this, "请选择售后理由", returnReason);
        } else {
            reasonDialog = new ReasonDialog(AfterSalesActivity.this, "请选择售后理由", exchangeReason);
        }
        reasonDialog.setListener(new ReasonDialog.ItemClickListener() {
            @Override
            public void itemClick(RefundReason reason) {
                tv_afterSalesReason.setText(reason.getRefundResonContent());
                reasonId = reason.getRefundResonID();
                reasonDialog.dismiss();
            }

            @Override
            public void itemUnClick() {
                tv_afterSalesReason.setText("请选择申请理由");
                reasonId = -1;
            }
        });
        reasonDialog.show();
    }

    @OnClick(R.id.tv_shoppingCartFinish)
    void submitToServer() {
        submit();
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
                            AfterSalesActivity.this.fileList.addAll(fileList);
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
    public void loadReason(List<RefundReason> reasons, int type) {
        hideLoading();
        if (type == 1) {
            returnReason.addAll(reasons);
        } else {
            exchangeReason.addAll(reasons);
        }
    }

    @Override
    public void fail(int code, String msg) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), msg);
        if (code == 3) {//未登录
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void submitSuccess(String msg) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), msg);
        Intent intent = new Intent(this, AfterSalesCenterActivity.class);
        startActivity(intent);
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
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }


}

