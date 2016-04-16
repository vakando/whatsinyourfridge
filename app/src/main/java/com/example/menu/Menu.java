package com.example.menu;

import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.canmorpro.whatsinyourfridge3.R;
import com.example.views.Calendar;
import com.example.views.Favorites;
import com.example.views.PagerRecipeDetails;
import com.example.views.RecipeDetails;
import com.example.views.Search;
import com.example.views.SearchResult;
import com.example.views.Settings;
import com.example.views.ShoppingList;
//import com.example.views.simpleFragment;

public class Menu extends Fragment implements View.OnClickListener {



    ImageButton searchButton, shoppingButton, calendarButton, favoritesButton;
    TextView searchText, shoppingText, calendarText, favoritesText;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    int selectedColor, deselectedColor;



    public Menu(){
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_menu, container, false);

        setHasOptionsMenu(true);

        fragment = new Search();
        fragmentTransaction = getFragmentManager().beginTransaction().add(R.id.main_container, fragment );
        fragmentTransaction.commit();

         searchText = (TextView) view.findViewById((R.id.menu_search_text));
        shoppingText = (TextView) view.findViewById((R.id.menu_shopping_text));
        calendarText = (TextView) view.findViewById((R.id.menu_calendar_text));
        favoritesText = (TextView) view.findViewById((R.id.menu_favorites_text));


        searchButton = (ImageButton) view.findViewById(R.id.menu_search_button);
         shoppingButton = (ImageButton) view.findViewById(R.id.menu_shopping_button);
         calendarButton = (ImageButton) view.findViewById(R.id.menu_calendar_button);
         favoritesButton = (ImageButton) view.findViewById(R.id.menu_favorites_button);


         searchButton.setOnClickListener(this);
         shoppingButton.setOnClickListener(this);
         calendarButton.setOnClickListener(this);
         favoritesButton.setOnClickListener(this);

         selectedColor = getResources().getColor(R.color.selected);
         deselectedColor = getResources().getColor(R.color.deselected);

//        select search button by default
         searchButton.setBackgroundColor(selectedColor);
         searchText.setBackgroundColor(selectedColor);



        return view;
    }

    public void deselectAll(){

        searchButton.setBackgroundColor(deselectedColor);
        shoppingButton.setBackgroundColor(deselectedColor);
        calendarButton.setBackgroundColor(deselectedColor);
        favoritesButton.setBackgroundColor(deselectedColor);

        searchText.setBackgroundColor(deselectedColor);
        shoppingText.setBackgroundColor(deselectedColor);
        calendarText.setBackgroundColor(deselectedColor);
        favoritesText.setBackgroundColor(deselectedColor);


    }

    public void replaceFragment(Fragment fragment){

        fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.main_container, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View v) {

        if (v.equals(searchButton)){

            deselectAll();

            searchButton.setBackgroundColor(selectedColor);
            searchText.setBackgroundColor(selectedColor);


            fragment = new Search();
            replaceFragment(fragment);


        }
        else if (v.equals(shoppingButton)){

            deselectAll();

            shoppingButton.setBackgroundColor(selectedColor);
            shoppingText.setBackgroundColor(selectedColor);


            fragment = new ShoppingList();
            replaceFragment(fragment);


        }
        else if (v.equals(calendarButton)){

            deselectAll();

            //calendarButton.setBackgroundColor(selectedColor);
            //calendarText.setBackgroundColor(selectedColor);

            searchButton.setBackgroundColor(selectedColor);
            searchText.setBackgroundColor(selectedColor);

            //fragment = new Calendar();
            //replaceFragment(fragment);

            //  A date-time specified in milliseconds since the epoch.
            long startMillis = System.currentTimeMillis ();
            Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
            builder.appendPath("time");
            ContentUris.appendId(builder, startMillis);
            Intent intent = new Intent(Intent.ACTION_VIEW)
                    .setData(builder.build());
            startActivity(intent);

        }
        else if (v.equals(favoritesButton)){

            deselectAll();

            favoritesButton.setBackgroundColor(selectedColor);
            favoritesText.setBackgroundColor(selectedColor);

            //fragment = new SearchResult();
            fragment = new Favorites();
            replaceFragment(fragment);

        }


    }

    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_settings, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings) {
            replaceFragment(new Settings());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
