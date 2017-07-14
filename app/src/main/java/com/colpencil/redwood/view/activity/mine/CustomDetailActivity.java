package com.colpencil.redwood.view.activity.mine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.result.CustomDetailResult;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.present.mine.CustomDetailPresenter;
import com.colpencil.redwood.view.activity.commons.GalleyActivity;
import com.colpencil.redwood.view.impl.ICustomDetailView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author 陈 宝
 * @Description:定制详情
 * @Email 1041121352@qq.com
 * @date 2016/10/25
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_custom_detail
)
public class CustomDetailActivity extends ColpencilActivity implements ICustomDetailView {

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.contact_name)
    TextView contact_name;
    @Bind(R.id.contact_mobile)
    TextView contact_mobile;
    @Bind(R.id.contact_qq)
    TextView contact_qq;
    @Bind(R.id.contact_wechat)
    TextView contact_wechat;
    @Bind(R.id.tv_description)
    TextView tv_description;
    @Bind(R.id.custom_gridview)
    RecyclerView recycler;
    @Bind(R.id.contact_time)
    TextView tv_time;

    private CustomDetailPresenter presenter;
    private int id;
    private MyAdapter adapter;
    private List<String> mdata = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        id = getIntent().getIntExtra("id", 0);
        tv_title.setText("私人定制");
        recycler.setLayoutManager(new GridLayoutManager(this, 5));
        adapter = new MyAdapter(mdata);
        recycler.setAdapter(adapter);
        presenter.loadContent(id);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CustomDetailPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadData(CustomDetailResult result) {
        if (result.getCode().equals("1")) {
            contact_name.setText(result.getContact());
            contact_mobile.setText(result.getMobile());
            contact_qq.setText(result.getQq());
            contact_wechat.setText(result.getWechat());
            tv_description.setText(result.getDescription());
            tv_time.setText(TimeUtil.timeFormat(result.getTime(), "yyyy-MM-dd HH:mm:ss"));
            mdata.addAll(result.getImg());
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<String> list;

        public MyAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(CustomDetailActivity.this).inflate(R.layout.item_image, null));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.iv_large.setVisibility(View.GONE);
            holder.iv_middle.setVisibility(View.GONE);
            holder.iv_small.setVisibility(View.VISIBLE);
            ImageLoaderUtils.loadImage(CustomDetailActivity.this, list.get(position), holder.iv_small);
            holder.iv_small.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CustomDetailActivity.this, GalleyActivity.class);
                    intent.putStringArrayListExtra("data", (ArrayList<String>) list);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_large;
        ImageView iv_middle;
        ImageView iv_small;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_large = (ImageView) itemView.findViewById(R.id.item_image_big);
            iv_middle = (ImageView) itemView.findViewById(R.id.item_image_middle);
            iv_small = (ImageView) itemView.findViewById(R.id.item_image_small);
        }
    }
}
