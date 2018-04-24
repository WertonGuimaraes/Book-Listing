package com.udacity.wertonguimaraes.booklisting;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by wertonguimaraes on 24/04/18.
 */

public class URLAsyncTaskLoader extends AsyncTaskLoader<List<Info>> {

    public URLAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    public List<Info> loadInBackground() {
        List<Info> responseListInfo = null;

        try {
            Request request = new Request();
            String getDataURL = request.getInfoInURL();

            Parse parse;
            parse = new Parse(getDataURL);

            responseListInfo = parse.convertJsonToInfoObjects();
        } catch (MalformedURLException e) {
            Log.e(TAG, "URL error.");
        } catch (IOException e) {
            Log.e(TAG, "I/O error to get data.");
        } catch (JSONException e) {
            Log.e(TAG, "JSON Malformed.");
        }

        return responseListInfo;
    }

}
