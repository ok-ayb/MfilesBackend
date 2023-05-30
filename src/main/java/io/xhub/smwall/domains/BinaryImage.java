package io.xhub.smwall.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BinaryImage {
    private Binary value;
    private String contentType;
    private String filename;
}
