package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.MinePostInfo;
import com.colpencil.redwood.bean.PostItemInfo;
import com.colpencil.redwood.view.adapters.MinePostAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author QFZ
 * @Description:他的帖子
 * @Email DramaScript@outlook.com
 * @date 2016/7/27
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_minepost
)
public class MinePostFragment extends ColpencilFragment{
    @Bind(R.id.minepost_listview)
    ListView minepost_listview;

    private List<MinePostInfo> minePostInfos=new ArrayList<>();
    @Override
    protected void initViews(View view) {
        for(int i=0;i<6;i++){
            MinePostInfo minePostInfo=new MinePostInfo();
            List<PostItemInfo> postItemInfos=new ArrayList<>();
            for(int j=0;j<6;j++){
                PostItemInfo postItemInfo=new PostItemInfo();
                postItemInfo.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489033202628&di=0dacc1f0cae01168ebe1d362f64a314e&imgtype=0&src=http%3A%2F%2F5.26923.com%2Fdownload%2Fpic%2F000%2F327%2F862e3d13da417308895e06bfac198f87.jpg");
                postItemInfos.add(postItemInfo);
            }
            minePostInfo.setPostItemInfos(postItemInfos);
            minePostInfos.add(minePostInfo);
        }

        minepost_listview.setAdapter(new MinePostAdapter(getActivity(),minePostInfos,R.layout.item_minepost));
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
}
