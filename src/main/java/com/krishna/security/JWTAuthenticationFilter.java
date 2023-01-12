package com.krishna.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.krishna.sevice.CutomUserDetailService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private CutomUserDetailService cutomUserDetailService;

	@Autowired
	private JWTTokenHelper jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestToken = request.getHeader("Authorization");

		String userName = null;
		String jwtToken = null;

		if (requestToken != null && requestToken.startsWith("Bearer ")) {
			jwtToken = requestToken.substring(7);

			try {
				userName = jwtUtil.extractUsername(jwtToken);

			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token expired");
			}

			// Security Token get Already
			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetail = cutomUserDetailService.loadUserByUsername(userName);

				if (jwtUtil.validateToken(jwtToken, userDetail)) {
					UsernamePasswordAuthenticationToken pass = new UsernamePasswordAuthenticationToken(jwtToken, null,
							userDetail.getAuthorities());

					pass.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(pass);
				} else {
					System.out.println("Invalid JWT Token");
				}

			} else {
				System.out.println("UserName is null");
			}

		} else {
			System.out.println("JWT Token does not start with Barer");
		}

		filterChain.doFilter(request, response);

	}

}
