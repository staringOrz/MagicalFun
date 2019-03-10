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
public class MusicFragment extends BaseFragment {

    private View mContextView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mContextView = inflater.inflate(R.layout.activity_home, container, false);
        initView();
        return mContextView;
    }

    private void initView() {

    }
}
