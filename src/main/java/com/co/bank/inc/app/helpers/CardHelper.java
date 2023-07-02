package com.co.bank.inc.app.helpers;

import java.math.BigDecimal;

import com.co.bank.inc.app.exceptions.ResourceBadRequestException;
import com.co.bank.inc.app.exceptions.ResourceNotFoundException;
import com.co.bank.inc.app.models.entitys.Card;
import com.co.bank.inc.app.utils.BankIncUtil;
import com.co.bank.inc.app.utils.StateCard;

public class CardHelper {

	private Card cardEntity;

	public CardHelper(Card cardEntity) {
		this.cardEntity = cardEntity;
		isCardNull();
	}

	private void isCardNull() {

		if (cardEntity == null) {
			throw new ResourceNotFoundException("Not Found Card.");
		}
	}

	public boolean validateStateCard(int state) {
		return StateCard.getEnumByInt(cardEntity.getState()).name().equals(StateCard.getEnumByInt(state).name());
	}

	public boolean isStateCardActive() {

		if (!validateStateCard(StateCard.ACTIVE.getStateCard())) {
			throw new ResourceBadRequestException(
					"State your card is not valid: ".concat(StateCard.getEnumByInt(cardEntity.getState()).name()));
		}

		return true;
	}

	public boolean cardHaveBalance() {
		if (!(cardEntity.getBalance().longValue() > 0)) {
			throw new ResourceBadRequestException("Card Balance, you must recharge the balance must be > 0.");
		}
		return true;
	}

	public boolean isCardVigent() {

		if (BankIncUtil.isValidCardExpire(cardEntity.getExpiresAt())) {
			throw new ResourceBadRequestException("Card not valid, it's expires.");
		}
		return true;
	}

	public boolean balanceValidBuy(BigDecimal price) {

		BigDecimal operation = cardEntity.getBalance().subtract(price);

		if (operation.longValue() < 0) {
			throw new ResourceBadRequestException("Invalid purchase, insufficient card balance.");
		}

		return true;
	}

	public boolean isValidCard(BigDecimal price) {
		return (isStateCardActive() && isCardVigent() && cardHaveBalance() && balanceValidBuy(price));
	}

	public Card getCardEntity() {
		return cardEntity;
	}

}
