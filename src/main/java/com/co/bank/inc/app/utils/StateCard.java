package com.co.bank.inc.app.utils;

import java.util.*;

public enum StateCard {

	INACTIVE(0), ACTIVE(1), LOCKED(2);

	private int stateCard;

	private StateCard(int stateCard) {
		this.stateCard = stateCard;
	}

	public int getStateCard() {
		return stateCard;
	}

	public static StateCard getEnumByInt(int code) {

		List<StateCard> stateList = Arrays.asList(StateCard.values());
		StateCard statecard = stateList.stream().filter(state -> state.stateCard == code).findAny().orElse(null);
		return statecard;
	}

}
