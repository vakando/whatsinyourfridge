package com.example.views;

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

import com.example.canmorpro.whatsinyourfridge3.R;

/**
 * Created by CanMorPro on 16-04-06.
 */
public class RecipeDetails extends Fragment implements View.OnClickListener {


    Fragment fragment;
    FragmentTransaction fragmentTransaction;

    ImageButton addToShopping, addToCalendar, favButton;
    ImageView recipeImage;

    TextView recipeTitle, ingredientsText;

    public RecipeDetails() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView =  inflater.inflate(R.layout.recipe_details, container, false);


        addToShopping = (ImageButton) rootView.findViewById(R.id.addToShoppingButton);
        addToCalendar = (ImageButton) rootView.findViewById(R.id.addToCalendarButton);
        favButton = (ImageButton) rootView.findViewById(R.id.fav_0_button);
        recipeImage = (ImageView) rootView.findViewById(R.id.recipeImage);
        recipeTitle = (TextView) rootView.findViewById(R.id.recipeTitle);
        ingredientsText = (TextView) rootView.findViewById(R.id.ingredientsText);

        addToCalendar.setOnClickListener(this);
        addToShopping.setOnClickListener(this);
        favButton.setOnClickListener(this);


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

//            fragment = new AddShoppingList();
//            replaceFragment(fragment);

        } else if (v.equals(favButton)) {

            favButton.setBackgroundResource(R.mipmap.fav_1);

        }

    }
}
