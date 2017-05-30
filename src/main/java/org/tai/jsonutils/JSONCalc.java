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

public class JSONCalc {

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

    private static String getLastPageUrl(String linkHeader){
        String[] links = linkHeader.split(", ");
        for (String link : links){
            String[] splitLink = link.split("; ");
            if (splitLink[1].equals("rel=\"last\"")){
                return getUrl(splitLink[0]);
            }
        }
        throw new RuntimeException("Shouldn't be here");
    }

    private static String getUrl(String url){
        url = url.substring(1);
        return url.substring(0, url.length() - 1);
    }

    private static int getLastPage(String lastPageUrl){
        String[] lastPageUrlSplit = lastPageUrl.split("page=");
        return Integer.valueOf(lastPageUrlSplit[1]);
    }

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

    private static Map<String, String> getLinkMap(String linkHeader){
        Map<String, String> result = new HashMap<>();
        String[] parts = linkHeader.split(", ");
        for (String part : parts){
            String[] splitParts = part.split("; ");
            String url = getUrl(splitParts[0]);
            String key = splitParts[1].split("\"")[1];
            result.put(key, url);
        }
        return result;
    }

}
