package com.co.bank.inc.app.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankIncErrorResponse {
	
	  private boolean success;
	  private int statusCode;
	  private String message;
	  @JsonInclude(JsonInclude.Include.NON_NULL)
	  private String description;
	  
	 public BankIncErrorResponse(int statusCode, String message, String description) {
		    this.success = false; 
		    this.statusCode = statusCode;
		    this.message = message;
		    this.description = description;
	}
	  
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	  

}
