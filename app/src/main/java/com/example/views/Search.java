package com.example.views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.canmorpro.whatsinyourfridge3.DBHelper;
import com.example.canmorpro.whatsinyourfridge3.IngredientSearch;
import com.example.canmorpro.whatsinyourfridge3.R;
import com.example.canmorpro.whatsinyourfridge3.RecipeSearch;

import java.util.ArrayList;

/**
 * Created by CanMorPro on 16-04-06.
 */
public class Search extends Fragment implements View.OnClickListener {


    DBHelper dbh;
    IngredientSearch ingSearch;
    RecipeSearch recipSearch;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;

    Button addButton;
    Button ingredientRadio;
    Button recipeRadio;
    Button searchButton;

    Button delBut1;
    Button delBut2;
    Button delBut3;

    TextView ing1;
    TextView ing2;
    TextView ing3;

    AutoCompleteTextView searchfield;

    LinearLayout layoutFrame;
    LinearLayout layoutButtonSearch;

    private ArrayList<String> ingredients;
    private ArrayList<String> list_recipes;

    int count = 0;//On initialise un compteur qui compte le nombre d'ingredients ajouté
    private IngredientSearch search;

    private ArrayList<String> ingResult;





    public Search(){
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dbh = new DBHelper(getContext());


        ingredients = dbh.getAllIngredients();

        View rootView = inflater.inflate(R.layout.search ,container , false);

        searchfield = (AutoCompleteTextView)rootView.findViewById(R.id.searching);


        addButton=(Button) rootView.findViewById(R.id.addButton);
        recipeRadio = (Button)rootView.findViewById(R.id.recipeRadio);
        ingredientRadio = (Button)rootView.findViewById(R.id.ingredientRadio);
        searchButton = (Button)rootView.findViewById(R.id.searchButton);
        ing1 = (TextView)rootView.findViewById(R.id.ingrView1);
        ing2 = (TextView)rootView.findViewById(R.id.ingrView2);
        ing3 = (TextView)rootView.findViewById(R.id.ingrView3);
        delBut1 = (Button)rootView.findViewById(R.id.delButton1);
        delBut2 = (Button)rootView.findViewById(R.id.delButton2);
        delBut3 = (Button)rootView.findViewById(R.id.delButton3);

        layoutFrame = (LinearLayout) rootView.findViewById(R.id.layoutFrame);
        layoutButtonSearch = (LinearLayout)rootView.findViewById(R.id.layoutSearchButton);

        ingredientRadio.setSelected(true);

        addButton.setOnClickListener(this);
        recipeRadio.setOnClickListener(this);
        ingredientRadio.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        delBut1.setOnClickListener(this);
        delBut2.setOnClickListener(this);
        delBut3.setOnClickListener(this);

        searchfield = (AutoCompleteTextView) rootView.findViewById(R.id.searchfield);




//        //pour l'autocomplete de la recherche par recette
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, list_recipes);
//        searchfield.setAdapter(adapter);

