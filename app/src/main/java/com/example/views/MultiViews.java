package com.example.views;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.canmorpro.whatsinyourfridge3.DBHelper;
import com.example.canmorpro.whatsinyourfridge3.R;
import com.example.menu.Menu;


/**
 * Created by CanMorPro on 16-04-06.
 */
public class MultiViews extends AppCompatActivity {


    DBHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_fragment);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.action_bar_logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);



        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.menu_container, new Menu()).commit();
        }

    }



    @Override

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
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
