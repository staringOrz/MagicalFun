package cn.dengxijian.magicalfun.application;

import android.app.Application;

/**
 *
 */
public class FunApplication extends Application {
    private  static FunApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
    public static FunApplication getInstance(){
        return mApplication;
    }
}
