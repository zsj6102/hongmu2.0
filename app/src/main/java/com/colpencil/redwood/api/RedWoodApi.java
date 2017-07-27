package com.colpencil.redwood.api;

import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.AddressReturn;
import com.colpencil.redwood.bean.AfterSalesCenterReturn;
import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.BrowsingCyclopediaDate;
import com.colpencil.redwood.bean.BrowsingGoodDate;
import com.colpencil.redwood.bean.BrowsingPostDate;
import com.colpencil.redwood.bean.CardWallInfo;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.CatReturnData;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.CodeBean;
import com.colpencil.redwood.bean.CommentReturn;
import com.colpencil.redwood.bean.CouponsResult;
import com.colpencil.redwood.bean.CustomReturn;
import com.colpencil.redwood.bean.CyclopediaContent;
import com.colpencil.redwood.bean.CyclopediaInfoVo;
import com.colpencil.redwood.bean.CyclopediaItem;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.FuncPointVo;
import com.colpencil.redwood.bean.GoodComment;
import com.colpencil.redwood.bean.GoodsItem;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.HomeRecommend;
import com.colpencil.redwood.bean.IntegralReturn;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.bean.LogisTicsBean;
import com.colpencil.redwood.bean.MessageReturn;
import com.colpencil.redwood.bean.MusicResourseReturn;
import com.colpencil.redwood.bean.MyCyclopediaInfo;
import com.colpencil.redwood.bean.MyWeekShootReturn;
import com.colpencil.redwood.bean.NewsInfoVo;
import com.colpencil.redwood.bean.OrderCenterReturn;
import com.colpencil.redwood.bean.OrderDetailsReturn;
import com.colpencil.redwood.bean.PayForReturn;
import com.colpencil.redwood.bean.PayKeyRetrun;
import com.colpencil.redwood.bean.PostCollectionReturn;
import com.colpencil.redwood.bean.Result;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.bean.SellApply;
import com.colpencil.redwood.bean.ShoppingCartReturn;
import com.colpencil.redwood.bean.SizeColorInfo;
import com.colpencil.redwood.bean.SortOptionsReturn;
import com.colpencil.redwood.bean.WeekPersonList;
import com.colpencil.redwood.bean.result.AdResult;
import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.colpencil.redwood.bean.result.AllSpecialResult;
import com.colpencil.redwood.bean.result.AnnounceResult;
import com.colpencil.redwood.bean.result.ApplyReturn;
import com.colpencil.redwood.bean.result.BidderResult;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.bean.result.CommentResult;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.bean.result.CustomDetailResult;
import com.colpencil.redwood.bean.result.CustomGoodResult;
import com.colpencil.redwood.bean.result.CustomResult;
import com.colpencil.redwood.bean.result.CyclopediaResult;
import com.colpencil.redwood.bean.result.DynamicResult;
import com.colpencil.redwood.bean.result.GoodInfoResult;
import com.colpencil.redwood.bean.result.GoodsTypeResult;
import com.colpencil.redwood.bean.result.HomeGoodResult;
import com.colpencil.redwood.bean.result.HomeTagResult;
import com.colpencil.redwood.bean.result.HotResult;
import com.colpencil.redwood.bean.result.MemberCouponResult;
import com.colpencil.redwood.bean.result.MyCommentResult;
import com.colpencil.redwood.bean.result.OfficialResult;
import com.colpencil.redwood.bean.result.PCommentResult;
import com.colpencil.redwood.bean.result.PostStateResult;
import com.colpencil.redwood.bean.result.PostsResult;
import com.colpencil.redwood.bean.result.ResultInfo;
import com.colpencil.redwood.bean.result.ResultRegion;
import com.colpencil.redwood.bean.result.SignInResult;
import com.colpencil.redwood.bean.result.SpecialIntroduceResult;
import com.colpencil.redwood.bean.result.StatisticResult;
import com.colpencil.redwood.bean.result.WeekAuctionListResult;
import com.colpencil.redwood.bean.result.WeekAuctionTagResult;
import com.colpencil.redwood.bean.result.WeekShootDetailResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;
import rx.Observer;

/**
 * 描述： Api
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/6 11 04
 */
public interface RedWoodApi {

    @GET("mobileArticle!getArticleList.do")
    Observable<CyclopediaResult> getCycloTagItem(@QueryMap HashMap<String, String> params);


    @GET("mobileSearch!hotSearch.do")
    Observable<HotResult> getHot(@Query("cat_id") int cat_id);

    @GET("mobileNotes!allNotesCats.do")
    Observable<HomeTagResult> loadCircleTag();

    /**
     * 获取百科列表
     */
    @GET("mobileArticle!getArticleList.do")
    Observable<ListResult<CyclopediaInfoVo>> loadCyclopedia(@QueryMap HashMap<String, String> params);

    /**
     * 获取百科搜索结果
     */
    @GET("mobileArticle!getArticleList.do")
    Observable<ListResult<CyclopediaInfoVo>> searcyCyclopedia(@QueryMap HashMap<String, String> params);

    /**
     * 获取我的评论
     */
    @GET("mobileNotes!getMyNotes.do")
    Observable<MyCommentResult> getMyComment(@QueryMap HashMap<String, String> params);

    /**
     * 发表帖子
     */
    @Multipart
    @POST("mobileNotes!postNotes.do")
    Observable<CommonResult> submitToServer(@PartMap Map<String, RequestBody> params);

    /**
     * 提交售后申请
     */
    @Multipart
    @POST("mobileOrder!applyForAfterSale.do")
    Observable<ResultCodeInt> applyForAfterSale(@PartMap Map<String, RequestBody> params);

