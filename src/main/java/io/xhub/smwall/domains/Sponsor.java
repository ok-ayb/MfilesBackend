package io.xhub.smwall.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sponsor {

    @Field("id")
    private String id;
    @Field("type")
    private String type;
    @Field("logoUrl")
    private String logoUrl;
}
