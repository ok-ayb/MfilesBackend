package io.xhub.smwall.mappers;

import io.xhub.smwall.domains.User;
import io.xhub.smwall.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        if (user == null)
            return null;

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setAuthorities(user.getAuthorities());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setLastModifiedAt(user.getLastModifiedAt());
        userDTO.setActivated(user.isActivated());
        return userDTO;
    }
}
