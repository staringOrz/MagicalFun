package cn.dengxijian.magicalfun.activity.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import butterknife.ButterKnife;
import cn.dengxijian.magicalfun.util.StatusBarUtil;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    protected final String TAG;

    public BaseActivity(){
        TAG = getClass().getSimpleName();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化View注入
        initContentView();
        ButterKnife.bind(this);
        initPresenter();
        mContext = this;
    }

    /**
     * 初始化控制中心
     */
    public  void initPresenter(){};
    /**
     * 初始化布局
     */
    public  void initContentView(){};

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
    /**
     * 隐藏状态栏
     */
    public void hiddenStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 改变状态栏颜色
     *
     * @param color
     */
    public void changeStatusBarColor(@ColorRes int color) {
        StatusBarUtil.setStatusBarColor(this, color);
    }

    /**
     * 调整状态栏为亮模式，这样状态栏的文字颜色就为深模式了。
     */
    private void reverseStatusColor() {
        StatusBarUtil.statusBarLightMode(this);
    }
}
