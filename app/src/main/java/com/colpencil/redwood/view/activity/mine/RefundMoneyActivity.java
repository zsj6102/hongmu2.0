package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.RefundReason;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.widgets.dialogs.ReasonDialog;
import com.colpencil.redwood.present.mine.RefundMoneyPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.impl.IRefundMoneyView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 描述 退款界面
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 16 30
 */
@ActivityFragmentInject(

        contentViewId = R.layout.activity_refundmoney
)
public class RefundMoneyActivity extends ColpencilActivity implements IRefundMoneyView {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;
    @Bind(R.id.tv_shoppingCartFinish)
    TextView tv_shoppingCartFinish;
    @Bind(R.id.et_refundMoney)
    EditText et_refundMoney;
    @Bind(R.id.tv_refundReason)
    TextView tv_reason;
    @Bind(R.id.et_people_return)
    EditText et_people;
    @Bind(R.id.et_people_bankname)
    EditText et_bankname;
    @Bind(R.id.et_people_banknum)
    EditText et_num;

    private ReasonDialog reasonDialog;
    private int order_id;
    private List<RefundReason> mRefundReasons = new ArrayList<>();
    private RefundMoneyPresenter presenter;
    private int reasonId = -1;

    @Override
    protected void initViews(View view) {
        order_id = getIntent().getIntExtra("order_id", 0);
        initData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        presenter.loadReason();
        tv_main_title.setText("申请退款");
        tv_shoppingCartFinish.setText("提交");
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new RefundMoneyPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    /**
     * 提交退款申请
     */
    private void submit() {
        if (reasonId == -1) {//选择退款理由
            ToastTools.showShort(this, "请选择退款理由");
            return;
        } else if (TextStringUtils.isEmpty(et_refundMoney.getText().toString()) == true) {//退款补充说明
            ToastTools.showShort(this, "请填写退款补充说明");
            return;
        } else {
            showLoading(Constants.progressName);
            HashMap<String, String> params = new HashMap<>();
            params.put("order_id", order_id + "");
            params.put("applyReasonId", reasonId + "");
            params.put("apply_reason", et_refundMoney.getText().toString());
            params.put("get_name", et_people.getText().toString());
            params.put("bank_name", et_bankname.getText().toString());
            params.put("bank_num", et_num.getText().toString());
            presenter.sumbtRefundMoney(params);//提交退款申请
        }
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    @OnClick(R.id.tv_shoppingCartFinish)
    void submitToServer() {
        submit();
    }

    @OnClick(R.id.select_refundMoneyReason)
    void chooseReason() {
        if (reasonDialog == null) {
            reasonDialog = new ReasonDialog(RefundMoneyActivity.this, "请选择退款理由", mRefundReasons);
        }
        reasonDialog.setListener(new ReasonDialog.ItemClickListener() {
            @Override
            public void itemClick(RefundReason reason) {
                reasonId = reason.getRefundResonID();
                tv_reason.setText(reason.getRefundResonContent());
                reasonDialog.dismiss();
            }

            @Override
            public void itemUnClick() {
                tv_reason.setText("请选择申请理由");
                reasonId = -1;
            }
        });
        reasonDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void loadReason(List<RefundReason> refundReasons) {
        hideLoading();
        mRefundReasons.addAll(refundReasons);
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
}