    @GET("mobileNotes!getNotesDetail.do")
    Observable<PostsResult> loadPosts(@Query("ote_id") String ote_id);

    @GET("mobileNotes!getNotesReplys.do")
    Observable<PCommentResult> loadCommentList(@Query("ote_id") String commentId, @Query("pageNo") int page, @Query("pageSize") int pageSize);

    @GET("mobileGoodsDetail!goodsDetail.do")
    Observable<GoodInfoResult> loadGoodInfo(@Query("goods_id") String goodid);

    @GET("mobileGoodsDetail!goodsComment.do")
    Observable<ListResult<GoodComment>> loadGoodComment(@Query("goods_id") String goodsid, @Query("pageNo") int page, @Query("pageSize") int pageSize);

    @GET("mobileMemberCenter!personIndex.do")
    Observable<LoginBean> loadIntegral(@Query("member_id") int member_id, @Query("token") String token);

    @GET("mobileMember!signIn.do")
    Observable<SignInResult> signinToServer(@Query("member_id") String userid, @Query("token") String token);

    /**
     * 获取新闻列表
     *
     * @return
     */
    @GET("mobileArticle!getArticleList.do")
    Observable<ListResult<NewsInfoVo>> loadNewsList(@QueryMap HashMap<String, String> params);

    /**
     * 注册获取验证码
     */
    @GET("mobileMember!getvalidateCode.do")
    Observable<LoginBean> getvalidateCode(@Query("mobile") String mobile);

    /**
     * 用户进行注册
     */
    @GET("mobileMember!phoneRegister.do")
    Observable<LoginBean> phoneRegister(@Query("mobile") String mobile, @Query("yzm") String yzm, @Query("password") String password);

    /**
     * 获取首页Banner
     */
    @GET("mobileAdv!getAdv.do")
    Observable<ListResult<BannerVo>> loadhomebanner(@Query("acid") String type);

    /**
     * 用户进行登录
     */
    @GET("mobileMember!login.do")
    Observable<LoginBean> login(@Query("mobile") String mobile, @Query("password") String password);

    /**
     * 是否为首次第三登录
     */
    @GET("mobileMember!thirdLogin.do")
    Observable<LoginBean> thirdLogin(@Query("openId") String openId);

    /**
     * 用户修改密码
     */
    @GET("mobileMember!editPassword.do")
    Observable<LoginBean> editPassword(@Query("mobile") String mobile, @Query("yzm") String yzm, @Query("new_password") String new_password, @Query("member_id") int member_id, @Query("token") String token);

    /**
     * 忘记密码操作
     */
    @GET("mobileMember!retrievePassword.do")
    Observable<LoginBean> retrievePassword(@Query("mobile") String mobile, @Query("yzm") String yzm, @Query("new_password") String new_password);

    /**
     * 我的
     */
    @GET("mobileMemberCenter!personIndex.do")
    Observable<LoginBean> personIndex(@Query("member_id") int member_id, @Query("token") String token);

    /**
     * 个人中心获取用户信息
     */
    @GET("mobileMember!personCenter.do")
    Observable<LoginBean> personCenter(@Query("member_id") int member_id, @Query("token") String token);

    /**
     * 个人中心修改头像
     */
    @Multipart
    @POST("mobileMember!editFace.do")
    Observable<LoginBean> editFace(@PartMap HashMap<String, RequestBody> params);

    /**
     * 用户修改个人信息
     */
    @GET("mobileMember!updateCenter.do")
    Observable<LoginBean> updateCenter(@Query("member_id") int member_id, @Query("token") String token, @Query("operationType") int operationType, @Query("modifyContent") String modifyContent);

    /**
     * 修改密码获取验证码
     */
    @GET("mobileMember!getUpdateYzm.do")
    Observable<LoginBean> getUpdateYzm(@Query("mobile") String mobile, @Query("token") String token, @Query("member_id") int member_id);

    /**
     * 忘记密码获取验证码
     */
    @GET("mobileMember!retrieveYzm.do")
    Observable<LoginBean> retrieveYzm(@Query("mobile") String mobile);

    /**
     * 用户绑定手机号码
     */
    @GET("mobileMember!thirdAdd.do")
    Observable<LoginBean> thirdAdd(@Query("mobile") String mobile, @Query("yzm") String yzm, @Query("password") String password, @Query("openId") String openId);

    /**
     * 我的百科
     */
    @GET("mobileSectionForMy!getMyArticleList.do")
    Observable<ListResult<MyCyclopediaInfo>> loadmyclclo(@QueryMap HashMap<String, String> params);

    /**
     * 首页商品列表(5:  首页-今日推荐商品组1; 6:  首页-今日推荐商品组2,9： 首页-为您推荐商品组
     * 4：  分类-藏友热卖-热卖品 )
     */
    @GET("mobileGoods!getCommendGoods.do")
    Observable<ListResult<HomeGoodInfo>> loadGoodList(@QueryMap HashMap<String, String> params);

    /**
     * 获取首页的Tag标签
     */
    @GET("mobileDataCat!getDataCat.do")
    Observable<ListResult<CategoryVo>> loadHomeTag(@QueryMap HashMap<String, String> params);

    /**
     * 获取公告的url
     */
    @GET("mobileHtml!getAnnounceHtml.do")
    Observable<AnnounceResult> loadAnnounce();

    /**
     * 获取帮助和反馈的url
     */
    @GET("mobileHtml!getHelpCenter.do")
    Observable<AnnounceResult> loadHelp();

    /**
     * 关于我们
     *
     * @return
     */
    @GET("mobileHtml!getAboutUs.do")
    Observable<AnnounceResult> loadAboutUs();

