package com.co.bank.inc.app.helpers;

import java.math.BigDecimal;

import com.co.bank.inc.app.exceptions.ResourceBadRequestException;
import com.co.bank.inc.app.models.DTOs.CommonBodyDTO;
import com.co.bank.inc.app.utils.BankIncUtil;

public class CommonBodyDTOHelper {

	private CommonBodyDTO dto;

	public CommonBodyDTOHelper(CommonBodyDTO dto) {
		this.dto = dto;
		isNotNullBody();
	}

	private void isNotNullBody() {
		if (dto == null) {
			throw new ResourceBadRequestException("Body can't be null");
		}
	}

	private boolean isValidCardNumber(int size, String propertie) {

		if (dto.getCardId() == null || dto.getCardId().isBlank()) {
			throw new ResourceBadRequestException(String.format("%s id, is requerid.", propertie));
		}

		if (!BankIncUtil.isValidCard(dto.getCardId(), size)) {
			throw new ResourceBadRequestException(
					String.format("%s, must have %d digits and only numbers.", propertie, size));
		}

		return true;
	}

	private boolean isValidBalance(BigDecimal balance, String propertie) {

		if (balance == null) {
			throw new ResourceBadRequestException(String.format("%s is required.", propertie));
		}

		if (!BankIncUtil.isOnlyNumbers(balance.toString())) {
			throw new ResourceBadRequestException(String.format("%s only accept numbers.", propertie));
		}

		if (!(balance.longValue() > 0)) {
			throw new ResourceBadRequestException(String.format("%s only accept numbers > 0.", propertie));
		}

		return true;
	}

	public boolean validOnlyCard(int size, String namePropertie) {
		return isValidCardNumber(size, namePropertie);
	}

	public boolean validCardAndBalance(boolean isBalance) {
		BigDecimal valueMoney = isBalance ? dto.getBalance() : dto.getPrice();
		String propertieBalance = isBalance ? "balance" : "price";
		return validOnlyCard(16, "cardId") && isValidBalance(valueMoney, propertieBalance);
	}

	public CommonBodyDTO getDto() {
		return dto;
	}

}
