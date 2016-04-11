package com.example.views;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import com.example.canmorpro.whatsinyourfridge3.R;
import com.example.menu.Menu;


/**
 * Created by CanMorPro on 16-04-06.
 */
public class MultiViews extends AppCompatActivity {

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
}
