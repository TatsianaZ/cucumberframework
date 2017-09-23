package com.gmail.pageobjects.utils;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import com.gmail.framework.AbstractPage;

public class BaseStepDef {
	protected static final Logger LOG = org.slf4j.LoggerFactory.getLogger(BaseStepDef.class);

	protected static GmailUser currentUser = GmailUser.firstUser();

	protected static void resetCurrentUser() {
		currentUser = GmailUser.firstUser();
	}

	protected WebDriver getDriver() {
		return AbstractPage.getDriver;
	}
}
