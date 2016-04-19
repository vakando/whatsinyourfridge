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

    public static final String TABLE_THEMES = "themes";
    public static final String KEY_T_ID = "_id";
    public static final String KEY_T_NAME = "themeName";
    public static final String KEY_T_ENABLE = "enabled";

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
                + KEY_REQ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_REQ_NUM + " INTEGER);";
        Log.d("CREATE_TABLE_REQUEST", CREATE_TABLE_REQUEST);
        db.execSQL(CREATE_TABLE_REQUEST);

        String CREATE_TABLE_THEMES = " CREATE TABLE " + TABLE_THEMES + " ("
                + KEY_T_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_T_NAME + " TEXT, "
                + KEY_T_ENABLE + " INTEGER);";
        Log.d("CREATE_TABLE_THEMES", CREATE_TABLE_THEMES);
        db.execSQL(CREATE_TABLE_THEMES);
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

    public Cursor getFavorites(){
        //return db.query(TABLE_RECIPES, new String[]{KEY_R_ID, KEY_R_NAME}, KEY_R_FAV + " = ?", new String[]{"1"}, null, null, null);
        return db.rawQuery("select * from " + TABLE_RECIPES + " where " + KEY_R_FAV + " = 1", null);
    }

    public Cursor getPreparationsByRecipeId( int recipeId){
//        return db.query(TABLE_PREPARATIONS, new String[]{KEY_P_ID, KEY_P_R_ID, KEY_P_PREP}, KEY_P_R_ID + " = ?", new String[]{"" + recipeId}, null, null, null);
        return db.rawQuery("select * from " + TABLE_PREPARATIONS + " where "+KEY_P_R_ID+" = " + recipeId, null);
    }

    public Cursor getIngredientIdByName(String name){
        return db.query(TABLE_INGREDIENTS, new String[]{KEY_I_ID,KEY_I_NAME}, KEY_I_NAME + " = ?", new String[]{name}, null, null, null);
//        return db.rawQuery("select * from " + TABLE_INGREDIENTS + " where "+KEY_I_NAME+" = " + name, null);

    }

    //je crois que je ne l'utilise plus
