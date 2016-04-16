package com.example.canmorpro.whatsinyourfridge3;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by Empress on 2016-04-15.
 */
public class SplashScreen extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    private DBHelper dbh;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splashscreen);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        dbh = new DBHelper(this);

        final DownloadTask dt = new DownloadTask();

        if(dbh.checkTable() == 0){
            new Thread(new Runnable() {
                public void run(){
                    dt.execute();
                }
            }).start();
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }


    public class DownloadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();}

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //ajout des themes dans la database
            dbh.setTableThemes("Default",1);
            dbh.setTableThemes("Grey",0);
            dbh.setTableThemes("Red",0);
            dbh.setTableThemes("Blue",0);
            //creation autocompleteIngredient
            String[] ingr = getResources().getStringArray(R.array.ingredients);
            for(int i=0;i<ingr.length;i++)
                dbh.addIngredients(ingr[i]);
            return null;
        }
    }
}
