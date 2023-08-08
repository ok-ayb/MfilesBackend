package io.xhub.smwall.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.xhub.smwall.commands.UserUpdateCommand;
import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.dto.UserDTO;
import io.xhub.smwall.mappers.UserMapper;
import io.xhub.smwall.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "User management resource")
@RestController
@RequestMapping(ApiPaths.V1 + ApiPaths.USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @ApiOperation(value = "Update user")
    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String userId, @RequestBody @Valid UserUpdateCommand userUpdateCommand) {
        return ResponseEntity.ok()
                .body(userMapper.toDTO(userService.updateUser(userId, userUpdateCommand)));
    }
}
