package io.xhub.smwall.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnnouncementDTO {
    private String id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