    /**
     * 获取功能点的list
     *
     * @return
     */
    @GET("mobileIndex!getSiteMenu.do")
    Observable<ListResult<FuncPointVo>> loadFuncList();

    /**
     * 获取帖子列表
     *
     * @return
     */
    @GET("mobileNotes!getNotesList.do")
    Observable<CommentResult> loadPosts(@QueryMap HashMap<String, String> params);

    /**
     * 获取帖子统计
     *
     * @return
     */
    @GET("mobileNotes!getMyNotesCount.do")
    Observable<StatisticResult> loadStatic(@Query("member_id") String member_id, @Query("token") String token);

    /**
     * 获取周拍分类
     *
     * @return
     */
    @GET("mobileWeekpat!weekpatType.do")
    Observable<WeekAuctionTagResult> loadWeekTag();

    /**
     * 获取周拍列表
     */
    @GET("mobileWeekpat!weekpatList.do")
    Observable<WeekAuctionListResult> loadWeekShoot(@QueryMap HashMap<String, String> params);

    /**
     * 获取周拍详情的商品列表
     */
    @GET("mobileWeekpat!weekpatDetails.do")
    Observable<WeekShootDetailResult> loadWeekDetail(@Query("id") int id);

    /**
     * 获取周拍详情的竞拍人头像
     */
    @GET("mobileWeekpat!peopleList.do")
    Observable<ListResult<WeekPersonList>> loadWeekPersons(@Query("id") int id);

    /**
     * 获取周拍详情的前三名竞拍者的信息
     */
    @GET("mobileWeekpat!thirdMing.do")
    Observable<BidderResult> loadBidders(@Query("id") int id);

    /**
     * 获取商品搜索结果
     */
    @Multipart
    @POST("mobileGoods!getGoods.do")
    Observable<ListResult<HomeGoodInfo>> loadGoodSearch(@Part("keyword") RequestBody keyword,
                                                        @Part("page") RequestBody page,
                                                        @Part("pageSize") RequestBody pageSize);

    /**
     * 获取百科搜索结果
     */
    @Multipart
    @POST("mobileArticle!getArticleList.do")
    Observable<CyclopediaResult> loadCycloSearch(@Query("cat_id") int cat_id,
                                                 @Query("page") int page,
                                                 @Query("pageSize") int pageSize,
                                                 @Part("title") RequestBody keyword);

