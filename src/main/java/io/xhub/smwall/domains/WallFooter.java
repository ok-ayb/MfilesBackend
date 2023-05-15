package io.xhub.smwall.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Document(collection = "wall_Footer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WallFooter {
    @Id
    @Field("id")
    private String id;

    @Field("coOrganizer")
    private String coOrganizer;

    @Field("institutionalPartners")
    private List<String> institutionalPartners;

    @Field("sponsors")
    private List<Sponsor> sponsors;

    @Field("logoUrl")
    private String logoUrl;

    @Field("timestamp")
    private Instant timestamp;
}
