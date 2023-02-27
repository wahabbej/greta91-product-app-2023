package fr.greta91.productapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import fr.greta91.productapp.filter.JwtAuthFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	JwtAuthFilter jwtTokenAuthFilter;
	
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
	/**
	 * cette configuration est applicable uniquement des urls commence par /api/
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Order(1)                                                        
	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
		http
			.antMatcher("/api/**")                                   
			.authorizeHttpRequests(authorize -> authorize
					// régles d'accès proprement dites
					.mvcMatchers(HttpMethod.GET, "/api/public/**").permitAll()
					.mvcMatchers(HttpMethod.POST, "/api/auth/login").permitAll()		
					.mvcMatchers(HttpMethod.POST, "/api/auth/register").permitAll()	
					.mvcMatchers("/api/products/**").hasRole("ADMIN")
					.mvcMatchers("/api/**").authenticated()
			);
		// On désactive la protection contre les CSRF.
		// Dans l'état actuel du logiciel, elle est inutile
		// Le probléme est que cette protection est stateful...
		http.csrf().disable();
		// on autorise cors
		http.cors();//cross origin request sharing
		// pas de session, on utilise JWT pour ça...
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// on se débarrasse de la sécurité par défaut.
		http.httpBasic().disable();
		// on désactive aussi la page de login
		http.formLogin().disable();	
		// chain of reponsibility
		// on met en place le filtre qui va récupérer le token s'il existe et authentifier l'utilisateur.
		http.addFilterBefore(jwtTokenAuthFilter, UsernamePasswordAuthenticationFilter.class);			
		return http.build();
	}
}
