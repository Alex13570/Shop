package ru.ivmiit.shop.security.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.ivmiit.shop.entities.RefreshToken;
import ru.ivmiit.shop.entities.UserEntity;
import ru.ivmiit.shop.exceptions.token.InvalidTokenException;
import ru.ivmiit.shop.security.TokenProvider;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class TokenProviderImpl implements TokenProvider {

    private String secretKey = "hyperthreadingonyourpc";

    private long expiredTimeForAccessTokenInMillis = 3_600_000;

    private long expiredTimeForRefreshTokenInMillis = 36_000_000;

    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @Override
    public String generateAccessToken(String email, String role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date startTime = new Date();
        Date endTime = new Date(startTime.getTime() + expiredTimeForAccessTokenInMillis);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(startTime)
                .setExpiration(endTime)
                .signWith(ALGORITHM, secretKey)
                .compact();
    }

    @Override
    public RefreshToken generateRefreshToken(UserEntity userAccount) {
        return RefreshToken.builder()
                .createdDate(Instant.now())
                .expiredTime(Instant.now().plusMillis(expiredTimeForRefreshTokenInMillis))
                .userAccount(userAccount)
                .build();
    }

    @Override
    public boolean isValidAccessToken(String token) {
        if (token == null) return false;
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
            return true;
        } catch (JwtException e){
            throw new InvalidTokenException("Invalid token structure");
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException("Missed or corrupted token");
        }
    }

    @Override
    public Authentication getAuthenticationFromAccessToken(String token) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(getUsernameFromAccessToken(token));
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        }catch (UsernameNotFoundException e) {
            throw new InvalidTokenException("No user in token");
        }
    }

    @Override
    public String getUsernameFromAccessToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public String getAccessTokenFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION);
    }

}
