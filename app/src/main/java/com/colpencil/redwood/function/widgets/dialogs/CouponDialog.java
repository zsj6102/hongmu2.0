package com.colpencil.redwood.function.widgets.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.MemberCoupon;
import com.colpencil.redwood.view.adapters.VoucherDialogAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈 宝
 * @Description:电子券的dialog
 * @Email 1041121352@qq.com
 * @date 2016/10/17
 */
public class CouponDialog extends Dialog {

    private Context context;
    private GridView gridView;
    private VoucherDialogAdapter adapter;
    private List<MemberCoupon> mList = new ArrayList<>();
    private VoucherClickListener listener;

    public CouponDialog(Context context) {
        super(context, R.style.PostDialogTheme);
        this.context = context;
        initViews();
        initWindow();
        setCanceledOnTouchOutside(false);
    }

    private void initViews() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_vouchers, null);
        setContentView(view);
        view.findViewById(R.id.dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.cancleClick();
            }
        });
        gridView = (GridView) view.findViewById(R.id.dialog_gridview);
        ((TextView) (view.findViewById(R.id.dialog_title))).setText("请选择优惠券");
        view.findViewById(R.id.dialog_chooseNum).setVisibility(View.GONE);
        adapter = new VoucherDialogAdapter(context, mList, R.layout.item_voucher);
        gridView.setAdapter(adapter);
        view.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.sureClick();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < mList.size(); i++) {
                    if (i == position) {
                        if (mList.get(i).isChoose()) {
                            mList.get(i).setChoose(false);
                        } else {
                            mList.get(i).setChoose(true);
                        }
                    } else {
                        mList.get(i).setChoose(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initWindow() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        lp.width = (int) (dm.widthPixels * 0.9);
        lp.height = (int) (dm.heightPixels * 0.7);
        window.setAttributes(lp);
    }

    public void setData(List<MemberCoupon> list) {
        mList.clear();
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void setListener(VoucherClickListener listener) {
        this.listener = listener;
    }

    public interface VoucherClickListener {
        void sureClick();

        void cancleClick();
    }
}
