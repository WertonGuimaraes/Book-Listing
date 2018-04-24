package com.udacity.wertonguimaraes.booklisting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wertonguimaraes on 24/04/18.
 */

public class Parse {
    private JSONArray mJsonResults;

    public Parse(String jsonString) throws JSONException {
        JSONObject jsonData = new JSONObject(jsonString);
        mJsonResults = jsonData.getJSONArray("items");
    }

    public List<Info> convertJsonToInfoObjects() throws JSONException, MalformedURLException {
        List<Info> infoObjects = new ArrayList<>();
        for (int i = 0; i < mJsonResults.length(); i++) {
            JSONObject volumeInfo = mJsonResults.getJSONObject(i).getJSONObject("volumeInfo");
            String title = volumeInfo.getString("title");
            String author = "";
            if (volumeInfo.has("authors")) {
                author = volumeInfo.getJSONArray("authors").getString(0);
            }
            infoObjects.add(new Info(title, author));
        }
        return infoObjects;
    }
}
