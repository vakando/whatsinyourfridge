package com.example.canmorpro.whatsinyourfridge3;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.views.Search;
import com.example.views.SearchResult;

import java.util.ArrayList;

/**
 * Created by CanMorPro on 16-04-15.
 */
public class ProcessSearch extends AsyncTask{


    Context context;
    String ing1, ing2, ing3, recipe;
    int radio;
    DBHelper dbh;

    private ArrayList<String> ingResult;

    Fragment fragment;
    FragmentTransaction fragmentTransaction;

    RecipeSearch recipSearch;
    IngredientSearch ingSearch;

    ProgressBar progressBar;


    public static Boolean done = false;

    Search.FragmentCallback fragmentCallback;

                        ;
    LinearLayout layoutProgressBar;

    public ProcessSearch (LinearLayout layoutProgressBar, ProgressBar progressBar, Search.FragmentCallback fragmentCallback, Context context,String ing1, String ing2, String ing3, String recipe, int radio, DBHelper dbh ){


        this.context = context;
        this.ing1 = ing1;
        this.ing2 = ing2;
        this.ing3 =ing3;
        this.recipe = recipe;
        this.radio = radio;
        this.dbh = dbh;
        this.fragmentCallback = fragmentCallback;
        this.progressBar = progressBar;
        this.layoutProgressBar = layoutProgressBar;


    }

    @Override
    protected Object doInBackground(Object[] params) {


//        synchronized (this) {

            if (radio == 1) { //ingredient search
                ingResult = new ArrayList<>();

                if (!"".equals(ing1)) {

                    ingResult.add(ing1);

                }
                if (!"".equals(ing2)) {

                    ingResult.add(ing2);

                }
                if (!"".equals(ing3)) {

                    ingResult.add(ing3);

                }

                if (ingResult.size() == 0) {


                } else if (ingResult.size() == 1) {

                    ingSearch = new IngredientSearch(ingResult.get(0), "", "", dbh);
                    ingSearch.storeData();

                } else if (ingResult.size() == 2) {

                    ingSearch = new IngredientSearch(ingResult.get(0), ingResult.get(1), "", dbh);
                    ingSearch.storeData();

                } else if (ingResult.size() == 3) {

                    ingSearch = new IngredientSearch(ingResult.get(0), ingResult.get(1), ingResult.get(2), dbh);
                    ingSearch.storeData();

                }


            } else if (radio == 2) { //recipe search

                System.out.println("recipe Radio");
                recipSearch = new RecipeSearch(recipe,dbh);
                recipSearch.storeData();

            }
//        }

return null;

    }

    @Override
    protected void onPreExecute() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        progressBar.setVisibility(View.VISIBLE);

        if(radio == 1){ // if ingredientRadio
            params.topMargin = -450;
            layoutProgressBar.setLayoutParams(params);

        }
        else if(radio == 2){ // if recipeRadio
            params.topMargin = -150;
            layoutProgressBar.setLayoutParams(params);
        }



    }

    @Override
    protected void onPostExecute(Object o) {

        fragmentCallback.onTaskDone();
        progressBar.setVisibility(View.INVISIBLE);

    }
}
