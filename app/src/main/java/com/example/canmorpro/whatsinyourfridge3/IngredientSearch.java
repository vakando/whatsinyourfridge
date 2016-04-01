package com.example.canmorpro.whatsinyourfridge3;

import java.util.ArrayList;

/**
 * Created by CanMorPro on 16-03-30.
 */
public class IngredientSearch {


    private String url_recipesByIngredient, url_recipesById;
    private String ing1, ing2 = "", ing3 = "" ;
    private int startRow, endRow;
    private HandleXML obj1, obj2 ;

    public IngredientSearch( String ing1, String ing2, String ing3){

        startRow = 1;
        endRow = 5;
        this.ing1= ing1;
        this.ing2= ing2;
        this.ing3= ing3;
        url_recipesByIngredient = "http://www.kraftfoods.com/ws/RecipeWS.asmx/GetRecipesByIngredients?sIngredient1="+ing1+"&sIngredient2="+ing2+"&sIngredient3="+ing3+"&bIsRecipePhotoRequired=true&sSortField=&sSortDirection=&iBrandID=1&iLangID=1&iStartRow="+startRow+"&iEndRow="+endRow+"";

    }


    public void storeData(){

        obj1 = new HandleXML(url_recipesByIngredient);
        obj1.fetchXML();
//      System.out.println(url_recipesByIngredient);
        while(obj1.parsingComplete);
        System.out.println("recipe ID is "+obj1.getRecipeID().get(0)+" the second is "+obj1.getRecipeID().get(1));
        obj1.getStatus();
        obj1.getTotalCount();
        obj1.getRecipeID();
        obj1.getRecipeName();
        obj1.getNumberOfIngredients();
        obj1.getPhotoURL();

        getIngredients(obj1.getRecipeID()); // chercher les ingredients a partir de recipeID


    }

    public void getIngredients(ArrayList ingredients){


        for(int i=0; i<ingredients.size() ; i++) {
            String recipeId = obj1.getRecipeID().get(i).toString();
            url_recipesById = "http://www.kraftfoods.com/ws/RecipeWS.asmx/GetRecipeByRecipeID?iRecipeID=" + recipeId + "&bStripHTML=true&iBrandID=1&iLangID=1";
//            System.out.println("url" +i+ " est : "+url_recipesById);

            obj2 = new HandleXML(url_recipesById);
            obj2.fetchXML();
            while (obj2.parsingComplete) ;
            System.out.println(obj2.getIngredientID());
//            System.out.println("retourne ID index 1:  "+obj2.getIngredientID().get(0)+ "index 2 : "+obj2.getIngredientID().get(1));

            obj2.getIngredientID();
            obj2.getIngredientName();
            obj2.getPreparationDescription();
        }
    }




}
