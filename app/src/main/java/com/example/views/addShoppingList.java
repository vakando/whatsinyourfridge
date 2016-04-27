package com.example.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canmorpro.whatsinyourfridge3.DBHelper;
import com.example.canmorpro.whatsinyourfridge3.R;
import com.example.menu.Menu;

public class addShoppingList extends Fragment implements View.OnClickListener {
    private DBHelper dbh;

    Fragment fragment;
    FragmentTransaction fragmentTransaction;

    private Button delete1;
    private Button delete2;
    private Button delete3;
    private Button delete4;
    private Button delete5;
    private Button delete6;
    private Button delete7;
    private Button delete8;
    private Button delete9;
    private Button delete10;
    private Button delete11;
    private Button delete12;
    private Button add_to_sl;
    private TextView ingredient1;
    private TextView ingredient2;
    private TextView ingredient3;
    private TextView ingredient4;
    private TextView ingredient5;
    private TextView ingredient6;
    private TextView ingredient7;
    private TextView ingredient8;
    private TextView ingredient9;
    private TextView ingredient10;
    private TextView ingredient11;
    private TextView ingredient12;
    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;
    private LinearLayout layout4;
    private LinearLayout layout5;
    private LinearLayout layout6;
    private LinearLayout layout7;
    private LinearLayout layout8;
    private LinearLayout layout9;
    private LinearLayout layout10;
    private LinearLayout layout11;
    private LinearLayout layout12;

//    int selectedColor, deselectedColor;
//    ImageButton searchButton, shoppingButton, calendarButton, favoritesButton;
//    TextView searchText, shoppingText, calendarText, favoritesText;

    private int ingredientNumber;
    private int recipeId;

