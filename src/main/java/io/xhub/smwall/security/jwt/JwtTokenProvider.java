package io.xhub.smwall.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.xhub.smwall.config.JwtProperties;
import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider implements AuthenticationProvider {
    private static final String AUTHORITIES_KEY = "authorities";
    private final Key key;
    private final JwtParser jwtParser;
    private final JwtProperties jwtProperties;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public JwtTokenProvider(JwtProperties jwtProperties, UserService userService, PasswordEncoder passwordEncoder) {
        String base64Secret = jwtProperties.getBase64Secret();

        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.jwtProperties = jwtProperties;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateToken(Authentication authentication) {
        String authorities = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Instant now = Instant.now();
        Instant expiryDate = now.plusSeconds(jwtProperties.getTokenValidity());

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiryDate))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.error("Token validation error {}", e.getMessage());
        }

        return false;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        io.xhub.smwall.domains.User user = userService.findUserByLogin(login);

        if (passwordEncoder.matches(password, user.getPassword())) {
            final List<GrantedAuthority> authorities = user
                    .getAuthorities()
                    .stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                    .collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(login, password, authorities);
        } else {
            throw new BusinessException(ApiClientErrorCodes.WRONG_CREDENTIALS.getErrorMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
