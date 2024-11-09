package br.com.estoque.backend.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import br.com.estoque.backend.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtFilter extends GenericFilterBean{

    private TokenProvider tokenProvider;

    @Autowired
    public JwtFilter(TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String jwt = httpServletRequest.getHeader("Authorization");

        if(StringUtils.hasText(jwt)) {
			if(tokenProvider.isValid(jwt, servletResponse)) {
				final User user = tokenProvider.getUserFromToken(jwt);
				UsernamePasswordAuthenticationToken authenticationToken = 
						new UsernamePasswordAuthenticationToken(user.getId(),null,user.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
			}
			else {
				return ;
			}
		}

        chain.doFilter(httpServletRequest, servletResponse);
    }
    
}
