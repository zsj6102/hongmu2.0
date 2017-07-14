package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.EncyclopediasInfo;
import com.colpencil.redwood.view.adapters.EncyclopediasAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_encyclopedias
)
public class EncyclopediasFragment extends ColpencilFragment {
    @Bind(R.id.encyclopedias_listview)
    ListView encyclopedias_listview;
    private List<EncyclopediasInfo> encyclopediasInfos=new ArrayList<>();
    @Override
    protected void initViews(View view) {

        for(int i=0;i<3;i++){
            EncyclopediasInfo encyclopediasInfo=new EncyclopediasInfo();
            encyclopediasInfo.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489033202628&di=0dacc1f0cae01168ebe1d362f64a314e&imgtype=0&src=http%3A%2F%2F5.26923.com%2Fdownload%2Fpic%2F000%2F327%2F862e3d13da417308895e06bfac198f87.jpg");
            encyclopediasInfos.add(encyclopediasInfo);
        }
        encyclopedias_listview.setAdapter(new EncyclopediasAdapter(getActivity(),encyclopediasInfos,R.layout.item_encyclopedias));

    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
}
