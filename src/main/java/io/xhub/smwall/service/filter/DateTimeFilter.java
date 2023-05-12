package io.xhub.smwall.service.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DateTimeFilter extends Filter<LocalDateTime> {
    private LocalDateTime before;
    private LocalDateTime after;
    private List<LocalDateTime> between;
}
