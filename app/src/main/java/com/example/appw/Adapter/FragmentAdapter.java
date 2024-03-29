package com.example.appw.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;


    /**

     * 功能：viewpager 添加　fragments 的适配器

     *

     */
    public final class FragmentAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;



        public FragmentAdapter(List<Fragment> fragments, FragmentManager fm) {

            super(fm);

            this.mFragments = fragments;

        }

//        public FragmentAdapter(List<android.support.v4.app.Fragment> fragments, android.support.v4.app.FragmentManager supportFragmentManager) {
//            super(fragments, supportFragmentManager);
//        }


        @Override

        public Fragment getItem(int i) {

            return mFragments.get(i);

        }



        @Override

        public int getCount() {

            return mFragments.size();

        }

    }


