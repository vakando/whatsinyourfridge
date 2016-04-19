package com.example.canmorpro.whatsinyourfridge3;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;

import com.example.views.Search;
import com.example.views.SearchResult;

/**
 * Created by CanMorPro on 16-04-16.
 */
public class BackgroundDataSearch extends AsyncTask {


    IngredientSearch ingSearch;
    RecipeSearch recipSearch;
    int radio;

    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    public BackgroundDataSearch(IngredientSearch ingSearch, RecipeSearch recipSearch, int radio){

        this.ingSearch = ingSearch;
        this.recipSearch = recipSearch;
        this.radio = radio;

    }



    @Override
    protected Object doInBackground(Object[] params) {

        if(radio==1) ingSearch.storeRemaining();
        else if (radio ==2 ) recipSearch.storeRemaining();

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        System.out.println("work started");

    }

    @Override
    protected void onPostExecute(Object o) {

        System.out.println("work done");
        // make start and end row have the default value after the process is done
        ingSearch.startRow = 1;
        ingSearch.endRow =20;

        recipSearch.startRow =1;
        recipSearch.endRow =20;
    }

}
