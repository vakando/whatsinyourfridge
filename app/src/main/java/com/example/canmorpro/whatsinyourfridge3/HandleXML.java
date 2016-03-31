package com.example.canmorpro.whatsinyourfridge3;


import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class HandleXML {



    private String Status = "Status";
    private String TotalCount = "TotalCount";
    private String RecipeID = "RecipeID";
    private String RecipeName = "RecipeName";
    private String NumberOfIngredients = "NumberOfIngredients";
    private String PhotoURL = "PhotoURL";
    private String IngredientID = "IngredientID";
    private String IngredientName = "IngredientName";
    private String PreparationDescription = "Description";
    private String urlString = null;
//    private Context context;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;




    public HandleXML(String url){
        this.urlString = url;
//        this.context = context;
    }

    public String getStatus(){
        return Status;
    }

    public String getTotalCount(){
        return TotalCount;
    }


    public String getRecipeID(){
        return RecipeID;
    }

    public String getRecipeName(){
        return RecipeName;
    }

    public String getNumberOfIngredients(){
        return NumberOfIngredients;
    }

    public String getPhotoURL(){
        return PhotoURL;
    }

    public String getIngredientID(){
        return IngredientID;
    }

    public String getIngredientName(){
        return IngredientName;
    }

    public String getPreparationDescription(){
        return PreparationDescription;
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

                        XmlPullParser.TEXT:
                        text = myParser.getText();
//                        break;


//                        Log.i("text", text);
//                        Log.i("tag", text);

                        if(name.equals("Status")){
                            Status = text;
                        }

                        else if(name.equals("TotalCount")){
                            TotalCount = text;
                        }

                        else if(name.equals("RecipeID")){
                            RecipeID = text;
                        }

                        else if(name.equals("RecipeName")){
                            RecipeName = text;
                        }

                        else if(name.equals("NumberOfIngredients")){
                            NumberOfIngredients = text;
                        }

                        else if(name.equals("PhotoURL")){
                            PhotoURL = text;
                        }

                        else if(name.equals("IngredientID")){
                            IngredientID = text;
                        }

                        else if(name.equals("IngredientName")){
                            IngredientName = text;
                        }

                        else if(name.equals("Description")){
                            PreparationDescription = text;
                        }

//                        else{
//                        }

                        break;
                    case XmlPullParser.END_TAG:
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