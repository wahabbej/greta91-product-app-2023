package fr.greta91.productapp.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import fr.greta91.productapp.service.JwtResult;
import fr.greta91.productapp.service.JwtService;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	@Autowired
	JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String autorisation = request.getHeader("Authorization");
		System.out.println(autorisation);
		//Authorization:bearer TOKENJWT
		if (autorisation != null && autorisation.startsWith("Bearer ")) {
			autorisation = autorisation.substring("bearer ".length());
			System.out.println(autorisation);
			JwtResult parsed = jwtService.checkAutorisation(autorisation);
			if (parsed.isOk()) {
				Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
				if (parsed.isAdmin()) {
					authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
					authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
				}
				else {
					authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
				}
				UsernamePasswordAuthenticationToken result = 
						new UsernamePasswordAuthenticationToken(parsed.getLogin(),
						"", authorities);
				SecurityContextHolder.getContext().setAuthentication(result);
			}
		}
		filterChain.doFilter(request, response);
	}

}
