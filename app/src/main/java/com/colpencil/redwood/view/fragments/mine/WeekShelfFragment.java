package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.view.adapters.WeekShelfAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_common_list
)
public class WeekShelfFragment extends ColpencilFragment {

    private WeekShelfAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Bind(R.id.common_listview)
    ListView common_listview;

    @Override
    protected void initViews(View view) {
        for (int i=0;i<20;i++){
            list.add("哈哈哈，嘎嘎嘎，瓜瓜瓜，啦啦啦，嘻嘻嘻"+i);
        }
        adapter = new WeekShelfAdapter(getActivity(),list, R.layout.item_weekshelf);
        common_listview.setAdapter(adapter);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
}