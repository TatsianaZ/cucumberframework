package com.gmail.pageobjects.utils.email;

import org.openqa.selenium.WebElement;

import com.gmail.pageobjects.pages.EmailDeliveryPage;

public class EmailUtils {

	private EmailDeliveryPage emailDeliveryPage = new EmailDeliveryPage();

	public void editValue(DeliveyEmailField deliverEmailField, String value) {
		WebElement element = emailDeliveryPage.waitForElementVisible(deliverEmailField.getBy());
		element.clear();
		element.sendKeys(value);
	}

}
