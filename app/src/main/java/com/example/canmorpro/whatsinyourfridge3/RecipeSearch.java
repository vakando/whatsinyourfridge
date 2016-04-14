package com.example.canmorpro.whatsinyourfridge3;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by CanMorPro on 16-03-31.
 */
public class RecipeSearch {


    private String url_recipesByKeyword, url_recipesById;
    private String keyword ;
    private int startRow, endRow;
    private HandleXML obj1,  obj2 ;

    private DBHelper dbh;

    private ArrayList<String> RecipeIdList =  new ArrayList<String>();
    private ArrayList<String> RecipeNameList =  new ArrayList<String>();
    private ArrayList<String> PhotoUrlList =  new ArrayList<String>();
    private ArrayList<String> NumberOfIngredientsList =  new ArrayList<String>();

    private ArrayList<String> IngredientIdList =  new ArrayList<String>();
    private ArrayList<String> IngredientNameList =  new ArrayList<String>();
    private ArrayList<String> PreparationDescriptionList =  new ArrayList<String>();


    public RecipeSearch(String keyword, DBHelper dbh){

        this.keyword = keyword;
        this.dbh= dbh;
        startRow = 1;
        endRow = 5;
        url_recipesByKeyword = "http://www.kraftfoods.com/ws/RecipeWS.asmx/GetRecipesByKeywords?sKeyword1="+keyword+"&sKeyword2=&sKeyword3=&sKeyword4=&sKeyword5=&sKeyword6=&bIsRecipePhotoRequired=true&bIsReadyIn30Mins=false&sSortField=&sSortDirection=&iBrandID=1&iLangID=1&iStartRow="+startRow+"&iEndRow="+endRow+"";

    }


    public void storeData(){

        obj1 = new HandleXML(url_recipesByKeyword);
        obj1.fetchXML();
//        System.out.println(url_recipesByKeyword);
        while(obj1.parsingComplete);

        obj1.getTotalCount();
        RecipeIdList= obj1.getRecipeID();
        RecipeNameList= obj1.getRecipeName();
        NumberOfIngredientsList= obj1.getNumberOfIngredients();
        PhotoUrlList= obj1.getPhotoURL();
        PreparationDescriptionList= obj1.getPreparationDescription();

        //        clear tables
        dbh.clearRecipeTable();
        dbh.clearIngredientTable();
        dbh.clearPreparationTable();

        for(int i=0; i<RecipeIdList.size(); i++){
            dbh.setRecettes(Integer.parseInt(RecipeIdList.get(i)), RecipeNameList.get(i), Integer.parseInt(NumberOfIngredientsList.get(i)), PhotoUrlList.get(i), 1, "", 1, 0);
        }

        getIngredients(obj1.getRecipeID()); // chercher les ingredients a partir de recipeID


    }

    public void getIngredients(ArrayList recipesId) {


        for (int i = 0; i < recipesId.size(); i++) {
            String recipeId = recipesId.get(i).toString();
            url_recipesById = "http://www.kraftfoods.com/ws/RecipeWS.asmx/GetRecipeByRecipeID?iRecipeID=" + recipeId + "&bStripHTML=true&iBrandID=1&iLangID=1";
//            System.out.println("url" +i+ " est : "+url_recipesById);

            obj2 = new HandleXML(url_recipesById);
            obj2.fetchXML();
            while (obj2.parsingComplete) ;

            IngredientIdList.clear();
            IngredientNameList.clear();
            PreparationDescriptionList.clear();

            IngredientIdList= obj2.getIngredientID();
            IngredientNameList= obj2.getIngredientName();
            PreparationDescriptionList= obj2.getPreparationDescription();

            for(int j=0; j<IngredientIdList.size(); j++){
                // set the Ingredients table and link table
                dbh.setIngredients(Integer.parseInt(IngredientIdList.get(j)), IngredientNameList.get(j), 0, 0);
                dbh.setLinkRecipeIngredients(j,Integer.parseInt(recipeId), Integer.parseInt(IngredientIdList.get(j)), 0);
            }
            for(int k=0; k<PreparationDescriptionList.size() ; k++ ){

                //set the preparation table
                dbh.setPreparation(Integer.parseInt(recipeId), PreparationDescriptionList.get(k));

            }

        }

    }
}
