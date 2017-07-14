package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

public class MinePostInfo implements Serializable {
    List<PostItemInfo> postItemInfos;

    public List<PostItemInfo> getPostItemInfos() {
        return postItemInfos;
    }

    public void setPostItemInfos(List<PostItemInfo> postItemInfos) {
        this.postItemInfos = postItemInfos;
    }
}