        //pour l'autocomplete de la recherche par ingredient
        ArrayAdapter<String> adapter1= new ArrayAdapter<String>(getContext(), R.layout.drop_down, ingredients);
        searchfield.setAdapter(adapter1);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        switch (v.getId()) {
            case R.id.addButton://pour le bouton plus
                String text = searchfield.getText().toString();//recupérer le texte de l'autoComplete
                if (text.length() == 0)
                    break;
                else if (count == 0) {
                    ing1.setText(text);//on met le texte de l'autoComplete dans le premier textView
                    ing1.setVisibility(View.VISIBLE);
                    delBut1.setVisibility(View.VISIBLE);
                    searchfield.setText("");
                    count++;
                } else if (count == 1) {
                    ing2.setText(text);//on le met dans le second textView
                    ing2.setVisibility(View.VISIBLE);
                    delBut2.setVisibility(View.VISIBLE);
                    searchfield.setText("");
                    count++;
                } else if (count == 2) {
                    ing3.setText(text);//on le met dans le 3ième textView
                    ing3.setVisibility(View.VISIBLE);
                    delBut3.setVisibility(View.VISIBLE);
                    searchfield.setText("");
                    count++;
                }
                break;

            case R.id.ingredientRadio://pour le bouton by ingredient
                addButton.setVisibility(View.VISIBLE);
                layoutFrame.setVisibility(View.VISIBLE);
                ingredientRadio.setSelected(true);
                recipeRadio.setSelected(false);
                ing1.setText("");
                ing2.setText("");
                ing3.setText("");
                delBut1.setVisibility(View.INVISIBLE);
                delBut2.setVisibility(View.INVISIBLE);
                delBut3.setVisibility(View.INVISIBLE);

                params.topMargin = 30;
                layoutButtonSearch.setLayoutParams(params);


                break;

            case R.id.recipeRadio://pour le bouton by recipe
                addButton.setVisibility(View.INVISIBLE);
                layoutFrame.setVisibility(View.INVISIBLE);
                recipeRadio.setSelected(true);
                ingredientRadio.setSelected(false);

                params.topMargin = -350;
                layoutButtonSearch.setLayoutParams(params);

//                searchButton
//                ing1.setVisibility(View.INVISIBLE);
//                ing2.setVisibility(View.INVISIBLE);
//                ing3.setVisibility(View.INVISIBLE);
//                delBut1.setVisibility(View.INVISIBLE);
//                delBut2.setVisibility(View.INVISIBLE);
//                delBut3.setVisibility(View.INVISIBLE);

                break;


            case R.id.delButton1:
                ing1.setText("");
                delBut1.setVisibility(View.INVISIBLE);
                count--;
                break;

            case R.id.delButton2:
                ing2.setText("");
                delBut2.setVisibility(View.INVISIBLE);
                count--;

                break;

            case R.id.delButton3:
                ing3.setText("");
                delBut3.setVisibility(View.INVISIBLE);
                count--;

                break;

            case R.id.searchButton://pour le bouton search

            System.out.println("ing radio : "+ingredientRadio.isSelected());

                System.out.println("recip radio : " + recipeRadio.isSelected());


                if(ingredientRadio.isSelected())  searchIngProc(ing1.getText().toString(),ing2.getText().toString(),ing3.getText().toString());
                else if (recipeRadio.isSelected()) searchRecipProc(searchfield.getText().toString());


                break;
//
//            case R.id.searchButtonReci:
//                new RecipeSearch(searchfield.getText().toString(), dbh);
//                break;


        }
    }

    public void replaceFragment(Fragment fragment){

        fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.main_container, fragment);
        fragmentTransaction.commit();

    }


    public void searchRecipProc(String recipeName){

        if(recipeName.length() == 0){

            System.out.println(" need to enter data");

        }else {

            recipSearch = new RecipeSearch(recipeName, dbh);
            recipSearch.storeData();

            //        show search result page
            fragment = new SearchResult();
            replaceFragment(fragment);

        }

    }

    public void searchIngProc (String ingredient1 , String ingredient2 , String ingredient3){

        ingResult = new ArrayList<>();

        if(!"".equals(ingredient1)){

            ingResult.add(ingredient1);

        } if(!"".equals(ingredient2)){

            ingResult.add(ingredient2);

        } if(!"".equals(ingredient3)){

            ingResult.add(ingredient3);

        }


        if (ingResult.size()==0){

//            pop up message

            System.out.println(" need to enter data");

        }else if (ingResult.size() == 1){

            ingSearch = new IngredientSearch(ingResult.get(0),"","",dbh);
            ingSearch.storeData();

            //        show search result page
            fragment = new SearchResult();
            replaceFragment(fragment);

        }
        else if (ingResult.size() == 2){

            ingSearch = new IngredientSearch(ingResult.get(0),ingResult.get(1),"",dbh);
            ingSearch.storeData();

            //        show search result page
            fragment = new SearchResult();
            replaceFragment(fragment);

        }
        else if (ingResult.size() == 3){

            ingSearch = new IngredientSearch(ingResult.get(0),ingResult.get(1),ingResult.get(2),dbh);
            ingSearch.storeData();

            //        show search result page
            fragment = new SearchResult();
            replaceFragment(fragment);


        }


    }


}
