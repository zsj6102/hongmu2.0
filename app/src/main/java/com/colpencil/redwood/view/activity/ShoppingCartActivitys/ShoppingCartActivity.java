package com.colpencil.redwood.view.activity.ShoppingCartActivitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CartItem;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.AllCartList;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.function.widgets.swipe.SwipeListView;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenu;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenuCreator;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenuItem;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.ShoppingCartPresenser;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.ShoppingCartAdapter;
import com.colpencil.redwood.view.impl.IShoppingCartView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.FormatUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * 描述：购物车
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/14 09 57
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_shoppingcart
)
public class ShoppingCartActivity extends ColpencilActivity implements IShoppingCartView {
    @Override
    public void loadNewCartData(AllCartList data) {

    }

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.tv_shoppingCartFinish)
    TextView tv_right;
    @Bind(R.id.listView_shoppingCart)
    SwipeListView listView;
    @Bind(R.id.shoppingCart_delete)
    LinearLayout ll_delete;
    @Bind(R.id.shoppingCart_buy)
    LinearLayout ll_purchase;
    @Bind(R.id.relativeLayout_have)
    RelativeLayout rl_content;
    @Bind(R.id.ll_empty)
    LinearLayout ll_empty;
    @Bind(R.id.shoppingCart_buyImg)
    ImageView iv_purchaseAll;
    @Bind(R.id.shoppingCart_deleteImg)
    ImageView iv_deleteAll;
    @Bind(R.id.shoppingCart_buyPrice)
    TextView tv_purchasePrice;
    @Bind(R.id.shoppingCart_buySumbit)
    TextView tv_purchaseSubmit;
    @Bind(R.id.shoppingCart_deleteSumbit)
    TextView tv_deleteSubmit;

    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    private ShoppingCartPresenser present;
    private List<CartItem> mdatas = new ArrayList<>();  //购物车的集合
    private ShoppingCartAdapter adapter;   //购物车的适配器
    private boolean isShowPurchase = true;
    private int count;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        tv_title.setText("购物车");
        tv_right.setText("编辑");
        initSwipe();
        initBus();
    }

    private void initSwipe() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem item = new SwipeMenuItem(ShoppingCartActivity.this);
                item.setBackground(R.color.main_red);
                item.setTitle("删除");
                item.setTitleColor(Color.WHITE);
                item.setTitleSize(14);
                menu.addMenuItem(item);
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                showLoading(Constants.progressName);
                present.deleteShoppingCart(mdatas.get(position).getCatid() + "");
                return false;
            }
        });
        adapter = new ShoppingCartAdapter(this, mdatas, R.layout.item_shoppingcart);
        listView.setAdapter(adapter);
        present.loadShoppingCartData();
    }

    private void initBus() {
        observable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 1) {
                    int position = msg.getPositonGroupMsg();
                    if ("reduce".equals(msg.getMsg())) {    //减少
                        if (mdatas.get(position).getNum() > 0) {
                            mdatas.get(position).setNum(mdatas.get(position).getNum() - 1);
                        }
                        present.changeProductInfor(mdatas.get(position));
                        adapter.notifyDataSetChanged();
                        changeState();
                    } else if ("add".equals(msg.getMsg())) {   //增加
                        mdatas.get(position).setNum(mdatas.get(position).getNum() + 1);
                        present.changeProductInfor(mdatas.get(position));
                        adapter.notifyDataSetChanged();
                        changeState();
                    } else {    //选中
                        changeCount(position);
                    }
                } else if (msg.getType() == 57) {
                    present.loadShoppingCartData();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new ShoppingCartPresenser();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void loadShoppingCartData(List<CartItem> datas) {
        hideLoading();
        if (ListUtils.listIsNullOrEmpty(datas)) {
            ll_empty.setVisibility(View.VISIBLE);
            rl_content.setVisibility(View.GONE);
            tv_right.setVisibility(View.GONE);
            ll_delete.setVisibility(View.VISIBLE);
        } else {
            ll_empty.setVisibility(View.GONE);
            rl_content.setVisibility(View.VISIBLE);
            tv_right.setVisibility(View.VISIBLE);
            mdatas.clear();
            mdatas.addAll(datas);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void reLoadData(String msg) {
        count = 0;
        iv_purchaseAll.setImageResource(R.mipmap.select_no);
        iv_deleteAll.setImageResource(R.mipmap.select_no);
        //切换到购买状态
        ll_purchase.setVisibility(View.VISIBLE);
        ll_delete.setVisibility(View.GONE);
        tv_right.setText("编辑");
        tv_purchasePrice.setText("￥" + 0.00);
        //重新加载数据
        showLoading(Constants.progressName);
        present.loadShoppingCartData();
    }

    @Override
    public void deletefail(String code, String msg) {
        ToastTools.showShort(this, msg);
    }

    @Override
    public void loadFail(String code, String msg) {
        if (code.equals("3")) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(StringConfig.REQUEST_CODE, 100);
            startActivityForResult(intent, Constants.REQUEST_LOGIN);
        } else {
            ll_empty.setVisibility(View.VISIBLE);
            rl_content.setVisibility(View.GONE);
            tv_right.setVisibility(View.GONE);
            ToastTools.showShort(this, msg);
        }
    }

    /**
     * 显示弹出框
     *
     * @param content
     * @param sure
     * @param cancle
     * @param cartIds
     */
    private void showDialog(String content, String sure, String cancle, final String cartIds) {
        final CommonDialog dialog = new CommonDialog(this, content, sure, cancle);
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                showLoading(Constants.progressName);
                present.deleteShoppingCart(cartIds);
                dialog.dismiss();
            }

            @Override
            public void cancleOnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    /**
     * 编辑
     */
    @OnClick(R.id.tv_shoppingCartFinish)
    void editClick() {
        if (isShowPurchase) {
            isShowPurchase = false;
            ll_purchase.setVisibility(View.GONE);
            ll_delete.setVisibility(View.VISIBLE);
            tv_right.setText("完成");
        } else {
            isShowPurchase = true;
            ll_purchase.setVisibility(View.VISIBLE);
            ll_delete.setVisibility(View.GONE);
            tv_right.setText("编辑");
        }
    }

    /**
     * 删除
     */
    @OnClick(R.id.shoppingCart_deleteSumbit)
    void deleteClick() {
        String cartIds = "";
        for (int i = 0; i < mdatas.size(); i++) {
            if (mdatas.get(i).isChooseState() == true) {
                cartIds = cartIds + mdatas.get(i).getCatid() + ",";
            }
        }
        if (!cartIds.isEmpty()) {
            cartIds = cartIds.substring(0, cartIds.length() - 1);
            showDialog("确认要删除吗?", "确认", "取消", cartIds);
        }
    }

    /**
     * 提交订单
     */
    @OnClick(R.id.shoppingCart_buySumbit)
    void submitClick() {
        String cartIds = "";
        for (int i = 0; i < mdatas.size(); i++) {
            if (mdatas.get(i).isChooseState() == true) {
                cartIds = cartIds + mdatas.get(i).getCatid() + ",";
            }
        }
        if (!cartIds.isEmpty()) {
            Intent intent = new Intent(ShoppingCartActivity.this, OrderActivity.class);
            intent.putExtra("key", "订单确定");
            cartIds = cartIds.substring(0, cartIds.length() - 1);
            intent.putExtra("cartIds", cartIds);
            intent.putExtra("goFrom", "ShoppingCartActivity");
            startActivity(intent);
        }
    }

    /**
     * 返回首页
     */
    @OnClick(R.id.tv_empty)
    void toHomeClick() {
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(37);
        RxBus.get().post("rxBusMsg", rxBusMsg);
        finish();
    }

    /**
     * 选中全部
     */
    @OnClick({R.id.shoppingCart_buySelectAll, R.id.shoppingCart_deleteSelectAll})
    void purchaseAll() {
        int size = 0;
        float price = 0;
        for (int i = 0; i < mdatas.size(); i++) {
            if (mdatas.get(i).isChooseState()) {
                size++;
            }
            price += mdatas.get(i).getPrice();
        }
        if (size == mdatas.size()) {    //全选状态,要改成未选中
            for (int i = 0; i < mdatas.size(); i++) {
                mdatas.get(i).setChooseState(false);
            }
            iv_purchaseAll.setImageResource(R.mipmap.select_no);
            iv_deleteAll.setImageResource(R.mipmap.select_no);
            tv_purchasePrice.setText("￥" + 0.00);
            tv_purchaseSubmit.setBackgroundResource(R.drawable.gary_solid_shape);
            tv_deleteSubmit.setBackgroundResource(R.drawable.gary_solid_shape);
            count = 0;
        } else {   //未全选状态,要改成全选
            for (CartItem item : mdatas) {
                item.setChooseState(true);
            }
            count = mdatas.size();
            iv_purchaseAll.setImageResource(R.mipmap.select_yes_red);
            iv_deleteAll.setImageResource(R.mipmap.select_yes_red);
            tv_purchaseSubmit.setBackgroundResource(R.drawable.red_solid_shape);
            tv_deleteSubmit.setBackgroundResource(R.drawable.red_solid_shape);
            float prices = 0.00f;
            for (int i = 0; i < mdatas.size(); i++) {
                CartItem item = mdatas.get(i);
                if (item.isChooseState()) {
                    prices += item.getPrice() * item.getNum();
                }
            }
            tv_purchasePrice.setText("￥" + FormatUtils.formatDouble(prices));
        }
        adapter.notifyDataSetChanged();
    }

    private void changeCount(int position) {
        if (mdatas.get(position).isChooseState()) {
            count++;
        } else {
            count--;
        }
        changeState();
    }

    private void changeState() {
        if (count == mdatas.size()) {
            iv_purchaseAll.setImageResource(R.mipmap.select_yes_red);
            iv_deleteAll.setImageResource(R.mipmap.select_yes_red);
        } else {
            iv_purchaseAll.setImageResource(R.mipmap.select_no);
            iv_deleteAll.setImageResource(R.mipmap.select_no);
        }
        float price = 0;
        for (int i = 0; i < mdatas.size(); i++) {
            CartItem item = mdatas.get(i);
            if (item.isChooseState()) {
                price += item.getPrice() * item.getNum();
            }
        }
        tv_purchasePrice.setText("￥" + FormatUtils.formatDouble(price));
        if (count == 0) {
            tv_purchaseSubmit.setBackgroundResource(R.drawable.gary_solid_shape);
            tv_deleteSubmit.setBackgroundResource(R.drawable.gary_solid_shape);
        } else {
            tv_purchaseSubmit.setBackgroundResource(R.drawable.red_solid_shape);
            tv_deleteSubmit.setBackgroundResource(R.drawable.red_solid_shape);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {
            present.loadShoppingCartData();
        } else {
            finish();
        }
    }
}