    public addShoppingList(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbh = new DBHelper(getContext());
        Log.d("dbh", "created");

        //layout pour ajouter dans la shopping list a partir de la
        // liste d'ingredients de la recette donc view8
        View rootView = inflater.inflate(R.layout.add_to_shopping_list, container, false);



        delete1 = (Button)rootView.findViewById(R.id.delete1);
        delete2 = (Button)rootView.findViewById(R.id.delete2);
        delete3 = (Button)rootView.findViewById(R.id.delete3);
        delete4 = (Button)rootView.findViewById(R.id.delete4);
        delete5 = (Button)rootView.findViewById(R.id.delete5);
        delete6 = (Button)rootView.findViewById(R.id.delete6);
        delete7 = (Button)rootView.findViewById(R.id.delete7);
        delete8 = (Button)rootView.findViewById(R.id.delete8);
        delete9 = (Button)rootView.findViewById(R.id.delete9);
        delete10 = (Button)rootView.findViewById(R.id.delete10);
        delete11 = (Button)rootView.findViewById(R.id.delete11);
        delete12 = (Button)rootView.findViewById(R.id.delete12);
        add_to_sl = (Button)rootView.findViewById(R.id.add_to_sl);


        delete1.setOnClickListener(this);
        delete2.setOnClickListener(this);
        delete3.setOnClickListener(this);
        delete4.setOnClickListener(this);
        delete5.setOnClickListener(this);
        delete6.setOnClickListener(this);
        delete7.setOnClickListener(this);
        delete8.setOnClickListener(this);
        delete9.setOnClickListener(this);
        delete10.setOnClickListener(this);
        delete11.setOnClickListener(this);
        delete12.setOnClickListener(this);
        add_to_sl.setOnClickListener(this);

        ingredient1 = (TextView)rootView.findViewById(R.id.ingredient1);
        ingredient2 = (TextView)rootView.findViewById(R.id.ingredient2);
        ingredient3 = (TextView)rootView.findViewById(R.id.ingredient3);
        ingredient4 = (TextView)rootView.findViewById(R.id.ingredient4);
        ingredient5 = (TextView)rootView.findViewById(R.id.ingredient5);
        ingredient6 = (TextView)rootView.findViewById(R.id.ingredient6);
        ingredient7 = (TextView)rootView.findViewById(R.id.ingredient7);
        ingredient8 = (TextView)rootView.findViewById(R.id.ingredient8);
        ingredient9 = (TextView)rootView.findViewById(R.id.ingredient9);
        ingredient10 = (TextView)rootView.findViewById(R.id.ingredient10);
        ingredient11 = (TextView)rootView.findViewById(R.id.ingredient11);
        ingredient12 = (TextView)rootView.findViewById(R.id.ingredient12);

        layout1 = (LinearLayout)rootView.findViewById(R.id.layout1);
        layout2 = (LinearLayout)rootView.findViewById(R.id.layout2);
        layout3 = (LinearLayout)rootView.findViewById(R.id.layout3);
        layout4 = (LinearLayout)rootView.findViewById(R.id.layout4);
        layout5 = (LinearLayout)rootView.findViewById(R.id.layout5);
        layout6 = (LinearLayout)rootView.findViewById(R.id.layout6);
        layout7 = (LinearLayout)rootView.findViewById(R.id.layout7);
        layout8 = (LinearLayout)rootView.findViewById(R.id.layout8);
        layout9 = (LinearLayout)rootView.findViewById(R.id.layout9);
        layout10 = (LinearLayout)rootView.findViewById(R.id.layout10);
        layout11 = (LinearLayout)rootView.findViewById(R.id.layout11);
        layout12 = (LinearLayout)rootView.findViewById(R.id.layout12);

        Bundle b = getArguments();
        recipeId = b.getInt("recipeId");

        Cursor c = dbh.getIngredientsNamesByRecipeId(recipeId);
        ingredientNumber = c.getCount();

        c.moveToFirst();
        switch (c.getCount()){
            case 1:
                ingredient1.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout1.setVisibility(View.VISIBLE);
                break;
            case 2:
                c.moveToFirst();
                ingredient1.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout1.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient2.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout2.setVisibility(View.VISIBLE);
                break;
            case 3:
                c.moveToFirst();
                ingredient1.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout1.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient2.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout2.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient3.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout3.setVisibility(View.VISIBLE);
                break;
            case 4:
                c.moveToFirst();
                ingredient1.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout1.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient2.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout2.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient3.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout3.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient4.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout4.setVisibility(View.VISIBLE);
                break;
            case 5:
                c.moveToFirst();
                ingredient1.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout1.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient2.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout2.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient3.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout3.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient4.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout4.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient5.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout5.setVisibility(View.VISIBLE);
                break;
            case 6:
                c.moveToFirst();
                ingredient1.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout1.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient2.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout2.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient3.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout3.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient4.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout4.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient5.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout5.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient6.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout6.setVisibility(View.VISIBLE);
                break;
            case 7:
                c.moveToFirst();
                ingredient1.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout1.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient2.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout2.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient3.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout3.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient4.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout4.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient5.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout5.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient6.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout6.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient7.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout7.setVisibility(View.VISIBLE);
                break;
            case 8:
                c.moveToFirst();
                ingredient1.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout1.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient2.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout2.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient3.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout3.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient4.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout4.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient5.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout5.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient6.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout6.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient7.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout7.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient8.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout8.setVisibility(View.VISIBLE);
                break;
            case 9:
                c.moveToFirst();
                ingredient1.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout1.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient2.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout2.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient3.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout3.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient4.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout4.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient5.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout5.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient6.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout6.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient7.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout7.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient8.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout8.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient9.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout9.setVisibility(View.VISIBLE);
                break;
            case 10:
                c.moveToFirst();
                ingredient1.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout1.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient2.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout2.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient3.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout3.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient4.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout4.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient5.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout5.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient6.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout6.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient7.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout7.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient8.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout8.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient9.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout9.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient10.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout10.setVisibility(View.VISIBLE);
                break;
            case 11:
                c.moveToFirst();
                ingredient1.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout1.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient2.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout2.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient3.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout3.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient4.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout4.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient5.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout5.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient6.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout6.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient7.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout7.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient8.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout8.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient9.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout9.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient10.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout10.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient11.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout11.setVisibility(View.VISIBLE);
                break;
            case 12:
                c.moveToFirst();
                ingredient1.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout1.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient2.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout2.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient3.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout3.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient4.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout4.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient5.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout5.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient6.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout6.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient7.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout7.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient8.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout8.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient9.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout9.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient10.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout10.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient11.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout11.setVisibility(View.VISIBLE);
                c.moveToNext();
                ingredient12.setText(c.getString(c.getColumnIndex(DBHelper.KEY_I_NAME)));
                layout12.setVisibility(View.VISIBLE);
                break;
        }

        return rootView;
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.delete1:
                ingredient1.setText("");
                layout1.setVisibility(View.INVISIBLE);
                break;
            case R.id.delete2:
                ingredient2.setText("");
                layout2.setVisibility(View.INVISIBLE);
                break;
            case R.id.delete3:
                ingredient3.setText("");
                layout3.setVisibility(View.INVISIBLE);
                break;
            case R.id.delete4:
                ingredient4.setText("");
                layout4.setVisibility(View.INVISIBLE);
                break;
            case R.id.delete5:
                ingredient5.setText("");
                layout5.setVisibility(View.INVISIBLE);
                break;
            case R.id.delete6:
                ingredient6.setText("");
                layout6.setVisibility(View.INVISIBLE);
                break;
            case R.id.delete7:
                ingredient7.setText("");
                layout7.setVisibility(View.INVISIBLE);
                break;
            case R.id.delete8:
                ingredient8.setText("");
                layout8.setVisibility(View.INVISIBLE);
                break;
            case R.id.delete9:
                ingredient9.setText("");
                layout9.setVisibility(View.INVISIBLE);
                break;
            case R.id.delete10:
                ingredient10.setText("");
                layout10.setVisibility(View.INVISIBLE);
                break;
            case R.id.delete11:
                ingredient11.setText("");
                layout11.setVisibility(View.INVISIBLE);
                break;
            case R.id.delete12:
                ingredient12.setText("");
                layout12.setVisibility(View.INVISIBLE);
                break;
            case R.id.add_to_sl:
                Boolean count = false;
                if(ingredient1.getText().toString().length()>0){
                    Cursor c = dbh.getIngredientIdByName(ingredient1.getText().toString());
                    if(c.getCount()>0){
                        dbh.addInShoppingList(ingredient1.getText().toString());
                        int id;
                        c.moveToFirst();
                        id = c.getInt(c.getColumnIndex(DBHelper.KEY_I_ID));
                        dbh.updateLink(recipeId,id,1);
                        count = true;
                    }
                }
                if(ingredient2.getText().toString().length()>0){
                    Cursor c = dbh.getIngredientIdByName(ingredient2.getText().toString());
                    if(c.getCount()>0) {
                        dbh.addInShoppingList(ingredient2.getText().toString());
                        int id;
                        c.moveToFirst();
                        id = c.getInt(c.getColumnIndex(DBHelper.KEY_I_ID));
                        dbh.updateLink(recipeId, id, 1);
                        count = true;
                    }
                }
                if(ingredient3.getText().toString().length()>0){
                    Cursor c = dbh.getIngredientIdByName(ingredient3.getText().toString());
                    if(c.getCount()>0) {
                        dbh.addInShoppingList(ingredient3.getText().toString());
                        int id;
                        c.moveToFirst();
                        id = c.getInt(c.getColumnIndex(DBHelper.KEY_I_ID));
                        dbh.updateLink(recipeId, id, 1);
                        count = true;
                    }
                }
                if(ingredient4.getText().toString().length()>0){
                    Cursor c = dbh.getIngredientIdByName(ingredient4.getText().toString());
                    if(c.getCount()>0) {
                        dbh.addInShoppingList(ingredient4.getText().toString());
                        int id;
                        c.moveToFirst();
                        id = c.getInt(c.getColumnIndex(DBHelper.KEY_I_ID));
                        dbh.updateLink(recipeId, id, 1);
                        count = true;
                    }
                }
                if(ingredient5.getText().toString().length()>0){
                    Cursor c = dbh.getIngredientIdByName(ingredient5.getText().toString());
                    if(c.getCount()>0) {
                        dbh.addInShoppingList(ingredient5.getText().toString());
                        int id;
                        c.moveToFirst();
                        id = c.getInt(c.getColumnIndex(DBHelper.KEY_I_ID));
                        dbh.updateLink(recipeId, id, 1);
                        count = true;
                    }
                }
                if(ingredient6.getText().toString().length()>0){
                    Cursor c = dbh.getIngredientIdByName(ingredient6.getText().toString());
                    if(c.getCount()>0) {
                        dbh.addInShoppingList(ingredient6.getText().toString());
                        int id;
                        c.moveToFirst();
                        id = c.getInt(c.getColumnIndex(DBHelper.KEY_I_ID));
                        dbh.updateLink(recipeId, id, 1);
                        count = true;
                    }
                }
                if(ingredient7.getText().toString().length()>0){
                    Cursor c = dbh.getIngredientIdByName(ingredient7.getText().toString());
                    if(c.getCount()>0) {
                        dbh.addInShoppingList(ingredient7.getText().toString());
                        int id;
                        c.moveToFirst();
                        id = c.getInt(c.getColumnIndex(DBHelper.KEY_I_ID));
                        dbh.updateLink(recipeId, id, 1);
                        count = true;
                    }
                }
                if(ingredient8.getText().toString().length()>0){
                    Cursor c = dbh.getIngredientIdByName(ingredient8.getText().toString());
                    if(c.getCount()>0) {
                        dbh.addInShoppingList(ingredient8.getText().toString());
                        int id;
                        c.moveToFirst();
                        id = c.getInt(c.getColumnIndex(DBHelper.KEY_I_ID));
                        dbh.updateLink(recipeId, id, 1);
                        count = true;
                    }
                }
                if(ingredient9.getText().toString().length()>0){
                    Cursor c = dbh.getIngredientIdByName(ingredient9.getText().toString());
                    if(c.getCount()>0) {
                        dbh.addInShoppingList(ingredient9.getText().toString());
                        int id;
                        c.moveToFirst();
                        id = c.getInt(c.getColumnIndex(DBHelper.KEY_I_ID));
                        dbh.updateLink(recipeId, id, 1);
                        count = true;
                    }
                }
                if(ingredient10.getText().toString().length()>0){
                    Cursor c = dbh.getIngredientIdByName(ingredient10.getText().toString());
                    if(c.getCount()>0) {
                        dbh.addInShoppingList(ingredient10.getText().toString());
                        int id;
                        c.moveToFirst();
                        id = c.getInt(c.getColumnIndex(DBHelper.KEY_I_ID));
                        dbh.updateLink(recipeId, id, 1);
                        count = true;
                    }
                }
                if(ingredient11.getText().toString().length()>0){
                    Cursor c = dbh.getIngredientIdByName(ingredient11.getText().toString());
                    if(c.getCount()>0) {
                        dbh.addInShoppingList(ingredient11.getText().toString());
                        int id;
                        c.moveToFirst();
                        id = c.getInt(c.getColumnIndex(DBHelper.KEY_I_ID));
                        dbh.updateLink(recipeId, id, 1);
                        count = true;
                    }
                }
                if(ingredient12.getText().toString().length()>0){
                    Cursor c = dbh.getIngredientIdByName(ingredient12.getText().toString());
                    if(c.getCount()>0) {
                        dbh.addInShoppingList(ingredient12.getText().toString());
                        int id;
                        c.moveToFirst();
                        id = c.getInt(c.getColumnIndex(DBHelper.KEY_I_ID));
                        dbh.updateLink(recipeId, id, 1);
                        count = true;
                    }
                }
                if(count == false){
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setTitle("ALERT");
                    alertDialog.setMessage("There is no ingredient to add to your shopping list.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else {
                    Toast.makeText(getContext(),"The ingredients were succesfully added to your shopping list",Toast.LENGTH_SHORT).show();
                    fragment = new ShoppingList();
                    replaceFragment(fragment);
                }
        }
    }

    public void replaceFragment(Fragment fragment){
        fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.main_container, fragment);
        fragmentTransaction.addToBackStack("shoppingList").commit();

    }

}
