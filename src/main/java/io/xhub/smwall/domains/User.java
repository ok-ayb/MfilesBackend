package io.xhub.smwall.domains;

import io.xhub.smwall.commands.UserUpdateCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;


@Document(collection = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractAuditingDocument {
    @Id
    private String id;

    @Field("firstName")
    private String firstName;

    @Field("lastName")
    private String lastName;

    @Field("email")
    private String email;

    @Field("password")
    private String password;

    @Field("activated")
    private boolean activated;

    @Field("authorities")
    @DBRef
    private Set<Authority> authorities;

    public void update(final UserUpdateCommand userUpdateCommand) {

        if (userUpdateCommand.getFirstName() != null) {
            this.setFirstName(userUpdateCommand.getFirstName());
        }
        if (userUpdateCommand.getLastName() != null) {
            this.setLastName(userUpdateCommand.getLastName());
        }
        if (userUpdateCommand.getActivated() != null) {
            this.setActivated(userUpdateCommand.getActivated());
        }
        if (userUpdateCommand.getEmail() != null) {
            this.setEmail(userUpdateCommand.getEmail());
        }
        if (userUpdateCommand.getAuthorities() != null) {
            this.setAuthorities(userUpdateCommand.getAuthorities());
        }
    }
}
