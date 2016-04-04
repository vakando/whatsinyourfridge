package com.example.canmorpro.whatsinyourfridge3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DBHelper dbHelper = new DBHelper(MainActivity.this);

        IngredientSearch ingSearch = new IngredientSearch("garlic","","");
        ingSearch.storeData();

        RecipeSearch recipSearch = new RecipeSearch("omelette");
        recipSearch.storeData();

    }
}
