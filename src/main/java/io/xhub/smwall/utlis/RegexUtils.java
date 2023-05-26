package io.xhub.smwall.utlis;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static List<String> findAllowedMatches(String text, String pattern, List<String> allowedMatches) {
        List<String> matches = new ArrayList<>();
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(text);

        while (matcher.find()) {
            String match = matcher.group().trim();
            if (allowedMatches.stream().anyMatch(match::equalsIgnoreCase)) {
                matches.add(match);
            }
        }

        return matches;
    }
}
