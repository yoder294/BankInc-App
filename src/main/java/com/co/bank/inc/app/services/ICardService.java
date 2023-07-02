package com.co.bank.inc.app.services;


import java.math.BigDecimal;

import com.co.bank.inc.app.models.DTOs.CardDTO;
import com.co.bank.inc.app.models.DTOs.CommonBodyDTO;
import com.co.bank.inc.app.models.entitys.Card;

public interface ICardService {

	public CardDTO findById(Long id);
	public Card findByNumberCard(String id);
	public CardDTO saveCard(String productId);
	public void activeStateCard(CommonBodyDTO dto);
	public void rechargeBalance(CommonBodyDTO dto);
	public void subtractBalance(BigDecimal balance, String cardId);
	public CardDTO getBalanceByCradId(String cardId);

}
