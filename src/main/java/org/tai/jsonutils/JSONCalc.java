package org.tai.jsonutils;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

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
        int pages = getLastPage(linkHeader, lastPageUrl);
        return firstPageLength * (pages - 1) + readJsonArray(lastPageUrl).length();
    }

    private static String getLastPageUrl(String linkHeader){
        String[] links = linkHeader.split(", ");
        for (String link : links){
            String[] splitLink = link.split("; ");
            if (splitLink[1].equals("rel=\"last\"")){
                String url = splitLink[0];
                url = url.substring(1);
                url = url.substring(0, url.length() - 1);
                return url;
            }
        }
        throw new RuntimeException("Shouldn't be here");
    }

    private static int getLastPage(String linkHeader, String lastPageUrl){
        String[] lastPageUrlSplit = lastPageUrl.split("page=");
        return Integer.valueOf(lastPageUrlSplit[1]);
    }

}
