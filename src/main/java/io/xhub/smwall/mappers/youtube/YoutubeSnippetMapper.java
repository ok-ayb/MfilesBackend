package io.xhub.smwall.mappers.youtube;

import io.xhub.smwall.domains.Owner;
import io.xhub.smwall.dto.youtube.YoutubeVideoSnippetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class YoutubeSnippetMapper {
    public Owner toOwnerEntity(YoutubeVideoSnippetDTO youtubeVideoSnippetDTO) {
        if (youtubeVideoSnippetDTO == null)
            return null;

        Owner owner = new Owner();
        owner.setId(youtubeVideoSnippetDTO.getChannelId());
        owner.setUsername(youtubeVideoSnippetDTO.getChannelTitle());
        owner.setAvatar(youtubeVideoSnippetDTO.getAvatar());

        return owner;
    }
}
