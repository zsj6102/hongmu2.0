package com.colpencil.redwood.view.activity.discovery;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.OfficialResult.ResultBean;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.CustomListPresenter;
import com.colpencil.redwood.view.activity.HomeActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.CustomListAdapter;
import com.colpencil.redwood.view.impl.ICustomListView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author 陈宝
 * @Description:官方定制列表
 * @Email DramaScript@outlook.com
 * @date 2016/9/1
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_custom_list)
public class CustomListActivity extends ColpencilActivity implements ICustomListView {

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.gridview)
    GridView gridView;
    @Bind(R.id.iv_home)
    ImageView iv_home;

    private CustomListPresenter presenter;
    private CustomListAdapter adapter;
    private List<ResultBean> resultBeen = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        tv_title.setText("官方定制");
        iv_home.setImageResource(R.mipmap.home_icon);
        adapter = new CustomListAdapter(this, resultBeen, R.layout.item_home_good);
        gridView.setAdapter(adapter);
        presenter.loadGoods();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CustomListPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.iv_back)
    void backOnClick() {
        finish();
    }

    @OnClick(R.id.iv_custom)
    void customOnClick() {
        gridView.setSelection(0);
    }

    @OnClick(R.id.iv_totop)
    void totopClick() {
        if (SharedPreferencesUtil.getInstance(this).getBoolean(StringConfig.ISLOGIN, false)) {
            Intent intent = new Intent(this, CustomActionActivity.class);
            startActivity(intent);
        } else {
            showDialog();
        }
    }

    private void showDialog() {
        final CommonDialog dialog = new CommonDialog(this, "你还没登录喔!", "去登录", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                Intent intent = new Intent(CustomListActivity.this, LoginActivity.class);
                intent.putExtra(StringConfig.REQUEST_CODE, 100);
                startActivityForResult(intent, Constants.REQUEST_LOGIN);
                dialog.dismiss();
            }

            @Override
            public void cancleOnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.iv_home)
    void toHome() {
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(3);
        RxBus.get().post("rxBusMsg", rxBusMsg);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loadGood(List<ResultBean> result) {
        resultBeen.addAll(result);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadError(String msg) {
        ToastTools.showShort(this, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
