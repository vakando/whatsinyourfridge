package com.example.views;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.example.canmorpro.whatsinyourfridge3.MainActivity;
import com.example.canmorpro.whatsinyourfridge3.R;
import com.example.canmorpro.whatsinyourfridge3.CustomAdapter;
import com.example.canmorpro.whatsinyourfridge3.DBHelper;

import java.util.ArrayList;

/**
 * Created by CanMorPro on 16-04-06.
 */
public class ShoppingList extends Fragment implements View.OnClickListener {
    private Button select;
    private Button delete;
    private Button add;
    private ListView list;
    private DBHelper dbh;
    private CustomAdapter adapter;
    private Cursor shoppingList;
    private ArrayList<String> ingredients;
    private ArrayList<String> checked = new ArrayList<>();
    private ArrayList<String> unchecked = new ArrayList<>();
    private AutoCompleteTextView actv;

    public ShoppingList(){
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dbh = new DBHelper(getContext());
        Log.d("dbh", "created");

        //layout pour la shopping list donc view2
        View rootView = inflater.inflate(R.layout.shopping_list,container , false);


        ingredients = dbh.getAllIngredients();

        actv = (AutoCompleteTextView)rootView.findViewById(R.id.searching);
        actv.setAdapter(new ArrayAdapter<>(getContext(), R.layout.drop_down, ingredients));

        select = (Button)rootView.findViewById(R.id.select_button);
        delete = (Button)rootView.findViewById(R.id.delete_button);
        add = (Button)rootView.findViewById(R.id.add_button);

        select.setOnClickListener(this);
        delete.setOnClickListener(this);
        add.setOnClickListener(this);

        list = (ListView)rootView.findViewById(R.id.shopping_list);

        //Test insertion dans la table ingredients
        dbh.setIngredients(1,"un",1,0);
        dbh.setIngredients(2,"deux",0,0);
        dbh.setIngredients(3,"trois",1,0);
        dbh.setIngredients(4, "quatre", 1, 1);
        dbh.setIngredients(5, "cinq", 1, 0);
        dbh.setIngredients(6, "six", 1, 1);
        Log.d("dbh", dbh.toString());

        DownloadTask dt = new DownloadTask();
        dt.execute();

        shoppingList = dbh.getShoppingList();
        Log.d("DBH", "nombreIingredientsSL = " + shoppingList.getCount());
        shoppingList.moveToFirst();
        int i=0;
        while(!shoppingList.isAfterLast()){
            Log.d("DBH", "ingr " + i + " name = " + shoppingList.getString(shoppingList.getColumnIndex(DBHelper.KEY_I_NAME)));
            Log.d("DBH", "ingr " + i + " checked = " + shoppingList.getInt(shoppingList.getColumnIndex(DBHelper.KEY_I_CHECK)));
            Log.d("alert", "checked ? " + (shoppingList.getInt(shoppingList.getColumnIndex(DBHelper.KEY_I_CHECK)) == 1));
            if(shoppingList.getInt(shoppingList.getColumnIndex(DBHelper.KEY_I_CHECK)) == 1) {
                checked.add(shoppingList.getString(shoppingList.getColumnIndex(DBHelper.KEY_I_NAME)));
            }
            else
                unchecked.add(shoppingList.getString(shoppingList.getColumnIndex(DBHelper.KEY_I_NAME)));
            shoppingList.moveToNext();
            i++;
        }
        Log.d("shopping list", "done");
        for(int c=0; c<checked.size();c++)
            Log.d("checked",""+c+"- "+checked.get(c));
        for(int u=0; u<unchecked.size();u++)
            Log.d("unchecked",""+u+"- "+unchecked.get(u));
        adapter = new CustomAdapter(getContext(),shoppingList,0,checked,unchecked);
        list.setAdapter(adapter);



        return rootView;
    }




    @Override
    public void onClick(View v) {
        for(int i=0;i<adapter.getChecked().size();i++){
            Log.d("checked size", ""+adapter.getChecked().size());
            dbh.setCheck(adapter.getChecked().get(i), 1);
            Log.d("set checked", "" + adapter.getChecked().get(i));
        }
        for(int i=0;i<adapter.getUnchecked().size();i++){
            Log.d("unchecked size", ""+adapter.getUnchecked().size());
            dbh.setCheck(adapter.getUnchecked().get(i),0);
            Log.d("set unchecked", "" + adapter.getUnchecked().get(i));
        }
        switch (v.getId()){
            case R.id.select_button:
                shoppingList.moveToFirst();
                while(!shoppingList.isAfterLast()){
                    Log.d("selected", shoppingList.getString(shoppingList.getColumnIndex(DBHelper.KEY_I_NAME)));
                    dbh.setCheck(shoppingList.getString(shoppingList.getColumnIndex(DBHelper.KEY_I_NAME)), 1);
                    if(!checked.contains(shoppingList.getString(shoppingList.getColumnIndex(DBHelper.KEY_I_NAME))))
                        checked.add(shoppingList.getString(shoppingList.getColumnIndex(DBHelper.KEY_I_NAME)));
                    shoppingList.moveToNext();
                }
                break;
            case R.id.delete_button:
                for(int i=0;i<checked.size();i++)
                    dbh.removeFromShoppingList(checked.get(i));
                dbh.removeIngredient();
                break;
            case R.id.add_button:
                //AutoCompleteTextView actv = (AutoCompleteTextView)findViewById(R.id.searching);
                String ingredient = actv.getText().toString();
                if(ingredient.length()==0)
                    break;
                if(!ingredients.contains(ingredient)){
                    dbh.addIngredients(ingredient);
                    ingredients = dbh.getAllIngredients();
                    //ajout de l'ingredient dans la shopping list
                }
                actv.setAdapter(new ArrayAdapter<>(getContext(), R.layout.drop_down, dbh.getAllIngredients()));
                actv.setText("");
                break;
        }
        checked = new ArrayList<>();
        unchecked = new ArrayList<>();
        shoppingList = dbh.getShoppingList();
        shoppingList.moveToFirst();
        while(!shoppingList.isAfterLast()){
            Log.d("DBH", "nom = " + shoppingList.getString(shoppingList.getColumnIndex(DBHelper.KEY_I_NAME)));
            if(shoppingList.getInt(shoppingList.getColumnIndex(DBHelper.KEY_I_CHECK)) == 1 && !checked.contains(shoppingList.getString(shoppingList.getColumnIndex(DBHelper.KEY_I_NAME))))
                checked.add(shoppingList.getString(shoppingList.getColumnIndex(DBHelper.KEY_I_NAME)));
            else
                unchecked.add(shoppingList.getString(shoppingList.getColumnIndex(DBHelper.KEY_I_NAME)));
            shoppingList.moveToNext();
        }

        adapter = new CustomAdapter(getContext(),shoppingList,0,checked,unchecked);
        list.setAdapter(adapter);
    }

    public class DownloadTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }

        @Override
        protected Void doInBackground(Void... params) {
            //creation autocompleteIngredient
            String[] ingr = getResources().getStringArray(R.array.ingredients);
            for(int i=0;i<ingr.length;i++)
                dbh.addIngredients(ingr[i]);
            return null;
        }
    }
}
