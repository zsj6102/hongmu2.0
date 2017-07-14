package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Address;
import com.colpencil.redwood.view.activity.mine.AddAdressActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;

import java.util.List;

/**
 * @author 曾 凤
 * @Description: 地址适配器
 * @Email 20000263@qq.com
 * @date 2016/8/2
 */
public class ReceiptAddressAdapter extends CommonAdapter<Address> {

    public ReceiptAddressAdapter(Context context, List<Address> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, final Address address, final int position) {
        holder.setText(R.id.tv_receipt_name, address.getName());
        holder.setText(R.id.tv_receipt_phone, address.getMobile());
        if (address.getDef_addr() == 1) {
            holder.getView(R.id.tv_DefState).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.tv_DefState).setVisibility(View.GONE);
        }
        String addressMsg = "";
        if (!TextStringUtils.isEmpty(address.getProvince())) {
            addressMsg = address.getProvince();
        }
        if (!TextStringUtils.isEmpty(address.getCity())) {
            addressMsg = addressMsg + "-" + address.getCity();
        }
        if (!TextStringUtils.isEmpty(address.getRegion())) {
            addressMsg = addressMsg + "-" + address.getRegion();
        }
        holder.setText(R.id.tv_receipt_context, addressMsg + "-" + address.getAddress());
        holder.getView(R.id.edit_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddAdressActivity.class);
                intent.putExtra("key", "修改收货地址");
                intent.putExtra("addressId", address.getAddrId());
                intent.putExtra("province", address.getProvince());
                intent.putExtra("city", address.getCity());
                intent.putExtra("region", address.getRegion());
                intent.putExtra("address", address.getAddress());
                intent.putExtra("name", address.getName());
                intent.putExtra("mobile", address.getMobile());
                intent.putExtra("def_addr", address.getDef_addr());
                intent.putExtra("zip", address.getZip());
                mContext.startActivity(intent);
            }
        });
    }
}
