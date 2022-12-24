package ru.ivmiit.shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String email;

    private String firstName;

    private String lastName;

    private String accessToken;

    private String refreshToken;

    private String role;

}
