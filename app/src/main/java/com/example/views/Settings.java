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

import com.example.canmorpro.whatsinyourfridge3.R;

/**
 * Created by Empress on 2016-04-14.
 */
public class Settings  extends Fragment implements View.OnClickListener {
    private Button blackTheme;
    private Button redTheme;
    private Button defaultTheme;
    private Button blueTheme;
    private DBHelper dbh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.settings, container, false);

        dbh = new DBHelper(getContext());
        Log.d("dbh in settings","done");

        Cursor c = dbh.getEnable();

        redTheme = (Button)rootView.findViewById(R.id.theme3);
        defaultTheme = (Button)rootView.findViewById(R.id.theme4);
        blackTheme = (Button)rootView.findViewById(R.id.theme2);
        blueTheme = (Button)rootView.findViewById(R.id.theme1);

        redTheme.setOnClickListener(this);
        defaultTheme.setOnClickListener(this);
        blackTheme.setOnClickListener(this);
        blueTheme.setOnClickListener(this);

        if(Themes.cTheme == 0 || Themes.cTheme == 3){
            redTheme.setEnabled(true);
            defaultTheme.setEnabled(false);
            blackTheme.setEnabled(true);
            blueTheme.setEnabled(true);
        }
        else  if(Themes.cTheme == 2){
            redTheme.setEnabled(false);
            defaultTheme.setEnabled(true);
            blackTheme.setEnabled(true);
            blueTheme.setEnabled(true);
        }
        else if(Themes.cTheme == 1){
            redTheme.setEnabled(true);
            defaultTheme.setEnabled(true);
            blackTheme.setEnabled(false);
            blueTheme.setEnabled(true);
        }
        else if(Themes.cTheme == 4){
            redTheme.setEnabled(true);
            defaultTheme.setEnabled(true);
            blackTheme.setEnabled(true);
            blueTheme.setEnabled(false);
        }

//        c.moveToFirst();
//        while(!c.isAfterLast()){
//            if(c.getString(c.getColumnIndex(DBHelper.KEY_T_NAME)) == "Red"){
//                Log.d("theme found","red");
//                if(c.getInt(c.getColumnIndex(DBHelper.KEY_T_ENABLE)) == 0)
//                    redTheme.setEnabled(false);
//                else
//                    redTheme.setEnabled(true);
//            }
//            if(c.getString(c.getColumnIndex(DBHelper.KEY_T_NAME)) == "Default"){
//                Log.d("theme found", "blue");
//                if(c.getInt(c.getColumnIndex(DBHelper.KEY_T_ENABLE)) == 0)
//                    defaultTheme.setEnabled(false);
//                else
//                    defaultTheme.setEnabled(true);
//            }
//            if(c.getString(c.getColumnIndex(DBHelper.KEY_T_NAME)) == "Grey"){
//                Log.d("theme found", "black");
//                if(c.getInt(c.getColumnIndex(DBHelper.KEY_T_ENABLE)) == 0)
//                    blackTheme.setEnabled(false);
//                else
//                    blackTheme.setEnabled(true);
//            }
//            c.moveToNext();
//        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.theme2:
                dbh.setEnable("Red", 1);
                dbh.setEnable("Black", 0);
                dbh.setEnable("Default", 1);
                dbh.setEnable("Blue",1);
                Themes.changeToTheme(getActivity(), Themes.BLACK);
                break;
            case R.id.theme3:
                dbh.setEnable("Red", 0);
                dbh.setEnable("Black", 1);
                dbh.setEnable("Default", 1);
                dbh.setEnable("Blue",1);
                Themes.changeToTheme(getActivity(), Themes.RED);
                break;
            case R.id.theme4:
                dbh.setEnable("Red", 1);
                dbh.setEnable("Black", 1);
                dbh.setEnable("Default",0);
                dbh.setEnable("Blue",1);
                Themes.changeToTheme(getActivity(), Themes.DEFAULT);
                break;
            case R.id.theme1:
                dbh.setEnable("Red", 1);
                dbh.setEnable("Black", 1);
                dbh.setEnable("Default", 1);
                dbh.setEnable("Blue",0);
                Themes.changeToTheme(getActivity(), Themes.BLUE);
                break;
        }
    }
}
