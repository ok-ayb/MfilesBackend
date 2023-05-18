package io.xhub.smwall.constants;

public class RegexPatterns {
    public final static String SOCIAL_MEDIA_HASHTAG = "#[^ !@#$%^&*(),.?\":{}|<>]*";
    public final static String USER_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
}
