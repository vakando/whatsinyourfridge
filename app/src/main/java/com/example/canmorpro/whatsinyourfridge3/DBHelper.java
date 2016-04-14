package com.example.canmorpro.whatsinyourfridge3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "whats_in_your_fridge.db";

    // Table Name and table columns
    public static final String TABLE_RECIPES = "recipes";
    public static final String KEY_R_ID = "_id";
    public static final String KEY_R_NAME = "recipeName";
    public static final String KEY_R_I_NB = "ingredientNb";
    public static final String KEY_R_URL = "imageUrl";
    public static final String KEY_R_FAV = "favorites";
    public static final String KEY_R_DATE = "date";
    public static final String KEY_R_TMP = "temp";
    public static final String KEY_R_VIEW = "viewed";


    public static final String TABLE_INGREDIENTS = "ingredients";
    public static final String KEY_I_ID = "_id";
    public static final String KEY_I_NAME = "ingredientName";
    public static final String KEY_I_SL = "shoppingList";
    public static final String KEY_I_CHECK = "checked";


    public static final String TABLE_PREPARATIONS = "preparations";
    public static final String KEY_P_ID = "_id";
    public static final String KEY_P_R_ID = "idRecipe";
    public static final String KEY_P_PREP = "preparation";

    public static final String TABLE_LINK = "linkRecipeIngredients";
    public static final String KEY_L_ID = "_id";
    public static final String KEY_L_R_ID = "idRecipe";
    public static final String KEY_L_I_ID = "idIngredient";
    public static final String KEY_L_SL = "shoppingList";

    public static final String TABLE_AUTOCOMPLETE_INGREDIENT = "autocomplete_ingredient";
    public static final String KEY_AI_ID = "_id";
    public static final String KEY_AI_NAME = "ingredientName";

    public static final String TABLE_AUTOCOMPLETE_RECIPE = "autocomplete_recipe";
    public static final String KEY_AR_ID = "_id";
    public static final String KEY_AR_NAME = "recipeName";

    public static final String TABLE_REQUEST = "request";
    public static final String KEY_REQ_ID = "_id";
    public static final String KEY_REQ_NUM = "nombreResultats";

