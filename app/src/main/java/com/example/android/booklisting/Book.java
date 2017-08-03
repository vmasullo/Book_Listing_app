package com.example.android.booklisting;

/**
 * Created by Enzo on the 14/07/2017.
 */

public class Book {

    private String mThumbnail;
    private String mTitolo;
    private StringBuilder mAutore;
    private String mEditore;
    private String mPagine;
    private String mUrl;

    public Book(String thumbnail, String titolo, StringBuilder autore, String editore, String Pagine, String url) {

        mThumbnail = thumbnail;
        mTitolo = titolo;
        mAutore = autore;
        mEditore = editore;
        mPagine = Pagine;
        mUrl = url;
    }
    public String getThumbnail(){return  mThumbnail;}

    public String getTitle() {
        return mTitolo;
    }

    public StringBuilder getAutore() {
        return mAutore;
    }

    public String getEditore() {
        return mEditore;
    }

    public String getPagine() {
        return mPagine;
    }
    public String getUrl(){return mUrl;}
}
