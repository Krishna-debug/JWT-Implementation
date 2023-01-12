package com.krishna.security;

import lombok.Data;

@Data
public class JWTAuthRequest {

	private String userName;
	
	private String password;
	
}
