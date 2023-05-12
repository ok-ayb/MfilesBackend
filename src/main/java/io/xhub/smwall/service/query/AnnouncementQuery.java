package io.xhub.smwall.service.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.xhub.smwall.domains.QAnnouncement;
import io.xhub.smwall.service.filter.DateTimeFilter;
import io.xhub.smwall.service.filter.StringFilter;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;

@Getter
@Setter
@ParameterObject
public class AnnouncementQuery extends Query {
    private StringFilter title;
    private StringFilter description;
    private DateTimeFilter startDate;
    private DateTimeFilter endDate;

    @Override
    public Predicate buildPredicate() {
        BooleanBuilder builder = new BooleanBuilder();

        if (this.getTitle() != null) {
            buildStringPredicates(this.getTitle(), QAnnouncement.announcement.title).forEach(builder::and);
        }

        if (this.getDescription() != null) {
            buildStringPredicates(this.getDescription(), QAnnouncement.announcement.description).forEach(builder::and);
        }

        if (this.getStartDate() != null) {
            buildDateTimePredicates(this.getStartDate(), QAnnouncement.announcement.startDate).forEach(builder::and);
        }

        if (this.getEndDate() != null) {
            buildDateTimePredicates(this.getEndDate(), QAnnouncement.announcement.endDate).forEach(builder::and);
        }

        return builder.getValue();
    }
}
