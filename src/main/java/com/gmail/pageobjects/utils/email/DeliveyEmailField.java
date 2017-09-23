package com.gmail.pageobjects.utils.email;

import org.openqa.selenium.By;

public enum DeliveyEmailField {

	TO("To", By.xpath(".//textarea[@name='to']")), 
	SUBJECT("Subject", By.xpath(".//input[@placeholder='Subject']")), 
	EMAIL_NOTE("MessageText", By.xpath(".//div[@aria-label='Message Body']"));

	private String displayName;
	private By by;

	DeliveyEmailField(String displayName, By by) {
		this.displayName = displayName;
		this.by = by;
	}

	public String getDisplayName() {
		return displayName;
	}

	public By getBy() {
		return by;
	}

	public static DeliveyEmailField getByFieldDisplayName(String label) {
		for (DeliveyEmailField emailDeliveryBasicForm : DeliveyEmailField.values()) {
			if (emailDeliveryBasicForm.getDisplayName().equalsIgnoreCase(label)) {
				return emailDeliveryBasicForm;
			}
		}
		throw new UnsupportedOperationException("Field " + label + " is not in the ENUM");
	}

}
