package com.krishna.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.krishna.security.JWTAuthenticationEntryPoint;
import com.krishna.security.JWTAuthenticationFilter;
import com.krishna.sevice.CutomUserDetailService;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CutomUserDetailService cutomUserDetailService;

	@Autowired
	JWTAuthenticationEntryPoint jWTAuthenticationEntryPoint;

	@Autowired
	JWTAuthenticationFilter jWTAuthenticationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers("/login")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(jWTAuthenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jWTAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(cutomUserDetailService).passwordEncoder(paswwordEncode());

	}

	@Bean
	public PasswordEncoder paswwordEncode() {
		
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {

		return super.authenticationManagerBean();
	}

}
