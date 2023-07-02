package com.co.bank.inc.app.helpers;

import com.co.bank.inc.app.exceptions.ResourceBadRequestException;
import com.co.bank.inc.app.models.DTOs.TransactionCardDTO;

public class TransactionCardDtoHelper {

	private TransactionCardDTO dto;

	public TransactionCardDtoHelper(TransactionCardDTO dto) {
		this.dto = dto;
		isBodyNull();
	}

	public void isBodyNull() {
		if (dto == null) {
			throw new ResourceBadRequestException("Body is required.");
		}
	}
	
	
	
}
