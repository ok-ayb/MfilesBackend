package io.xhub.smwall.utlis;

import io.xhub.smwall.domains.Authority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SecurityUtils {
    public static Optional<String> getCurrentUserLogin() {
        return Optional.ofNullable(extractPrincipal(
                SecurityContextHolder.getContext().getAuthentication()
        ));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }
}
