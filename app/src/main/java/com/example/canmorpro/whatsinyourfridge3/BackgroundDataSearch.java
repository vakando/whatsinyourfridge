package com.example.canmorpro.whatsinyourfridge3;

import android.os.AsyncTask;
import android.widget.ListView;

import com.example.views.SearchResult;

/**
 * Created by CanMorPro on 16-04-16.
 */
public class BackgroundDataSearch extends AsyncTask {


    IngredientSearch ingSearch;
    RecipeSearch recipSearch;
    int radio;
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

//        SearchResult searchResult = new SearchResult();
//        searchResult.after_last =ingSearch.count;
//        searchResult.first = 21;


        ingSearch.startRow = 1;
        ingSearch.endRow =20;

        recipSearch.startRow =1;
        recipSearch.endRow =20;
    }

}
