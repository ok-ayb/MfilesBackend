package io.xhub.smwall.smbackend.client.meta.response;

import io.xhub.smwall.smbackend.dto.InstagramMediaDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class InstagramMediaResponse {
    public final static String[] FIELDS = {"id", "caption", "media_type", "media_url", "permalink", "timestamp"};
    private List<InstagramMediaDTO> data;
}
