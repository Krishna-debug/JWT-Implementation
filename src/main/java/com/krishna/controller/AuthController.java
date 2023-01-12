package com.krishna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.krishna.global.exception.ApiException;
import com.krishna.security.JWTAuthRequest;
import com.krishna.security.JWTAuthResponse;
import com.krishna.security.JWTTokenHelper;

@RestController
public class AuthController {

	@Autowired
	private JWTTokenHelper jWTTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JWTAuthRequest request) throws Exception {

		// System.out.println(passwordEncoder.encode("qwe"));

		authenticate(request.getUserName(), request.getPassword());

		UserDetails userDetail = userDetailsService.loadUserByUsername(request.getUserName());
		String token = jWTTokenHelper.generateToken(userDetail);

		JWTAuthResponse response = new JWTAuthResponse();
		response.setToken(token);

		return new ResponseEntity<JWTAuthResponse>(response, HttpStatus.OK);

	}

	private void authenticate(String userName, String password) throws Exception {

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);

		try {
			authenticationManager.authenticate(token);

		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details  !!");
			throw new ApiException("Invalid Password !! ");
		}
	}

}