//
//    //Table Columns
//    static final String KEY_LINK_ID = "_idLink";
//    static final String KEY_REQUEST_ID = "_id";
//    static final String KEY_AUTO_COMP_INGREDIENT_ID = "_id";
//    static final String KEY_AUTO_COMP_RECIPE_ID = "_id";
//    static final String KEY_ID_RECIPE = "_idRecipe";
//    static final String KEY_RECIPE_NAME = "recipeName";
//    static final String KEY_INGREDIENT_NB = "ingredientNB";
//    static final String KEY_RECIPE_IMAGE_URL = "recipeImageURL";
//    static final String KEY_FAVORITES = "favorites";
//    static final String KEY_TO_DO_DATE_TIME = "toDoDateTime";
//    static final String KEY_ID_INGREDIENT = "_id";
//    static final String KEY_INGREDIENT_NAME = "ingredientName";
//    static final String KEY_SHOPPING_LIST = "shoppingList";
//    static final String KEY_PREPARATION = "preparations";
//    static final String KEY_RESPONSE = "response";
//    static final String KEY_NUMBER_RESULT = "numberResults";
//    static final String KEY_CHECKED = "checked";

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
                + KEY_R_ID + " INTEGER PRIMARY KEY, "
                + KEY_R_NAME + " TEXT, "
                + KEY_R_I_NB + " INTEGER, "
                + KEY_R_URL + " TEXT, "
                + KEY_R_FAV + " INTEGER, "
                + KEY_R_DATE + " TEXT, "
                + KEY_R_TMP + " INTEGER, "
                + KEY_R_VIEW + " INTEGER);";
        Log.d("TABLE_RECIPES", CREATE_TABLE_RECIPES);
        db.execSQL(CREATE_TABLE_RECIPES);

        String CREATE_TABLE_INGREDIENTS = "CREATE TABLE " + TABLE_INGREDIENTS + " ("
                + KEY_I_ID + " INTEGER PRIMARY KEY, "
                + KEY_I_NAME + " TEXT, "
                + KEY_I_SL + " INTEGER, "
                + KEY_I_CHECK + " INTEGER);";
        db.execSQL(CREATE_TABLE_INGREDIENTS);
        Log.d("TABLE_INGREDIENTS", CREATE_TABLE_INGREDIENTS);

        String CREATE_TABLE_PREPARATIONS = "CREATE TABLE " + TABLE_PREPARATIONS + " ("
                + KEY_P_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_P_R_ID + " INTEGER, "
                + KEY_P_PREP + " TEXT);";
        Log.d("TABLE_PREPARATIONS", CREATE_TABLE_PREPARATIONS);
        db.execSQL(CREATE_TABLE_PREPARATIONS);

        String CREATE_TABLE_LINK = "CREATE TABLE " + TABLE_LINK + " ("
                + KEY_L_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_L_R_ID + " INTEGER, "
                + KEY_L_I_ID + " INTEGER, "
                + KEY_L_SL + " INTEGER);";
        Log.d("TABLE_LINK", CREATE_TABLE_LINK);
        db.execSQL(CREATE_TABLE_LINK);

        String CREATE_TABLE_AUTOCOMPLETE_INGREDIENT = "CREATE TABLE " + TABLE_AUTOCOMPLETE_INGREDIENT + " ("
                + KEY_AI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_AI_NAME + " TEXT);";
        Log.d("TABLE_AUTOC_INGREDIENT", CREATE_TABLE_AUTOCOMPLETE_INGREDIENT);
        db.execSQL(CREATE_TABLE_AUTOCOMPLETE_INGREDIENT);

        String CREATE_TABLE_AUTOCOMPLETE_RECIPE = "CREATE TABLE " + TABLE_AUTOCOMPLETE_RECIPE + " ("
                + KEY_AR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_AR_NAME + " TEXT);";
        Log.d("TABLE_AUTOC_RECIPE", CREATE_TABLE_AUTOCOMPLETE_RECIPE);
        db.execSQL(CREATE_TABLE_AUTOCOMPLETE_RECIPE);

        String CREATE_TABLE_REQUEST = "CREATE TABLE " + TABLE_REQUEST + " ("
                + KEY_REQ_ID + " INTEGER PRIMARY KEY, "
                + KEY_REQ_NUM + " INTEGER);";
        Log.d("CREATE_TABLE_REQUEST", CREATE_TABLE_AUTOCOMPLETE_RECIPE);
        db.execSQL(CREATE_TABLE_REQUEST);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES
                + TABLE_INGREDIENTS
                + TABLE_PREPARATIONS
                + TABLE_LINK
                + TABLE_AUTOCOMPLETE_INGREDIENT
                + TABLE_AUTOCOMPLETE_RECIPE + ";");

        // Create tables again
        onCreate(db);
    }

    //retourne un cursor avec les id des ingredients de la recette avec id recipeID
    public Cursor getIngredientsIDByRecipeID(int recipeId){
        return db.query(TABLE_LINK, new String[]{KEY_L_ID, KEY_L_I_ID}, KEY_L_R_ID + " = ?", new String[]{"" + recipeId}, null, null, null);
        //return db.rawQuery("select * from " + TABLE_LINK + " where recipe_id=" + recipeId + "", null);
    }

    public Cursor getIngredientNameById(int ingredientId){
        return db.query(TABLE_INGREDIENTS, new String[]{KEY_I_ID,KEY_I_NAME}, KEY_I_ID + " = ?", new String[]{""+ingredientId}, null, null, null);
        //return  db.rawQuery("select " + KEY_INGREDIENT_NAME + " from " + TABLE_INGREDIENTS + " where id_ingredient=" + ingredientId + "", null);
    }

    public Cursor getShoppingList(){
        return db.query(TABLE_INGREDIENTS, new String[]{KEY_I_ID, KEY_I_NAME, KEY_I_CHECK}, KEY_I_SL + " = ?", new String[]{"1"}, null, null, null);
//        return db.rawQuery( "select * from "+TABLE_INGREDIENTS+" where shoppingList='true'", null );
    }

    public Cursor getFavorites(){
        //return db.query(TABLE_RECIPES, new String[]{KEY_R_ID, KEY_R_NAME}, KEY_R_FAV + " = ?", new String[]{"1"}, null, null, null);
        return db.rawQuery("select * from " + TABLE_RECIPES + " where " + KEY_R_FAV + " = 1", null);
    }

    public Cursor getPreparationsByRecipeId( int recipeId){
        return db.query(TABLE_PREPARATIONS, new String[]{KEY_P_R_ID,KEY_P_PREP}, KEY_P_R_ID + " = ?", new String[]{""+recipeId}, null, null, null);
        //return db.rawQuery("select * from " + TABLE_PREPARATIONS + " where id_recipe=" + recipeId, null);
    }

    public Cursor getIngredientIdByName(String name){
        return db.query(TABLE_INGREDIENTS, new String[]{KEY_I_ID}, KEY_I_NAME + " = ?", new String[]{name}, null, null, null);
    }

    //je crois que je ne l'utilise plus
