package com.example.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.canmorpro.whatsinyourfridge3.R;
import com.example.canmorpro.whatsinyourfridge3.CustomAdapter;
import com.example.canmorpro.whatsinyourfridge3.DBHelper;

import java.util.ArrayList;

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
    private int idIngredient = 1;

    public ShoppingList(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dbh = new DBHelper(getContext());
        Log.d("dbh", "created");

        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.shopping_list, container, false);

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

        Log.d("dbh", dbh.toString());

        shoppingList = dbh.getShoppingList();
        if(shoppingList.getCount()<=0){
            select.setEnabled(false);
            delete.setEnabled(false);
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("SHOPPING LIST");
            alertDialog.setMessage("There is nothing in your shopping list. \n ADD SOMETHING !");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
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
                dbh.clearIngredientTable();
                Toast.makeText(getContext(),"Delete succesful",Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_button:
                select.setEnabled(true);
                delete.setEnabled(true);
                String ingredient = actv.getText().toString();
                if(ingredient.length()==0){
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setTitle("ALERT");
                    alertDialog.setMessage("You have to write an ingredient");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                if(!ingredients.contains(ingredient)){
                    dbh.addIngredients(ingredient);
                    ingredients = dbh.getAllIngredients();
                }
                Cursor c = dbh.getIngredientIdByName(ingredient);
                long result = -1;
                int id = 0;
                if(c.getCount() <= 0){
                    while(result<0){
                        result = dbh.setIngredients(idIngredient,ingredient,1,0);
                        id = idIngredient;
                        idIngredient++;
                    }
                    dbh.setLinkRecetteIng(id, id, 1);
                    dbh.setRecettes(id,"",1,"",0,"",0,0);
                }
                else
                    dbh.addInShoppingList(ingredient);
                actv.setAdapter(new ArrayAdapter<>(getContext(), R.layout.drop_down, dbh.getAllIngredients()));
                actv.setText("");
                Toast.makeText(getContext(),"Ingredient added successfully",Toast.LENGTH_SHORT).show();
                break;
        }
        checked = new ArrayList<>();
        unchecked = new ArrayList<>();
        shoppingList = dbh.getShoppingList();
        shoppingList.moveToFirst();
        while(!shoppingList.isAfterLast()){
            Log.d("DBH", "nom = " + shoppingList.getString(shoppingList.getColumnIndex(DBHelper.KEY_I_NAME)));
            if(shoppingList.getInt(shoppingList.getColumnIndex(DBHelper.KEY_I_CHECK)) == 1
                    && !checked.contains(shoppingList.getString(shoppingList.getColumnIndex(DBHelper.KEY_I_NAME))))
                checked.add(shoppingList.getString(shoppingList.getColumnIndex(DBHelper.KEY_I_NAME)));
            else
                unchecked.add(shoppingList.getString(shoppingList.getColumnIndex(DBHelper.KEY_I_NAME)));
            shoppingList.moveToNext();
        }

        adapter = new CustomAdapter(getContext(),shoppingList,0,checked,unchecked);
        list.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_share) {
            doShare();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void doShare() {
        Cursor curseur = dbh.getShoppingList();
        StringBuilder text = new StringBuilder();
        curseur.moveToFirst();
        while(!curseur.isAfterLast()){
            text.append("ingredient : "+curseur.getString(curseur.getColumnIndex(DBHelper.KEY_I_NAME)));
            if(curseur.getString(curseur.getColumnIndex(DBHelper.KEY_R_NAME)) != null)
                text.append(" for recipe :"+curseur.getString(curseur.getColumnIndex(DBHelper.KEY_R_NAME)));
            text.append("\n");
            curseur.moveToNext();
        }
        Log.d("share", text.toString());
        Intent share_i = new Intent(Intent.ACTION_SEND);
        share_i.putExtra(Intent.EXTRA_SUBJECT, "This is your shopping list");
        share_i.putExtra(Intent.EXTRA_TEXT, text.toString());
        share_i.setType("text/plain");
        try{
            startActivity(Intent.createChooser(share_i, getResources().getText(R.string.send)));
        }catch(Exception e){
            Log.e("SHARE",e.getMessage());
        }
    }
}
