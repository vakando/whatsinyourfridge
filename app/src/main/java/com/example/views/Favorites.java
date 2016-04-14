package com.example.views;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.canmorpro.whatsinyourfridge3.R;
import com.example.canmorpro.whatsinyourfridge3.DBHelper;
import com.squareup.picasso.Picasso;

/**
 * Created by CanMorPro on 16-04-06.
 */
public class Favorites extends Fragment {


    public Favorites(){
    }

    ListView listView;
    Cursor curs;
    MyCursorAdapter adapter;
    DBHelper dbh = new DBHelper(getContext());


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

/*
        //Test insertion dans la table recette
        dbh.deleteAllFromTable();


        dbh.setRecettes(1, "Recette1", 1, "PhotoUrl1", 1, "date1", 0, 0);
        dbh.setRecettes(2, "Recette2", 2, "PhotoUrl1", 1, "date1", 0, 0);
        dbh.setRecettes(3, "Recette3", 3, "PhotoUrl1", 1, "date1", 0, 0);
        dbh.setRecettes(4, "Recette4", 4, "PhotoUrl1", 1, "date1", 0, 0);
        dbh.setRecettes(5, "Recette5", 5, "PhotoUrl1", 1, "date1", 0, 0);
        dbh.setRecettes(6, "Recette6", 6, "PhotoUrl1", 1, "date1", 0, 0);
        dbh.setRecettes(7, "Recette7", 7, "PhotoUrl1", 1, "date1", 0, 0);
        dbh.setRecettes(8, "Recette8", 8, "PhotoUrl1", 1, "date1", 0, 0);
        dbh.setRecettes(9, "Recette9", 9, "PhotoUrl1", 1, "date1", 0, 0);
        dbh.setRecettes(10, "Recette10", 1, "PhotoUrl1", 1, "date1", 0, 0);
        dbh.setRecettes(11, "Recette11", 1, "PhotoUrl1", 1, "date1", 0, 0);
        dbh.setRecettes(12, "Recette12", 1, "PhotoUrl1", 0, "date1", 0, 0);

        dbh.setLinkRecipeIngredients(1, 1, 1, 0);
        dbh.setLinkRecipeIngredients(2, 2, 3, 0);
        dbh.setLinkRecipeIngredients(3, 3, 3, 0);
        dbh.setLinkRecipeIngredients(4, 4, 4, 0);
        dbh.setLinkRecipeIngredients(5, 5, 1, 0);
        dbh.setLinkRecipeIngredients(6, 6, 3, 0);
        dbh.setLinkRecipeIngredients(7, 7, 3, 0);
        dbh.setLinkRecipeIngredients(8, 8, 4, 0);
        dbh.setLinkRecipeIngredients(9, 9, 1, 0);
        dbh.setLinkRecipeIngredients(10, 10, 3, 0);
        dbh.setLinkRecipeIngredients(11, 11, 3, 0);
        dbh.setLinkRecipeIngredients(12, 12, 4, 0);

        dbh.setLinkRecipeIngredients(13, 1, 3, 0);
        dbh.setLinkRecipeIngredients(14, 2, 4, 0);

        dbh.setLinkRecipeIngredients(15, 1, 8, 0);
        dbh.setLinkRecipeIngredients(16, 2, 9, 0);

        dbh.setLinkRecipeIngredients(17, 1, 10, 0);
        dbh.setLinkRecipeIngredients(18,2,11,0);

*/
        curs = dbh.getFavorites();

        if(curs.getCount()!=0) {

            View rootView = inflater.inflate(R.layout.favorites, container, false);
            listView = (ListView) rootView.findViewById(R.id.fovoriteListView);

            adapter = new MyCursorAdapter(getContext(), curs, 0);
            listView.setAdapter(adapter);

            return rootView;
        }

        else {
            View rootView = inflater.inflate(R.layout.nofavorites, container, false);

            return rootView;
        }
    }


    public class MyCursorAdapter extends CursorAdapter {

        public MyCursorAdapter(Context context, Cursor cursor, int flags) {
            super(context, cursor, 0);
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

                textVueTitre.setOnClickListener(new MyOnClickListener(IdRecipe, title, url));
                textVueIngrediants.setOnClickListener(new MyOnClickListener(IdRecipe, title, url));
                imageView.setOnClickListener(new MyOnClickListener(IdRecipe, title, url));

                Cursor c = dbh.getIngredientsNamesByRecipeId(IdRecipe);
                int n = 0;
                String noms="";
                c.moveToFirst();
                while (!c.isAfterLast() && n < 3) {
                    noms = noms +  c.getString(c.getColumnIndexOrThrow(DBHelper.KEY_I_NAME)) + " ";
                    c.moveToNext();
                    if(n==2) noms= noms + "...";
                    n++;
                }


                // Populate fields with extracted properties
                imageButton.setImageResource(R.mipmap.fav_2);
                textVueTitre.setText(title);
                textVueIngrediants.setText(noms);
                //textVueIngrediants.setText(url + " ...");
                Picasso.with(getContext()).load(url).into(imageView);
                //imageView.setImageResource(R.drawable.menu_exemple);
        }
    }

    public class   MyOnClickListener implements View.OnClickListener {
        int idRecipe;
        String recipeName;
        String imageUrl;

        public MyOnClickListener(int idRecipe, String recipName, String imageUrl) {
            this.idRecipe = idRecipe;
            this.recipeName = recipeName;
            this.imageUrl = imageUrl;
        }

        @Override
        public void onClick(View v) {
            Fragment  fragment = new RecipeDetails();
            Bundle args = new Bundle();
            args.putInt("idRecipe", idRecipe);
            args.putString("recipeName", recipeName);
            args.putString("imageUrl", imageUrl);
            fragment.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
        }
    }

}
