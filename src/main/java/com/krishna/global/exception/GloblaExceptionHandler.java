package com.krishna.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.krishna.util.ApiResponse;
import com.krishna.util.ResponseHandler;

@RestControllerAdvice
public class GloblaExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException ex) {

		String message = ex.getMessage();

		return ResponseHandler.errorResponse(message, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> apiException(ApiException ex) {

		String message = ex.getMessage();
		ApiResponse apiRespose = new ApiResponse(message, true);

		return new ResponseEntity<ApiResponse>(apiRespose, HttpStatus.BAD_REQUEST);

	}

}