//    public void setShoppingListValue(int id, int value){
//        ContentValues cv = new ContentValues();
//        cv.put(KEY_I_ID, id);
//        cv.put(KEY_I_SL,value);
//        db.replace(TABLE_INGREDIENTS, null, cv);
//        Log.d("replaced",KEY_I_SL);
//    }

    //enleve des ingredients de la table TABLE_INGREDIENTS
    public void removeIngredient(){
        db.delete(TABLE_INGREDIENTS, KEY_I_SL + " = ?", new String[]{"0"});
        Log.d("removed", KEY_I_SL);
    }

    //ajoute des ingredients pour l'autocompleteIngredient
    public void addIngredients(String name){
        ContentValues cv = new ContentValues();
        cv.put(KEY_AI_NAME, name);
        try{
            db.insertOrThrow(TABLE_AUTOCOMPLETE_INGREDIENT, null, cv);
        }catch (SQLException e){}
        Log.d("inserted", name);
    }

    //retourne tous les ingredients de la table de l'autocompleteIngredient
    public ArrayList<String> getAllIngredients(){
        Cursor cursor = db.query(TABLE_AUTOCOMPLETE_INGREDIENT, new String[] {KEY_AI_ID,KEY_AI_NAME}, null, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0)
        {
            ArrayList<String> str = new ArrayList<>();
            while (cursor.moveToNext())
            {
                str.add(cursor.getString(cursor.getColumnIndex(KEY_AI_NAME)));
            }
            return str;
        }
        else
            return new ArrayList<String>(){};
    }

    //pour ajouter un ingredient dans la shopping list
    public void addInShoppingList(String name){
        ContentValues value = new ContentValues();
        value.put(KEY_I_SL, 1);
        db.update(TABLE_INGREDIENTS, value, KEY_I_NAME + " = ?", new String[]{name});

    }

    //selon la variable d'entree check, on sait si l'ingredient a ete selectionne ou non
    public void setCheck(String name, int check){
        ContentValues value = new ContentValues();
        value.put(KEY_I_CHECK, check);
        db.update(TABLE_INGREDIENTS, value, KEY_I_NAME + " = ?", new String[]{name});

    }

    //met shoppingList a 0 donc false
    public void removeFromShoppingList(String name){
        ContentValues value = new ContentValues();
        value.put(KEY_I_SL, 0);
        db.update(TABLE_INGREDIENTS, value, KEY_I_NAME + " = ?", new String[]{name});
        Log.d("removedFromSL", name);
    }

    //ajoute des ingredients dans la table ingredients
    public long setIngredients(int id, String name, int shoppingList, int check){
        ContentValues cv = new ContentValues();
        cv.put(KEY_I_ID,id);
        cv.put(KEY_I_NAME, name);
        cv.put(KEY_I_SL, shoppingList);
        cv.put(KEY_I_CHECK, check);
        long result = 0;
        try{
            result = db.insertOrThrow(TABLE_INGREDIENTS, null, cv);
        }catch (SQLException e){}
        Log.d("inserted", name);
        return result;
    }

    public Cursor getIngredientsNamesByRecipeId(int IdRecipe){
        return db.rawQuery( "select " + KEY_I_NAME + " from " + TABLE_INGREDIENTS + " , " + TABLE_LINK + " WHERE " + TABLE_LINK + "." + KEY_L_R_ID + " = " + IdRecipe + " AND " + TABLE_LINK + "." + KEY_L_I_ID + " = " + TABLE_INGREDIENTS + "." + KEY_I_ID, null );
    }

    public Cursor getRecipeNameByIngredientId(int ingredientId){
        return db.rawQuery("SELECT " + KEY_R_NAME + " FROM " + TABLE_RECIPES + ", " + TABLE_LINK + " WHERE " + TABLE_LINK + "." + KEY_L_I_ID + " = " + ingredientId, null);
    }

    public Cursor getShoppingList() {
        return db.rawQuery("SELECT " + TABLE_INGREDIENTS + "." + KEY_I_ID + ", " + KEY_I_NAME + ", " + KEY_R_NAME + ", " + KEY_I_CHECK
                + " FROM " + TABLE_INGREDIENTS + " LEFT OUTER JOIN " + TABLE_LINK
                + " ON (" + TABLE_LINK + "." + KEY_L_I_ID + " = " + TABLE_INGREDIENTS + "." + KEY_I_ID + " AND " + TABLE_LINK + "." + KEY_L_SL + " = 1) "
                + " LEFT OUTER JOIN " + TABLE_RECIPES + " ON " + TABLE_LINK + "." + KEY_L_R_ID + " = " + TABLE_RECIPES + "." + KEY_R_ID, null);
    }

    public void setRecettes(int id, String name, int nbIngredients, String photoUrl, int favorite, String date, int temp, int view){
        ContentValues cv = new ContentValues();
        cv.put(KEY_R_ID,id);
        cv.put(KEY_R_NAME, name);
        cv.put(KEY_R_I_NB, nbIngredients);
        cv.put(KEY_R_URL, photoUrl);
        cv.put(KEY_R_FAV,favorite);
        cv.put(KEY_R_DATE, date);
        cv.put(KEY_R_TMP, temp);
        cv.put(KEY_R_VIEW, view);
        try{
            db.insertOrThrow(TABLE_RECIPES, null, cv);
        }catch (SQLException e){}

        Log.d("inserted", name);

    }

 /*
    public void setLinkRecetteIng(int idRecipe, int idIngredient, int shoppingList){

        ContentValues cv = new ContentValues();
        cv.put(KEY_L_R_ID,idRecipe);
        cv.put(KEY_L_I_ID, idIngredient);
        cv.put(KEY_L_SL, shoppingList);
        try{
            db.insertOrThrow(TABLE_LINK, null, cv);
        }catch (SQLException e){}

    }

   */

    public void setLinkRecipeIngredients(int id,int idRecipe,int idIngredient,int shoppingList){
        ContentValues cv = new ContentValues();
        cv.put(KEY_L_ID,id);
        cv.put(KEY_L_R_ID, idRecipe);
        cv.put(KEY_L_I_ID, idIngredient);
        cv.put(KEY_L_SL, shoppingList);

        try{
            db.insertOrThrow(TABLE_LINK, null, cv);
        }catch (SQLException e){}
    }



    public void setPreparation(int recipeId, String preparation){
        ContentValues cv = new ContentValues();
        cv.put(KEY_P_R_ID, recipeId);
        cv.put(KEY_P_PREP, preparation);
        try{
            db.insertOrThrow(TABLE_PREPARATIONS, null, cv);
        }catch (SQLException e){}
    }

    public Cursor getAllRecipes(){
        return db.query(TABLE_RECIPES, new String[]{KEY_R_ID, KEY_R_NAME}, KEY_R_NAME, null, null, null, null);
    }

    public Cursor getRecipesTmpTrue(){
        return db.query(TABLE_RECIPES, new String[]{KEY_R_ID, KEY_R_NAME}, KEY_R_TMP + " = ?", new String[]{"1"}, null, null, null);
    }


    public void deleteAllFromTable(){
        db.delete(DBHelper.TABLE_RECIPES, null, null);
        db.delete(DBHelper.TABLE_LINK, null, null);
    }

    public void clearRecipeTable(){
        db.delete(TABLE_RECIPES, KEY_R_TMP + " = ?", new String[]{"1"});
        Log.d("deleted", TABLE_RECIPES);
    }
    public void clearIngredientTable(){
        db.delete(TABLE_INGREDIENTS, KEY_I_SL + " = ?", new String[]{"0"});
        Log.d("deleted", TABLE_INGREDIENTS);
    }

    public void clearPreparationTable(){
        db.delete(TABLE_PREPARATIONS, null, null);
        Log.d("deleted", TABLE_PREPARATIONS);
    }
}