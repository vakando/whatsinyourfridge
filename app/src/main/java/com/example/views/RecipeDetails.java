package com.example.views;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
        int idRecipe = args.getInt("idRecipe");
        String recipeName = args.getString("recipeName");
        String imageUrl = args.getString("imageUrl");

        Cursor curs= dbh.getPreparationsByRecipeId(idRecipe);
        //String preparation = curs.getString(curs.getColumnIndexOrThrow(DBHelper.KEY_P_PREP));

        Cursor c = dbh.getIngredientsNamesByRecipeId(idRecipe);
        String nomsIngredients="";
        c.moveToFirst();
        while (!c.isAfterLast()) {
            nomsIngredients = nomsIngredients +  c.getString(c.getColumnIndexOrThrow(DBHelper.KEY_I_NAME)) + " ";
            c.moveToNext();
        }

        recipeTitle.setText(recipeName);
        ingredientsText.setText(nomsIngredients);
       // preparationText.setText(preparation);
        Picasso.with(getContext()).load(imageUrl).into(recipeImage);

        return rootView;
    }



    public void replaceFragment(Fragment fragment) {

        fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.main_container, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View v) {

        if (v.equals(addToCalendar)) {

//            fragment = new ();
//            replaceFragment(fragment);

        } else if (v.equals(addToShopping)) {

            fragment = new addShoppingList();

            Bundle bundle = new Bundle();
//          bundle.putInt("par1", value);
            fragment.setArguments(bundle);

//            on the other fragment

//            Bundle bundle = this.getArguments();
//            int myInt = bundle.getInt(key, defaultValue);

            replaceFragment(fragment);

        } else if (v.equals(favButton)) {

            favButton.setBackgroundResource(R.mipmap.fav_1);

        }

    }
}
