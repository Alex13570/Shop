package ru.ivmiit.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ivmiit.shop.dto.request.AuthRequest;
import ru.ivmiit.shop.dto.request.RefreshTokenRequest;
import ru.ivmiit.shop.dto.request.SignUpRequest;
import ru.ivmiit.shop.dto.response.AuthResponse;
import ru.ivmiit.shop.services.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"${settings.cors_origin}"})
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse signIn(@RequestBody AuthRequest authRequest){
        return authService.authenticate(authRequest);
    }

    @PostMapping(path = "/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse signUp(@RequestBody SignUpRequest signUpRequest){
        return authService.signUp(signUpRequest);
    }

    @PostMapping(path = "/refresh")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse refresh(@RequestBody RefreshTokenRequest tokenRequest){
        return authService.updateAccessTokenWithRefreshToken(tokenRequest);
    }

}
