package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.colpencil.redwood.R;
import com.colpencil.redwood.base.PhotoBaseActivity;
import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.BrandApplyInfo;
import com.colpencil.redwood.bean.CatBean;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.PostTypeInfo;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CategoryDialog;
import com.colpencil.redwood.function.widgets.dialogs.SelectPlaceDialog;
import com.colpencil.redwood.present.mine.ApplyPresenter;
import com.colpencil.redwood.services.BrandApplyService;
import com.colpencil.redwood.services.PersonApplyService;
import com.colpencil.redwood.view.impl.ApplayView;
import com.jph.takephoto.model.TResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.colpencil.redwood.R.id.edt_store_name;
import static com.colpencil.redwood.R.id.tv_city;
import static com.unionpay.mobile.android.global.a.B;
import static com.unionpay.mobile.android.global.a.v;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_brand_apply)
public class BrandApplyActivity extends PhotoBaseActivity implements View.OnClickListener, ApplayView {

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    //身份证正面
    @Bind(R.id.iv_positive)
    ImageView iv_positive;
    //身份证反面
    @Bind(R.id.iv_negative)
    ImageView iv_negative;
    //营业执照
    @Bind(R.id.iv_license)
    ImageView iv_license;
    @Bind(R.id.layout_region)
    LinearLayout layoutRegion;
    @Bind(R.id.layout_tv_biz_type)
    LinearLayout layoutTvBizType;
    @Bind(R.id.tv_biz_type)
    TextView tvBizType;
    @Bind(tv_city)
    TextView tvCity;
    @Bind(R.id.edt_name)
    EditText edtName;
    @Bind(R.id.id_number)
    EditText idNumber;
    @Bind(R.id.edt_address)
    EditText edtAddress;
    @Bind(R.id.bankcard_number)
    EditText bankcardNumber;
    @Bind(R.id.bankcard_type)
    EditText bankcardType;
    @Bind(R.id.edt_cardholder)
    EditText edtCardholder;
    @Bind(R.id.edt_store_name)
    EditText edtStoreName;
    @Bind(R.id.edt_license)
    EditText edtLicense;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    private AddresBean bean;
    private ApplyPresenter presenter;
    private String sec_id = "";
    private CategoryDialog dialog;
    private int touch = 0;
    /**
     * 0表示身份证正面，1表示身份证反面，2表示营业执照
     */
    private int type = 0;
    private List<PostTypeInfo> list = new ArrayList<>();
    String s = "";
    private String photoZ; //身份证正面
    private String photoF; //身份证反面
    private String photoL;//营业执照
    @Override
    protected void initViews(View view) {
        tv_title.setText("品牌商家申请");
        iv_back.setOnClickListener(this);
        iv_positive.setOnClickListener(this);
        iv_negative.setOnClickListener(this);
        iv_license.setOnClickListener(this);
        layoutRegion.setOnClickListener(this);
        layoutTvBizType.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new ApplyPresenter();
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        presenter.loadRegion(0);
        presenter.loadCatList(0);
        ButterKnife.bind(this);
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (type == 0) {
            Glide.with(this).load(result.getImages().get(0).getCompressPath()).into(iv_positive);
            photoZ = result.getImages().get(0).getCompressPath();
        } else if (type == 1) {
            Glide.with(this).load(result.getImages().get(0).getCompressPath()).into(iv_negative);
            photoF = result.getImages().get(0).getCompressPath();
        } else {
            Glide.with(this).load(result.getImages().get(0).getCompressPath()).into(iv_license);
            photoL = result.getImages().get(0).getCompressPath();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                Intent intent = new Intent(BrandApplyActivity.this,BusinessActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_positive:
                type = 0;
                openSelect(false, 0);
                break;
            case R.id.iv_negative:
                type = 1;
                openSelect(false, 1);
                break;
            case R.id.iv_license:
                type = 2;
                openSelect(false, 2);
                break;
            case R.id.layout_tv_biz_type:
                if (dialog == null) {
                    dialog = new CategoryDialog(BrandApplyActivity.this, R.style.PostDialogTheme, list);
                }
                dialog.setTitle("请选择经营品类");
                dialog.setListener(new CategoryDialog.PostClickListener() {
                    @Override
                    public void closeClick() {
                        dialog.dismiss();
                    }

                    @Override
                    public void itemUnClick() {
                        tvBizType.setText("请选择经营品类");
                        sec_id = "";
                    }

                    @Override
                    public void itemClick(List<Integer> position) {
                        for (int i = 0; i < position.size(); i++) {
                            if (i != position.size() - 1) {
                                s = s + list.get(position.get(i)).getTypename() + ",";
                                sec_id = sec_id + list.get(position.get(i)).getSec_id() + ",";
                            } else {
                                s = s + list.get(position.get(i)).getTypename();
                                sec_id = sec_id + list.get(position.get(i)).getSec_id();
                            }
                        }
                        tvBizType.setText(s);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.layout_region:
                if (bean != null && bean.getData().size() != 0) {
                    new SelectPlaceDialog(this, tvCity, bean, 0).show();
                } else {
                    touch = 1;
                    showLoading("加载中");
                }
                break;
            case R.id.tv_submit:
                submit();
                break;
        }

    }

    private void submit(){
        if (TextStringUtils.isEmpty(edtName.getText().toString())) {
            Toast.makeText(BrandApplyActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(idNumber.getText().toString())) {
            Toast.makeText(BrandApplyActivity.this, "身份证号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(photoZ)) {
            Toast.makeText(BrandApplyActivity.this, "请上传身份证正面照", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(photoF)) {
            Toast.makeText(BrandApplyActivity.this, "请上传身份证反面照", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tvCity.getText().toString().equals("请点击选择所在的区域")) {
            Toast.makeText(BrandApplyActivity.this, "请选择所在的区域", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(edtAddress.getText().toString())) {
            Toast.makeText(BrandApplyActivity.this, "请输入详细地址", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tvBizType.getText().toString().equals("请点击选择经营品类") || sec_id.equals("")) {
            Toast.makeText(BrandApplyActivity.this, "请选择经营品类", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(bankcardNumber.getText().toString())) {
            Toast.makeText(BrandApplyActivity.this, "请填写银行卡号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(bankcardType.getText().toString())) {
            Toast.makeText(BrandApplyActivity.this, "请填写卡类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(edtCardholder.getText().toString())) {
            Toast.makeText(BrandApplyActivity.this, "请填写持卡人", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(photoF)) {
            Toast.makeText(BrandApplyActivity.this, "请上传营业执照", Toast.LENGTH_SHORT).show();
            return;
        }
        BrandApplyInfo info = new BrandApplyInfo();
        info.setStore_type(2 + "");
        info.setReal_name(edtName.getText().toString());
        info.setId_img(new File(photoZ));
        info.setId_img_opposite(new File(photoF));
        info.setLicense_img(new File(photoL));
        info.setLicense(edtLicense.getText().toString());
        info.setPhysical_store_name(edtStoreName.getText().toString());
        info.setRegion_id(SharedPreferencesUtil.getInstance(this).getInt("region_id0") + "");
        info.setAddress(edtAddress.getText().toString());
        info.setBiz_type(sec_id);
        info.setCard(bankcardNumber.getText().toString());
        info.setCard_type(bankcardType.getText().toString());
        info.setCardholder(edtCardholder.getText().toString());
        info.setId_number(idNumber.getText().toString());
        Intent intent = new Intent(BrandApplyActivity.this, BrandApplyService.class);
        intent.putExtra("data", info);
        startService(intent);
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                hideLoading();
                Intent mIntent = new Intent(BrandApplyActivity.this, BusinessActivity.class);
                startActivity(mIntent);
                BrandApplyActivity.this.finish();
            }
        };
        timer.start();
    }
    @Override
    public void applyError(String message) {
        Toast.makeText(this,"网络错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void load(AddresBean regionInfo) {
        hideLoading();
        bean = regionInfo;
        if (touch == 1) {
            new SelectPlaceDialog(this, tvCity, bean, 0).show();
        }
    }

    @Override
    public void loadCat(CatListBean catListBean) {
        if (!ListUtils.listIsNullOrEmpty(catListBean.getData())) {
            list.clear();
            for (CatBean vo : catListBean.getData()) {
                PostTypeInfo info = new PostTypeInfo();
                info.setTypename(vo.getCat_name());
                info.setSec_id(String.valueOf(vo.getCat_id()));
                info.setChoose(false);
                list.add(info);
            }
        }
    }
}
