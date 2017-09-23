package com.gmail.pageobjects.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.gmail.framework.AbstractPage;

public class CommonMethods extends AbstractPage {

	public WebElement button(String button){
		return waitForElementVisible(By.xpath(".//div[text()='" + button + "']"));
	}
	
	public void scrollIntoAndClickLink(String linkText) {
		waitForViewScrollingToElement(By.linkText(linkText));
		scrollIntoViewAndClick(getElementByLinkText(linkText));
	}
}
