package io.xhub.smwall.service.query;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.LiteralExpression;
import com.querydsl.core.types.dsl.StringPath;
import io.xhub.smwall.service.filter.BooleanFilter;
import io.xhub.smwall.service.filter.DateTimeFilter;
import io.xhub.smwall.service.filter.Filter;
import io.xhub.smwall.service.filter.StringFilter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Query implements Predictable {
    protected <T extends Comparable<?>> List<Predicate> buildCommonPredicates(Filter<T> filter, LiteralExpression<T> expression) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getEq() != null) {
            predicates.add(expression.eq(filter.getEq()));
        }

        if (filter.getSpecified() != null) {
            predicates.add(filter.getSpecified() ? expression.isNotNull() : expression.isNull());
        }

        if (filter.getIn() != null) {
            predicates.add(expression.in(filter.getIn()));
        }

        return predicates;
    }

    protected List<Predicate> buildStringPredicates(StringFilter filter, StringPath path) {
        List<Predicate> predicates = buildCommonPredicates(filter, path);

        if (filter.getContains() != null) {
            predicates.add(path.contains(filter.getContains()));
        }

        if (filter.getStartsWith() != null) {
            predicates.add(path.startsWith(filter.getStartsWith()));
        }

        if (filter.getEndsWith() != null) {
            predicates.add(path.endsWith(filter.getEndsWith()));
        }

        if (filter.getBetween() != null && filter.getBetween().size() > 1) {
            predicates.add(path.between(filter.getBetween().get(0), filter.getBetween().get(1)));
        }

        if (filter.getGoe() != null) {
            predicates.add(path.goe(filter.getGoe()));
        }

        if (filter.getGt() != null) {
            predicates.add(path.gt(filter.getGt()));
        }

        if (filter.getLoe() != null) {
            predicates.add(path.loe(filter.getLoe()));
        }

        if (filter.getLt() != null) {
            predicates.add(path.lt(filter.getLt()));
        }

        return predicates;
    }

    protected List<BooleanExpression> buildQueryExpressions(String term, List<StringPath> paths) {
        return paths.stream()
                .map(path -> path.containsIgnoreCase(term))
                .collect(Collectors.toList());
    }

    protected List<Predicate> buildDateTimePredicates(DateTimeFilter filter, DateTimePath<LocalDateTime> path) {
        List<Predicate> predicates = buildCommonPredicates(filter, path);

        if (filter.getBefore() != null) {
            predicates.add(path.before(filter.getBefore()));
        }

        if (filter.getAfter() != null) {
            predicates.add(path.after(filter.getAfter()));
        }

        if (filter.getBetween() != null && filter.getBetween().size() > 1) {
            predicates.add(path.between(filter.getBetween().get(0), filter.getBetween().get(1)));
        }

        return predicates;
    }

    protected List<Predicate> buildBooleanPredicates(BooleanFilter booleanFilter, BooleanExpression expression) {

        return buildCommonPredicates(booleanFilter, expression);
    }

    public abstract Predicate buildPredicate();
}
