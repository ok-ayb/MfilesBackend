package io.xhub.smwall.smbackend.client.meta.response;

import io.xhub.smwall.smbackend.dto.IGMedia;
import lombok.Getter;

import java.util.List;

@Getter
public class IGHashtaggedMediaResponse {
    public final static String[] FIELDS = {"id", "caption", "media_type", "media_url", "permalink", "timestamp"};
    private List<IGMedia> data;
}
