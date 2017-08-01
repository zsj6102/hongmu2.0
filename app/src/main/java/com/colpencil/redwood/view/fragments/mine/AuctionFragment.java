package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AuctionInfo;
import com.colpencil.redwood.bean.HomeAuctionInfo;
import com.colpencil.redwood.view.adapters.AuctionItemAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
/**
 * @author QFZ
 * @Description:架上拍品
 * @Email DramaScript@outlook.com
 * @date 2017-03-09
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_auction
)
public class AuctionFragment extends ColpencilFragment {
    @Bind(R.id.listview)
    ListView listview;
    private List<AuctionInfo> auctionInfos=new ArrayList<AuctionInfo>();

    @Override
    protected void initViews(View view) {

        for(int i=0;i<6;i++){
            AuctionInfo auctionInfo=new AuctionInfo();
            List<HomeAuctionInfo> homeAuctionInfos=new ArrayList<>();
            for(int j=0;j<6;j++){
                HomeAuctionInfo homeAuctionInfo=new HomeAuctionInfo();
                homeAuctionInfo.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489033202628&di=0dacc1f0cae01168ebe1d362f64a314e&imgtype=0&src=http%3A%2F%2F5.26923.com%2Fdownload%2Fpic%2F000%2F327%2F862e3d13da417308895e06bfac198f87.jpg");
                homeAuctionInfos.add(homeAuctionInfo);
            }
            auctionInfo.setHomeAuctionInfos(homeAuctionInfos);
            auctionInfos.add(auctionInfo);
        }

        listview.setAdapter(new AuctionItemAdapter(getActivity(), auctionInfos, R.layout.item_auction));
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }
    @Override
    public void loadData() {}
    @Override
    public void bindView(Bundle savedInstanceState) {

    }
}
