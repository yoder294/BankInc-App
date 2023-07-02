package com.co.bank.inc.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.bank.inc.app.exceptions.BankIncSuccessDTO;
import com.co.bank.inc.app.exceptions.ResourceBankIncException;
import com.co.bank.inc.app.models.DTOs.CardDTO;
import com.co.bank.inc.app.models.DTOs.CommonBodyDTO;
import com.co.bank.inc.app.services.ICardService;

@RestController
@RequestMapping("/card")
public class CardController {

	@Autowired
	private ICardService cardService;

	@GetMapping("/{productId}/number")
	private ResponseEntity<?> generatedCard(@PathVariable(required = true, name = "productId") String productId) {

		CardDTO cardDto = cardService.saveCard(productId);
		if (cardDto == null) {
			throw new ResourceBankIncException("Error persist entity.");
		}

		BankIncSuccessDTO<CardDTO> response = new BankIncSuccessDTO<>(cardDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/{cardId}/id")
	private ResponseEntity<?> seacrhCardById(@PathVariable(required = true, name = "cardId") Long cardId) {

		CardDTO cardDTO = cardService.findById(cardId);
		BankIncSuccessDTO<CardDTO> response = new BankIncSuccessDTO<>(cardDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/enroll")
	private ResponseEntity<?> enrollCrad(@RequestBody(required = true) CommonBodyDTO dto) {

		cardService.activeStateCard(dto);

		BankIncSuccessDTO<Object> response = new BankIncSuccessDTO<Object>(null, "Enroll card success.");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/balance")
	private ResponseEntity<?> upBalanceCrad(@RequestBody(required = true) CommonBodyDTO dto) {

		cardService.rechargeBalance(dto);

		BankIncSuccessDTO<Object> response = new BankIncSuccessDTO<Object>(null, "Balance update success.");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/balance/{cardId}")
	private ResponseEntity<?> seacrhCardById(@PathVariable(required = true, name = "cardId") String cardId) {

		CardDTO cardDTO = cardService.getBalanceByCradId(cardId);

		BankIncSuccessDTO<CardDTO> response = new BankIncSuccessDTO<>(cardDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
