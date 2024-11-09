package br.com.estoque.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    private TokenProvider tokenProvider;

    @Autowired
    public SecurityConfiguration(TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
        .csrf(csrf-> csrf.disable())
        .authorizeHttpRequests(authorize -> authorize
        .requestMatchers(PathRequest.toH2Console()).permitAll()
        .requestMatchers("/h2-console/**").permitAll()
        .anyRequest().permitAll())
        .headers(headers -> headers
                .frameOptions().disable() 
            )
            .csrf(csrf-> csrf.ignoringRequestMatchers("/h2-console/**"))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(new JwtFilter(tokenProvider),UsernamePasswordAuthenticationFilter.class)
        .httpBasic(httpBasic-> httpBasic.disable())
        .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(new AccessDeniedHandlerImpl()));


        return http.build();
        
    }
    
}
