package com.example.newbiechen.simpledependencedemo.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.newbiechen.simpledependencedemo.R;
import com.example.newbiechen.simpledependencedemo.ui.base.BaseActivity;
import com.example.newbiechen.simpledependencedemo.ui.base.BaseFragment;
import com.example.newbiechen.simpledependencedemo.ui.fragment.AndroidFragment;
import com.example.newbiechen.simpledependencedemo.ui.fragment.RecommendFragment;
import com.example.newbiechen.simpledependencedemo.ui.fragment.TestFragment;
import com.example.newbiechen.simpledependencedemo.ui.fragment.WelfareFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mVp;

    private List<BaseFragment> mFragmentList = new ArrayList<>();
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        mToolbar = getViewById(R.id.toolbar);
        mTabLayout = getViewById(R.id.main_tab);
        mVp = getViewById(R.id.main_vp);
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        initTabLayout();
    }

    private void initTabLayout(){
        mFragmentList.add(new RecommendFragment());
        mFragmentList.add(new WelfareFragment());
        mFragmentList.add(new AndroidFragment());

        //binder width viewPager
        mVp.setAdapter(new MyFragmentPager(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mVp);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        supportActionBar(mToolbar);
        //shade left arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    class MyFragmentPager extends FragmentPagerAdapter {

        public MyFragmentPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentList.get(position).getName();
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
