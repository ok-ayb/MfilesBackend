package io.xhub.smwall.service.filter;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Filter<T> implements Serializable {
    private T eq;
    private Boolean specified;
    private List<T> in;
}
