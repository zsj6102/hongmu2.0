package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.Info.StoreDetail;
import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.mine.StoreDetailPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.FansAndLikeActivity;
import com.colpencil.redwood.view.activity.mine.StoreHomeActivity;
import com.colpencil.redwood.view.activity.mine.StoreIntroductionActivity;
import com.colpencil.redwood.view.adapters.FansItemAdapter;
import com.colpencil.redwood.view.impl.IAboutView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicGridView;
import com.property.colpencil.colpencilandroidlibrary.Ui.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.type;


/**
 * @author QFZ
 * @Description:关于他
 * @Email DramaScript@outlook.com
 * @date 2017-03-09
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_about_him)
public class AboutHimFragment extends ColpencilFragment implements IAboutView {

    @Bind(R.id.iv_head)
    SelectableRoundedImageView ivHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.iv_lv)
    ImageView ivLv;
    @Bind(R.id.tv_lv)
    TextView tvLv;
    @Bind(R.id.iv_store_type)
    ImageView ivStoreType;
    @Bind(R.id.tv_store_type)
    TextView tvStoreType;
    @Bind(R.id.tv_praise_rate)
    TextView tvPraiseRate;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_store_sign)
    TextView tvStoreSign;
    @Bind(R.id.like_gridview)
    MosaicGridView likeGridview;
    @Bind(R.id.fans_gridview)
    MosaicGridView fansGridview;
    @Bind(R.id.notice)
    TextView tvNotice;
    private int storeid;
    @Bind(R.id.unfocus_layout)
    LinearLayout unfocus;     //未关注
    @Bind(R.id.befocus_layout)
    LinearLayout befocus;
    @Bind(R.id.layout_refer)
    LinearLayout referLayout;
    @Bind(R.id.layout_cat)
    LinearLayout catLayout;
    @Bind(R.id.tv_reffer)
    TextView tvRefer;
    @Bind(R.id.tv_cat)
    TextView tvCat;
    private StoreDetailPresenter presenter;
    private FansItemAdapter adapterFans;
    private FansItemAdapter adapterLike;
    HashMap<String, String> params = new HashMap<>();
    private List<ItemStoreFans> dataFans = new ArrayList<>();
    private List<ItemStoreFans> dataLike = new ArrayList<>();
    private Integer member_id;//商家id
    Map<String, String> map = new HashMap<>();
    Map<String, String> maps = new HashMap<>();
    private int likepos;
    private int fanspos;
    private boolean isFans = false;//判断是商家粉丝列表还是商家关注
    private String detail;
    private int type;

    public static AboutHimFragment newInstance(int store_id, int type) {
        AboutHimFragment fragment = new AboutHimFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("store_id", store_id);
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        storeid = getArguments().getInt("store_id");
        type = getArguments().getInt("type");

        initAdapter();
    }

    private void initAdapter() {
        adapterFans = new FansItemAdapter(getActivity(), dataFans, R.layout.item_laytou_fans);
        adapterFans.setListener(new FansItemAdapter.CareClick() {
            @Override
            public void careClick(int position) {
                isFans = false;
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
                    HashMap<String, String> params = new HashMap<String, String>();
                    fanspos = position;
                    if (dataFans.get(position).getIsfocus() == 0) {
                        params.put("fans_type", 6 + "");                       //取消关注
                    } else {
                        params.put("fans_type", 5 + "");                       //关注
                    }
                    params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                    params.put("fans_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    params.put("concern_id", dataFans.get(position).getMember_id() + "");
                    showLoading("");
                    presenter.getOperate(params);
                } else {
                    showLogin();
                }
            }

            @Override
            public void toClick(int position) {
                if (dataFans.get(position).getStore_id() != null && dataFans.get(position).getStore_type() != null) {
                    Intent intent = new Intent(getActivity(), StoreHomeActivity.class);
                    intent.putExtra("type", dataFans.get(position).getStore_type() + "");
                    intent.putExtra("store_id", dataFans.get(position).getStore_id());
                    getActivity().startActivity(intent);
                } else {
                    showMsg("他还不是商家");
                }
            }
        });
        adapterLike = new FansItemAdapter(getActivity(), dataLike, R.layout.item_laytou_fans);
        adapterLike.setListener(new FansItemAdapter.CareClick() {
            @Override
            public void toClick(int position) {
                if (dataLike.get(position).getStore_id() != null && dataLike.get(position).getStore_type() != null) {
                    Intent intent = new Intent(getActivity(), StoreHomeActivity.class);
                    intent.putExtra("type", dataLike.get(position).getStore_type() + "");
                    intent.putExtra("store_id", dataLike.get(position).getStore_id());
                    getActivity().startActivity(intent);
                } else {
                    showMsg("他还不是商家");
                }
            }

            @Override
            public void careClick(int position) {
                isFans = true;
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
                    likepos = position;
                    HashMap<String, String> params = new HashMap<String, String>();
                    if (dataLike.get(position).getIsfocus() == 0) {
                        params.put("fans_type", 6 + "");                       //取消关注
                    } else {
                        params.put("fans_type", 5 + "");                       //关注
                    }
                    params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                    params.put("fans_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    params.put("concern_id", dataLike.get(position).getMember_id() + "");
                    showLoading("");
                    presenter.getOperate(params);
                } else {
                    showLogin();
                }

            }
        });
        likeGridview.setAdapter(adapterLike);
        fansGridview.setAdapter(adapterFans);
    }

    private void showMsg(String msg) {
        ToastTools.showShort(getActivity(), msg);
    }

    @Override
    public ColpencilPresenter getPresenter() {

        presenter = new StoreDetailPresenter();
        return presenter;
    }

    @OnClick(R.id.main_introduction)
    void toIntro() {
        Intent intent = new Intent(getActivity(), StoreIntroductionActivity.class);
        intent.putExtra("detail", detail);
        startActivity(intent);
    }

    @OnClick(R.id.main_follow)
    void toFollow() {
        Intent intent = new Intent(getActivity(), FansAndLikeActivity.class);
        intent.putExtra("store_id", storeid);
        intent.putExtra("type", 1 + "");
        startActivity(intent);
    }

    @OnClick(R.id.main_fans)
    void toFans() {
        Intent intent = new Intent(getActivity(), FansAndLikeActivity.class);
        intent.putExtra("store_id", storeid);
        intent.putExtra("type", 0 + "");
        startActivity(intent);
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadData() {
        params.put("store_id", storeid + "");
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        presenter.getStoreDetail(params);
        map.put("store_id", storeid + "");
        map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        map.put("fans_type", "0");
        map.put("page", "1");
        map.put("pageSiez", "10");
        presenter.getStoreFans(map);
        maps.put("store_id", storeid + "");
        maps.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        maps.put("fans_type", "1");
        maps.put("page", "1");
        maps.put("pageSiez", "10");
        presenter.getStoreLike(maps);
    }

    @OnClick(R.id.unfocus_layout)
    void care() {
        //关注
        if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("fans_type", 5 + "");//关注
            params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            params.put("fans_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
            if (member_id != null) {
                params.put("concern_id", member_id + "");
                presenter.getCareReturn(params);
            }
        } else {
            showLogin();
        }

    }

    @Override
    public void operate(CareReturn result) {
        hideLoading();
        ToastTools.showShort(getActivity(), result.getMessage());
        if (result.getCode() == 0) {
            if (!isFans) {

                //                presenter.getStoreFans(map);
                if (dataFans.get(fanspos).getIsfocus() == 0) {
                    dataFans.get(fanspos).setIsfocus(1);
                } else {
                    dataFans.get(fanspos).setIsfocus(0);
                }
                for (int i = 0; i < dataLike.size(); i++) {
                    if (dataLike.get(i).getStore_id() == dataFans.get(fanspos).getStore_id()) {
                        if (dataLike.get(i).getIsfocus() == 0) {
                            dataLike.get(i).setIsfocus(1);
                        } else {
                            dataLike.get(i).setIsfocus(0);
                        }
                    }
                }
                adapterLike.notifyDataSetChanged();
                adapterFans.notifyDataSetChanged();
            } else {
                //                presenter.getStoreLike(maps);
                if (dataLike.get(likepos).getIsfocus() == 0) {
                    dataLike.get(likepos).setIsfocus(1);
                } else {
                    dataLike.get(likepos).setIsfocus(0);
                }
                for (int i = 0; i < dataFans.size(); i++) {
                    if (dataFans.get(i).getStore_id() == dataLike.get(likepos).getStore_id()) {
                        if (dataFans.get(i).getIsfocus() == 0) {
                            dataFans.get(i).setIsfocus(1);
                        } else {
                            dataFans.get(i).setIsfocus(0);
                        }
                    }
                }
                adapterFans.notifyDataSetChanged();
                adapterLike.notifyDataSetChanged();
            }
        }

    }

    private void showLogin() {
        final CommonDialog dialog = new CommonDialog(getActivity(), "你还没登录喔!", "去登录", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
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

    @OnClick(R.id.befocus_layout)
    void quitCare() {
        //取消关注
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fans_type", 6 + "");//关注
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("fans_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        if (member_id != null) {
            params.put("concern_id", member_id + "");
            presenter.getUncareReturn(params);
        }
    }

    @Override
    public void loadFail(String message) {
        ToastTools.showShort(getActivity(), message);
        hideLoading();
    }

    @Override
    public void getStoreDetail(ResultInfo<StoreDetail> data) {
        if (data.getCode() == 0) {
            if (data.getData() != null) {
                ImageLoaderUtils.loadImage(getActivity(), data.getData().getStore_path(), ivHead);
                tvName.setText(data.getData().getStore_name());
                ImageLoaderUtils.loadImage(getActivity(), data.getData().getMember_photo(), ivLv);
                tvLv.setText(data.getData().getLv_name());

                if(type == 3){
                    if(!TextUtils.isEmpty(data.getData().getReferrer())){
                        referLayout.setVisibility(View.VISIBLE);
                        tvRefer.setText(data.getData().getReferrer());
                    }
                    if(data.getData().getCatname()!=null){
                        catLayout.setVisibility(View.VISIBLE);
                        tvCat.setText(data.getData().getCatname());
                    }

                }else{
                    referLayout.setVisibility(View.GONE);
                    catLayout.setVisibility(View.GONE);
                }
                ImageLoaderUtils.loadImage(getActivity(), data.getData().getStore_type_path(), ivStoreType);
                tvStoreType.setText(data.getData().getStore_type_name());
                tvPraiseRate.setText(data.getData().getPraise_rate());
                tvAddress.setText(data.getData().getStore_city());
                tvStoreSign.setText(data.getData().getSign());
                member_id = data.getData().getMember_id();
                detail = data.getData().getDescription();
                if (data.getData().getIsfocus() == 0) {
                    unfocus.setVisibility(View.GONE);
                    befocus.setVisibility(View.VISIBLE);
                } else {
                    unfocus.setVisibility(View.VISIBLE);
                    befocus.setVisibility(View.GONE);
                }
                if (data.getData().getStore_banner() != null && !TextUtils.isEmpty(data.getData().getStore_banner())) {
                    tvNotice.setVisibility(View.VISIBLE);
                    tvNotice.setText(data.getData().getStore_banner());
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void getLike(ResultInfo<List<ItemStoreFans>> like) {
        hideLoading();
        if (like.getCode() == 0) {
            dataLike.clear();
            if (like.getData().size() > 8) {
                for (int i = 0; i < 8; i++) {
                    dataLike.add(like.getData().get(i));
                }
            } else {
                dataLike.addAll(like.getData());
            }
            adapterLike.notifyDataSetChanged();
        }
    }

    @Override
    public void getFans(ResultInfo<List<ItemStoreFans>> fans) {
        hideLoading();
        if (fans.getCode() == 0) {
            dataFans.clear();
            if (fans.getData().size() > 8) {
                for (int i = 0; i < 8; i++) {
                    dataFans.add(fans.getData().get(i));
                }
            } else {
                dataFans.addAll(fans.getData());
            }
            adapterFans.notifyDataSetChanged();
        }
    }

    @Override
    public void care(CareReturn result) {
        if (result.getCode() == 0) {
            unfocus.setVisibility(View.GONE);
            befocus.setVisibility(View.VISIBLE);
            RxBusMsg msg = new RxBusMsg();
            msg.setType(510);
            RxBus.get().post("rxBusMsg", msg);
        }
            ToastTools.showShort(getActivity(), result.getMessage());

    }

    @Override
    public void uncare(CareReturn reult) {
        if (reult.getCode() == 0) {
            unfocus.setVisibility(View.VISIBLE);
            befocus.setVisibility(View.GONE);
            RxBusMsg msg = new RxBusMsg();
            msg.setType(511);
            RxBus.get().post("rxBusMsg", msg);
        }
        ToastTools.showShort(getActivity(), reult.getMessage());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
