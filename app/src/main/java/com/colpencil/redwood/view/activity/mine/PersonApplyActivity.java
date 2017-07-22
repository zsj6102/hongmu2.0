package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.colpencil.redwood.R;
import com.colpencil.redwood.base.PhotoBaseActivity;
import com.colpencil.redwood.bean.AddresBean;

import com.colpencil.redwood.bean.CatBean;
import com.colpencil.redwood.bean.CatListBean;

import com.colpencil.redwood.bean.PersonApplyInfo;
import com.colpencil.redwood.bean.PostTypeInfo;

import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CategoryDialog;
import com.colpencil.redwood.function.widgets.dialogs.SelectPlaceDialog;
import com.colpencil.redwood.present.mine.ApplyPresenter;

import com.colpencil.redwood.services.PersonApplyService;
import com.colpencil.redwood.view.impl.ApplayView;
import com.jph.takephoto.model.TResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.text.TextUtils.concat;
import static android.transition.Fade.IN;
import static com.colpencil.redwood.R.id.tv_biz_type;
import static com.umeng.socialize.utils.DeviceConfig.context;
import static com.unionpay.mobile.android.global.a.C;
import static com.unionpay.mobile.android.global.a.I;
import static com.unionpay.mobile.android.global.a.s;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_person_business)
public class PersonApplyActivity extends PhotoBaseActivity implements View.OnClickListener, ApplayView {

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.iv_positive)
    ImageView iv_positive;

    @Bind(R.id.iv_negative)
    ImageView iv_negative;
    @Bind(R.id.tv_city)
    TextView tv_city;
    @Bind(R.id.tv_assist_title)
    TextView tvAssistTitle;
    @Bind(R.id.tv_shoppingCartFinish)
    TextView tvShoppingCartFinish;
    @Bind(R.id.base_header_ll)
    LinearLayout baseHeaderLl;
    @Bind(R.id.base_header_layout)
    RelativeLayout baseHeaderLayout;
    @Bind(R.id.toolbar)
    LinearLayout toolbar;
    @Bind(R.id.edt_name)
    EditText edtName;
    @Bind(R.id.id_number)
    EditText idNumber;
    @Bind(R.id.edt_address)
    EditText edtAddress;
    @Bind(tv_biz_type)
    TextView tvBizType;
    @Bind(R.id.bankcard_number)
    EditText bankcardNumber;
    @Bind(R.id.bankcard_type)
    EditText bankcardType;
    @Bind(R.id.edt_cardholder)
    EditText edtCardholder;
    @Bind(R.id.tv_commit)
    TextView tvCommit;

    private int type = 0;
    private String photoZ; //身份证正面
    private String photoF; //身份证反面
    private ApplyPresenter presenter;

    private AddresBean bean;
    private int touch = 0;
    private String sec_id = "";
    private CategoryDialog dialog;
    String s = "";
    private List<PostTypeInfo> list = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        tv_title.setText("个人商家申请");

        iv_back.setOnClickListener(this);
        iv_positive.setOnClickListener(this);
        iv_negative.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        tvCommit.setOnClickListener(this);
        tvBizType.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new ApplyPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            Intent intent = new Intent(PersonApplyActivity.this,BusinessActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.iv_positive) {
            type = 0;
            openSelect(false, 1);
        } else if (id == R.id.iv_negative) {
            type = 1;
            openSelect(false, 1);
        } else if (id == R.id.tv_city) {
            if (bean != null && bean.getData().size() != 0) {
                new SelectPlaceDialog(this, tv_city, bean,0).show();
            } else {
                touch = 1;
                showLoading("加载中");
            }

        } else if (id == R.id.tv_commit) {
            commit();
        } else if (id == R.id.tv_biz_type) {
            if (dialog == null) {
                dialog = new CategoryDialog(PersonApplyActivity.this, R.style.PostDialogTheme, list);
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
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (type == 0) {
            Glide.with(this).load(result.getImages().get(0).getCompressPath()).into(iv_positive);
            photoZ = result.getImages().get(0).getCompressPath();
        } else {
            Glide.with(this).load(result.getImages().get(0).getCompressPath()).into(iv_negative);
            photoF = result.getImages().get(0).getCompressPath();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        presenter.loadRegion(0);
        presenter.loadCatList(0);
        ButterKnife.bind(this);
    }

    //提交
    private void commit() {

        if (TextStringUtils.isEmpty(edtName.getText().toString())) {
            Toast.makeText(PersonApplyActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(idNumber.getText().toString())) {
            Toast.makeText(PersonApplyActivity.this, "身份证号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(photoZ)) {
            Toast.makeText(PersonApplyActivity.this, "请上传身份证正面照", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(photoF)) {
            Toast.makeText(PersonApplyActivity.this, "请上传身份证反面照", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tv_city.getText().toString().equals("请点击选择所在的区域")) {
            Toast.makeText(PersonApplyActivity.this, "请选择所在的区域", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(edtAddress.getText().toString())) {
            Toast.makeText(PersonApplyActivity.this, "请输入详细地址", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tvBizType.getText().toString().equals("请点击选择经营品类") || sec_id.equals("")) {
            Toast.makeText(PersonApplyActivity.this, "请选择经营品类", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(bankcardNumber.getText().toString())) {
            Toast.makeText(PersonApplyActivity.this, "请填写银行卡号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(bankcardType.getText().toString())) {
            Toast.makeText(PersonApplyActivity.this, "请填写卡类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextStringUtils.isEmpty(edtCardholder.getText().toString())) {
            Toast.makeText(PersonApplyActivity.this, "请填写持卡人", Toast.LENGTH_SHORT).show();
            return;
        }
        showLoading("正在提交");
        PersonApplyInfo info = new PersonApplyInfo();
        info.setStore_type(1 + "");
        info.setReal_name(edtName.getText().toString());
        info.setId_img(new File(photoZ));
        info.setId_img_opposite(new File(photoF));
        info.setRegion_id(SharedPreferencesUtil.getInstance(this).getInt("region_id0") + "");
        info.setAddress(edtAddress.getText().toString());
        info.setBiz_type(sec_id);
        info.setCard(bankcardNumber.getText().toString());
        info.setCard_type(bankcardType.getText().toString());
        info.setCardholder(edtCardholder.getText().toString());
        info.setId_number(idNumber.getText().toString());
        Intent intent = new Intent(PersonApplyActivity.this, PersonApplyService.class);
        intent.putExtra("data", info);
        startService(intent);
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                hideLoading();
                Intent mIntent = new Intent(PersonApplyActivity.this, BusinessActivity.class);
                startActivity(mIntent);
                PersonApplyActivity.this.finish();
            }
        };
        timer.start();
    }



    @Override
    public void applyError(String message) {
        Toast.makeText(PersonApplyActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void load(AddresBean regionInfo) {
        hideLoading();
        bean = regionInfo;
        if (touch == 1) {
            new SelectPlaceDialog(this, tv_city, bean,0).show();
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
