package cn.dengxijian.magicalfun.fragment.base;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.dengxijian.magicalfun.R;
import cn.dengxijian.magicalfun.activity.base.BaseActivity;
import cn.dengxijian.magicalfun.util.StatusBarUtil;

/**
 * @author dengxijian
 * @time 2019/3/9 6:51 PM
 */
public abstract class BaseFragment extends Fragment {
    protected BaseActivity mActivity;
    private View mLayoutView;

    /**
     * 申请指定的权限.
     */
    public void requestPermission(int code, String... permissions) {

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permissions, code);
        }
    }

    protected abstract void managerArguments();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            managerArguments();
        }
    }

    /**
     * 获取Activity
     *
     * @return
     */
    public BaseActivity getBaseActivity() {
        if (mActivity == null) {
            mActivity = (BaseActivity) getActivity();
        }
        return mActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //20160727 修复该方法多次调用 bug
        if (mLayoutView != null) {
            ViewGroup parent = (ViewGroup) mLayoutView.getParent();
            if (parent != null) {
                parent.removeView(mLayoutView);
            }
        } else {
            mLayoutView = getCreateView(inflater, container);
            ButterKnife.bind(this, mLayoutView);
            initView();     //初始化布局
        }
        return mLayoutView;
    }

    /**
     * 获取Fragment布局文件的View
     *
     * @param inflater
     * @param container
     * @return
     */
    private View getCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    /**
     * 初始化布局
     * @return 布局文件的id。
     */
    public abstract int getLayoutRes();
    /**
     * 初始化视图
     */
    public abstract void initView();
    /**
     * 判断是否有指定的权限
     */
    public boolean hasPermission(String... permissions) {

        for (String permisson : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permisson)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }



}
