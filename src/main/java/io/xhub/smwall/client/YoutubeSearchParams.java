package io.xhub.smwall.client;

import io.xhub.smwall.enumeration.YoutubeParams;

import java.util.HashMap;
import java.util.Map;

public class YoutubeSearchParams {

    public static Map<String, String> getDefaultSearchParams() {
        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put(YoutubeParams.PART.name().toLowerCase(), YoutubeParams.PART.getValue());
        queryParamsMap.put(YoutubeParams.TYPE.name().toLowerCase(), YoutubeParams.TYPE.getValue());
        queryParamsMap.put(YoutubeParams.VIDEO_DURATION.name().toLowerCase(), YoutubeParams.VIDEO_DURATION.getValue());
        queryParamsMap.put(YoutubeParams.MAX_DURATION.name().toLowerCase(), YoutubeParams.MAX_DURATION.getValue());
        queryParamsMap.put(YoutubeParams.Q.name().toLowerCase(), YoutubeParams.Q.getValue());
        queryParamsMap.put(YoutubeParams.MAX_RESULTS.name().toLowerCase(), YoutubeParams.MAX_RESULTS.getValue());
        queryParamsMap.put(YoutubeParams.ORDER.name().toLowerCase(), YoutubeParams.ORDER.getValue());
        return queryParamsMap;
    }
    public static Map<String, String> getVideoSearchParams() {
        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put(YoutubeParams.PART.name().toLowerCase(), YoutubeParams.PART.getValue());
        queryParamsMap.put(YoutubeParams.TYPE.name().toLowerCase(), YoutubeParams.TYPE.getValue());
        queryParamsMap.put(YoutubeParams.MAX_RESULTS.name().toLowerCase(), YoutubeParams.MAX_RESULTS.getValue());
        queryParamsMap.put(YoutubeParams.ORDER.name().toLowerCase(), YoutubeParams.ORDER.getValue());
        return queryParamsMap;
    }
}

