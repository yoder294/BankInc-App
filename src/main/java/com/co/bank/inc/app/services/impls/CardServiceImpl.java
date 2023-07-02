package com.co.bank.inc.app.services.impls;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.bank.inc.app.exceptions.ResourceBadRequestException;
import com.co.bank.inc.app.exceptions.ResourceBankIncException;
import com.co.bank.inc.app.exceptions.ResourceNotFoundException;
import com.co.bank.inc.app.helpers.CardHelper;
import com.co.bank.inc.app.helpers.CommonBodyDTOHelper;
import com.co.bank.inc.app.models.DTOs.CardDTO;
import com.co.bank.inc.app.models.DTOs.CommonBodyDTO;
import com.co.bank.inc.app.models.entitys.Card;
import com.co.bank.inc.app.repositories.ICardRepository;
import com.co.bank.inc.app.services.ICardService;
import com.co.bank.inc.app.utils.BankIncUtil;
import com.co.bank.inc.app.utils.StateCard;

import jakarta.transaction.Transactional;

@Service
public class CardServiceImpl implements ICardService {

	@Autowired
	private ICardRepository cardRepository;

	@SuppressWarnings("unused")
	private CardHelper cardHelper;
	private CommonBodyDTOHelper coBodyDTOHelper;

	@Override
	public CardDTO findById(Long id) {

		Card entity = cardRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Card with value: " + id));

		CardDTO dto = new CardDTO();
		dto.setId(entity.getId());
		dto.setCardId(entity.getNumberCard());
		dto.setExpiresAt(entity.getExpiresAt());
		dto.setState(StateCard.getEnumByInt(entity.getState()).name());

		return dto;
	}

	@Transactional
	@Override
	public CardDTO saveCard(String productId) {

		coBodyDTOHelper = new CommonBodyDTOHelper(new CommonBodyDTO(productId));

		if (coBodyDTOHelper.validOnlyCard(6, "productId")) {

			Card card = new Card();
			card.setBalance(BigDecimal.ZERO);
			card.setNumberCard(productId.trim().concat(BankIncUtil.generate10RandomNumbers()));
			card.setCreateAt(new Date());
			card.setExpiresAt(BankIncUtil.generateDateExpireCard(3));
			card.setState(StateCard.INACTIVE.getStateCard());

			Card cardNew = cardRepository.save(card);
			return generateCardDTO(cardNew);
		}

		return null;
	}

	private CardDTO generateCardDTO(Card card) {

		CardDTO cardDTO = new CardDTO();
		cardDTO.setCardId(card.getNumberCard());
		cardDTO.setState(StateCard.getEnumByInt(card.getState()).name());
		cardDTO.setExpiresAt(card.getExpiresAt());

		return cardDTO;

	}

	@Transactional
	@Override
	public void activeStateCard(CommonBodyDTO dto) {
		coBodyDTOHelper = new CommonBodyDTOHelper(dto);

		if (coBodyDTOHelper.validOnlyCard(16, "cardId")) {
			try {
				if (findByNumberCard(dto.getCardId()) != null) {
					cardRepository.activeStateCard(dto.getCardId());
				}
			} catch (ResourceBadRequestException e) {
				throw e;
			} catch (Exception e) {
				throw new ResourceBankIncException("Error updated information Card.");
			}
		}
	}

	@Override
	public Card findByNumberCard(String id) {
		Card card = cardRepository.findByNumberCard(id)
				.orElseThrow(() -> new ResourceBadRequestException("Card Id Not found, please enterd Card Id valid."));
		return card;
	}

	@Transactional
	@Override
	public void rechargeBalance(CommonBodyDTO dto) {

		coBodyDTOHelper = new CommonBodyDTOHelper(dto);

		if (coBodyDTOHelper.validCardAndBalance(true)) {
			BigDecimal sum = findByNumberCard(dto.getCardId()).getBalance().add(dto.getBalance());
			try {
				cardRepository.rechargeBalance(sum, dto.getCardId());
			} catch (Exception e) {
				throw new ResourceBankIncException("Error updated information Card.");
			}
		}
	}

	@Override
	public CardDTO getBalanceByCradId(String cardId) {

		coBodyDTOHelper = new CommonBodyDTOHelper(new CommonBodyDTO(cardId));

		if (coBodyDTOHelper.validOnlyCard(16, "cardId")) {
			return new CardDTO(findByNumberCard(cardId).getBalance());
		}

		return null;
	}

	@Override
	public void subtractBalance(BigDecimal balance, String cardId) {

		try {
			cardRepository.rechargeBalance(balance, cardId);
		} catch (Exception e) {
			throw new ResourceBankIncException("Error updated information Card.");
		}
	}

}
