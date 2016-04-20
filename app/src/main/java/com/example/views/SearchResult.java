package com.example.views;

import android.content.Context;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.canmorpro.whatsinyourfridge3.IngredientSearch;
import com.example.canmorpro.whatsinyourfridge3.R;
import com.example.canmorpro.whatsinyourfridge3.DBHelper;
import com.squareup.picasso.Picasso;

import java.net.URI;

/**
 * Created by CanMorPro on 16-04-06.
 */
public class SearchResult extends Fragment {


//    IngredientSearch ingredientSearch;



    public SearchResult(){
    }

//    int first = ingredientSearch.count;

    ListView listView;
    Cursor curs, cursCount;
    String count;
    MyCursorAdapter adapter;
    DBHelper dbh = new DBHelper(getContext());

    int more = 15;
//    Cursor c;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        curs = dbh.getRecipesTmpTrue();
        cursCount = dbh.getSearchCount();

        cursCount.moveToLast();
            count = cursCount.getString(cursCount.getColumnIndex(DBHelper.KEY_REQ_NUM))+" Recipes found";
//            count = cursCount.getCount() +" Recipes found";
            View rootView = inflater.inflate(R.layout.search_result, container, false);
            listView = (ListView) rootView.findViewById(R.id.searchResultListView);

            TextView resultCount = (TextView) rootView.findViewById(R.id.resultCount);
            resultCount.setText(count);

            adapter = new MyCursorAdapter(getContext(),curs,0);
            fetchData();
            listView.setAdapter(adapter);

            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }
                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

//                    System.out.println("firstVisibleItem "+firstVisibleItem);

                    if ( firstVisibleItem == more){
                        fetchData();
                        more = more+15;

                    }
                }

            });

            return rootView;

    }

    public void fetchData(){

//        c.requery();
        curs.requery();
        adapter.changeCursor(curs);
        adapter.notifyDataSetChanged();

        System.out.println(" data fetched ");

//        return ad;
    }



    public class MyCursorAdapter extends CursorAdapter {

        public MyCursorAdapter(Context context, Cursor cursor, int flags) {
            super(context, cursor, 0);
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.recette_ligne, parent, false);
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            // Find fields to populate in inflated template
            TextView textVueTitre = (TextView) view.findViewById(R.id.recipe_title_line);
            TextView textVueIngrediants = (TextView) view.findViewById(R.id.recipe_ingredient_line);
            ImageView imageView = (ImageView) view.findViewById(R.id.recipe_image_line);
            ImageButton imageButton = (ImageButton) view.findViewById(R.id.fav_line);

            // Extract properties from cursor

            String title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KEY_R_NAME));
            int IdRecipe = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.KEY_R_ID));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KEY_R_URL));


            int cursorPosition = cursor.getPosition();

            textVueTitre.setOnClickListener(new MyOnClickListener(IdRecipe, title, url, cursorPosition ));
            textVueIngrediants.setOnClickListener(new MyOnClickListener(IdRecipe, title, url, cursorPosition ));
            imageView.setOnClickListener(new MyOnClickListener(IdRecipe, title, url, cursorPosition ));
            imageButton.setOnClickListener(new MyFavOnClickListener(IdRecipe));

            Cursor c = dbh.getIngredientsNamesByRecipeId(IdRecipe);

            int n = 0;
            String noms="";
            c.moveToFirst();
            while (!c.isAfterLast() && n < 3) {
                noms = noms +  c.getString(c.getColumnIndexOrThrow(DBHelper.KEY_I_NAME)) + ", ";
                c.moveToNext();
                if(n==2) noms= noms + "...";
                n++;
            }


            // Populate fields with extracted properties
            //imageButton.setImageResource(R.drawable.fav_00);
            imageButton.setBackgroundResource(R.drawable.fav_00);

            if(dbh.getFavorit(IdRecipe)==1) imageButton.setBackgroundResource(R.drawable.fav_11);
            else imageButton.setBackgroundResource(R.drawable.fav_00);

            textVueTitre.setText(title);
            textVueIngrediants.setText(noms);
            try{
            Picasso.with(getContext()).load(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KEY_R_URL))).into(imageView);}catch (IllegalArgumentException e){}
            //imageView.setImageResource(R.drawable.menu_exemple);

        }
    }

    public class   MyOnClickListener implements View.OnClickListener {
        int idRecipe;
        String recipeName;
        String imageUrl;
        int cursorPosition;

        public MyOnClickListener(int idRecipe, String recipeName, String imageUrl, int cursorPosition ) {
            this.idRecipe = idRecipe;
            this.recipeName = recipeName;
            this.imageUrl = imageUrl;
            this.cursorPosition = cursorPosition;
//            this.numberOfRecipes = numberOfRecipes;

        }

        @Override
        public void onClick(View v) {

            Fragment  fragment = new PagerRecipeDetails();
            Bundle args = new Bundle();
            args.putInt("idRecipe", idRecipe);
            args.putString("recipeName", recipeName);
            args.putString("imageUrl", imageUrl);
            args.putInt("cursorPosition", cursorPosition);
//            args.putInt("numberOfRecipes", numberOfRecipes);


            fragment.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit();
        }
    }


    public class   MyFavOnClickListener implements View.OnClickListener {
        int idRecipe;

        public MyFavOnClickListener(int idRecipe) {
            this.idRecipe = idRecipe;
        }

        @Override
        public void onClick(View v) {
            if(dbh.getFavorit(idRecipe)==0){
                dbh.setFavorit(idRecipe, 1);
                v.setBackgroundResource(R.drawable.fav_11);
            }

            else{
                dbh.setFavorit(idRecipe, 0);
                v.setBackgroundResource(R.drawable.fav_00);
            }
        }
    }
}
