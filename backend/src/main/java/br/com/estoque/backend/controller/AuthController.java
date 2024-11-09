package br.com.estoque.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estoque.backend.dtos.RegisterDto;
import br.com.estoque.backend.services.AuthService;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto){

        String message = authService.createAccount(registerDto);
        if(message=="Conta criada"){
            return new ResponseEntity<>(message,(HttpStatus.CREATED));
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
}