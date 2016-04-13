package com.example.views;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.canmorpro.whatsinyourfridge3.R;
import com.example.canmorpro.whatsinyourfridge3.ViewPagerAdapter;

/**
 * Created by CanMorPro on 16-04-06.
 */
public class PagerRecipeDetails extends Fragment {


     ViewPager pager ;
    ViewPagerAdapter adapter;

    public PagerRecipeDetails(){
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.view_pager ,container , false);

        pager = (ViewPager) rootView.findViewById(R.id.viewPagerRecipes);
        adapter = new ViewPagerAdapter(getFragmentManager());
        pager.setAdapter(adapter);

        return rootView;
    }
}
