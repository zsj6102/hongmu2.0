package com.colpencil.redwood.view.fragments;

import android.support.v4.app.Fragment;

import com.colpencil.redwood.view.activity.HomeActivity;
import com.colpencil.redwood.view.fragments.classification.ClassificationFragment;
import com.colpencil.redwood.view.fragments.cyclopedia.CyclopediaFragment;
import com.colpencil.redwood.view.fragments.discovery.DiscoveryFragment;
import com.colpencil.redwood.view.fragments.home.HomeGoodsFragment;

import com.colpencil.redwood.view.fragments.home.HomePageFragment;
import com.colpencil.redwood.view.fragments.mine.MeFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 曾 凤
 * @Description: fragment 工厂
 * @Email 20000263@qq.com
 * @date 2016/7/6
 */
public class FragmentFactory {

    private static Map<Integer, Fragment> map = new HashMap<Integer, Fragment>();

    public static Fragment creatFragment(int position, HomeActivity homeActivity) {
        Fragment fragment = null;
        fragment = map.get(position);
        if (fragment == null) {
            if (position == 0) {
                fragment = new HomeGoodsFragment();
            } else if (position == 1) {
                fragment = new ClassificationFragment();
            } else if (position == 2) {
                fragment = new DiscoveryFragment();
            } else if (position == 3) {
                fragment = new CyclopediaFragment();
            } else {
                fragment = new MeFragment();
            }
        }
        return fragment;
    }

}
