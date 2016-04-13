package com.example.canmorpro.whatsinyourfridge3;

import android.content.Intent;
import android.database.Cursor;
//import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

//
    private DBHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbh = new DBHelper(this);
        DownloadTask dt = new DownloadTask();
        if(dbh.TABLE_AUTOCOMPLETE_INGREDIENT == null){
        dt.execute();
        }

        setContentView(R.layout.multi_fragment);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.action_bar_logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.menu_container, new com.example.menu.Menu()).commit();
        }


    }

    public class DownloadTask extends AsyncTask<Void, Void, Void> {

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                doShare();
                return true;
            case R.id.action_settings:
//                setContentView(R.layout.settings);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doShare() {
        Cursor curseur = dbh.getShoppingList();
        StringBuilder text = new StringBuilder();
        curseur.moveToFirst();
        while(!curseur.isAfterLast()){
            text.append(curseur.getString(curseur.getColumnIndex(DBHelper.KEY_I_NAME)));
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

