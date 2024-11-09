package br.com.estoque.backend.services;


import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.estoque.backend.dtos.RegisterDto;
import br.com.estoque.backend.entities.User;
import br.com.estoque.backend.repository.UserRepository;

@Service
public class AuthService {

    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository,RoleService roleService
    ,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public String createAccount(RegisterDto registerDto){
        String passwordEncrypted = passwordEncoder.encode(registerDto.password());

        User newUser = User.builder()
            .id(UUID.randomUUID().toString())
            .email(registerDto.name())
            .password(passwordEncrypted)
            .roles(Collections.singletonList(roleService.findByName(registerDto.role())))
            .build();

        userRepository.save(newUser);

        return "Conta criada";
        
    }
    
}
