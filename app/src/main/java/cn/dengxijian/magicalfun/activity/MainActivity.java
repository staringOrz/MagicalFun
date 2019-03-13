package cn.dengxijian.magicalfun.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.dengxijian.magicalfun.R;
import cn.dengxijian.magicalfun.activity.base.BaseActivity;
import cn.dengxijian.magicalfun.database.news.CategoryDao;
import cn.dengxijian.magicalfun.entity.AllCategoryBean;
import cn.dengxijian.magicalfun.entity.news.CategoryEntity;
import cn.dengxijian.magicalfun.entity.news.CategoryManager;
import cn.dengxijian.magicalfun.fragment.FindFragment;
import cn.dengxijian.magicalfun.fragment.MusicFragment;
import cn.dengxijian.magicalfun.fragment.NewsFragment;
import cn.dengxijian.magicalfun.fragment.wechat.WechatFragment;
import cn.dengxijian.magicalfun.network.Network;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String NEWS_FRAGMENT = "NEWS_FRAGMENT";
    private static final String WECHAT_FRAGMENT = "WECHAT_FRAGMENT";
    private static final String FIND_FRAGMENT = "FIND_FRAGMENT";
    public static final String ME_FRAGMENT = "ME_FRAGMENT";
    public static final String FROM_FLAG = "FROM_FLAG";

    private Subscription mSubscription;

    private FragmentManager mFragmentManager;
    private MusicFragment mMusicFragment;
    private WechatFragment mWechatFragment;
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
        requestCategory();

    }

    public void initView() {
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
                hideFragment(mWechatFragment, fragmentTransaction);
                hideFragment(mNewsFragment, fragmentTransaction);
                changeStatusBarColor(R.color.colorPrimary);
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
                if (mWechatFragment == null) {
                    mWechatFragment = new WechatFragment();
                    fragmentTransaction.add(R.id.content_layout, mWechatFragment);
                } else {
                    mCurrent = mWechatFragment;
                    fragmentTransaction.show(mWechatFragment);
                }
                break;
            case R.id.rl_news_view:
                hideFragment(mMusicFragment, fragmentTransaction);
                hideFragment(mWechatFragment, fragmentTransaction);
                changeStatusBarColor(R.color.newColorPrimary);
                if (mNewsFragment == null) {
                    mNewsFragment = NewsFragment.newInstance("a", "b");
                    fragmentTransaction.add(R.id.content_layout, mNewsFragment, NEWS_FRAGMENT);
                } else {
                    mCurrent = mNewsFragment;
                    fragmentTransaction.show(mNewsFragment);
                }
                break;

        }
        fragmentTransaction.commit();
    }

//    private void saveFunctionToDB() {
//        Function function = null;
//        try {
//            function = (new Gson()).fromJson(StreamUtils.get(getApplicationContext(), R.raw.function), Function.class);
//        } catch (Exception e) {
//            Logger.e("读取raw中的function.json文件异常：" + e.getMessage());
//        }
//        if (function != null && function.getFunction() != null && function.getFunction().size() != 0) {
//            List<FunctionBean> functionBeanList = function.getFunction();
//            FunctionDao functionDao = new FunctionDao(getApplicationContext());
//            functionDao.insertFunctionBeanList(functionBeanList);
//        }
//
//
//    }

    Observer<AllCategoryBean> mObserver = new Observer<AllCategoryBean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Logger.e("onError:" + e.getMessage());
            saveCategoryToDB(null);
        }

        private void saveCategoryToDB(List<CategoryEntity> categoryEntityList) {
            CategoryDao categoryDao = new CategoryDao(getApplicationContext());
            categoryDao.deleteAllCategory();
            categoryDao.insertCategoryList(categoryEntityList == null ?
                    (new CategoryManager(getApplicationContext()).getAllCategory())
                    : categoryEntityList);
        }

        @Override
        public void onNext(AllCategoryBean wechatItem) {
            if (wechatItem != null && "200".equals(wechatItem.getRetCode())) {
                List<CategoryEntity> categoryEntityList = new ArrayList<>();
                List<AllCategoryBean.ResultBean> result = wechatItem.getResult();
                for (int i = 0; i < result.size(); i++) {
                    AllCategoryBean.ResultBean resultBean = result.get(i);
                    CategoryEntity categoryEntity = new CategoryEntity(null, resultBean.getName(), resultBean.getCid(), i);
                    categoryEntityList.add(categoryEntity);
                }
                saveCategoryToDB(categoryEntityList);
            } else {
                saveCategoryToDB(null);
            }
        }
    };

    /**
     * 第一次打开App时，将news的所有类别保存到本地数据库
     */
    private void requestCategory() {
        unsubscribe();
        mSubscription = Network.getAllCategoryApi()
                .getAllCategory()//key,页码,每页条数
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    private void hideFragment(Fragment fragment, FragmentTransaction ft) {
        if (fragment != null) {
            ft.hide(fragment);
        }
    }
}
