package org.tai.jsonutils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.tai.jsonutils.JSONReader.readJsonArray;

/**
 * Helper class for JSON handling
 */
public class JSONCalc {

    /**
     * Returns length of JSONArray from arrayUrl. Note, that array under given url can be only a part of whole result,
     * that's why this method handles that and checks for Link header to see if there is a continuation of array
     * in other url.
     *
     * @param arrayUrl - url of initial part of JSONArray
     * @return length of JSONArray which starts at arrayUrl
     * @throws IOException
     * @throws JSONException
     */
    public static int getArrayLength(String arrayUrl) throws IOException, JSONException {
        URL urlObj = new URL(arrayUrl);
        URLConnection connection = urlObj.openConnection();
        String linkHeader = connection.getHeaderField("Link");
        InputStream is = urlObj.openStream();
        int firstPageLength = readJsonArray(is).length();
        if (linkHeader == null){
            return firstPageLength;
        }
        String lastPageUrl = getLastPageUrl(linkHeader);
        int pages = getLastPage(lastPageUrl);
        return firstPageLength * (pages - 1) + readJsonArray(lastPageUrl).length();
    }

    /**
     * @param linkHeader - Link header from HTTP Response
     * @return String containing url to last part of JSONArray
     */
    private static String getLastPageUrl(String linkHeader){
        String[] links = linkHeader.split(", ");
        for (String link : links){
            String[] splitLink = link.split("; ");
            if (splitLink[1].equals("rel=\"last\"")){
                return parseUrl(splitLink[0]);
            }
        }
        throw new RuntimeException("Shouldn't be here");
    }

    /**
     * @param url - Url from Link header
     * @return - parsed url, stripped from unnecessary characters
     */
    private static String parseUrl(String url){
        url = url.substring(1);
        return url.substring(0, url.length() - 1);
    }

    /**
     * @param lastPageUrl - url of last part of JSONArray
     * @return number of last page
     */
    private static int getLastPage(String lastPageUrl){
        String[] lastPageUrlSplit = lastPageUrl.split("page=");
        return Integer.valueOf(lastPageUrlSplit[1]);
    }

    /**
     * This method returns a list which consists of:
     * JSONArray from given arrayUrl and
     * all JSONArrays taken from next urls from Link header
     *
     * @param arrayUrl - url of initial part of JSONArray
     * @return List of JSONArrays
     * @throws IOException
     * @throws JSONException
     */
    public static List<JSONArray> getArrays(String arrayUrl) throws IOException, JSONException {
        List<JSONArray> result = new LinkedList<>();
        String url = arrayUrl;
        while (true) {
            URL urlObj = new URL(url);
            URLConnection connection = urlObj.openConnection();
            String linkHeader = connection.getHeaderField("Link");
            InputStream is = urlObj.openStream();
            result.add(readJsonArray(is));
            if (linkHeader == null) break;
            Map<String, String> linksMap = getLinkMap(linkHeader);
            if (!linksMap.containsKey("next")) break;
            url = linksMap.get("next");
        }
        return result;
    }

    /**
     * @param linkHeader linkHeader as a String from HTTP Response
     * @return Map, where key is header's key and value is a value of header's key
     */
    private static Map<String, String> getLinkMap(String linkHeader){
        Map<String, String> result = new HashMap<>();
        String[] parts = linkHeader.split(", ");
        for (String part : parts){
            String[] splitParts = part.split("; ");
            String url = parseUrl(splitParts[0]);
            String key = splitParts[1].split("\"")[1];
            result.put(key, url);
        }
        return result;
    }

}
