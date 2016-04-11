package com.example.views;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class SearchResult extends Fragment {


    public SearchResult(){
    }

    ListView listView;
    Cursor curs;
    MyCursorAdapter adapter;
    int n = 30; //sera le nombre d'éléments dans la base de favorite
    DBHelper dbh = new DBHelper(this.getContext());


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.search_result, container , false);

        curs = dbh.getRecipesTmpTrue();
        adapter = new MyCursorAdapter(getContext(),curs,0);
        listView.setAdapter(adapter);



        return rootView;
    }


    public class MyCursorAdapter extends CursorAdapter {
        public MyCursorAdapter(Context context, Cursor cursor, int flags) {
            super(context, cursor, 0);
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.recette_ligne, parent, false);
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template
            TextView textVueTitre = (TextView)view.findViewById(R.id.recipe_title_line);
            TextView textVueIngrediants = (TextView)view.findViewById(R.id.recipe_ingredient_line);
            ImageView imageView = (ImageView)view.findViewById(R.id.recipe_image_line);
            ImageButton imageButton = (ImageButton)view.findViewById(R.id.fav_line);

            // Extract properties from cursor
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KEY_R_NAME));

            int IdRecipe = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.KEY_R_ID));
            Cursor c = dbh.getIngredientsNamesByRecipeId(IdRecipe);
            int n=0;
            String[] noms = new String[3];
            c.moveToFirst();
            while (!c.isAfterLast() && n<3){
                noms[n]=c.getString(c.getColumnIndexOrThrow(DBHelper.KEY_I_NAME));
                c.moveToNext();
                n++;
            }
            String url = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KEY_R_URL));

            // Populate fields with extracted properties
            imageButton.setImageResource(R.mipmap.fav_0);
            textVueTitre.setText(title);
            textVueIngrediants.setText(noms[0] + noms[1] + noms[2] + "...");
            Picasso.with(getContext()).load(url).into(imageView);
        }
    }
}
