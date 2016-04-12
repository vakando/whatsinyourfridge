package com.example.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.canmorpro.whatsinyourfridge3.R;

/**
 * Created by CanMorPro on 16-04-06.
 */
public class Search extends Fragment {


    public Search(){
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.search ,container , false);


        return rootView;
    }
}
