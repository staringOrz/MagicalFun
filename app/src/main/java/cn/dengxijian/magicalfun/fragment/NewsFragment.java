package cn.dengxijian.magicalfun.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dengxijian.magicalfun.R;
import cn.dengxijian.magicalfun.database.news.CategoryDao;
import cn.dengxijian.magicalfun.entity.RefreshNewsFragmentEvent;
import cn.dengxijian.magicalfun.entity.news.CategoryEntity;
import cn.dengxijian.magicalfun.fragment.base.BaseFragment;
import cn.dengxijian.magicalfun.fragment.news.DefaultStyleFragment;
import cn.dengxijian.magicalfun.fragment.news.NewsPagerAdapter;
import cn.dengxijian.magicalfun.util.Const;
import cn.dengxijian.magicalfun.util.StatusBarUtil;

/**
 * @author dengxijian
 * @time 2019/3/9
 */
public class NewsFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mContextView;
    private String mParam1;
    private String mParam2;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.add_btn)
    ImageView mAddBtn;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private List<CategoryEntity> mCategoryEntityList;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_news;
    }


    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void initView() {
        mCategoryEntityList = getCategoryFromDB();
        if (mCategoryEntityList == null) {
            mCategoryEntityList = new ArrayList<>();
        }

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < mCategoryEntityList.size(); i++) {
            if (true) {
                DefaultStyleFragment meFragment = DefaultStyleFragment.newInstance(mCategoryEntityList.get(i).getKey(), "");
                fragments.add(meFragment);
            } else {
//                MeFragment meFragment = new MeFragment();
//                fragments.add(meFragment);
            }
        }
        mViewPager.setAdapter(new NewsPagerAdapter(getChildFragmentManager(), fragments));
        initMagicIndicator();
    }

    private List<CategoryEntity> getCategoryFromDB() {
        CategoryDao categoryDao = new CategoryDao(getContext().getApplicationContext());
        return categoryDao.queryCategoryList();
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mCategoryEntityList == null ? 0 : mCategoryEntityList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(mCategoryEntityList.get(index).getName());
                simplePagerTitleView.setNormalColor(Color.WHITE);
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.colorAccent));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Color.WHITE);
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    protected void managerArguments() {
        mParam1 = getArguments().getString(ARG_PARAM1);
        mParam2 = getArguments().getString(ARG_PARAM2);
    }

    @OnClick(R.id.add_btn)
    public void onClick() {
        EventBus.getDefault().post(new RefreshNewsFragmentEvent(Const.NEWSFRAGMENT_CATEGORYACTIVITY_REQUESTCODE));
    }
}
