package com.gmail.pageobjects.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.gmail.framework.AbstractPage;

public class EmailDeliveryPage extends AbstractPage {

	public boolean isNewMessagePopupDisplayed() {
		return isElementDisplayed(By.xpath(".//div[@class='aYF'][contains(text(), 'New Message')]"));
	}

	public WebElement toFieldWithinPopup(){
		return findElement(By.xpath(".//textarea[@name='to']"));
	}
	
}
