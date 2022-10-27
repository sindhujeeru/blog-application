package com.sindhu.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.sindhu.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String msg = ex.getMessage();
		ApiResponse apiRes = new ApiResponse(msg,false);
		
		return new ResponseEntity<ApiResponse>(apiRes,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
		
		Map<String, String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			
			String fieldName = ((FieldError)error).getField();
			String message  = error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String, String>>(resp,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
		//String msg = ex.getLocalizedMessage();
		String msg = ex.getName()+", the pathVariable in API should be of type "+ ex.getRequiredType();
		ApiResponse res = new ApiResponse(msg,false);
		return new ResponseEntity<ApiResponse>(res,HttpStatus.BAD_REQUEST);
	}
	
//	@ExceptionHandler(BadCredentialsException.class)
//	public String handleException(BadCredentialsException ex) {
//		return "Invalid Credentials";
//	}
}
