package com.udacity.wertonguimaraes.booklisting;

/**
 * Created by wertonguimaraes on 24/04/18.
 */

public class Info {
    private String mTitle;
    private String mAuthor;


    public Info(String title, String author) {
        mTitle = title;
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }


}