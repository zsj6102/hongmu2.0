package com.colpencil.redwood.view;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.colpencil.redwood.configs.StringConfig;

import java.io.Serializable;

/**
 * @author 陈 宝
 * @Description:播放音乐的服务
 * @Email: 1041121352@qq.com
 * @date 2016/9/8
 */
public class PlayerService extends Service {

    public MediaPlayer player;
    private boolean isLoop = true;

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        player.setLooping(true);
        new UpdateProgressThread().start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }

    class MusicBinder extends Binder implements IMusicPlayer {

        @Override
        public void playOrpause() {
            Intent intent = new Intent(StringConfig.ACTION_MUSIC_PAUSE);
            if (player.isPlaying()) {
                player.pause();
                intent.putExtra("isplay", "no");
            } else {
                player.start();
                intent.putExtra("isplay", "yes");
            }
            sendBroadcast(intent);
        }

        @Override
        public void playCurrentMusic(String path) {
            try {
                player.reset();
                player.setDataSource(path);
                player.prepare();
                player.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void seekTo(int position) {
            player.seekTo(position);
        }
    }

    class UpdateProgressThread extends Thread {

        @Override
        public void run() {
            while (isLoop) {
                try {
                    Thread.sleep(1000);
                    if (player.isPlaying()) {
                        int currentPosition = player.getCurrentPosition();
                        int total = player.getDuration();
                        Intent intent = new Intent(StringConfig.ACTION_MUSIC_UPDATE_PROGRESS);
                        intent.putExtra("current", currentPosition);
                        intent.putExtra("total", total);
                        sendBroadcast(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isLoop = false;
        player.release();
    }

    public interface IMusicPlayer extends Serializable {

        /**
         * 停止或者播放
         */
        void playOrpause();

        /**
         * 播放新的音乐
         *
         * @param path
         */
        void playCurrentMusic(String path);

        /**
         * 跳转到新的位置
         *
         * @param position
         */
        void seekTo(int position);

    }
}
