package com.sistema.blog.excepciones;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sistema.blog.dto.ErrorClases;

@ControllerAdvice // permite manejar excepciones Handler
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorClases> manejarResourceNotFoundEXCEPTION(ResourceNotFoundException exception,
			WebRequest webRequest) {

		ErrorClases errorClases = new ErrorClases(new Date(), exception.getMessage(), webRequest.getDescription(false));

		return new ResponseEntity<>(errorClases, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(BlogAppException.class)
	public ResponseEntity<ErrorClases> manejarBlogAppException(BlogAppException exception, WebRequest webRequest) {

		ErrorClases errorClases = new ErrorClases(new Date(), exception.getMessage(), webRequest.getDescription(false));

		return new ResponseEntity<>(errorClases, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorClases> manejarGlobalException(Exception exception, WebRequest webRequest) {

		ErrorClases errorClases = new ErrorClases(new Date(), exception.getMessage(), webRequest.getDescription(false));

		return new ResponseEntity<>(errorClases, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> errores = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String nombreCampo = ((FieldError) error).getField();
			String mensaje = error.getDefaultMessage();

			errores.put(nombreCampo, mensaje);

		});

		return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
	}
}
