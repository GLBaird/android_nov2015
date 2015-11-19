package com.leonbaird.networking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPManager {

    // error handling values
    public static Exception error = null;
    public static String    errorReason = null;

    // check networking
    public static boolean isOnline(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    // Prepare Network Stream for downloading
    public static InputStream getData(RequestPackage p) {

        // make URI
        String uri = p.getUri();

        if (p.getMethod().equals(RequestPackage.METHOD_GET)) {
            uri += "?" + p.getURLEncodedParams();
        }

        try {
            // Make URL, connection and set the method
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(p.getConnectionTimeout());
            connection.setReadTimeout(p.getReadTimeout());
            connection.setRequestMethod(p.getMethod());

            if (p.getMethod().equals(RequestPackage.METHOD_POST)
                    && p.getMimeType().equals(RequestPackage.TYPE_ENCODE_JSON))
            {
                connection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            }

            // Handle Post Values
            if (p.getMethod().equals(RequestPackage.METHOD_POST)) {
                connection.setDoOutput(true);
                connection.setDoInput(true);

                // write data into request body
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write( p.getEncodedParams() );
                writer.flush();
                writer.close();
            }

            Log.d("HTTPManager", "Prepared request and connection");

            return connection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            error = e;
            errorReason = "Malformed URL";
        } catch (IOException e) {
            e.printStackTrace();
            error = e;
            errorReason = "Error openning connection";
        }

        return null;
    }

    public static String getDataAsString(RequestPackage p) {

        InputStream is = getData(p);

        if (is != null) {
            // convert IS to text
            StringBuilder  sb = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                error = e;
                errorReason = "Failed to read input stream as String";
            }
        }

        return  null;
    }

    public static Bitmap getDataAsBitmap(RequestPackage p) {
        InputStream is = getData(p);
        if (is != null) {
            return BitmapFactory.decodeStream(is);
        }

        return null;
    }

    public static JSONObject getDataAsJSONObject(RequestPackage p) {
        String data = getDataAsString(p);

        if (data != null) {
            try {
                return new JSONObject(data);
            } catch (JSONException e) {
                e.printStackTrace();
                error = e;
                errorReason = "Failed to parse JSON OBject, try JSON Array or check data is valid";
            }
        } else {
            errorReason = "Can't download string to parse JSON";
        }

        return null;
    }

    public static JSONArray getDataAsJSONArray(RequestPackage p) {
        String data = getDataAsString(p);

        if (data != null) {
            try {
                return new JSONArray(data);
            } catch (JSONException e) {
                e.printStackTrace();
                error = e;
                errorReason = "Failed to parse JSON Array, try JSON Object or check data is valid";
            }
        } else {
            errorReason = "Can't download string to parse JSON";
        }

        return null;
    }


}
