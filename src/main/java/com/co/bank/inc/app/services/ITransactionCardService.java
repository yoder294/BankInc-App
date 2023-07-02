package com.co.bank.inc.app.services;

import java.util.List;

import com.co.bank.inc.app.models.DTOs.CommonBodyDTO;
import com.co.bank.inc.app.models.DTOs.TransactionCardDTO;

public interface ITransactionCardService {

	public TransactionCardDTO findById(Long id);

	public TransactionCardDTO saveTransactionCard(CommonBodyDTO dto);

	public void canceledTransactionCard(Long transactionId);

	public List<TransactionCardDTO> findByCardOrderByCreateAtDesc(String cardId);
}
