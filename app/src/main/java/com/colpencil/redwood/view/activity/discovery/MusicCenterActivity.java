package com.colpencil.redwood.view.activity.discovery;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Music;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.present.home.MusicCenterPresenter;
import com.colpencil.redwood.view.DownLoadService;
import com.colpencil.redwood.view.PlayerService;
import com.colpencil.redwood.view.PlayerService.IMusicPlayer;
import com.colpencil.redwood.view.activity.HomeActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.MusicCenterAdapter;
import com.colpencil.redwood.view.impl.IMusicCenterView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/29 16 46
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_musiccenter
)
public class MusicCenterActivity extends ColpencilActivity implements IMusicCenterView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.tv_main_title)
    TextView tv_main_title;
    @Bind(R.id.activity_bga_music)
    BGARefreshLayout activity_bga_base;
    @Bind(R.id.activity_rv_music)
    RecyclerView activity_rv_base;
    @Bind(R.id.music_bottom)
    LinearLayout bottom;
    @Bind(R.id.music_pause)
    ImageView iv_pause;
    @Bind(R.id.seekbar)
    SeekBar seekbar;
    @Bind(R.id.music_name)
    TextView tv_name;
    @Bind(R.id.music_playtime)
    TextView tv_playtime;
    @Bind(R.id.music_alltime)
    TextView tv_alltime;
    @Bind(R.id.music_img)
    ImageView iv_music;

    private MusicCenterPresenter presenter;

    /**
     * 分页请求页码
     */
    private int pageNo = 1;
    /**
     * 每页信息条数
     */
    private int pageSize = 10;
    /**
     * 是否可进行上拉加载操作
     */
    private boolean flag;

    private MusicCenterAdapter mAdapter;

    private PopupWindow window;

    private View mview;

    private int mposition = 0;
    private ServiceConnection conn;
    private IMusicPlayer player;
    private MusicInfoReciver reciver;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        mview = view;
        activity_bga_base.setDelegate(this);
        activity_bga_base.setRefreshViewHolder(new BGANormalRefreshViewHolder(MusicCenterActivity.this, true));
        activity_bga_base.setSnackStyle(findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        initPlayer();
        initData();
        initReciver();
    }

    private void initPlayer() {
        Intent intent = new Intent(this, PlayerService.class);
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                player = (IMusicPlayer) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        this.bindService(intent, conn, Service.BIND_AUTO_CREATE);
    }

    private void initReciver() {
        reciver = new MusicInfoReciver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(StringConfig.ACTION_MUSIC_UPDATE_PROGRESS);
        filter.addAction(StringConfig.ACTION_MUSIC_PAUSE);
        this.registerReceiver(reciver, filter);
    }

    /**
     * 数据初始化
     */
    private void initData() {
        mAdapter = new MusicCenterAdapter(activity_rv_base);
        activity_rv_base.setLayoutManager(new LinearLayoutManager(MusicCenterActivity.this, LinearLayoutManager.VERTICAL, false));
        activity_rv_base.setAdapter(mAdapter);
        tv_main_title.setText("禅音");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showLoading(Constants.progressName);
        loadData();

        mAdapter.setListeners(new MusicCenterAdapter.ItemClickListeners() {
            @Override
            public void itemClick(int position) {
                mposition = position;
                showPopWindow(mview);
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MusicCenterPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void refresh(List<Music> data) {
        hideLoading();
        mAdapter.clear();//清空数据
        mAdapter.addNewDatas(data);//添加最新数据
        mAdapter.notifyDataSetChanged();//适配器进行刷新操作
        //停止刷新
        activity_bga_base.endRefreshing(data.size());
        //判断是否可以进行上拉加载更多操作
        isLoadMore(data.size());

    }

    @Override
    public void loadMore(List<Music> data) {
        hideLoading();
        mAdapter.addMoreDatas(data);//增加新数据
        mAdapter.notifyDataSetChanged();
        //停止加载
        activity_bga_base.endLoadingMore();
        isLoadMore(data.size());
    }

    @Override
    public void fail(String code, String msg) {
        hideLoading();
        activity_bga_base.endLoadingMore();
        activity_bga_base.endRefreshing(0);
        ColpenciSnackbarUtil.downShowing(MusicCenterActivity.this.findViewById(android.R.id.content), msg);
        if (code.equals("3")) {//未登录
            Intent intent = new Intent(MusicCenterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        pageNo = 1;
        loadData();
    }

    /**
     * 上拉加载
     *
     * @param refreshLayout
     * @return
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (flag == true) {
            loadData();
        }
        return false;
    }

    /**
     * 数据加载
     */
    private void loadData() {
        presenter.getContent(pageNo, pageSize);
        pageNo++;
    }

    /**
     * 设置是否可进行上拉加载操作
     */
    private void isLoadMore(int size) {
        if (size < pageSize) {
            flag = false;
        } else {
            flag = true;
        }
        hideLoading();
        activity_bga_base.endLoadingMore();
        activity_bga_base.endRefreshing(0);
    }

    private void showPopWindow(View view) {
        if (window == null) {
            window = new PopupWindow(initPopWindow(),
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        window.setFocusable(true);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(view, Gravity.BOTTOM, 100, 0);
        window.showAsDropDown(view);
    }

    private View initPopWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_music, null);
        view.findViewById(R.id.popwindow_null).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPop();
            }
        });
        view.findViewById(R.id.ll_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottom.setVisibility(View.VISIBLE);
                dismissPop();
                play();
            }
        });
        view.findViewById(R.id.ll_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPop();
                download();
            }
        });
        view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPop();
            }
        });
        return view;
    }

    private void dismissPop() {
        if (window != null) {
            window.dismiss();
        }
    }

    @OnClick(R.id.music_forword)
    void preOnClick() {
        if (mposition < 0) {
            ToastTools.showShort(this, "没有上一首了");
            return;
        }
        mposition--;
        play();
    }

    @OnClick(R.id.music_next)
    void nextOnClick() {
        if (mposition >= mAdapter.getItemCount() - 1) {
            ToastTools.showShort(this, "没有下一首了");
            return;
        }
        mposition++;
        play();
    }

    @OnClick(R.id.music_pause)
    void pauseOnClick() {
        player.playOrpause();
    }

    private void play() {
        String title = mAdapter.getItem(mposition).getTitle();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
                "_redwood/" + title + ".mp3");
        if (file.exists()) {
            player.playCurrentMusic(file.getAbsolutePath());
        } else {
            player.playCurrentMusic(mAdapter.getItem(mposition).getUrl());
        }
        tv_name.setText(title);
        iv_pause.setImageResource(R.mipmap.music_pause);
        ImageLoaderUtils.loadImage(this, mAdapter.getItem(mposition).getImage(), iv_music);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unbindService(conn);
        this.unregisterReceiver(reciver);
    }

    class MusicInfoReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(StringConfig.ACTION_MUSIC_UPDATE_PROGRESS)) {     //更新音乐进度
                //更新音乐进度
                int current = intent.getIntExtra("current", 0);
                int total = intent.getIntExtra("total", 0);
                //更新seekBar
                seekbar.setMax(total);
                seekbar.setProgress(current);
                //更新两个TextView
                tv_playtime.setText(TimeUtil.timeFormat(current, "mm:ss"));
                tv_alltime.setText(TimeUtil.timeFormat(total, "mm:ss"));
            } else if (action.equals(StringConfig.ACTION_MUSIC_PAUSE)) {
                String state = intent.getStringExtra("isplay");
                if (state.equals("yes")) {
                    iv_pause.setImageResource(R.mipmap.music_pause);
                } else {
                    iv_pause.setImageResource(R.mipmap.music_play);
                }
            }
        }
    }

    private void download() {
        Intent intent = new Intent(this, DownLoadService.class);
        String path = mAdapter.getItem(mposition).getUrl();
        String title = mAdapter.getItem(mposition).getTitle();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
                "_redwood/" + title + ".mp3");
        if (file.exists()) {
            ToastTools.showShort(this, "该音乐已经下载了");
        } else {
            intent.putExtra("path", path);
            intent.putExtra("title", title);
            startService(intent);
        }
    }

    @OnClick(R.id.iv_home)
    void toHome() {
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(3);
        RxBus.get().post("rxBusMsg", rxBusMsg);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.totop_iv)
    void totop() {
        activity_rv_base.smoothScrollToPosition(0);
    }
}
