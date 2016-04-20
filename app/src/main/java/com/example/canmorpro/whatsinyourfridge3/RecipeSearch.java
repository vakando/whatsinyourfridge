package com.example.canmorpro.whatsinyourfridge3;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by CanMorPro on 16-03-31.
 */
public class RecipeSearch {


    private String url_recipesByKeyword, url_recipesById;
    private String keyword ;
    public static int startRow=1;
    public static int endRow =20;
    private HandleXML obj1,  obj2 ;

    public static int count;
    private boolean clear;

    public static int recipeStart = 0;
    public static int recipeIdStart = 0;
    public static int IngStart = 0;
    public static int PrepStart = 0;

    private DBHelper dbh;

    private ArrayList<String> RecipeIdList =  new ArrayList<String>();
    private ArrayList<String> RecipeNameList =  new ArrayList<String>();
    private ArrayList<String> PhotoUrlList =  new ArrayList<String>();
    private ArrayList<String> NumberOfIngredientsList =  new ArrayList<String>();
    private String SearchCount =  new String();


    private ArrayList<String> IngredientIdList =  new ArrayList<String>();
    private ArrayList<String> IngredientNameList =  new ArrayList<String>();
    private ArrayList<String> PreparationDescriptionList =  new ArrayList<String>();


    public RecipeSearch(String keyword, DBHelper dbh){

        this.keyword = keyword;
        this.dbh= dbh;

        clear =true;


    }


    public void storeData(){

        url_recipesByKeyword = "http://www.kraftfoods.com/ws/RecipeWS.asmx/GetRecipesByKeywords?sKeyword1="+keyword+"&sKeyword2=&sKeyword3=&sKeyword4=&sKeyword5=&sKeyword6=&bIsRecipePhotoRequired=true&bIsReadyIn30Mins=false&sSortField=&sSortDirection=&iBrandID=1&iLangID=1&iStartRow="+startRow+"&iEndRow="+endRow+"";

//        remove spaces
        url_recipesByKeyword = url_recipesByKeyword.replaceAll("\\s+","%20");

        obj1 = new HandleXML(url_recipesByKeyword);
        obj1.fetchXML();
//        System.out.println(url_recipesByKeyword);
        while(obj1.parsingComplete);


//        fix number of result for max 500
        if(Integer.parseInt(obj1.getTotalCount())>=500) SearchCount = "500";
        else SearchCount = obj1.getTotalCount();


        RecipeIdList= obj1.getRecipeID();
        RecipeNameList= obj1.getRecipeName();
        NumberOfIngredientsList= obj1.getNumberOfIngredients();
        PhotoUrlList= obj1.getPhotoURL();
        PreparationDescriptionList= obj1.getPreparationDescription();

        //        clear tables
        if(clear) {
            dbh.clearRecipeTable();
            dbh.clearIngredientTable();
            dbh.clearPreparationTable();
            dbh.clearTableRequest();
        }

        dbh.setRequestCount(SearchCount);


        for( recipeStart=0; recipeStart<RecipeIdList.size(); recipeStart++){
            dbh.setRecettes(Integer.parseInt(RecipeIdList.get(recipeStart)), RecipeNameList.get(recipeStart), Integer.parseInt(NumberOfIngredientsList.get(recipeStart)), PhotoUrlList.get(recipeStart), 0, "", 1, 0, recipeStart);
        }


        getIngredients(RecipeIdList); // chercher les ingredients a partir de recipeID




    }

    public void getIngredients(ArrayList recipesId) {


        for( recipeIdStart=0; recipeIdStart<recipesId.size() ; recipeIdStart++) {
            String recipeId = recipesId.get(recipeIdStart).toString();
            url_recipesById = "http://www.kraftfoods.com/ws/RecipeWS.asmx/GetRecipeByRecipeID?iRecipeID=" + recipeId + "&bStripHTML=true&iBrandID=1&iLangID=1";
            url_recipesById = url_recipesById.replaceAll("\\s+","%20");

            obj2 = new HandleXML(url_recipesById);
            obj2.fetchXML();
            while (obj2.parsingComplete) ;



            IngredientIdList.clear();
            IngredientNameList.clear();
            PreparationDescriptionList.clear();


            IngredientIdList= obj2.getIngredientID();
            IngredientNameList= obj2.getIngredientName();
            PreparationDescriptionList= obj2.getPreparationDescription();

            for( IngStart = 0 ; IngStart<IngredientIdList.size(); IngStart++){
                // set the Ingredients table and link table
                dbh.setIngredients(Integer.parseInt(IngredientIdList.get(IngStart)), IngredientNameList.get(IngStart), 0, 0);
                dbh.setLinkRecetteIng(Integer.parseInt(recipeId), Integer.parseInt(IngredientIdList.get(IngStart)), 0);
            }
            for(PrepStart=0; PrepStart<PreparationDescriptionList.size() ; PrepStart++ ){

                String s = PreparationDescriptionList.get(PrepStart);

                //set the preparation table

                if(!s.replaceAll("\\s+","").equals("")) dbh.setPreparation(Integer.parseInt(recipeId),PreparationDescriptionList.get(PrepStart));

            }

        }

    }

    public void storeRemaining(){

//        System.out.println("the storeRemaining is here");

        clear=false;
        startRow=20;
        endRow =500;
        storeData();

    }
}
