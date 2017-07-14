package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

public class AuctionInfo implements Serializable {
    List<HomeAuctionInfo> homeAuctionInfos;

    public List<HomeAuctionInfo> getHomeAuctionInfos() {
        return homeAuctionInfos;
    }

    public void setHomeAuctionInfos(List<HomeAuctionInfo> homeAuctionInfos) {
        this.homeAuctionInfos = homeAuctionInfos;
    }
}
