package ru.ivmiit.shop.services;

import ru.ivmiit.shop.dto.request.AuthRequest;
import ru.ivmiit.shop.dto.request.RefreshTokenRequest;
import ru.ivmiit.shop.dto.request.SignUpRequest;
import ru.ivmiit.shop.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse authenticate(AuthRequest authRequest);

    AuthResponse updateAccessTokenWithRefreshToken(RefreshTokenRequest tokenRequest);

    AuthResponse signUp(SignUpRequest request);

}
