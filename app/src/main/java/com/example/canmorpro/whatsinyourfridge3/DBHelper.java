package com.example.canmorpro.whatsinyourfridge3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;



/**
 * Created by CanMorPro on 16-03-25.
 */
public class DBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "whats_in_your_fridge.db";

    // Tables Name
    public static final String TABLE_RECIPES = "recipes";
    public static final String TABLE_INGREDIENTS = "ingredients";
    public static final String TABLE_PREPARATIONS = "preparations";
    public static final String TABLE_LINK_RECIPE_INGREDIENT = "link_recipe_ingredients";
//    private static final String TABLE_AUTOCOMPLETE_INGREDIENT = "autocomplete_ingredient";
//    private static final String TABLE_AUTOCOMPLETE_RECIPE = "autocomplete_recipe";
    public static final String TABLE_REQUEST = "request";



    //Table Columns
    public static final String KEY_LINK_ID = "_id";
    public static final String KEY_REQUEST_ID = "_id";
//    private static final String KEY_AUTO_COMP_INGREDIENT_ID = "_id";
//    private static final String KEY_AUTO_COMP_RECIPE_ID = "_id";
    public static final String KEY_ID_RECIPE = "id_recipe";
    public static final String KEY_TMP = "tmp";
    public static final String KEY_RECIPE_NAME = "recipe_name";
    public static final String KEY_INGREDIENT_NB = "ingredient_nb";
    public static final String KEY_RECIPE_IMAGE_URL = "recipe_image_url";
    public static final String KEY_FAVORITES = "favorites";
    public static final String KEY_TO_DO_DATE_TIME = "to_do_date_time";
    public static final String KEY_ID_INGREDIENT = "id_ingredient";
    public static final String KEY_INGREDIENT_NAME = "ingredient_name";
    public static final String KEY_SHOPPING_LIST = "shopping_list";
    public static final String KEY_PREPARATION = "preparations";
//    private static final String KEY_RESPONSE = "response";
//    private static final String KEY_NUMBER_RESULT = "number_results";


    private static SQLiteDatabase db = null;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if(db == null)
            db = getWritableDatabase();
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_RECIPES = "CREATE TABLE " + TABLE_RECIPES + " ("
                + KEY_ID_RECIPE + " INTEGER PRIMARY KEY, "
                + KEY_RECIPE_NAME + " TEXT, "
                + KEY_INGREDIENT_NB + " INTEGER, "
                + KEY_RECIPE_IMAGE_URL + " TEXT, "
                + KEY_FAVORITES + " BOOLEAN, "
                + KEY_TO_DO_DATE_TIME + " TEXT);";
        Log.d("CREATE_TABLE_RECIPES", CREATE_TABLE_RECIPES);
        db.execSQL(CREATE_TABLE_RECIPES);

        String CREATE_TABLE_INGREDIENTS = "CREATE TABLE " + TABLE_INGREDIENTS + " ("
                + KEY_ID_INGREDIENT + " INTEGER PRIMARY KEY, "
                + KEY_INGREDIENT_NAME + " TEXT, "
                + KEY_TMP + " BOOLEAN, "
                + KEY_SHOPPING_LIST + " BOOLEAN);";
        Log.d("TABLE_INGREDIENTS", CREATE_TABLE_INGREDIENTS);
        db.execSQL(CREATE_TABLE_INGREDIENTS);

        String CREATE_TABLE_PREPARATIONS = "CREATE TABLE " + TABLE_PREPARATIONS + " ("
                + KEY_ID_RECIPE + " INTEGER PRIMARY KEY, "
                + KEY_PREPARATION + " TEXT);";
        Log.d("TABLE_PREPARATIONS", CREATE_TABLE_PREPARATIONS);
        db.execSQL(CREATE_TABLE_PREPARATIONS);

        String CREATE_TABLE_LINK_RECIPE_INGREDIENT = "CREATE TABLE " + TABLE_LINK_RECIPE_INGREDIENT + " ("
                + KEY_LINK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_ID_RECIPE + " INTEGER, "
                + KEY_ID_INGREDIENT + " INTEGER, "
                + KEY_SHOPPING_LIST + " BOOLEAN);";
        Log.d("LINK_RECIPE_INGREDIENT", CREATE_TABLE_LINK_RECIPE_INGREDIENT);
        db.execSQL(CREATE_TABLE_LINK_RECIPE_INGREDIENT);
