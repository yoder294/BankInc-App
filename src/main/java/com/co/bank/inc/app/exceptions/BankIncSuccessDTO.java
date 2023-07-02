package com.co.bank.inc.app.exceptions;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankIncSuccessDTO<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;
	private boolean success = true;
	@JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer length = null;
    private String message = null;
    
    
    @SuppressWarnings("rawtypes")
	public BankIncSuccessDTO(T data, int length, String message) {
    	this.success = true;
        this.data = data;
        this.length = length;
        this.message = message;
        if (length == 0) {
            if (this.data instanceof List) {
                this.length = ( (List) this.data).size();
            }

            if (this.data instanceof Map) {
                this.length = ((Map) this.data).size();
            }
        }
    }
    
    @SuppressWarnings("rawtypes")
	public BankIncSuccessDTO(T data, String message) {
    	this.success = true;
        this.data = data;
        this.message = message;

        if (this.data instanceof List) {
            this.length = ((List) this.data).size();
        }

        if (this.data instanceof Map) {
            this.length = ((Map) this.data).size();
        }
    }

    public BankIncSuccessDTO(T data, Integer length) {
    	this.success = true;
        this.data = data;
        this.length = length;
    }

	public BankIncSuccessDTO(T data) {
    	this.success = true;
        this.data = data;
    }

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

}
