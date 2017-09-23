package com.gmail.tests.step_definitions;

import java.util.Map;

import com.gmail.pageobjects.common.BaseCommonLoginNavigation;

import cucumber.api.java.en.Given;

public class CommonLoginNavigationSteps extends BaseCommonLoginNavigation {

	@Given("^Gmail user is logged in with the following details$")
	public void gmailUserIsLoggedInWithTheFollowingDetails(Map<String, String> userName) throws Throwable {
		super.gmailUserIsLoggedIn(userName.get("userName"));
		
	}

}
