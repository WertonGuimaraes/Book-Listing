package com.udacity.wertonguimaraes.booklisting;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by wertonguimaraes on 24/04/18.
 */

public class Request {
    private URL mURL;

    public Request(String query) throws MalformedURLException {
        String baseUrl = "https://www.googleapis.com/books/v1/volumes?q=" + query + "&maxResults=15";

        mURL = new URL(baseUrl);
    }

    public String getInfoInURL() throws IOException {
        String jsonData = "";

        if (mURL == null) {
            return jsonData;
        }

        HttpURLConnection urlConnection = openConection();
        if (urlConnection.getResponseCode() == 200) {
            jsonData = getJsonData(urlConnection);
        } else {
            Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
        }
        closeConection(urlConnection);

        return jsonData;
    }

    private String getJsonData(HttpURLConnection urlConnection) throws IOException {
        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader;
        String line;

        if (inputStream == null) {
            return null;
        }

        reader = new BufferedReader(new InputStreamReader(inputStream));

        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();

        if (buffer.length() == 0) {
            return null;
        }

        return buffer.toString();
    }

    private HttpURLConnection openConection() throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) mURL.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        return urlConnection;
    }

    private void closeConection(HttpURLConnection urlConnection) {
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
    }
}