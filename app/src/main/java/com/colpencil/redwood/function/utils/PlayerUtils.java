package com.colpencil.redwood.function.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerUtils implements OnCompletionListener, OnBufferingUpdateListener, OnPreparedListener {

    public MediaPlayer player;
    private SeekBar seekBar;
    private Timer timer = new Timer();
    private TextView tv_alltime;    //总时长
    private TextView tv_playtime;

    public PlayerUtils(SeekBar seekBar, TextView tv_alltime, TextView play_time) {
        super();
        this.seekBar = seekBar;
        this.tv_alltime = tv_alltime;
        this.tv_playtime = play_time;
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnBufferingUpdateListener(this);
        player.setOnPreparedListener(this);
        timer.schedule(task, 0, 1000);
    }

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (player == null) {
                return;
            }
            if (player.isPlaying() && seekBar.isPressed() == false) {
                handler.sendEmptyMessage(0);
            }
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int position = player.getCurrentPosition();
            int duration = player.getDuration();
            if (duration > 0) {
                // 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）
                long pos = seekBar.getMax() * position / duration;
                seekBar.setProgress((int) pos);
                tv_playtime.setText(position / (1000 * 60) + ":" + (position / 1000) % 60);
            }
        }
    };

    /**
     * 播放的方法
     */
    public void play() {
        player.start();
    }

    public void playUrl(String url) {
        try {
            player.reset();
            player.setDataSource(url); // 设置数据源
            player.prepare(); // prepare自动播放
            int musicTime = player.getDuration() / 1000;
            tv_alltime.setText(musicTime / 60 + ":" + musicTime % 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        player.pause();
    }

    public void stop() {
        if (player != null) {
            player.stop();
            player.release();
//            player = null;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
//        player.release();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setSecondaryProgress(percent);
        int currentProgress = seekBar.getMax()
                * player.getCurrentPosition() / player.getDuration();
        Log.e(currentProgress + "% play", percent + " buffer");
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
