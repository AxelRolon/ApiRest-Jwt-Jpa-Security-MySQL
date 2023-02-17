package com.sistema.blog.excepciones;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.sistema.blog.dto.ErrorClases;

@ControllerAdvice // permite manejar excepciones Handler
public class GlobalExceptionHandler {

	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorClases> manejarResourceNotFoundEXCEPTION(ResourceNotFoundException exception, WebRequest webRequest){
		
		ErrorClases errorClases = new ErrorClases(new Date(), exception.getMessage(), webRequest.getDescription(false));
		
		return new ResponseEntity<>(errorClases, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(BlogAppException.class)
	public ResponseEntity<ErrorClases> manejarBlogAppException(BlogAppException exception, WebRequest webRequest){
		
		ErrorClases errorClases = new ErrorClases(new Date(), exception.getMessage(), webRequest.getDescription(false));
		
		return new ResponseEntity<>(errorClases, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorClases> manejarGlobalException(Exception exception, WebRequest webRequest){
		
		ErrorClases errorClases = new ErrorClases(new Date(), exception.getMessage(), webRequest.getDescription(false));
		
		return new ResponseEntity<>(errorClases, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}
