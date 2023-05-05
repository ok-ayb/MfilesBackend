package io.xhub.smwall.enumeration;

public enum YoutubeParams {
    PART("snippet"),
    TYPE("video"),
    VIDEO_DURATION("short"),
    MAX_DURATION("60"),
    Q("%23shorts"),
    MAX_RESULTS("10"),
    ORDER("date");

    private final String value;

    YoutubeParams(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
