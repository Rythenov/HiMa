package com.jermyn.hima.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> _fragmentList = new ArrayList<>();
    private List<String> _fragmentTitleList = new ArrayList<>();

    public TabPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @Override
    public int getCount() {
        return _fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return _fragmentTitleList.get(position);
    }

    public void setData(List<Fragment> viewList) {
        _fragmentList = viewList;
    }

    public void setTitle(List<String> titleList) {
        _fragmentTitleList = titleList;
    }

    public List<String> getTitles() {
        return _fragmentTitleList;
    }

    public List<Fragment> getDatas() {
        return _fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return _fragmentList.get(position);
    }
}