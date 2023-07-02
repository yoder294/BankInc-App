package com.co.bank.inc.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public abstract class BankIncUtil {

	private static final String REGEX_NUMBER_ONLY = "[0-9]+";
	private static final String DATE_FORMAT_CARD_EXPIRE = "MM/yyyy";
	private static final long DAY = 24 * 60 * 60 * 1000;

	public static String generate10RandomNumbers() {
		Long random = (new Random().nextLong(9000000000L) + 1000000000L);
		return Long.toString(random);
	}

	private static boolean isBlankString(String value) {
		return value.isBlank();
	}

	private static boolean isSizeValid(String value, int size) {
		return value.trim().length() == size;
	}

	public static boolean isOnlyNumbers(String value) {
		return value.matches(REGEX_NUMBER_ONLY);
	}

	public static boolean isValidCard(String value, int size) {
		if (!isBlankString(value)) {
			return (isSizeValid(value, size) && isOnlyNumbers(value));
		}
		return false;
	}

	public static boolean isValidCardExpire(String value) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_CARD_EXPIRE);
		simpleDateFormat.setLenient(false);

		try {
			Date expiry = simpleDateFormat.parse(value);
			return expiry.before(new Date());
		} catch (ParseException e) {
			return true;
		}

	}

	public static LocalDate addYearsCurrentDate(int years) {
		LocalDate date = LocalDate.now();
		date = date.plusYears(years);
		return date;
	}

	public static String formmatLocalDate(LocalDate localDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_CARD_EXPIRE);
		return localDate.format(formatter);
	}

	public static String generateDateExpireCard(int years) {
		LocalDate date = addYearsCurrentDate(years);
		return formmatLocalDate(date);
	}

	public static String formatDateDMYWithHour(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return sdf.format(date);
	}

	public static boolean isRange24Hour(Date date) {
		return date.getTime() > System.currentTimeMillis() - DAY;
	}

}
