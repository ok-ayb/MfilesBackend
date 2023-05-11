package io.xhub.smwall.dto;

import io.xhub.smwall.enumeration.MediaSource;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WallHeaderDTO {
    private String logoUrl;
    private String title;
    private List<MediaSource> sources;

}
