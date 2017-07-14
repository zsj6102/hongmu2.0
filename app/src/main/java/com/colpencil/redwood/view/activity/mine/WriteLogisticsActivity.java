package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.GoodOfOrder;
import com.colpencil.redwood.bean.LogisTicsBean;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.widgets.dialogs.LogisticDialog;
import com.colpencil.redwood.present.mine.WriteLogisticsPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.impl.IWriteLogisticsView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 描述：填写物流
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 18 14
 */
@ActivityFragmentInject(

        contentViewId = R.layout.activity_writelogistics
)
public class WriteLogisticsActivity extends ColpencilActivity implements IWriteLogisticsView {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;

    @Bind(R.id.et_companyName)
    TextView et_companyName;

    @Bind(R.id.et_logisticsNum)
    EditText et_logisticsNum;

    @Bind(R.id.iv_writeLogistics)
    ImageView iv_writeLogistics;

    @Bind(R.id.goodName_writeLogistics)
    TextView goodName_writeLogistics;

    @Bind(R.id.writeLogistics_buyGoodPrice)
    TextView writeLogistics_buyGoodPrice;

    @Bind(R.id.writeLogistics_spec)
    TextView writeLogistics_spec;

    @Bind(R.id.writeLogistics_buyGoodCount)
    TextView writeLogistics_buyGoodCount;

    private WriteLogisticsPresenter presenter;

    private GoodOfOrder goodOfOrder;

    private LogisticDialog dialog;

    private List<LogisTicsBean> mList = new ArrayList<>();

    private int logi_id = -1;

    @Override
    protected void initViews(View view) {
        goodOfOrder = new Gson().fromJson(getIntent().getStringExtra("key"), new TypeToken<GoodOfOrder>() {
        }.getType());
        initData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        tv_main_title.setText("填写物流");
        ImageLoaderUtils.loadImage(this, goodOfOrder.getGoodHeadPath(), iv_writeLogistics);
        goodName_writeLogistics.setText(goodOfOrder.getName());
        writeLogistics_buyGoodPrice.setText("¥" + goodOfOrder.getSalePrice());
        writeLogistics_spec.setText(goodOfOrder.getSpecs());
        if (goodOfOrder.getNum() == 0) {
            writeLogistics_buyGoodCount.setText("X1");
        } else {
            writeLogistics_buyGoodCount.setText("X" + goodOfOrder.getNum());
        }
        presenter.loadLogistic(goodOfOrder.getItem_id(), getIntent().getStringExtra("afterId"));
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new WriteLogisticsPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @OnClick(R.id.tv_sumbitWriteLogistics)
    void submit() {
        if (logi_id == -1) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "请填写物流公司信息");
        } else if (TextStringUtils.isEmpty(et_logisticsNum.getText().toString()) == true) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "请填写物流单号信息");
        } else {
            showLoading(Constants.progressName);
            HashMap<String, String> map = new HashMap<>();
            map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
            map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            map.put("item_id", goodOfOrder.getItem_id() + "");
            map.put("return_id", getIntent().getStringExtra("afterId"));
            map.put("logi_id", logi_id + "");
            map.put("postageNo", et_logisticsNum.getText().toString());
            presenter.sumbitInfor(map);
        }
    }

    @OnClick(R.id.et_companyName)
    void choose() {
        if (dialog == null) {
            dialog = new LogisticDialog(this, mList);
        }
        dialog.setListener(new LogisticDialog.PostClickListener() {

            @Override
            public void closeClick() {
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).isChoose()) {
                        logi_id = mList.get(i).getLogi_id();
                        break;
                    } else {
                        logi_id = -1;
                    }
                }
                dialog.dismiss();
            }

            @Override
            public void itemUnClick() {
                et_companyName.setText("请选择物流公司");
                logi_id = -1;
                dialog.dismiss();
            }

            @Override
            public void itemClick(int position) {
                et_companyName.setText(mList.get(position).getLogi_name());
                logi_id = mList.get(position).getLogi_id();
                dialog.dismiss();
            }
        });
        dialog.setTitle("请选择物流公司");
        dialog.show();
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
        finish();
    }

    @Override
    public void loadGis(List<LogisTicsBean> list) {
        mList.addAll(list);
    }
}

