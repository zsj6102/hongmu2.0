package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CartItem;
import com.colpencil.redwood.bean.result.CartList;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;

import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ImgTool;

import java.util.ArrayList;
import java.util.List;

import static com.colpencil.redwood.R.id.iv_shoppingCartSelect;
import static com.colpencil.redwood.R.id.ll_good;


public class NewCartListAdapter extends BaseAdapter {
    private static final int TYPE_ITEM = 0;  //
    private static final int TYPE_SEPARATOR = 1; //时间分割
    private List<CartList> mlist = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private comItemClickListener listener;

    public NewCartListAdapter(Context context, List<CartList> list) {
        this.mContext = context;
        this.mlist = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int count = 0;
        if (null != mlist) {
            for (CartList category : mlist) {
                count += category.getItemCount();

            }
        }
        return count;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (null == mlist || position < 0 || position > getCount()) {
            return TYPE_ITEM;
        }
        int categoryFirstIndex = 0;
        for (CartList category : mlist) {
            int size = category.getItemCount();
            int categoryIndex = position - categoryFirstIndex;
            if (categoryIndex == 0) {
                return TYPE_SEPARATOR;
            }
            categoryFirstIndex += size;
        }
        return TYPE_ITEM;
    }

    @Override
    public Object getItem(int position) {
        if (null == mlist || position < 0 || position > getCount()) {
            return null;
        }
        int categoryFirstIndex = 0;
        for (CartList category : mlist) {
            int size = category.getItemCount();
            int categoryIndex = position - categoryFirstIndex;
            if (categoryIndex < size) {
                return category.getItem(categoryIndex);
            }
            categoryFirstIndex += size;
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setComListener(comItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        int type = getItemViewType(position);

        switch (type) {

            case TYPE_SEPARATOR:
                if (null == view) {
                    view = mInflater.inflate(R.layout.cart_group, null);
                }
                TextView textView = (TextView) view.findViewById(R.id.tv_time);
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_groupCartSelect);
                for (CartList category : mlist) {
                    if (category.getStore_name().equals(getItem(position))){
                        boolean isAll = true;
                        for(int i = 0; i < category.getGoodslist().size();i++){
                            if(category.getGoodslist().get(i).isChooseState()){
                                isAll = true && isAll;
                            }else{
                                isAll = false;
                                break;
                            }
                        }
                        category.setAllChoose(isAll);
                    }
                }
                for (CartList category : mlist) {
                    if (category.getStore_name().equals(getItem(position))) {
                        if (category.isAllChoose()) {
                            imageView.setImageResource(R.mipmap.select_yes_red);
                        } else {
                            imageView.setImageResource(R.mipmap.select_no);
                        }
                    }
                }
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (CartList category : mlist) {
                            if (category.getStore_name().equals(getItem(position))) {
                                if (category.isAllChoose()) {
                                    category.setAllChoose(false);
                                } else {
                                    category.setAllChoose(true);
                                }
                            }
                        }
                        notifyDataSetChanged();
                        listener.titleClick(position);
                    }
                });

                textView.setText( (String)getItem(position));
                break;
            case TYPE_ITEM:
                ViewHolder holder = null;
                if (null == view) {
                    view = mInflater.inflate(R.layout.item_shoppingcart, null);
                    holder = new ViewHolder();
                    holder.line_view = view.findViewById(R.id.line_view);
                    holder.item_shoppingcart_count = (TextView) view.findViewById(R.id.item_shoppingcart_count);
                    holder.iv_shoppingCartTitle = (TextView) view.findViewById(R.id.iv_shoppingCartTitle);
                    holder.iv_shoppingCartExplain = (TextView) view.findViewById(R.id.iv_shoppingCartExplain);
                    holder.iv_shoppingCartPrice = (TextView) view.findViewById(R.id.iv_shoppingCartPrice);
                    holder.item_shoppingcart_reduce = (TextView) view.findViewById(R.id.item_shoppingcart_reduce);
                    holder.item_shoppingcart_add = (TextView) view.findViewById(R.id.item_shoppingcart_add);
                    holder.iv_shoppingCartImg = (ImageView) view.findViewById(R.id.iv_shoppingCartImg);
                    holder.iv_shoppingCartSelect = (ImageView) view.findViewById(iv_shoppingCartSelect);
                    holder.ll_good = (LinearLayout) view.findViewById(ll_good);
                    view.setTag(holder);
                } else {
                    holder = (ViewHolder) view.getTag();
                }
                holder.item_shoppingcart_count.setText(((CartItem) getItem(position)).getNum() + "");
                holder.iv_shoppingCartTitle.setText(((CartItem) getItem(position)).getName() + "");
                holder.iv_shoppingCartExplain.setText(((CartItem) getItem(position)).getSpecs() + "");
                holder.iv_shoppingCartPrice.setText(("￥" + ((CartItem) getItem(position)).getPrice()));
                if (((CartItem) getItem(position)).isChooseState()) {
                    holder.iv_shoppingCartSelect.setImageResource(R.mipmap.select_yes_red);
                } else {
                    holder.iv_shoppingCartSelect.setImageResource(R.mipmap.select_no);
                }
                if (((CartItem) getItem(position)).getNum() <=1) {
                    holder.item_shoppingcart_reduce.setClickable(false);
                } else {
                    holder.item_shoppingcart_reduce.setClickable(true);
                    holder.item_shoppingcart_reduce.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listener.click("reduce", position);
                        }
                    });
                }
                holder.ll_good.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, GoodDetailActivity.class);
                        intent.putExtra("goodsId", ((CartItem) getItem(position)).getGoods_id());
                        mContext.startActivity(intent);
                    }
                });
                holder.item_shoppingcart_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.click("add", position);
                    }
                });
                holder.iv_shoppingCartSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (((CartItem) getItem(position)).isChooseState()) {
                            ((CartItem) getItem(position)).setChooseState(false);
                            for (CartList category : mlist) {
                                if (category.getGoodslist().contains((CartItem) getItem(position))) {
                                        category.setAllChoose(false);
                                }
                            }
                        } else {
                            ((CartItem) getItem(position)).setChooseState(true);
                        }

                        listener.click("choose", position);
                        notifyDataSetChanged();
                    }
                });
                ImgTool.getImgToolInstance(mContext).loadImgByString(((CartItem) getItem(position)).getImage_default(), holder.iv_shoppingCartImg);
                break;
        }
        return view;
    }

    public interface comItemClickListener {
        void click(String fuc, int position);

        void titleClick(int position);
    }

    class ViewHolder {
        View line_view;
        TextView item_shoppingcart_count;
        TextView iv_shoppingCartTitle;
        TextView iv_shoppingCartExplain;
        TextView iv_shoppingCartPrice;
        ImageView iv_shoppingCartImg;
        ImageView iv_shoppingCartSelect;
        TextView item_shoppingcart_reduce;
        TextView item_shoppingcart_add;
        LinearLayout ll_good;
    }
}
