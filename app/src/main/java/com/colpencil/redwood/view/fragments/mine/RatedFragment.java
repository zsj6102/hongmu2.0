package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.RatedInfo;
import com.colpencil.redwood.view.adapters.RatedAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_rated
)
public class RatedFragment extends ColpencilFragment {
    @Bind(R.id.rated_listview)
    ListView rated_listview;
    private List<RatedInfo> ratedInfos=new ArrayList<>();
    @Override
    protected void initViews(View view) {
        for(int i=0;i<5;i++){
            RatedInfo ratedInfo=new RatedInfo();
            ratedInfos.add(ratedInfo);
        }
        rated_listview.setAdapter(new RatedAdapter(getActivity(),ratedInfos,R.layout.item_rated));
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
}
