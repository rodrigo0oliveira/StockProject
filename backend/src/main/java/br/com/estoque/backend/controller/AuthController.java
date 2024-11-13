package br.com.estoque.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estoque.backend.dtos.LoginDto;
import br.com.estoque.backend.dtos.RegisterDto;
import br.com.estoque.backend.security.TokenResponse;
import br.com.estoque.backend.services.AuthService;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto){

        String message = authService.createAccount(registerDto);
        if(message=="Conta criada"){
            return new ResponseEntity<>(message,(HttpStatus.CREATED));
        }
        else if(message=="O email informado já está cadastrado!"){
            return new ResponseEntity<>(message,(HttpStatus.CONFLICT));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginDto loginDto) throws Exception{
        TokenResponse tokenResponse = authService.login(loginDto);
        return new ResponseEntity<>(tokenResponse,(HttpStatus.OK));
    }
    
}