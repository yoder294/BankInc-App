package com.co.bank.inc.app.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.co.bank.inc.app.models.entitys.TransactionCard;

public interface ITransactionCardRepository extends CrudRepository<TransactionCard, Long> {

	public Optional<TransactionCard> findById(Long id);

}
