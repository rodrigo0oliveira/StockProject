package br.com.estoque.backend.services;


import java.util.Collections;
import java.util.UUID;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.estoque.backend.dtos.LoginDto;
import br.com.estoque.backend.dtos.RegisterDto;
import br.com.estoque.backend.entities.User;
import br.com.estoque.backend.repository.UserRepository;
import br.com.estoque.backend.security.TokenProvider;
import br.com.estoque.backend.security.TokenResponse;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    public String createAccount(RegisterDto registerDto){
        if(findByEmail(registerDto.email())){
            return "O email informado já está cadastrado!";
        }
        String passwordEncrypted = passwordEncoder.encode(registerDto.password());

        User newUser = User.builder()
            .id(UUID.randomUUID().toString())
            .email(registerDto.email())
            .password(passwordEncrypted)
            .roles(Collections.singletonList(roleService.findByName(registerDto.role())))
            .build();

        userRepository.save(newUser);

        return "Conta criada";
        
    }

    public TokenResponse login(LoginDto loginDto, HttpServletResponse response) throws Exception{
        try{
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));

            TokenResponse tokenResponse =  tokenProvider.generateToken(authentication);
            Cookie cookie = tokenProvider.createCookie(response,tokenResponse.getToken(),7200);
            response.addCookie(cookie);
            return tokenResponse;
        }catch(AuthenticationException e){
            throw new Exception("Credenciais inválidas :"+e.getMessage());
        }catch(Exception e) {
            throw new Exception("Erro ao autenticar: " + e.getMessage());
        }
    }

    public boolean findByEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public User getUser(Authentication authentication){
        String id = authentication.getName();
        return userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found"));


    }
    
}
