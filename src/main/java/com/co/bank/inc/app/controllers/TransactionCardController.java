package com.co.bank.inc.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.bank.inc.app.exceptions.BankIncSuccessDTO;
import com.co.bank.inc.app.models.DTOs.CommonBodyDTO;
import com.co.bank.inc.app.models.DTOs.TransactionCardDTO;
import com.co.bank.inc.app.services.ITransactionCardService;

@RestController
@RequestMapping("/transaction")
public class TransactionCardController {

	@Autowired
	private ITransactionCardService transactionCardService;

	@GetMapping("/{transactionId}")
	private ResponseEntity<?> getTransactionCardById(
			@PathVariable(required = true, name = "transactionId") Long transactionId) {

		TransactionCardDTO dto = transactionCardService.findById(transactionId);

		BankIncSuccessDTO<TransactionCardDTO> response = new BankIncSuccessDTO<>(dto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/purchase")
	private ResponseEntity<?> saveTransactionCard(@RequestBody CommonBodyDTO dto) {

		TransactionCardDTO traCard = transactionCardService.saveTransactionCard(dto);

		BankIncSuccessDTO<TransactionCardDTO> response = new BankIncSuccessDTO<>(traCard);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/canceled/{transactionId}")
	private ResponseEntity<?> canceledTransactionCard(
			@PathVariable(required = true, name = "transactionId") Long transactionId) {

		transactionCardService.canceledTransactionCard(transactionId);

		BankIncSuccessDTO<Object> response = new BankIncSuccessDTO<Object>(null, "Transaction canceled success.");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}


	@GetMapping("/{cardId}/card")
	private ResponseEntity<?> getAllTransactionByCard(@PathVariable(required = true, name = "cardId") String cardId) {

		List<TransactionCardDTO> dtos = transactionCardService.findByCardOrderByCreateAtDesc(cardId);

		BankIncSuccessDTO<List<TransactionCardDTO>> response = new BankIncSuccessDTO<>(dtos);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
