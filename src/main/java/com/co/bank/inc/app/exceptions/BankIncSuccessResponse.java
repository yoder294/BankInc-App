package com.co.bank.inc.app.exceptions;

public class BankIncSuccessResponse<T> {
	
	private BankIncSuccessDTO<T> response;

    public BankIncSuccessDTO<T> getResponse() {
        return response;
    }

    public void setResponse(BankIncSuccessDTO<T> response) {
        this.response = response;
    }

    public BankIncSuccessResponse(T object) {
        this.response = new BankIncSuccessDTO<>(object);
    }

    public BankIncSuccessResponse(T object, String message) {
        this.response = new BankIncSuccessDTO<>(object, message);
    }

    public BankIncSuccessResponse(T object, Integer length, String message) {
        this.response = new BankIncSuccessDTO<>(object, length, message);
    }

    public BankIncSuccessResponse(T object, Integer length) {
        this.response = new BankIncSuccessDTO<>(object, length);
    }

}
