package cn.dengxijian.magicalfun.application;

import android.app.Application;

/**
 *
 */
public class MyApplication extends Application {
    private  static MyApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
    public static MyApplication getInstance(){
        return mApplication;
    }
}
