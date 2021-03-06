package com.example.views;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.canmorpro.whatsinyourfridge3.DBHelper;
import com.example.canmorpro.whatsinyourfridge3.R;
import com.squareup.picasso.Picasso;

import java.util.GregorianCalendar;

/**
 * Created by CanMorPro on 16-04-06.
 */
public class RecipeDetails extends Fragment implements View.OnClickListener {


    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    DBHelper dbh = new DBHelper(getContext());

    ImageButton addToShopping, addToCalendar, favButton;
    ImageView recipeImage;

    TextView recipeTitle, ingredientsText, preparationText;

    int idRecipe;
    String recipeName ;
    String imageUrl;

    public RecipeDetails() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.recipe_details, container, false);

        addToShopping = (ImageButton) rootView.findViewById(R.id.addToShoppingButton);
        addToCalendar = (ImageButton) rootView.findViewById(R.id.addToCalendarButton);
        favButton = (ImageButton) rootView.findViewById(R.id.fav_0_button);
        recipeImage = (ImageView) rootView.findViewById(R.id.recipeImage);
        recipeTitle = (TextView) rootView.findViewById(R.id.recipeTitle);
        ingredientsText = (TextView) rootView.findViewById(R.id.ingredientsText);
        preparationText = (TextView) rootView.findViewById(R.id.preparationText);

        addToCalendar.setOnClickListener(this);
        addToShopping.setOnClickListener(this);
        favButton.setOnClickListener(this);

        //Recuperer les info du recipe dans la base

        Bundle args = getArguments();
        idRecipe = args.getInt("idRecipe");
        recipeName = args.getString("recipeName");
        imageUrl = args.getString("imageUrl");

        String newline = System.getProperty("line.separator");

        Cursor curs= dbh.getPreparationsByRecipeId(idRecipe);
        curs.moveToFirst();
        String preparation ="";
        while (!curs.isAfterLast()) {

            System.out.print(" curseur -"+curs.getString(curs.getColumnIndexOrThrow(DBHelper.KEY_P_PREP))+"-");

         preparation = preparation + "+"+ curs.getString(curs.getColumnIndexOrThrow(DBHelper.KEY_P_PREP)) + " "+newline+newline;
            curs.moveToNext();

        }

        Cursor c = dbh.getIngredientsNamesByRecipeId(idRecipe);
        String nomsIngredients="";
        c.moveToFirst();
        while (!c.isAfterLast()) {
            nomsIngredients = nomsIngredients + "+"+ c.getString(c.getColumnIndexOrThrow(DBHelper.KEY_I_NAME)) + " "+newline;
            c.moveToNext();
        }


        if(dbh.getFavorit(idRecipe)==1) favButton.setBackgroundResource(R.mipmap.fav_1);
        else favButton.setBackgroundResource(R.mipmap.fav_0);
        recipeTitle.setText(recipeName);
        ingredientsText.setText(nomsIngredients);
        preparationText.setText(preparation);
        Picasso.with(getContext()).load(imageUrl).into(recipeImage);

        return rootView;
    }



    public void replaceFragment(Fragment fragment) {

        fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.main_container, fragment);
        fragmentTransaction.addToBackStack("recipeDetails").commit();

    }

    @Override
    public void onClick(View v) {

        if (v.equals(addToCalendar)) {

            Intent intent = new Intent(Intent.ACTION_INSERT);//Intent.ACTION_INSERT
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra(CalendarContract.Events.TITLE, recipeName);
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Home");
            intent.putExtra(CalendarContract.Events.DESCRIPTION, "Prepare");

            GregorianCalendar calDate = new GregorianCalendar(2016, 3, 01);
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                    calDate.getTimeInMillis());
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                    calDate.getTimeInMillis());

            intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);


            intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
            intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

            startActivity(intent);

        } else if (v.equals(addToShopping)) {

            fragment = new addShoppingList();

            Bundle bundle = new Bundle();

            bundle.putInt("recipeId", idRecipe);
            fragment.setArguments(bundle);

            replaceFragment(fragment);

        } else if (v.equals(favButton)) {

            if(dbh.getFavorit(idRecipe)==0){
                dbh.setFavorit(idRecipe, 1);
                favButton.setBackgroundResource(R.mipmap.fav_1);
            }

            else{
                dbh.setFavorit(idRecipe, 0);
                favButton.setBackgroundResource(R.mipmap.fav_0);
            }
        }

    }
//    public void onBackPressed() {
//        FragmentManager fm = getActivity().getSupportFragmentManager();
//        fm.popBackStack();
//    }
}
