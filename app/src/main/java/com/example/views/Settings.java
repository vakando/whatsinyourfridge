package com.example.views;

import com.example.canmorpro.whatsinyourfridge3.DBHelper;
import com.example.canmorpro.whatsinyourfridge3.Themes;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.canmorpro.whatsinyourfridge3.R;

/**
 * Created by Empress on 2016-04-14.
 */
public class Settings  extends Fragment implements View.OnClickListener {
    private Button defaultTheme;
    private Button greyTheme;
    private Button redTheme;
    private Button blueTheme;
    private DBHelper dbh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.settings, container, false);

        dbh = new DBHelper(getContext());
        Log.d("dbh in settings","done");

        Cursor c = dbh.getEnable();

        defaultTheme = (Button)rootView.findViewById(R.id.theme1);
        redTheme = (Button)rootView.findViewById(R.id.theme3);
        blueTheme = (Button)rootView.findViewById(R.id.theme4);
        greyTheme = (Button)rootView.findViewById(R.id.theme2);

        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(DBHelper.KEY_T_NAME)) == "Default") {
                if(c.getInt(c.getColumnIndex(DBHelper.KEY_T_ENABLE)) == 0)
                    defaultTheme.setEnabled(false);
                else
                    defaultTheme.setEnabled(true);
            }
            else if(c.getString(c.getColumnIndex(DBHelper.KEY_T_NAME)) == "Red"){
                if(c.getInt(c.getColumnIndex(DBHelper.KEY_T_ENABLE)) == 0)
                    redTheme.setEnabled(false);
                else
                    redTheme.setEnabled(true);
            }
            else if(c.getString(c.getColumnIndex(DBHelper.KEY_T_NAME)) == "Blue"){
                if(c.getInt(c.getColumnIndex(DBHelper.KEY_T_ENABLE)) == 0)
                    blueTheme.setEnabled(false);
                else
                    blueTheme.setEnabled(true);
            }
            else if(c.getString(c.getColumnIndex(DBHelper.KEY_T_NAME)) == "Grey"){
                if(c.getInt(c.getColumnIndex(DBHelper.KEY_T_ENABLE)) == 0)
                    greyTheme.setEnabled(false);
                else
                    greyTheme.setEnabled(true);
            }
            c.moveToNext();
        }

        defaultTheme.setOnClickListener(this);
        redTheme.setOnClickListener(this);
        blueTheme.setOnClickListener(this);
        greyTheme.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.theme1:
                dbh.setEnable("Default", 0);
                dbh.setEnable("Red", 1);
                dbh.setEnable("Grey", 1);
                dbh.setEnable("Blue", 1);
                Themes.changeToTheme(getActivity(), Themes.DEFAULT);
                break;
            case R.id.theme2:
                dbh.setEnable("Default", 1);
                dbh.setEnable("Red", 1);
                dbh.setEnable("Grey", 0);
                dbh.setEnable("Blue", 1);
                Themes.changeToTheme(getActivity(), Themes.GREY);
                break;
            case R.id.theme3:
                dbh.setEnable("Default", 1);
                dbh.setEnable("Red", 0);
                dbh.setEnable("Grey", 1);
                dbh.setEnable("Blue", 1);
                Themes.changeToTheme(getActivity(), Themes.RED);
                break;
            case R.id.theme4:
                dbh.setEnable("Default", 1);
                dbh.setEnable("Red", 1);
                dbh.setEnable("Grey", 1);
                dbh.setEnable("Blue",0);
                Themes.changeToTheme(getActivity(), Themes.BLUE);
                break;
        }
    }
}
