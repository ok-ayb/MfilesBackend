package io.xhub.smwall.utlis;

public class StringUtils {
    private static final String CHANNEL_URL_EMBED = "https://www.youtube.com/embed/";

    public static String concat(String str1, String str2) {
        return new StringBuilder().append(str1).append(str2).toString();
    }

    public static String generateStringEmbedUrl(String channelId) {
        return CHANNEL_URL_EMBED.concat(channelId);
    }

    public static String prependHashtag(String hashtag) {
        return new StringBuilder().append("#").append(hashtag.substring(hashtag.indexOf('.') + 1)).toString();
    }

    public static String prependAtSign(String mention) {
        return new StringBuilder().append("@").append(mention).toString();
    }
}
