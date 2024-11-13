package br.com.estoque.backend.security;

import lombok.*;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class TokenResponse {

    private String token;

    private long expiresIn;

    private String userEmail;

}
