package com.ezypay;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ezypay.entity.Subscription;
import com.ezypay.service.SubcriptionService;

public class SubscriptionMonthlyTest {

	static SubcriptionService subscriptionService;
	static DateTimeFormatter formatter;

	@BeforeClass
	public static void setup() {
		subscriptionService = new SubcriptionService();
		formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	}

	@Test
	/**
	 * Monthly subscription test case
	 * 4th June to 30th August 
	 * Number of subscription invoice dates should be 3
	 */
	public void TestMonthly() {
		String start = "04/06/2018";   
		String end =   "30/08/2018";

		LocalDate startDate = LocalDate.parse(start, formatter);
		LocalDate endDate = LocalDate.parse(end, formatter);

		subscriptionService.createMonthlySubscription(startDate, endDate, 20.5, 5);
		Subscription newsub = subscriptionService.getNewSubscription();
		assertTrue(newsub.getInvoiceDates().size() == 3);
		
		//ensure these dates are generated as expected
		assertTrue((newsub.getInvoiceDates().toString()).contains("05/06/2018"));
		assertTrue((newsub.getInvoiceDates().toString()).contains("05/07/2018"));
		assertTrue((newsub.getInvoiceDates().toString()).contains("05/08/2018"));
	}
	
	@Test
	/**
	 * Monthly subscription test case.Ensure return price is as inputted
	 */
	public void TestInvoicePrice() {
		String start = "04/06/2018";  
		String end =   "30/08/2018";

		LocalDate startDate = LocalDate.parse(start, formatter);
		LocalDate endDate = LocalDate.parse(end, formatter);

		subscriptionService.createMonthlySubscription(startDate, endDate, 20.5, 5);
		Subscription newsub = subscriptionService.getNewSubscription();
		assertTrue(newsub.getPrice() == 20.5);
	}
	
	/**
	 * Monthly test case.See if date more than 3 months is generated .SHOULD NOT create SEPTEMBER
	 */
	public void TestUnexpectedDate() {
		String start = "04/06/2018"; 
		String end =   "30/08/2018";

		LocalDate startDate = LocalDate.parse(start, formatter);
		LocalDate endDate = LocalDate.parse(end, formatter);

		subscriptionService.createMonthlySubscription(startDate, endDate, 20.5, 5);
		Subscription newsub = subscriptionService.getNewSubscription();
		
		assertFalse((newsub.getInvoiceDates().toString()).contains("05/09/2018"));
	}
}
