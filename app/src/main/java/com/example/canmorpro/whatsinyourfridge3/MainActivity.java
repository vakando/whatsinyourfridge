package com.example.canmorpro.whatsinyourfridge3;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.views.MultiViews;


public class MainActivity extends AppCompatActivity {

//
    private DBHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbh = new DBHelper(this);


        Intent intent = new Intent(getApplicationContext(), MultiViews.class);
        startActivity(intent);
    }

//    @Override
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_share:
//                doShare();
//                return true;
//            case R.id.action_settings:
////                setContentView(R.layout.settings);
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void doShare() {
//        Cursor curseur = dbh.getShoppingList();
//        StringBuilder text = new StringBuilder();
//        curseur.moveToFirst();
//        while(!curseur.isAfterLast()){
//            text.append(curseur.getString(curseur.getColumnIndex(DBHelper.KEY_I_NAME)));
//            text.append("\n");
//            curseur.moveToNext();
//        }
//        Log.d("share", text.toString());
//        Intent share_i = new Intent(Intent.ACTION_SEND);
//        share_i.putExtra(Intent.EXTRA_SUBJECT, "This is your shopping list");
//        share_i.putExtra(Intent.EXTRA_TEXT, text.toString());
//        share_i.setType("text/plain");
//        try{
//            startActivity(Intent.createChooser(share_i, getResources().getText(R.string.send)));
//        }catch(Exception e){
//            Log.e("SHARE",e.getMessage());
//        }
//    }
}

