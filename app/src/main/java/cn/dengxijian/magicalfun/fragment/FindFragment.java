package cn.dengxijian.magicalfun.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.dengxijian.magicalfun.R;
import cn.dengxijian.magicalfun.fragment.base.BaseFragment;

/**
 * @author dengxijian
 * @date 2019/3/9
 */
public class FindFragment extends BaseFragment {
    private View mContextView;

    @Override
    protected void managerArguments() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getBaseActivity();
        mContextView = inflater.inflate(R.layout.activity_main, container, false);
        return mContextView;
    }

    @Override
    public int getLayoutRes() {
        return 0;
    }

    public void initView() {

    }
}
