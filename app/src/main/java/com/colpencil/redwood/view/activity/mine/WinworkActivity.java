package com.colpencil.redwood.view.activity.mine;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.WinworkInfo;
import com.colpencil.redwood.view.adapters.WinworkAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


@ActivityFragmentInject(
        contentViewId = R.layout.activity_common
)
public class WinworkActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.common_listview)
    ListView listview;
    @Bind(R.id.tv_shoppingCartFinish)
    TextView tv_right;

    private WinworkAdapter mAdapter;
    private List<WinworkInfo> mlist = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        tv_title.setText("获奖作品");
        tv_right.setText("添加");
        iv_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        mAdapter = new WinworkAdapter(this, mlist, R.layout.item_winning_works);
        listview.setAdapter(mAdapter);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.tv_shoppingCartFinish) {

        }
    }
}
