package com.gmail.tests.step_definitions.common;

import com.gmail.pageobjects.common.CommonMethods;
import com.gmail.pageobjects.pages.LoginPage;

import cucumber.api.java.en.When;

public class CommonStepTest {
	
	private CommonMethods commonMethods = new CommonMethods();
	private LoginPage loginPage = new LoginPage();

	@When("^the user clicks on link \"([^\"]*)\"$")
	public void theUserClicksOnLink(String linkText) throws Throwable {
		loginPage.waitForPageToLoad();
		loginPage.waitForPageToLoadAndJQueryProcessing();
		commonMethods.scrollIntoAndClickLink(linkText);
	}
	
	@When("^the user clicks on button \"([^\"]*)\"$")
	public void theUserClicksOnButton(String button) throws Throwable {
		loginPage.waitForPageToLoad();
		commonMethods.button(button).click();
	}

}
