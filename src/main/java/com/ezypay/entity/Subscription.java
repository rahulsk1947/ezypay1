package com.ezypay.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.time.DayOfWeek;

public class Subscription {

	double price;
	SubscriptionType subscriptionType;
	LocalDate startDate;
	LocalDate endDate;
	int dayOfMonth;
	DayOfWeek dayofweek;  
	ArrayList <String> InvoiceDates = new ArrayList <String>();
	

	public Subscription(LocalDate startDate, LocalDate endDate, double price, SubscriptionType subscriptionType) {
		this.subscriptionType = subscriptionType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
	}

	public Subscription(LocalDate startDate, LocalDate endDate, double price, SubscriptionType subscriptionType,
			DayOfWeek weekday) {

		this.subscriptionType = subscriptionType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		dayofweek = weekday;
	}

	public Subscription(LocalDate startDate, LocalDate endDate, double price, SubscriptionType subscriptionType, int dayofmonth) {
		this.subscriptionType = subscriptionType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.dayOfMonth = dayofmonth;
	}
	
	public void addInvoiceDate(String date) {
		InvoiceDates.add(date);
	}
	
	public ArrayList <String>  getInvoiceDates() {
		return InvoiceDates;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public SubscriptionType getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(SubscriptionType subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getDateOfMonth() {
		return dayOfMonth;
	}

	public void setDateOfMonth(int dateOfMonth) {
		this.dayOfMonth = dateOfMonth;
	}

	public DayOfWeek getDayofweekly() {
		return dayofweek;
	}

	public void setDayofweekly(DayOfWeek dayofweekly) {
		this.dayofweek = dayofweekly;
	}

}
