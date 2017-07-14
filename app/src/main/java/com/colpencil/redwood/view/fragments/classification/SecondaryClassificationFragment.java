package com.colpencil.redwood.view.fragments.classification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.ChidrenCat;
import com.colpencil.redwood.view.activity.classification.CommoditySearchActivity;
import com.colpencil.redwood.view.adapters.HSearchGVAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.BaseGridView;

import java.util.List;

import butterknife.Bind;

/**
 * @author 曾 凤
 * @Description: 二级分类
 * @Email 20000263@qq.com
 * @date 2016/8/5
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_secondaryclassification
)
public class SecondaryClassificationFragment extends ColpencilFragment {

    @Bind(R.id.gridview_hSearch)
    BaseGridView gridview_hSearch;

    private HSearchGVAdapter hSearchGVAdapter;

    private List<ChidrenCat> mchidrenCats;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mchidrenCats = new Gson().fromJson(args.getString("key"), new TypeToken<List<ChidrenCat>>() {
            }.getType());
        }
    }

    public static SecondaryClassificationFragment getInstance(List<ChidrenCat> list) {
        SecondaryClassificationFragment fragment = new SecondaryClassificationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", new Gson().toJson(list));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        initData();
    }

    private void initData() {
        hSearchGVAdapter = new HSearchGVAdapter(mchidrenCats, getActivity());
        gridview_hSearch.setAdapter(hSearchGVAdapter);
        gridview_hSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CommoditySearchActivity.class);
                intent.putExtra("child_cat_id", mchidrenCats.get(position).getChild_cat_id());
                startActivity(intent);
            }
        });

    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
}
