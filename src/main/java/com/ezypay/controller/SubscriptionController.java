package com.ezypay.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezypay.entity.SubscriptionType;
import com.ezypay.service.SubcriptionService;

@RestController
public class SubscriptionController {

	/**
	 * 
	 * @param startDate        start date of subscription
	 * @param endDate		   end date of subscription
	 * @param price			   price of subscription
	 * @param subscriptionType subscriptionType(D:Daily, M:Monthly, W:Weekly)
	 * @param dayoftheweek     for W:Weekly subscription day of the week to subscribe(1-Mon  7:Sun) 
	 * @param dayofthemonth    for M:Monthly subscription day of the week to subscribe(1-Mon  7:Sun) 
	 * @return JSON object with subscription details.
	 */
	@PostMapping("/createSubscription")
	public JSONObject createSubscription(
			@RequestParam("startDate") @NotNull String startDate,
			@RequestParam("endDate") @NotNull String endDate,
			@RequestParam("price") @NotNull double price,
			@RequestParam("subscriptionType") @NotNull char subscriptionType,
			@RequestParam(required = false) Integer dayoftheweek,
			@RequestParam(required = false) Integer dayofthemonth) {

		JSONObject error = new JSONObject();
		error.put("error", "error");
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate start = LocalDate.parse(startDate, formatter);
			LocalDate end = LocalDate.parse(endDate, formatter);

			SubcriptionService sub = new SubcriptionService();
			//validate to ensure dates are within 3months
			if (sub.validateDates(start, end)) {
				if (subscriptionType == SubscriptionType.DAILY.getType()) {
					//create daily subscription
					sub.createDailySubscription(start, end, price);
					return sub.createResponseJson();
				}
				if (subscriptionType == SubscriptionType.WEEKLY.getType()) {
					//create weekly subscription
					DayOfWeek weekday = sub.validateDayofTheWeek(dayoftheweek);
					if (weekday == null) {
						error.put("error", "Invalid day of the week.");
						return error;
					} else {
						sub.createWeeklySubscription(start, end, price, weekday);
						return sub.createResponseJson();
					}
				}
				if (subscriptionType == SubscriptionType.MONTHLY.getType()) {
					//create MONTHLY subscription
					if (dayofthemonth == null) {
						error.put("error", "Invalid day of the month.");
						return error;
					} else {
						sub.createMonthlySubscription(start, end, price, dayofthemonth);
						return sub.createResponseJson();
					}
				}
			} else {
				error.put("error", "Invalid Date range. Start to End date,must be within 3 months.");
				return error;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return error;
		}
		return error;
	}
}