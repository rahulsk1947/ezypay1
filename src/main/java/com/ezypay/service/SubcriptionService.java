package com.ezypay.service;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONObject;

import com.ezypay.entity.Subscription;
import com.ezypay.entity.SubscriptionType;
import com.fasterxml.jackson.core.JsonProcessingException;

public class SubcriptionService {

	Subscription subcription;

	public void createDailySubscription(LocalDate startDate, LocalDate endDate, double amount) {
		Subscription newsubcription = new Subscription(startDate, endDate, amount, SubscriptionType.DAILY);
		calculateDailyDates(newsubcription);
		subcription = newsubcription;
	}

	public void createWeeklySubscription(LocalDate startDate, LocalDate endDate, double amount, DayOfWeek weekday) {
		Subscription newsubcription = new Subscription(startDate, endDate, amount, SubscriptionType.WEEKLY, weekday);
		calculateWeeklyDates(newsubcription);
		subcription = newsubcription;
	}

	public void createMonthlySubscription(LocalDate startDate, LocalDate endDate, double amount, int dayofmonth) {
		Subscription newsubcription = new Subscription(startDate, endDate, amount, SubscriptionType.MONTHLY,
				dayofmonth);
		calculateMonthlyDates(newsubcription);
		subcription = newsubcription;
	}

	/**
	 * Process and calculate daily invoice dates.
	 */
	public void calculateDailyDates(Subscription sub) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate currrentDate = sub.getStartDate();
		LocalDate endDate = sub.getEndDate();

		/* calculate dates */
		while (currrentDate.compareTo(endDate) <= 0) {
			sub.addInvoiceDate(formatter.format(currrentDate));
			currrentDate = currrentDate.plusDays(1);
		}

	}

	/**
	 * Process and calculate weekly invoice dates.
	 */
	public void calculateWeeklyDates(Subscription sub) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate currrentDate = sub.getStartDate();
		LocalDate endDate = sub.getEndDate();

		/* adjust current date to subscription date */
		while (currrentDate.getDayOfWeek().getValue() != sub.getDayofweekly().getValue()) {
			currrentDate = currrentDate.plusDays(1);
		}

		/* calculate dates */
		while (currrentDate.compareTo(endDate) <= 0) {
			sub.addInvoiceDate(formatter.format(currrentDate));
			currrentDate = currrentDate.plusDays(7);
		}
	}

	/**
	 * Process and calculate monthly invoice dates.
	 */
	public void calculateMonthlyDates(Subscription sub) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate currrentDate = sub.getStartDate();
		LocalDate endDate = sub.getEndDate();

		/* adjust current date to subscription date */
		while (currrentDate.getDayOfMonth() != sub.getDateOfMonth()) {
			currrentDate = currrentDate.plusDays(1);
		}

		/* calculate dates */
		while (currrentDate.compareTo(endDate) <= 0) {
			sub.addInvoiceDate(formatter.format(currrentDate));
			currrentDate = currrentDate.plusMonths(1);
		}
	}

	/**
	 * Validate to ensure start to end date is THREE months only.
	 * 
	 * @param start Start date
	 * @param end   End date
	 * @return True is dates are within 3 months, false if more than 3 months
	 */
	public Boolean validateDates(LocalDate start, LocalDate end) {
		start = start.plusMonths(3);
		if (end.compareTo(start) <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Validation method: check if correct day of week. Can be between 1 to 7 only.
	 * @return DayOfWeek object for day of week. mon - > sun
	 */
	public DayOfWeek validateDayofTheWeek(Integer day) {
		DayOfWeek dayOfWeek;

		if (day == null) {
			return null;
		} else {
			switch (day) {
			case 1:
				dayOfWeek = DayOfWeek.MONDAY;
				break;
			case 2:
				dayOfWeek = DayOfWeek.TUESDAY;
				break;
			case 3:
				dayOfWeek = DayOfWeek.WEDNESDAY;
				break;
			case 4:
				dayOfWeek = DayOfWeek.THURSDAY;
				break;
			case 5:
				dayOfWeek = DayOfWeek.FRIDAY;
				break;
			case 6:
				dayOfWeek = DayOfWeek.SATURDAY;
				break;
			case 7:
				dayOfWeek = DayOfWeek.SUNDAY;
				break;
			default:
				dayOfWeek = null;
				break;
			}
		}
		return dayOfWeek;
	}
	
	/**
	 * 
	 * @return JSONObject with invoice dates and details.
	 */
	public JSONObject createResponseJson() {

		JSONObject json = new JSONObject();
		json.put("amount", Double.toString(subcription.getPrice()));
		json.put("SubscriptionType", subcription.getSubscriptionType());
		json.put("InvoiceDates", subcription.getInvoiceDates().toString());
		return json;
	}

	public Subscription getNewSubscription() {
		return subcription;
	}
}
