package com.colpencil.redwood.view.activity.home;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.PostInfo;
import com.colpencil.redwood.bean.PostTypeInfo;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.PostDialog;
import com.colpencil.redwood.present.home.PostPresenter;
import com.colpencil.redwood.services.PostsService;
import com.colpencil.redwood.view.activity.mine.WebViewActivity;
import com.colpencil.redwood.view.adapters.ImageSelectAdapter;
import com.colpencil.redwood.view.adapters.ImageSelectAdapter.OnRecyclerViewItemClickListener;
import com.colpencil.redwood.view.adapters.NullAdapter;
import com.colpencil.redwood.view.impl.IPostView;
import com.jaeger.library.StatusBarUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;

/**
 * @author 陈宝
 * @Description:发帖界面
 * @Email DramaScript@outlook.com
 * @date 2016/7/12
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_common)
public class PostsActivity extends ColpencilActivity implements IPostView, OnRecyclerViewItemClickListener {

    @Bind(R.id.common_listview)
    ListView listView;
    @Bind(R.id.tv_main_title)
    TextView tv_title;
    private View headerView;
    private HeaderViewHolder holder;
    private List<PostTypeInfo> list = new ArrayList<>();
    private PostPresenter presenter;
    private ImageSelectAdapter adapter;
    private String sec_id;
    private ArrayList<ImageItem> defaultDataArray = new ArrayList<>();
    private PostDialog dialog;
    private int maxImgCount = 9;               //允许选择图片最大数
    private ImagePicker imagePicker;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private List<File> fileList = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        tv_title.setText(getString(R.string.post_posts));
        initImagePicker();
        initHeader();
    }

    private void initHeader() {
        headerView = LayoutInflater.from(this).inflate(R.layout.posts_header, null);
        holder = new HeaderViewHolder(headerView);
        listView.addHeaderView(headerView);
        listView.setAdapter(new NullAdapter(this, new ArrayList<String>(), R.layout.item_null));
        adapter = new ImageSelectAdapter(this, defaultDataArray, maxImgCount);
        adapter.setOnItemClickListener(this);
        holder.gridview.setLayoutManager(new GridLayoutManager(this, 4));
        holder.gridview.setHasFixedSize(true);
        holder.gridview.setAdapter(adapter);
        holder.tv_rule.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        presenter.loadTag();
    }

    private void initImagePicker() {
        imagePicker = App.getInstance().getImagePicker();
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new PostPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void loadTag(List<CategoryVo> categoryVos) {
        if (ListUtils.listIsNullOrEmpty(categoryVos))
            return;
        for (CategoryVo cate : categoryVos) {
            PostTypeInfo info = new PostTypeInfo();
            info.setTypename(cate.getCat_name());
            info.setChoose(false);
            info.setSec_id(cate.getCat_id());
            list.add(info);
        }
        if (dialog != null) {
            dialog.setList(list);
        }
    }

    @Override
    public void subResult(CommonResult result) {
        hideLoading();
        ToastTools.showShort(this, result.getMsg());
        RefreshMsg msg = new RefreshMsg();
        msg.setType(3);
        RxBus.get().post("refreshmsg", msg);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        holder.unbind();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                defaultDataArray.addAll(images);
                adapter.setImages(defaultDataArray);
                fileList.clear();
                List<File> files = new ArrayList<>();
                for (int i = 0; i < defaultDataArray.size(); i++) {
                    files.add(new File(defaultDataArray.get(i).path));
                }
                compress(files);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                defaultDataArray.clear();
                defaultDataArray.addAll(images);
                adapter.setImages(defaultDataArray);
                fileList.clear();
                List<File> files = new ArrayList<>();
                for (int i = 0; i < defaultDataArray.size(); i++) {
                    files.add(new File(defaultDataArray.get(i).path));
                }
                compress(files);
            }
        }
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    public void compress(List<File> list) {
        if (list.size() > 0) {
//            DialogTools.showLoding(this, "温馨提示", "获取中。。。");
            Luban.compress(this,list)
                    .putGear(Luban.THIRD_GEAR)
                    .launch(new OnMultiCompressListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(List<File> fileList) {
//                            DialogTools.dissmiss();
                            PostsActivity.this.fileList.addAll(fileList);
                        }

                        @Override
                        public void onError(Throwable e) {
//                            DialogTools.dissmiss();
                        }
                    });
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case Constants.IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - defaultDataArray.size());
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    class HeaderViewHolder {

        @Bind(R.id.post_choose_type)
        TextView tv_choose;
        @Bind(R.id.post_title)
        EditText et_title;
        @Bind(R.id.post_content)
        EditText et_content;
        @Bind(R.id.photo_gridview)
        RecyclerView gridview;
        @Bind(R.id.video_url)
        EditText video_url;
        @Bind(R.id.read_rule)
        TextView tv_rule;

        public HeaderViewHolder(View itemview) {
            ButterKnife.bind(this, itemview);
        }

        public void unbind() {
            ButterKnife.unbind(this);
        }

        @OnClick(R.id.post_submit)
        void SubmitClick() {
            String title = et_title.getText().toString();
            String content = et_content.getText().toString();
            String url = video_url.getText().toString();
            if (TextUtils.isEmpty(sec_id)) {
                ToastTools.show(PostsActivity.this, "请选择圈子类别", Toast.LENGTH_SHORT);
                return;
            } else if (TextUtils.isEmpty(title)) {
                ToastTools.show(PostsActivity.this, "请输入标题", Toast.LENGTH_SHORT);
                return;
            } else if (title.length() > 40) {
                ToastTools.show(PostsActivity.this, "标题不得超过40个字", Toast.LENGTH_SHORT);
                return;
            } else if (TextUtils.isEmpty(content)) {
                ToastTools.show(PostsActivity.this, "请输入帖子内容", Toast.LENGTH_SHORT);
                return;
            } else if (content.length() > 300) {
                ToastTools.show(PostsActivity.this, "帖子内容不得超过400个字", Toast.LENGTH_SHORT);
                return;
            } else {
                showLoading("正在提交中...");
                PostInfo info = new PostInfo();
                info.setTitle(title);
                info.setContent(content);
                info.setUrl(url);
                info.setSec_id(sec_id);
                info.setFileList(fileList);
                Intent intent = new Intent(PostsActivity.this, PostsService.class);
                intent.putExtra("data", info);
                startService(intent);
                CountDownTimer timer = new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        hideLoading();
                        finish();
                    }
                };
                timer.start();
            }
        }

        @OnClick(R.id.post_choose_type)
        void chooseClick() {
            if (dialog == null) {
                dialog = new PostDialog(PostsActivity.this, R.style.PostDialogTheme, list);
            }
            dialog.show();
            dialog.setListener(new PostDialog.PostClickListener() {
                @Override
                public void closeClick() {
                    dialog.dismiss();
                }

                @Override
                public void itemUnClick() {
                    tv_choose.setText("请选择圈子类别");
                    sec_id = "";
                }

                @Override
                public void itemClick(int position) {
                    tv_choose.setText(list.get(position).getTypename());
                    sec_id = list.get(position).getSec_id();
                    dialog.dismiss();
                }
            });
        }

        @OnClick(R.id.read_rule)
        void ruleClick() {
            Intent intent = new Intent(PostsActivity.this, WebViewActivity.class);
            intent.putExtra("key", "rule");
            startActivity(intent);
        }
    }

}
