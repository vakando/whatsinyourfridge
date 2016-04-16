package com.example.canmorpro.whatsinyourfridge3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Themes.onActivityCreateSetTheme(this);

//        dbh = new DBHelper(this);
//        final DownloadTask dt = new DownloadTask();
//        if(dbh.checkTable() == 0)
//
//        new Thread(new Runnable() {
//            public void run(){
//                dt.execute();
//            }
//        }).start();

        setContentView(R.layout.multi_fragment);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.action_bar_logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.menu_container, new com.example.menu.Menu()).commit();
        }
    }

//    public class DownloadTask extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            //ajout des themes dans la database
//            dbh.setTableThemes("Default",1);
//            dbh.setTableThemes("Grey",0);
//            dbh.setTableThemes("Red",0);
//            dbh.setTableThemes("Blue",0);
//            //creation autocompleteIngredient
//            String[] ingr = getResources().getStringArray(R.array.ingredients);
//            for(int i=0;i<ingr.length;i++)
//                dbh.addIngredients(ingr[i]);
//            return null;
//        }
//    }
}