    /**
     * 获取帖子搜索结果
     */
    @Multipart
    @POST("mobileNotes!getNotesListByKeyword.do")
    Observable<CommentResult> loadPostsSearch(@Part("keyword") RequestBody keyword, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 获取百科奖励的url
     */
    @GET("mobileHtml!getBaikeActivityHtml.do")
    Observable<EntityResult<String>> loadAward();

    /**
     * 获取百科详情的url
     */
    @GET("mobileArticle!getArticleContent.do")
    Observable<AnnounceResult> loadCycloUrl(@Query("cat_id") String cat_id, @Query("article_id") String article_id);

    /**
     * 获取百科详情的评论
     */
    @GET("mobileArticle!memberCommentForArticle.do")
    Observable<PCommentResult> loadCycloComments(@Query("article_id") String ote_id,
                                                 @Query("page") int page,
                                                 @Query("pageSize") int pageSize,
                                                 @Query("commenttype") String commenttype);

    /**
     * 获取商品一级分类
     */
    @GET("mobileSearch!getCatList.do")
    Observable<CatReturnData> getCatList();

    /**
     * 获取商品筛选项
     */
    @GET("mobileGoods!getSortOptions.do")
    Observable<SortOptionsReturn> getSortOptions(@Query("cat_id") int cat_id);

    /**
     * 商品筛选结果 （1：默认排序
     * 2：销量优先 （从高到低）
     * 3：价格排序（从低到高）
     * 4：价格排序（从高到低）
     * ）
     */
    @GET("mobileGoods!selectGoods.do")
    Observable<HomeGoodResult> selectGoods(@Query("cat_id") int cat_id,
                                           @Query("sort_type") int sort_type,
                                           @Query("page") int page,
                                           @Query("pageSize") int pageSize);

    /**
     * 根据其他筛选条件查询商品
     */
    @GET("mobileGoods!selectGoods.do")
    Observable<HomeGoodResult> selectGoods(@Query("cat_id") int cat_id,
                                           @Query("sorts") String sorts,
                                           @Query("price_range") String price_range,
                                           @Query("page") int page,
                                           @Query("pageSize") int pageSize);

    /**
     * 我的收藏（商品）
     */
    @GET("mobileSectionForMy!favoriteForGoods.do")
    Observable<ListResult<GoodsItem>> favoriteForGoods(@Query("member_id") int member_id, @Query("token") String token, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 我的收藏（百科）
     */
    @GET("mobileSectionForMy!favoriteForBaike.do")
    Observable<ListResult<CyclopediaItem>> favoriteForBaike(@Query("member_id") int member_id, @Query("token") String token, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 我的收藏（帖子）
     */
    @GET("mobileSectionForMy!favoriteForNotes.do")
    Observable<PostCollectionReturn> favoriteForNotes(@Query("member_id") int member_id, @Query("token") String token, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 清空收藏记录
     */
    @GET("mobileSectionForMy!clearMyFavorite.do")
    Observable<Result> clearMyFavorite(@Query("member_id") int member_id, @Query("token") String token);

    /**
     * 清空收藏记录
     */
    @GET("mobileSectionForMy!clearMyFavorite.do")
    Observable<EntityResult<String>> clearMyFavoriteById(@Query("member_id") int member_id, @Query("token") String token, @Query("favorite_id") int favorite_id);

    /**
     * 我的积分和积分明细
     */
    @GET("mobileSectionForMy!getMyPoint.do")
    Observable<IntegralReturn> getMyPoint(@Query("member_id") int member_id, @Query("token") String token, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 我的消息
     */
    @GET("mobileSectionForMy!getMyMessage.do")
    Observable<MessageReturn> getMyMessage(@Query("member_id") int member_id, @Query("token") String token, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 清空我的消息
     */
    @GET("mobileSectionForMy!deleteAllMessage.do")
    Observable<Result> deleteMyMessage(@Query("member_id") int member_id, @Query("token") String token);

    /**
     * 清空我的消息
     */
    @GET("mobileSectionForMy!deleteMyMessage.do")
    Observable<Result> deleteMyMessageById(@Query("member_id") int member_id, @Query("token") String token, @Query("message_id") int message_id);

    /**
     * 我的电子券
     */
    @GET("mobileSectionForMy!getMyCouponList2.do")
    Observable<CouponsResult> getMyCouponList(@QueryMap HashMap<String, String> params);

    /**
     * 获取可兑换优惠券总数
     */
    @GET("mobileSectionForMy!getCanUseCouponCount.do")
    Observable<Result> getCanUseCouponCount(@Query("member_id") int member_id, @Query("token") String token);

    /**
     * 可兑换的优惠券列表
     */
    @GET("mobileSectionForMy!getCanChangeCouponList.do")
    Observable<CouponsResult> getCanChangeCouponList(@Query("member_id") int member_id, @Query("token") String token, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 兑换优惠券
     */
    @GET("mobileSectionForMy!getCouponByPoint.do")
    Observable<Result> getCouponByPoint(@Query("member_id") int member_id, @Query("token") String token, @Query("point") int point, @Query("cpns_sn") String cpns_sn, @Query("cpns_id") int cpns_id);

    /**
     * 地址管理列表
     */
    @GET("mobileMemberAddress!listAddress.do")
    Observable<AddressReturn> listAddress(@Query("member_id") int member_id, @Query("token") String token);

    /**
     * 删除地址信息
     */
    @GET("mobileMemberAddress!deleteAddress.do")
    Observable<Result> deleteAddress(@Query("member_id") int member_id, @Query("token") String token, @Query("addrId") int addrId);

    /**
     * 新增地址
     */
    @GET("mobileMemberAddress!addAddress.do")
    Observable<EntityResult<String>> addAddress(@QueryMap HashMap<String, String> params);

    /**
     * 修改地址信息
     */
    @GET("mobileMemberAddress!updateAddress.do")
    Observable<EntityResult<String>> updateAddress(@QueryMap HashMap<String, String> params);

    /**
     * 商品加入购物车
     */
    @GET("mobileCart!add.do")
    Observable<EntityResult<String>> addToCar(@QueryMap HashMap<String, String> params);

    /**
     * 获取我的评价
     */
    @GET("mobileSectionForMy!getMyCommentList.do")
    Observable<CommentReturn> getMyCommentList(@Query("member_id") int member_id, @Query("token") String token, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 获取音乐链接
     */
    @GET("mobileArticle!getVoice.do")
    Observable<MusicResourseReturn> getMusicUrl(@Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 帖子发表评论
     */
    @Multipart
    @POST("mobileNotes!pinglun.do")
    Observable<EntityResult<String>> submitComments(@Part("member_id") RequestBody member_id,
                                                    @Part("token") RequestBody token,
                                                    @Part("comContent") RequestBody comContent,
                                                    @Part("ote_id") RequestBody ote_id);

    /**
     * 点赞的统一接口
     *
     * @param params
     * @return
     */
    @GET("mobileArticle!dianZanCommon.do")
    Observable<EntityResult<String>> like(@QueryMap HashMap<String, String> params);

    /**
     * 取消订单
     */
    @GET("mobileOrder!cancelOrder.do")
    Observable<ResultCodeInt> cancelOrder(@Query("member_id") String member_id, @Query("token") String token, @Query("order_id") String order_id);

    /**
     * 进行确认收货操作
     */
    @GET("mobileOrder!confirmRecept.do")
    Observable<ResultCodeInt> confirmRecept(@Query("member_id") int member_id, @Query("token") String token, @Query("order_id") int order_id);

    /**
     * 取消退款操作
     */
    @GET("mobileOrder!cancelApplyForRefund.do")
    Observable<ResultCodeInt> cancelApplyForRefund(@Query("member_id") int member_id, @Query("token") String token, @Query("order_id") int order_id, @Query("return_order_id") int return_order_id);

    /**
     * 提醒卖家进行发货
     */
    @GET("mobileOrder!remindDelivery.do")
    Observable<ResultCodeInt> remindDelivery(@Query("member_id") int member_id, @Query("token") String token, @Query("sn") String sn);

    /**
     * 退款理由
     */
    @GET("mobileOrder!refundApplyReason.do")
    Observable<ResultCodeInt> refundApplyReason(@QueryMap HashMap<String, String> params);

    /**
     * 退货理由
     */
    @GET("mobileOrder!returnApplyReason.do")
    Observable<ResultCodeInt> returnApplyReason(@Query("member_id") int member_id, @Query("token") String token);

    /**
     * 换货理由
     */
    @GET("mobileOrder!exchangeApplyReason.do")
    Observable<ResultCodeInt> exchangeApplyReason(@Query("member_id") int member_id, @Query("token") String token);

    /**
     * 提交退款理由
     */
    @GET("mobileOrder!applyForRefund.do")
    Observable<ResultCodeInt> applyForRefund(@QueryMap HashMap<String, String> params);

    /**
     * 取消售后操作
     */
    @GET("mobileOrder!cancelAfterSale.do")
    Observable<ResultCodeInt> cancelAfterSale(@QueryMap HashMap<String, String> params);

    /**
     * 获取客服电话
     */
    @GET("mobileOrder!customerService.do")
    Observable<ResultCodeInt> customerService();

    /**
     * 提交填写的物流信息
     */
    @GET("mobileOrder!writeLogistics.do")
    Observable<ResultCodeInt> writeLogistics(@QueryMap HashMap<String, String> params);

    /**
     * 订制
     */
    @Multipart
    @POST("mobileCustomMade!addPersonCustom.do")
    Observable<EntityResult<String>> submitCustom(@PartMap HashMap<String, RequestBody> params);

    @GET("mobileNotes!deleteNote.do")
    Observable<CommonResult> deleteComment(@Query("token") String token,
                                           @Query("member_id") int member_id,
                                           @Query("ote_id") String ote_id);

    /**
     * 获取购物车列表
     */
    @GET("mobileCart!list.do")
    Observable<ShoppingCartReturn> shoppingCartInfor(@Query("memberId") int memberId, @Query("token") String token);

    /**
     * 修改购物车信息
     */
    @GET("mobileCart!update.do")
    Observable<Result> updateShoppingCart(@QueryMap HashMap<String, String> params);

    /**
     * 删除购物车商品信息
     */
    @GET("mobileCart!delete.do")
    Observable<Result> deleteShoppingCart(@QueryMap HashMap<String, String> params);

    /**
     * 我的定制(operationType------0：全部 1：定制中 2：定制完成)
     */
    @GET("mobileCustomMade!myCustomMade.do")
    Observable<CustomReturn> myCustomMade(@Query("member_id") int member_id, @Query("token") String token,
                                          @Query("page") int page, @Query("pageSize") int pageSize, @Query("operationType") int operationType);

    /**
     * 我的周拍(operationType------0：全部 1：竞拍成功 2：竞拍失败)
     */
    @GET("mobileWeekpat!myweekpat.do")
    Observable<MyWeekShootReturn> myweekpat(@QueryMap HashMap<String, String> params);

    /**
     * 删除周拍
     */
    @GET("mobileWeekpat!deleteLogs.do")
    Observable<EntityResult<String>> deleteMyWeek(@QueryMap HashMap<String, String> params);

    /**
     * 我的百科浏览记录
     */
    @GET("mobileSectionForMy!getMemberFootForBaike.do")
    Observable<ListResult<BrowsingCyclopediaDate>> getMemberFootForBaike(@Query("member_id") int member_id, @Query("token") String token, @Query("date") long page, @Query("dateSize") int dateSize);

    /**
     * 我的帖子浏览记录
     */
    @GET("mobileSectionForMy!getMemberFootForNotes.do")
    Observable<ListResult<BrowsingPostDate>> getMemberFootForNotes(@Query("member_id") int member_id, @Query("token") String token, @Query("date") long page, @Query("dateSize") int dateSize);

    /**
     * 我的商品浏览记录
     */
    @GET("mobileSectionForMy!getMemberFootForGoods.do")
    Observable<ListResult<BrowsingGoodDate>> getMemberFootForGoods(@Query("member_id") int member_id, @Query("token") String token, @Query("date") long page, @Query("dateSize") int dateSize);

    /**
     * 根据浏览id 删除浏览记录
     */
    @GET("mobileSectionForMy!clearMemberFoot.do")
    Observable<EntityResult<String>> clearMemberFootById(@QueryMap HashMap<String, String> params);

    /**
     * 清空浏览记录
     */
    @GET("mobileSectionForMy!clearMemberFoot.do")
    Observable<Result> clearMemberFootDeleAll(@QueryMap HashMap<String, String> params);

    /**
     * 正常订单的支付流程
     */
    @GET("mobileOrder!orderPay.do")
    Observable<PayForReturn> orderPay(@Query("member_id") int member_id, @Query("token") String token, @Query("cart_ids") String cart_ids);

    /**
     * 查看物流
     */
    @GET("mobileHtml!getCheckship.do")
    Observable<ResultCodeInt> getCheckship(@Query("member_id") int member_id, @Query("token") String token, @Query("order_id") int order_id);

    /**
     * 注册协议
     */
    @GET("mobileHtml!registered_agreement.do")
    Observable<ResultCodeInt> registered_agreement();

    /**
     * 关于我们
     */
    @GET("mobileHtml!getAboutUs.do")
    Observable<ResultCodeInt> getAboutUs();

    /**
     * 获取正常购买流程的支付信息
     */
    @GET("mobileOrder!orderCreate.do")
    Observable<PayKeyRetrun> orderCreate(@QueryMap HashMap<String, String> params);

    /**
     * 申请定制..周拍...下单
     */
    @GET("mobileOrder!otherOrderPay.do")
    Observable<PayForReturn> otherOrderPay(@QueryMap HashMap<String, String> params);

    /**
     * 我的订单中心--立即支付
     */
    @GET("mobileOrder!immediatePayment.do")
    Observable<PayKeyRetrun> immediatePayment(@Query("member_id") int member_id, @Query("token") String token, @Query("order_id") int order_id);

    /**
     * 获取定制的url
     */
    @GET("mobileHtml!getOfficialCustom.do")
    Observable<CustomResult> loadCustomUrl(@Query("official_id") int official_id);

    @GET("mobileWeekpat!offer.do")
    Observable<EntityResult<String>> submitBid(@QueryMap HashMap<String, String> params);

    @GET("mobileDataCat!getDataCat.do")
    Observable<ListResult<CategoryVo>> loadAllTag();

    /**
     * 获取我的分类
     *
     * @param params
     * @return
     */
    @GET("mobileDataCat!getMyDataCat.do")
    Observable<ListResult<CategoryVo>> loadMyTag(@QueryMap HashMap<String, String> params);

    @GET("mobileDataCat!saveMyDataCat.do")
    Observable<EntityResult<String>> addMyTag(@Query("token") String token,
                                              @Query("member_id") int member_id,
                                              @Query("cat_type") int cat_type,
                                              @Query("catidList") List<String> list);

    @GET("mobileHtml!getGoodsInformation.do")
    Observable<AnnounceResult> loadGoodMiddle(@Query("goods_id") int goods_id);

    /**
     * 发布百科
     */
    @Multipart
    @POST("mobileArticle!memberPublish.do")
    Observable<EntityResult<String>> postCyclopedia(@PartMap Map<String, RequestBody> params);

    /**
     * 我的订单中心
     */
    @GET("mobileOrder!orderList.do")
    Observable<OrderCenterReturn> orderList(@Query("member_id") int member_id, @Query("token") String token,
                                            @Query("optType") int optType,
                                            @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 订单详情
     */
    @GET("mobileOrder!orderDetail.do")
    Observable<OrderDetailsReturn> orderDetail(@Query("member_id") int member_id, @Query("token") String token,
                                               @Query("order_id") int order_id);

    /**
     * 我的售后中心
     */
    @GET("mobileOrder!afterSale.do")
    Observable<AfterSalesCenterReturn> afterSale(@Query("member_id") int member_id, @Query("token") String token,
                                                 @Query("page") int page, @Query("pageSize") int pageSize, @Query("operationType") int operationType);

    /**
     * 提交商品评论
     */
    @Multipart
    @POST("mobileComments!addMemberComments.do")
    Observable<ResultCodeInt> submitGoodComment(@PartMap Map<String, RequestBody> params);

    /**
     * 提交商品评论
     */
    @Multipart
    @POST("mobileArticle!publishArticleComment.do")
    Observable<EntityResult<String>> submitCycloComment(@PartMap Map<String, RequestBody> params);

    /**
     * 查看百科收藏状态
     */
    @GET("mobileArticle!favoriteStatus.do")
    Observable<EntityResult<String>> checkViewState(@Query("id") String article_id,
                                                    @Query("member_id") int member_id,
                                                    @Query("token") String token, @Query("type") int type);

    /**
     * 获取帖子点赞收藏状态
     */
    @GET("mobileNotes!getNoteStatus.do")
    Observable<PostStateResult> loadPostState(@Query("ote_id") String article_id,
                                              @Query("member_id") int member_id,
                                              @Query("token") String token);

    /**
     * 收藏状态的统一接口
     */
    @GET("mobileArticle!favoriteStatus.do")
    Observable<EntityResult<String>> loadState(@QueryMap HashMap<String, String> params);

    /**
     * 收藏的统一接口
     */
    @GET("mobileArticle!favoriteCommon.do")
    Observable<EntityResult<String>> collect(@QueryMap HashMap<String, String> params);

    /**
     * 收藏商品
     */
    @GET("mobileSectionForMy!addGoodsMemberFoot.do")
    Observable<CommonResult> BrowerGood(@Query("goods_id") String goods_id,
                                        @Query("member_id") int member_id,
                                        @Query("token") String token);

    /**
     * 帖子浏览记录
     */
    @GET("mobileSectionForMy!addNotesMemberFoot.do")
    Observable<CommonResult> BrowerPosts(@Query("oteId") String ote_id,
                                         @Query("member_id") int member_id,
                                         @Query("token") String token);

    /**
     * 百科浏览记录
     */
    @GET("mobileSectionForMy!addBaikeMemberFoot.do")
    Observable<EntityResult<String>> BrowerCyclopedia(@Query("catid") int catid,
                                                      @Query("articleid") String articleid,
                                                      @Query("member_id") int member_id,
                                                      @Query("token") String token);

    /**
     * 获取官方定制列表
     *
     * @return
     */
    @GET("mobileCustomMade!officialList.do")
    Observable<OfficialResult> loadCustomList();

    /**
     * 获取定制的url
     */
    @GET("mobileCustomMade!officialDetails.do")
    Observable<CustomGoodResult> loadCustomGoods(@Query("official_id") int official_id);

    /**
     * 商品分享链接
     *
     * @param goods_id
     * @param member_id
     * @param token
     * @return
     */
    @GET("mobileGoodsDetail!getGoodsLink.do")
    Observable<CommonResult> goodShare(@Query("goods_id") String goods_id,
                                       @Query("member_id") int member_id,
                                       @Query("token") String token);

    /**
     * 帖子分享链接
     *
     * @param ote_id
     * @param member_id
     * @param token
     * @return
     */
    @GET("mobileNotes!getNotesLink.do")
    Observable<CommonResult> postShare(@Query("ote_id") int ote_id,
                                       @Query("member_id") int member_id,
                                       @Query("token") String token);

    /**
     * 百科分享链接
     *
     * @param ote_id
     * @param article_id
     * @param member_id
     * @param token
     * @return
     */
    @GET("mobileArticle!getBaikeLink.do")
    Observable<EntityResult<String>> cycloShare(@Query("cat_id") int ote_id,
                                                @Query("article_id") String article_id,
                                                @Query("member_id") int member_id,
                                                @Query("token") String token);

    @GET("mobileMember!signStatus.do")
    Observable<EntityResult<String>> loadSignState(@Query("member_id") int member_id,
                                                   @Query("token") String token);

    /**
     * 记录分享
     *
     * @param params
     * @return
     */
    @GET("mobileArticle!share.do")
    Observable<EntityResult<String>> addUpShare(@QueryMap HashMap<String, String> params);

    /**
     * 点赞状态的统一接口
     *
     * @param params
     * @return
     */
    @GET("mobileArticle!dianZanStatus.do")
    Observable<EntityResult<String>> loadLikeState(@QueryMap HashMap<String, String> params);

    /**
     * 获取首页推荐
     */
    @GET("mobileIndex!getIndex.do")
    Observable<EntityResult<HomeRecommend>> loadHomeRecommend();

    /**
     * 商品搜索
     *
     * @param params
     * @return
     */
    @Multipart
    @POST("mobileGoods!getGoods.do")
    Observable<ListResult<HomeGoodInfo>> loadGoodByKeywood(@PartMap HashMap<String, RequestBody> params);


    /**
     * 获取商品评论数
     *
     * @param goods_id
     * @return
     */
    @GET("mobileGoodsDetail!getCommentNum.do")
    Observable<EntityResult<String>> loadGoodCommentNum(@Query("goods_id") int goods_id);

    /**
     * 相关规范
     *
     * @return
     */
    @GET("mobileHtml!guifan.do")
    Observable<EntityResult<String>> loadRule();

    /**
     * 客服
     */
    @GET("mobileKeFu!getKeFuInfoValue.do")
    Observable<EntityResult<String>> loadService(@Query("member_id") int member_id, @Query("token") String token);

    /**
     * 删除订单
     *
     * @param params
     * @return
     */
    @GET("mobileOrder!deleteOrder.do")
    Observable<EntityResult<String>> deleteOrderById(@QueryMap HashMap<String, String> params);

    /**
     * 我的收藏（百科）
     *
     * @param params
     * @return
     */
    @GET("mobileSectionForMy!favoriteForNews.do")
    Observable<ListResult<CyclopediaItem>> loadMyNews(@QueryMap HashMap<String, String> params);

    /**
     * 获取优惠券
     *
     * @param params
     * @return
     */
    @GET("mobileOrder!getOrderCouponList.do")
    Observable<MemberCouponResult> loadCoupon(@QueryMap HashMap<String, String> params);

    /**
     * 获取可兑换的优惠券
     *
     * @param params
     * @return
     */
    @GET("mobileSectionForMy!getCanChangeCouponList2.do")
    Observable<CouponsResult> getCoupon(@QueryMap HashMap<String, String> params);

    /**
     * 兑换电子券
     *
     * @param params
     * @return
     */
    @GET("mobileSectionForMy!getCouponByPoint.do")
    Observable<EntityResult<String>> changeVoucher(@QueryMap HashMap<String, String> params);

    /**
     * 取消审核百科和新闻
     *
     * @param params
     * @return
     */
    @GET("mobileSectionForMy!cancelPublishArticle.do")
    Observable<EntityResult<String>> cancleAudit(@QueryMap HashMap<String, String> params);

    /**
     * 删除我的百科和新闻
     *
     * @param params
     * @return
     */
    @GET("mobileSectionForMy!deleteMyArticle.do")
    Observable<EntityResult<String>> deleteCyclopedia(@QueryMap HashMap<String, String> params);

    /**
     * 获取周拍链接
     *
     * @param id
     * @return
     */
    @GET("mobileHtml!getWeekIntro.do")
    Observable<EntityResult<String>> loadWeekShootUrl(@Query("id") int id);

    /**
     * 获取定制详情
     *
     * @param params
     * @return
     */
    @GET("mobileCustomMade!personDetails.do")
    Observable<CustomDetailResult> loadCustomDetail(@QueryMap HashMap<String, String> params);

    /**
     * 删除定制完成
     *
     * @param params
     * @return
     */
    @GET("mobileCustomMade!deleteCustom.do")
    Observable<EntityResult<String>> deleteCustom(@QueryMap HashMap<String, String> params);

    /**
     * 我的周拍分类
     *
     * @param params
     * @return
     */
    @GET("mobileWeekpat!getWeekCat.do")
    Observable<ListResult<CategoryVo>> loadWeekShootSort(@QueryMap HashMap<String, String> params);

    /**
     * 所有周拍分类
     *
     * @return
     */
    @GET("mobileWeekpat!weekpatType.do")
    Observable<ListResult<CategoryVo>> loadAllWeekShootSort();

    @GET("mobileWeekpat!addWeekCat.do")
    Observable<EntityResult<String>> saveWeekTag(@QueryMap HashMap<String, String> params);

    /**
     * 我的周拍分类
     *
     * @param params
     * @return
     */
    @GET("mobileNotes!getMyNotesCats.do")
    Observable<ListResult<CategoryVo>> loadCircleSort(@QueryMap HashMap<String, String> params);

    /**
     * 所有周拍分类
     *
     * @return
     */
    @GET("mobileNotes!allNotesCats.do")
    Observable<ListResult<CategoryVo>> loadAllCircleSort();

    /**
     * 保存圈子标签
     *
     * @param params
     * @return
     */
    @GET("mobileNotes!saveMyNotesCats.do")
    Observable<EntityResult<String>> saveCircleTag(@QueryMap HashMap<String, String> params);

    /**
     * 获取百科内容
     *
     * @param id
     * @return
     */
    @GET("mobileArticle!getautorInfo.do")
    Observable<EntityResult<CyclopediaContent>> loadCyclopediaContent(@Query("article_id") String id);

    /**
     * 获取下载url
     *
     * @return
     */
    @GET("mobileErWeiMa!getErWeiMa.do")
    Observable<EntityResult<CodeBean>> loadCodeUrl();

    /**
     * 会员等级介绍
     *
     * @return
     */
    @GET("mobileHtml!getHuiYuanInfo.do")
    Observable<EntityResult<String>> loadMemberInfo();

    /**
     * 查看物流
     *
     * @param params
     * @return
     */
    @GET("mobileOrder!logistics.do")
    Observable<ListResult<LogisTicsBean>> loadLogistic(@QueryMap HashMap<String, String> params);

    /**
     * 我的收藏清楚----》商品
     *
     * @param params
     * @return
     */
    @GET("mobileSectionForMy!clearMyFavorite.do")
    Observable<EntityResult<String>> clearCollection(@QueryMap HashMap<String, String> params);

    /**
     * 获取商品类型
     */
    @Multipart
    @POST("MobileGoods2!getChild.do")
    Observable<GoodsTypeResult> getGoodsType(@Part("cat_id") int cat_id);

    /**
     * 获取速拍所有拍品以及指定拍品
     */
    @Multipart
    @POST("mobileGoodsList!getAllGoodsListSupai.do")
    Observable<AllGoodsResult> getAllGoods(@PartMap Map<String, RequestBody> strparams,@PartMap Map<String, Integer> intparams);

    /**
     * 速拍区我的拍品动态
     */

    @POST("mobileGoodsList!getAllGuanzhuSupai.do")
    @FormUrlEncoded
    Observable<AllGoodsResult> getSupaiDynamic(@FieldMap HashMap<String,String> params);

    /**
     * 店铺动态（品牌商和名师公用）
     */
    @POST("mobileGoodsList!getAllPinGuanzhu.do")
    @FormUrlEncoded
    Observable<DynamicResult> getStoreDynamics(@FieldMap Map<String,String> map);


    /**
     *  我的收藏(品牌和名师共用)
     * @param map
     * @return
     */
    @POST("mobileGoodsList!getAllByFavoritePinpai.do")
    @FormUrlEncoded
    Observable<DynamicResult> getCollection(@FieldMap Map<String,String> map);


    /**
     *  我的收藏(速拍)
     * @param map
     * @return
     */
    @POST("mobileGoodsList!getAllCollectSupai.do")
    @FormUrlEncoded
    Observable<AllGoodsResult> getCollectionSupai(@FieldMap Map<String,String> map);
    /**
     * 获取广告
     */
    @GET("mobileStoreAdv!advType.do")
    Observable<AdResult> getAd(@Query("type") String type);



    /**
     * 获取品牌/名师名匠 /专场新品
     */
    @Multipart
    @POST("mobileGoodsList!getAllpinpai.do")
    Observable<DynamicResult> getDynamic(@PartMap Map<String, RequestBody> strparams, @PartMap Map<String, Integer> intparams);
    /**
     * 商品区名片墙
     */
    @POST("memberFans2!findGoodsCard.do")
    @FormUrlEncoded
    Observable<CardWallInfo> getCardStore(@FieldMap Map<String,String> map);

    /**
     * 我的名片墙
     */
    @POST("memberFans2!findMyCard.do")
    @FormUrlEncoded
    Observable<CardWallInfo> getCardMine(@FieldMap Map<String,String> map);

    /**
     * 关注和取消关注
     */
    @POST("memberFans2!update.do")
    @FormUrlEncoded
    Observable<CareReturn> getCareStatus(@FieldMap Map<String,String> map);

    /**
     * 获取专场
     */
    @Multipart
    @POST("mobileSpeSection!findSpecialList.do")
    Observable<AllSpecialResult> getSpecial(@PartMap Map<String, RequestBody> strparams, @PartMap Map<String, Integer> intparams);

    /**
     * 获取专场介绍
     * @param
     * @param
     * @return
     */
    @Multipart
    @POST("mobileSpeSection!getSpecialDetail.do")
    Observable<SpecialIntroduceResult> getSpecialIntroduce(@Part("special_id") int special_id);

    /**
     *商家申请
     * @param params
     * @return
     */
    @Multipart
    @POST("mobileStore!applyStore.do")
    Observable<ApplyReturn> getApplyResult(@PartMap HashMap<String,RequestBody> params);

    /**
     * 获取省市地区
     * @param
     * @return
     */

    @POST("mobileRegion!getAllRegion.do")
    @FormUrlEncoded
    Observable<AddresBean> getRegion(@Field("parent_id") int parent_id);

    /**
     * 商品一级品类表
     * @param parent_id
     * @return
     */
    @POST("mobileGoods2!getCatList.do")
    @FormUrlEncoded
    Observable<CatListBean> getFirstList(@Field("id") int parent_id);

    /**
     * 申请状态
     * @param map
     * @return
     */
    @POST("mobileStore!getApplyStatus.do")
        @FormUrlEncoded
    Observable<ApplyStatusReturn> getApplyStatus(@FieldMap Map<String,String> map);

    /**
     * 获取发布商品状态
     * @param
     * @return
     */

    @Multipart
    @POST("MobileGoods2!add.do")
    Observable<ApplyReturn> getPublishStataus(@PartMap HashMap<String,RequestBody> params);

    /**
     * 商品规格
     * @param parent_id
     * @return
     */
    @POST("mobileGoods2!getCatList.do")
    @FormUrlEncoded
    Observable<SizeColorInfo> getSize(@Field("id") int parent_id);

}
