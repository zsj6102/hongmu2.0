package com.colpencil.redwood.holder;

import com.colpencil.redwood.bean.AllSpecialInfo;

import java.util.ArrayList;
import java.util.List;

public class SpecialCategory {
    private String mCategoryName;
    private List<AllSpecialInfo> mCategoryItem = new ArrayList<>();

    public SpecialCategory(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public void addItem(AllSpecialInfo allSpecialInfo) {
        mCategoryItem.add(allSpecialInfo);
    }

    public Object getItem(int position) {
        if (position == 0) {
            return mCategoryName;
        } else {
            return mCategoryItem.get(position - 1);
        }
    }

    public int getItemCount(){
        return mCategoryItem.size()+1;
    }
}
