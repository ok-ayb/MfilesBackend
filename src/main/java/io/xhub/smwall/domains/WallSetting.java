package io.xhub.smwall.domains;

import io.xhub.smwall.commands.WallSettingAddCommand;
import io.xhub.smwall.commands.WallSettingUpdateCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Document(collection = "wallSettings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WallSetting {
    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("logo")
    private BinaryImage logo;

    public void initLogo(MultipartFile file) throws IOException {
        BinaryImage logo = new BinaryImage();
        logo.setValue(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        logo.setFilename(file.getOriginalFilename());
        logo.setContentType(file.getContentType());

        setLogo(logo);
    }

    public static WallSetting create(WallSettingAddCommand command) throws IOException {
        WallSetting wallSetting = new WallSetting();
        wallSetting.setTitle(command.getTitle());
        wallSetting.initLogo(command.getLogo());

        return wallSetting;
    }

    public void update(WallSettingUpdateCommand command) throws IOException {
        if (command.getTitle() != null) {
            this.setTitle(command.getTitle());
        }
        if (command.getLogo() != null) {
            this.initLogo(command.getLogo());
        }
    }
}
