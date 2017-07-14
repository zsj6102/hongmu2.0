package com.colpencil.redwood.view.impl;

import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @author 曾 凤
 * @Description: 我的收藏
 * @Email 20000263@qq.com
 * @date 2016/8/8
 */
public interface IMyCollectionView extends ColpencilBaseView {
    /**
     * 清空收藏记录
     * @param code
     * @param msg
     */
    void removeResult(String code, String msg);
}
