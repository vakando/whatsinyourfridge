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

    private DBHelper dbh;

    private ArrayList<String> RecipeIdList =  new ArrayList<String>();
    private ArrayList<String> RecipeNameList =  new ArrayList<String>();
    private ArrayList<String> PhotoUrlList =  new ArrayList<String>();
    private ArrayList<String> NumberOfIngredientsList =  new ArrayList<String>();

    private ArrayList<String> IngredientIdList =  new ArrayList<String>();
    private ArrayList<String> IngredientNameList =  new ArrayList<String>();
    private ArrayList<String> PreparationDescriptionList =  new ArrayList<String>();

    public IngredientSearch( String ing1, String ing2, String ing3, DBHelper dbh){

        startRow = 1;
        endRow = 5;
        this.ing1= ing1;
        this.ing2= ing2;
        this.ing3= ing3;
        this.dbh= dbh;
        url_recipesByIngredient = "http://www.kraftfoods.com/ws/RecipeWS.asmx/GetRecipesByIngredients?sIngredient1="+ing1+"&sIngredient2="+ing2+"&sIngredient3="+ing3+"&bIsRecipePhotoRequired=true&sSortField=&sSortDirection=&iBrandID=1&iLangID=1&iStartRow="+startRow+"&iEndRow="+endRow+"";

    }


    public void storeData(){

        obj1 = new HandleXML(url_recipesByIngredient);
        obj1.fetchXML();
//      System.out.println(url_recipesByIngredient);
        while(obj1.parsingComplete);
//        System.out.println("recipe ID is "+obj1.getRecipeID().get(0)+" the second is "+obj1.getRecipeID().get(1));
        obj1.getTotalCount(); // number_result
        RecipeIdList= obj1.getRecipeID();
        RecipeNameList= obj1.getRecipeName();
        NumberOfIngredientsList= obj1.getNumberOfIngredients();
        PhotoUrlList= obj1.getPhotoURL();

//        clear tables
        dbh.clearRecipeTable();
        dbh.clearIngredientTable();
        dbh.clearPreparationTable();

        for(int i=0; i<RecipeIdList.size(); i++){
            // set the recipe table
            dbh.setRecettes(Integer.parseInt(RecipeIdList.get(i)), RecipeNameList.get(i), Integer.parseInt(NumberOfIngredientsList.get(i)), PhotoUrlList.get(i), 1, "", 1, 0);
        }

        getIngredients(RecipeIdList); // chercher les ingredients a partir de recipeID

    }

    public void getIngredients(ArrayList recipesId){


        for(int i=0; i<recipesId.size() ; i++) {
            String recipeId = recipesId.get(i).toString();
            url_recipesById = "http://www.kraftfoods.com/ws/RecipeWS.asmx/GetRecipeByRecipeID?iRecipeID=" + recipeId + "&bStripHTML=true&iBrandID=1&iLangID=1";
//            System.out.println("url" +i+ " est : "+url_recipesById);

            obj2 = new HandleXML(url_recipesById);
            obj2.fetchXML();
            while (obj2.parsingComplete) ;
            //System.out.println(obj2.getIngredientID());
//            System.out.println("retourne ID index 1:  "+obj2.getIngredientID().get(0)+ "index 2 : "+obj2.getIngredientID().get(1));

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
                dbh.setPreparation(Integer.parseInt(recipeId),PreparationDescriptionList.get(k));

            }
        }
    }




}
