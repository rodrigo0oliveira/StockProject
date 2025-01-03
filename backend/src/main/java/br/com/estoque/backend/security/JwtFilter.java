package br.com.estoque.backend.security;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean{

    private final TokenProvider tokenProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		String jwt = tokenProvider.getJwtFromCookie(httpServletRequest);
		if(StringUtils.hasText(jwt)) {
			if(tokenProvider.isValid(jwt, response)) {
				final User user = tokenProvider.getUserFromToken(jwt);
				UsernamePasswordAuthenticationToken authenticationToken =
						new UsernamePasswordAuthenticationToken(user.getId(),null,user.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			else {
				return ;
			}
		}
		chain.doFilter(httpServletRequest, response);

	}
    
}
