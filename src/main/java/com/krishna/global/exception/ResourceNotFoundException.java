package com.krishna.global.exception;

public class ResourceNotFoundException extends RuntimeException {

	String resourceName;
	String fieldName;
	long value;

//	public ResourceNotFoundException(String resourceName, String fieldName, long value) {
//		super(String.format("%s not found %s : %s", resourceName, fieldName, value));
//
//		this.resourceName = resourceName;
//		this.fieldName = fieldName;
//		this.value = value;
//
//	}

	public ResourceNotFoundException(String resourceName) {
		super(resourceName);
		this.resourceName = resourceName;
	}

}
