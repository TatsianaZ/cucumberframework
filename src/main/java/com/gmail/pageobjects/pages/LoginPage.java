package com.gmail.pageobjects.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.gmail.framework.AbstractPage;

public class LoginPage extends AbstractPage {

	public WebElement usernameTextField() {
		return waitForElementVisible(By.id("Email"));
	}

	public WebElement passwordTextField() {
		return waitForElementVisible(By.id("Passwd"));
	}

	public WebElement nextButton() {
		return waitForElementVisible(By.id("next"));
	}

	public WebElement signInButton() {
		return waitForElementVisible(By.id("signIn"));
	}

	public WebElement emailOrPhoneTextField() {
		return waitForElementVisible(By.xpath(".//*[@id='identifierId' and @autocomplete='username']"));
	}

	public WebElement passwordTextFieldWithinNewInterface() {
		return waitForElementVisible(By.xpath(".//input[@type='password']"));
	}

	public WebElement nextButtonWithinNewInterface() {
		return waitForElementVisible(By.xpath(".//span[text()='Next']"));
	}

	public boolean isBannerTextDisplayed() {
		return isElementDisplayed(
				By.xpath(".//div[@class='banner']/h1[contains(text(), 'One account. All of Google')]"));
	}
}
