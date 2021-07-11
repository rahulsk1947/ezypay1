package com.ezypay;

import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ezypay.entity.Subscription;
import com.ezypay.service.SubcriptionService;

public class SubscriptionWeeklyTest {

	static SubcriptionService subscriptionService;
	static DateTimeFormatter formatter;

	@BeforeClass
	public static void setup() {
		subscriptionService = new SubcriptionService();
		formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	}

	@Test
	/**
	 * Weekly subscription test case. Day of week is SAME as start date weekday.
	 * Example 6th Feb 2018 is a tuesday
	 * Number of subscription invoice dates generated should be FOUR.
	 */
	public void TestWeekly() {
		String start = "06/02/2018";  //this is a tuesday
		String end = "27/02/2018";

		LocalDate startDate = LocalDate.parse(start, formatter);
		LocalDate endDate = LocalDate.parse(end, formatter);

		subscriptionService.createWeeklySubscription(startDate, endDate, 20.5, DayOfWeek.TUESDAY);
		Subscription newsub = subscriptionService.getNewSubscription();
		assertTrue(newsub.getInvoiceDates().size() == 4);

	}
	@Test
	/**
	 * Weekly subscription test case. Day of week is NOT same as start date weekday.
	 * Example 6th Feb 2018 is a tuesday
	 * Number of subscription invoice dates generated should be FOUR.
	 */
	public void TestWeeklyDiffWeekDay() {
		String start = "05/02/2018"; //this is a monday
		String end = "27/02/2018";

		LocalDate startDate = LocalDate.parse(start, formatter);
		LocalDate endDate = LocalDate.parse(end, formatter);

		subscriptionService.createWeeklySubscription(startDate, endDate, 20.5, DayOfWeek.TUESDAY);
		Subscription newsub = subscriptionService.getNewSubscription();
		assertTrue(newsub.getInvoiceDates().size() == 4);
	}
	
	@Test
	/**
	 * Weekly subscription test case.Ensure return price is as inputted
	 */
	public void TestInvoicePrice() {
		String start = "05/02/2018";  
		String end = "27/02/2018";

		LocalDate startDate = LocalDate.parse(start, formatter);
		LocalDate endDate = LocalDate.parse(end, formatter);

		subscriptionService.createWeeklySubscription(startDate, endDate, 20.5, DayOfWeek.TUESDAY);
		Subscription newsub = subscriptionService.getNewSubscription();
		assertTrue(newsub.getPrice() == 20.5);
	}
}