//
//        String CREATE_TABLE_AUTOCOMPLETE_INGREDIENT = "CREATE TABLE " + TABLE_AUTOCOMPLETE_INGREDIENT + " ("
//                + KEY_AUTO_COMP_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + KEY_INGREDIENT_NAME + " TEXT);";
//        Log.d("AUTOCOMPLETE_INGREDIENT", CREATE_TABLE_AUTOCOMPLETE_INGREDIENT);
//        db.execSQL(CREATE_TABLE_AUTOCOMPLETE_INGREDIENT);
//
//        String CREATE_TABLE_AUTOCOMPLETE_RECIPE = "CREATE TABLE " + TABLE_AUTOCOMPLETE_RECIPE + " ("
//                + KEY_AUTO_COMP_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + KEY_RECIPE_NAME + " TEXT);";
//        Log.d("AUTOCOMPLETE_RECIPE", CREATE_TABLE_AUTOCOMPLETE_RECIPE);
//        db.execSQL(CREATE_TABLE_AUTOCOMPLETE_RECIPE);

//        String CREATE_TABLE_REQUEST = "CREATE TABLE " + TABLE_REQUEST + " ("
//                + KEY_REQUEST_ID + " INTEGER PRIMARY KEY, "
//                + KEY_RESPONSE + " TEXT, "
//                + KEY_NUMBER_RESULT + " INTEGER);";
////    Log.d("CREATE_TABLE_REQUEST", CREATE_TABLE_AUTOCOMPLETE_RECIPE);
//        db.execSQL(CREATE_TABLE_REQUEST);
    }




    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES
                + TABLE_INGREDIENTS
                + TABLE_PREPARATIONS
                + TABLE_LINK_RECIPE_INGREDIENT + ";");

        // Create tables again
        onCreate(db);
    }


    public Cursor getIngredientsIDByRecipeID(int recipeId){
        Cursor c =  db.rawQuery( "select * from "+TABLE_LINK_RECIPE_INGREDIENT+" where recipe_id="+recipeId+"", null );
        return c;
    }

    public Cursor getIngredientNameById(int ingredientId){
        Cursor c =  db.rawQuery( "select "+KEY_INGREDIENT_NAME+" from "+TABLE_INGREDIENTS+" where id_ingredient="+ingredientId+"", null );
        return c;
    }

    public Cursor getShoppingList(){
        Cursor c =  db.rawQuery( "select * from "+TABLE_INGREDIENTS+" where shopping_list=true", null );
        return c;
    }

    public Cursor getFavorites(){
        Cursor c =  db.rawQuery( "select * from "+TABLE_RECIPES+" where favorites=true", null );
        return c;
    }

    public Cursor getTmp(){
        Cursor c =  db.rawQuery( "select * from "+TABLE_RECIPES+" where tmp=true", null );
        return c;
    }

    public Cursor getPreparationsByRecipeId( int recipeId){
        Cursor c =  db.rawQuery( "select * from "+TABLE_PREPARATIONS+" where id_recipe="+recipeId+"", null );
        return c;
    }

    // Je retourne une ArrayList, je ne sais pas s'il y a une meilleure façon de faire
    public ArrayList getIngredientsNameByRecipeID(int recipeID) {
        Cursor c = getIngredientsIDByRecipeID(recipeID);
        c.moveToFirst();
        ArrayList list = new ArrayList();
        while (!c.isAfterLast()) {
            list.add( getIngredientNameById(c.getInt(c.getColumnIndex(DBHelper.KEY_ID_INGREDIENT))));
            c.moveToNext();
        }
        return list;
    }

}
