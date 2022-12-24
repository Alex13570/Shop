package ru.ivmiit.shop.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ivmiit.shop.api.Role;
import ru.ivmiit.shop.dto.request.AuthRequest;
import ru.ivmiit.shop.dto.request.RefreshTokenRequest;
import ru.ivmiit.shop.dto.request.SignUpRequest;
import ru.ivmiit.shop.dto.response.AuthResponse;
import ru.ivmiit.shop.entities.RefreshToken;
import ru.ivmiit.shop.entities.UserEntity;
import ru.ivmiit.shop.exceptions.token.InvalidTokenException;
import ru.ivmiit.shop.exceptions.user.UserEmailAlreadyTakenException;
import ru.ivmiit.shop.exceptions.user.UserNotFoundException;
import ru.ivmiit.shop.exceptions.user.UserUnauthorizedException;
import ru.ivmiit.shop.repositories.RefreshTokenRepository;
import ru.ivmiit.shop.repositories.UserRepository;
import ru.ivmiit.shop.security.TokenProvider;
import ru.ivmiit.shop.services.AuthService;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userAccountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse authenticate(AuthRequest authRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String accessToken = tokenProvider.generateAccessToken(userDetails.getUsername(),
                    userDetails.getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(",")));
            UserEntity userAccount = userAccountRepository.findByEmail(authRequestDto.getEmail())
                    .orElseThrow(UserNotFoundException::new);
            RefreshToken refreshToken = tokenProvider.generateRefreshToken(userAccount);
            userAccount = saveRefreshTokenInUserAccount(userAccount, refreshToken);
            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(userAccount.getRefreshToken().getId().toString())
                    .email(userAccount.getEmail())
                    .firstName(userAccount.getFirstName())
                    .lastName(userAccount.getLastName())
                    .role(userAccount.getRole().toString())
                    .build();
        }catch (AuthenticationException e) {
            throw new UserUnauthorizedException(e.getMessage());
        }
    }

    @Override
    public AuthResponse updateAccessTokenWithRefreshToken(RefreshTokenRequest tokenRequest) {
        RefreshToken refreshToken = refreshTokenRepository.findById(tokenRequest.getRefreshToken())
                .orElseThrow(InvalidTokenException::new);

        if(!refreshToken.getExpiredTime().isAfter(Instant.now())) throw new InvalidTokenException();

        UserEntity userAccount = userAccountRepository.findByRefreshToken(refreshToken)
                .orElseThrow(UserNotFoundException::new);
        String accessToken = tokenProvider.generateAccessToken(userAccount.getEmail(), userAccount.getRole().toString());
        refreshToken = tokenProvider.generateRefreshToken(userAccount);
        userAccount = saveRefreshTokenInUserAccount(userAccount, refreshToken);
        return AuthResponse.builder()
                .refreshToken(userAccount.getRefreshToken().getId().toString())
                .accessToken(accessToken)
                .email(userAccount.getEmail())
                .firstName(userAccount.getFirstName())
                .lastName(userAccount.getLastName())
                .role(userAccount.getRole().toString())
                .build();
    }

    @Override
    public AuthResponse signUp(SignUpRequest request) {
        if(userAccountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserEmailAlreadyTakenException();
        }

        userAccountRepository.save(UserEntity.builder()
                .email(request.getEmail())
                .role(Role.USER)
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build());

        return authenticate(AuthRequest.builder().email(request.getEmail()).password(request.getPassword()).build());
    }

    private UserEntity saveRefreshTokenInUserAccount(UserEntity userAccount, RefreshToken refreshToken) {
        userAccount.setRefreshToken(refreshToken);
        return userAccountRepository.save(userAccount);
    }

}
