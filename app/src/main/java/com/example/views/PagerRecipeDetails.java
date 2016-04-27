package com.example.views;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.canmorpro.whatsinyourfridge3.DBHelper;
import com.example.canmorpro.whatsinyourfridge3.R;

/**
 * Created by CanMorPro on 16-04-06.
 */
public class PagerRecipeDetails extends Fragment {

    ViewPager pager ;
    ViewPagerAdapter adapter;

    public PagerRecipeDetails(){
    }

    int cursorPosition;
    DBHelper dbh = new DBHelper(getContext());


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.view_pager, container, false);

        Bundle args1 = getArguments();
        cursorPosition = args1.getInt("cursorPosition");

        pager = (ViewPager) rootView.findViewById(R.id.viewPagerRecipes);
        adapter = new ViewPagerAdapter(getFragmentManager());
        pager.setAdapter(adapter);
        pager.setCurrentItem(cursorPosition);

        return rootView;
    }


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        Cursor curs  = dbh.getRecipesTmpTrue();
        int numberOfRecipes = curs.getCount();


        public ViewPagerAdapter(FragmentManager fm) {

            super(fm);

        }

        @Override
        public RecipeDetails getItem(int position) {

            RecipeDetails recipeDetails = new RecipeDetails();

            curs.moveToFirst();
            curs.move(position);
            Bundle args = new Bundle();

            int idRecipe = curs.getInt(curs.getColumnIndexOrThrow(DBHelper.KEY_R_ID));
            String recipeName =  curs.getString(curs.getColumnIndexOrThrow(DBHelper.KEY_R_NAME));
            String imageUrl = curs.getString(curs.getColumnIndexOrThrow(DBHelper.KEY_R_URL));

            args.putInt("idRecipe", idRecipe);
            args.putString("recipeName", recipeName);
            args.putString("imageUrl", imageUrl);
            recipeDetails.setArguments(args);

            return recipeDetails;

        }

        @Override
        public int getCount() {
            return numberOfRecipes;
        }
    }
}
