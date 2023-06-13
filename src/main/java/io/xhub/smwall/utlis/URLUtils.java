package io.xhub.smwall.utlis;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLUtils {
    public static boolean isURLValid(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
