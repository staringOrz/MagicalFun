package cn.dengxijian.magicalfun.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.dengxijian.magicalfun.R;
import cn.dengxijian.magicalfun.fragment.base.BaseFragment;


/**
 * @author dengxijian
 * @date 2019/3/9
 */
public class MusicFragment extends BaseFragment {

    private View mContextView;
    private PlayBarFragment mPlayBarFragment;
    private FragmentManager mFragmentManager;
    private Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mContextView = inflater.inflate(R.layout.activity_home, container, false);
        initView(mContextView);
        mFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mPlayBarFragment == null) {
            mPlayBarFragment = PlayBarFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_playbar, mPlayBarFragment).commit();
        }else {
            fragmentTransaction.show(mPlayBarFragment).commit();
        }
        return mContextView;
    }

    private void initView(View mContextView) {
        toolbar = (Toolbar)mContextView.findViewById(R.id.home_activity_toolbar);
        toolbar.setTitle("听听音乐");
    }
}
