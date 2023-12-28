package com.ll.medium.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeRequests(authorizeRequests ->
						authorizeRequests.requestMatchers("/**")
								.permitAll()
				)
				.headers(
						headers ->
								headers.frameOptions(
										frameOptions ->
												frameOptions.sameOrigin()
								)
				)
				.csrf(
						csrf ->
								csrf.ignoringRequestMatchers(
										"/h2-console/**"
								)
				)
				.formLogin((formLogin) -> formLogin
						.loginPage("/member/login")
						.defaultSuccessUrl("/"))
				.logout((logout) -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
						.logoutSuccessUrl("/")
						.invalidateHttpSession(true)
				);

		return http.build();
	}
}
