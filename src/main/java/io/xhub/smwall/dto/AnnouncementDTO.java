package io.xhub.smwall.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AnnouncementDTO {

    private String id;

    private String title;

    private String description;
    private boolean deleted;

    private Instant startDate;
    private Instant endDate;
}
