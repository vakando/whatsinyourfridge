package com.example.canmorpro.whatsinyourfridge3;


import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class HandleXML {



    private ArrayList<String> RecipeIdList =  new ArrayList<String>();
    private ArrayList<String> RecipeNameList =  new ArrayList<String>();
    private ArrayList<String> NumberOfIngredientsList =  new ArrayList<String>();
    private ArrayList<String> PhotoUrlList =  new ArrayList<String>();
    private ArrayList<String> IngredientIdList =  new ArrayList<String>();
    private ArrayList<String> IngredientNameList =  new ArrayList<String>();
    private ArrayList<String> PreparationDescriptionList =  new ArrayList<String>();


    private String TotalCount = "TotalCount";

    private String urlString = null;
//    private Context context;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;




    public HandleXML(String url){
        this.urlString = url;
//        this.context = context;
//        RecipeIdList = null;
//        RecipeNameList = null;
//        NumberOfIngredientsList = null;
//        PhotoUrlList = null;
//        IngredientIdList= null;
//        IngredientNameList = null;
    }



    public String getTotalCount(){
        return TotalCount;
    }


    public ArrayList getRecipeID(){
        return RecipeIdList;
    }

    public ArrayList getRecipeName(){
        return RecipeNameList;
    }

    public ArrayList getNumberOfIngredients(){return NumberOfIngredientsList; }

    public ArrayList getPhotoURL(){
        return PhotoUrlList;
    }

    public ArrayList getIngredientID(){
        return IngredientIdList;
    }

    public ArrayList getIngredientName(){
        return IngredientNameList;
    }

    public ArrayList getPreparationDescription(){
        return PreparationDescriptionList;
    }


    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text=null;

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();

                switch (event){
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
//                        Log.i("text", text);



                        if(name.equals("TotalCount")){
                            TotalCount = text;
                        }

                        else if(name.equals("RecipeID")){
                            RecipeIdList.add(text);
                        }

                        else if(name.equals("RecipeName")){
                            RecipeNameList.add(text);
                        }

                        else if(name.equals("NumberOfIngredients")){
                            NumberOfIngredientsList.add(text);
                        }

                        else if(name.equals("PhotoURL")){
                            PhotoUrlList.add(text);
                        }

                        else if(name.equals("IngredientID")){
                            IngredientIdList.add(text);
                        }

                        else if(name.equals("IngredientName")){
                            IngredientNameList.add(text);
                        }

                        else if(name.equals("Description")){
                            PreparationDescriptionList.add(text);
                        }

//                        else{
//                        }

                        break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchXML(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream stream = conn.getInputStream();
                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);

                    parseXMLAndStoreIt(myparser);
                    stream.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}