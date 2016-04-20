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
    private Button blueTheme;
    private DBHelper dbh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.settings, container, false);

        dbh = new DBHelper(getContext());
        Log.d("dbh in settings","done");

        Cursor c = dbh.getEnable();

        redTheme = (Button)rootView.findViewById(R.id.theme3);
        blueTheme = (Button)rootView.findViewById(R.id.theme4);
        blackTheme = (Button)rootView.findViewById(R.id.theme2);

        redTheme.setOnClickListener(this);
        blueTheme.setOnClickListener(this);
        blackTheme.setOnClickListener(this);

//        if(getActivity().getApplicationInfo().theme == 1){
//            redTheme.setEnabled(true);
//            blueTheme.setEnabled(true);
//            blackTheme.setEnabled(false);
//        }
//        else  if(getActivity().getApplicationInfo().theme == 2){
//            redTheme.setEnabled(false);
//            blueTheme.setEnabled(true);
//            blackTheme.setEnabled(true);
//        }
//        else if(getActivity().getApplicationInfo().theme == 1){
//            redTheme.setEnabled(true);
//            blueTheme.setEnabled(false);
//            blackTheme.setEnabled(true);
//        }

        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(DBHelper.KEY_T_NAME)) == "Red"){
                Log.d("theme found","red");
                if(c.getInt(c.getColumnIndex(DBHelper.KEY_T_ENABLE)) == 0)
                    redTheme.setEnabled(false);
                else
                    redTheme.setEnabled(true);
            }
            if(c.getString(c.getColumnIndex(DBHelper.KEY_T_NAME)) == "Blue"){
                Log.d("theme found","blue");
                if(c.getInt(c.getColumnIndex(DBHelper.KEY_T_ENABLE)) == 0)
                    blueTheme.setEnabled(false);
                else
                    blueTheme.setEnabled(true);
            }
            if(c.getString(c.getColumnIndex(DBHelper.KEY_T_NAME)) == "Grey"){
                Log.d("theme found", "black");
                if(c.getInt(c.getColumnIndex(DBHelper.KEY_T_ENABLE)) == 0)
                    blackTheme.setEnabled(false);
                else
                    blackTheme.setEnabled(true);
            }
            c.moveToNext();
        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.theme2:
                dbh.setEnable("Red", 1);
                dbh.setEnable("Black", 0);
                dbh.setEnable("Blue", 1);
                Themes.changeToTheme(getActivity(), Themes.BLACK);
                break;
            case R.id.theme3:
                dbh.setEnable("Red", 0);
                dbh.setEnable("Black", 1);
                dbh.setEnable("Blue", 1);
                Themes.changeToTheme(getActivity(), Themes.RED);
                break;
            case R.id.theme4:
                dbh.setEnable("Red", 1);
                dbh.setEnable("Black", 1);
                dbh.setEnable("Blue",0);
                Themes.changeToTheme(getActivity(), Themes.BLUE);
                break;
        }
    }
}
