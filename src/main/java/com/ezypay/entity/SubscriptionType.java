package com.ezypay.entity;

public enum SubscriptionType {
	DAILY, WEEKLY, MONTHLY;

	public Character getType() {
		switch (this) {
		case DAILY:
			return 'D';
		case WEEKLY:
			return 'W';
		case MONTHLY:
			return 'M';
		default:
			return null;
		}
	}

}