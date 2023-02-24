package fr.greta91.productapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class WebSecurityConfig {
	/**
	 * hachage de mot de passe
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	/**
	 * Config d'authentification
	 * @return UserDetailsService
	 */
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		UserDetails user = User.builder()
//			.username("user")
//			.password("$2a$10$ESndoqC0EpcmuloTKUB.QuZvqSgg1654qlnCiVydByJBnCaYMoaRu")
//			.roles("USER")
//			.build();
//		UserDetails admin = User.builder()
//			.username("admin")
//			.password("$2a$10$tGzHu0NvhzW9wQqyqBfF.e2MiMiIPO5xhPeZ9xGdCabvf1wrOPLYK")
//			.roles("USER", "ADMIN")
//			.build();
//		UserDetails client = User.builder()
//				.username("client")
//				.password("$2a$10$tGzHu0NvhzW9wQqyqBfF.e2MiMiIPO5xhPeZ9xGdCabvf1wrOPLYK")
//				.roles("CLIENT")
//				.build();
//			
//		InMemoryUserDetailsManager manager = 
//				new InMemoryUserDetailsManager(user, admin, client);
//		//API fluent
////		manager.createUser(User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build());
////		manager.createUser(User.withDefaultPasswordEncoder().username("admin").password("admin").roles("USER","ADMIN").build());
//		return manager;
//	}
	/**
	 * Config d'autorisation
	 * @return UserDetailsService
	 */
	@Bean
	SecurityFilterChain web(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorize -> authorize
				.mvcMatchers("/admin/**").hasRole("ADMIN")
				.mvcMatchers("/client/**").hasRole("CLIENT")
				.anyRequest().permitAll()
			)
			.formLogin(withDefaults())
			.httpBasic(withDefaults());

		return http.build();
	}
}
