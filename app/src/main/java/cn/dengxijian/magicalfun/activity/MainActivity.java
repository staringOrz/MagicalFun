package cn.dengxijian.magicalfun.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.dengxijian.magicalfun.R;
import cn.dengxijian.magicalfun.activity.base.BaseActivity;
import cn.dengxijian.magicalfun.fragment.FindFragment;
import cn.dengxijian.magicalfun.fragment.MusicFragment;
import cn.dengxijian.magicalfun.fragment.NewsFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FragmentManager mFragmentManager;
    private MusicFragment mMusicFragment;
    private FindFragment mFindFragment;
    private NewsFragment mNewsFragment;
    private Fragment mCurrent;

    private RelativeLayout mMusicLayout;
    private RelativeLayout mNewsLayout;
    private RelativeLayout mFindLayout;
    private TextView mMusicView;
    private TextView mNewsView;
    private TextView mFindView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        changeStatusBarColor(R.color.colorPrimary);
        initView();

        mMusicFragment = new MusicFragment();
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_layout,mMusicFragment);
        fragmentTransaction.commit();


    }

    private void initView() {
        mMusicLayout = (RelativeLayout) findViewById(R.id.rl_music_view);
        mMusicLayout.setOnClickListener(this);
        mNewsLayout = (RelativeLayout) findViewById(R.id.rl_news_view);
        mNewsLayout.setOnClickListener(this);
        mFindLayout = (RelativeLayout) findViewById(R.id.rl_find_view);
        mFindLayout.setOnClickListener(this);

        mMusicView = (TextView) findViewById(R.id.tv_image_music);
        mNewsView = (TextView) findViewById(R.id.tv_image_news);
        mFindView = (TextView) findViewById(R.id.tv_image_find);

        //音乐图标被选中
        //mMusicView.setBackgroundResource(R.drawable.comui_tab_home_selected);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        switch (v.getId()){
            case R.id.rl_music_view:
                hideFragment(mFindFragment, fragmentTransaction);
                hideFragment(mNewsFragment, fragmentTransaction);
                if (mMusicFragment == null) {
                    mMusicFragment = new MusicFragment();
                    fragmentTransaction.add(R.id.content_layout, mMusicFragment);
                } else {
                    mCurrent = mMusicFragment;
                    fragmentTransaction.show(mMusicFragment);
                }
                break;
            case R.id.rl_find_view:
                hideFragment(mMusicFragment, fragmentTransaction);
                hideFragment(mNewsFragment, fragmentTransaction);
                if (mFindFragment == null) {
                    mFindFragment = new FindFragment();
                    fragmentTransaction.add(R.id.content_layout, mFindFragment);
                } else {
                    mCurrent = mFindFragment;
                    fragmentTransaction.show(mFindFragment);
                }
                break;
            case R.id.rl_news_view:
                hideFragment(mMusicFragment, fragmentTransaction);
                hideFragment(mFindFragment, fragmentTransaction);
                if (mNewsFragment == null) {
                    mNewsFragment = new NewsFragment();
                    fragmentTransaction.add(R.id.content_layout, mNewsFragment);
                } else {
                    mCurrent = mNewsFragment;
                    fragmentTransaction.show(mNewsFragment);
                }
                break;

        }
        fragmentTransaction.commit();
    }

    private void hideFragment(Fragment fragment, FragmentTransaction ft) {
        if (fragment != null) {
            ft.hide(fragment);
        }
    }
}
