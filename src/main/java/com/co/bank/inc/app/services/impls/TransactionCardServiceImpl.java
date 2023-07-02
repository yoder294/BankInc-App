package com.co.bank.inc.app.services.impls;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.bank.inc.app.exceptions.ResourceBankIncException;
import com.co.bank.inc.app.exceptions.ResourceNotFoundException;
import com.co.bank.inc.app.helpers.CardHelper;
import com.co.bank.inc.app.helpers.CommonBodyDTOHelper;
import com.co.bank.inc.app.models.DTOs.CommonBodyDTO;
import com.co.bank.inc.app.models.DTOs.TransactionCardDTO;
import com.co.bank.inc.app.models.entitys.Card;
import com.co.bank.inc.app.models.entitys.TransactionCard;
import com.co.bank.inc.app.repositories.ITransactionCardRepository;
import com.co.bank.inc.app.services.ICardService;
import com.co.bank.inc.app.services.ITransactionCardService;
import com.co.bank.inc.app.utils.BankIncUtil;
import com.co.bank.inc.app.utils.StateTransactionCard;

import jakarta.transaction.Transactional;

@Service
public class TransactionCardServiceImpl implements ITransactionCardService {

	@Autowired
	private ITransactionCardRepository transactionCardRepository;
	@Autowired
	private ICardService iCardService;

	private CardHelper cardHelper;
	private CommonBodyDTOHelper bodyDTOHelper;

	@Override
	public TransactionCardDTO findById(Long id) {

		TransactionCard traCard = getTransactionCard(id);
		return genertedTransactionCardDTO(traCard);
	}

	private TransactionCard getTransactionCard(Long transactionId) {

		return transactionCardRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Transaction id: " + transactionId));
	}

	@Transactional
	@Override
	public TransactionCardDTO saveTransactionCard(CommonBodyDTO dto) {

		bodyDTOHelper = new CommonBodyDTOHelper(dto);

		if (bodyDTOHelper.validCardAndBalance(false)) { // Validate Body

			Card card = iCardService.findByNumberCard(dto.getCardId());
			cardHelper = new CardHelper(card);

			if (cardHelper.isValidCard(dto.getPrice())) { // Validate card is valid

				try {

					card.setBalance(card.getBalance().subtract(dto.getPrice()));

					TransactionCard transactionCard = new TransactionCard();
					transactionCard.setBalance(dto.getPrice());
					transactionCard.setCard(card);
					transactionCard.setState(StateTransactionCard.SUCCESS.getStateTransaction());
					TransactionCard traCard = transactionCardRepository.save(transactionCard);

					return genertedTransactionCardDTO(traCard);

				} catch (Exception e) {
					System.err.println(e.getMessage());
					throw new ResourceBankIncException("Error generated transaction.");
				}
			}

		}

		return null;
	}

	@Transactional
	@Override
	public void canceledTransactionCard(Long transactionId) {

		TransactionCard transactionCard = getTransactionCard(transactionId);

		if (!BankIncUtil.isRange24Hour(transactionCard.getCreateAt())) {
			throw new ResourceBankIncException("Cancellation is not possible and 24 hours have elapsed.");
		}

		transactionCard.setCanceledAt(new Date());
		transactionCard.setState(StateTransactionCard.CANCELED.getStateTransaction());
		Card card = transactionCard.getCard();
		card.setBalance(card.getBalance().add(transactionCard.getBalance()));
		transactionCard.setCard(card);

		try {
			transactionCardRepository.save(transactionCard);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new ResourceBankIncException("Error canceled transaction.");
		}
	}

	@Override
	public List<TransactionCardDTO> findByCardOrderByCreateAtDesc(String cardId) {

		bodyDTOHelper = new CommonBodyDTOHelper(new CommonBodyDTO(cardId));

		if (bodyDTOHelper.validOnlyCard(16, "cardId")) {

			Card card = iCardService.findByNumberCard(cardId);

			if (card.getTransactions() != null && card.getTransactions().size() > 0) {

				return sortTransactions(card.getTransactions());
			}

			return new LinkedList<>();
		}

		return new LinkedList<>();
	}

	// ---- MAP DTO  ---

	public List<TransactionCardDTO> sortTransactions(List<TransactionCard> transactions) {

		List<TransactionCardDTO> dtos = transactions.stream().map(a -> genertedTransactionCardDTO(a))
				.collect(Collectors.toList());

		Collections.sort(dtos, (a, b) -> {
			return b.getCreateAt().compareTo(a.getCreateAt());
		});

		return dtos;
	}

	public TransactionCardDTO genertedTransactionCardDTO(TransactionCard traCard) {

		TransactionCardDTO dto = new TransactionCardDTO();

		dto.setTransactionId(traCard.getId());
		dto.setCardId(traCard.getCard().getNumberCard());
		dto.setPrice(traCard.getBalance());
		StateTransactionCard state = StateTransactionCard.getEnumByInt(traCard.getState());
		dto.setState(state.name());
		dto.setCreateAt(BankIncUtil.formatDateDMYWithHour(traCard.getCreateAt()));

		if (StateTransactionCard.CANCELED.name().equals(state.name())) {
			dto.setCanceledAt(BankIncUtil.formatDateDMYWithHour(traCard.getCanceledAt()));
		}

		return dto;
	}

}
