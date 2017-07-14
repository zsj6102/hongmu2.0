package com.colpencil.redwood.view.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.colpencil.redwood.present.login.SplashPresenter;
import com.colpencil.redwood.view.activity.HomeActivity;

/**
 * @author 曾 凤
 * @Description: 欢迎界面
 * @Email 20000263@qq.com
 * @date 2016/7/6
 */

public class SplashActivity extends Activity {

    private SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SplashPresenter();
        presenter.login();
        new Thread() {
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(1000);
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }

}
