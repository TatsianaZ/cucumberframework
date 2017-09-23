package com.gmail.pageobjects.utils;

import com.gmail.pageobjects.pages.LoginPage;

public class LoginUtils {

	private LoginPage loginPage = new LoginPage();

	public void loginGmailWithUsernameAndPassword(String username, String password) {
		loginPage.usernameTextField().clear();
		loginPage.usernameTextField().sendKeys(username);
		loginPage.nextButton().click();
		loginPage.passwordTextField().clear();
		loginPage.passwordTextField().sendKeys(password);
		loginPage.signInButton().click();
	}
	
	public void loginGmailWithEmailAndPassword(String email, String password){
		loginPage.emailOrPhoneTextField().clear();
		loginPage.emailOrPhoneTextField().sendKeys(email);
		loginPage.nextButtonWithinNewInterface().click();
		loginPage.passwordTextFieldWithinNewInterface().clear();
		loginPage.passwordTextFieldWithinNewInterface().sendKeys(password);
		loginPage.nextButtonWithinNewInterface().click();
	}
}
