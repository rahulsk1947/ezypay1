package com.ezypay;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ezypay.entity.Subscription;
import com.ezypay.service.SubcriptionService;

public class SubscriptionDailyTest {

	static SubcriptionService subscriptionService;
	static DateTimeFormatter formatter;

	@BeforeClass
	public static void setup() {
		subscriptionService = new SubcriptionService();
		formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	}

	@Test
	/**
	 * Daily subscription test case
	 * 4th June to 13thJune 
	 * Number of subscription invoice dates should be 10
	 */
	public void TestDaily() {
		String start = "04/06/2018";   
		String end =   "13/06/2018";

		LocalDate startDate = LocalDate.parse(start, formatter);
		LocalDate endDate = LocalDate.parse(end, formatter);

		subscriptionService.createDailySubscription(startDate, endDate, 20.5);
		Subscription newsub = subscriptionService.getNewSubscription();
		assertTrue(newsub.getInvoiceDates().size() == 10);
	}
	
	@Test
	/**
	 * Daily subscription test case.Ensure return price is as inputted
	 */
	public void TestInvoicePrice() {
		String start = "04/06/2018";   
		String end =   "30/08/2018";

		LocalDate startDate = LocalDate.parse(start, formatter);
		LocalDate endDate = LocalDate.parse(end, formatter);

		subscriptionService.createDailySubscription(startDate, endDate, 20.5);
		Subscription newsub = subscriptionService.getNewSubscription();
		assertTrue(newsub.getPrice() == 20.5);
	}	
}
