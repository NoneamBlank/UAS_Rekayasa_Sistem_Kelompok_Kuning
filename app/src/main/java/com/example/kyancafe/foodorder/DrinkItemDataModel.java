package com.example.kyancafe.foodorder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class DrinkItemDataModel {
    public String itemname;
    public String itemprice;

    public DrinkItemDataModel(JSONObject object) {

        try
        {
            this.itemname = object.getString("itemname");
            this.itemprice = object.getString("itemprice");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    //itemname and itemprice values retrieval from JSON Array..
    public static ArrayList<DrinkItemDataModel> fromJson(JSONArray jsonObjects)
    {
        ArrayList<DrinkItemDataModel> drinkitems = new ArrayList<DrinkItemDataModel>();
        for (int i = 0; i < jsonObjects.length(); i++)
        {
            try
            {
                drinkitems.add(new DrinkItemDataModel(jsonObjects.getJSONObject(i)));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return drinkitems;
    }

}


