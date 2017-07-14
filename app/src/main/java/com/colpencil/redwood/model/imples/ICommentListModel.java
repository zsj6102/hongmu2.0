package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.CommentList;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.List;

import rx.Observer;

/**
 * @Description:评论集合的Model接口类
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface ICommentListModel extends ColpencilModel{

    void loadComment(String commentid, int page, int pageSize);
    void sub(Observer<List<CommentList>> observer);

}
