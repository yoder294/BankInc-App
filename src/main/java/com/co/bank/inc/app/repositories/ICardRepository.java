package com.co.bank.inc.app.repositories;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.co.bank.inc.app.models.entitys.Card;

public interface ICardRepository extends CrudRepository<Card, Long> {

	public Optional<Card> findById(Long id);

	public Optional<Card> findByNumberCard(String id);

	@Modifying
	@Query("UPDATE Card SET state=1 WHERE numberCard=:numberCard")
	void activeStateCard(String numberCard);

	@Modifying
	@Query("UPDATE Card SET balance=:balance WHERE numberCard=:numberCard")
	void rechargeBalance(BigDecimal balance, String numberCard);
}
