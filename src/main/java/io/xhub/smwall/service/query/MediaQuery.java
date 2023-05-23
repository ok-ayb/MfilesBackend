package io.xhub.smwall.service.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.xhub.smwall.domains.QMedia;
import io.xhub.smwall.enumeration.MediaSource;
import io.xhub.smwall.service.filter.BooleanFilter;
import io.xhub.smwall.service.filter.Filter;
import io.xhub.smwall.service.filter.StringFilter;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;

@Getter
@Setter
@ParameterObject
public class MediaQuery extends Query {
    private StringFilter text;
    private StringFilter userName;
    private Filter<MediaSource> source;
    private BooleanFilter hidden;

    @Override
    public Predicate buildPredicate() {
        BooleanBuilder builder = new BooleanBuilder();

        if (this.getText() != null) {
            buildStringPredicates(this.getText(), QMedia.media.text).forEach(builder::and);
        }

        if (this.getUserName() != null) {
            buildStringPredicates(this.getUserName(), QMedia.media.owner.username).forEach(builder::and);
        }

        if (this.getSource() != null) {
            buildCommonPredicates(this.getSource(), QMedia.media.source).forEach(builder::and);
        }

        if (this.getHidden() != null) {
            buildBooleanPredicates(this.getHidden(), QMedia.media.hidden).forEach(builder::and);
        }

        return builder.getValue();
    }
}
