package io.xhub.smwall.service.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class DateTimeFilter extends Filter<Instant> {
    private Instant before;
    private Instant after;
    private List<Instant> between;
}
