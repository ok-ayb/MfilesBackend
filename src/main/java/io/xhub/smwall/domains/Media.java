package io.xhub.smwall.domains;

import io.xhub.smwall.enumeration.MediaSource;
import io.xhub.smwall.enumeration.MediaType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Document(collection = "media")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Media {
    @Id
    private String id;

    @Field("text")
    private String text;

    @Field("type")
    private MediaType type;

    @Field("source")
    private MediaSource source;

    @Field("url")
    private String url;

    @Field("thumbnail")
    private String thumbnail;

    @Field("permalink")
    private String permalink;

    @Field("children")
    private List<MediaChild> children;

    @Field("timestamp")
    private Instant timestamp;

    @Field("owner")
    private Owner owner;

    @Field("hidden")
    private Boolean hidden = false;

    @Field("pinned")
    private Boolean pinned = false;

    @Field("sourceTypes")
    private List<String> sourceTypes;

    @Field("clean")
    private Boolean clean;

    @Field("analyzed")
    private Boolean analyzed;


}
