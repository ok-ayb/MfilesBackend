package io.xhub.smwall.service.query;

import com.querydsl.core.types.Predicate;

public interface Predictable {
    Predicate buildPredicate();
}
