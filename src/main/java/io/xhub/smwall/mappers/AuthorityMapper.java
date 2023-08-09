package io.xhub.smwall.mappers;

import io.xhub.smwall.domains.Authority;
import io.xhub.smwall.dto.AuthorityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorityMapper {
    public AuthorityDTO toDTO(Authority authority) {
        if (authority == null)
            return null;

        AuthorityDTO authorityDTO = new AuthorityDTO();
        authorityDTO.setId(authority.getId());
        authorityDTO.setName(authority.getName());

        return authorityDTO;
    }
}
