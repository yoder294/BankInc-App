package com.co.bank.inc.app.utils;

import java.util.Arrays;
import java.util.List;

public enum StateTransactionCard {

	SUCCESS(0), REJECTED(1), CANCELED(2);

	private int stateTransaction;

	private StateTransactionCard(int stateTransaction) {
		this.stateTransaction = stateTransaction;
	}

	public int getStateTransaction() {
		return stateTransaction;
	}

	public static StateTransactionCard getEnumByInt(int code) {

		List<StateTransactionCard> stateList = Arrays.asList(StateTransactionCard.values());
		StateTransactionCard stateTransaction = stateList.stream().filter(state -> state.stateTransaction == code)
				.findAny().orElse(null);
		return stateTransaction;
	}

}
