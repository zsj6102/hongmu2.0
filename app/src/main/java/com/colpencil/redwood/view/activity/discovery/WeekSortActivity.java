package com.colpencil.redwood.view.activity.discovery;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.home.CategoryPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.CategoryAdapter;
import com.colpencil.redwood.view.adapters.NullAdapter;
import com.colpencil.redwood.view.adapters.SystemCategoryAdapter;
import com.colpencil.redwood.view.impl.ICategoryView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 陈 宝
 * @Description:藏友圈和周拍的分类编辑
 * @Email 1041121352@qq.com
 * @date 2016/10/27
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_common
)
public class WeekSortActivity extends ColpencilActivity implements ICategoryView {
    @Bind(R.id.common_listview)
    ListView listView;
    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.tv_shoppingCartFinish)
    TextView tv_right;
    private HeaderHolder holder;
    private View header;
    private List<CategoryVo> mineList = new ArrayList<>();
    private CategoryAdapter mineAdapter;
    private List<CategoryVo> systemList = new ArrayList<>();
    private SystemCategoryAdapter systemAdapter;
    private CategoryPresenter presenter;
    private List<String> stringList = new ArrayList<>();
    private List<String> mine = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        tv_title.setText("添加分类");
        tv_right.setText("确定");
        header = LayoutInflater.from(this).inflate(R.layout.category_header, null);
        holder = new HeaderHolder(header);
        listView.addHeaderView(header);
        listView.setAdapter(new NullAdapter(this, new ArrayList<String>(), R.layout.item_null));
        initHeader();
        showLoading("");
        presenter.loadWeekShoot();
        presenter.loadAllWeekTag();
    }

    private void initHeader() {
        mineAdapter = new CategoryAdapter(this, mineList, R.layout.item_category);
        holder.mine_gridview.setAdapter(mineAdapter);
        systemAdapter = new SystemCategoryAdapter(this, systemList, R.layout.item_category);
        holder.system_gridview.setAdapter(systemAdapter);
        holder.mine_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mine.remove(position);
                mineList.remove(position);
                mineAdapter.notifyDataSetChanged();
            }
        });
        holder.system_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mine.contains(systemList.get(position).getCat_name())) {
                    mine.add(systemList.get(position).getCat_name());
                    mineList.add(systemList.get(position));
                    mineAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CategoryPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @OnClick(R.id.iv_back)
    void backOnClick() {
        finish();
    }

    @OnClick(R.id.tv_shoppingCartFinish)
    void addOnClick() {
        if (SharedPreferencesUtil.getInstance(this).getBoolean(StringConfig.ISLOGIN, false)) {
            stringList.clear();
            String cat_id = "";
            for (int i = 0; i < mineList.size(); i++) {
                stringList.add(mineList.get(i).getCat_id());
                cat_id += mineList.get(i).getCat_id() + ",";
            }
            if (stringList.size() <= 0) {
                ToastTools.showShort(this, "至少选择一个分类");
            } else {
                showLoading("正在提交数据中...");
                presenter.saveWeekTag(cat_id);
            }
        } else {
            final CommonDialog dialog = new CommonDialog(this, "你还没登录喔!", "去登录", "取消");
            dialog.setListener(new DialogOnClickListener() {
                @Override
                public void sureOnClick() {
                    Intent intent = new Intent(WeekSortActivity.this, LoginActivity.class);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        holder.unbind();
    }

    @Override
    public void operateResult(EntityResult<String> result) {
        hideLoading();
        ToastTools.showShort(this, result.getMsg());
        if (result.getCode() == 1) {
            RxBusMsg msg = new RxBusMsg();
            msg.setType(58);
            RxBus.get().post("rxBusMsg", msg);
        }
    }

    @Override
    public void loadMyTag(List<CategoryVo> list) {
        hideLoading();
        for (int i = 0; i < list.size(); i++) {
            mine.add(list.get(i).getCat_name());
        }
        mineList.addAll(list);
        mineAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadAllTag(List<CategoryVo> list) {
        hideLoading();
        systemList.addAll(list);
        systemAdapter.notifyDataSetChanged();
    }

    class HeaderHolder {

        @Bind(R.id.category_mine_gridview)
        MosaicGridView mine_gridview;
        @Bind(R.id.category_system_gridview)
        MosaicGridView system_gridview;

        public HeaderHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void unbind() {
            ButterKnife.unbind(this);
        }
    }
}
