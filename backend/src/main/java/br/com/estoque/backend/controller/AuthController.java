package br.com.estoque.backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.estoque.backend.dtos.LoginDto;
import br.com.estoque.backend.dtos.RegisterDto;
import br.com.estoque.backend.security.TokenResponse;
import br.com.estoque.backend.services.AuthService;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @CrossOrigin(origins = "https://localhost")
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
    @CrossOrigin(origins = "https://localhost")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginDto loginDto,HttpServletResponse response) throws Exception{
        TokenResponse tokenResponse = authService.login(loginDto,response);
        return new ResponseEntity<>(tokenResponse,(HttpStatus.OK));
    }

    @CrossOrigin(origins = "https://localhost", allowCredentials = "true")
    @PostMapping("/logout")
    public void logout(HttpServletResponse response, HttpServletRequest request){
        request.getSession().invalidate();

        Cookie cookie = new Cookie("jwt","");
        cookie.setDomain("localhost");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}