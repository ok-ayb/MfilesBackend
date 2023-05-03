package io.xhub.smwall.mappers.meta;

import io.xhub.smwall.domains.Owner;
import io.xhub.smwall.dto.meta.InstagramUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramUserMapper {
    public Owner toOwnerEntity(InstagramUserDTO instagramUserDTO) {
        if (instagramUserDTO == null)
            return null;

        Owner owner = new Owner();
        owner.setId(instagramUserDTO.getId());
        owner.setUsername(instagramUserDTO.getUsername());
        owner.setAvatar(instagramUserDTO.getProfilePictureUrl());
        return owner;
    }
}