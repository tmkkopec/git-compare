package org.tai.jsonutils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Class for reading JSON objects and arrays
 */
public class JSONReader {

    /**
     * @param url - url of JSON object to read
     * @param acceptHeader - value of Accept Header
     * @return object of class JSONObject with content from given url
     * @throws IOException
     */
    public static JSONObject readJson(String url, String acceptHeader) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setRequestProperty("Accept", acceptHeader);
        return new JSONObject(readInputStream(con.getInputStream()));
    }

    /**
     * @param url - url of JSON object to read
     * @return object of class JSONObject with content from given url
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject readJson(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        return new JSONObject(readInputStream(is));
    }

    /**
     * @param is - InputStream object received after openStream() method on URL object
     * @return content of InputStream as a String
     * @throws IOException
     */
    private static String readInputStream(InputStream is) throws IOException {
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            return readUrl(rd);
        } finally {
            is.close();
        }
    }

    /**
     * @param rd - BufferedReader object to read lines from
     * @return String representation of what was inside BufferedReader
     * @throws IOException
     */
    private static String readUrl(BufferedReader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * @param url - url of JSONArray to read
     * @return object of class JSONArray with content from given url
     * @throws IOException
     * @throws JSONException
     */
    public static JSONArray readJsonArray(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        return readJsonArray(is);
    }

    /**
     * @param is - InputStream object received after openStream() method on URL object
     * @return object of class JSONArray with content from given InputStream
     * @throws IOException
     * @throws JSONException
     */
    public static JSONArray readJsonArray(InputStream is) throws IOException, JSONException {
        return new JSONArray(readInputStream(is));
    }
}