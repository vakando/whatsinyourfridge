package com.example.canmorpro.whatsinyourfridge3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by CanMorPro on 16-03-30.
 */
public class IngredientSearch {


    private String url_recipesByIngredient, url_recipesById;
    private String ing1, ing2 = "", ing3 = "" ;
    public static int startRow=1;
    public static int endRow=20;
    private HandleXML obj1, obj2 ;
    public boolean clear;

    public static int recipeStart = 0;
    public static int recipeIdStart = 0;
    public static int IngStart = 0;
    public static int PrepStart = 0;


    private DBHelper dbh;

    private ArrayList<String> RecipeIdList =  new ArrayList<String>();
    private ArrayList<String> RecipeNameList =  new ArrayList<String>();
    private ArrayList<String> PhotoUrlList =  new ArrayList<String>();
    private ArrayList<String> NumberOfIngredientsList =  new ArrayList<String>();
    private String SearchCount;


    private ArrayList<String> IngredientIdList =  new ArrayList<String>();
    private ArrayList<String> IngredientNameList =  new ArrayList<String>();
    private ArrayList<String> PreparationDescriptionList =  new ArrayList<String>();

    public IngredientSearch( String ing1, String ing2, String ing3, DBHelper dbh){

        clear =true;
        this.ing1= ing1;
        this.ing2= ing2;
        this.ing3= ing3;
        this.dbh= dbh;
    }


    public void storeData(){

        url_recipesByIngredient = "http://www.kraftfoods.com/ws/RecipeWS.asmx/GetRecipesByIngredients?sIngredient1="+ing1+"&sIngredient2="+ing2+"&sIngredient3="+ing3+"&bIsRecipePhotoRequired=true&sSortField=&sSortDirection=&iBrandID=1&iLangID=1&iStartRow="+startRow+"&iEndRow="+endRow+"";
        url_recipesByIngredient = url_recipesByIngredient.replaceAll("\\s+","%20");

        obj1 = new HandleXML(url_recipesByIngredient);
        obj1.fetchXML();
        System.out.println(url_recipesByIngredient);

        while(obj1.parsingComplete);



//        fix number of result for max 500
        if(Integer.parseInt(obj1.getTotalCount())>=500) SearchCount = "500";
        else SearchCount = obj1.getTotalCount();



        RecipeIdList= obj1.getRecipeID();
        RecipeNameList= obj1.getRecipeName();
        NumberOfIngredientsList= obj1.getNumberOfIngredients();
        PhotoUrlList= obj1.getPhotoURL();

//        clear tables
        if(clear){
        dbh.clearRecipeTable();
        dbh.clearIngredientTable();
        dbh.clearPreparationTable();
        dbh.clearTableRequest();
        }

        dbh.setRequestCount(SearchCount);




                for( recipeStart=0; recipeStart<RecipeIdList.size(); recipeStart++){
                    dbh.setRecettes(Integer.parseInt(RecipeIdList.get(recipeStart)), RecipeNameList.get(recipeStart), Integer.parseInt(NumberOfIngredientsList.get(recipeStart)), PhotoUrlList.get(recipeStart), 0, "", 1, 0);
                }


        getIngredients(RecipeIdList); // chercher les ingredients a partir de recipeID


    }

    public void getIngredients(ArrayList recipesId){


        for( recipeIdStart=0; recipeIdStart<recipesId.size() ; recipeIdStart++) {
            String recipeId = recipesId.get(recipeIdStart).toString();
            url_recipesById = "http://www.kraftfoods.com/ws/RecipeWS.asmx/GetRecipeByRecipeID?iRecipeID=" + recipeId + "&bStripHTML=true&iBrandID=1&iLangID=1";
            url_recipesById = url_recipesById.replaceAll("\\s+","%20");

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

            for( IngStart = 0 ; IngStart<IngredientIdList.size(); IngStart++){
                // set the Ingredients table and link table
                dbh.setIngredients(Integer.parseInt(IngredientIdList.get(IngStart)), IngredientNameList.get(IngStart), 0, 0);
                dbh.setLinkRecetteIng(Integer.parseInt(recipeId), Integer.parseInt(IngredientIdList.get(IngStart)), 0);



            for(PrepStart=0; PrepStart<PreparationDescriptionList.size() ; PrepStart++ ){

                String s = PreparationDescriptionList.get(PrepStart);
                //set the preparation table
                if(!s.replaceAll("\\s+","").equals("")) dbh.setPreparation(Integer.parseInt(recipeId),PreparationDescriptionList.get(PrepStart));

}
            }
        }
    }

//
//    public void print(String s){
//
//        try(FileWriter fw = new FileWriter("/Users/CanMorPro/Documents/ingredients3000.txt", true);
//            BufferedWriter bw = new BufferedWriter(fw);
//            PrintWriter out = new PrintWriter(bw))
//
//        {
//            out.print("ingredient" + "");
//            //more code
//            System.out.println(" printed ");
//
//            //more code
//        } catch (IOException e) {
//            //exception handling left as an exercise for the reader
//        }
//
//    }



    public void storeRemaining(){ //store the remaining data

        clear=false;
        startRow=20;
        endRow =500;
        storeData();

    }




}
