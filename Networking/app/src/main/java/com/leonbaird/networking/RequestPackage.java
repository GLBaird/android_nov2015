package com.leonbaird.networking;

import org.json.JSONException;
import org.json.JSONStringer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RequestPackage {

    // constants for request methods
    public static final String METHOD_GET   = "GET";
    public static final String METHOD_POST  = "POST";

    // datatypes (mimetype)
    public static final String TYPE_ENCODE_URLFORM = "URL";
    public static final String TYPE_ENCODE_JSON    = "JSON";

    // basic data for request
    private String uri;
    private String method   = METHOD_GET;
    private String mimeType = TYPE_ENCODE_URLFORM;

    private Map<String, String> params = new HashMap<>();

    // extra settings with defaults
    private int readTimeout       = 5000;
    private int connectionTimeout = 5000;

    // Getters
    public String getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public String getMimeType() {
        return mimeType;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    // Setters

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    // paramater handling

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setParam(String key, String value) {
        params.put(key, value);
    }

    public String getURLEncodedParams() {
        // Convert params into URL Encodings
        StringBuilder sb = new StringBuilder();

        for (String key : params.keySet()) {
            String value;

            try {
                value = URLEncoder.encode(params.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                value = "";
            }

            if (sb.length() > 0) {
                sb.append("&");
            }

            sb.append(key)
              .append("=")
              .append(value);
        }

        return  sb.toString();
    }

    public String getJSONEncodedParams() {
        try {
            JSONStringer json = new JSONStringer().object();

            for (String key : params.keySet()) {
                json.key(key).value(params.get(key));
            }

            return URLEncoder.encode(json.endObject().toString(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getEncodedParams() {
        return mimeType.equals(TYPE_ENCODE_URLFORM)
                ? getURLEncodedParams()
                : getJSONEncodedParams();
    }
}
