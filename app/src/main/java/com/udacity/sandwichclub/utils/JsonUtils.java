package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        // Parse the JSON data and return the JSON oject
        JSONObject sandwichDetails = null;
        Sandwich sandwichObj = new Sandwich();

        // Get the sandwich JSON. If that fails, return the empty sandwich object.
        try {
            sandwichDetails = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return sandwichObj;
        }

        // Parse the sandwich data from the JSON into the sandwich object
        String sandwichName;
        JSONArray alsoKnown;
        String description;
        String imageURL;
        String origin;
        JSONArray ingredients;
        try {
            JSONObject sandwichNames = sandwichDetails.getJSONObject("name");
            sandwichName = sandwichNames.getString("mainName");
            alsoKnown = sandwichNames.getJSONArray("alsoKnownAs");
            description = sandwichDetails.getString("description");
            origin = sandwichDetails.getString("placeOfOrigin");
            imageURL = sandwichDetails.getString("image");
            ingredients = sandwichDetails.getJSONArray("ingredients");
        } catch (JSONException e) {
            e.printStackTrace();
            return sandwichObj;
        }

        // Set data in sandwich object
        // Sandwich name
        sandwichObj.setMainName(sandwichName);
        // Also known as list
        if (alsoKnown != null) {
            List<String> alsoKnownList = new ArrayList<String>();
            int len = alsoKnown.length();
            for (int i = 0; i < len; i++) {
                try {
                    alsoKnownList.add(alsoKnown.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            sandwichObj.setAlsoKnownAs(alsoKnownList);
        }
        //Description
        sandwichObj.setDescription(description);
        // Origin
        sandwichObj.setPlaceOfOrigin(origin);
        // Image URL
        sandwichObj.setImage(imageURL);
        // Ingredients list
        if (ingredients != null) {
            List<String> ingredientsList = new ArrayList<String>();
            int len = ingredients.length();
            for (int i = 0; i < len; i++) {
                try {
                    ingredientsList.add(ingredients.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            sandwichObj.setIngredients(ingredientsList);
        }

        return sandwichObj;
    }
}