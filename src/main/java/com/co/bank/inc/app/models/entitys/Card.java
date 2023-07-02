package com.co.bank.inc.app.models.entitys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cards", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "number_card" }, name = "UQ_NumberCard") })
public class Card implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(name = "number_card", length = 16)
	private String numberCard;

	@NotNull
	@Column(name = "state", nullable = false, length = 1)
	private int state;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at", nullable = false)
	private Date createAt;

	@NotNull
	@Column(name = "balance", nullable = false)
	private BigDecimal balance;

	@NotNull
	@Column(name = "expires_at", nullable = false)
	private String expiresAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "locked_at")
	private Date lockedAt;

	@OneToMany(mappedBy = "card", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TransactionCard> transactions;

	public Card(Long id) {
		this.id = id;
	}

	public Card() {
		transactions = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumberCard() {
		return numberCard;
	}

	public void setNumberCard(String numberCard) {
		this.numberCard = numberCard;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
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

	public Date getLockedAt() {
		return lockedAt;
	}

	public void setLockedAt(Date lockedAt) {
		this.lockedAt = lockedAt;
	}

	public List<TransactionCard> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionCard> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "{ id:" + id + ", numberCard:" + numberCard + ", state:" + state + ", createAt:" + createAt
				+ ", balance:" + balance + ", expiresAt:" + expiresAt + ", lockedAt:" + lockedAt + "}";
	}

}
