package com.co.bank.inc.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<BankIncErrorResponse> resourceNotFoundException(ResourceNotFoundException ex,
			WebRequest request) {

		BankIncErrorResponse message = new BankIncErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<BankIncErrorResponse>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceBadRequestException.class)
	public ResponseEntity<BankIncErrorResponse> resourceBadRequestException(ResourceBadRequestException ex,
			WebRequest request) {

		BankIncErrorResponse message = new BankIncErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<BankIncErrorResponse>(message, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceBankIncException.class)
	public ResponseEntity<BankIncErrorResponse> resourceBankIncException(ResourceBankIncException ex, WebRequest request) {
		BankIncErrorResponse message = new BankIncErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<BankIncErrorResponse>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<BankIncErrorResponse> globalExceptionHandler(Exception ex, WebRequest request) {
		BankIncErrorResponse message = new BankIncErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<BankIncErrorResponse>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
