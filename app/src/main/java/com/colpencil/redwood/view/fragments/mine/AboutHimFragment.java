package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.View;

import com.colpencil.redwood.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

/**
 * @author QFZ
 * @Description:关于他
 * @Email DramaScript@outlook.com
 * @date 2017-03-09
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_about_him
)
public class AboutHimFragment extends ColpencilFragment {

    @Override
    protected void initViews(View view) {

    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
}
