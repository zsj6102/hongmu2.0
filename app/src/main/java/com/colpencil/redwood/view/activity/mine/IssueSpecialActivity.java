package com.colpencil.redwood.view.activity.mine;

import android.os.Bundle;
import android.view.View;

import com.colpencil.redwood.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_issuespecial
)
public class IssueSpecialActivity extends ColpencilActivity{
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
