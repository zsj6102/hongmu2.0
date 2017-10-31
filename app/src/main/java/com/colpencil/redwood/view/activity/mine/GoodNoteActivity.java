package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ImageSpan;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.function.Compressor;
import com.colpencil.redwood.function.tools.GlobalField;
import com.colpencil.redwood.function.widgets.MyRadioButton;
import com.colpencil.redwood.present.mine.GoodNotePresenter;
import com.colpencil.redwood.view.impl.IGoodNoteView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import zsj.com.richlibrary.RichEditor;

/**
 * 编辑商品详情
 */
@ActivityFragmentInject(contentViewId = R.layout.layout_good_note)
public class GoodNoteActivity extends ColpencilActivity implements IGoodNoteView {

    @Bind(R.id.tv_main_title)
    TextView tvMainTitle;
    @Bind(R.id.tv_shoppingCartFinish)
    TextView tvShoppingCartFinish;
    @Bind(R.id.editor)
    RichEditor mEditor;
    @Bind(R.id.ll_font_option_a)
    LinearLayout llFontSizeArea;
    @Bind(R.id.ll_txteditor_style_area)
    LinearLayout llFontBoldArea;
    @Bind(R.id.ll_font_option_color)
    LinearLayout llFontColorArea;
    @Bind(R.id.rg_font_option_b)
    RadioGroup rgFontBold;
    @Bind(R.id.mrb_font_option_add)
    MyRadioButton mrbFontOptionAdd;
    @Bind(R.id.mrb_font_option_sub)
    MyRadioButton mrbFontOptionSub;
    @Bind(R.id.mrb_font_option_black)
    MyRadioButton blackButton;

    private GoodNotePresenter presenter;
    private Bitmap head;
    private File photoFile;
    private String detail;
    private String sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private File uploadFile;

