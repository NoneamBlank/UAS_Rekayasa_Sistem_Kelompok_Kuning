package com.example.kyancafe.foodorder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class DessertItemDataModel {
    public String itemname;
    public String itemprice;

    public DessertItemDataModel(JSONObject object) {

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
    public static ArrayList<DessertItemDataModel> fromJson(JSONArray jsonObjects)
    {
        ArrayList<DessertItemDataModel> dessertitems = new ArrayList<DessertItemDataModel>();
        for (int i = 0; i < jsonObjects.length(); i++)
        {
            try
            {
                dessertitems.add(new DessertItemDataModel(jsonObjects.getJSONObject(i)));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return dessertitems;
    }

}
