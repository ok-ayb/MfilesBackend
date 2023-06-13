package io.xhub.smwall.constants;

public class RegexPatterns {
    public final static String SOCIAL_MEDIA_HASHTAG = "#[^ !@#$%^&*(),.?\":{}|<>]*";
    public final static String USER_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public final static String SOURCE_TYPES = "(?:^|\\s+)(?:(?<mention>@)|(?<hash>#))(?<item>\\w+)(?=\\s*)";
    public static final String WALL_TITLE = "^[\\s\\S]{5,}$";
    public final static String ANNOUNCEMENT_TITLE = "^[a-zA-Z0-9'\\- ]{5,}$";
    public final static String ANNOUNCEMENT_DESCRIPTION = "^(.{0,200})$";
}
