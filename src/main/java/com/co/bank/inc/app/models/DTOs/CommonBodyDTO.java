package com.co.bank.inc.app.models.DTOs;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonBodyDTO {

	private String cardId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigDecimal balance;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigDecimal price;

	public CommonBodyDTO() {
	}

	public CommonBodyDTO(String cardId) {
		this.cardId = cardId;
	}

	public CommonBodyDTO(String cardId, BigDecimal balance) {
		this.cardId = cardId;
		this.balance = balance;
	}

	public CommonBodyDTO(BigDecimal price, String cardId) {
		this.cardId = cardId;
		this.price = price;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
