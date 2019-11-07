package com.taxidriver.santos.view.adapter;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Author : WaterFlower.
 * Created on 2017/8/24.
 * Desc :  fragment适配器
 */
public class TabBarAdapter extends FragmentStatePagerAdapter {

    private static final int NO_TITLES = -1;
    private static final int HAVE_TITLES = 1;
    private String[] titles;
    private int type;
    private List<Fragment> fragments;

    public void setType(int type) {
        this.type = type;
    }

    public TabBarAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        setType(NO_TITLES);
        this.fragments = fragments;
    }

    public TabBarAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
        super(fm);
        setType(HAVE_TITLES);
        this.fragments = fragments;
        this.titles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (this.type == HAVE_TITLES && titles != null) {
            return titles[position];
        }

        return super.getPageTitle(position);
    }
}
