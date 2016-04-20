package com.example.canmorpro.whatsinyourfridge3;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Themes.cTheme == 0){
            DBHelper dbh = new DBHelper(this);
            Cursor c = dbh.getTheme();
            c.moveToFirst();
            Themes.cTheme = c.getInt(c.getColumnIndex(DBHelper.KEY_T_ID));
        }
        Themes.onActivityCreateSetTheme(this);

        setContentView(R.layout.multi_fragment);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.action_bar_logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("");

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.menu_container, new com.example.menu.Menu()).commit();
        }
    }
}