//    public void setShoppingListValue(int id, int value){
//        ContentValues cv = new ContentValues();
//        cv.put(KEY_I_ID, id);
//        cv.put(KEY_I_SL,value);
//        db.replace(TABLE_INGREDIENTS, null, cv);
//        Log.d("replaced",KEY_I_SL);
//    }

    //ajoute des ingredients pour l'autocompleteIngredient
    public void addIngredients(String name){
        ContentValues cv = new ContentValues();
        cv.put(KEY_AI_NAME, name);
        try{
            db.insertOrThrow(TABLE_AUTOCOMPLETE_INGREDIENT, null, cv);
        }catch (SQLException e){}
        Log.d("inserted", name);
    }

    public void setRequestCount(String count){
        ContentValues cv = new ContentValues();
        cv.put(KEY_REQ_NUM, count);
        try{
            db.insertOrThrow(TABLE_REQUEST, null, cv);
        }catch (SQLException e){}
        Log.d("inserted", count);
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
        Log.d("ingredient added in shopping list",name);
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
        return db.rawQuery("SELECT " + KEY_I_NAME + " FROM " + TABLE_LINK  + ", " + TABLE_INGREDIENTS + " WHERE " + TABLE_LINK + "." + KEY_L_R_ID + " = " + IdRecipe + " AND " + TABLE_LINK + "." + KEY_L_I_ID + " = " + TABLE_INGREDIENTS + "." + KEY_I_ID, null );
    }

    public Cursor getRecipeNameByIngredientId(int ingredientId){
        return db.rawQuery("SELECT " + KEY_R_NAME + " FROM " + TABLE_RECIPES + ", " + TABLE_LINK + " WHERE " + TABLE_LINK + "." + KEY_L_I_ID + " = " + ingredientId, null);
    }

    public Cursor getShoppingList() {
        return db.rawQuery("SELECT " + TABLE_INGREDIENTS + "." + KEY_I_ID + ", " + KEY_I_NAME + ", " + KEY_R_NAME + ", " + KEY_I_CHECK
                + " FROM " + TABLE_INGREDIENTS + " INNER JOIN " + TABLE_LINK
                + " ON (" + TABLE_LINK + "." + KEY_L_I_ID + " = " + TABLE_INGREDIENTS + "." + KEY_I_ID + " AND " + TABLE_LINK + "." + KEY_L_SL + " = 1)"// AND " + TABLE_INGREDIENTS + "." + KEY_I_SL + " = 1)"
                + " INNER JOIN " + TABLE_RECIPES + " ON " + TABLE_LINK + "." + KEY_L_R_ID + " = " + TABLE_RECIPES + "." + KEY_R_ID , null);
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

    public void setLinkRecetteIng(int recipeId, int ingredientId, int sl){
        Cursor c = db.query(TABLE_LINK, new String[]{KEY_L_ID}, KEY_L_I_ID + " = ? AND " + KEY_L_R_ID + " = ?", new String[]{""+ingredientId,""+recipeId}, null, null, null);
        if(c.getCount() <= 0) {
            ContentValues cv = new ContentValues();
            cv.put(KEY_L_R_ID, recipeId);
            cv.put(KEY_L_I_ID, ingredientId);
            cv.put(KEY_L_SL, sl);
            try {
                db.insertOrThrow(TABLE_LINK, null, cv);
            } catch (SQLException e) {}
            Log.d("inserted", "recette :" + recipeId + ", ingredient :" + ingredientId + " in sl" + sl);
        }
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
        return db.rawQuery("select * from " + TABLE_RECIPES + " where " + KEY_R_TMP + " = 1", null);
    }

    public Cursor getSearchCount(){

     return db.rawQuery("select * from "+TABLE_REQUEST+" ",null);
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

    public void clearTableRequest(){
        db.delete(TABLE_REQUEST, null, null);
        Log.d("deleted", TABLE_REQUEST);

    }

    public int checkTable(){
        Cursor c = db.query(TABLE_AUTOCOMPLETE_INGREDIENT, new String[]{KEY_AI_ID}, null, null, null, null, null);
        return c.getCount();
    }

    public void setTableThemes(String themeName, int enabled){
        ContentValues cv = new ContentValues();
        cv.put(KEY_T_NAME, themeName);
        cv.put(KEY_T_ENABLE, enabled);
        try {
            db.insertOrThrow(TABLE_THEMES, null, cv);
        } catch (SQLException e) {}
        Log.d("theme inserted", themeName);
    }

    public void setEnable(String name, int enable){
        ContentValues value = new ContentValues();
        value.put(KEY_T_ENABLE, enable);
        db.update(TABLE_THEMES, value, KEY_T_NAME + " = ?", new String[]{name});
    }

    public Cursor getEnable(){
        return  db.query(TABLE_THEMES, new String[]{KEY_T_ID, KEY_T_NAME, KEY_T_ENABLE}, null, null, null, null, null);
    }

    public void updateLink(int idRecipe, int idIngredient, int shoppingList){
        ContentValues cv = new ContentValues();
        cv.put(KEY_L_SL, shoppingList);
        db.update(TABLE_LINK, cv, KEY_L_I_ID + " = ? AND " + KEY_L_R_ID + " = ?", new String[]{""+idIngredient,""+idRecipe});
    }

    public void setFavorit(int idReceipe, int valeur){
        ContentValues value = new ContentValues();
        value.put(KEY_R_FAV, valeur);
        db.update(TABLE_RECIPES, value, KEY_R_ID + " = ?", new String[]{Integer.toString(idReceipe)});
    }

    public int getFavorit(int idReceipe){
        Cursor c = db.rawQuery("select " + KEY_R_FAV + " from " + TABLE_RECIPES + " where " + KEY_R_ID + " = " + idReceipe, null);
        c.moveToFirst();
        return c.getInt(c.getColumnIndexOrThrow(DBHelper.KEY_R_FAV));
    }

}

