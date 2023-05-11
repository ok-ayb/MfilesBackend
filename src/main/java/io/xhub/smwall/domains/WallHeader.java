package io.xhub.smwall.domains;

import io.xhub.smwall.enumeration.MediaSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Document(collection = "wall_Header")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WallHeader {

    @Id
    @Field("id")
    private String id;
    @Field("logoUrl")
    private String logoUrl;
    @Field("title")
    private String title;
    @Field("sources")
    private List<MediaSource> sources;
    @Field("timestamp")
    private Instant timestamp;

}
