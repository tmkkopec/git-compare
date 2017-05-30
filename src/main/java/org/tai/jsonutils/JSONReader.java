package org.tai.jsonutils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

public class JSONReader {

    public static JSONObject readJson(String url, String acceptHeader) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setRequestProperty("Accept", acceptHeader);
        return new JSONObject(readInputStream(con.getInputStream()));
    }

    public static JSONObject readJson(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        return new JSONObject(readInputStream(is));
    }

    private static String readInputStream(InputStream is) throws IOException {
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            return readUrl(rd);
        } finally {
            is.close();
        }
    }

    private static String readUrl(BufferedReader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static JSONArray readJsonArray(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        return readJsonArray(is);
    }

    public static JSONArray readJsonArray(InputStream is) throws IOException, JSONException {
        return new JSONArray(readInputStream(is));
    }
}