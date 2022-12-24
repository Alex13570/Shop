package ru.ivmiit.shop.security;

import org.springframework.security.core.Authentication;
import ru.ivmiit.shop.dto.request.RefreshTokenRequest;
import ru.ivmiit.shop.entities.RefreshToken;
import ru.ivmiit.shop.entities.UserEntity;

import javax.servlet.http.HttpServletRequest;

public interface TokenProvider {

    String generateAccessToken(String email, String role);

    RefreshToken generateRefreshToken(UserEntity userAccount);

    boolean isValidAccessToken(String token);

    Authentication getAuthenticationFromAccessToken(String token);

    String getUsernameFromAccessToken(String token);

    String getAccessTokenFromHeader(HttpServletRequest request);
}
