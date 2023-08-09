package io.xhub.smwall.domains;

import io.xhub.smwall.constants.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "authorities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
    @Id
    private String id;

    @Field("name")
    private RoleName name;
}