    @Override
    protected void initViews(View view) {
        tvMainTitle.setText("编辑商品详情");
        tvShoppingCartFinish.setText("完成");

        mEditor.setEditorFontSize(18);
        mEditor.setEditorFontColor(GlobalField.FontColor.COLOR_BLACK);
        mEditor.setPadding(10, 10, 10, 10);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        if (SharedPreferencesUtil.getInstance(App.getInstance()).getString("good") != "") {
            mEditor.setHtml(SharedPreferencesUtil.getInstance(App.getInstance()).getString("good"));
        }
        mEditor.setPlaceholder("请输入商品详情");
        blackButton.setChecked(true);
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                detail = text;
            }
        });
        setLitener();
    }

    @OnClick(R.id.tv_shoppingCartFinish)
    void save() {

        if (detail == null ) {
            SharedPreferencesUtil.getInstance(App.getInstance()).setString("good", "");
        } else {
            SharedPreferencesUtil.getInstance(App.getInstance()).setString("good", detail);
        }
        RxBusMsg msg = new RxBusMsg();
        msg.setType(100);
        RxBus.get().post("rxBusMsg", msg);
        finish();
        //        }else{
        //            ToastTools.showShort(this,"请输入商品详情再保存");
        //        }

    }

    @Override
    public void loadFail(String msg) {

    }

    @OnClick(R.id.iv_font_option_a)
    void aClick() {
        llFontSizeArea.setVisibility(View.VISIBLE);
        llFontBoldArea.setVisibility(View.GONE);
        llFontColorArea.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_font_option_picture)
    void pictureClick() {
        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent1, 1);
    }

    @OnClick(R.id.iv_font_option_color)
    void colorClick() {
        llFontSizeArea.setVisibility(View.GONE);
        llFontBoldArea.setVisibility(View.GONE);
        llFontColorArea.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ll_font_option_area)
    void areaClick() {
        rgFontBold.clearCheck();
        llFontSizeArea.setVisibility(View.GONE);
        llFontBoldArea.setVisibility(View.GONE);
        llFontColorArea.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_font_option_b)
    void bClick() {
        llFontSizeArea.setVisibility(View.GONE);
        llFontBoldArea.setVisibility(View.VISIBLE);
        llFontColorArea.setVisibility(View.GONE);
    }

    @OnClick(R.id.mrb_font_option_normal)
    void normalClick() {
        mEditor.setFontSize(GlobalField.FontSize.Middle);
    }

    @OnClick(R.id.mrb_font_option_black)
    void blackClick() {
        mEditor.setTextColor(GlobalField.FontColor.COLOR_BLACK);
    }

    @OnClick(R.id.mrb_font_option_gray)
    void grayClick() {
        mEditor.setTextColor(GlobalField.FontColor.COLOR_GRAY);
    }

    @OnClick(R.id.mrb_font_option_blackgray)
    void blackgrayClick() {
        mEditor.setTextColor(GlobalField.FontColor.COLOR_BLACKGRAY);
    }

    @OnClick(R.id.mrb_font_option_blue)
    void blueClick() {
        mEditor.setTextColor(GlobalField.FontColor.COLOR_BLUE);
    }

    @OnClick(R.id.mrb_font_option_green)
    void greenClick() {
        mEditor.setTextColor(GlobalField.FontColor.COLOR_GREEN);
    }

    @OnClick(R.id.mrb_font_option_yellow)
    void yellowClick() {
        mEditor.setTextColor(GlobalField.FontColor.COLOR_YELLOW);
    }

    @OnClick(R.id.mrb_font_option_violet)
    void violetClick() {
        mEditor.setTextColor(GlobalField.FontColor.COLOR_VOILET);
    }

    @OnClick(R.id.mrb_font_option_white)
    void whiteClick() {
        mEditor.setTextColor(GlobalField.FontColor.COLOR_WHITE);
    }

    @OnClick(R.id.mrb_font_option_red)
    void redClick() {
        mEditor.setTextColor(GlobalField.FontColor.COLOR_RED);
    }

    @OnClick(R.id.mcb_font_option_border)
    void borderClick() {
        mEditor.setBold();
    }

    @OnClick(R.id.mcb_font_option_inter)
    void interClick() {
        mEditor.setItalic();
    }

    @OnClick(R.id.mcb_font_option_line)
    void lineClick() {
        mEditor.setUnderline();
    }

    private void setLitener() {

        mrbFontOptionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((MyRadioButton) view).isChecked()) {
                    mEditor.setFontSize(GlobalField.FontSize.Large);
                } else {
                    mEditor.setFontSize(GlobalField.FontSize.Middle);
                }
            }
        });
        mrbFontOptionSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((MyRadioButton) view).isChecked()) {
                    mEditor.setFontSize(GlobalField.FontSize.SMALL);
                } else {
                    mEditor.setFontSize(GlobalField.FontSize.Middle);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        head = null;
                        head = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (head != null) {
                        deleteOldFile();
                        photoFile = new File(sdCardPath + "/" + "insertphoto.jpg");
                        try {

                            FileOutputStream fop = new FileOutputStream(photoFile);

                            head.compress(Bitmap.CompressFormat.JPEG, 100, fop);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            long leng = photoFile.length();
                            uploadFile = new Compressor(this).compressToFile(photoFile, "upload.jpg");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        showLoading("图片获取中...");
                        HashMap<String, RequestBody> params = new HashMap<>();
                        RequestBody body = RequestBody.create(MediaType.parse("image/png"), uploadFile);
                        params.put("infoImg" + "\"; filename=\"avatar.jpg", body);
                        presenter.getGoodUrl(params);
                    } else {
                        ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "选择图片失败");
                    }
                }
                break;
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    void deleteOldFile() {
        photoFile = new File(sdCardPath + "/" + "insertphoto.jpg");
        if (photoFile.exists()) {
            photoFile.delete();
        }
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new GoodNotePresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void loadUrl(ResultInfo<ImageSpan> info) {
        if (info.getCode() == 0) {
            mEditor.insertImage(info.getData().getUrl(), "100%", "auto");
            hideLoading();
        }
    }


}
