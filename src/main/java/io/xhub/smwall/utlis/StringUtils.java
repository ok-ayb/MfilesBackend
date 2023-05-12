package io.xhub.smwall.utlis;

public class StringUtils {
    private static final String CHANNEL_URL_EMBED = "https://www.youtube.com/embed/";

    public static String concat(String str1, String str2) {
        return new StringBuilder().append(str1).append(str2).toString();
    }

    public static String generateStringEmbedUrl(String channelId) {
        return CHANNEL_URL_EMBED.concat(channelId);
    }
}
