package io.xhub.smwall.client.response;

import io.xhub.smwall.dto.meta.InstagramMediaDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class InstagramMediaResponse {
    private List<InstagramMediaDTO> data;
}
