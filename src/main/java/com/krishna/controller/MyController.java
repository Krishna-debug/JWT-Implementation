package com.krishna.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

	@GetMapping("/get")
	private String get() {
		
		return "Hello World";
	}

}
