package io.xhub.smwall.service.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.xhub.smwall.domains.QUser;
import io.xhub.smwall.service.filter.StringFilter;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;

import java.util.List;

@Getter
@Setter
@ParameterObject
public class UserQuery extends Query {
    private String q;
    private StringFilter firstName;
    private StringFilter lastName;
    private StringFilter email;
    private StringFilter authorities;

    @Override
    public Predicate buildPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        if (this.getQ() != null) {
            QUser qUser = QUser.user;
            buildQueryExpressions(this.getQ(), List.of(qUser.firstName, qUser.lastName, qUser.email)).forEach(builder::or);
        }
        if (this.getEmail() != null) {
            buildStringPredicates(this.getEmail(), QUser.user.email).forEach(builder::and);
        }
        if (this.getFirstName() != null) {
            buildStringPredicates(this.getFirstName(), QUser.user.firstName).forEach(builder::and);
        }
        if (this.getLastName() != null) {
            buildStringPredicates(this.getLastName(), QUser.user.lastName).forEach(builder::and);
        }
        if (this.getAuthorities() != null) {
            buildStringPredicates(this.getAuthorities(), QUser.user.authorities.any().id).forEach(builder::and);
        }
        return builder.getValue();
    }
}
