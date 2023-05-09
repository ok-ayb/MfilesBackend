package io.xhub.smwall.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "announcements")
public class Announcement {
    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("startDate")
    private LocalDateTime startDate;

    @Field("endDate")
    private LocalDateTime endDate;
}
