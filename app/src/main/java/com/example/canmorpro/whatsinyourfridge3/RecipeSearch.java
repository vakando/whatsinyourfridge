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
    private HandleXML obj1, obj2 ;



    public RecipeSearch(String keyword){

        this.keyword = keyword;
        startRow = 1;
        endRow = 5;
        url_recipesByKeyword = "http://www.kraftfoods.com/ws/RecipeWS.asmx/GetRecipesByKeywords?sKeyword1="+keyword+"&sKeyword2=&sKeyword3=&sKeyword4=&sKeyword5=&sKeyword6=&bIsRecipePhotoRequired=true&bIsReadyIn30Mins=false&sSortField=&sSortDirection=&iBrandID=1&iLangID=1&iStartRow="+startRow+"&iEndRow="+endRow+"";

    }


    public void storeData(){

        obj1 = new HandleXML(url_recipesByKeyword);
        obj1.fetchXML();
      System.out.println(url_recipesByKeyword);
        while(obj1.parsingComplete);

//        System.out.println("recipe ID is "+obj1.getRecipeID().get(0)+" the second is "+obj1.getRecipeID().get(1));
        obj1.getStatus();
        obj1.getTotalCount();
        obj1.getRecipeID();
        obj1.getRecipeName();
        obj1.getNumberOfIngredients();
        obj1.getPhotoURL();

        getIngredients(obj1.getRecipeID()); // chercher les ingredients a partir de recipeID


    }

    public void getIngredients(ArrayList ingredients) {


        for (int i = 0; i < ingredients.size(); i++) {
            String recipeId = obj1.getRecipeID().get(i).toString();
            url_recipesById = "http://www.kraftfoods.com/ws/RecipeWS.asmx/GetRecipeByRecipeID?iRecipeID=" + recipeId + "&bStripHTML=true&iBrandID=1&iLangID=1";
//            System.out.println("url" +i+ " est : "+url_recipesById);

            obj2 = new HandleXML(url_recipesById);
            obj2.fetchXML();
            while (obj2.parsingComplete) ;
//            System.out.println(obj2.getIngredientID());
//            System.out.println("retourne ID index 1:  "+obj2.getIngredientID().get(0)+ "index 2 : "+obj2.getIngredientID().get(1));

            obj2.getIngredientID();
            obj2.getIngredientName();
            obj2.getPreparationDescription();
        }

    }
}
