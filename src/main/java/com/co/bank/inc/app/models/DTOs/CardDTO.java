package com.co.bank.inc.app.models.DTOs;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDTO {

	private Long id;
	private String cardId;
	private String state;
	private String createAt;
	private BigDecimal balance;
	private String expiresAt;
	private String lockedAt;

	public CardDTO() {
	}

	public CardDTO(String cardId) {
		this.cardId = cardId;
	}

	public CardDTO(BigDecimal balance) {
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(String expiresAt) {
		this.expiresAt = expiresAt;
	}

	public String getLockedAt() {
		return lockedAt;
	}

	public void setLockedAt(String lockedAt) {
		this.lockedAt = lockedAt;
	}

}
