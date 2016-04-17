package com.example.canmorpro.whatsinyourfridge3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Themes.onActivityCreateSetTheme(this);

        setContentView(R.layout.multi_fragment);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.action_bar_logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.menu_container, new com.example.menu.Menu()).commit();
        }
    }
}

