package br.com.estoque.backend.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class TokenResponse {

    private String token;

    private long expiresIn;

    private String user_id;

}
