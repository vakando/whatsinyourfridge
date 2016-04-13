package com.example.canmorpro.whatsinyourfridge3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.views.RecipeDetails;

/**
 * Created by CanMorPro on 16-04-12.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {



    public ViewPagerAdapter(FragmentManager fm) {

        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        return new RecipeDetails();
    }

    @Override
    public int getCount() {
        return 5;
    }
}
