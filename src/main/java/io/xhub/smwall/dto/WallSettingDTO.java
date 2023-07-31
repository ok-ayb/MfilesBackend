package io.xhub.smwall.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WallSettingDTO {
    private String id;
    private String title;
    private String filename;
    private String logoBase64;

}
