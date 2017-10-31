package com.colpencil.redwood.present;

import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.FirstComment;
import com.colpencil.redwood.bean.NodeReplyItem;
import com.colpencil.redwood.bean.NumReturn;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.model.NodeReplyModel;
import com.colpencil.redwood.model.imples.INodeReplyModel;
import com.colpencil.redwood.view.activity.home.SearchStoreActivity;
import com.colpencil.redwood.view.impl.INodeReply;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;
import java.util.Map;

import rx.Observer;

public class NodeReplyPresenter extends ColpencilPresenter<INodeReply> {
    private INodeReplyModel model;
    public NodeReplyPresenter(){
        model = new NodeReplyModel();
    }
    public void getNodeReply(final int pageNo, Map<String,String> map){
        model.getNodeReply(map);
        Observer<ResultInfo<List<NodeReplyItem>>> observer = new Observer<ResultInfo<List<NodeReplyItem>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<List<NodeReplyItem>> listResultInfo) {
               if(listResultInfo!=null && mView != null){
                   if(pageNo ==1){
                       mView.refresh(listResultInfo);
                   }else{
                       mView.loadMore(listResultInfo);
                   }
               }else{
                   mView.loadFail(listResultInfo.getMessage());
               }
            }

        };
        model.subReply(observer);
    }

    public void loadCommentsNum(Map<String,String> map) {
        model.loadCommentsNum(map);
        Observer<FirstComment> observer = new Observer<FirstComment>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(FirstComment result) {
                mView.loadNums(result);
            }
        };
        model.subCommentsNum(observer);
    }
    public void getAddCommentResult(Map<String,String> map){
        model.getAddCommentResult(map);
        Observer<AddResult> observer = new Observer<AddResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AddResult result) {
                if(result!=null && mView!=null){
                    mView.addComment(result);
                }
            }
        };
        model.subAddResult(observer);
    }

    public void getLikeResult(Map<String,String> map){
        model.addLike(map);
        Observer<ResultInfo<String>> observer = new Observer<ResultInfo<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<String> resultInfo) {
                 if(mView!=null && resultInfo!=null){
                     mView.addLike(resultInfo);
                 }
            }
        };
        model.subLike(observer);
    }

}
