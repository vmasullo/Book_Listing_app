package com.example.android.booklisting;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enzo on the 14/07/2017.
 */

public final class Utils {

    private static final String LOG_TAG = Utils.class.getSimpleName();

    private Utils() {}

    public static List<Book> fetchBookList(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Book> books = extractFeatureFromJson(jsonResponse);

        return books;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Book> extractFeatureFromJson(String bookJSON) {

        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        List<Book> books = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(bookJSON);

            JSONArray itemsArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < itemsArray.length(); i++) {

                JSONObject currentBook = itemsArray.getJSONObject(i);

                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                String thumbnail = "N/A";
                if (volumeInfo.has("thumbnail")) {
                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                    if (imageLinks.has("thumbnail")) {
                        thumbnail = imageLinks.getString("thumbnail");
                    } else {
                        thumbnail = "No Image";
                    }
                }

                String title = volumeInfo.getString("title");

                JSONArray authorsArray;
                StringBuilder authors = new StringBuilder();
                if (volumeInfo.has("authors")) {
                    authorsArray = volumeInfo.getJSONArray("authors");
                    for (int n=0; n < authorsArray.length(); n++) {
                        authors.append(System.getProperty("line.separator"));
                        authors.append(authorsArray.getString(n));
                    }
                } else {
                    authors.append("No Autore");
                }

                String publisher;
                if (volumeInfo.has("publisher")) {
                    publisher = volumeInfo.getString("publisher");
                } else {
                    publisher = "No editore";
                }

                String pageCount;
                if (volumeInfo.has("pageCount")) {
                    pageCount = volumeInfo.getString("pageCount");
                } else {
                    pageCount = "No pagine";
                }
                String url = null;
                if (volumeInfo.has("infoLink")){
                    url = volumeInfo.getString("infoLink");
                }

                Book book = new Book(thumbnail,title, authors, publisher, pageCount,url);


                books.add(book);
            }
        } catch (JSONException e) {

            Log.e(LOG_TAG, "Problem parsing the book JSON results", e);
        }

        return books;
    }
}
