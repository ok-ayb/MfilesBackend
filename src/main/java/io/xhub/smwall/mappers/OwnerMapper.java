package io.xhub.smwall.mappers;

import io.xhub.smwall.domains.Owner;
import io.xhub.smwall.dto.OwnerDTO;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {
    public OwnerDTO toDTO(Owner owner) {
        if (owner == null)
            return null;

        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setId(owner.getId());
        ownerDTO.setUsername(owner.getUsername());
        ownerDTO.setAvatar(owner.getAvatar());
        return ownerDTO;
    }
}
